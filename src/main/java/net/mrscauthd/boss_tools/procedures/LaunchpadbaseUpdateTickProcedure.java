package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.block.RockedBaseBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class LaunchpadbaseUpdateTickProcedure extends BossToolsModElements.ModElement {
	public LaunchpadbaseUpdateTickProcedure(BossToolsModElements instance) {
		super(instance, 390);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure LaunchpadbaseUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure LaunchpadbaseUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure LaunchpadbaseUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure LaunchpadbaseUpdateTick!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((!((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) (z + 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) (z - 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) (z + 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		} else if ((!((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) (z - 1)))).getBlock() == RockedBaseBlock.block.getDefaultState()
				.getBlock()))) {
			world.setBlockState(new BlockPos((int) x, (int) y, (int) z), RockedBaseBlock.block.getDefaultState(), 3);
		}
	}
}
