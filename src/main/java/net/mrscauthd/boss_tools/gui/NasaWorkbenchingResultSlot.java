package net.mrscauthd.boss_tools.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.machines.WorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchingResultSlot extends Slot {

	private final CustomTileEntity tileEntity;

	public NasaWorkbenchingResultSlot(IInventory inventory, int slotIndex, int xPos, int yPos, CustomTileEntity tileEntity) {
		super(inventory, slotIndex, xPos, yPos);
		this.tileEntity = tileEntity;
	}

	@Override
	protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {
		super.onCrafting(p_75210_1_, p_75210_2_);
		System.out.println("_____________" + p_75210_2_);
	}

	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}

	public ItemStack onTake(PlayerEntity player, ItemStack stack) {
		return super.onTake(player, stack);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}
}