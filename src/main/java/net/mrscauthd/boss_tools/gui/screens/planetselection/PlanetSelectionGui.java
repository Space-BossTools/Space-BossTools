package net.mrscauthd.boss_tools.gui.screens.planetselection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.mrscauthd.boss_tools.ModInnet;

import java.util.function.Supplier;

public class PlanetSelectionGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {

		public GuiContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(ModInnet.PLANET_SELECTION_GUI.get(), id);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !player.removed;
		}
	}

	public static class NetworkMessage {
		private BlockPos blockPos = BlockPos.ZERO;
		private int integer = 0;

		public NetworkMessage() {

		}

		public NetworkMessage(BlockPos pos, int integer) {
			this.setBlockPos(pos);
			this.setInteger(integer);
		}

		public NetworkMessage(PacketBuffer buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setInteger(buffer.readInt());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public int getInteger() {
			return this.integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public static NetworkMessage decode(PacketBuffer buffer) {
			return new NetworkMessage(buffer);
		}

		public static void encode(NetworkMessage message, PacketBuffer buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeInt(message.getInteger());
		}

		public static void handle(NetworkMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			if (message.getInteger() == 0) {
				System.out.println("Pressed Overworld Button");
			}

			context.setPacketHandled(true);
		}
	}
}
