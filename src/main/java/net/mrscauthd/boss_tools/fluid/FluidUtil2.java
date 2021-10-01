package net.mrscauthd.boss_tools.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandlerModifiable;
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

	public static boolean isEquivalentTo(FluidStack left, Fluid right) {
		if (left.isEmpty())
			return right == Fluids.EMPTY;
		else if (right == Fluids.EMPTY)
			return false;

		return left.getFluid().isEquivalentTo(right);
	}

	public static boolean isEquivalentTo(FluidStack left, FluidStack right) {
		if (left.isEmpty())
			return right.isEmpty();
		else if (right.isEmpty())
			return false;

		return left.getFluid().isEquivalentTo(right.getFluid()) && FluidStack.areFluidStackTagsEqual(left, right);
	}

	@Nullable
	public static Fluid getFluid(FluidStack stack) {
		return Optional.ofNullable(stack).map(fs -> fs.getFluid()).orElse(null);
	}

	public static IFluidHandlerItem getItemStackFluidHandler(ItemStack itemStack) {
		return itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
	}

	/**
	 * test insert fluid to itemstack
	 * 
	 * @param itemStack
	 * @param fluid
	 * @return
	 */
	public static boolean canFill(ItemStack itemStack, Fluid fluid) {
		if (itemStack.isEmpty() || fluid == null) {
			return false;
		}

		if (itemStack.getItem() == Items.BUCKET) {
			return fluid.getFilledBucket() != Items.AIR;
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

		if (handlerInItemStack != null && handlerInItemStack.fill(new FluidStack(fluid, 1), FluidAction.SIMULATE) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * test drain fluid from itemstack
	 * 
	 * @param itemStack
	 * @param fluid
	 * @return
	 */
	public static boolean canDrain(ItemStack itemStack, Fluid fluid) {
		if (itemStack.isEmpty() || fluid == null) {
			return false;
		}

		if (itemStack.getItem() == fluid.getFilledBucket()) {
			return true;
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

		if (handlerInItemStack != null && !handlerInItemStack.drain(new FluidStack(fluid, 1), FluidAction.SIMULATE).isEmpty()) {
			return true;
		}

		return false;
	}

	public static int getMaxCapacity(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return 0;
		}

		if (itemStack.getItem() == Items.BUCKET) {
			return BUCKET_SIZE;
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

		if (handlerInItemStack != null) {
			return getMaxCapacity(handlerInItemStack);
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

	public static ItemStack makeFull(ItemStack itemStack, Fluid fluid) {
		if (itemStack.isEmpty()) {
			return itemStack;
		}

		if (itemStack.getItem() == Items.BUCKET && fluid.getFilledBucket() != null) {
			return new ItemStack(fluid.getFilledBucket());
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

		if (handlerInItemStack != null) {
			FluidStack fluidStack = new FluidStack(fluid, getMaxCapacity(handlerInItemStack));
			handlerInItemStack.fill(fluidStack, FluidAction.EXECUTE);
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
			IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

			if (handlerInItemStack != null) {
				for (int i = 0; i < handlerInItemStack.getTanks(); i++) {
					fluidStacks.add(handlerInItemStack.getFluidInTank(i));
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

	public static boolean fillSink(IItemHandlerModifiable itemHandler, int sinkItemSlot, IFluidHandler source, int transfer) {
		ItemStack sinkItemStack = itemHandler.getStackInSlot(sinkItemSlot);

		if (fillSinkBucket(itemHandler, sinkItemSlot, source, sinkItemStack)) {
			return true;
		} else if (!fillSinkCapability(source, sinkItemStack, transfer).isEmpty()) {
			return true;
		}

		return false;
	}

	public static FluidStack fillSinkCapability(IFluidHandler source, ItemStack sinkItemStack, int transfer) {
		IFluidHandlerItem sink = getItemStackFluidHandler(sinkItemStack);
		return tryTransfer(sink, source, transfer);
	}

	public static boolean fillSinkBucket(IItemHandlerModifiable itemHandler, int sinkItemSlot, IFluidHandler source, ItemStack itemStack) {
		if (itemStack.getItem() == Items.BUCKET) {
			int size = FluidUtil2.BUCKET_SIZE;
			FluidStack fluidStack = source.drain(size, FluidAction.SIMULATE);

			if (fluidStack.getAmount() == size) {
				source.drain(size, FluidAction.EXECUTE);
				itemHandler.setStackInSlot(sinkItemSlot, new ItemStack(fluidStack.getFluid().getFilledBucket()));
				return true;
			}
		}

		return false;
	}

	public static boolean drainSource(IItemHandlerModifiable itemHandler, int sourceItemSlot, IFluidHandler sink, int transfer) {
		ItemStack sourceItemStack = itemHandler.getStackInSlot(sourceItemSlot);

		if (drainSourceBucket(itemHandler, sourceItemSlot, sink, sourceItemStack)) {
			return true;
		} else if (!drainSourceCapability(sink, sourceItemStack, transfer).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean drainSourceBucket(IItemHandlerModifiable itemHandler, int itemSlot, IFluidHandler sink, ItemStack sourceItemStack) {
		Item sourceItem = sourceItemStack.getItem();
		Fluid sourceFluid = FluidUtil2.findBucketFluid(sourceItem);

		if (sourceFluid != null) {
			FluidStack fluidStack = new FluidStack(sourceFluid, FluidUtil2.BUCKET_SIZE);

			if (sink.fill(fluidStack, FluidAction.SIMULATE) == fluidStack.getAmount()) {
				sink.fill(fluidStack, FluidAction.EXECUTE);
				itemHandler.setStackInSlot(itemSlot, new ItemStack(Items.BUCKET));
				return true;
			}
		}

		return false;
	}

	public static FluidStack drainSourceCapability(IFluidHandler sink, ItemStack sourceItemStack, int transfer) {
		IFluidHandlerItem source = getItemStackFluidHandler(sourceItemStack);
		return tryTransfer(sink, source, transfer);
	}

	public static FluidStack tryTransfer(IFluidHandler sink, IFluidHandler source, int transfer) {
		if (sink != null && source != null && transfer > 0) {
			return FluidUtil.tryFluidTransfer(sink, source, transfer, true);
		} else {
			return FluidStack.EMPTY;
		}
	}

	private FluidUtil2() {

	}
}
