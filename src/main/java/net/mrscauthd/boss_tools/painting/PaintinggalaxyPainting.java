
package net.mrscauthd.boss_tools.painting;

import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.entity.item.PaintingType;

@BossToolsModElements.ModElement.Tag
public class PaintinggalaxyPainting extends BossToolsModElements.ModElement {
	public PaintinggalaxyPainting(BossToolsModElements instance) {
		super(instance, 417);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@SubscribeEvent
	public void registerPaintingType(RegistryEvent.Register<PaintingType> event) {
		event.getRegistry().register(new PaintingType(32, 32).setRegistryName("paintinggalaxy"));
	}
}
