package net.mrscauthd.boss_tools.gui.screens.planetselection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;

import java.util.function.Supplier;

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
		private ResourceLocation planet;

		public NetworkMessage() {

		}

		public NetworkMessage(ResourceLocation planet) {
			this.setPlanet(planet);
		}

		public NetworkMessage(PacketBuffer buffer) {
			this.setPlanet(buffer.readResourceLocation());
		}
		
		public ResourceLocation getPlanet() {
			return planet;
		}
		
		public void setPlanet(ResourceLocation planet) {
			this.planet = planet;
		}

		public static NetworkMessage decode(PacketBuffer buffer) {
			return new NetworkMessage(buffer);
		}

		public static void encode(NetworkMessage message, PacketBuffer buffer) {
			buffer.writeResourceLocation(message.getPlanet());
		}

		public static void handle(NetworkMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			//TODO Replace it with the Category

			Methodes.teleportButton(context.getSender(), message.getPlanet());
			context.getSender().setNoGravity(false);
			context.getSender().closeScreen();
			context.setPacketHandled(true);
		}
	}
}
