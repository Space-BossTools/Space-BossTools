package net.mrscauthd.boss_tools.flag;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.ModInnet;

public class FlagTileEntity extends TileEntity implements ITickableTileEntity {
    @Nullable
    private static PlayerProfileCache profileCache;
    @Nullable
    private static MinecraftSessionService sessionService;
    @Nullable
    private GameProfile playerProfile;

    public FlagTileEntity() {
        super(ModInnet.FLAG.get());
    }

    public static void setProfileCache(PlayerProfileCache profileCacheIn) {
        profileCache = profileCacheIn;
    }

    public static void setSessionService(MinecraftSessionService sessionServiceIn) {
        sessionService = sessionServiceIn;
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.playerProfile != null) {
            CompoundNBT compoundnbt = new CompoundNBT();
            NBTUtil.writeGameProfile(compoundnbt, this.playerProfile);
            compound.put("SkullOwner", compoundnbt);
        }

        return compound;
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("SkullOwner", 10)) {
            this.setPlayerProfile(NBTUtil.readGameProfile(nbt.getCompound("SkullOwner")));
        } else if (nbt.contains("ExtraType", 8)) {
            String s = nbt.getString("ExtraType");
            if (!StringUtils.isNullOrEmpty(s)) {
                this.setPlayerProfile(new GameProfile((UUID)null, s));
            }
        }

    }

    @Override
    public void tick() {

    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 4, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void setPlayerProfile(@Nullable GameProfile p_195485_1_) {
        playerProfile = p_195485_1_;
        this.updatePlayerProfile();
        //System.out.println(playerProfile);
    }

    private void updatePlayerProfile() {
        this.playerProfile = updateGameProfile(this.playerProfile);
        this.markDirty();
    }

    @Nullable
    public static GameProfile updateGameProfile(@Nullable GameProfile input) {
        if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
            if (input.isComplete() && input.getProperties().containsKey("textures")) {
                return input;
            } else if (profileCache != null && sessionService != null) {
                GameProfile gameprofile = profileCache.getGameProfileForUsername(input.getName());
                if (gameprofile == null) {
                    return input;
                } else {
                    Property property = Iterables.getFirst(gameprofile.getProperties().get("textures"), (Property)null);
                    if (property == null) {
                        gameprofile = sessionService.fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            } else {
                return input;
            }
        } else {
            return input;
        }
    }
}