package net.mrscauthd.boss_tools.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import net.mrscauthd.boss_tools.entity.RocketAbstractEntity;

public abstract class RocketAbstractItem extends Item {

	public RocketAbstractItem(Properties properties) {
		super(properties);
	}

	public abstract EntityType<? extends RocketAbstractEntity> getRocketEntityType();

	public abstract int getTier(@Nullable RocketAbstractEntity rocket);

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);
		Hand hand = context.getHand();
		ItemStack itemStack = context.getItem();

		if (world instanceof ServerWorld) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			if (state.getBlock() instanceof RocketLaunchPad && state.get(RocketLaunchPad.STAGE)) {
				BlockPos pos1 = new BlockPos(x, y + 1, z);
				BlockPos pos2 = new BlockPos(x, y + 2, z);
				BlockPos pos3 = new BlockPos(x, y + 3, z);
				BlockPos pos4 = new BlockPos(x, y + 4, z);

				if (world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir() && world.getBlockState(pos3).isAir() && world.getBlockState(pos4).isAir()) {

					AxisAlignedBB scanAbove = new AxisAlignedBB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
					List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, scanAbove);

					if (entities.isEmpty()) {
						RocketAbstractEntity rocket = this.getRocketEntityType().create((ServerWorld) world, null, null, player, pos, SpawnReason.MOB_SUMMONED, true, true);
						this.applyItemNBT(itemStack, rocket);
						world.addEntity(rocket);

						player.setHeldItem(hand, ItemStack.EMPTY);
						playRocketPlaceSound(pos, world);
					}
				}
			}
		}

		return super.onItemUseFirst(stack, context);
	}

	public void fetchItemNBT(ItemStack itemStack, RocketAbstractEntity rocket) {

	}

	public void applyItemNBT(ItemStack itemStack, RocketAbstractEntity rocket) {

	}

	public static void playRocketPlaceSound(BlockPos pos, World world) {
		world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1, 1);
	}
}