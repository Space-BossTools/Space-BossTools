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
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock.CustomTileEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingTileEntity;

@BossToolsModElements.ModElement.Tag
public class OxygenLoaderGuiGui extends BossToolsModElements.ModElement {
	private static ContainerType<GuiContainerMod> containerType = null;

	public OxygenLoaderGuiGui(BossToolsModElements instance) {
		super(instance, 303);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("oxygen_loader_gui"));
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, OxygenLoaderGuiGuiWindow::new));
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

			IItemHandlerModifiable internal = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(internal, OxygenMakingTileEntity.SLOT_INPUT_SOURCE, 26, 22));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlock.SLOT_OUTPUT_SINK, 92, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenMakingTileEntity.SLOT_INPUT_SINK, 26, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlock.SLOT_OUTPUT_SOURCE, 92, 22));

			ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity player, int index) {
			return ContainerHelper.transferStackInSlot(this, player, index, this.getTileEntity(), this::mergeItemStack);
		}
	}
}
