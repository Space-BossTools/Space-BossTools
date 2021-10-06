
package net.mrscauthd.boss_tools.item;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.registries.ObjectHolder;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

@BossToolsModElements.ModElement.Tag
public class Tier1RocketItemItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:tier_1_rocket")
	public static final Item block = null;

	public Tier1RocketItemItem(BossToolsModElements instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends RocketAbstractItem {
		public ItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1).rarity(Rarity.RARE));
			setRegistryName("tier_1_rocket");
		}

		@Override
		public EntityType<? extends RocketAbstractEntity> getEntityType() {
			return ModInnet.TIER_1_ROCKET.get();
		}

		@Override
		public int getRocketFuelCapacity() {
			return RocketTier1Entity.FUEL_CAPACITY;
		}
	}
}
