package net.mrscauthd.boss_tools.gui.guihelper;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.util.ThreeConsumer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;

public class RocketPartGridPlacer {

	public static int place(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, ThreeConsumer<Integer, Integer, Rectangle2d> consumer) {
		for (int i = 0; i < part.getSlots(); i++) {
			Rectangle2d bounds = placer.place(i, left, top, mod);
			consumer.accept(i, slot, bounds);
			slot++;
		}
		return slot;
	}

	public static int placeContainer(int left, int top, int mod, IPlacer placer, RocketPart part, RocketPartsItemHandler itemHandler, Function<Slot, Slot> addSlot) {
		return place(0, left, top, mod, placer, part, (i, s, bounds) -> {
			IItemHandlerModifiable parent = itemHandler.getParent();
			int slot = itemHandler.getParentSlotIndex(part, i);
			addSlot.apply(new SlotItemHandler(parent, slot, bounds.getX(), bounds.getY()) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return true;
				}
			});
		});
	}

	public static int placeJEI(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, IRecipeLayout iRecipeLayout, WorkbenchingRecipe recipe) {
		IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
		List<Ingredient> ingredients = recipe.getParts().get(part);

		return place(slot, left, top, mod, placer, part, (i, s, bounds) -> {
			Ingredient ingredient = (ingredients != null && i < ingredients.size()) ? ingredients.get(i) : Ingredient.EMPTY;
			stacks.init(s, true, bounds.getX(), bounds.getY());
			stacks.set(s, Lists.newArrayList(ingredient.getMatchingStacks()));
		});
	}

	private RocketPartGridPlacer() {

	}

}
