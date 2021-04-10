package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class RocketOnEntityTickUpdateProcedure extends BossToolsModElements.ModElement {
	public RocketOnEntityTickUpdateProcedure(BossToolsModElements instance) {
		super(instance, 98);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketOnEntityTickUpdate!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure RocketOnEntityTickUpdate!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure RocketOnEntityTickUpdate!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure RocketOnEntityTickUpdate!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure RocketOnEntityTickUpdate!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
			if (((entity.getPersistentData().getDouble("fly")) >= 200)) {
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/particle minecraft:flame ~ ~-2.2 ~ .1 .1 .1 .001 100 force");
				}
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/particle minecraft:smoke ~ ~-3.2 ~ .1 .1 .1 .04 50 force");
				}
			}
		}
		if (((entity.getPosY()) >= 625)) {
			if (((entity.getPersistentData().getDouble("Powup_trigger")) == 1)) {
				if (!entity.world.isRemote())
					entity.remove();
			}
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager()
					.handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/team add SpaceBossToolsRo");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/team modify SpaceBossToolsRo collisionRule never");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/team join SpaceBossToolsRo @e[type=boss_tools:rocket,]");
		}
		if (((new Object() {
			public ItemStack getItemStack(int sltid, Entity entity) {
				AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
					_retval.set(capability.getStackInSlot(sltid).copy());
				});
				return _retval.get();
			}
		}.getItemStack((int) (0), entity)).getItem() == new ItemStack(FuelBlock.bucket, (int) (1)).getItem())) {
			if (((entity.getPersistentData().getDouble("Rocketfuel")) == 0)) {
				{
					final ItemStack _setstack = new ItemStack(Items.BUCKET, (int) (1));
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
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/particle minecraft:campfire_cosy_smoke ~ ~-0.5 ~ 0.1 .1 0.1 .013 6 force");
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
		if (((entity.getPersistentData().getDouble("Rotation")) >= 1)) {
			entity.rotationYaw = (float) (((entity.rotationYaw) - 1));
			entity.setRenderYawOffset(entity.rotationYaw);
			entity.prevRotationYaw = entity.rotationYaw;
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).prevRenderYawOffset = entity.rotationYaw;
				((LivingEntity) entity).rotationYawHead = entity.rotationYaw;
				((LivingEntity) entity).prevRotationYawHead = entity.rotationYaw;
			}
			entity.rotationPitch = (float) (0);
		}
		if (((entity.getPersistentData().getDouble("RotationB")) >= 1)) {
			entity.rotationYaw = (float) (((entity.rotationYaw) + 1));
			entity.setRenderYawOffset(entity.rotationYaw);
			entity.prevRotationYaw = entity.rotationYaw;
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).prevRenderYawOffset = entity.rotationYaw;
				((LivingEntity) entity).rotationYawHead = entity.rotationYaw;
				((LivingEntity) entity).prevRotationYawHead = entity.rotationYaw;
			}
			entity.rotationPitch = (float) (0);
		}
		if ((!(entity.isBeingRidden()))) {
			entity.getPersistentData().putDouble("Rotation", 0);
			entity.getPersistentData().putDouble("RotationB", 0);
		}
		if (((entity.getPersistentData().getDouble("Powup")) == 1)) {
			entity.getPersistentData().putDouble("AnimationRotation", ((entity.getPersistentData().getDouble("AnimationRotation")) + 1));
			if (((entity.getPersistentData().getDouble("AnimationRotation")) == 1)) {
				entity.getPersistentData().putDouble("Animation", ((entity.getPersistentData().getDouble("Animation")) + 0.006));
				entity.getPersistentData().putDouble("AnimationPitch", ((entity.getPersistentData().getDouble("AnimationPitch")) + 0.006));
			}
			if (((entity.getPersistentData().getDouble("AnimationRotation")) == 2)) {
				entity.getPersistentData().putDouble("Animation", 0);
				entity.getPersistentData().putDouble("AnimationPitch", 0);
				entity.getPersistentData().putDouble("AnimationRotation", 0);
			}
		}
		entity.getPersistentData().putDouble("fuelgui", ((entity.getPersistentData().getDouble("fuel")) / 4));
	}
}
