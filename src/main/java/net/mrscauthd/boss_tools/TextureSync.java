package net.mrscauthd.boss_tools;

import net.mrscauthd.boss_tools.entity.AlienEntity;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityEvent;
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
public class TextureSync extends BossToolsModElements.ModElement {
	public TextureSync(BossToolsModElements instance) {
		super(instance, 754);
		MinecraftForge.EVENT_BUS.register(this);
		NetworkLoader.registerMessages();
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		Entity entity = (Entity) dependencies.get("entity");
		// IWorld world = (IWorld) dependencies.get("world");
		if ((entity instanceof AlienEntity)) {
			if (!entity.world.isRemote) {
				NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
						new TextureSpinPacket(entity.getEntityId(), entity.getPersistentData().getDouble("texture")));
			}
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
			INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "texture_link"), () -> "1.0", s -> true, s -> true);
			INSTANCE.registerMessage(nextID(), TextureSpinPacket.class, TextureSpinPacket::encode, TextureSpinPacket::decode,
					TextureSpinPacket::handle);
		}
	}

	// NBT sync
	private static class TextureSpinPacket {
		private double texture;
		private int entityId;
		public TextureSpinPacket(int entityId, double texture) {
			this.texture = texture;
			this.entityId = entityId;
		}

		public static void encode(TextureSpinPacket msg, PacketBuffer buf) {
			buf.writeInt(msg.entityId);
			buf.writeDouble(msg.texture);
		}

		public static TextureSpinPacket decode(PacketBuffer buf) {
			return new TextureSpinPacket(buf.readInt(), buf.readDouble());
		}

		public static void handle(TextureSpinPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).getPersistentData().putDouble("texture", msg.texture);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
	@SubscribeEvent
	public void onEntity(EntityEvent event) {
		if (event != null && event.getEntity() != null) {
			Entity entity = event.getEntity();
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			World world = entity.world;
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
