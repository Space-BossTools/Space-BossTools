package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.Rotation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

import java.util.Map;

import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
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
				model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(3F);
				model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(3F);
				model.bipedRightArm.rotationPointX = (float) Math.toRadians(-250F);// -200
				model.bipedLeftArm.rotationPointX = (float) Math.toRadians(250F);
				model.bipedLeftArm.rotateAngleX = (float) -0.07;
				model.bipedRightArm.rotateAngleX = (float) -0.07;
				// model.bipedLeftArm.rotationPointZ = (float) 0;
				// model.bipedRightArm.rotationPointZ = (float) 0;
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
	public void Rotation(RenderItemEvent.Held event) {
		Entity player = event.getEntity();
		if (((player.getRidingEntity()) instanceof RocketTier2Entity.CustomEntity)) {
			event.getMatrixStack().scale(0, 0, 0);
		}
	}
}
