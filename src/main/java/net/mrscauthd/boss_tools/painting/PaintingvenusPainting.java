
package net.mrscauthd.boss_tools.painting;

import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.entity.item.PaintingType;

@BossToolsModElements.ModElement.Tag
public class PaintingvenusPainting extends BossToolsModElements.ModElement {
	public PaintingvenusPainting(BossToolsModElements instance) {
		super(instance, 562);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@SubscribeEvent
	public void registerPaintingType(RegistryEvent.Register<PaintingType> event) {
		event.getRegistry().register(new PaintingType(16, 16).setRegistryName("paintingvenus"));
	}
}
