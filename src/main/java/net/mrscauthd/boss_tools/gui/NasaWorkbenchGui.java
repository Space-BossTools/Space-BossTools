
package net.mrscauthd.boss_tools.gui;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
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
import net.mrscauthd.boss_tools.crafting.RocketPart;
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

			IItemHandlerModifiable internal = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(internal, WorkbenchBlock.SLOT_OUTPUT, 133, 74) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});
			

			RocketPartsItemHandler partsItemHandler = tileEntity.getPartsItemHandler();
			GridPlacer placer = new GridPlacer();
			RocketPartGridPlacer.placeContainer(40, 18, 1, placer::placeBottom, RocketPart.NOSE, partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 36, 2, placer::placeBottom, RocketPart.BODY, partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 90, 1, placer::placeRight, RocketPart.TANK, partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(13, 90, 1, placer::placeBottom, RocketPart.FIN_LEFT, partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(67, 90, 1, placer::placeBottom, RocketPart.FIN_RIGHT, partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(40, 108, 1, placer::placeBottom, RocketPart.ENGINE, partsItemHandler, this::addSlot);

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
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::mergeItemStack);
		}
	}
}
