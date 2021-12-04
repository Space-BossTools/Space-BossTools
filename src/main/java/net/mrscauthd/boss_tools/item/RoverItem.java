package net.mrscauthd.boss_tools.item;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;

public class RoverItem extends Item {

	public static String fuelTag = BossToolsMod.ModId + ":fuel";

	public RoverItem(Properties properties) {
		super(properties);
	}

	@Override
	public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.addInformation(itemstack, world, list, flag);
		int fuel = itemstack.getOrCreateTag().getInt(fuelTag);
		list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE))));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();

		if (!(world instanceof ServerWorld)) {
			return super.onItemUse(context);
		}

		PlayerEntity player = context.getPlayer();
		BlockPos pos = context.getPos();
		ItemStack itemStack = context.getItem();

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		BlockPos pos1 = new BlockPos(x, y + 1, z);
		BlockPos pos2 = new BlockPos(x + 1, y + 1, z);
		BlockPos pos3 = new BlockPos(x - 1, y + 1, z);
		BlockPos pos4 = new BlockPos(x, y + 1, z + 1);
		BlockPos pos5 = new BlockPos(x, y + 1, z - 1);
		BlockPos pos6 = new BlockPos(x + 1, y + 1, z + 1);
		BlockPos pos7 = new BlockPos(x + 1, y + 1, z - 1);
		BlockPos pos8 = new BlockPos(x - 1, y + 1, z + 1);
		BlockPos pos9 = new BlockPos(x - 1, y + 1, z - 1);

		if (!world.getBlockState(pos1).isSolid() && !world.getBlockState(pos2).isSolid() && !world.getBlockState(pos3).isSolid() && !world.getBlockState(pos4).isSolid() && !world.getBlockState(pos5).isSolid() && !world.getBlockState(pos6).isSolid() && !world.getBlockState(pos7).isSolid() && !world.getBlockState(pos8).isSolid() && !world.getBlockState(pos9).isSolid()) {

			AxisAlignedBB scanAbove = new AxisAlignedBB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
			List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, scanAbove);

			if (entities.isEmpty()) {
				ITextComponent component = itemStack.hasDisplayName() ? itemStack.getDisplayName() : null;
				RoverEntity rover = ModInnet.ROVER.get().create((ServerWorld) world, null, component, player, pos, SpawnReason.MOB_SUMMONED, true, true);
				rover.rotationYawHead = player.rotationYaw;
				rover.renderYawOffset = player.rotationYaw;

				rover.setFuel(itemStack.getOrCreateTag().getInt(fuelTag));
				world.addEntity(rover);
				roverPlaceSound(pos, world);

				if (!player.isCreative()) {
					itemStack.shrink(1);
				}

				return ActionResultType.CONSUME;
			}
		}

		return super.onItemUse(context);
	}

	public static void roverPlaceSound(BlockPos pos, World world) {
		world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1, 1);
	}
}
