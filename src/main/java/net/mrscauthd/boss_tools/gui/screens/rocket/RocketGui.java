package net.mrscauthd.boss_tools.gui.screens.rocket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractFuelEntity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class RocketGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			Entity entity = inv.player.world.getEntityByID(extraData.readVarInt());
			return new GuiContainer(id, inv, (RocketAbstractFuelEntity) entity);
		}
	}

	public static class GuiContainer extends Container {
		public RocketAbstractFuelEntity rocket;

		public GuiContainer(int id, PlayerInventory inv, RocketAbstractFuelEntity rocket) {
			super(ModInnet.ROCKET_GUI.get(), id);

			this.rocket = rocket;
			IItemHandlerModifiable itemHandler = rocket.getItemHandler();

			this.addSlot(new SlotItemHandler(itemHandler, 0, 46, 22) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					if (Methodes.tagCheck(FluidUtil2.findBucketFluid(stack.getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG)) {
						return true;
					}
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.rocket.removed;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, 0, 1, this::mergeItemStack);
		}
	}
}
