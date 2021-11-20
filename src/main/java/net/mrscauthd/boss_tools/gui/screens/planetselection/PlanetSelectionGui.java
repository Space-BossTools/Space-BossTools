package net.mrscauthd.boss_tools.gui.screens.planetselection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;

public class PlanetSelectionGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {

		public GuiContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(ModInnet.PLANET_SELECTION_GUI.get(), id);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !player.removed;
		}
	}
}
