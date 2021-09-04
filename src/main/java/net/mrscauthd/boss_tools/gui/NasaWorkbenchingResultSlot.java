package net.mrscauthd.boss_tools.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class NasaWorkbenchingResultSlot extends Slot {

	public NasaWorkbenchingResultSlot(IInventory inventory, int slotIndex, int xPos, int yPos) {
		super(inventory, slotIndex, xPos, yPos);
	}

	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}

	public ItemStack onTake(PlayerEntity player, ItemStack stack) {
		return stack;
	}
}