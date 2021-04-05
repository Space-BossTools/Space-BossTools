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
public class OxygenBulletGeneratorSyncProcedure extends BossToolsModElements.ModElement {
	public OxygenBulletGeneratorSyncProcedure(BossToolsModElements instance) {
		super(instance, 754);
		MinecraftForge.EVENT_BUS.register(this);
		NetworkLoader.registerMessages();
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		Entity entity = (Entity) dependencies.get("entity");
		// IWorld world = (IWorld) dependencies.get("world");
		if (!entity.world.isRemote) {
			NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new OxygenBulletGeneratorSpinPacket(entity.getEntityId(), entity.getPersistentData().getDouble("Oxygen_Bullet_Generator")));
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
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "oxygenbulletgenerator_link"), () -> "1.0", s -> true,
					s -> true);
			INSTANCE.registerMessage(nextID(), OxygenBulletGeneratorSpinPacket.class, OxygenBulletGeneratorSpinPacket::encode,
					OxygenBulletGeneratorSpinPacket::decode, OxygenBulletGeneratorSpinPacket::handle);
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
