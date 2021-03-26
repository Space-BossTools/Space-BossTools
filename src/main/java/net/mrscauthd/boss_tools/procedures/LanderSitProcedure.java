package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;
import net.mrscauthd.boss_tools.item.FuelBuckedItem;
import net.mrscauthd.boss_tools.item.BucketBigItem;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Comparator;

@BossToolsModElements.ModElement.Tag
public class LanderSitProcedure extends BossToolsModElements.ModElement {
	public LanderSitProcedure(BossToolsModElements instance) {
		super(instance, 371);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LanderSit!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure LanderSit!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure LanderSit!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure LanderSit!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure LanderSit!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((entity.getPersistentData().getDouble("Landersit")) == 1)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/summon boss_tools:landing_gear");
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (0.1 / 2d), (y + 1) - (0.1 / 2d), z - (0.1 / 2d), x + (0.1 / 2d), (y + 1) + (0.1 / 2d), z + (0.1 / 2d)),
						null).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, (y + 1), z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof LandingGearEntity.CustomEntity)) {
						entity.startRiding(entityiterator);
						entity.getPersistentData().putDouble("Landersit", 0);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							{
								final ItemStack _setstack = new ItemStack(Tier1RocketItemItem.block, (int) (1));
								final int _sltid = (int) (0);
								_setstack.setCount((int) 1);
								(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> {
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
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
								{
									final ItemStack _setstack = new ItemStack(FuelBuckedItem.block, (int) (1));
									final int _sltid = (int) (1);
									_setstack.setCount((int) 1);
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Landersit")) == 2)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/summon boss_tools:landing_gear");
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (0.1 / 2d), (y + 1) - (0.1 / 2d), z - (0.1 / 2d), x + (0.1 / 2d), (y + 1) + (0.1 / 2d), z + (0.1 / 2d)),
						null).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, (y + 1), z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof LandingGearEntity.CustomEntity)) {
						entity.startRiding(entityiterator);
						entity.getPersistentData().putDouble("Landersit", 0);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							{
								final ItemStack _setstack = new ItemStack(Tier2RocketItemItem.block, (int) (1));
								final int _sltid = (int) (0);
								_setstack.setCount((int) 1);
								(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> {
											if (capability instanceof IItemHandlerModifiable) {
												((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
											}
										});
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
								{
									final ItemStack _setstack = new ItemStack(BucketBigItem.block, (int) (1));
									final int _sltid = (int) (1);
									_setstack.setCount((int) 1);
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
								{
									final ItemStack _setstack = new ItemStack(FuelBucketBigItem.block, (int) (1));
									final int _sltid = (int) (1);
									_setstack.setCount((int) 1);
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Landersit")) == 3)) {
			if (((entity.getPersistentData().getDouble("LanderSpawn")) == 1)) {
				entity.getPersistentData().putDouble("LanderSpawn", 0);
				if (world instanceof ServerWorld) {
					((World) world).getServer().getCommandManager().handleCommand(
							new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
									new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
							"/summon boss_tools:landing_gear");
				}
			}
			{
				List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
						new AxisAlignedBB(x - (0.1 / 2d), (y + 1) - (0.1 / 2d), z - (0.1 / 2d), x + (0.1 / 2d), (y + 1) + (0.1 / 2d), z + (0.1 / 2d)),
						null).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(x, (y + 1), z)).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof LandingGearEntity.CustomEntity)) {
						entity.startRiding(entityiterator);
						entity.getPersistentData().putDouble("Landersit", 0);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							{
								final ItemStack _setstack = new ItemStack(Tier3RocketItemItem.block, (int) (1));
								final int _sltid = (int) (0);
								_setstack.setCount((int) 1);
								(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
										.ifPresent(capability -> {
											if (capability instanceof IItemHandlerModifiable) {
												((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
											}
										});
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 1)) {
								{
									final ItemStack _setstack = new ItemStack(BucketBigItem.block, (int) (1));
									final int _sltid = (int) (1);
									_setstack.setCount((int) 1);
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 2)) {
								{
									final ItemStack _setstack = new ItemStack(FuelBucketBigItem.block, (int) (1));
									final int _sltid = (int) (1);
									_setstack.setCount((int) 1);
									(entity.getRidingEntity()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
											.ifPresent(capability -> {
												if (capability instanceof IItemHandlerModifiable) {
													((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
												}
											});
								}
								entity.getPersistentData().putDouble("Bucket", 0);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}
}
