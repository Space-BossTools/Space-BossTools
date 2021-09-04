package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock.CustomTileEntity;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

@BossToolsModElements.ModElement.Tag
public class BlastFurnaceGUIGui extends BossToolsModElements.ModElement {
	private static ContainerType<GuiContainerMod> containerType = null;

	public BlastFurnaceGUIGui(BossToolsModElements instance) {
		super(instance, 161);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("blast_furnace_gui"));
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, BlastFurnaceGUIGuiWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory<GuiContainerMod> {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			BlockPos pos = extraData.readBlockPos();
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.world.getTileEntity(pos);
			return new GuiContainerMod(id, inv, tileEntity);
		}
	}

	public static class GuiContainerMod extends Container {
		private CustomTileEntity tileEntity;

		public GuiContainerMod(int id, PlayerInventory inv, CustomTileEntity tileEntity) {
			super(containerType, id);
			this.tileEntity = tileEntity;

			IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackTileEntity.SLOT_INGREDIENT, 53, 19) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return tileEntity.canInsertItem(this.getSlotIndex(), stack, null);
				}
			});
			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackTileEntity.SLOT_OUTPUT, 104, 38) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return tileEntity.canInsertItem(this.getSlotIndex(), stack, null);
				}
			});
			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackTileEntity.SLOT_FUEL, 53, 56) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return tileEntity.canInsertItem(this.getSlotIndex(), stack, null);
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 87, 145, this::addSlot);
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::mergeItemStack);
		}
	}
}
