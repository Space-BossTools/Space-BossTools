package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;
import net.mrscauthd.boss_tools.item.BucketBigItem;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.block.FuelBlock;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.potion.EffectInstance;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@BossToolsModElements.ModElement.Tag
public class LandingGearfalltoworldProcedure extends BossToolsModElements.ModElement {
	public LandingGearfalltoworldProcedure(BossToolsModElements instance) {
		super(instance, 333);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure LandingGearfalltoworld!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure LandingGearfalltoworld!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
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
			}.getItemStack((int) (1), (entity.getRidingEntity()))).getItem() == new ItemStack(FuelBlock.bucket, (int) (1)).getItem())) {
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
			if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_overworld"))))) {
				if ((((entity.getRidingEntity()).getPosY()) <= 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (World.OVERWORLD)))) {
						{
							Entity _ent = entity;
							if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
								RegistryKey<World> destinationType = World.OVERWORLD;
								ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
								if (nextWorld != null) {
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
									((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 700, entity.getPosZ(), _ent.rotationYaw,
											_ent.rotationPitch);
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
									for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
										((ServerPlayerEntity) _ent).connection
												.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
									}
								}
							}
						}
					}
					if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (World.OVERWORLD))) {
						{
							Entity _ent = entity;
							_ent.setPositionAndUpdate((entity.getPosX()), 700, (entity.getPosZ()));
							if (_ent instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), 700, (entity.getPosZ()),
										_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
							}
						}
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity2.rotationYaw = (float) (0);
						if (entity2 instanceof MobEntity)
							entity2.setRenderYawOffset(entity2.rotationYaw);
						entity2.prevRotationYaw = entity2.rotationYaw;
						((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						Entity entityiterator = entity2;
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
									final ItemStack _setstack = new ItemStack(FuelBlock.bucket, (int) (1));
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
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_moon"))))) {
				if ((((entity.getRidingEntity()).getPosY()) <= 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon")))))) {
						{
							Entity _ent = entity;
							if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
								RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
										new ResourceLocation("boss_tools:moon"));
								ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
								if (nextWorld != null) {
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
									((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 700, entity.getPosZ(), _ent.rotationYaw,
											_ent.rotationPitch);
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
									for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
										((ServerPlayerEntity) _ent).connection
												.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
									}
								}
							}
						}
					}
					if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))))) {
						{
							Entity _ent = entity;
							_ent.setPositionAndUpdate((entity.getPosX()), 700, (entity.getPosZ()));
							if (_ent instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), 700, (entity.getPosZ()),
										_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
							}
						}
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity2.rotationYaw = (float) (0);
						if (entity2 instanceof MobEntity)
							entity2.setRenderYawOffset(entity2.rotationYaw);
						entity2.prevRotationYaw = entity2.rotationYaw;
						((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						Entity entityiterator = entity2;
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
									final ItemStack _setstack = new ItemStack(FuelBlock.bucket, (int) (1));
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
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_mars"))))) {
				if ((((entity.getRidingEntity()).getPosY()) <= 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars")))))) {
						{
							Entity _ent = entity;
							if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
								RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
										new ResourceLocation("boss_tools:mars"));
								ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
								if (nextWorld != null) {
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
									((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 700, entity.getPosZ(), _ent.rotationYaw,
											_ent.rotationPitch);
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
									for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
										((ServerPlayerEntity) _ent).connection
												.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
									}
								}
							}
						}
					}
					if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))))) {
						{
							Entity _ent = entity;
							_ent.setPositionAndUpdate((entity.getPosX()), 700, (entity.getPosZ()));
							if (_ent instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), 700, (entity.getPosZ()),
										_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
							}
						}
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity2.rotationYaw = (float) (0);
						if (entity2 instanceof MobEntity)
							entity2.setRenderYawOffset(entity2.rotationYaw);
						entity2.prevRotationYaw = entity2.rotationYaw;
						((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						Entity entityiterator = entity2;
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
									final ItemStack _setstack = new ItemStack(FuelBlock.bucket, (int) (1));
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
								(entity.getRidingEntity()).getPersistentData().putDouble("Lander1", 1);
								entity.getPersistentData().putDouble("Landerfly", 0);
							}
						}
					}
				}
			}
			if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_mercury"))))) {
				if ((((entity.getRidingEntity()).getPosY()) <= 1)) {
					if (!(entity.getRidingEntity()).world.isRemote())
						(entity.getRidingEntity()).remove();
					if ((!((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))))) {
						{
							Entity _ent = entity;
							if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
								RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
										new ResourceLocation("boss_tools:mercury"));
								ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
								if (nextWorld != null) {
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
									((ServerPlayerEntity) _ent).teleport(nextWorld, entity.getPosX(), 700, entity.getPosZ(), _ent.rotationYaw,
											_ent.rotationPitch);
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
									for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
										((ServerPlayerEntity) _ent).connection
												.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
									}
								}
							}
						}
					}
					if (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey
							.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury"))))) {
						{
							Entity _ent = entity;
							_ent.setPositionAndUpdate((entity.getPosX()), 700, (entity.getPosZ()));
							if (_ent instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) _ent).connection.setPlayerLocation((entity.getPosX()), 700, (entity.getPosZ()),
										_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
							}
						}
					}
					if (((world.isRemote()) == (false))) {
						Entity entity2 = new LandingGearEntity.CustomEntity(LandingGearEntity.entity, entity.world);
						entity2.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
						entity2.rotationYaw = (float) (0);
						if (entity2 instanceof MobEntity)
							entity2.setRenderYawOffset(entity2.rotationYaw);
						entity2.prevRotationYaw = entity2.rotationYaw;
						((MobEntity) entity2).prevRotationYawHead = entity2.rotationYaw;
						entity.world.addEntity(entity2);
						entity.startRiding(entity2);
						Entity entityiterator = entity2;
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
									final ItemStack _setstack = new ItemStack(FuelBlock.bucket, (int) (1));
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
