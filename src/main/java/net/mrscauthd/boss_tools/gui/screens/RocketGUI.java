package net.mrscauthd.boss_tools.gui.screens;

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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.ContainerHelper;

@BossToolsModElements.ModElement.Tag
public class RocketGUI extends BossToolsModElements.ModElement {
	private static ContainerType<GuiContainerMod> containerType = null;

	public RocketGUI(BossToolsModElements instance) {
		super(instance, 142);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("rocket_tier_1_gui_fuel"));
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, RocketGUIWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory<GuiContainerMod> {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			int entityId = extraData.readInt();
			RocketAbstractEntity rocket = (RocketAbstractEntity) inv.player.world.getEntityByID(entityId);
			return new GuiContainerMod(id, inv, rocket);
		}
	}

	public static class GuiContainerMod extends Container {

		private RocketAbstractEntity entity;

		public GuiContainerMod(int id, PlayerInventory inv, RocketAbstractEntity entity) {
			super(containerType, id);
			this.entity = entity;

			ItemStackHandler internal = entity.getInventory();
			this.addSlot(new SlotItemHandler(internal, 0, 46, 22) {
				@Override
				public boolean isItemValid(ItemStack stack) {
//					return FluidUtil2.canDrain(stack, ModInnet.FUEL_STILL.get());
					return stack.getItem() == ModInnet.FUEL_BUCKET.get();
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

		public RocketAbstractEntity getEntity() {
			return this.entity;
		}

	}

}
