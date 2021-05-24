
package net.mrscauthd.boss_tools.keybind;

import org.lwjgl.glfw.GLFW;

import net.mrscauthd.boss_tools.procedures.LanderSpaceProcedure;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class LanderKeySpaceKeyBinding extends BossToolsModElements.ModElement {
	@OnlyIn(Dist.CLIENT)
	private KeyBinding keys;
	public LanderKeySpaceKeyBinding(BossToolsModElements instance) {
		super(instance, 387);
		elements.addNetworkMessage(KeyBindingPressedMessage.class, KeyBindingPressedMessage::buffer, KeyBindingPressedMessage::new,
				KeyBindingPressedMessage::handler);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		// keys = new KeyBinding("key.boss_tools.lander_key_space", GLFW.GLFW_KEY_SPACE,
		// "key.categories.spacebosstools");
		// ClientRegistry.registerKeyBinding(keys);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_SPACE))) {
				if (Minecraft.getInstance().currentScreen == null) {
					// if
					// ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(),
					// GLFW.GLFW_PRESS))) {
					BossToolsMod.PACKET_HANDLER.sendToServer(new KeyBindingPressedMessage(0, 0));
					pressAction(Minecraft.getInstance().player, 0, 0);
				}
			}
		}
	}
	// }
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
		if (type == 0) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				LanderSpaceProcedure.executeProcedure($_dependencies);
			}
		}
	}
}
