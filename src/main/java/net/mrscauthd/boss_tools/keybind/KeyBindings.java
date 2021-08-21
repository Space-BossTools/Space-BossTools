package net.mrscauthd.boss_tools.keybind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.events.ClientEventBusSubscriber;
import net.mrscauthd.boss_tools.procedures.PowUpOnKeyReleasedProcedure;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "boss_tools")
public class KeyBindings {
	public static SimpleChannel INSTANCE;
	private static int id = 1;

	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("boss_tools", "key_bindings"), () -> "1.0", s -> true, s -> true);
		INSTANCE.registerMessage(nextID(), KeyBindingPressedMessage.class, KeyBindingPressedMessage::buffer, KeyBindingPressedMessage::new, KeyBindingPressedMessage::handler);
	}

	//Client Tick Event
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput1(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_SPACE)) {
				if (Minecraft.getInstance().currentScreen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(0, 0));
					pressAction(Minecraft.getInstance().player, 0, 0);
				}
			}
		}
	}

	//Key Press Event
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onKeyInput2(InputEvent.KeyInputEvent event) {
		if (Minecraft.getInstance().currentScreen == null) {
			if (event.getKey() == ClientEventBusSubscriber.key1.getKey().getKeyCode()) {
				if (event.getAction() == GLFW.GLFW_PRESS) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(1, 0));
					pressAction(Minecraft.getInstance().player, 1, 0);
				}
			}
		}
	}

	public static class KeyBindingPressedMessage {
		int type, pressedms;

		public KeyBindingPressedMessage(int type, int pressedms) {
			this.type = type;
			this.pressedms = pressedms;
		}

		public KeyBindingPressedMessage(PacketBuffer buffer) {
			this.type = buffer.readInt();
			this.pressedms = buffer.readInt();
		}

		public static void buffer(KeyBindingPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.type);
			buffer.writeInt(message.pressedms);
		}

		public static void handler(KeyBindingPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				pressAction(context.getSender(), message.type, message.pressedms);
			});
			context.setPacketHandled(true);
		}
	}

	private static void pressAction(PlayerEntity entity, int type, int pressedms) {
		World world = entity.world;
		double x = entity.getPosX();
		double y = entity.getPosY();
		double z = entity.getPosZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
		//Type 0
		if (type == 0) {
			if (entity.getRidingEntity() instanceof LandingGearEntity.CustomEntity) {
				if (entity.getRidingEntity().isOnGround() == (false)
						&& entity.getRidingEntity().areEyesInFluid(FluidTags.WATER) == (false)) {
					if (entity.getRidingEntity().getMotion().getY() <= -0.05) {
						entity.getRidingEntity().setMotion(entity.getRidingEntity().getMotion().getX(), entity.getRidingEntity().getMotion().getY() * 0.85, entity.getRidingEntity().getMotion().getZ());
					}
					entity.getRidingEntity().getPersistentData().putDouble("Lander1", 1);
					entity.getRidingEntity().getPersistentData().putDouble("Lander2", 1);
				}
			}
			if (entity.getRidingEntity() instanceof LandingGearEntity.CustomEntity) {
				(entity.getRidingEntity()).fallDistance = (float) ((entity.getRidingEntity().getMotion().getY() * (-1)) * 4.5);
			}
		}
		//Type 1
		if (type == 1) {
			Map<String, Object> $_dependencies = new HashMap<>();
			$_dependencies.put("entity", entity);
			$_dependencies.put("x", x);
			$_dependencies.put("y", y);
			$_dependencies.put("z", z);
			$_dependencies.put("world", world);
			PowUpOnKeyReleasedProcedure.executeProcedure($_dependencies);
		}
	}
}