package net.mrscauthd.boss_tools.armor;

import net.mrscauthd.boss_tools.procedures.SpaceArmorBodyTickEventProcedure;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.capability.SpaceSuitCapabilityProvider;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.BipedModel;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

public class NetheriteSpaceSuit {
	//Armor Material
	public static IArmorMaterial armormaterial = new IArmorMaterial() {
		@Override
		public int getDurability(EquipmentSlotType slot) {
			return new int[]{48, 55, 60, 40}[slot.getIndex()] * 10;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slot) {
			return new int[]{3, 6, 8, 3}[slot.getIndex()];
		}

		@Override
		public int getEnchantability() {
			return 0;
		}

		@Override
		public net.minecraft.util.SoundEvent getSoundEvent() {
			return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
		}

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public String getName() {
			return "netherite_space_suit";
		}

		@Override
		public float getToughness() {
			return 0f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0f;
		}
	};

	//NETHERITE_OXYGEN_MASK
	public static ArmorItem NETHERITE_OXYGEN_MASK = new ArmorItem(armormaterial, EquipmentSlotType.HEAD, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedHead = new Netherite_Space_Suit_Part_1().Head;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/netherite_space_suit_head.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitH", (true));
		}
	};

	//NETHERITE_SPACE_SUIT
	public static ArmorItem NETHERITE_SPACE_SUIT = new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedBody = new Netherite_Space_Suit_Part_1().Body;
			armorModel.bipedLeftArm = new Netherite_Space_Suit_Part_1().ArmLeft;
			armorModel.bipedRightArm = new Netherite_Space_Suit_Part_1().ArmRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			return new SpaceSuitCapabilityProvider(stack, 48000);
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

			if (oxygenStorage != null) {
				list.add(new StringTextComponent("\u00A79Oxygen:\u00A76 " + oxygenStorage.getOxygenStored() + " mB\u00A78 | \u00A7c" + oxygenStorage.getMaxOxygenStored() + " mB"));
			}
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/netherite_space_suit.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("itemstack", itemstack);
				$_dependencies.put("world", world);
				SpaceArmorBodyTickEventProcedure.executeProcedure($_dependencies);
			}
		}
	};

	//NETHERITE_SPACE_PANTS
	public static ArmorItem NETHERITE_SPACE_PANTS = new ArmorItem(armormaterial, EquipmentSlotType.LEGS, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new Netherite_Space_Suit_Part_2().LegLeft;
			armorModel.bipedRightLeg = new Netherite_Space_Suit_Part_2().LegRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/netherite_space_suit_legs.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitL", (true));
		}
	};

	//NETHERITE_SPACE_BOOTS
	public static ArmorItem NETHERITE_SPACE_BOOTS = new ArmorItem(armormaterial, EquipmentSlotType.FEET, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new Netherite_Space_Suit_Part_1().FootLeft;
			armorModel.bipedRightLeg = new Netherite_Space_Suit_Part_1().FootRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/netherite_space_suit.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitB", (true));
		}
	};

	//SPACE SUIT MODEL PART 1
	public static class Netherite_Space_Suit_Part_1 extends EntityModel<Entity> {
		private final ModelRenderer Head;
		private final ModelRenderer Body;
		private final ModelRenderer ArmRight;
		private final ModelRenderer ArmLeft;
		private final ModelRenderer FootRight;
		private final ModelRenderer FootLeft;
		public Netherite_Space_Suit_Part_1() {
			textureWidth = 64;
			textureHeight = 64;
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 0.0F, 0.0F);
			setRotationAngle(Head, -0.0175F, 0.0873F, 0.0F);
			Head.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
			Head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 0.0F, 0.0F);
			Body.setTextureOffset(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
			Body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
			Body.setTextureOffset(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, 0.25F, false);
			Body.setTextureOffset(0, 55).addBox(-2.5F, 1.0F, 2.75F, 5.0F, 8.0F, 1.0F, 0.75F, false);
			ArmRight = new ModelRenderer(this);
			ArmRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
			ArmRight.setTextureOffset(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			ArmLeft = new ModelRenderer(this);
			ArmLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
			ArmLeft.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			FootLeft = new ModelRenderer(this);
			FootLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
			FootLeft.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
			FootLeft.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
			FootRight = new ModelRenderer(this);
			FootRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
			FootRight.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
			FootRight.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			Head.render(matrixStack, buffer, packedLight, packedOverlay);
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			ArmRight.render(matrixStack, buffer, packedLight, packedOverlay);
			ArmLeft.render(matrixStack, buffer, packedLight, packedOverlay);
			FootLeft.render(matrixStack, buffer, packedLight, packedOverlay);
			FootRight.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {

		}

	}

	//SPACE SUIT MODEL PART 2
	public static class Netherite_Space_Suit_Part_2 extends EntityModel<Entity> {
		private final ModelRenderer LegRight;
		private final ModelRenderer LegLeft;
		public Netherite_Space_Suit_Part_2() {
			textureWidth = 64;
			textureHeight = 32;
			LegRight = new ModelRenderer(this);
			LegRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
			LegRight.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, false);
			LegLeft = new ModelRenderer(this);
			LegLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
			LegLeft.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, true);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			LegRight.render(matrixStack, buffer, packedLight, packedOverlay);
			LegLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {

		}

	}

}