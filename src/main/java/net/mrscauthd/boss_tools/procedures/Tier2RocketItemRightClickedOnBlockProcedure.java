package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class Tier2RocketItemRightClickedOnBlockProcedure extends BossToolsModElements.ModElement {
	public Tier2RocketItemRightClickedOnBlockProcedure(BossToolsModElements instance) {
		super(instance, 320);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure Tier2RocketItemRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure Tier2RocketItemRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure Tier2RocketItemRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure Tier2RocketItemRightClickedOnBlock!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure Tier2RocketItemRightClickedOnBlock!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		BlockState state = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
		if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == RocketLaunchPadBlock.block.getDefaultState().getBlock())) {
			if (state.getBlock() instanceof RocketLaunchPadBlock.CustomBlock && state.get(RocketLaunchPadBlock.CustomBlock.STAGE) == true) {
				if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
						.getItem() == new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem())) {
					if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getOrCreateTag()
							.getDouble("Rocketfuel")) == 0)) {
						if (world instanceof ServerWorld) {
							Entity entityToSpawn = new RocketTier2Entity.CustomEntity(RocketTier2Entity.entity, (World) world);
							entityToSpawn.setLocationAndAngles((x + 0.5), (y + 1), (z + 0.5), (float) 0, (float) 0);
							entityToSpawn.setRenderYawOffset((float) 0);
							entityToSpawn.setMotion(0, 0, 0);
							entityToSpawn.rotationYaw = (float) (0);
							entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
							entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
							if (entityToSpawn instanceof MobEntity)
								((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
									world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null,
									(CompoundNBT) null);
							entityToSpawn.getPersistentData().putDouble("Rocketfuel", 0);
							world.addEntity(entityToSpawn);
						}
						if (world instanceof World && !world.isRemote()) {
							((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
									(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
									SoundCategory.NEUTRAL, (float) 1, (float) 1);
						} else {
							((World) world).playSound(x, y, z,
									(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
									SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
						}
						if (entity instanceof LivingEntity) {
							ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
							_setstack.setCount((int) 1);
							((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
							if (entity instanceof ServerPlayerEntity)
								((ServerPlayerEntity) entity).inventory.markDirty();
						}
					}
					if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getOrCreateTag()
							.getDouble("Rocketfuel")) == 1)) {
						if (world instanceof ServerWorld) {
							Entity entityToSpawn = new RocketTier2Entity.CustomEntity(RocketTier2Entity.entity, (World) world);
							entityToSpawn.setLocationAndAngles((x + 0.5), (y + 1), (z + 0.5), (float) 0, (float) 0);
							entityToSpawn.setRenderYawOffset((float) 0);
							entityToSpawn.setMotion(0, 0, 0);
							entityToSpawn.rotationYaw = (float) (0);
							entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
							entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
							if (entityToSpawn instanceof MobEntity)
								((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
							((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
									world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null,
									(CompoundNBT) null);
							entityToSpawn.getPersistentData().putDouble("Rocketfuel", 1);
							entityToSpawn.getPersistentData().putDouble("fuel",
									(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
											.getOrCreateTag().getDouble("fuel")));
							world.addEntity(entityToSpawn);
						}
						if (world instanceof World && !world.isRemote()) {
							((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
									(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
									SoundCategory.NEUTRAL, (float) 1, (float) 1);
						} else {
							((World) world).playSound(x, y, z,
									(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
									SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
						}
						if (entity instanceof LivingEntity) {
							ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
							_setstack.setCount((int) 1);
							((LivingEntity) entity).setHeldItem(Hand.MAIN_HAND, _setstack);
							if (entity instanceof ServerPlayerEntity)
								((ServerPlayerEntity) entity).inventory.markDirty();
						}
					}
				}
			}
		}
	}
}
