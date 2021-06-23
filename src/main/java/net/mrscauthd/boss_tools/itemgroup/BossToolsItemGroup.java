
package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

@BossToolsModElements.ModElement.Tag
public class BossToolsItemGroup extends BossToolsModElements.ModElement {
	public BossToolsItemGroup(BossToolsModElements instance) {
		super(instance, 129);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabboss_tools") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(Tier1RocketItemItem.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
