package net.mrscauthd.boss_tools.armor;

import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.armormaterial.SpaceSuitNetheriteArmorMaterial;
import net.mrscauthd.boss_tools.entity.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.capability.SpaceSuitCapabilityProvider;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.entity.model.BipedModel;

import java.util.List;

public class NetheriteSpaceSuit {

	public static double OXYGEN_TIMER = 0;

	public static ArmorItem NETHERITE_OXYGEN_MASK = new ArmorItem(SpaceSuitNetheriteArmorMaterial.ARMOR_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedHead = new SpaceSuitModel.SPACE_SUIT_P1().Head;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/netherite_space_suit_head.png";
		}
	};

	public static ArmorItem NETHERITE_SPACE_SUIT = new ArmorItem(SpaceSuitNetheriteArmorMaterial.ARMOR_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedBody = new SpaceSuitModel.SPACE_SUIT_P1().Body;
			armorModel.bipedLeftArm = new SpaceSuitModel.SPACE_SUIT_P1().ArmLeft;
			armorModel.bipedRightArm = new SpaceSuitModel.SPACE_SUIT_P1().ArmRight;
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
			list.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenStorage));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/netherite_space_suit.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity player) {
			if (!player.abilities.isCreativeMode && !player.isSpectator() && Methodes.spaceSuitCheckBoth(player) && Config.PlayerOxygenSystem) {
				IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

				OXYGEN_TIMER = OXYGEN_TIMER + 1;

				if (oxygenStorage.getOxygenStored() > 0 && OXYGEN_TIMER > 3 && !player.isPotionActive(ModInnet.OXYGEN_EFFECT.get())) {
					oxygenStorage.extractOxygen(1, false);
					OXYGEN_TIMER = 0;
				}

			}

		}
	};

	public static ArmorItem NETHERITE_SPACE_PANTS = new ArmorItem(SpaceSuitNetheriteArmorMaterial.ARMOR_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new SpaceSuitModel.SPACE_SUIT_P2().LegLeft;
			armorModel.bipedRightLeg = new SpaceSuitModel.SPACE_SUIT_P2().LegRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/netherite_space_suit_legs.png";
		}
	};

	public static ArmorItem NETHERITE_SPACE_BOOTS = new ArmorItem(SpaceSuitNetheriteArmorMaterial.ARMOR_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(BossToolsItemGroups.tab_normal).isImmuneToFire()) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new SpaceSuitModel.SPACE_SUIT_P1().FootLeft;
			armorModel.bipedRightLeg = new SpaceSuitModel.SPACE_SUIT_P1().FootRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/netherite_space_suit.png";
		}
	};
}