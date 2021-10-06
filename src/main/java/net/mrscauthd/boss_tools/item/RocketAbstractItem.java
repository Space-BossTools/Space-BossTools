package net.mrscauthd.boss_tools.item;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;

public abstract class RocketAbstractItem extends Item implements IVehicleItem {

	public RocketAbstractItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 1F;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> list) {
		super.fillItemGroup(group, list);

		if (this.isInGroup(group)) {
			ItemStack full = new ItemStack(this);
			this.setItemStackFuel(full, this.getRocketFuelCapacity());
			list.add(full);
		}

	}

	@Override
	public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.addInformation(itemstack, world, list, flag);
		int fuel = this.getItemStackFuel(itemstack);
		int fuelCapacity = this.getRocketFuelCapacity();
		list.add(new StringTextComponent("\u00A79Fuel: " + fuel + " " + GaugeDataHelper.FLUID_UNIT + ", " + (int) (fuel / (fuelCapacity / 100.0D)) + "%"));
	}

	public void setItemStackFuel(ItemStack itemstack, int fuel) {
		CompoundNBT tag = itemstack.getOrCreateTag();
		tag.putInt(RocketAbstractEntity.KEY_FUEL, fuel);
	}

	public int getItemStackFuel(ItemStack itemstack) {
		CompoundNBT tag = itemstack.getTag();
		return tag != null ? tag.getInt(RocketAbstractEntity.KEY_FUEL) : 0;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();

		if (world.isAirBlock(pos.up(1)) && world.isAirBlock(pos.up(2)) && world.isAirBlock(pos.up(3))) {
			BlockState state = world.getBlockState(pos);

			if (state.hasProperty(RocketLaunchPadBlock.CustomBlock.STAGE) && state.get(RocketLaunchPadBlock.CustomBlock.STAGE)) {
				List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos));

				if (entities.size() < 1) {
					this.placeRocket(context.getPlayer(), world, pos, context.getHand());
					context.getItem().shrink(1);
					return ActionResultType.func_233537_a_(world.isRemote);
				}
			}
		}

		return super.onItemUse(context);
	}

	public void placeRocket(PlayerEntity entity, World world, BlockPos pos, Hand hand) {
		if (world instanceof ServerWorld) {
			ItemStack itemstack = entity.getHeldItem(hand);
			CompoundNBT tag = itemstack.getTag();

			EntityType<? extends RocketAbstractEntity> entityType = this.getEntityType();
			RocketAbstractEntity rocket = entityType.create((ServerWorld) world, tag, null, entity, pos, SpawnReason.SPAWN_EGG, true, true);

			if (tag != null) {
				rocket.readItemStackData(tag);
			}

			world.addEntity(rocket);
		}

		world.playSound(pos.getX(), pos.getY(), pos.getZ(), (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")), SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
	}

	public abstract EntityType<? extends RocketAbstractEntity> getEntityType();

	public abstract int getRocketFuelCapacity();
}
