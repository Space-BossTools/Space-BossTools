package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.util.ResourceLocation;

import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class SyncEvents {
	@Mod.EventBusSubscriber
	public static class FuelGuiSyncEvent {
		public static SimpleChannel INSTANCE;
		private static int id = 1;

		public static int nextID() {
			return id++;
		}

		public static void registerMessages() {
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("space", "channelfuelgui"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), PacketFuelGuiSync.class, PacketFuelGuiSync::encode, PacketFuelGuiSync::decode, PacketFuelGuiSync::handle);
		}

		@SubscribeEvent
		public static void clickSync(PlayerInteractEvent.EntityInteract event) {
			event.getPlayer().getPersistentData().putDouble("fuelgui", event.getTarget().getPersistentData().getDouble("fuelgui"));
			event.getPlayer().getPersistentData().putInt("connectedfuelguiedentityid", event.getTarget().getEntityId());
		}

		@SubscribeEvent
		public static void playerTraceCheckSync(TickEvent.PlayerTickEvent event) {
			PlayerEntity playerEntity = event.player;
			Entity entity = playerEntity.world.getEntityByID(playerEntity.getPersistentData().getInt("connectedfuelguiedentityid"));
			if (!playerEntity.world.isRemote) {
				if (entity != null) {
					playerEntity.getPersistentData().putDouble("fuelgui", entity.getPersistentData().getDouble("fuelgui"));
				}
				INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
						new PacketFuelGuiSync(playerEntity.getEntityId(), playerEntity.getPersistentData().getDouble("fuelgui")));
			}
		}

		private static class PacketFuelGuiSync {
			private int entityId;
			private double fuelgui;

			public PacketFuelGuiSync(int entityId, double fuelgui) {
				this.entityId = entityId;
				this.fuelgui = fuelgui;
			}

			public static void encode(PacketFuelGuiSync msg, PacketBuffer buf) {
				buf.writeInt(msg.entityId);
				buf.writeDouble(msg.fuelgui);
			}

			public static PacketFuelGuiSync decode(PacketBuffer buf) {
				return new PacketFuelGuiSync(buf.readInt(), buf.readDouble());
			}

			public static void handle(PacketFuelGuiSync msg, Supplier<NetworkEvent.Context> ctx) {
				ctx.get().enqueueWork(() -> {
					Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
					if (entity != null) {
						entity.getPersistentData().putDouble("fuelgui", msg.fuelgui);
					}
				});
				ctx.get().setPacketHandled(true);
			}
		}
	}

	@Mod.EventBusSubscriber
	public static class FuelSyncEvent {
		public static SimpleChannel INSTANCE;
		private static int id = 1;

		public static int nextID() {
			return id++;
		}

		public static void registerMessages() {
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("space", "channel"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), PacketFuelSync.class, PacketFuelSync::encode, PacketFuelSync::decode, PacketFuelSync::handle);
		}

		@SubscribeEvent
		public static void clickSync(PlayerInteractEvent.EntityInteract event) {
			event.getPlayer().getPersistentData().putDouble("fuel", event.getTarget().getPersistentData().getDouble("fuel"));
			event.getPlayer().getPersistentData().putInt("connectedfueledentityid", event.getTarget().getEntityId());
		}

		@SubscribeEvent
		public static void playerTraceCheckSync(TickEvent.PlayerTickEvent event) {
			PlayerEntity playerEntity = event.player;
			Entity entity = playerEntity.world.getEntityByID(playerEntity.getPersistentData().getInt("connectedfueledentityid"));
			if (!playerEntity.world.isRemote) {
				if (entity != null) {
					playerEntity.getPersistentData().putDouble("fuel", entity.getPersistentData().getDouble("fuel"));
				}
				INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
						new PacketFuelSync(playerEntity.getEntityId(), playerEntity.getPersistentData().getDouble("fuel")));
			}
		}
		private static class PacketFuelSync {
			private int entityId;
			private double fuel;
			public PacketFuelSync(int entityId, double fuel) {
				this.entityId = entityId;
				this.fuel = fuel;
			}

			public static void encode(PacketFuelSync msg, PacketBuffer buf) {
				buf.writeInt(msg.entityId);
				buf.writeDouble(msg.fuel);
			}

			public static PacketFuelSync decode(PacketBuffer buf) {
				return new PacketFuelSync(buf.readInt(), buf.readDouble());
			}

			public static void handle(PacketFuelSync msg, Supplier<NetworkEvent.Context> ctx) {
				ctx.get().enqueueWork(() -> {
					Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
					if (entity != null) {
						entity.getPersistentData().putDouble("fuel", msg.fuel);
					}
				});
				ctx.get().setPacketHandled(true);
			}
		}
	}
	//OxygenBulletGeneratorSyncEvent
	@Mod.EventBusSubscriber
	public static class OxygenBulletGeneratorSyncEvent {
		// packages System
		public static class NetworkLoader {
			public static SimpleChannel INSTANCE;
			private static int id = 1;
			public static int nextID() {
				return id++;
			}
			public static void registerMessages() {
				INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "oxygenbulletgenerator_link"), () -> "1.0", s -> true,
						s -> true);
				INSTANCE.registerMessage(nextID(), OxygenBulletGeneratorSpinPacket.class, OxygenBulletGeneratorSpinPacket::encode, OxygenBulletGeneratorSpinPacket::decode, OxygenBulletGeneratorSpinPacket::handle);
			}
		}

		// NBT sync
		private static class OxygenBulletGeneratorSpinPacket {
			private double oxygen_bullet_generator;
			private int entityId;
			public OxygenBulletGeneratorSpinPacket(int entityId, double oxygen_bullet_generator) {
				this.oxygen_bullet_generator = oxygen_bullet_generator;
				this.entityId = entityId;
			}

			public static void encode(OxygenBulletGeneratorSpinPacket msg, PacketBuffer buf) {
				buf.writeInt(msg.entityId);
				buf.writeDouble(msg.oxygen_bullet_generator);
			}

			public static OxygenBulletGeneratorSpinPacket decode(PacketBuffer buf) {
				return new OxygenBulletGeneratorSpinPacket(buf.readInt(), buf.readDouble());
			}

			public static void handle(OxygenBulletGeneratorSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
				ctx.get().enqueueWork(() -> {
					Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).getPersistentData().putDouble("Oxygen_Bullet_Generator", msg.oxygen_bullet_generator);
					}
				});
				ctx.get().setPacketHandled(true);
			}
		}

		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				if (!entity.world.isRemote) {
					NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
							new OxygenBulletGeneratorSpinPacket(entity.getEntityId(), entity.getPersistentData().getDouble("Oxygen_Bullet_Generator")));
				}
			}
		}
	}
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
				INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "player_link"), () -> "1.0", s -> true, s -> true);
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
