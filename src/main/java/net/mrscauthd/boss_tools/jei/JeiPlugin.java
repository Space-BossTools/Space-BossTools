package net.mrscauthd.boss_tools.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipe;
import net.mrscauthd.boss_tools.machines.WorkbenchBlock;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock;
import net.mrscauthd.boss_tools.machines.CompressorBlock;
import net.mrscauthd.boss_tools.machines.OxygenGeneratorBlock;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock;
import net.mrscauthd.boss_tools.gui.*;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.item.RocketfinsItem;
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
    	// Generator
    	registration.addRecipeTransferHandler(GeneratorGUIGui.GuiContainerMod.class, GeneratorJeiCategory.Uid, CoalGeneratorBlock.SLOT_FUEL, 1, CoalGeneratorBlock.SLOT_FUEL + 1, inventorySlotCount);
    	// BlastFurnace
		int blastInventoryStartIndex = ItemStackToItemStackTileEntity.SLOT_FUEL + 1;
		registration.addRecipeTransferHandler(BlastFurnaceGUIGui.GuiContainerMod.class, BlastingFurnaceJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, blastInventoryStartIndex, inventorySlotCount);
    	registration.addRecipeTransferHandler(BlastFurnaceGUIGui.GuiContainerMod.class, VanillaRecipeCategoryUid.FUEL, ItemStackToItemStackTileEntity.SLOT_FUEL, 1, blastInventoryStartIndex, inventorySlotCount);
    	// Compressor
    	registration.addRecipeTransferHandler(CompressorGuiGui.GuiContainerMod.class, CompressorJeiCategory.Uid, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 1, ItemStackToItemStackTileEntity.SLOT_OUTPUT + 1, inventorySlotCount);
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, WorkbenchJeiCategory.Uid);
        registration.addGuiContainerHandler(GeneratorGUIGuiWindow.class, new CoalGeneratorGuiContainerHandler());
