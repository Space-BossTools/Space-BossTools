
package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.block.FlagPurpleBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

@BossToolsModElements.ModElement.Tag
public class SpaceBosstoolsFlagsItemGroup extends BossToolsModElements.ModElement {
	public SpaceBosstoolsFlagsItemGroup(BossToolsModElements instance) {
		super(instance, 251);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabspace_bosstools_flags") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(FlagPurpleBlock.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
