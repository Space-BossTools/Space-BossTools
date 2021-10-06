
package net.mrscauthd.boss_tools.item;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.registries.ObjectHolder;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

@BossToolsModElements.ModElement.Tag
public class Tier3RocketItemItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:tier_3_rocket")
	public static final Item block = null;

	public Tier3RocketItemItem(BossToolsModElements instance) {
		super(instance, 3);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends RocketAbstractItem {
		public ItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1).rarity(Rarity.RARE));
			setRegistryName("tier_3_rocket");
		}

		@Override
		public EntityType<? extends RocketAbstractEntity> getEntityType() {
			return ModInnet.TIER_3_ROCKET.get();
		}

		@Override
		public int getRocketFuelCapacity() {
			return RocketTier3Entity.FUEL_CAPACITY;
		}
	}
}