//    	registration.addRecipeClickArea(FuelRefineryGUIGuiWindow.class, 77, 61, 13, 13, FuelMakerJeiCategory.Uid, FuelMaker2JeiCategory.Uid);
        registration.addRecipeClickArea(BlastFurnaceGUIGuiWindow.class, BlastFurnaceGUIGuiWindow.ARROW_LEFT, BlastFurnaceGUIGuiWindow.AROOW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeClickArea(CompressorGuiGuiWindow.class, CompressorGuiGuiWindow.ARROW_LEFT, CompressorGuiGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, CompressorJeiCategory.Uid);
        registration.addGuiContainerHandler(OxygenLoaderGuiGuiWindow.class, new OxygenLoaderGuiContainerHandler());
        registration.addRecipeClickArea(OxygenBulletGeneratorGUIGuiWindow.class, 76, 30, 14, 12, OxygenGeneratorJeiCategory.Uid);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        jeiHelper = registration.getJeiHelpers();
        registration.addRecipeCategories(new OxygenMakingJeiCategory(jeiHelper.getGuiHelper()));
        registration.addRecipeCategories(new OxygenLoadingJeiCategory(jeiHelper.getGuiHelper()));
        //Neue maschine
        registration.addRecipeCategories(new OxygenGeneratorJeiCategory(jeiHelper.getGuiHelper()));
        //Genrator
        registration.addRecipeCategories(new GeneratorJeiCategory(jeiHelper.getGuiHelper()));
        //workbench
        registration.addRecipeCategories(new WorkbenchJeiCategory(jeiHelper.getGuiHelper()));
        //BlastFurnace
        registration.addRecipeCategories(new BlastingFurnaceJeiCategory(jeiHelper.getGuiHelper()));
        //RocketTier1Gui
        registration.addRecipeCategories(new Tier1RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
        //RocketTier2Gui
        registration.addRecipeCategories(new Tier2RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
        //RocketItem3Gui
        registration.addRecipeCategories(new Tier3RocketItemItemJeiCategory(jeiHelper.getGuiHelper()));
        //Compressor
        registration.addRecipeCategories(new CompressorJeiCategory(jeiHelper.getGuiHelper()));
        //Fuel Maker
        registration.addRecipeCategories(new FuelMakerJeiCategory(jeiHelper.getGuiHelper()));
        //Fuel Maker Recpie 2
        registration.addRecipeCategories(new FuelMaker2JeiCategory(jeiHelper.getGuiHelper()));
        //Rover
        registration.addRecipeCategories(new RoverJeiCategory(jeiHelper.getGuiHelper()));
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
    	//OxygenMaking
        registration.addRecipes(generateOxygenMakingRecipes(), OxygenMakingJeiCategory.Uid);
        registration.addRecipes(generateOxygenLoadingRecipes(), OxygenLoadingJeiCategory.Uid);
        //Neue maschine
        registration.addRecipes(generateOxygenGeneratorRecipes(), OxygenGeneratorJeiCategory.Uid);
        //Generator
        registration.addRecipes(generateGeneratorRecipes(), GeneratorJeiCategory.Uid);
        //workbench
        registration.addRecipes(generateWorkbenchRecipes(), WorkbenchJeiCategory.Uid);
        //workbench Tier 2
        registration.addRecipes(generateWorkbenchRecipes2(), WorkbenchJeiCategory.Uid);
        //workbench Tier 3
        registration.addRecipes(generateWorkbenchRecipes3(), WorkbenchJeiCategory.Uid);
        //BlastFurnace
        registration.addRecipes(generateBlastingFurnaceRecipes(), BlastingFurnaceJeiCategory.Uid);
        //RocketTier1Gui
        registration.addRecipes(generateTier1RocketItemItemRecipes(), Tier1RocketItemItemJeiCategory.Uid);
        //RocketTier2Gui
        registration.addRecipes(generateTier2RocketItemItemRecipes(), Tier2RocketItemItemJeiCategory.Uid);
        //RocketTier3Gui
        registration.addRecipes(generateTier3RocketItemItemRecipes(), Tier3RocketItemItemJeiCategory.Uid);
        //Compressor
        registration.addRecipes(generateCompressingRecipes(), CompressorJeiCategory.Uid);
        //Fuel Maker
        registration.addRecipes(generateFuelMakerRecipes(), FuelMakerJeiCategory.Uid);
        //fuel Maker 2 Recpie
        registration.addRecipes(generateFuelMakerRecipes2(), FuelMaker2JeiCategory.Uid);
        //Rover
        registration.addRecipes(generateRoverRecipes(), RoverJeiCategory.Uid);
        //Oil
        registration.addIngredientInfo(new ItemStack(ModInnet.OIL_BUCKET.get()),registration.getIngredientManager().getIngredientType(ItemStack.class),"  You can find Oil in the Ocean");
        // ...
    }

    private List<OxygenMakingRecipe> generateOxygenMakingRecipes() {
    	return BossToolsRecipeTypes.OXYGENMAKING.getRecipes(Minecraft.getInstance().world);
    }

    private List<ItemStack> generateOxygenLoadingRecipes() {
    	return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> is.getCapability(CapabilityOxygen.OXYGEN).orElse(null) != null).collect(Collectors.toList());
    }
    
    //New Maschine
    private List<OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper> generateOxygenGeneratorRecipes() {
        List<OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.OAK_LEAVES));
        // ...
        recipes.add(new OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper(inputs));
        return recipes;
    }
    //Generator
    private List<GeneratingRecipe> generateGeneratorRecipes() {
        return BossToolsRecipeTypes.GENERATING.getRecipes(Minecraft.getInstance().world);
    }
    //Workbench
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.ROCKET_NOSE_CONE.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_STEEL.get()));
        inputs.add(new ItemStack(ModInnet.IRON_TANK.get()));
        inputs.add(new ItemStack(ModInnet.IRON_TANK.get()));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(ModInnet.IRON_ENGINE.get()));
        outputs.add(new ItemStack(Tier1RocketItemItem.block));
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Workbench Tier 2
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes2() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.ROCKET_NOSE_CONE.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_DESH.get()));
        inputs.add(new ItemStack(ModInnet.GOLDEN_TANK.get()));
        inputs.add(new ItemStack(ModInnet.GOLDEN_TANK.get()));
        inputs.add(new ItemStack(ModInnet.OXYGEN_TANK.get()));
        inputs.add(new ItemStack(ModInnet.OXYGEN_TANK.get()));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(ModInnet.GOLDEN_ENGINE.get()));
        outputs.add(new ItemStack(Tier2RocketItemItem.block));
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Workbench Tier 3
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes3() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.ROCKET_NOSE_CONE.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.COMPRESSED_SILICON.get()));
        inputs.add(new ItemStack(ModInnet.DIAMOND_TANK.get()));
        inputs.add(new ItemStack(ModInnet.DIAMOND_TANK.get()));
        inputs.add(new ItemStack(ModInnet.OXYGEN_TANK.get()));
        inputs.add(new ItemStack(ModInnet.OXYGEN_TANK.get()));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(RocketfinsItem.block));
        inputs.add(new ItemStack(ModInnet.DIAMOND_ENGINE.get()));
        outputs.add(new ItemStack(Tier3RocketItemItem.block));
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //BlastFurnace
    private List<BlastingRecipe> generateBlastingFurnaceRecipes() {
        return BossToolsRecipeTypes.BLASTING.getRecipes(Minecraft.getInstance().world);
    }
    //Compressor
    private List<CompressingRecipe> generateCompressingRecipes() {
        return BossToolsRecipeTypes.COMPRESSING.getRecipes(Minecraft.getInstance().world);
    }
    //Fuel Maker
    private List<FuelMakerJeiCategory.FuelMakerRecipeWrapper> generateFuelMakerRecipes() {
        List<FuelMakerJeiCategory.FuelMakerRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.LAVA_BUCKET));
        outputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));//FuelBuckedItem.block //FuelBlock.FuelBucket
        // ...
        recipes.add(new FuelMakerJeiCategory.FuelMakerRecipeWrapper(inputs, outputs));
        return recipes;
    }

    //Fuel Maker Recpie 2
    private List<FuelMaker2JeiCategory.FuelMakerRecipeWrapper> generateFuelMakerRecipes2() {
        List<FuelMaker2JeiCategory.FuelMakerRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.LAVA_BUCKET));
        outputs.add(new ItemStack(ModInnet.FUEL_BARREL.get()));
        // ...
        recipes.add(new FuelMaker2JeiCategory.FuelMakerRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Rockettier1Gui
    private List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> generateTier1RocketItemItemRecipes() {
        List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.FUEL_BUCKET.get()));
        // ...
        recipes.add(new Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //RocektTier2Gui
    private List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> generateTier2RocketItemItemRecipes() {
        List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.FUEL_BARREL.get()));
        // ...
        recipes.add(new Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //RocektTier3Gui
    private List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> generateTier3RocketItemItemRecipes() { //RocketItemTier3Block
        List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(ModInnet.FUEL_BARREL.get()));
        // ...
        recipes.add(new Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //Rover
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
        //Neue maschine
        registration.addRecipeCatalyst(new ItemStack(OxygenGeneratorBlock.block), OxygenGeneratorJeiCategory.Uid);
        //Genrator
        registration.addRecipeCatalyst(new ItemStack(ModInnet.COAL_GENERATOR_BLOCK.get()), GeneratorJeiCategory.Uid);
        //workbench
        registration.addRecipeCatalyst(new ItemStack(WorkbenchBlock.block), WorkbenchJeiCategory.Uid);
        //BlastingFurnace
        registration.addRecipeCatalyst(new ItemStack(ModInnet.BLAST_FURNACE_BLOCK.get()), BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL);
        //RocketTier1Gui
        registration.addRecipeCatalyst(new ItemStack(Tier1RocketItemItem.block), Tier1RocketItemItemJeiCategory.Uid);
        //RocketTier2Gui
        registration.addRecipeCatalyst(new ItemStack(Tier2RocketItemItem.block), Tier2RocketItemItemJeiCategory.Uid);
        //RocketTier3Gui
        registration.addRecipeCatalyst(new ItemStack(Tier3RocketItemItem.block), Tier3RocketItemItemJeiCategory.Uid);
        //Compressor
        registration.addRecipeCatalyst(new ItemStack(ModInnet.COMPRESSOR_BLOCK.get()), CompressorJeiCategory.Uid);
        //FuelMaker
        registration.addRecipeCatalyst(new ItemStack(ModInnet.FUEL_REFINERY_BLOCK.get()), FuelMakerJeiCategory.Uid);
        //fuel Maker Recpie 2
        registration.addRecipeCatalyst(new ItemStack(ModInnet.FUEL_REFINERY_BLOCK.get()), FuelMaker2JeiCategory.Uid);
        //Rover
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
            this.title = "Oxygen Loading";
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
            	int speed = OxygenLoaderBlock.ENERGY_PER_TICK * CompressorBlock.ENERGY_PER_TICK;
				list.add(new StringTextComponent("Using: " + speed + " FE/t"));
        		list.add(new StringTextComponent("Total: " + (capacity / speed) + " FE"));
            }
            else if (this.getOxygenBounds().contains((int) mouseX, (int) mouseY)) {
				list.add(new StringTextComponent("Using: " + OxygenLoaderBlock.OXYGEN_PER_TICK + " Oxygen/t"));
            }

    		return list;
        }

        public Rectangle2d getOxygenBounds() {
        	return GuiHelper.getEnergyBounds(OXYGEN_LEFT, OXYGEN_TOP);
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
        public static final int OXYGEN_LEFT = 44;
        public static final int OXYGEN_TOP = 32;
        // ...
        private final String title;
        private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedOxygens;
        
        public OxygenMakingJeiCategory(IGuiHelper guiHelper) {
            this.title = "Oxygen Making";
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
        
        @Override
        public List<ITextComponent> getTooltipStrings(OxygenMakingRecipe recipe, double mouseX, double mouseY) {
        	if (this.getOxygenBounds().contains((int) mouseX, (int) mouseY)) {
        		return Collections.singletonList(new StringTextComponent("Activating Time: " + recipe.getActivaingTime()));
        	}
        	
        	return IRecipeCategory.super.getTooltipStrings(recipe, mouseX, mouseY);
        }
        
        public Rectangle2d getOxygenBounds(){
        	return GuiHelper.getOxygenBounds(OXYGEN_LEFT, OXYGEN_TOP);
        }

        @Override
        public void draw(OxygenMakingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        	IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        	
        	int activaingTime = recipe.getActivaingTime();
        	this.cachedOxygens.getUnchecked(activaingTime).draw(matrixStack, OXYGEN_LEFT, OXYGEN_TOP);
        	
        	FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			fontRenderer.drawString(matrixStack, "Oxygen: " + (activaingTime * OxygenLoaderBlock.OXYGEN_PER_TICK) , 60, 35, 0x808080);
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, OxygenMakingRecipe recipe, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(OxygenLoaderBlock.SLOT_ACTIVATING, true, 42, 47);
            stacks.set(OxygenLoaderBlock.SLOT_ACTIVATING, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
        }
    }

    //New Maschine
    public static class OxygenGeneratorJeiCategory implements IRecipeCategory<OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygengeneratorcategory");// muss klein geschrieben sein
        private static final int input1 = 0; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        public OxygenGeneratorJeiCategory(IGuiHelper guiHelper) {
            this.title = "Oxygen Bullet Generator | 3x6";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_geneartor_gui.png"), 0, 0, 144, 84);
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends OxygenGeneratorRecipeWrapper> getRecipeClass() {
            return OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper.class;
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
        public void setIngredients(OxygenGeneratorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, OxygenGeneratorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 38, 40);//Numern wie im GUI
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class OxygenGeneratorRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public OxygenGeneratorRecipeWrapper(ArrayList input) {
                this.input = input;
            }


            public ArrayList getInput() {
                return input;
            }
        }
    }
    //Genrator
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
            this.title = "Coal Generator";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/generator_gui_jei.png"), 0, 0, 144, 84);
            this.fires = createFires(guiHelper);
            this.energies = createGeneratingEnergies(guiHelper);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(GeneratingRecipe recipe, double mouseX, double mouseY) {
        	if (this.getFireBounds().contains((int) mouseX, (int) mouseY)) {
        		return Collections.singletonList(new StringTextComponent("Burn Time: " + recipe.getBurnTime()));
        	}
        	else if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
        		List<ITextComponent> list = new ArrayList<>();
        		list.add(new StringTextComponent("Generating: " + CoalGeneratorBlock.ENERGY_PER_TICK + " FE/t"));
        		list.add(new StringTextComponent("Total: " + recipe.getBurnTime() * CoalGeneratorBlock.ENERGY_PER_TICK + " FE"));
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
            stacks.init(CoalGeneratorBlock.SLOT_FUEL, true, 44, 25);//Numern wie im GUI
            // ...

            stacks.set(CoalGeneratorBlock.SLOT_FUEL, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            // ...
        }
    }
    //workbench
    public static class WorkbenchJeiCategory implements IRecipeCategory<WorkbenchJeiCategory.WorkbenchRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "workbenchcategory"); // muss klein sein !
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int input2 = 1; // THE NUMBER = SLOTID
        private static final int input3 = 2; // THE NUMBER = SLOTID
        private static final int input4 = 3; // THE NUMBER = SLOTID
        private static final int input5 = 4; // THE NUMBER = SLOTID
        private static final int input6 = 5; // THE NUMBER = SLOTID
        private static final int input7 = 6; // THE NUMBER = SLOTID
        private static final int input8 = 7; // THE NUMBER = SLOTID
        private static final int input9 = 8; // THE NUMBER = SLOTID
        private static final int input10 = 9; // THE NUMBER = SLOTID
        private static final int input11 = 10; // THE NUMBER = SLOTID
        private static final int input12 = 11; // THE NUMBER = SLOTID
        private static final int input13 = 12; // THE NUMBER = SLOTID
        private static final int input14 = 13; // THE NUMBER = SLOTID
        private static final int output1 = 14; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        public WorkbenchJeiCategory(IGuiHelper guiHelper) {
            this.title = "NASA Workbench";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/nasaworkbenchjei.png"), 0, 0, 176, 122);
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends WorkbenchRecipeWrapper> getRecipeClass() {
            return WorkbenchJeiCategory.WorkbenchRecipeWrapper.class;
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
        public void setIngredients(WorkbenchRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
            iIngredients.setOutputs(VanillaTypes.ITEM, recipeWrapper.getOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, WorkbenchRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 37, 9);
            stacks.init(input2, true, 29, 26);
            stacks.init(input3, true, 46, 26);
            stacks.init(input4, true, 46, 43);
            stacks.init(input5, true, 29, 43);
            stacks.init(input6, true, 46, 60);
            stacks.init(input7, true, 29, 60);
            stacks.init(input8, true, 46, 77);
            stacks.init(input9, true, 29, 77);
            stacks.init(input10, true, 12, 77);
            stacks.init(input11, true, 63, 77);
            stacks.init(input12, true, 63, 94);
            stacks.init(input13, true, 12, 94);
            stacks.init(input14, true, 37, 94);
            stacks.init(output1, false, 133, 81);
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(input2, iIngredients.getInputs(VanillaTypes.ITEM).get(1));
            stacks.set(input3, iIngredients.getInputs(VanillaTypes.ITEM).get(2));
            stacks.set(input4, iIngredients.getInputs(VanillaTypes.ITEM).get(3));
            stacks.set(input5, iIngredients.getInputs(VanillaTypes.ITEM).get(4));
            stacks.set(input6, iIngredients.getInputs(VanillaTypes.ITEM).get(5));
            stacks.set(input7, iIngredients.getInputs(VanillaTypes.ITEM).get(6));
            stacks.set(input8, iIngredients.getInputs(VanillaTypes.ITEM).get(7));
            stacks.set(input9, iIngredients.getInputs(VanillaTypes.ITEM).get(8));
            stacks.set(input10, iIngredients.getInputs(VanillaTypes.ITEM).get(9));
            stacks.set(input11, iIngredients.getInputs(VanillaTypes.ITEM).get(10));
            stacks.set(input12, iIngredients.getInputs(VanillaTypes.ITEM).get(11));
            stacks.set(input13, iIngredients.getInputs(VanillaTypes.ITEM).get(12));
            stacks.set(input14, iIngredients.getInputs(VanillaTypes.ITEM).get(13));
            stacks.set(output1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class WorkbenchRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public WorkbenchRecipeWrapper(ArrayList input, ArrayList output) {
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
    
    //BlastingFurnace
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
            this.title = "Blast Furnace";
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
            iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, BlastingRecipe recipe, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, true, 36, 16);//Iron
            stacks.init(ItemStackToItemStackTileEntity.SLOT_OUTPUT, false, 86, 35);//steel
            // ...

            stacks.set(ItemStackToItemStackTileEntity.SLOT_INGREDIENT, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(ItemStackToItemStackTileEntity.SLOT_OUTPUT, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
    }
    //RocketTier1Gui
    public static class Tier1RocketItemItemJeiCategory implements IRecipeCategory<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "tier1rocketitemitemcategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        public Tier1RocketItemItemJeiCategory(IGuiHelper guiHelper) {
            this.title = "Tier 1 Rocket";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jui.png"), 0, 0, 128, 71);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(Tier1RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {
            List<ITextComponent> fuel = new ArrayList<ITextComponent>();
            fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
            fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
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
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, Tier1RocketItemItemRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 13, 18);
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class Tier1RocketItemItemRecipeWrapper {
            private ArrayList input;

            public Tier1RocketItemItemRecipeWrapper(ArrayList input) {
                this.input = input;
            }


            public ArrayList getInput() {
                return input;
            }
        }
    }
    //RocketTier2Gui
    public static class Tier2RocketItemItemJeiCategory implements IRecipeCategory<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "tier2rocketitemitemcategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        public Tier2RocketItemItemJeiCategory(IGuiHelper guiHelper) {
            this.title = "Tier 2 Rocket";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jui.png"), 0, 0, 128, 71);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {
            List<ITextComponent> fuel = new ArrayList<ITextComponent>();
            fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
            fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
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
    //Compressor
    public static class CompressorJeiCategory implements IRecipeCategory<CompressingRecipe> {
    	
    	private static ResourceLocation Uid = new ResourceLocation("boss_tools", "compressorcategory");
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
            this.title = "Compressor";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_gui_jei.png"), 0, 0, 144, 84);
    		this.cachedArrows = createArrows(guiHelper);
    		this.cachedEnergies = createUsingEnergies(guiHelper);
        }

        @Override
        public List<ITextComponent> getTooltipStrings(CompressingRecipe recipe, double mouseX, double mouseY) {
            if (this.getEnergyBounds().contains((int) mouseX, (int) mouseY)) {
        		List<ITextComponent> list = new ArrayList<>();
        		list.add(new StringTextComponent("Using: " + CompressorBlock.ENERGY_PER_TICK + " FE/t"));
        		list.add(new StringTextComponent("Total: " + recipe.getCookTime() * CompressorBlock.ENERGY_PER_TICK + " FE"));
        		return list;
            }
            
            return Collections.emptyList();
        }

		private Rectangle2d getEnergyBounds() {
			return new Rectangle2d(ENERGY_LEFT, ENERGY_TOP, GuiHelper.ENERGY_WIDTH, GuiHelper.ENERGY_HEIGHT);
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
            iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
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
    //RocketTier3Gui
    public static class Tier3RocketItemItemJeiCategory implements IRecipeCategory<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "tier3rocketitemitemcategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        public Tier3RocketItemItemJeiCategory(IGuiHelper guiHelper) {
            this.title = "Tier 3 Rocket";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/rocket_gui_jui.png"), 0, 0, 128, 71);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper recipe, double mouseX, double mouseY) {
            List<ITextComponent> fuel = new ArrayList<ITextComponent>();
            fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
            fuel.add(ITextComponent.getTextComponentOrEmpty("100" + "%"));
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
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
    //FuelMaker
    public static class FuelMakerJeiCategory implements IRecipeCategory<FuelMakerJeiCategory.FuelMakerRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "fuelmakercategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int output1 = 2; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        private final IDrawable textureanimation1;
        private final IDrawable textureanimation2;
        private final IDrawable textureanimation3;
        private final IDrawable textureanimation4;
        private final IDrawable textureanimation5;
        private final IDrawable textureanimation6;
        private final IDrawable textureanimation7;
        private final IDrawable textureanimation8;
        private final IDrawable textureanimation9;
        private final IDrawable textureanimation10;
        private final IDrawable textureanimation11;
        private final IDrawable textureanimation12;
        private final IDrawable textureanimation13;
        private final IDrawable textureanimation14;
        private final IDrawable textureanimation15;
        private final IDrawable textureanimation16;
        private final IDrawable textureanimation17;
        private final IDrawable textureanimation18;
        private final IDrawable textureanimation19;
        private final IDrawable textureanimation20;
        private final IDrawable textureanimation21;
        private final IDrawable textureanimation22;
        private final IDrawable textureanimation23;

        //Animation nummber
        int counter = 9000;
        int animation = 1 ;
        public FuelMakerJeiCategory(IGuiHelper guiHelper) {
            this.title = "Fuel Refinery";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine0.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine1.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine2.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine3.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine4.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine5.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine6.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine7.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine8.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine9.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei/fuel_refine23.png"), 0, 0, 144, 84);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(FuelMakerRecipeWrapper recipe, double mouseX, double mouseY) {
            List<ITextComponent> fuel = new ArrayList<ITextComponent>();
            fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
            fuel.add(ITextComponent.getTextComponentOrEmpty("1000.0" + " mb / 3000.0 mb"));
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
            // }
            // animation = counter;
            if (mouseX > 102 && mouseX < 127 && mouseY > 16 && mouseY < 65) {
                return Collections.singletonList(new TranslationTextComponent(animation + " FE / 9000.0 FE"));
            }
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
        public Class<? extends FuelMakerRecipeWrapper> getRecipeClass() {
            return FuelMakerJeiCategory.FuelMakerRecipeWrapper.class;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public IDrawable getBackground() {
            //animation tick
            counter = counter - 1;
            if (counter <= 0){
                counter = 9000;
            }
            animation = counter;
            //animation tick
            if (counter >= 360 && counter <= 720) {
                return textureanimation1;
            }
            if (counter >= 720 && counter <= 1080) {
                return textureanimation2;
            }
            if (counter >= 1080 && counter <= 1440) {
                return textureanimation3;
            }
            if (counter >= 1440 && counter <= 1800) {
                return textureanimation4;
            }
            if (counter >= 1800 && counter <= 2160) {
                return textureanimation5;
            }
            if (counter >= 2160 && counter <= 2520) {
                return textureanimation6;
            }
            if (counter >= 2520 && counter <= 2880) {
                return textureanimation7;
            }
            if (counter >= 2880 && counter <= 3240) {
                return textureanimation8;
            }
            if (counter >= 3240 && counter <= 3600) {
                return textureanimation9;
            }
            if (counter >= 3600 && counter <= 3960) {
                return textureanimation10;
            }
            if (counter >= 3960 && counter <= 4320) {
                return textureanimation11;
            }
            if (counter >= 4320 && counter <= 4680) {
                return textureanimation12;
            }
            if (counter >= 4680 && counter <= 5040) {
                return textureanimation13;
            }
            if (counter >= 5040 && counter <= 5400) {
                return textureanimation14;
            }
            if (counter >= 5040 && counter <= 5760) {
                return textureanimation15;
            }
            if (counter >= 5760 && counter <= 6120) {
                return textureanimation16;
            }
            if (counter >= 6120 && counter <= 6480) {
                return textureanimation17;
            }
            if (counter >= 6480 && counter <= 6840) {
                return textureanimation18;
            }
            if (counter >= 6840 && counter <= 7200) {
                return textureanimation19;
            }
            if (counter >= 7200 && counter <= 7560) {
                return textureanimation20;
            }
            if (counter >= 7560 && counter <= 8000) {
                return textureanimation21;
            }
            if (counter >= 8000 && counter <= 8560) {
                return textureanimation22;
            }
            if (counter >= 8560 && counter <= 9000) {
                return textureanimation23;
            }
            return background;
        }

        @Override
        public IDrawable getIcon() {
            return null;
        }

        @Override
        public void setIngredients(FuelMakerRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
            iIngredients.setOutputs(VanillaTypes.ITEM, recipeWrapper.getOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, FuelMakerRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 52, 34);
            stacks.init(output1, false, 7, 56);// 64, 10
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(output1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class FuelMakerRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public FuelMakerRecipeWrapper(ArrayList input, ArrayList output) {
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
    //FuelMaker Recpie 2
    public static class FuelMaker2JeiCategory implements IRecipeCategory<FuelMaker2JeiCategory.FuelMakerRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "fuelmaker2category");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int output1 = 2; // THE NUMBER = SLOTID
        // ...
        private final String title;
        private final IDrawable background;
        private final IDrawable textureanimation1;
        private final IDrawable textureanimation2;
        private final IDrawable textureanimation3;
        private final IDrawable textureanimation4;
        private final IDrawable textureanimation5;
        private final IDrawable textureanimation6;
        private final IDrawable textureanimation7;
        private final IDrawable textureanimation8;
        private final IDrawable textureanimation9;
        private final IDrawable textureanimation10;
        private final IDrawable textureanimation11;
        private final IDrawable textureanimation12;
        private final IDrawable textureanimation13;
        private final IDrawable textureanimation14;
        private final IDrawable textureanimation15;
        private final IDrawable textureanimation16;
        private final IDrawable textureanimation17;
        private final IDrawable textureanimation18;
        private final IDrawable textureanimation19;
        private final IDrawable textureanimation20;
        private final IDrawable textureanimation21;
        private final IDrawable textureanimation22;
        private final IDrawable textureanimation23;

        //Animation nummber
        int counter = 9000;
        int animation = 1 ;
        public FuelMaker2JeiCategory(IGuiHelper guiHelper) {
            this.title = "Fuel Refinery";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine0.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine1.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine2.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine3.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine4.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine5.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine6.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine7.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine8.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine9.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/fuel_refine_jei2/fuel_refine23.png"), 0, 0, 144, 84);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(FuelMaker2JeiCategory.FuelMakerRecipeWrapper recipe, double mouseX, double mouseY) {
            List<ITextComponent> fuel = new ArrayList<ITextComponent>();
            fuel.add(ITextComponent.getTextComponentOrEmpty("\u00A79Fluid: \u00A77Fuel"));
            fuel.add(ITextComponent.getTextComponentOrEmpty("3000.0" + " mb / 3000.0 mb"));
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
            // }
            // animation = counter;
            if (mouseX > 102 && mouseX < 127 && mouseY > 16 && mouseY < 65) {
                return Collections.singletonList(new TranslationTextComponent(animation + " FE / 9000.0 FE"));
            }
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
        public Class<? extends FuelMakerRecipeWrapper> getRecipeClass() {
            return FuelMaker2JeiCategory.FuelMakerRecipeWrapper.class;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public IDrawable getBackground() {
            //animation tick
            counter = counter - 1;
            if (counter <= 0){
                counter = 9000;
            }
            animation = counter;
            //animation tick
            if (counter >= 360 && counter <= 720) {
                return textureanimation1;
            }
            if (counter >= 720 && counter <= 1080) {
                return textureanimation2;
            }
            if (counter >= 1080 && counter <= 1440) {
                return textureanimation3;
            }
            if (counter >= 1440 && counter <= 1800) {
                return textureanimation4;
            }
            if (counter >= 1800 && counter <= 2160) {
                return textureanimation5;
            }
            if (counter >= 2160 && counter <= 2520) {
                return textureanimation6;
            }
            if (counter >= 2520 && counter <= 2880) {
                return textureanimation7;
            }
            if (counter >= 2880 && counter <= 3240) {
                return textureanimation8;
            }
            if (counter >= 3240 && counter <= 3600) {
                return textureanimation9;
            }
            if (counter >= 3600 && counter <= 3960) {
                return textureanimation10;
            }
            if (counter >= 3960 && counter <= 4320) {
                return textureanimation11;
            }
            if (counter >= 4320 && counter <= 4680) {
                return textureanimation12;
            }
            if (counter >= 4680 && counter <= 5040) {
                return textureanimation13;
            }
            if (counter >= 5040 && counter <= 5400) {
                return textureanimation14;
            }
            if (counter >= 5040 && counter <= 5760) {
                return textureanimation15;
            }
            if (counter >= 5760 && counter <= 6120) {
                return textureanimation16;
            }
            if (counter >= 6120 && counter <= 6480) {
                return textureanimation17;
            }
            if (counter >= 6480 && counter <= 6840) {
                return textureanimation18;
            }
            if (counter >= 6840 && counter <= 7200) {
                return textureanimation19;
            }
            if (counter >= 7200 && counter <= 7560) {
                return textureanimation20;
            }
            if (counter >= 7560 && counter <= 8000) {
                return textureanimation21;
            }
            if (counter >= 8000 && counter <= 8560) {
                return textureanimation22;
            }
            if (counter >= 8560 && counter <= 9000) {
                return textureanimation23;
            }
            return background;
        }

        @Override
        public IDrawable getIcon() {
            return null;
        }

        @Override
        public void setIngredients(FuelMakerRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
            iIngredients.setOutputs(VanillaTypes.ITEM, recipeWrapper.getOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, FuelMakerRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 52, 34);
            stacks.init(output1, false, 7, 56);// 64, 10
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(output1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class FuelMakerRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public FuelMakerRecipeWrapper(ArrayList input, ArrayList output) {
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
    //RocketTier1Gui
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
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
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
    //HERE der neue code dan
}