package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

public class RocketOnEntityTickTier3Procedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketOnEntityTickTier3!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure RocketOnEntityTickTier3!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure RocketOnEntityTickTier3!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure RocketOnEntityTickTier3!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure RocketOnEntityTickTier3!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		ItemStack itemfuel = ItemStack.EMPTY;
		if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
			if (((entity.getPersistentData().getDouble("fly")) >= 200)) {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, ParticleTypes.FLAME, true, entity.getPosX(), entity.getPosY() - 2.2, entity.getPosZ(),
								100, 0.1, 0.1, 0.1, (double) 0.001);
						((ServerWorld) world).spawnParticle(p, ParticleTypes.SMOKE, true, entity.getPosX(), entity.getPosY() - 3.2, entity.getPosZ(),
								50, 0.1, 0.1, 0.1, (double) 0.04);
					}
				}
			}
		}
		if (((entity.getPosY()) >= 625)) {
			if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
				if (!entity.world.isRemote())
					entity.remove();
			}
		}
		if (((new Object() {
			public ItemStack getItemStack(int sltid, Entity entity) {
				AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
					_retval.set(capability.getStackInSlot(sltid).copy());
				});
				return _retval.get();
			}
		}.getItemStack((int) (0), entity)).getItem() == new ItemStack(ModInnet.FUEL_BARREL.get(), (int) (1)).getItem())) {
			if (((entity.getPersistentData().getDouble("Rocketfuel")) == 0)) {
				{
					final ItemStack _setstack = new ItemStack(ModInnet.BARREL.get(), (int) (1));
					final int _sltid = (int) (0);
					_setstack.setCount((int) 1);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						if (capability instanceof IItemHandlerModifiable) {
							((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
						}
					});
				}
				entity.getPersistentData().putDouble("Rocketfuel", 1);
			}
		}
		if (((entity.getPersistentData().getDouble("Rocketfuel")) == 1)) {
			if (((entity.getPersistentData().getDouble("fuel")) <= 399)) {
				entity.getPersistentData().putDouble("fuel", ((entity.getPersistentData().getDouble("fuel")) + 1));
			}
			if (((entity.getPersistentData().getDouble("loading")) <= 399)) {
				if (((entity.getPersistentData().getDouble("loadingArrow")) <= 399)) {
					entity.getPersistentData().putDouble("loading", ((entity.getPersistentData().getDouble("loading")) + 1));
				}
			}
			if (((entity.getPersistentData().getDouble("loading")) == 400)) {
				entity.getPersistentData().putDouble("loadingArrow", 500);
				entity.getPersistentData().putDouble("loading", 0);
			}
		}
		if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
			entity.getPersistentData().putDouble("fly", ((entity.getPersistentData().getDouble("fly")) + 1));
		}
		if (((entity.getPersistentData().getDouble("fly")) >= 200)) {
			if (((entity.getMotion().getY()) <= 0.5)) {
				entity.setMotion((entity.getMotion().getX()), ((entity.getMotion().getY()) + 0.1), (entity.getMotion().getZ()));
			}
			if (((entity.getMotion().getY()) >= 0.5)) {
				entity.setMotion((entity.getMotion().getX()), 0.63, (entity.getMotion().getZ()));
			}
		}
		if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
			if (((entity.getPersistentData().getDouble("fly")) <= 200)) {
				if (world instanceof ServerWorld) {
					for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
						((ServerWorld) world).spawnParticle(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, entity.getPosX(), entity.getPosY() - 0.5,
								entity.getPosZ(), 6, 0.1, 0.1, 0.1, (double) 0.013);
					}
				}
			}
		}
		if ((entity.isBeingRidden())) {
			if (((entity.getPersistentData().getDouble("fly")) == 1)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"10\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 20)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"9\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 40)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"8\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 60)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"7\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 80)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"6\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 100)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"5\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 120)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"4\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 140)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"3\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 160)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"2\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
			if (((entity.getPersistentData().getDouble("fly")) == 180)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 5 10 10");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p title {\"text\":\"1\",\"color\":\"red\",\"bold\":\"false\"}");
				}
			}
		}
		entity.getPersistentData().putDouble("fuelgui", ((entity.getPersistentData().getDouble("fuel")) / 4));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).setAir((int) 300);
		if ((entity.isOnGround())) {
			if ((!world.isAirBlock(new BlockPos((int) Math.floor(x), y - 0.01, (int) Math.floor(z))))) {
				BlockState state = world.getBlockState(new BlockPos(Math.floor(x), y - 0.1, Math.floor(z)));
				if (((state.getBlock() instanceof RocketLaunchPadBlock.CustomBlock && state.get(RocketLaunchPadBlock.CustomBlock.STAGE) == false)
						|| ((world.getBlockState(new BlockPos((int) (Math.floor(x)), (int) (Math.floor(y)), (int) (Math.floor(z)))))
								.getBlock() != RocketLaunchPadBlock.block.getDefaultState().getBlock()))) {
					if (((new Object() {
						public ItemStack getItemStack(int sltid, Entity entity) {
							AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).copy());
							});
							return _retval.get();
						}
					}.getItemStack((int) (0), entity)).getItem() == new ItemStack(ModInnet.FUEL_BARREL.get(), (int) (1)).getItem())) {
						{
							final ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
							final int _sltid = (int) (0);
							_setstack.setCount((int) 1);
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
						if (world instanceof World && !world.isRemote()) {
							ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(ModInnet.FUEL_BARREL.get(), (int) (1)));
							entityToSpawn.setPickupDelay((int) 10);
							world.addEntity(entityToSpawn);
						}
					}
					if (((new Object() {
						public ItemStack getItemStack(int sltid, Entity entity) {
							AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).copy());
							});
							return _retval.get();
						}
					}.getItemStack((int) (0), entity)).getItem() == new ItemStack(ModInnet.BARREL.get(), (int) (1)).getItem())) {
						{
							final ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
							final int _sltid = (int) (0);
							_setstack.setCount((int) 1);
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
						if (world instanceof World && !world.isRemote()) {
							ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(ModInnet.BARREL.get(), (int) (1)));
							entityToSpawn.setPickupDelay((int) 10);
							world.addEntity(entityToSpawn);
						}
					}
					if (((entity.getPersistentData().getDouble("Rocketfuel")) == 0)) {
						if (world instanceof World && !world.isRemote()) {
							ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(Tier3RocketItemItem.block, (int) (1)));
							entityToSpawn.setPickupDelay((int) 10);
							world.addEntity(entityToSpawn);
						}
					}
					if (((entity.getPersistentData().getDouble("Rocketfuel")) == 1)) {
						itemfuel = new ItemStack(Tier3RocketItemItem.block, (int) (1));
						(itemfuel).getOrCreateTag().putDouble("Rocketfuel", 1);
						(itemfuel).getOrCreateTag().putDouble("fuel", (entity.getPersistentData().getDouble("fuel")));
						(itemfuel).getOrCreateTag().putDouble("fuelgui", ((entity.getPersistentData().getDouble("fuel")) / 4));
						if (world instanceof World && !world.isRemote()) {
							ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, (itemfuel));
							entityToSpawn.setPickupDelay((int) 10);
							world.addEntity(entityToSpawn);
						}
					}
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/stopsound @p neutral boss_tools:rocketfly");
					}
					if (!entity.world.isRemote())
						entity.remove();
				}
			}
		}
	}
}
