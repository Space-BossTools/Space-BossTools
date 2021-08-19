
package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.item.IceshardItem;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.mrscauthd.boss_tools.ModInnet;

@BossToolsModElements.ModElement.Tag
public class SpaceBosstoolsSpawnEggsItemGroup extends BossToolsModElements.ModElement {
	public SpaceBosstoolsSpawnEggsItemGroup(BossToolsModElements instance) {
		super(instance, 675);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabspace_bosstools_spawn_eggs") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(ModInnet.ALIEN_SPAWN_EGG.get(), (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
