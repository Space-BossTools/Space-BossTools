package net.mrscauthd.boss_tools.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.entity.RocketAbstractFuelEntity;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;

public abstract class RocketAbstractFuelItem extends RocketAbstractItem {

	public static String fuelTag = BossToolsMod.ModId + ":fuel";
	public static String fuelFullTag = BossToolsMod.ModId + ":fuelFull";
	public static String bucketTag = BossToolsMod.ModId + ":buckets";

	public RocketAbstractFuelItem(Properties properties) {
		super(properties);
	}

	@Override
	public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.addInformation(itemstack, world, list, flag);
		CompoundNBT compound = itemstack.getOrCreateTag();
		int fuel = compound.getInt(fuelTag);
		int fuelFull = compound.getInt(fuelFullTag);
		list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, fuelFull))));
	}

	@Override
	public void fetchItemNBT(ItemStack itemStack, RocketAbstractEntity rocket) {
		super.fetchItemNBT(itemStack, rocket);

		if (rocket instanceof RocketAbstractFuelEntity) {
			RocketAbstractFuelEntity fuelRocket = (RocketAbstractFuelEntity) rocket;
			CompoundNBT compound = itemStack.getOrCreateTag();
			compound.putInt(fuelTag, fuelRocket.getFuel());
			compound.putInt(fuelFullTag, fuelRocket.getFuelOfFull());
			compound.putInt(bucketTag, fuelRocket.getBuckets());
		}
	}

	@Override
	public void applyItemNBT(ItemStack itemStack, RocketAbstractEntity rocket) {
		super.applyItemNBT(itemStack, rocket);

		if (rocket instanceof RocketAbstractFuelEntity) {
			RocketAbstractFuelEntity fuelRocket = (RocketAbstractFuelEntity) rocket;
			CompoundNBT compound = itemStack.getOrCreateTag();
			fuelRocket.setFuel(compound.getInt(fuelTag));
			fuelRocket.setBuckets(compound.getInt(bucketTag));
		}
	}

}
