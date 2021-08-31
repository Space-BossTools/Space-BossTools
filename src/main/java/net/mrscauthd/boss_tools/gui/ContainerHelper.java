package net.mrscauthd.boss_tools.gui;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHelper {

	public static ItemStack transferStackInSlot(PlayerEntity player, int index, Container container, IInventory tileEntity, IMergeItemStack mergeItemStack) {
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = container.inventorySlots;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemStack = slotStack.copy();

			if (index < tileEntity.getSizeInventory()) {
				if (!mergeItemStack.mergeItemStack(slotStack, tileEntity.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack.mergeItemStack(slotStack, 0, tileEntity.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemStack;
	}

}
