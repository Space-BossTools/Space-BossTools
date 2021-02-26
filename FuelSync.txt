package net.mcreator.boss_tools.procedures;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import net.mcreator.boss_tools.BossToolsModElements;

import java.util.function.Supplier;
import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class FuelSyncEventProcedure extends BossToolsModElements.ModElement {
	public static SimpleChannel INSTANCE;
	private static int id = 1;
	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("space", "channel"), () -> "1.0", s -> true, s -> true);
		INSTANCE.registerMessage(nextID(), PacketFuelSync.class, PacketFuelSync::encode, PacketFuelSync::decode, PacketFuelSync::handle);
	}

	public FuelSyncEventProcedure(BossToolsModElements instance) {
		super(instance, 217);
		registerMessages();
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@SubscribeEvent
	public void clickSync(PlayerInteractEvent.EntityInteract event) {
		event.getPlayer().getPersistentData().putDouble("fuel", event.getTarget().getPersistentData().getDouble("fuel"));
		event.getPlayer().getPersistentData().putInt("connectedfueledentityid", event.getTarget().getEntityId());
	}

	@SubscribeEvent
	public void playerTraceCheckSync(TickEvent.PlayerTickEvent event) {
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
