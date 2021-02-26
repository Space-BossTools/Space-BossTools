package net.mrscauthd.boss_tools.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;


import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import net.mrscauthd.boss_tools.entity.LandingGearEntity;
import net.mrscauthd.boss_tools.BossToolsModElements;

import java.util.Map;
import java.util.HashMap;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;

@BossToolsModElements.ModElement.Tag
public class PlayerRotationsLanderProcedure extends BossToolsModElements.ModElement {
	public PlayerRotationsLanderProcedure(BossToolsModElements instance) {
		super(instance, 429);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupPlayerAngles(PlayerModelEvent.SetupAngles.Pre event) {
		PlayerEntity player = event.getPlayer();// Entity entity = (Entity) dependencies.get("entity");
		if (((player.getRidingEntity()) instanceof LandingGearEntity.CustomEntity)) {
			{
				PlayerModel model = event.getModelPlayer();
				// model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-60F);
				// model.bipedRightLeg.rotationPointY = (float) Math.toRadians(0);
				// Legs
				model.bipedRightLeg.rotationPointY = (float) Math.toRadians(5F);
				model.bipedLeftLeg.rotationPointY = (float) Math.toRadians(5F);
				model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(0F);
				model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(0F);
				// Arms
				model.bipedRightArm.rotationPointY = (float) Math.toRadians(100F);
				model.bipedLeftArm.rotationPointY = (float) Math.toRadians(100F);
				model.bipedRightArm.rotationPointX = (float) Math.toRadians(0F);//-200
				model.bipedLeftArm.rotationPointX = (float) Math.toRadians(0F);
				// Body
				model.bipedBody.rotationPointY = (float) Math.toRadians(55F);
				model.bipedBody.rotationPointZ = (float) Math.toRadians(-200F);
				// chest
				model.bipedBodyWear.rotationPointY = (float) Math.toRadians(55F);
				model.bipedBodyWear.rotationPointZ = (float) Math.toRadians(-200F);
				// Head
				model.bipedHead.rotationPointY = (float) Math.toRadians(500F);
				model.bipedHead.rotationPointZ = (float) Math.toRadians(-100F);
				// Arms
				// model.bipedRightArm.rotateAngleX = (float) Math.toRadians(0F); //bugy
				// model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(0F); //bugy
				// model.bipedHead.rotationPointX = 0.0F;
				// model.bipedHead.rotationPointZ = 0.0F;
				event.setCanceled(true);
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
