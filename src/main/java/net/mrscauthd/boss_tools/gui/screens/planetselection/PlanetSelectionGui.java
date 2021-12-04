package net.mrscauthd.boss_tools.gui.screens.planetselection;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;

public class PlanetSelectionGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			int tier = extraData.readInt();
			return new GuiContainer(id, inv, tier);
		}
	}

	public static class GuiContainer extends Container {

		private int tier;

		public GuiContainer(int id, PlayerInventory inv, int tier) {
			super(ModInnet.PLANET_SELECTION_GUI.get(), id);
			this.tier = tier;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !player.removed;
		}

		public int getTier() {
			return this.tier;
		}
	}

	public static class NetworkMessage {
		private ResourceLocation world;
		private boolean spaceStation;

		public NetworkMessage() {

		}

		public NetworkMessage(ResourceLocation world, boolean spaceStation) {
			this.setWorld(world);
		}

		public NetworkMessage(PacketBuffer buffer) {
			this.setWorld(buffer.readResourceLocation());
			this.setSpaceStation(buffer.readBoolean());
		}

		public ResourceLocation getWorld() {
			return world;
		}

		public void setWorld(ResourceLocation world) {
			this.world = world;
		}

		public boolean isSpaceStation() {
			return spaceStation;
		}

		public void setSpaceStation(boolean spaceStation) {
			this.spaceStation = spaceStation;
		}

		public static NetworkMessage decode(PacketBuffer buffer) {
			return new NetworkMessage(buffer);
		}

		public static void encode(NetworkMessage message, PacketBuffer buffer) {
			buffer.writeResourceLocation(message.getWorld());
			buffer.writeBoolean(message.isSpaceStation());
		}

		public static void handle(NetworkMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();

			defaultOptions(context.getSender());
			Methodes.teleportButton(context.getSender(), message.getWorld(), message.isSpaceStation());
			context.setPacketHandled(true);
		}

	}

	public static void defaultOptions(PlayerEntity player) {
		player.setNoGravity(false);
		player.closeScreen();
	}

	public static void deleteItems(PlayerEntity player) {
		player.inventory.func_234564_a_(p -> Items.DIAMOND == p.getItem(), 6, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.IRON_PLATE.get() == p.getItem(), 12, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.STEEL_INGOT.get() == p.getItem(), 16, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.DESH_PLATE.get() == p.getItem(), 4, player.inventory);
	}
}
