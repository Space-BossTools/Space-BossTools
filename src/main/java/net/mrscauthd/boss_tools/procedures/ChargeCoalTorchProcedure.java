package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.MobInnet;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Direction;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

public class ChargeCoalTorchProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
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

			//Check Dim
			if ((!(((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:moon"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("boss_tools:mars"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_overworld"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:mercury"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_moon"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_mars"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_mercury"))))
					|| (((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(
					Registry.WORLD_KEY, new ResourceLocation("boss_tools:venus"))))
					|| ((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(
					Registry.WORLD_KEY,
					new ResourceLocation("boss_tools:orbit_venus")))))))))))))) {

				//Flint and Steel check
				if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
						.getItem() == new ItemStack(Items.FLINT_AND_STEEL, (int) (1)).getItem())
						|| (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
						.getItem() == new ItemStack(Items.FLINT_AND_STEEL, (int) (1)).getItem()))) {

					//Block check (wall torch)
					if ((world.getBlockState(new BlockPos((int) (Math.floor(x)), (int) (Math.floor(y)), (int) (Math.floor(z)))))
							.getBlock() == MobInnet.WALLCOALTORCHBLOCK.get().getDefaultState().getBlock()) {

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
							.getBlock() == MobInnet.COALTORCHBLOCK.get().getDefaultState().getBlock()) {

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