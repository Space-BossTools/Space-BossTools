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
public class LoadingSyncEventProcedure extends BossToolsModElements.ModElement {
	public static SimpleChannel INSTANCE;
	private static int id = 1;
	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("space2", "channel2"), () -> "1.0", s -> true, s -> true);//space2 und channal2 musst du wen zu zb. ein neues machst space3 also +1!
		INSTANCE.registerMessage(nextID(), PacketLoadingSync.class, PacketLoadingSync::encode, PacketLoadingSync::decode, PacketLoadingSync::handle);
	}

	public LoadingSyncEventProcedure(BossToolsModElements instance) {
		super(instance, 217);
		registerMessages();
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@SubscribeEvent
	public void clickSync(PlayerInteractEvent.EntityInteract event) {
		event.getPlayer().getPersistentData().putDouble("loading", event.getTarget().getPersistentData().getDouble("loading"));
		event.getPlayer().getPersistentData().putInt("connectedloadingedentityid", event.getTarget().getEntityId());
	}

	@SubscribeEvent
	public void playerTraceCheckSync(TickEvent.PlayerTickEvent event) {
		PlayerEntity playerEntity = event.player;
		Entity entity = playerEntity.world.getEntityByID(playerEntity.getPersistentData().getInt("connectedloadingedentityid"));
		if (!playerEntity.world.isRemote) {
			if (entity != null) {
				playerEntity.getPersistentData().putDouble("loading", entity.getPersistentData().getDouble("loading"));
			}
			INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
					new PacketLoadingSync(playerEntity.getEntityId(), playerEntity.getPersistentData().getDouble("loading")));
		}
	}
	private static class PacketLoadingSync {
		private int entityId;
		private double loading;
		public PacketLoadingSync(int entityId, double loading) {
			this.entityId = entityId;
			this.loading = loading;
		}

		public static void encode(PacketLoadingSync msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.loading);
		}

		public static PacketLoadingSync decode(PacketBuffer buf) {
			return new PacketLoadingSync(buf.readInt(), buf.readDouble());
		}

		public static void handle(PacketLoadingSync msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity != null) {
					entity.getPersistentData().putDouble("loading", msg.loading);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}