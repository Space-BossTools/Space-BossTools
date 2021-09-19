package net.mrscauthd.boss_tools.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.gui.screens.RocketGUIWindow;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock;
import net.mrscauthd.boss_tools.machines.CompressorBlock;
import net.mrscauthd.boss_tools.machines.FuelRefineryBlock;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock;
import net.mrscauthd.boss_tools.gui.*;
import net.mrscauthd.boss_tools.gui.guihelper.GridPlacer;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.gui.guihelper.IPlacer;
import net.mrscauthd.boss_tools.gui.guihelper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.RoverItemItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelper;

	private List<ItemStack> oilFullItemStacks;
	private List<ItemStack> fuelFullItemStacks;

	//TODO: Rework Fluid Bar in Rockets,Rover

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation("boss_tools", "default");
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		int inventorySlotCount = 36;
		// OxygenLoader
		int oxygenLoaderInventoryStartIndex = OxygenLoaderBlock.SLOT_ACTIVATING + 1;
		registration.addRecipeTransferHandler(OxygenLoaderGuiGui.GuiContainerMod.class, OxygenMakingJeiCategory.Uid, OxygenLoaderBlock.SLOT_ACTIVATING, 1, oxygenLoaderInventoryStartIndex, inventorySlotCount);
		registration.addRecipeTransferHandler(OxygenLoaderGuiGui.GuiContainerMod.class, OxygenLoadingJeiCategory.Uid, OxygenLoaderBlock.SLOT_ITEM, 1, oxygenLoaderInventoryStartIndex, inventorySlotCount);
		// OxygenBulletGenerator
		registration.addRecipeTransferHandler(OxygenBubbleDistributorGUI.GuiContainerMod.class, OxygenMakingJeiCategory.Uid, OxygenBubbleDistributorBlock.SLOT_ACTIVATING, 1, OxygenBubbleDistributorBlock.SLOT_ACTIVATING + 1, inventorySlotCount);
		// Generator
		registration.addRecipeTransferHandler(GeneratorGUIGui.GuiContainerMod.class, GeneratorJeiCategory.Uid, CoalGeneratorBlock.SLOT_FUEL, 1, CoalGeneratorBlock.SLOT_FUEL + 1, inventorySlotCount);
		// BlastFurnace
		int blastInventoryStartIndex = BlastingFurnaceBlock.SLOT_FUEL + 1;
		registration.addRecipeTransferHandler(BlastFurnaceGUIGui.GuiContainerMod.class, BlastingFurnaceJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, blastInventoryStartIndex, inventorySlotCount);
		registration.addRecipeTransferHandler(BlastFurnaceGUIGui.GuiContainerMod.class, VanillaRecipeCategoryUid.FUEL, BlastingFurnaceBlock.SLOT_FUEL, 1, blastInventoryStartIndex, inventorySlotCount);
		// Compressor
		registration.addRecipeTransferHandler(CompressorGuiGui.GuiContainerMod.class, CompressorJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, ItemStackToItemStackTileEntity.SLOT_OUTPUT + 1, inventorySlotCount);
		// WorkBench
		int workbenchPartSlotStart = 1 + NASAWorkbenchBlock.SLOT_PARTS;
		int workbenchPartSlotCount = NASAWorkbenchBlock.getBasicPartSlots();
		registration.addRecipeTransferHandler(NasaWorkbenchGui.GuiContainerMod.class, WorkbenchJeiCategory.Uid, workbenchPartSlotStart, workbenchPartSlotCount, workbenchPartSlotStart + workbenchPartSlotCount, inventorySlotCount);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, WorkbenchJeiCategory.Uid);
		registration.addGuiContainerHandler(GeneratorGUIGuiWindow.class, new CoalGeneratorGuiContainerHandler());
		registration.addRecipeClickArea(FuelRefineryGUIGuiWindow.class, FuelRefineryGUIGuiWindow.ARROW_LEFT, FuelRefineryGUIGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, FuelMakerJeiCategory.Uid);
		registration.addGuiContainerHandler(BlastFurnaceGUIGuiWindow.class, new BlastFurnaceGuiContainerHandler());
		registration.addGuiContainerHandler(CompressorGuiGuiWindow.class, new CompressorGuiContainerHandler());
		registration.addGuiContainerHandler(OxygenLoaderGuiGuiWindow.class, new OxygenLoaderGuiContainerHandler());
		registration.addGuiContainerHandler(OxygenBubbleDistributorGUIWindow.class, new OxygenGeneratorGuiContainerHandler());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelper = registration.getJeiHelpers();
		registration.addRecipeCategories(new OxygenMakingJeiCategory(jeiHelper.getGuiHelper()));
		registration.addRecipeCategories(new OxygenLoadingJeiCategory(jeiHelper.getGuiHelper()));
		// Genrator
		registration.addRecipeCategories(new GeneratorJeiCategory(jeiHelper.getGuiHelper()));
		// workbench
		registration.addRecipeCategories(new WorkbenchJeiCategory(jeiHelper.getGuiHelper()));
		// BlastFurnace
		registration.addRecipeCategories(new BlastingFurnaceJeiCategory(jeiHelper.getGuiHelper()));
		// RocketTier1Gui
		registration.addRecipeCategories(new Tier1RocketItemItemJeiCategory(this, jeiHelper.getGuiHelper()));
		// RocketTier2Gui
		registration.addRecipeCategories(new Tier2RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
		// RocketItem3Gui
		registration.addRecipeCategories(new Tier3RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
		// Compressor
		registration.addRecipeCategories(new CompressorJeiCategory(jeiHelper.getGuiHelper()));
		// Fuel Maker
		registration.addRecipeCategories(new FuelMakerJeiCategory(this, jeiHelper.getGuiHelper()));
		// Rover
		registration.addRecipeCategories(new RoverJeiCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		// OxygenMaking
		registration.addRecipes(generateOxygenMakingRecipes(), OxygenMakingJeiCategory.Uid);
		registration.addRecipes(generateOxygenLoadingRecipes(), OxygenLoadingJeiCategory.Uid);
		// Generator
		registration.addRecipes(generateGeneratorRecipes(), GeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipes(generateWorkbenchRecipes(), WorkbenchJeiCategory.Uid);
		// BlastFurnace
		registration.addRecipes(generateBlastingFurnaceRecipes(), BlastingFurnaceJeiCategory.Uid);
		// RocketTier1Gui
		this.fuelFullItemStacks = this.generateFluidFullIngredients(ModInnet.FUEL_STILL.get());
		registration.addRecipes(generateTier1RocketItemItemRecipes(), Tier1RocketItemItemJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipes(generateTier2RocketItemItemRecipes(), Tier2RocketItemItemJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipes(generateTier3RocketItemItemRecipes(), Tier3RocketItemItemJeiCategory.Uid);
		// Compressor
		registration.addRecipes(generateCompressingRecipes(), CompressorJeiCategory.Uid);
		// Fuel Maker
		this.fuelFullItemStacks = this.generateFluidFullIngredients(ModInnet.FUEL_STILL.get());
		this.oilFullItemStacks = this.generateFluidFullIngredients(ModInnet.OIL_STILL.get());
		registration.addRecipes(generateFuelMakerRecipes(), FuelMakerJeiCategory.Uid);
		// Rover
		registration.addRecipes(generateRoverRecipes(), RoverJeiCategory.Uid);
		// Oil
		registration.addIngredientInfo(new ItemStack(ModInnet.OIL_BUCKET.get(), 1), VanillaTypes.ITEM, "  You can find Oil in the Ocean");
		registration.addIngredientInfo(new FluidStack(ModInnet.OIL_STILL.get(), 1000), VanillaTypes.FLUID, "  You can find Oil in the Ocean");
		// ...
	}

	private List<OxygenMakingRecipe> generateOxygenMakingRecipes() {
		return BossToolsRecipeTypes.OXYGENMAKING.getRecipes(Minecraft.getInstance().world);
	}

	private List<ItemStack> generateOxygenLoadingRecipes() {
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> is.getCapability(CapabilityOxygen.OXYGEN).orElse(null) != null).collect(Collectors.toList());
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
	private List<ItemStack> generateFluidEmptyIngredients(Fluid fluid){
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> FluidUtil2.canFill(is, fluid)).collect(Collectors.toList());
	}

	private List<ItemStack> generateFluidFullIngredients(Fluid fluid){
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> FluidUtil2.canFill(is, fluid)).map(is -> FluidUtil2.makeFull(is, fluid)).collect(Collectors.toList());
	}

	private List<FuelRefiningRecipe> generateFuelMakerRecipes() {
		return BossToolsRecipeTypes.FUELREFINING.getRecipes(Minecraft.getInstance().world);
	}

	// Rockettier1Gui
	private List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> generateTier1RocketItemItemRecipes() {
		List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
		ArrayList<ItemStack> inputs = new ArrayList<>();
		ArrayList<FluidStack> outputs = new ArrayList<>();

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), 1000);

		outputs.add(fluidStack);

		inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
		// ...
		recipes.add(new Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper(inputs, outputs));
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
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_LOADER_BLOCK.get()), OxygenMakingJeiCategory.Uid, OxygenLoadingJeiCategory.Uid);
		// Neue maschine
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_LOADER_BLOCK.get()), OxygenMakingJeiCategory.Uid);
		// Genrator
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COAL_GENERATOR_BLOCK.get()), GeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipeCatalyst(new ItemStack(ModInnet.NASA_WORKBENCH_ITEM.get()), WorkbenchJeiCategory.Uid);
		// BlastingFurnace
		registration.addRecipeCatalyst(new ItemStack(ModInnet.BLAST_FURNACE_BLOCK.get()), BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL);
		// RocketTier1Gui
		registration.addRecipeCatalyst(new ItemStack(Tier1RocketItemItem.block), Tier1RocketItemItemJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipeCatalyst(new ItemStack(Tier2RocketItemItem.block), Tier2RocketItemItemJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipeCatalyst(new ItemStack(Tier3RocketItemItem.block), Tier3RocketItemItemJeiCategory.Uid);
		// Compressor
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COMPRESSOR_BLOCK.get()), CompressorJeiCategory.Uid);
		// FuelMaker
		registration.addRecipeCatalyst(new ItemStack(ModInnet.FUEL_REFINERY_BLOCK.get()), FuelMakerJeiCategory.Uid);
		// Rover
		registration.addRecipeCatalyst(new ItemStack(RoverItemItem.block), RoverJeiCategory.Uid);
	}

	public static class OxygenLoadingJeiCategory implements IRecipeCategory<ItemStack> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygenloadingcategory");
		public static final int OXYGEN_LEFT = 44;
		public static final int OXYGEN_TOP = 32;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;
		// ...
		private final String title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedOxygens;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenLoadingJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("category.boss_tools.oxygenloading").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loading_jei.png"), 0, 0, 144, 84);
			this.cachedOxygens = createUsingOxygens(guiHelper);
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends ItemStack> getRecipeClass() {
			return ItemStack.class;
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
		public void setIngredients(ItemStack recipe, IIngredients iIngredients) {
			iIngredients.setInput(VanillaTypes.ITEM, recipe);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(ItemStack recipe, double mouseX, double mouseY) {
			List<ITextComponent> list = new ArrayList<>();
			int capacity = recipe.getCapability(CapabilityOxygen.OXYGEN).map(i -> i.getMaxOxygenStored()).orElse(0);

			if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
				list.add(GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(OxygenLoaderBlock.ENERGY_PER_TICK)));
			} else if (this.getOxygenBounds().contains((int) mouseX, (int) mouseY)) {
				list.add(GaugeTextHelper.getUsingText(GaugeDataHelper.getOxygen(OxygenLoaderBlock.OXYGEN_PER_TICK)));
			}

			return list;
		}

		public Rectangle2d getOxygenBounds() {
			return GuiHelper.getOxygenBounds(OXYGEN_LEFT, OXYGEN_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public void draw(ItemStack recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			int activaingTime = 200;
			this.cachedOxygens.getUnchecked(activaingTime).draw(matrixStack, OXYGEN_LEFT, OXYGEN_TOP);
			this.cachedEnergies.getUnchecked(activaingTime).draw(matrixStack, ENERGY_LEFT, ENERGY_TOP);

			IOxygenStorage oxygenStorage = recipe.getCapability(CapabilityOxygen.OXYGEN).orElse(null);

			if (oxygenStorage != null) {
				if (oxygenStorage.receiveOxygen(OxygenLoaderBlock.OXYGEN_PER_TICK, false) == 0) {
					oxygenStorage.setOxygenStored(0);
				}

				drawTextTime(matrixStack, this.getBackground(), oxygenStorage.getMaxOxygenStored() / OxygenLoaderBlock.OXYGEN_PER_TICK);
			}
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, ItemStack recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(OxygenLoaderBlock.SLOT_ITEM, true, 42, 13);
			stacks.set(OxygenLoaderBlock.SLOT_ITEM, recipe);
		}
	}

	public static class OxygenMakingJeiCategory implements IRecipeCategory<OxygenMakingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygenmakingcategory");
		public static final int OXYGEN_LEFT = 32;
		public static final int OXYGEN_TOP = 25;
		// ...
		private final String title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedOxygens;

		public OxygenMakingJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("category.boss_tools.oxygenmaking").getString();
			ResourceLocation path = new ResourceLocation("boss_tools", "textures/oxygen_making_jei.png");
			this.background = guiHelper.createDrawable(path, 0, 0, 144, 84);
			this.cachedOxygens = createMakingOxygens(guiHelper);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenMakingRecipe> getRecipeClass() {
			return OxygenMakingRecipe.class;
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
		public void setIngredients(OxygenMakingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
		}

		public Rectangle2d getOxygenBounds() {
			return GuiHelper.getOxygenBounds(OXYGEN_LEFT, OXYGEN_TOP);
		}

		@Override
		public void draw(OxygenMakingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);

			int activaingTime = 200;
			this.cachedOxygens.getUnchecked(activaingTime).draw(matrixStack, OXYGEN_LEFT, OXYGEN_TOP);

			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			fontRenderer.func_243248_b(matrixStack, GaugeDataHelper.getOxygen(recipe.getOxygen()).getText(), 54, 35, 0x404040);
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, OxygenMakingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(OxygenLoaderBlock.SLOT_ACTIVATING, true, 30, 42);
			stacks.set(OxygenLoaderBlock.SLOT_ACTIVATING, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
		}
	}

	// Genrator
	public static class GeneratorJeiCategory implements IRecipeCategory<GeneratingRecipe> {
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

		public GeneratorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslationTextComponent("container.boss_tools.coal_generator").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/generator_gui_jei.png"), 0, 0, 144, 84);
			this.fires = createFires(guiHelper);
			this.energies = createGeneratingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(GeneratingRecipe recipe, double mouseX, double mouseY) {
			if (this.getFireBounds().contains((int) mouseX, (int) mouseY)) {
				return Collections.singletonList(GaugeDataHelper.getBurnTime(recipe.getBurnTime()).getText());
			} else if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
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
	public static class WorkbenchJeiCategory implements IRecipeCategory<WorkbenchingRecipe> {
		private static ResourceLocation Uid = new ResourceLocation("boss_tools", "workbenchcategory"); // muss klein sein !
		// ...
		private final String title;
		private final IDrawable background;

		public WorkbenchJeiCategory(IGuiHelper guiHelper) {
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

	public static LoadingCache<Integer, IDrawableAnimated> createOxygens(IGuiHelper guiHelper, boolean inverted) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.OXYGEN_PATH, GuiHelper.OXYGEN_WIDTH, GuiHelper.OXYGEN_HEIGHT).buildAnimated(time, inverted ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.BOTTOM, inverted);
			}
		});

	}

	public static LoadingCache<Integer, IDrawableAnimated> createUsingOxygens(IGuiHelper guiHelper) {
		return createOxygens(guiHelper, true);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createMakingOxygens(IGuiHelper guiHelper) {
		return createOxygens(guiHelper, false);
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
	public static class Tier1RocketItemItemJeiCategory implements IRecipeCategory<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> {
		private static ResourceLocation Uid = new ResourceLocation("boss_tools", "rocket_t_1_category");
		private static final int input1 = 0; // THE NUMBER = SLOTID
		// ...
		private final String title;
		private final IDrawable background;
		private final JeiPlugin plugin;

		public Tier1RocketItemItemJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
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
			return Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper.class;
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

			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.fuelFullItemStacks);

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
		private static ResourceLocation Uid = new ResourceLocation("boss_tools", "tier2rocketitemitemcategory");
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
			if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
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
		private static ResourceLocation Uid = new ResourceLocation("boss_tools", "tier3rocketitemitemcategory");
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
	public static class FuelMakerJeiCategory implements IRecipeCategory<FuelRefiningRecipe> {
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

		public FuelMakerJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslationTextComponent("container.boss_tools.fuel_refinery").getString();
			this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refinery_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<ITextComponent> getTooltipStrings(FuelRefiningRecipe recipe, double mouseX, double mouseY) {
			if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
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
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.oilFullItemStacks);
			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.fuelFullItemStacks);

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
		private static ResourceLocation Uid = new ResourceLocation("boss_tools", "rovercategory");
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