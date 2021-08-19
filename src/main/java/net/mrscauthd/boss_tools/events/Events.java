package net.mrscauthd.boss_tools.events;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.entity.RocketEntity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class Events {
    //Player Tick
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Entity entity = event.player;
            World world = entity.world;
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();
        }
    }
    //RightClickonBlock Event
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity entity = event.getPlayer();
        if (event.getHand() != entity.getActiveHand()) {
            return;
        }
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        IWorld world = event.getWorld();
        //Carge Coal Torch Event
        {
            //Check Dim
            if (!(((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_overworld")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus")))) || ((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_venus"))))))))))))) {

                //Flint and Steel check
                if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == new ItemStack(Items.FLINT_AND_STEEL, (int) (1)).getItem()) || (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem() == new ItemStack(Items.FLINT_AND_STEEL, (int) (1)).getItem()))) {
                    if (entity.isSneaking() == false) {

                        //Block check (wall torch)
                        if ((world.getBlockState(new BlockPos((int) (Math.floor(x)), (int) (Math.floor(y)), (int) (Math.floor(z)))))
                                .getBlock() == ModInnet.WALLCOALTORCHBLOCK.get().getDefaultState().getBlock()) {

                            //Check Direction
                            Direction TD = Direction.NORTH;
                            TD = (Direction) (new Object() {
                                public Direction getDirection(BlockPos pos) {
                                    try {
                                        BlockState _bs = world.getBlockState(pos);
                                        DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
                                        if (property != null)
                                            return _bs.get(property);
                                        return Direction.getFacingFromAxisDirection(
                                                _bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
                                                Direction.AxisDirection.POSITIVE);
                                    } catch (Exception e) {
                                        return Direction.NORTH;
                                    }
                                }
                            }.getDirection(new BlockPos((int) x, (int) y, (int) z)));

                            //Set Block
                            world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.WALL_TORCH.getDefaultState(), 3);

                            //Set Rotation
                            try {
                                BlockState _bs = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
                                DirectionProperty _property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
                                if (_property != null) {
                                    world.setBlockState(new BlockPos((int) x, (int) y, (int) z), _bs.with(_property, TD), 3);
                                } else {
                                    world.setBlockState(new BlockPos((int) x, (int) y, (int) z),
                                            _bs.with((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis"), TD.getAxis()), 3);
                                }
                            } catch (Exception e) {
                            }

                            //Sounds
                            if (world instanceof World && !world.isRemote()) {
                                ((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
                                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")),
                                        SoundCategory.NEUTRAL, (float) 1, (float) 1);
                            } else {
                                ((World) world).playSound(x, y, z,
                                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")),
                                        SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                            }
                        }

                        //Block check (stand Torch)
                        if ((world.getBlockState(new BlockPos((int) (Math.floor(x)), (int) (Math.floor(y)), (int) (Math.floor(z)))))
                                .getBlock() == ModInnet.COALTORCHBLOCK.get().getDefaultState().getBlock()) {

                            //Set Block
                            world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.TORCH.getDefaultState(), 3);

                            //Sounds
                            if (world instanceof World && !world.isRemote()) {
                                ((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
                                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")),
                                        SoundCategory.NEUTRAL, (float) 1, (float) 1);
                            } else {
                                ((World) world).playSound(x, y, z,
                                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")),
                                        SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                            }
                        }
                    }
                }
            }
        }
    }
    //BlockEvent
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent event) {
        IWorld world = event.getWorld();
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        if ((((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_overworld")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars")))) || (((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury")))) || ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus")))) || ((world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_venus")))))))))))) {
            if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == Blocks.FIRE.getDefaultState().getBlock())) {
                if (world instanceof World && !world.isRemote()) {
                    ((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z), (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1);
                } else {
                    ((World) world).playSound(x, y, z, (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                }
                world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
            }
            if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == Blocks.WALL_TORCH.getDefaultState().getBlock())) {
                Direction TD = Direction.NORTH;
                TD = (Direction) (new Object() {
                    public Direction getDirection(BlockPos pos) {
                        try {
                            BlockState _bs = world.getBlockState(pos);
                            DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
                            if (property != null)
                                return _bs.get(property);
                            return Direction.getFacingFromAxisDirection(
                                    _bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
                                    Direction.AxisDirection.POSITIVE);
                        } catch (Exception e) {
                            return Direction.NORTH;
                        }
                    }
                }.getDirection(new BlockPos((int) x, (int) y, (int) z)));

                if (world instanceof World && !world.isRemote()) {
                    ((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z), (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1);
                } else {
                    ((World) world).playSound(x, y, z, (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                }

                world.setBlockState(new BlockPos((int) x, (int) y, (int) z), ModInnet.WALLCOALTORCHBLOCK.get().getDefaultState(), 3);
                try {
                    BlockState _bs = world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
                    DirectionProperty _property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
                    if (_property != null) {
                        world.setBlockState(new BlockPos((int) x, (int) y, (int) z), _bs.with(_property, TD), 3);
                    } else {
                        world.setBlockState(new BlockPos((int) x, (int) y, (int) z),
                                _bs.with((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis"), TD.getAxis()), 3);
                    }
                } catch (Exception e) {
                }
            }
            if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == Blocks.TORCH.getDefaultState().getBlock())) {
                if (world instanceof World && !world.isRemote()) {((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z), (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1);
                } else {
                    ((World) world).playSound(x, y, z, (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                }

                world.setBlockState(new BlockPos((int) x, (int) y, (int) z), ModInnet.COALTORCHBLOCK.get().getDefaultState(), 3);
            }
            if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == Blocks.WALL_TORCH.getDefaultState().getBlock())) {
                if (world instanceof World && !world.isRemote()) {
                    ((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z), (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1);
                } else {
                    ((World) world).playSound(x, y, z, (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
                }
                world.setBlockState(new BlockPos((int) x, (int) y, (int) z), ModInnet.COALTORCHBLOCK.get().getDefaultState(), 3);
            }
            if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == Blocks.CAMPFIRE.getDefaultState().getBlockState().with(CampfireBlock.LIT, false).getBlock())) {
                {
                    BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
                    BlockState _bs = Blocks.CAMPFIRE.getDefaultState();
                    BlockState _bso = world.getBlockState(_bp);
                    for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                        Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
                        if (_property != null && _bs.get(_property) != null)
                            try {
                                _bs = _bs.with(_property, (Comparable) entry.getValue());
                            } catch (Exception e) {
                            }
                    }
                    world.setBlockState(_bp, _bs.with(CampfireBlock.LIT, false), 3);
                }

                //world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, false), 3);
            }
        }
    }
    //OnEntityTick Event
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntityLiving();
        World world = entity.world;
        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();
        // Config
        if ((Config.EntityOxygenSystem == (true))) {
            if ((EntityTypeTags.getCollection().getTagByID(new ResourceLocation(("forge:entities/space").toLowerCase(java.util.Locale.ENGLISH))).contains(entity.getType()))) {
                if ((((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_overworld")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mercury")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_moon")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mars")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_mercury")))) || (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus")))) || ((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:orbit_venus"))))))))))))) {
                    entity.getPersistentData().putDouble("tick", ((entity.getPersistentData().getDouble("tick")) + 1));
                    if (((entity.getPersistentData().getDouble("tick")) >= 15)) {
                        world.addParticle(ParticleTypes.SMOKE, x, (y + 1), z, 0, 0, 0);
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).attackEntityFrom(new DamageSource("space").setDamageBypassesArmor(), (float) 1);
                        }
                        entity.getPersistentData().putDouble("tick", 0);
                    }
                }
            }
        }
    }
    //Camera Event
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
        if (event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketEntity.CustomEntity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketTier2Entity.CustomEntity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof RocketTier3Entity.CustomEntity || event.getInfo().getRenderViewEntity().getRidingEntity() instanceof LandingGearEntity.CustomEntity) {
            if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_FRONT)) {
                event.getInfo().movePosition(-event.getInfo().calcCameraDistance(8d), 0d, 0);
            }
            if (Minecraft.getInstance().gameSettings.getPointOfView().equals(PointOfView.THIRD_PERSON_BACK)) {
                event.getInfo().movePosition(-event.getInfo().calcCameraDistance(8d), 0d, 0);
            }
        }
    }
    //Other Events
}
