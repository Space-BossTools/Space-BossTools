
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
import net.mrscauthd.boss_tools.entity.LanderEntity.CustomEntity;

@BossToolsModElements.ModElement.Tag
public class LandinggearGuiGui extends BossToolsModElements.ModElement {
	private static ContainerType<GuiContainerMod> containerType = null;

	public LandinggearGuiGui(BossToolsModElements instance) {
		super(instance, 181);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("landinggear_gui"));
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, LandinggearGuiGuiWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory<GuiContainerMod> {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			CustomEntity lander = (CustomEntity) inv.player.world.getEntityByID(extraData.readInt());
			return new GuiContainerMod(id, inv, lander);
		}
	}

	public static class GuiContainerMod extends Container {

		private CustomEntity entity;

		public GuiContainerMod(int id, PlayerInventory inv, CustomEntity entity) {
			super(containerType, id);
			this.entity = entity;

			IItemHandlerModifiable internal = entity.getInventory();
			this.addSlot(new SlotItemHandler(internal, 0, 78, 29) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});
			this.addSlot(new SlotItemHandler(internal, 1, 28, 29) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 84, 142, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return this.getEntity().isAlive();
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getEntity().getInventory(), this::mergeItemStack);
		}

		public CustomEntity getEntity() {
			return this.entity;
		}
	}
}
