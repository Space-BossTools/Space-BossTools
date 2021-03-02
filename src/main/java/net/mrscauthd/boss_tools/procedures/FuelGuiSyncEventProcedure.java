package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;

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

import java.util.function.Supplier;
import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class FuelGuiSyncEventProcedure extends BossToolsModElements.ModElement {
	public static SimpleChannel INSTANCE;
	private static int id = 1;
	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("space", "channelfuelgui"), () -> "1.0", s -> true, s -> true);
		INSTANCE.registerMessage(nextID(), PacketFuelGuiSync.class, PacketFuelGuiSync::encode, PacketFuelGuiSync::decode, PacketFuelGuiSync::handle);
	}

	public FuelGuiSyncEventProcedure(BossToolsModElements instance) {
		super(instance, 700);
		registerMessages();
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@SubscribeEvent
	public void clickSync(PlayerInteractEvent.EntityInteract event) {
		event.getPlayer().getPersistentData().putDouble("fuelgui", event.getTarget().getPersistentData().getDouble("fuelgui"));
		event.getPlayer().getPersistentData().putInt("connectedfuelguiedentityid", event.getTarget().getEntityId());
	}

	@SubscribeEvent
	public void playerTraceCheckSync(TickEvent.PlayerTickEvent event) {
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
