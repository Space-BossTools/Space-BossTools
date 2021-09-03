
package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
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
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.guihelper.GridPlacer;
import net.mrscauthd.boss_tools.gui.guihelper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import net.mrscauthd.boss_tools.machines.WorkbenchBlock;
import net.mrscauthd.boss_tools.machines.WorkbenchBlock.CustomTileEntity;

@BossToolsModElements.ModElement.Tag
public class NasaWorkbenchGui extends BossToolsModElements.ModElement {
	private static ContainerType<GuiContainerMod> containerType = null;

	public NasaWorkbenchGui(BossToolsModElements instance) {
		super(instance, 165);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("nasa_workbench"));
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, NasaWorkbenchGuiWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory<GuiContainerMod> {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.world.getTileEntity(extraData.readBlockPos());
			return new GuiContainerMod(id, inv, tileEntity);
		}
	}

	public static class GuiContainerMod extends Container {
		private CustomTileEntity tileEntity;

		public GuiContainerMod(int id, PlayerInventory inv, CustomTileEntity tileEntity) {
			super(containerType, id);
			this.tileEntity = tileEntity;

			this.addSlot(new NasaWorkbenchingResultSlot(tileEntity, tileEntity.getOutputSlot(), 133, 74));

			RocketPartsItemHandler partsItemHandler = tileEntity.getPartsItemHandler();
			GridPlacer placer = new GridPlacer();
			RocketPartGridPlacer.placeContainer(40, 18, 1, placer::placeBottom, ModInnet.ROCKET_PART_NOSE.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 36, 2, placer::placeBottom, ModInnet.ROCKET_PART_BODY.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 90, 1, placer::placeRight, ModInnet.ROCKET_PART_TANK.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(13, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_LEFT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(67, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_RIGHT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(40, 108, 1, placer::placeBottom, ModInnet.ROCKET_PART_ENGINE.get(), partsItemHandler, this::addSlot);

			ContainerHelper.addInventorySlots(this, inv, 8, 142, 200, this::addSlot);
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			Slot slot = this.getSlot(index);
			ItemStack prev = slot.getStack().copy();
			this.getTileEntity().onTransferStackInSlot(index, prev);

			if (index == this.getTileEntity().getOutputSlot()) {
				ItemStack itemStack = ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::mergeItemStack);
				ItemStack next = slot.getStack().copy();

				if (!prev.isEmpty()) {
					int nextSize = next.isEmpty() ? 0 : next.getCount();

					if (nextSize > 0) {
						playerIn.dropItem(next, false);
						slot.putStack(ItemStack.EMPTY);
					}
				}

				return itemStack;
			} else {
				return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::mergeItemStack);
			}
		}
	}
}
