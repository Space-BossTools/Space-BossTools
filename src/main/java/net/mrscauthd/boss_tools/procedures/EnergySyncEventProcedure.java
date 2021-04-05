package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class EnergySyncEventProcedure extends BossToolsModElements.ModElement {
	public EnergySyncEventProcedure(BossToolsModElements instance) {
		super(instance, 754);
		MinecraftForge.EVENT_BUS.register(this);
		NetworkLoader.registerMessages();
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		Entity entity = (Entity) dependencies.get("entity");
		// IWorld world = (IWorld) dependencies.get("world");
		if (!entity.world.isRemote) {
			NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new EnergySpinPacket(entity.getEntityId(), entity.getPersistentData().getDouble("Energy")));
		}
	}
	// packages System
	private static class NetworkLoader {
		public static SimpleChannel INSTANCE;
		private static int id = 1;
		public static int nextID() {
			return id++;
		}

		public static void registerMessages() {
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "energy_link"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), EnergySpinPacket.class, EnergySpinPacket::encode, EnergySpinPacket::decode, EnergySpinPacket::handle);
		}
	}

	// NBT sync
	private static class EnergySpinPacket {
		private double energy;
		private int entityId;
		public EnergySpinPacket(int entityId, double energy) {
			this.energy = energy;
			this.entityId = entityId;
		}

		public static void encode(EnergySpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.energy);
		}

		public static EnergySpinPacket decode(PacketBuffer buf) {
			return new EnergySpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(EnergySpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("Energy", msg.energy);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}
}
