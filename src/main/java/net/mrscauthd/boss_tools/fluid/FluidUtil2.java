package net.mrscauthd.boss_tools.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidUtil2 {

	public static final int BUCKET_SIZE = 1000;
	private static final Map<Item, Fluid> fluidCacheds = new HashMap<>();

	public static Fluid findBucketFluid(Item item) {
		if (item == Items.AIR) {
			return null;
		}
		return fluidCacheds.computeIfAbsent(item, FluidUtil2::findBucketFluidInternal);
	}

	public static Fluid findBucketFluidInternal(Item item) {
		return ForgeRegistries.FLUIDS.getValues().stream().filter(f -> f.isSource(null) && f.getFilledBucket() == item).findFirst().orElse(null);
	}

	/**
	 * 
	 * @param itemStack
	 * @param fluidStack
	 * @return filled amount
	 */
	public static int canAccept(ItemStack itemStack, FluidStack fluidStack) {
		if (itemStack.isEmpty()) {
			return 0;
		}

		Item item = itemStack.getItem();

		if (item == Items.BUCKET) {
			return fluidStack.getAmount() >= BUCKET_SIZE ? BUCKET_SIZE : 0;
		} else {
			IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			return fluidHandlerItem.fill(fluidStack, FluidAction.SIMULATE);
		}
	}

	public static List<FluidStack> getFluidStack(ItemStack itemStack) {
		List<FluidStack> fluidStacks = new ArrayList<>();

		if (itemStack.isEmpty()) {
			return fluidStacks;
		}

		Item item = itemStack.getItem();
		Fluid fluid = findBucketFluid(item);

		if (fluid != null) {
			fluidStacks.add(new FluidStack(fluid, BUCKET_SIZE));
		} else {
			IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

			for (int i = 0; i < fluidHandlerItem.getTanks(); i++) {
				fluidStacks.add(fluidHandlerItem.getFluidInTank(i));
			}
		}

		return fluidStacks;
	}

	private FluidUtil2() {

	}
}
