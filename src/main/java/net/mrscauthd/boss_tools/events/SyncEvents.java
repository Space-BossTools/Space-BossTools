package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.util.ResourceLocation;

import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class SyncEvents { //TODO In Future get it Removed
	//Player Movement Sync Event
	@Mod.EventBusSubscriber
	public static class PlayerMovementSyncEvent {
		// packages System
		public static class NetworkLoader {
			public static SimpleChannel INSTANCE;
			private static int id = 1;
			public static int nextID() {
				return id++;
			}

			public static void registerMessages() {
				INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "player_link2"), () -> "1.0", s -> true, s -> true);
				INSTANCE.registerMessage(nextID(), PlayerMovementSpinPacket.class, PlayerMovementSpinPacket::encode, PlayerMovementSpinPacket::decode, PlayerMovementSpinPacket::handle);
			}
		}

		// NBT sync
		private static class PlayerMovementSpinPacket {
			private double player_movement;
			private int entityId;
			public PlayerMovementSpinPacket(int entityId, double player_movement) {
				this.player_movement = player_movement;
				this.entityId = entityId;
			}

			public static void encode(PlayerMovementSpinPacket msg, PacketBuffer buf) {
				buf.writeInt(msg.entityId);
				buf.writeDouble(msg.player_movement);
			}

			public static PlayerMovementSpinPacket decode(PacketBuffer buf) {
				return new PlayerMovementSpinPacket(buf.readInt(), buf.readDouble());
			}

			public static void handle(PlayerMovementSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
				ctx.get().enqueueWork(() -> {
					Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).getPersistentData().putDouble("Player_movement", msg.player_movement);
					}
				});
				ctx.get().setPacketHandled(true);
			}
		}
		@SubscribeEvent
		public void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				if (!entity.world.isRemote) {
					NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
							new PlayerMovementSpinPacket(entity.getEntityId(), entity.getPersistentData().getDouble("Player_movement")));
				}
			}
		}
	}
}
