
package net.mrscauthd.boss_tools.armor;

import net.mrscauthd.boss_tools.procedures.SpaceArmorBodyTickEventProcedure;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
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

public class SpaceSuit {
	//Armor Material
	public static IArmorMaterial armormaterial = new IArmorMaterial() {
		@Override
		public int getDurability(EquipmentSlotType slot) {
			return new int[]{15, 18, 16, 14}[slot.getIndex()] * 10;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slot) {
			return new int[]{4, 5, 4, 4}[slot.getIndex()];
		}

		@Override
		public int getEnchantability() {
			return 0;
		}

		@Override
		public net.minecraft.util.SoundEvent getSoundEvent() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
		}

		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public String getName() {
			return "space_armor";
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

	//OXYGEN_MASK
	public static ArmorItem OXYGEN_MASK = new ArmorItem(armormaterial, EquipmentSlotType.HEAD, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedHead = new Space_Suit_Part_1().kopf;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/spacesuitmode__layer_1_head.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitH", (true));
		}
	};

	//SPACE_SUIT
	public static ArmorItem SPACE_SUIT =  new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedBody = new Space_Suit_Part_1().Body;
			armorModel.bipedLeftArm = new Space_Suit_Part_1().arml;
			armorModel.bipedRightArm = new Space_Suit_Part_1().armr;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("\u00A79Oxygen:\u00A76 " + itemstack.getOrCreateTag().getDouble("Energy") + "\u00A78 | \u00A7c48000.0"));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/spacesuitmode__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
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

	//SPACE_PANTS
	public static ArmorItem SPACE_PANTS = new ArmorItem(armormaterial, EquipmentSlotType.LEGS, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new Space_Suit_Part_2().LeftLeg;
			armorModel.bipedRightLeg = new Space_Suit_Part_2().RightLeg;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/spacesuitmode__layer_2.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitL", (true));
		}
	};

	//SPACE_BOOTS
	public static ArmorItem SPACE_BOOTS = new ArmorItem(armormaterial, EquipmentSlotType.FEET, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new Space_Suit_Part_1().Left_Foot;
			armorModel.bipedRightLeg = new Space_Suit_Part_1().Right_Foot;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "boss_tools:textures/models/armor/spacesuitmode__layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
			entity.getPersistentData().putBoolean("SpaceSuitB", (true));
		}
	};

	//SPACE SUIT MODEL PART 1
	public static class Space_Suit_Part_1 extends EntityModel<Entity> {
		private final ModelRenderer kopf;
		private final ModelRenderer Body;
		private final ModelRenderer armr;
		private final ModelRenderer arml;
		private final ModelRenderer Left_Foot;
		private final ModelRenderer Right_Foot;
		public Space_Suit_Part_1() {
			textureWidth = 64;
			textureHeight = 64;
			kopf = new ModelRenderer(this);
			kopf.setRotationPoint(0.0F, 0.0F, 0.0F);
			setRotationAngle(kopf, -0.0175F, 0.0873F, 0.0F);
			kopf.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
			kopf.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 0.0F, 0.0F);
			Body.setTextureOffset(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
			Body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
			Body.setTextureOffset(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, 0.25F, false);
			Body.setTextureOffset(0, 55).addBox(-2.5F, 1.0F, 2.75F, 5.0F, 8.0F, 1.0F, 0.75F, false);
			armr = new ModelRenderer(this);
			armr.setRotationPoint(-5.0F, 2.0F, 0.0F);
			armr.setTextureOffset(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			arml = new ModelRenderer(this);
			arml.setRotationPoint(5.0F, 2.0F, 0.0F);
			arml.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
			Left_Foot = new ModelRenderer(this);
			Left_Foot.setRotationPoint(2.0F, 12.0F, 0.0F);
			Left_Foot.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
			Left_Foot.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
			Right_Foot = new ModelRenderer(this);
			Right_Foot.setRotationPoint(-2.0F, 12.0F, 0.0F);
			Right_Foot.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
			Right_Foot.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
				kopf.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
				Body.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
				armr.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
				arml.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
				Left_Foot.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
				Right_Foot.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
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
	public static class Space_Suit_Part_2 extends EntityModel<Entity> {
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;
		public Space_Suit_Part_2() {
			textureWidth = 64;
			textureHeight = 32;
			RightLeg = new ModelRenderer(this);
			RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
			RightLeg.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, false);
			LeftLeg = new ModelRenderer(this);
			LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
			LeftLeg.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, true);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
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
