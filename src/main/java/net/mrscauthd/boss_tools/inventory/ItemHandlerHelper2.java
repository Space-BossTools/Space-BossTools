package net.mrscauthd.boss_tools.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerHelper2 {

	public static boolean isEmpty(IItemHandler handler) {
		for (int i = 0; i < handler.getSlots(); i++) {
			if (!handler.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public static NonNullList<ItemStack> getStacks(IItemHandler handler) {
		NonNullList<ItemStack> list = NonNullList.withSize(handler.getSlots(), ItemStack.EMPTY);

		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			list.set(i, stack);
		}

		return list;
	}

	private ItemHandlerHelper2() {

	}
}
