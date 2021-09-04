package net.mrscauthd.boss_tools.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemStackCacher {

	private final List<ItemStack> list;

	public ItemStackCacher() {
		this.list = new ArrayList<>();
	}

	public boolean test(ItemStack stack) {
		return this.test(Lists.newArrayList(stack));
	}

	public boolean test(Collection<ItemStack> stacks) {
		List<ItemStack> list = this.getStacks();
		List<ItemStack> tests = new ArrayList<>(stacks);

		int size = list.size();

		if (size != tests.size()) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			ItemStack left = list.get(i);
			ItemStack right = tests.get(i);

			if (left.isEmpty() && right.isEmpty()) {
				continue;
			} else if (!ItemHandlerHelper.canItemStacksStack(left, right)) {
				return false;
			}
		}

		return true;
	}

	public void set(ItemStack stack) {
		this.set(Lists.newArrayList(stack));
	}

	public void set(Collection<ItemStack> stacks) {
		this.list.clear();
		stacks.stream().map(ItemStack::copy).forEach(this.list::add);
	}

	public List<ItemStack> getStacks() {
		return Collections.unmodifiableList(this.list);
	}
}
