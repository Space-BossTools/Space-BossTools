
package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.item.OxygenGearItem;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

@BossToolsModElements.ModElement.Tag
public class SpaceBosstoolsBasicsItemGroup extends BossToolsModElements.ModElement {
	public SpaceBosstoolsBasicsItemGroup(BossToolsModElements instance) {
		super(instance, 251);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabspace_bosstools_basics") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(OxygenGearItem.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
