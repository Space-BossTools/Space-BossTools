
package net.mrscauthd.boss_tools.item;

import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.block.RocketLaunchPadBlock;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import java.util.Comparator;

@BossToolsModElements.ModElement.Tag
public class Tier1RocketItemItem extends BossToolsModElements.ModElement {
	@ObjectHolder("boss_tools:tier_1_rocket")
	public static final Item block = null;
	public Tier1RocketItemItem(BossToolsModElements instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(BossToolsItemGroup.tab).maxStackSize(1).rarity(Rarity.COMMON));
			setRegistryName("tier_1_rocket");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			int fuel = (int) (itemstack.getOrCreateTag().getDouble("fuelgui"));
			list.add(new StringTextComponent("\u00A77" + fuel + "%" + " " + "\u00A79Fuel"));
		}

		@Override
		public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
			ActionResultType retval = super.onItemUseFirst(stack, context);
			World world = context.getWorld();
			BlockPos pos = context.getPos();
			PlayerEntity entity = context.getPlayer();
			Direction direction = context.getFace();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			ItemStack itemstack = context.getItem();
			{
				BlockState state = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
				if (world.isAirBlock(new BlockPos((int) x, (int) y + 1, (int) z)) && world.isAirBlock(new BlockPos((int) x, (int) y + 2, (int) z))&& world.isAirBlock(new BlockPos((int) x, (int) y + 3, (int) z))) {
					if (state.getBlock() instanceof RocketLaunchPadBlock.CustomBlock && state.get(RocketLaunchPadBlock.CustomBlock.STAGE) == true) {
						// check if entity on this pos
						Boolean entityblock = false;
						{
							List<Entity> _entfound = world.getEntitiesWithinAABB(Entity.class,
									new AxisAlignedBB((x + 0.5) - (1 / 2d), (Math.floor(y)) - (0.4 / 2d), (z + 0.5) - (1 / 2d),
											(x + 0.5) + (1 / 2d), (Math.floor(y)) + (0.4 / 2d), (z + 0.5) + (1 / 2d)),
									null).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
								}
							}.compareDistOf((Math.floor(x)), (Math.floor(y)), (Math.floor(z)))).collect(Collectors.toList());
							for (Entity entityiterator : _entfound) {
								if (entityiterator instanceof RocketEntity.CustomEntity || entityiterator instanceof RocketTier2Entity.CustomEntity
										|| entityiterator instanceof RocketTier3Entity.CustomEntity
										|| entityiterator instanceof LandingGearEntity.CustomEntity) {
									entityblock = true;
								} else {
									entityblock = false;
								}
							}
						}
						if (entityblock == false) {
						if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier3RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() != new ItemStack(RoverItemItem.block, (int) (1)).getItem())) {
								if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
										.getOrCreateTag().getDouble("Rocketfuel")) == 0)) {
									if (world instanceof ServerWorld) {
										Entity entityToSpawn = new RocketEntity.CustomEntity(RocketEntity.entity, (World) world);
										ServerWorld serverworld = (ServerWorld) world;
										ArmorStandEntity rentity = EntityType.ARMOR_STAND.create(serverworld, itemstack.getTag(),
												(ITextComponent) null, context.getPlayer(), pos, SpawnReason.SPAWN_EGG, true, true);
										entityToSpawn.setLocationAndAngles((x + 0.5), (rentity.getPosY()), (z + 0.5), (float) 0, (float) 0);
										entityToSpawn.setRenderYawOffset((float) 0);
										entityToSpawn.rotationYaw = (float) (0);
										entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
										entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
										if (entityToSpawn instanceof MobEntity)
											((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
												world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED,
												(ILivingEntityData) null, (CompoundNBT) null);
										entityToSpawn.getPersistentData().putDouble("Rocketfuel", 0);
										world.addEntity(entityToSpawn);
									}
									if (world instanceof World && !world.isRemote()) {
										((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1);
									} else {
										((World) world).playSound(x, y, z,
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
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
								if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
										.getOrCreateTag().getDouble("Rocketfuel")) == 1)) {
									if (world instanceof ServerWorld) {
										Entity entityToSpawn = new RocketEntity.CustomEntity(RocketEntity.entity, (World) world);
										ServerWorld serverworld = (ServerWorld) world;
										ArmorStandEntity rentity = EntityType.ARMOR_STAND.create(serverworld, itemstack.getTag(),
												(ITextComponent) null, context.getPlayer(), pos, SpawnReason.SPAWN_EGG, true, true);
										entityToSpawn.setLocationAndAngles((x + 0.5), (rentity.getPosY()), (z + 0.5), (float) 0, (float) 0);
										entityToSpawn.setRenderYawOffset((float) 0);
										entityToSpawn.rotationYaw = (float) (0);
										entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
										entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
										if (entityToSpawn instanceof MobEntity)
											((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
												world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED,
												(ILivingEntityData) null, (CompoundNBT) null);
										entityToSpawn.getPersistentData().putDouble("Rocketfuel", 1);
										entityToSpawn.getPersistentData().putDouble("fuel",
												(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
														.getOrCreateTag().getDouble("fuel")));
										world.addEntity(entityToSpawn);
									}
									if (world instanceof World && !world.isRemote()) {
										((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1);
									} else {
										((World) world).playSound(x, y, z,
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
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
								//return
							//	return retval;
							}
						}
							//Rocket Place Off Hand
						if (entityblock == false) {
							if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() == new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() != new ItemStack(Tier3RocketItemItem.block, (int) (1)).getItem() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() != new ItemStack(RoverItemItem.block, (int) (1)).getItem())) {
								if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
										.getOrCreateTag().getDouble("Rocketfuel")) == 0)) {
									if (world instanceof ServerWorld) {
										Entity entityToSpawn = new RocketEntity.CustomEntity(RocketEntity.entity, (World) world);
										ServerWorld serverworld = (ServerWorld) world;
										ArmorStandEntity rentity = EntityType.ARMOR_STAND.create(serverworld, itemstack.getTag(),
												(ITextComponent) null, context.getPlayer(), pos, SpawnReason.SPAWN_EGG, true, true);
										entityToSpawn.setLocationAndAngles((x + 0.5), (rentity.getPosY()), (z + 0.5), (float) 0, (float) 0);
										entityToSpawn.setRenderYawOffset((float) 0);
										entityToSpawn.rotationYaw = (float) (0);
										entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
										entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
										if (entityToSpawn instanceof MobEntity)
											((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
												world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED,
												(ILivingEntityData) null, (CompoundNBT) null);
										entityToSpawn.getPersistentData().putDouble("Rocketfuel", 0);
										world.addEntity(entityToSpawn);
									}
									if (world instanceof World && !world.isRemote()) {
										((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1);
									} else {
										((World) world).playSound(x, y, z,
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
									}
									if (entity instanceof LivingEntity) {
										ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
										_setstack.setCount((int) 1);
										((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
										if (entity instanceof ServerPlayerEntity)
											((ServerPlayerEntity) entity).inventory.markDirty();
									}
								}
								if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
										.getOrCreateTag().getDouble("Rocketfuel")) == 1)) {
									if (world instanceof ServerWorld) {
										Entity entityToSpawn = new RocketEntity.CustomEntity(RocketEntity.entity, (World) world);
										ServerWorld serverworld = (ServerWorld) world;
										ArmorStandEntity rentity = EntityType.ARMOR_STAND.create(serverworld, itemstack.getTag(),
												(ITextComponent) null, context.getPlayer(), pos, SpawnReason.SPAWN_EGG, true, true);
										entityToSpawn.setLocationAndAngles((x + 0.5), (rentity.getPosY()), (z + 0.5), (float) 0, (float) 0);
										entityToSpawn.setRenderYawOffset((float) 0);
										entityToSpawn.rotationYaw = (float) (0);
										entityToSpawn.setRenderYawOffset(entityToSpawn.rotationYaw);
										entityToSpawn.prevRotationYaw = entityToSpawn.rotationYaw;
										if (entityToSpawn instanceof MobEntity)
											((MobEntity) entityToSpawn).prevRenderYawOffset = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).rotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).prevRotationYawHead = entityToSpawn.rotationYaw;
										((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
												world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED,
												(ILivingEntityData) null, (CompoundNBT) null);
										entityToSpawn.getPersistentData().putDouble("Rocketfuel", 1);
										entityToSpawn.getPersistentData().putDouble("fuel",
												(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
														.getOrCreateTag().getDouble("fuel")));
										world.addEntity(entityToSpawn);
									}
									if (world instanceof World && !world.isRemote()) {
										((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1);
									} else {
										((World) world).playSound(x, y, z,
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("block.stone.break")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
									}
									if (entity instanceof LivingEntity) {
										ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
										_setstack.setCount((int) 1);
										((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
										if (entity instanceof ServerPlayerEntity)
											((ServerPlayerEntity) entity).inventory.markDirty();
									}
								}
								//return
								//return retval;
							}
							//Rocket Place Off Hand End
						}
					}
				}
			}
			return retval;
		}
	}
}
