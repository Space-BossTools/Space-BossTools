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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.block.Blocks;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;
import java.util.HashMap;

@BossToolsModElements.ModElement.Tag
public class LandingGearfalltoworldProcedure extends BossToolsModElements.ModElement {
	public LandingGearfalltoworldProcedure(BossToolsModElements instance) {
		super(instance, 393);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LandingGearfalltoworld!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure LandingGearfalltoworld!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure LandingGearfalltoworld!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure LandingGearfalltoworld!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure LandingGearfalltoworld!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if ((((entity.getRidingEntity()).getPersistentData().getDouble("Lander2")) == 1)) {
				entity.getPersistentData().putDouble("Landerfly", 1);
			}
			if (((((entity.getRidingEntity()).getPersistentData().getDouble("Lander2")) == 1) == (false))) {
				entity.getPersistentData().putDouble("Landerfly", 0);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("RocketTier", 0);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 0);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("RocketTier", 1);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("RocketTier", 2);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (0), (entity.getRidingEntity()))).getItem() == new ItemStack(Tier3RocketItemItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("RocketTier", 3);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(Items.BUCKET, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 1);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBuckedItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 2);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(BucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 3);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if (((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBucketBigItem.block, (int) (1)).getItem())) {
				entity.getPersistentData().putDouble("Bucket", 4);
			}
		}
		if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			if ((world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z))) != null
					&& world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
							.equals(new ResourceLocation("boss_tools:orbit_overworld_biom")))) {
				if ((((entity.getRidingEntity()).getPosY()) < 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/execute in minecraft:overworld run teleport @p ~ 500 ~");
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 1)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 2)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 3)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
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
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 3)) {
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
							if (((entity.getPersistentData().getDouble("Bucket")) == 4)) {
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
						if (((entity.getPersistentData().getDouble("Landerfly")) == 1)) {
							if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
								if ((entity.getRidingEntity()) instanceof LivingEntity)
									((LivingEntity) (entity.getRidingEntity()))
											.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 1800, (int) 3, (false), (false)));
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if ((world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z))) != null
					&& world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
							.equals(new ResourceLocation("boss_tools:orbit_moon_biom")))) {
				if ((((entity.getRidingEntity()).getPosY()) < 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/execute in boss_tools:moon run teleport @p ~ 500 ~");
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 1)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 2)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 3)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
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
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 3)) {
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
							if (((entity.getPersistentData().getDouble("Bucket")) == 4)) {
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
						if (((entity.getPersistentData().getDouble("Landerfly")) == 1)) {
							if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
								if ((entity.getRidingEntity()) instanceof LivingEntity)
									((LivingEntity) (entity.getRidingEntity()))
											.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 1800, (int) 3, (false), (false)));
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if ((world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z))) != null
					&& world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
							.equals(new ResourceLocation("boss_tools:orbit_mars_biom")))) {
				if ((((entity.getRidingEntity()).getPosY()) < 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/execute in boss_tools:mars run teleport @p ~ 500 ~");
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 1)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 2)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 3)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
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
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 3)) {
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
							if (((entity.getPersistentData().getDouble("Bucket")) == 4)) {
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
						if (((entity.getPersistentData().getDouble("Landerfly")) == 1)) {
							if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
								if ((entity.getRidingEntity()) instanceof LivingEntity)
									((LivingEntity) (entity.getRidingEntity()))
											.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 1800, (int) 3, (false), (false)));
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if ((world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z))) != null
					&& world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos((int) x, (int) y, (int) z)))
							.equals(new ResourceLocation("boss_tools:orbit_mercury_biom")))) {
				if ((((entity.getRidingEntity()).getPosY()) < 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if (world instanceof ServerWorld) {
						((World) world).getServer().getCommandManager().handleCommand(
								new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
										new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
								"/execute in boss_tools:mercury run teleport @p ~ 500 ~");
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 1)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 2)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
							}
						}
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("RocketTier")) == 3)) {
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
								entity.getPersistentData().putDouble("RocketTier", 0);
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
						if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
							if (((entity.getPersistentData().getDouble("Bucket")) == 3)) {
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
							if (((entity.getPersistentData().getDouble("Bucket")) == 4)) {
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
						if (((entity.getPersistentData().getDouble("Landerfly")) == 1)) {
							if (((entity.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
								if ((entity.getRidingEntity()) instanceof LivingEntity)
									((LivingEntity) (entity.getRidingEntity()))
											.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 1800, (int) 3, (false), (false)));
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
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
