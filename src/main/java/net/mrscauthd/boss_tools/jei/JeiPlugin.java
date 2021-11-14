package net.mrscauthd.boss_tools.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.crafting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;
import net.mrscauthd.boss_tools.crafting.OxygenBubbleDistributorRecipe;
import net.mrscauthd.boss_tools.crafting.OxygenLoaderRecipe;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGui;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGui;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGui;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGui;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGui;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGuiWindow;
import net.mrscauthd.boss_tools.jei.machineguihandlers.BlastFurnaceGuiContainerHandler;
import net.mrscauthd.boss_tools.jei.machineguihandlers.CoalGeneratorGuiContainerHandler;
import net.mrscauthd.boss_tools.jei.machineguihandlers.CompressorGuiContainerHandler;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingTileEntity;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock;
import net.mrscauthd.boss_tools.machines.CompressorBlock;
import net.mrscauthd.boss_tools.machines.FuelRefineryBlock;
import net.mrscauthd.boss_tools.gui.helper.GridPlacer;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.helper.IPlacer;
import net.mrscauthd.boss_tools.gui.helper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.RoverItemItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelper;

	private Map<Fluid, List<ItemStack>> fluidFullItemStacks;
	private List<ItemStack> oxygenFullItemStacks;

	//TODO: Rework Fluid Bar in Rockets,Rover

	public List<ItemStack> getFluidFullItemStacks(Fluid fluid){
		return this.fluidFullItemStacks.computeIfAbsent(fluid, this::generateFluidFullIngredients);
	}

	public List<ItemStack> getFluidFullItemStacks(Collection<Fluid> fluids){
		return fluids.stream().flatMap(f -> this.getFluidFullItemStacks(f).stream()).collect(Collectors.toList());
	}
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation("boss_tools", "default");
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		int inventorySlotCount = 36;
		// OxygenLoader
		registration.addRecipeTransferHandler(OxygenLoaderGui.GuiContainer.class, OxygenLoaderJeiCategory.Uid, OxygenMakingTileEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// OxygenBubbleDistributor
		registration.addRecipeTransferHandler(OxygenBubbleDistributorGui.GuiContainer.class, OxygenBubbleDistributorJeiCategory.Uid, OxygenMakingTileEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Generator
		registration.addRecipeTransferHandler(CoalGeneratorGui.GuiContainer.class, CoalGeneratorJeiCategory.Uid, CoalGeneratorBlock.SLOT_FUEL, 1, CoalGeneratorBlock.SLOT_FUEL + 1, inventorySlotCount);
		// BlastFurnace
		int blastInventoryStartIndex = BlastingFurnaceBlock.SLOT_FUEL + 1;
		registration.addRecipeTransferHandler(BlastFurnaceGui.GuiContainer.class, BlastingFurnaceJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, blastInventoryStartIndex, inventorySlotCount);
		registration.addRecipeTransferHandler(BlastFurnaceGui.GuiContainer.class, VanillaRecipeCategoryUid.FUEL, BlastingFurnaceBlock.SLOT_FUEL, 1, blastInventoryStartIndex, inventorySlotCount);
		// Compressor
		registration.addRecipeTransferHandler(CompressorGui.GuiContainer.class, CompressorJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, ItemStackToItemStackTileEntity.SLOT_OUTPUT + 1, inventorySlotCount);
		// WorkBench
		int workbenchPartSlotStart = 1 + NASAWorkbenchBlock.SLOT_PARTS;
		int workbenchPartSlotCount = NASAWorkbenchBlock.getBasicPartSlots();
		registration.addRecipeTransferHandler(NasaWorkbenchGui.GuiContainer.class, NASAWorkbenchJeiCategory.Uid, workbenchPartSlotStart, workbenchPartSlotCount, workbenchPartSlotStart + workbenchPartSlotCount, inventorySlotCount);
		//Fuel Refinery
		registration.addRecipeTransferHandler(FuelRefineryGui.GuiContainer.class, FuelRefineryJeiCategory.Uid, FuelRefineryBlock.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		//Rocket Fuel
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, Tier1RocketJeiCategory.Uid, 0, 1, 0, inventorySlotCount);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, NASAWorkbenchJeiCategory.Uid);
		registration.addGuiContainerHandler(CoalGeneratorGuiWindow.class, new CoalGeneratorGuiContainerHandler());
		registration.addRecipeClickArea(FuelRefineryGuiWindow.class, FuelRefineryGuiWindow.ARROW_LEFT, FuelRefineryGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, FuelRefineryJeiCategory.Uid);
		registration.addGuiContainerHandler(BlastFurnaceGuiWindow.class, new BlastFurnaceGuiContainerHandler());
		registration.addGuiContainerHandler(CompressorGuiWindow.class, new CompressorGuiContainerHandler());
		registration.addRecipeClickArea(OxygenLoaderGuiWindow.class, OxygenLoaderGuiWindow.ARROW_LEFT, OxygenLoaderGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenLoaderJeiCategory.Uid);
		registration.addRecipeClickArea(OxygenBubbleDistributorGuiWindow.class, OxygenBubbleDistributorGuiWindow.ARROW_LEFT, OxygenBubbleDistributorGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenBubbleDistributorJeiCategory.Uid);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelper = registration.getJeiHelpers();
		registration.addRecipeCategories(new OxygenLoaderJeiCategory(this, jeiHelper.getGuiHelper()));
		registration.addRecipeCategories(new OxygenBubbleDistributorJeiCategory(this, jeiHelper.getGuiHelper()));
		// Genrator
		registration.addRecipeCategories(new CoalGeneratorJeiCategory(jeiHelper.getGuiHelper()));
		// workbench
		registration.addRecipeCategories(new NASAWorkbenchJeiCategory(jeiHelper.getGuiHelper()));
		// BlastFurnace
		registration.addRecipeCategories(new BlastingFurnaceJeiCategory(jeiHelper.getGuiHelper()));
		// RocketTier1Gui
		registration.addRecipeCategories(new Tier1RocketJeiCategory(this, jeiHelper.getGuiHelper()));
		// RocketTier2Gui
		registration.addRecipeCategories(new Tier2RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
		// RocketItem3Gui
		registration.addRecipeCategories(new Tier3RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
		// Compressor
		registration.addRecipeCategories(new CompressorJeiCategory(jeiHelper.getGuiHelper()));
		// Fuel Maker
		registration.addRecipeCategories(new FuelRefineryJeiCategory(this, jeiHelper.getGuiHelper()));
		// Rover
		registration.addRecipeCategories(new RoverJeiCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		this.fluidFullItemStacks = new HashMap<>();
		this.oxygenFullItemStacks = this.generateOxygenLoadingItems();
		
		// OxygenLoader
		registration.addRecipes(generateOxygenLoaderRecipes(), OxygenLoaderJeiCategory.Uid);
		// OxygenBubbleDistributor
		registration.addRecipes(generateOxygenBubbleDistributorRecipes(), OxygenBubbleDistributorJeiCategory.Uid);
		// Generator
		registration.addRecipes(generateGeneratorRecipes(), CoalGeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipes(generateWorkbenchRecipes(), NASAWorkbenchJeiCategory.Uid);
		// BlastFurnace
		registration.addRecipes(generateBlastingFurnaceRecipes(), BlastingFurnaceJeiCategory.Uid);
		// RocketTier1Gui
		registration.addRecipes(generateTier1RocketItemItemRecipes(), Tier1RocketJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipes(generateTier2RocketItemItemRecipes(), Tier2RocketItemItemJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipes(generateTier3RocketItemItemRecipes(), Tier3RocketItemItemJeiCategory.Uid);
		// Compressor
		registration.addRecipes(generateCompressingRecipes(), CompressorJeiCategory.Uid);
		// Fuel Maker
		registration.addRecipes(generateFuelMakerRecipes(), FuelRefineryJeiCategory.Uid);
		// Rover
		registration.addRecipes(generateRoverRecipes(), RoverJeiCategory.Uid);
		// Oil
		registration.addIngredientInfo(new ItemStack(ModInnet.OIL_BUCKET.get(), 1), VanillaTypes.ITEM, "  You can find Oil in the Ocean");
		registration.addIngredientInfo(new FluidStack(ModInnet.OIL_STILL.get(), 1000), VanillaTypes.FLUID, "  You can find Oil in the Ocean");
		// ...
	}

	// Oxygen Loading
	private List<ItemStack> generateOxygenLoadingItems() {
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> OxygenUtil.canReceive(is)).map(OxygenUtil::makeFull).collect(Collectors.toList());
	}

	// Oxygen Loader
	private List<OxygenLoaderRecipe> generateOxygenLoaderRecipes() {
		return BossToolsRecipeTypes.OXYGENLOADER.getRecipes(Minecraft.getInstance().world);
	}

	// Oxygen Bubble Distributor
	private List<OxygenBubbleDistributorRecipe> generateOxygenBubbleDistributorRecipes() {
		return BossToolsRecipeTypes.OXYGENBUBBLEDISTRIBUTOR.getRecipes(Minecraft.getInstance().world);
	}

	// Generator
	private List<GeneratingRecipe> generateGeneratorRecipes() {
		return BossToolsRecipeTypes.GENERATING.getRecipes(Minecraft.getInstance().world);
	}

	// Workbench
	private List<WorkbenchingRecipe> generateWorkbenchRecipes() {
		return BossToolsRecipeTypes.WORKBENCHING.getRecipes(Minecraft.getInstance().world);
	}

	// BlastFurnace
	private List<BlastingRecipe> generateBlastingFurnaceRecipes() {
		return BossToolsRecipeTypes.BLASTING.getRecipes(Minecraft.getInstance().world);
	}

	// Compressor
	private List<CompressingRecipe> generateCompressingRecipes() {
		return BossToolsRecipeTypes.COMPRESSING.getRecipes(Minecraft.getInstance().world);
	}

	// Fuel Maker
	private List<ItemStack> generateFluidFullIngredients(Fluid fluid){
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> FluidUtil2.canFill(is, fluid)).map(is -> FluidUtil2.makeFull(is, fluid)).collect(Collectors.toList());
	}

	private List<FuelRefiningRecipe> generateFuelMakerRecipes() {
		return BossToolsRecipeTypes.FUELREFINING.getRecipes(Minecraft.getInstance().world);
	}

	// Rockettier1Gui
	private List<Tier1RocketJeiCategory.Tier1RocketItemItemRecipeWrapper> generateTier1RocketItemItemRecipes() {
		List<Tier1RocketJeiCategory.Tier1RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
		ArrayList<ItemStack> inputs = new ArrayList<>();
		ArrayList<FluidStack> outputs = new ArrayList<>();

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), 1000);

		outputs.add(fluidStack);

		inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
		// ...
		recipes.add(new Tier1RocketJeiCategory.Tier1RocketItemItemRecipeWrapper(inputs, outputs));
		return recipes;
	}

	// RocektTier2Gui
	private List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> generateTier2RocketItemItemRecipes() {
		List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
		ArrayList<ItemStack> inputs = new ArrayList<>();
		inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
		// ...
		recipes.add(new Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper(inputs));
		return recipes;
	}

	// RocektTier3Gui
	private List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> generateTier3RocketItemItemRecipes() { // RocketItemTier3Block
		List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
		ArrayList<ItemStack> inputs = new ArrayList<>();
		inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
		// ...
		recipes.add(new Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper(inputs));
		return recipes;
	}

	// Rover
	private List<RoverJeiCategory.RoverRecipeWrapper> generateRoverRecipes() {
		List<RoverJeiCategory.RoverRecipeWrapper> recipes = new ArrayList<>();
		ArrayList<ItemStack> inputs = new ArrayList<>();
		inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
		// ...
		recipes.add(new RoverJeiCategory.RoverRecipeWrapper(inputs));
		return recipes;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_LOADER_BLOCK.get()), OxygenLoaderJeiCategory.Uid);
		// Neue maschine
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()), OxygenBubbleDistributorJeiCategory.Uid);
		// Genrator
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COAL_GENERATOR_BLOCK.get()), CoalGeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipeCatalyst(new ItemStack(ModInnet.NASA_WORKBENCH_ITEM.get()), NASAWorkbenchJeiCategory.Uid);
		// BlastingFurnace
		registration.addRecipeCatalyst(new ItemStack(ModInnet.BLAST_FURNACE_BLOCK.get()), BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL);
		// RocketTier1Gui
		registration.addRecipeCatalyst(new ItemStack(Tier1RocketItemItem.block), Tier1RocketJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipeCatalyst(new ItemStack(Tier2RocketItemItem.block), Tier2RocketItemItemJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipeCatalyst(new ItemStack(Tier3RocketItemItem.block), Tier3RocketItemItemJeiCategory.Uid);
		// Compressor
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COMPRESSOR_BLOCK.get()), CompressorJeiCategory.Uid);
		// FuelMaker
		registration.addRecipeCatalyst(new ItemStack(ModInnet.FUEL_REFINERY_BLOCK.get()), FuelRefineryJeiCategory.Uid);
		// Rover
		registration.addRecipeCatalyst(new ItemStack(RoverItemItem.block), RoverJeiCategory.Uid);
	}

	public static class OxygenLoaderJeiCategory implements IRecipeCategory<OxygenLoaderRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygen_loader");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final String title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenLoaderJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslationTextComponent("container.boss_tools.oxygen_loader").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(OxygenLoaderRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(FuelRefineryBlock.ENERGY_PER_TICK)));
			}
			else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeDataHelper.getOxygen(recipe.getOxygen()).getText());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenLoaderRecipe> getRecipeClass() {
			return OxygenLoaderRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(OxygenLoaderRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(matrixStack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public void setIngredients(OxygenLoaderRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.oxygenFullItemStacks);

			iIngredients.setInputs(VanillaTypes.FLUID, recipe.getInput().toStacks());
		}
		
		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, OxygenLoaderRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlock.SLOT_OUTPUT_SINK, false, 90, 38);
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SINK, false, 24, 38);
			itemStacks.init(FuelRefineryBlock.SLOT_OUTPUT_SOURCE, false, 90, 8);

			itemStacks.set(FuelRefineryBlock.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			itemStacks.set(FuelRefineryBlock.SLOT_OUTPUT_SINK, iIngredients.getOutputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	public static class OxygenBubbleDistributorJeiCategory implements IRecipeCategory<OxygenBubbleDistributorRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygen_bubble_distributor");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final String title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenBubbleDistributorJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslationTextComponent("container.boss_tools.oxygen_bubble_distributor").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_bubble_distributor_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(OxygenBubbleDistributorRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(FuelRefineryBlock.ENERGY_PER_TICK)));
			}
			else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeDataHelper.getOxygen(recipe.getOxygen()).getText());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenBubbleDistributorRecipe> getRecipeClass() {
			return OxygenBubbleDistributorRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(OxygenBubbleDistributorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(matrixStack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public void setIngredients(OxygenBubbleDistributorRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setInputs(VanillaTypes.FLUID, recipe.getInput().toStacks());
		}
		
		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, OxygenBubbleDistributorRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SINK, false, 24, 38);

			itemStacks.set(FuelRefineryBlock.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.OXYGEN_TANK_WIDTH, GuiHelper.OXYGEN_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	// Genrator
	public static class CoalGeneratorJeiCategory implements IRecipeCategory<GeneratingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "generatorcategory");// muss klein geschrieben sein
		public static final int FIRE_LEFT = 45;
		public static final int FIRE_TOP = 45;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;

		// ...
		private final String title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> fires;
		private final LoadingCache<Integer, IDrawableAnimated> energies;

		public CoalGeneratorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("container.boss_tools.coal_generator").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/generator_gui_jei.png"), 0, 0, 144, 84);
			this.fires = createFires(guiHelper);
			this.energies = createGeneratingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(GeneratingRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getFireBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeDataHelper.getBurnTime(recipe.getBurnTime()).getText());
			} else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeTextHelper.getGeneratingText(GaugeDataHelper.getEnergy(CoalGeneratorBlock.ENERGY_PER_TICK)));
				return list;
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends GeneratingRecipe> getRecipeClass() {
			return GeneratingRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		public Rectangle2d getFireBounds() {
			return GuiHelper.getFireBounds(FIRE_LEFT, FIRE_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public void draw(GeneratingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			int burnTime = recipe.getBurnTime();
			this.fires.getUnchecked(burnTime).draw(matrixStack, FIRE_LEFT, FIRE_TOP);
			this.energies.getUnchecked(200).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(matrixStack, this.getBackground(), burnTime);
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(GeneratingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, GeneratingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(CoalGeneratorBlock.SLOT_FUEL, true, 44, 25);// Numern wie im GUI
			// ...

			stacks.set(CoalGeneratorBlock.SLOT_FUEL, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// workbench
	public static class NASAWorkbenchJeiCategory implements IRecipeCategory<WorkbenchingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "workbenchcategory"); // muss klein sein !
		// ...
		private final String title;
		private final IDrawable background;

		public NASAWorkbenchJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("container.boss_tools.nasa_workbench").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/nasaworkbenchjei.png"), 0, 0, 176, 122);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends WorkbenchingRecipe> getRecipeClass() {
			return WorkbenchingRecipe.class;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(WorkbenchingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, WorkbenchingRecipe recipe, IIngredients iIngredients) {
			IDrawable background = this.getBackground();
			iRecipeLayout.moveRecipeTransferButton(background.getWidth() - 20, background.getHeight() - 20);

			int slots = NASAWorkbenchBlock.SLOT_PARTS;
			GridPlacer placer = new GridPlacer();
			slots = placeRcketParts(slots, 38, 7, 1, placer::placeBottom, ModInnet.ROCKET_PART_NOSE.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 29, 25, 2, placer::placeBottom, ModInnet.ROCKET_PART_BODY.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 29, 79, 1, placer::placeRight, ModInnet.ROCKET_PART_TANK.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 11, 79, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_LEFT.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 65, 79, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_RIGHT.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 38, 97, 1, placer::placeBottom, ModInnet.ROCKET_PART_ENGINE.get(), iRecipeLayout, recipe);

			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(slots, false, 126, 72);
			stacks.set(slots, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			slots++;
		}
	}

	public static int placeRcketParts(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, IRecipeLayout iRecipeLayout, WorkbenchingRecipe recipe) {
		IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
		List<Ingredient> ingredients = recipe.getParts().get(part);

		return RocketPartGridPlacer.place(slot, left, top, mod, placer, part, (i, s, bounds) -> {
			Ingredient ingredient = (ingredients != null && i < ingredients.size()) ? ingredients.get(i) : Ingredient.EMPTY;
			stacks.init(s, true, bounds.getX(), bounds.getY());
			stacks.set(s, Lists.newArrayList(ingredient.getMatchingStacks()));
		});
	}

	public static IDrawableStatic createFireStatic(IGuiHelper guiHelper) {
		return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).build();
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper) {
		return createFireAnimated(guiHelper, 200);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, int ticks) {
		return createFireAnimated(guiHelper, createFireStatic(guiHelper), ticks);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, IDrawableStatic fireStatic, int ticks) {
		return guiHelper.createAnimatedDrawable(fireStatic, ticks, IDrawableAnimated.StartDirection.TOP, true);
	}

	public static IDrawableBuilder drawableBuilder(IGuiHelper guiHelper, ResourceLocation path, int width, int height) {
		return guiHelper.drawableBuilder(path, 0, 0, width, height).setTextureSize(width, height);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createFires(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.TOP, true);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createArrows(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ARROW_PATH, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createEnergies(IGuiHelper guiHelper, boolean inverted) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ENERGY_PATH, GuiHelper.ENERGY_WIDTH, GuiHelper.ENERGY_HEIGHT).buildAnimated(time, inverted ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.BOTTOM, inverted);
			}
		});

	}

	public static LoadingCache<Integer, IDrawableAnimated> createUsingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, true);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createGeneratingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, false);
	}

	public static void drawText(MatrixStack matrixStack, IDrawable background, String text) {
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		int stringWidth = fontRenderer.getStringWidth(text);
		fontRenderer.drawString(matrixStack, text, background.getWidth() - 5 - stringWidth, background.getHeight() - fontRenderer.FONT_HEIGHT - 5, 0x808080);
	}

	public static void drawTextTime(MatrixStack matrixStack, IDrawable background, int ticks) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String text = numberInstance.format(ticks / 20.0F) + "s";

		drawText(matrixStack, background, text);
	}

	// BlastingFurnace
	public static class BlastingFurnaceJeiCategory implements IRecipeCategory<BlastingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "blastingfurnacecategory");
		public static final int FIRE_LEFT = 37;
		public static final int FIRE_TOP = 37;
		public static final int ARROW_LEFT = 55;
		public static final int ARROW_TOP = 35;

		private final String title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> fire;
		private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

		public BlastingFurnaceJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("container.boss_tools.blast_furnace").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_gui_jei.png"), 0, 0, 144, 84);
			this.fire = createFires(guiHelper);
			this.cachedArrows = createArrows(guiHelper);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends BlastingRecipe> getRecipeClass() {
			return BlastingRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public void draw(BlastingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			int cookTime = recipe.getCookTime();
			this.fire.getUnchecked(cookTime).draw(matrixStack, FIRE_LEFT, FIRE_TOP);
			this.cachedArrows.getUnchecked(cookTime).draw(matrixStack, ARROW_LEFT, ARROW_TOP);
			drawTextTime(matrixStack, this.getBackground(), cookTime);
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(BlastingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, BlastingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, true, 36, 16);// Iron
			stacks.init(ItemStackToItemStackTileEntity.SLOT_OUTPUT, false, 86, 35);// steel
			// ...

			stacks.set(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			stacks.set(ItemStackToItemStackTileEntity.SLOT_OUTPUT, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// RocketTier1Gui
	public static class Tier1RocketJeiCategory implements IRecipeCategory<Tier1RocketJeiCategory.Tier1RocketItemItemRecipeWrapper> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "rocket_t_1_category");
		private static final int input1 = 0; // THE NUMBER = SLOTID
		// ...
		private final String title;
		private final IDrawable background;
		private final JeiPlugin plugin;

		public Tier1RocketJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = "Tier 1 Rocket";
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(Tier1RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {

			return Collections.emptyList();
		}

		@Override
		public void draw(Tier1RocketItemItemRecipeWrapper recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends Tier1RocketItemItemRecipeWrapper> getRecipeClass() {
			return Tier1RocketJeiCategory.Tier1RocketItemItemRecipeWrapper.class;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(Tier1RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());

			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(ModInnet.FUEL_STILL.get()));

			iIngredients.setOutputs(VanillaTypes.FLUID, recipeWrapper.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, Tier1RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(input1, true, 13, 18);
			// ...

			stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, false, 66, 12, 46, 46, 1000, false, null);
			fluidStacks.set(tanks, iIngredients.getOutputs(VanillaTypes.FLUID).get(0));
			tanks++;

		}

		public static class Tier1RocketItemItemRecipeWrapper {
			private ArrayList input;
			private ArrayList output;

			public Tier1RocketItemItemRecipeWrapper(ArrayList input, ArrayList output) {
				this.input = input;
				this.output = output;
			}

			public ArrayList getInput() {
				return input;
			}

			public ArrayList getOutput() {
				return output;
			}
		}
	}

	// RocketTier2Gui
	public static class Tier2RocketItemItemJeiCategory implements IRecipeCategory<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "tier2rocketitemitemcategory");
		private static final int input1 = 0; // THE NUMBER = SLOTID
		// ...
		private final String title;
		private final IDrawable background;

		public Tier2RocketItemItemJeiCategory(IGuiHelper guiHelper) {
			this.title = "Tier 2 Rocket";
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {
			List<ITextComponent> fuel = new ArrayList<ITextComponent>();
			fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
			// counter = counter - 1;
			// if (counter <= 0){
			// counter = 9000;
			// }
			// animation = counter;
			if (mouseX > 64 && mouseX < 113 && mouseY > 10 && mouseY < 59) {
				return Collections.synchronizedList(fuel);
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends Tier2RocketItemItemRecipeWrapper> getRecipeClass() {
			return Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper.class;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(Tier2RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, Tier2RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(input1, true, 13, 18);
			// ...

			stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...

		}

		public static class Tier2RocketItemItemRecipeWrapper {
			private ArrayList input;

			public Tier2RocketItemItemRecipeWrapper(ArrayList input) {
				this.input = input;
			}

			public ArrayList getInput() {
				return input;
			}
		}
	}

	// Compressor
	public static class CompressorJeiCategory implements IRecipeCategory<CompressingRecipe> {

		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "compressorcategory");
		public static final int ARROW_LEFT = 36;
		public static final int ARROW_TOP = 29;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;
		// ...
		private final String title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public CompressorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("container.boss_tools.compressor").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_gui_jei.png"), 0, 0, 144, 84);
			this.cachedArrows = createArrows(guiHelper);
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(CompressingRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(CompressorBlock.ENERGY_PER_TICK)));
				return list;
			}

			return Collections.emptyList();
		}

		private Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends CompressingRecipe> getRecipeClass() {
			return CompressingRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public void draw(CompressingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			int cookTime = recipe.getCookTime();
			this.cachedArrows.getUnchecked(cookTime).draw(matrixStack, ARROW_LEFT, ARROW_TOP);
			this.cachedEnergies.getUnchecked(cookTime).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(matrixStack, this.getBackground(), cookTime);
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(CompressingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, CompressingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, true, 14, 29);
			stacks.init(ItemStackToItemStackTileEntity.SLOT_OUTPUT, false, 69, 28);
			// ...

			stacks.set(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			stacks.set(ItemStackToItemStackTileEntity.SLOT_OUTPUT, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// RocketTier3Gui
	public static class Tier3RocketItemItemJeiCategory implements IRecipeCategory<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "tier3rocketitemitemcategory");
		private static final int input1 = 0; // THE NUMBER = SLOTID
		// ...
		private final String title;
		private final IDrawable background;

		public Tier3RocketItemItemJeiCategory(IGuiHelper guiHelper) {
			this.title = "Tier 3 Rocket";
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {
			List<ITextComponent> fuel = new ArrayList<ITextComponent>();
			fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
			// counter = counter - 1;
			// if (counter <= 0){
			// counter = 9000;
			// }
			// animation = counter;
			if (mouseX > 64 && mouseX < 113 && mouseY > 10 && mouseY < 59) {
				return Collections.synchronizedList(fuel);
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends Tier3RocketItemItemRecipeWrapper> getRecipeClass() {
			return Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper.class;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(Tier3RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, Tier3RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(input1, true, 13, 18);
			// ...

			stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...
		}

		public static class Tier3RocketItemItemRecipeWrapper {
			private ArrayList input;

			public Tier3RocketItemItemRecipeWrapper(ArrayList input) {
				this.input = input;
			}

			public ArrayList getInput() {
				return input;
			}
		}
	}

	// FuelMaker
	public static class FuelRefineryJeiCategory implements IRecipeCategory<FuelRefiningRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "fuelmakercategory");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final String title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public FuelRefineryJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslationTextComponent("container.boss_tools.fuel_refinery").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refinery_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(FuelRefiningRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(FuelRefineryBlock.ENERGY_PER_TICK)));
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelRefiningRecipe> getRecipeClass() {
			return FuelRefiningRecipe.class;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(FuelRefiningRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public void setIngredients(FuelRefiningRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getOutput().getFluids()));

			iIngredients.setInputs(VanillaTypes.FLUID, recipe.getInput().toStacks());
			iIngredients.setOutputs(VanillaTypes.FLUID, recipe.getOutput().toStacks());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelRefiningRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlock.SLOT_OUTPUT_SINK, false, 90, 38);
			itemStacks.init(FuelRefineryBlock.SLOT_INPUT_SINK, false, 24, 38);
			itemStacks.init(FuelRefineryBlock.SLOT_OUTPUT_SOURCE, false, 90, 8);

			itemStacks.set(FuelRefineryBlock.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(i -> i.stream()).collect(Collectors.toList()));
			itemStacks.set(FuelRefineryBlock.SLOT_OUTPUT_SINK, iIngredients.getOutputs(VanillaTypes.ITEM).stream().flatMap(i -> i.stream()).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
			tanks++;

			fluidStacks.init(tanks, false, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getOutputs(VanillaTypes.FLUID).get(0));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	// Rover
	public static class RoverJeiCategory implements IRecipeCategory<RoverJeiCategory.RoverRecipeWrapper> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "rovercategory");
		private static final int input1 = 0; // THE NUMBER = SLOTID
		// ...
		private final String title;
		private final IDrawable background;

		public RoverJeiCategory(IGuiHelper guiHelper) {
			this.title = "Rover";
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rover_jei.png"), 0, 0, 144, 84);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(RoverRecipeWrapper recipe, double mouseX, double mouseY) {
			List<ITextComponent> fuel = new ArrayList<ITextComponent>();
			fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
			fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
			// counter = counter - 1;
			// if (counter <= 0){
			// counter = 9000;
			// }
			// animation = counter;
			if (mouseX > 8 && mouseX < 23 && mouseY > 7 && mouseY < 56) {
				return Collections.synchronizedList(fuel);
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends RoverRecipeWrapper> getRecipeClass() {
			return RoverJeiCategory.RoverRecipeWrapper.class;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(RoverRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, RoverRecipeWrapper recipeWrapper, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(input1, true, 7, 59);
			// ...

			stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...
		}

		public static class RoverRecipeWrapper {
			private ArrayList input;

			public RoverRecipeWrapper(ArrayList input) {
				this.input = input;
			}

			public ArrayList getInput() {
				return input;
			}
		}
	}
	// HERE der neue code dan
}