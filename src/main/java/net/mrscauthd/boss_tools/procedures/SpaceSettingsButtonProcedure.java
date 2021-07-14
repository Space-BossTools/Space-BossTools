package net.mrscauthd.boss_tools;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.GuiScreenEvent;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpaceSettingsButtonProcedure extends BossToolsModElements.ModElement {
    public SpaceSettingsButtonProcedure(BossToolsModElements elements, int sortid) {
        super(elements, sortid);
        elements.addNetworkMessage(GlobalTrigger.ButtonPressedMessage.class, GlobalTrigger.ButtonPressedMessage::buffer, GlobalTrigger.ButtonPressedMessage::new, GlobalTrigger.ButtonPressedMessage::handler);
    }

    @Mod.EventBusSubscriber
    private static class GlobalTrigger {
        @SubscribeEvent
        public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event) {
            final Screen gui = event.getGui();
            int x = 0, y = 0, z = 0;
            // System.out.println(gui);
            if (gui instanceof VideoSettingsScreen) {
                int maxY = 0;
                for (final Widget button : event.getWidgetList()) {
                    maxY = Math.max(button.y, maxY);
                }
                // event.addWidget(new IngameModListButton(gui, maxY + 24));
                event.addWidget(new Button((event.getGui().width / 2) + 130, maxY, 80, 20, new StringTextComponent("Space Settings"), e -> {
                    // System.out.println("test");
                    //BossToolsMod.PACKET_HANDLER.sendToServer(new SpaceSettingsButtonProcedure.GlobalTrigger.ButtonPressedMessage(0, x, y, z));
                    SpaceSettingsButtonProcedure.GlobalTrigger.handleButtonAction(Minecraft.getInstance().player, 0, x, y, z);
                }));
            }
        }
        public static class ButtonPressedMessage {
            int buttonID, x, y, z;
            public ButtonPressedMessage(PacketBuffer buffer) {
                this.buttonID = buffer.readInt();
                this.x = buffer.readInt();
                this.y = buffer.readInt();
                this.z = buffer.readInt();
            }

            public ButtonPressedMessage(int buttonID, int x, int y, int z) {
                this.buttonID = buttonID;
                this.x = x;
                this.y = y;
                this.z = z;
            }

            public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
                buffer.writeInt(message.buttonID);
                buffer.writeInt(message.x);
                buffer.writeInt(message.y);
                buffer.writeInt(message.z);
            }

            public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
                NetworkEvent.Context context = contextSupplier.get();
                context.enqueueWork(() -> {
                    PlayerEntity entity = context.getSender();
                    int buttonID = message.buttonID;
                    int x = message.x;
                    int y = message.y;
                    int z = message.z;
                    handleButtonAction(entity, buttonID, x, y, z);
                });
                context.setPacketHandled(true);
            }
        }
        public static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
        //    World world = entity.world;
            // security measure to prevent arbitrary chunk generation
      //      if (!world.isBlockLoaded(new BlockPos(x, y, z)))
      //          return;
            if (buttonID == 0) {
                {
                   // Map<String, Object> $_dependencies = new HashMap<>();
                   // $_dependencies.put("entity", entity);
                   // OpenTier2mainMenu2Procedure.executeProcedure($_dependencies);
                    System.out.println("test");
                }
            }
        }
    }
}