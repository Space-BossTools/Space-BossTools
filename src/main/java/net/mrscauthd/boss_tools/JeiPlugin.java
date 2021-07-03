
package net.mrscauthd.boss_tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
//workbench
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.mrscauthd.boss_tools.block.WorkbenchBlock;
//FuelMaker
import net.mrscauthd.boss_tools.block.FuelMakerBlock;
//fuel
import net.mrscauthd.boss_tools.block.FuelBlock;
//Generator
import net.mrscauthd.boss_tools.block.GeneratorBlock;
//New maschine
import net.mrscauthd.boss_tools.block.OxygenGeneratorBlock;
//old
import net.mrscauthd.boss_tools.block.OxygenMachineBlock;
//Compressor
import net.mrscauthd.boss_tools.block.CompressorBlock;
//Space Armor100item
import net.mrscauthd.boss_tools.item.SpaceArmorItem;
//Commpressedsteel
import net.mrscauthd.boss_tools.item.CompressesteelItem;
//CompressedTinItem
import net.mrscauthd.boss_tools.item.CompressedTinItem;
//MotorItem
import net.mrscauthd.boss_tools.item.MotorItem;
//OxygenTank Item
import net.mrscauthd.boss_tools.item.OxygenTankItem;
//MotorItem Tier2
import net.mrscauthd.boss_tools.item.MotorTier2Item;
//BlastFurnace
import net.mrscauthd.boss_tools.block.BlastingFurnaceBlock;
//steel
import net.mrscauthd.boss_tools.item.SteahlItem;
//Tin
import net.mrscauthd.boss_tools.item.MoonCopperingotItem;
//Turbineitem
import net.mrscauthd.boss_tools.item.TurbineItem;
//TurbineTier2 Item
import net.mrscauthd.boss_tools.item.TurbineTier2Item;
//RocketFinsItems
import net.mrscauthd.boss_tools.item.RocketfinsItem;
//RocketItem
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem; //wichtig Vergissnicht das .block ...
//Rocket Tier 2 Item
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
//Rocket Tier 3 item
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
//CompressedSilicon
import net.mrscauthd.boss_tools.item.CompressedsiliconItem;
//SliconIngotItem
import net.mrscauthd.boss_tools.item.SiliconIngotItem;
//Engine Tier 3
import net.mrscauthd.boss_tools.item.EngineTier3Item;
//Tank Tier 3
import net.mrscauthd.boss_tools.item.TankTier3Item;
//FuelBucket
//import net.mrscauthd.boss_tools.item.FuelBuckedItem;
//Big Fuel Bucket
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;
//RocketNose
import net.mrscauthd.boss_tools.item.RocketNoseItem;
//OxygenMachine
//Rover
import net.mrscauthd.boss_tools.item.RoverItemItem;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.trees.OakTree;
import net.minecraft.item.BucketItem;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelper;
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("boss_tools", "default");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        jeiHelper = registration.getJeiHelpers();
        registration.addRecipeCategories(new OxygenMachineJeiCategory(jeiHelper.getGuiHelper()));
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
        registration.addRecipes(generateOxygenMachineRecipes(), OxygenMachineJeiCategory.Uid);
        //Neue maschine
        registration.addRecipes(generateOxygenGeneratorRecipes(), OxygenGeneratorJeiCategory.Uid);
        //Generator
        registration.addRecipes(generateGeneratorRecipes(), GeneratorJeiCategory.Uid);
        //generator Recpie 2
        registration.addRecipes(generateGeneratorRecipes2(), GeneratorJeiCategory.Uid);
        //generator Recpie 3
        registration.addRecipes(generateGeneratorRecipes3(), GeneratorJeiCategory.Uid);
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
        registration.addRecipes(generateCompressorRecipes(), CompressorJeiCategory.Uid);
        //Compresor 2 Recpie
        registration.addRecipes(generateCompressorRecipes2(), CompressorJeiCategory.Uid);
        //Compressor 3 Recpie
        registration.addRecipes(generateCompressorRecipes3(), CompressorJeiCategory.Uid);
        //Fuel Maker
        registration.addRecipes(generateFuelMakerRecipes(), FuelMakerJeiCategory.Uid);
        //fuel Maker 2 Recpie
        registration.addRecipes(generateFuelMakerRecipes2(), FuelMaker2JeiCategory.Uid);
        //Rover
        registration.addRecipes(generateRoverRecipes(), RoverJeiCategory.Uid);
        // ...
    }

    private List<OxygenMachineJeiCategory.OxygenMachineRecipeWrapper> generateOxygenMachineRecipes() {
        List<OxygenMachineJeiCategory.OxygenMachineRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(SpaceArmorItem.body));
        inputs.add(new ItemStack(Items.OAK_LEAVES));
        inputs.get(0).getTag().putDouble("Energy", 48000);
        // ...
        recipes.add(new OxygenMachineJeiCategory.OxygenMachineRecipeWrapper(inputs));
        return recipes;
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
    private List<GeneratorJeiCategory.GeneratorRecipeWrapper> generateGeneratorRecipes() {
        List<GeneratorJeiCategory.GeneratorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.COAL));
        inputs.add(new ItemStack(Items.COAL_BLOCK));
        // ...
        recipes.add(new GeneratorJeiCategory.GeneratorRecipeWrapper(inputs));
        return recipes;
    }
    //Generator
    private List<GeneratorJeiCategory.GeneratorRecipeWrapper> generateGeneratorRecipes2() {
        List<GeneratorJeiCategory.GeneratorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.COAL_BLOCK));
        // ...
        recipes.add(new GeneratorJeiCategory.GeneratorRecipeWrapper(inputs));
        return recipes;
    }
    //Generator
    private List<GeneratorJeiCategory.GeneratorRecipeWrapper> generateGeneratorRecipes3() {
        List<GeneratorJeiCategory.GeneratorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.CHARCOAL));
        // ...
        recipes.add(new GeneratorJeiCategory.GeneratorRecipeWrapper(inputs));
        return recipes;
    }
    //Workbench
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(RocketNoseItem.block)); //RocketNoseitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(CompressesteelItem.block)); //Compressesteelitem
        inputs.add(new ItemStack(MotorItem.block)); //MotorItem
        inputs.add(new ItemStack(MotorItem.block)); //MotorItem
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(TurbineItem.block)); //TurbineItem
        outputs.add(new ItemStack(Tier1RocketItemItem.block)); //RocketItem
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Workbench Tier 2
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes2() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(RocketNoseItem.block)); //RocketNoseitem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(CompressedTinItem.block)); //CompressedTinItem
        inputs.add(new ItemStack(MotorTier2Item.block)); //MotorTier2Item
        inputs.add(new ItemStack(MotorTier2Item.block)); //MotorTier2Item
        inputs.add(new ItemStack(OxygenTankItem.block)); //OxygenTankItem
        inputs.add(new ItemStack(OxygenTankItem.block)); //OxygenTankItem
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(TurbineTier2Item.block)); //TurbineTier2Item
        outputs.add(new ItemStack(Tier2RocketItemItem.block)); //RocketItemtir2
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Workbench Tier 3
    private List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> generateWorkbenchRecipes3() {
        List<WorkbenchJeiCategory.WorkbenchRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(RocketNoseItem.block)); //RocketNoseitem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(CompressedsiliconItem.block)); //CompressedsiliconItem
        inputs.add(new ItemStack(TankTier3Item.block)); //MotorTier3Item
        inputs.add(new ItemStack(TankTier3Item.block)); //MotorTier3Item
        inputs.add(new ItemStack(OxygenTankItem.block)); //OxygenTankItem
        inputs.add(new ItemStack(OxygenTankItem.block)); //OxygenTankItem
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(RocketfinsItem.block)); //Rocketfinsitems
        inputs.add(new ItemStack(EngineTier3Item.block)); //TurbineTier3Item
        outputs.add(new ItemStack(Tier3RocketItemItem.block)); //RocketItemtir3
        // ...
        recipes.add(new WorkbenchJeiCategory.WorkbenchRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //BlastFurnace
    private List<BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper> generateBlastingFurnaceRecipes() {
        List<BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.COAL));
        inputs.add(new ItemStack(Items.IRON_INGOT));
        outputs.add(new ItemStack(SteahlItem.block));
        // ...
        recipes.add(new BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper(inputs, outputs)); //Compressor
        return recipes;
    }
    //Compressor
    private List<CompressorJeiCategory.CompressorRecipeWrapper> generateCompressorRecipes() {
        List<CompressorJeiCategory.CompressorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(SteahlItem.block));
        outputs.add(new ItemStack(CompressesteelItem.block));
        // ...
        recipes.add(new CompressorJeiCategory.CompressorRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Compressor 2 Recpie
    private List<CompressorJeiCategory.CompressorRecipeWrapper> generateCompressorRecipes2() {
        List<CompressorJeiCategory.CompressorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(MoonCopperingotItem.block));
        outputs.add(new ItemStack(CompressedTinItem.block));
        // ...
        recipes.add(new CompressorJeiCategory.CompressorRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Compressor 3 Recpie
    private List<CompressorJeiCategory.CompressorRecipeWrapper> generateCompressorRecipes3() {
        List<CompressorJeiCategory.CompressorRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(SiliconIngotItem.block));
        outputs.add(new ItemStack(CompressedsiliconItem.block));
        // ...
        recipes.add(new CompressorJeiCategory.CompressorRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Fuel Maker
    private List<FuelMakerJeiCategory.FuelMakerRecipeWrapper> generateFuelMakerRecipes() {
        List<FuelMakerJeiCategory.FuelMakerRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        ArrayList<ItemStack> outputs = new ArrayList<>();
        inputs.add(new ItemStack(Items.LAVA_BUCKET));
        outputs.add(new ItemStack(FuelBlock.bucket));//FuelBuckedItem.block //FuelBlock.FuelBucket
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
        outputs.add(new ItemStack(FuelBucketBigItem.block));
        // ...
        recipes.add(new FuelMaker2JeiCategory.FuelMakerRecipeWrapper(inputs, outputs));
        return recipes;
    }
    //Rockettier1Gui
    private List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> generateTier1RocketItemItemRecipes() {
        List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(FuelBlock.bucket));
        // ...
        recipes.add(new Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //RocektTier2Gui
    private List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> generateTier2RocketItemItemRecipes() {
        List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(FuelBucketBigItem.block));
        // ...
        recipes.add(new Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //RocektTier3Gui
    private List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> generateTier3RocketItemItemRecipes() { //RocketItemTier3Block
        List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(FuelBucketBigItem.block));
        // ...
        recipes.add(new Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper(inputs));
        return recipes;
    }
    //Rover
    private List<RoverJeiCategory.RoverRecipeWrapper> generateRoverRecipes() {
        List<RoverJeiCategory.RoverRecipeWrapper> recipes = new ArrayList<>();
        ArrayList<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(FuelBlock.bucket));
        // ...
        recipes.add(new RoverJeiCategory.RoverRecipeWrapper(inputs));
        return recipes;
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(OxygenMachineBlock.block), OxygenMachineJeiCategory.Uid);
        //Neue maschine
        registration.addRecipeCatalyst(new ItemStack(OxygenGeneratorBlock.block), OxygenGeneratorJeiCategory.Uid);
        //Genrator
        registration.addRecipeCatalyst(new ItemStack(GeneratorBlock.block), GeneratorJeiCategory.Uid);
        //workbench
        registration.addRecipeCatalyst(new ItemStack(WorkbenchBlock.block), WorkbenchJeiCategory.Uid);
        //BlastingFurnace
        registration.addRecipeCatalyst(new ItemStack(BlastingFurnaceBlock.block), BlastingFurnaceJeiCategory.Uid);
        //RocketTier1Gui
        registration.addRecipeCatalyst(new ItemStack(Tier1RocketItemItem.block), Tier1RocketItemItemJeiCategory.Uid);
        //RocketTier2Gui
        registration.addRecipeCatalyst(new ItemStack(Tier2RocketItemItem.block), Tier2RocketItemItemJeiCategory.Uid);
        //RocketTier3Gui
        registration.addRecipeCatalyst(new ItemStack(Tier3RocketItemItem.block), Tier3RocketItemItemJeiCategory.Uid);
        //Compressor
        registration.addRecipeCatalyst(new ItemStack(CompressorBlock.block), CompressorJeiCategory.Uid);
        //FuelMaker
        registration.addRecipeCatalyst(new ItemStack(FuelMakerBlock.block), FuelMakerJeiCategory.Uid);
        //fuel Maker Recpie 2
        registration.addRecipeCatalyst(new ItemStack(FuelMakerBlock.block), FuelMaker2JeiCategory.Uid);
        //Rover
        registration.addRecipeCatalyst(new ItemStack(RoverItemItem.block), RoverJeiCategory.Uid);
    }
    public static class OxygenMachineJeiCategory implements IRecipeCategory<OxygenMachineJeiCategory.OxygenMachineRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygenmachinecategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int input2 = 1; // THE NUMBER = SLOTID
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
        public OxygenMachineJeiCategory(IGuiHelper guiHelper) {
            this.title = "Oxygen Loader";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/0.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/1.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/2.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/3.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/4.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/5.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/6.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/7.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/8.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/9.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/oxygen_loader_eng_bar/23.png"), 0, 0, 144, 84);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(OxygenMachineRecipeWrapper recipe, double mouseX, double mouseY) {
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
            // }
            // animation = counter;
            if (mouseX > 102 && mouseX < 127 && mouseY > 16 && mouseY < 65) {
                return Collections.singletonList(new TranslationTextComponent(animation + " FE / 9000.0 FE"));
            }
            return Collections.emptyList();
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends OxygenMachineRecipeWrapper> getRecipeClass() {
            return OxygenMachineJeiCategory.OxygenMachineRecipeWrapper.class;
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
        public void setIngredients(OxygenMachineRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, OxygenMachineRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 42, 13);
            stacks.init(input2, true, 42, 47);
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(input2, iIngredients.getInputs(VanillaTypes.ITEM).get(1));
            // ...
            //stacks.getGuiIngredients().get(0).getDisplayedIngredient().getTag().putDouble("Energy", stacks.getGuiIngredients().get(0).getDisplayedIngredient().getTag().getDouble("Energy") + 1);
            //System.out.println("test");
        }
        public static class OxygenMachineRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public OxygenMachineRecipeWrapper(ArrayList input) {
                this.input = input;
                this.output = output;
            }


            public ArrayList getInput() {
                return input;
            }
        }
    }
    //New Maschine
    public static class OxygenGeneratorJeiCategory implements IRecipeCategory<OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "oxygengeneratorcategory");// muss klein geschrieben sein
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int input2 = 1; // THE NUMBER = SLOTID
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
    public static class GeneratorJeiCategory implements IRecipeCategory<GeneratorJeiCategory.GeneratorRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "generatorcategory");// muss klein geschrieben sein
        private static final int input1 = 0; // THE NUMBER = SLOTID
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
        int counter = 0;
        int animation = 1 ;
        public GeneratorJeiCategory(IGuiHelper guiHelper) {
            this.title = "Coal Generator";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen0.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen1.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen2.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen3.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen4.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen5.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen6.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen7.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen8.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen9.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/coal_generator_jei/coalgen23.png"), 0, 0, 144, 84);
        }
        @Override
        public List<ITextComponent> getTooltipStrings(GeneratorJeiCategory.GeneratorRecipeWrapper recipe, double mouseX, double mouseY) {
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
            // }
            // animation = counter;
            if (mouseX > 106 && mouseX < 131 && mouseY > 15 && mouseY < 64) {
                return Collections.singletonList(new TranslationTextComponent(animation + " FE / 9000.0 FE"));
            }
            return Collections.emptyList();
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends GeneratorRecipeWrapper> getRecipeClass() {
            return GeneratorJeiCategory.GeneratorRecipeWrapper.class;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public IDrawable getBackground() {
            //animation tick
            counter = counter + 1;
            if (counter >= 9000){
                counter = 0;
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
        public void setIngredients(GeneratorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, GeneratorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 44, 25);//Numern wie im GUI
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class GeneratorRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public GeneratorRecipeWrapper(ArrayList input) {
                this.input = input;
            }


            public ArrayList getInput() {
                return input;
            }
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
    //BlastingFurnace
    public static class BlastingFurnaceJeiCategory implements IRecipeCategory<BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "blastingfurnacecategory");
        private static final int input1 = 0; // THE NUMBER = SLOTID
        private static final int input2 = 1; // THE NUMBER = SLOTID
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
        public BlastingFurnaceJeiCategory(IGuiHelper guiHelper) {
            this.title = "Blast Furnace";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_gui_jei.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei01.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei02.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei03.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei04.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei05.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei06.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei07.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei08.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei09.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/blast_furnace_jei_a/blast_furnace_gui_jei23.png"), 0, 0, 144, 84);
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends BlastingFurnaceRecipeWrapper> getRecipeClass() {
            return BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper.class;
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
                return textureanimation23;
            }
            if (counter >= 720 && counter <= 1080) {
                return textureanimation22;
            }
            if (counter >= 1080 && counter <= 1440) {
                return textureanimation21;
            }
            if (counter >= 1440 && counter <= 1800) {
                return textureanimation20;
            }
            if (counter >= 1800 && counter <= 2160) {
                return textureanimation19;
            }
            if (counter >= 2160 && counter <= 2520) {
                return textureanimation18;
            }
            if (counter >= 2520 && counter <= 2880) {
                return textureanimation17;
            }
            if (counter >= 2880 && counter <= 3240) {
                return textureanimation16;
            }
            if (counter >= 3240 && counter <= 3600) {
                return textureanimation15;
            }
            if (counter >= 3600 && counter <= 3960) {
                return textureanimation14;
            }
            if (counter >= 3960 && counter <= 4320) {
                return textureanimation13;
            }
            if (counter >= 4320 && counter <= 4680) {
                return textureanimation12;
            }
            if (counter >= 4680 && counter <= 5040) {
                return textureanimation11;
            }
            if (counter >= 5040 && counter <= 5400) {
                return textureanimation10;
            }
            if (counter >= 5040 && counter <= 5760) {
                return textureanimation9;
            }
            if (counter >= 5760 && counter <= 6120) {
                return textureanimation8;
            }
            if (counter >= 6120 && counter <= 6480) {
                return textureanimation7;
            }
            if (counter >= 6480 && counter <= 6840) {
                return textureanimation6;
            }
            if (counter >= 6840 && counter <= 7200) {
                return textureanimation5;
            }
            if (counter >= 7200 && counter <= 7560) {
                return textureanimation4;
            }
            if (counter >= 7560 && counter <= 8000) {
                return textureanimation3;
            }
            if (counter >= 8000 && counter <= 8560) {
                return textureanimation2;
            }
            if (counter >= 8560 && counter <= 9000) {
                return textureanimation1;
            }
            return background;
        }

        @Override
        public IDrawable getIcon() {
            return null;
        }

        @Override
        public void setIngredients(BlastingFurnaceRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
            iIngredients.setOutputs(VanillaTypes.ITEM, recipeWrapper.getOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, BlastingFurnaceRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 36, 53); //coal
            stacks.init(input2, true, 36, 16);//Iron
            stacks.init(output1, false, 86, 35);//steel
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(input2, iIngredients.getInputs(VanillaTypes.ITEM).get(1));
            stacks.set(output1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class BlastingFurnaceRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public BlastingFurnaceRecipeWrapper(ArrayList input, ArrayList output) {
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
    public static class CompressorJeiCategory implements IRecipeCategory<CompressorJeiCategory.CompressorRecipeWrapper> {
        private static ResourceLocation Uid = new ResourceLocation("boss_tools", "compressorcategory");
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
        public CompressorJeiCategory(IGuiHelper guiHelper) {
            this.title = "Compressor";
            this.background = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor0.png"), 0, 0, 144, 84);
            //animation
            this.textureanimation1 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor1.png"), 0, 0, 144, 84);
            this.textureanimation2 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor2.png"), 0, 0, 144, 84);
            this.textureanimation3 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor3.png"), 0, 0, 144, 84);
            this.textureanimation4 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor4.png"), 0, 0, 144, 84);
            this.textureanimation5 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor5.png"), 0, 0, 144, 84);
            this.textureanimation6 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor6.png"), 0, 0, 144, 84);
            this.textureanimation7 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor7.png"), 0, 0, 144, 84);
            this.textureanimation8 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor8.png"), 0, 0, 144, 84);
            this.textureanimation9 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor9.png"), 0, 0, 144, 84);
            this.textureanimation10 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor10.png"), 0, 0, 144, 84);
            this.textureanimation11 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor11.png"), 0, 0, 144, 84);
            this.textureanimation12 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor12.png"), 0, 0, 144, 84);
            this.textureanimation13 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor13.png"), 0, 0, 144, 84);
            this.textureanimation14 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor14.png"), 0, 0, 144, 84);
            this.textureanimation15 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor15.png"), 0, 0, 144, 84);
            this.textureanimation16 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor16.png"), 0, 0, 144, 84);
            this.textureanimation17 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor17.png"), 0, 0, 144, 84);
            this.textureanimation18 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor18.png"), 0, 0, 144, 84);
            this.textureanimation19 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor19.png"), 0, 0, 144, 84);
            this.textureanimation20 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor20.png"), 0, 0, 144, 84);
            this.textureanimation21 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor21.png"), 0, 0, 144, 84);
            this.textureanimation22 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor22.png"), 0, 0, 144, 84);
            this.textureanimation23 = guiHelper.createDrawable(new ResourceLocation("boss_tools", "textures/compressor_jei/compressor23.png"), 0, 0, 144, 84);
        }

        @Override
        public List<ITextComponent> getTooltipStrings(CompressorRecipeWrapper recipe, double mouseX, double mouseY) {
            //   counter = counter - 1;
            //    if (counter <= 0){
            //        counter = 9000;
            // }
            // animation = counter;
            if (mouseX > 102 && mouseX < 127 && mouseY > 16 && mouseY < 65) {
                return Collections.singletonList(new TranslationTextComponent(animation + " FE / 9000.0 FE"));
            }
            return Collections.emptyList();
        }

        @Override
        public ResourceLocation getUid() {
            return Uid;
        }

        @Override
        public Class<? extends CompressorRecipeWrapper> getRecipeClass() {
            return CompressorJeiCategory.CompressorRecipeWrapper.class;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public IDrawable getBackground() {
            //animation tick
            counter = counter - 1;
            if (counter <= 1){
                counter = 9000;
            }
            animation = counter;
            //animation tick
            if (counter >= 360 && counter <= 720) {
                return textureanimation23;
            }
            if (counter >= 720 && counter <= 1080) {
                return textureanimation22;
            }
            if (counter >= 1080 && counter <= 1440) {
                return textureanimation21;
            }
            if (counter >= 1440 && counter <= 1800) {
                return textureanimation20;
            }
            if (counter >= 1800 && counter <= 2160) {
                return textureanimation19;
            }
            if (counter >= 2160 && counter <= 2520) {
                return textureanimation18;
            }
            if (counter >= 2520 && counter <= 2880) {
                return textureanimation17;
            }
            if (counter >= 2880 && counter <= 3240) {
                return textureanimation16;
            }
            if (counter >= 3240 && counter <= 3600) {
                return textureanimation15;
            }
            if (counter >= 3600 && counter <= 3960) {
                return textureanimation14;
            }
            if (counter >= 3960 && counter <= 4320) {
                return textureanimation13;
            }
            if (counter >= 4320 && counter <= 4680) {
                return textureanimation12;
            }
            if (counter >= 4680 && counter <= 5040) {
                return textureanimation11;
            }
            if (counter >= 5040 && counter <= 5400) {
                return textureanimation10;
            }
            if (counter >= 5040 && counter <= 5760) {
                return textureanimation9;
            }
            if (counter >= 5760 && counter <= 6120) {
                return textureanimation8;
            }
            if (counter >= 6120 && counter <= 6480) {
                return textureanimation7;
            }
            if (counter >= 6480 && counter <= 6840) {
                return textureanimation6;
            }
            if (counter >= 6840 && counter <= 7200) {
                return textureanimation5;
            }
            if (counter >= 7200 && counter <= 7560) {
                return textureanimation4;
            }
            if (counter >= 7560 && counter <= 8000) {
                return textureanimation3;
            }
            if (counter >= 8000 && counter <= 8560) {
                return textureanimation2;
            }
            if (counter >= 8560 && counter <= 9000) {
                return textureanimation1;
            }
            return background;
        }

        @Override
        public IDrawable getIcon() {
            return null;
        }

        @Override
        public void setIngredients(CompressorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            iIngredients.setInputs(VanillaTypes.ITEM, recipeWrapper.getInput());
            iIngredients.setOutputs(VanillaTypes.ITEM, recipeWrapper.getOutput());
        }

        @Override
        public void setRecipe(IRecipeLayout iRecipeLayout, CompressorRecipeWrapper recipeWrapper, IIngredients iIngredients) {
            IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
            stacks.init(input1, true, 14, 29);
            stacks.init(output1, false, 69, 28);
            // ...

            stacks.set(input1, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
            stacks.set(output1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
            // ...
        }
        public static class CompressorRecipeWrapper {
            private ArrayList input;
            private ArrayList output;

            public CompressorRecipeWrapper(ArrayList input, ArrayList output) {
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