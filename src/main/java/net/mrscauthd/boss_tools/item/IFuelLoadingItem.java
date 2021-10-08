package net.mrscauthd.boss_tools.item;

import net.minecraft.item.ItemStack;

public interface IFuelLoadingItem {

	public void setFuelAmount(ItemStack itemStack, int fuel);
	
	public int getFuelAmount(ItemStack itemStack);
	
	public int getFuelCapacity(ItemStack itemStack);
	
	public int getFuelTransfer(ItemStack  itemStack);
}
