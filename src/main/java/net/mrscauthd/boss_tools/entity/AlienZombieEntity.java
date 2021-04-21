
package net.mrscauthd.boss_tools.entity;

import net.mrscauthd.boss_tools.procedures.AlienZombieOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.itemgroup.SpaceBosstoolsSpawnEggsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import java.util.Map;
import java.util.HashMap;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@BossToolsModElements.ModElement.Tag
public class AlienZombieEntity extends BossToolsModElements.ModElement {
	public static EntityType entity = null;
	public AlienZombieEntity(BossToolsModElements instance) {
		super(instance, 15);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		// MinecraftForge.EVENT_BUS.register(this);
		// FMLJavaModLoadingContext.get().getModEventBus().register(new
		// EntityAttributesRegisterHandler());
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 2.4f)).build("alien_zombie")
						.setRegistryName("alien_zombie");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -14804199, -16740159, new Item.Properties().group(SpaceBosstoolsSpawnEggsItemGroup.tab))
				.setRegistryName("alien_zombie_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelalienzombie(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("boss_tools:textures/alien_zombie.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 20);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.8));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, false));
			this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, AlienEntity.class, false, false));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEAD;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.death"));
		}

		@Override
		public void baseTick() {
			super.baseTick();
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				AlienZombieOnEntityTickUpdateProcedure.executeProcedure($_dependencies);
			}
		}
	}

	// Made with Blockbench 3.8.4
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports
	public static class Modelalienzombie extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer nose_r1;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer head_r1;
		private final ModelRenderer head_r2;
		private final ModelRenderer body;
		private final ModelRenderer leg0;
		private final ModelRenderer leg1;
		private final ModelRenderer arm2;
		private final ModelRenderer arms;
		private final ModelRenderer monsterarm1;
		private final ModelRenderer cube_r4;
		private final ModelRenderer monsterarm2;
		private final ModelRenderer cube_r5;
		private final ModelRenderer monsterarm3;
		private final ModelRenderer cube_r6;
		private final ModelRenderer monsterarm4;
		private final ModelRenderer cube_r7;
		public Modelalienzombie() {
			textureWidth = 96;
			textureHeight = 64;
			head = new ModelRenderer(this);
			head.setRotationPoint(-0.0084F, 0.023F, -2.9621F);
			setRotationAngle(head, -0.2182F, 0.0F, 0.0F);
			head.setTextureOffset(0, 0).addBox(-3.9916F, -7.4559F, -5.0853F, 8.0F, 9.0F, 8.0F, 0.0F, true);
			nose_r1 = new ModelRenderer(this);
			nose_r1.setRotationPoint(0.0084F, -0.8556F, -6.2184F);
			head.addChild(nose_r1);
			setRotationAngle(nose_r1, -1.0036F, 0.0F, 0.0F);
			nose_r1.setTextureOffset(24, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(1.897F, 5.0235F, -7.2107F);
			head.addChild(cube_r1);
			setRotationAngle(cube_r1, 1.0036F, 0.48F, -0.8727F);
			cube_r1.setTextureOffset(88, 59).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(-1.939F, 5.0505F, -7.2254F);
			head.addChild(cube_r2);
			setRotationAngle(cube_r2, 1.0036F, -0.48F, 0.8727F);
			cube_r2.setTextureOffset(88, 59).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(0.0084F, 3.0701F, -8.6546F);
			head.addChild(cube_r3);
			setRotationAngle(cube_r3, -0.3927F, 0.0F, 0.0F);
			cube_r3.setTextureOffset(88, 54).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);
			head_r1 = new ModelRenderer(this);
			head_r1.setRotationPoint(0.0084F, -11.4255F, -0.113F);
			head.addChild(head_r1);
			setRotationAngle(head_r1, -0.1745F, 0.0F, 0.0F);
			head_r1.setTextureOffset(33, 0).addBox(-4.5F, -5.0F, -4.5F, 9.0F, 10.0F, 9.0F, 0.0F, true);
			head_r2 = new ModelRenderer(this);
			head_r2.setRotationPoint(0.0084F, 2.093F, -4.4925F);
			head.addChild(head_r2);
			setRotationAngle(head_r2, -1.0036F, 0.0F, 0.0F);
			head_r2.setTextureOffset(40, 53).addBox(-3.5F, -3.0F, -2.5F, 7.0F, 6.0F, 5.0F, 0.0F, true);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 0.0F, -3.0F);
			setRotationAngle(body, 0.2182F, 0.0F, 0.0F);
			body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, true);
			body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, 0.5F, true);
			leg0 = new ModelRenderer(this);
			leg0.setRotationPoint(2.0F, 12.0F, 0.0F);
			leg0.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
			leg1 = new ModelRenderer(this);
			leg1.setRotationPoint(-2.0F, 12.0F, 0.0F);
			leg1.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
			arm2 = new ModelRenderer(this);
			arm2.setRotationPoint(-6.0F, 2.0F, -1.5F);
			arm2.setTextureOffset(44, 22).addBox(10.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
			arms = new ModelRenderer(this);
			arms.setRotationPoint(0.0F, 4.0F, -1.5F);
			setRotationAngle(arms, 0.0436F, 0.0F, 0.0F);
			arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
			monsterarm1 = new ModelRenderer(this);
			monsterarm1.setRotationPoint(-4.0F, 0.0F, -2.0F);
			setRotationAngle(monsterarm1, 0.0F, 0.0F, 0.9599F);
			monsterarm1.setTextureOffset(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-16.25F, 0.0F, 0.75F);
			monsterarm1.addChild(cube_r4);
			setRotationAngle(cube_r4, 0.0F, -1.1345F, 0.0F);
			cube_r4.setTextureOffset(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, -0.1F, false);
			monsterarm2 = new ModelRenderer(this);
			monsterarm2.setRotationPoint(-3.0F, -0.25F, -3.0F);
			setRotationAngle(monsterarm2, 0.0F, 0.0F, -0.3927F);
			monsterarm2.setTextureOffset(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(-16.25F, 0.0F, 0.75F);
			monsterarm2.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.0F, -1.1345F, 0.0F);
			cube_r5.setTextureOffset(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, -0.1F, false);
			monsterarm3 = new ModelRenderer(this);
			monsterarm3.setRotationPoint(4.0F, 0.0F, -2.0F);
			setRotationAngle(monsterarm3, 0.0F, 0.0F, 2.1817F);
			monsterarm3.setTextureOffset(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(-16.25F, 0.0F, 0.75F);
			monsterarm3.addChild(cube_r6);
			setRotationAngle(cube_r6, 0.0F, -1.1345F, 0.0F);
			cube_r6.setTextureOffset(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, -0.1F, false);
			monsterarm4 = new ModelRenderer(this);
			monsterarm4.setRotationPoint(3.0F, -0.25F, -3.0F);
			setRotationAngle(monsterarm4, 0.0F, 0.0F, -2.7489F);
			monsterarm4.setTextureOffset(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, 0.0F, false);
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(-16.25F, 0.0F, 0.75F);
			monsterarm4.addChild(cube_r7);
			setRotationAngle(cube_r7, 0.0F, -1.1345F, 0.0F);
			cube_r7.setTextureOffset(30, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, -0.1F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			leg0.render(matrixStack, buffer, packedLight, packedOverlay);
			leg1.render(matrixStack, buffer, packedLight, packedOverlay);
			arm2.render(matrixStack, buffer, packedLight, packedOverlay);
			arms.render(matrixStack, buffer, packedLight, packedOverlay);
			monsterarm1.render(matrixStack, buffer, packedLight, packedOverlay);
			monsterarm2.render(matrixStack, buffer, packedLight, packedOverlay);
			monsterarm3.render(matrixStack, buffer, packedLight, packedOverlay);
			monsterarm4.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.leg0.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leg1.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.monsterarm1.rotateAngleY = MathHelper.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
			this.monsterarm4.rotateAngleY = MathHelper.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
			this.monsterarm3.rotateAngleY = MathHelper.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
			this.monsterarm2.rotateAngleY = MathHelper.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
			this.arms.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.arm2.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
