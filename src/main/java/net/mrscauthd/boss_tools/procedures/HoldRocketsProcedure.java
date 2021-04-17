package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.item.Tier3RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier2RocketItemItem;
import net.mrscauthd.boss_tools.item.Tier1RocketItemItem;
import net.mrscauthd.boss_tools.BossToolsModElements;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

import java.util.Map;
import java.util.HashMap;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;

@BossToolsModElements.ModElement.Tag
public class HoldRocketsProcedure extends BossToolsModElements.ModElement {
	public HoldRocketsProcedure(BossToolsModElements instance) {
		super(instance, 392);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure HoldRockets!");
			return;
		}
		if (dependencies.get("playerModel") == null) {
			if (!dependencies.containsKey("playerModel"))
				BossToolsMod.LOGGER.warn("Failed to load dependency playerModel for procedure HoldRockets!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		PlayerModel playerModel = (PlayerModel) dependencies.get("playerModel");
		// Rocket Tier 1
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		}
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier1RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		} // Rocket Tier 2
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		}
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier2RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		} // Rocket Tier 3
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier3RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		}
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Tier3RocketItemItem.block, (int) (1)).getItem())) {
			playerModel.bipedRightArm.rotateAngleX = (float) 10;
			playerModel.bipedLeftArm.rotateAngleX = (float) 10;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void setupPlayerRotations(PlayerModelEvent.SetupAngles.Post event) {
		Entity entity = event.getEntity();
		PlayerModel playerModel = event.getModelPlayer();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("entity", entity);
		dependencies.put("playerModel", playerModel);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
