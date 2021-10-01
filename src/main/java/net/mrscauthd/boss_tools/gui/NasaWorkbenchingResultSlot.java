package net.mrscauthd.boss_tools.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchingResultSlot extends Slot {

	private final CustomTileEntity tileEntity;

	public NasaWorkbenchingResultSlot(IInventory inventory, int slotIndex, int xPos, int yPos, CustomTileEntity tileEntity) {
		super(inventory, slotIndex, xPos, yPos);
		this.tileEntity = tileEntity;
	}

	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}
}