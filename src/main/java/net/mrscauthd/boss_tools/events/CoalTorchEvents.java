package net.mrscauthd.boss_tools.events;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;

@Mod.EventBusSubscriber
public class CoalTorchEvents {
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

        //Check Dim
        if (Events.Dimcheck((World) world) == false
                && entity.getHeldItemMainhand().getItem() == Items.FLINT_AND_STEEL || entity.getHeldItemOffhand().getItem() == Items.FLINT_AND_STEEL
                && entity.isSneaking() == false) {

            //Block check (wall torch)
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == ModInnet.WALLCOALTORCHBLOCK.get().getDefaultState().getBlock()) {
                //Get Wall Torch Dr
                BlockPos pos = new BlockPos(x,y,z);

                //Facing
                BlockState bs = world.getBlockState(new BlockPos(pos));
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateContainer().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlockState(new BlockPos(pos), Blocks.WALL_TORCH.getDefaultState().with(property,bs.get(property)), 3);

                play_fire_sounds_place(pos, (World) world);
            }

            //Replace Coal Torch to Torch
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == ModInnet.COALTORCHBLOCK.get().getDefaultState().getBlock()) {
                world.setBlockState(new BlockPos(x,y,z), Blocks.TORCH.getDefaultState(), 3);

                play_fire_sounds_place(new BlockPos(x,y,z), (World) world);
            }
        }
    }

    //BlockEvent
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        IWorld world = event.getWorld();
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        if (Events.Dimcheck((World) world) == true) {
            //Remove Fire
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == Blocks.FIRE.getDefaultState().getBlock()) {
                world.setBlockState(new BlockPos(x,y,z), Blocks.AIR.getDefaultState(), 3);

                play_fire_sounds_delete(new BlockPos(x,y,z), (World) world);
            }

            //Place Coal Wall Torch
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == Blocks.WALL_TORCH.getDefaultState().getBlock()) {
                //Get Wall Torch Dr
                BlockPos pos = new BlockPos(x,y,z);

                //Facing
                BlockState bs = world.getBlockState(new BlockPos(pos));
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateContainer().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlockState(new BlockPos(pos), ModInnet.WALLCOALTORCHBLOCK.get().getDefaultState().with(property,bs.get(property)), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            //Place Coal Torch
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == Blocks.TORCH.getDefaultState().getBlock()) {
                world.setBlockState(new BlockPos(x,y,z), ModInnet.COALTORCHBLOCK.get().getDefaultState(), 3);

                play_fire_sounds_delete(new BlockPos(x,y,z), (World) world);
            }

            //Campfire State Change
            if (world.getBlockState(new BlockPos(x,y,z)).getBlock() == Blocks.CAMPFIRE.getDefaultState().getBlockState().with(CampfireBlock.LIT, true).getBlock()) {
                BlockPos pos = new BlockPos(x,y,z);
                //Get Block State
                BooleanProperty property = (BooleanProperty) world.getBlockState(pos).getBlock().getStateContainer().getProperty("lit");

                //Set Block State
                world.setBlockState(pos, world.getBlockState(pos).with(property, false), 3);

                play_fire_sounds_delete(pos, (World) world);
            }
        }
    }

    //Play Sound Methode
    public static void play_fire_sounds_delete(BlockPos pos, World world) {
        if (world instanceof World && !world.isRemote()) {
            world.playSound(null, new BlockPos(pos), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, 1, 1);
        } else {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundCategory.NEUTRAL, 1, 1, false);
        }
    }
    //Play Sound Methode
    public static void play_fire_sounds_place(BlockPos pos, World world) {
        if (world instanceof World && !world.isRemote()) {
            world.playSound(null, new BlockPos(pos), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")), SoundCategory.NEUTRAL, 1, 1);
        } else {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.flintandsteel.use")), SoundCategory.NEUTRAL, 1, 1, false);
        }
    }
}
