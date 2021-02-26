package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.FuelBuckedItem;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import java.util.Map;

@BossToolsModElements.ModElement.Tag
public class RocketTier1overworldOrbitProcedure extends BossToolsModElements.ModElement {
	public RocketTier1overworldOrbitProcedure(BossToolsModElements instance) {
		super(instance, 198);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure RocketTier1overworldOrbit!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure RocketTier1overworldOrbit!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure RocketTier1overworldOrbit!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure RocketTier1overworldOrbit!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure RocketTier1overworldOrbit!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/effect clear @p boss_tools:rocketpotion_7");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/effect clear @p boss_tools:player_movement");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager()
					.handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/title @p times 20 60 5");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/title @p title {\"text\":\"Press\",\"color\":\"red\",\"bold\":\"false\"}");
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/title @p subtitle {\"text\":\"Space\",\"color\":\"blue\"}");
		}
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cWARNING! \u00A77Press \u00A7cSPACE\u00A77."), (false));
		}
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cWARNING! \u00A77Press \u00A7cSPACE\u00A77."), (true));
		}
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(
					new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"/execute in boss_tools:umlaufbahnerde run teleport @p ~ 500 ~");
		}
		if (((world.isRemote()) == (false))) {
			Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
			entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
			entity.world.addEntity(entity2);
			entity.startRiding(entity2);
			if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
				{
					final ItemStack _setstack = new ItemStack(Tier1RocketItemItem.block, (int) (1));
					final int _sltid = (int) (0);
					_setstack.setCount((int) 1);
					(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						if (capability instanceof IItemHandlerModifiable) {
							((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
						}
					});
				}
			}
			if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
				if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
					{
						final ItemStack _setstack = new ItemStack(Items.BUCKET, (int) (1));
						final int _sltid = (int) (1);
						_setstack.setCount((int) 1);
						(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
			}
			if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
				if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
					{
						final ItemStack _setstack = new ItemStack(FuelBuckedItem.block, (int) (1));
						final int _sltid = (int) (1);
						_setstack.setCount((int) 1);
						(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
			}
		}
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).closeScreen();
	}
}
