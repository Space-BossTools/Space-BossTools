
package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.MoonoreironBlock;
import net.mrscauthd.boss_tools.machines.WorkbenchBlock;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

public class BossToolsItemGroups {
	//Normal
	public static ItemGroup tab_normal = new ItemGroup("tab_normal") {
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
	//Machines
	public static ItemGroup tab_machines = new ItemGroup("tab_machines") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(WorkbenchBlock.block, (int) (1));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean hasSearchBar() {
			return false;
		}
	};
	//Basics
	public static ItemGroup tab_basics = new ItemGroup("tab_basics") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.OXYGEN_GEAR.get(), (int) (1));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean hasSearchBar() {
			return false;
		}
	};
	//Materials
	public static ItemGroup tab_materials = new ItemGroup("tab_materials") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.IRON_PLATE.get(), (int) (1));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean hasSearchBar() {
			return false;
		}
	};
	//Flags
	public static ItemGroup tab_flags = new ItemGroup("tab_flags") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.FLAG_PURPLE_BLOCK.get(), (int) (1));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean hasSearchBar() {
			return false;
		}
	};
	//Blocks
	public static ItemGroup tab_blocks = new ItemGroup("tab_blocks") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(MoonoreironBlock.block, (int) (1));
		}

		@OnlyIn(Dist.CLIENT)
		public boolean hasSearchBar() {
			return false;
		}
	};
	//Spawn_eggs
	public static ItemGroup tab_spawn_eggs = new ItemGroup("tab_spawn_eggs") {
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
