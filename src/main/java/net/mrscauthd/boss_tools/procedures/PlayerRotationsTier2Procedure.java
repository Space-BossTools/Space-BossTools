package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;


import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;

import java.util.Map;
import java.util.HashMap;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;

@BossToolsModElements.ModElement.Tag
public class PlayerRotationsTier2Procedure extends BossToolsModElements.ModElement {
	public PlayerRotationsTier2Procedure(BossToolsModElements instance) {
		super(instance, 429);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupPlayerAngles(PlayerModelEvent.SetupAngles.Post event) {
		PlayerEntity player = event.getPlayer();// Entity entity = (Entity) dependencies.get("entity");
		if (((player.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)) {
			{
				PlayerModel model = event.getModelPlayer();
				// model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-60F);
				// model.bipedRightLeg.rotationPointY = (float) Math.toRadians(0);
				// Legs
				model.bipedRightLeg.rotationPointY = (float) Math.toRadians(485F);
				model.bipedLeftLeg.rotationPointY = (float) Math.toRadians(485F);
				model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(0F);
				model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(0F);
				// Arms
				// model.bipedRightArm.rotateAngleX = (float) Math.toRadians(0F); //bugy
				// model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(0F); //bugy
				// model.bipedHead.rotationPointX = 0.0F;
				// model.bipedHead.rotationPointZ = 0.0F;
				// event.setCanceled(true);
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
