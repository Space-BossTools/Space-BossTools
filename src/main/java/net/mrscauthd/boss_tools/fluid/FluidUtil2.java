package net.mrscauthd.boss_tools.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
	public static boolean canFill(ItemStack itemStack, Fluid fluid) {
		if (itemStack.isEmpty()) {
			return false;
		}

		if (itemStack.getItem() == Items.BUCKET) {
			return fluid.getFilledBucket() != Items.AIR;
		}

		IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

		if (fluidHandlerItem != null && fluidHandlerItem.fill(new FluidStack(fluid, 1), FluidAction.SIMULATE) > 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(ItemStack itemStack) {
		for (FluidStack fluidStack : getFluidStacks(itemStack)) {
			if (!fluidStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public static int getMaxCapacity(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return 0;
		}

		if (itemStack.getItem() == Items.BUCKET) {
			return BUCKET_SIZE;
		}

		IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

		if (fluidHandlerItem != null) {
			return getMaxCapacity(fluidHandlerItem);
		}

		return 0;
	}

	public static int getMaxCapacity(IFluidHandler fluidHandler) {
		int capacity = 0;

		for (int i = 0; i < fluidHandler.getTanks(); i++) {
			capacity = Math.max(capacity, fluidHandler.getTankCapacity(i));
		}

		return capacity;
	}

	public static int getTotalCapacity(IFluidHandler fluidHandler) {
		int capacity = 0;

		for (int i = 0; i < fluidHandler.getTanks(); i++) {
			capacity += fluidHandler.getTankCapacity(i);
		}

		return capacity;
	}

	public static ItemStack makeFull(ItemStack itemStack, Fluid fluid) {
		if (!canFill(itemStack, fluid)) {
			return itemStack;
		}

		if (itemStack.getItem() == Items.BUCKET && fluid.getFilledBucket() != null) {
			return new ItemStack(fluid.getFilledBucket());
		}

		IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

		if (fluidHandlerItem != null) {
			fluidHandlerItem.fill(new FluidStack(fluid, getMaxCapacity(fluidHandlerItem)), FluidAction.EXECUTE);
		}

		return itemStack;
	}

	public static List<FluidStack> getFluidStacks(ItemStack itemStack) {
		List<FluidStack> fluidStacks = new ArrayList<>();

		if (itemStack.isEmpty()) {
			return fluidStacks;
		}

		Item item = itemStack.getItem();

		if (item == Items.BUCKET) {
			fluidStacks.add(new FluidStack(Fluids.EMPTY, 0));
		} else {
			IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

			if (fluidHandlerItem != null) {
				for (int i = 0; i < fluidHandlerItem.getTanks(); i++) {
					fluidStacks.add(fluidHandlerItem.getFluidInTank(i));
				}
			} else {
				Fluid fluid = findBucketFluid(item);
				if (fluid != null) {
					fluidStacks.add(new FluidStack(fluid, BUCKET_SIZE));
				}
			}
		}

		return fluidStacks;
	}

	private FluidUtil2() {

	}
}
