
package net.mrscauthd.boss_tools.entity;

import net.mrscauthd.boss_tools.procedures.LandingGearOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.procedures.LandingGearEntityIsHurtProcedure;
import net.mrscauthd.boss_tools.procedures.LandingGearEntityDiesProcedure;
import net.mrscauthd.boss_tools.gui.LandinggearGuiGui;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@BossToolsModElements.ModElement.Tag
public class LandingGearEntity extends BossToolsModElements.ModElement {
	public static EntityType entity = null;
	public LandingGearEntity(BossToolsModElements instance) {
		super(instance, 80);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(100).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 2f))
						.build("landing_gear").setRegistryName("landing_gear");
		elements.entities.add(() -> entity);
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);
	}
	private static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelLander(), 0f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("boss_tools:textures/lander.png");
					}
				};
			});
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 10);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends CreatureEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
			enablePersistence();
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		@Override
		public double getMountedYOffset() {
			return super.getMountedYOffset() + -0.6;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			Entity sourceentity = source.getTrueSource();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				LandingGearEntityIsHurtProcedure.executeProcedure($_dependencies);
			}
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source.getImmediateSource() instanceof PlayerEntity)
				return false;
			if (source.getImmediateSource() instanceof PotionEntity)
				return false;
			if (source == DamageSource.CACTUS)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			if (source == DamageSource.LIGHTNING_BOLT)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public void onDeath(DamageSource source) {
			super.onDeath(source);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity sourceentity = source.getTrueSource();
			Entity entity = this;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				LandingGearEntityDiesProcedure.executeProcedure($_dependencies);
			}
		}
		private final ItemStackHandler inventory = new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}
		};
		private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this),
				new EntityArmorInvWrapper(this));
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
			if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null)
				return LazyOptional.of(() -> combined).cast();
			return super.getCapability(capability, side);
		}

		/*
		 * @Override protected void dropInventory() { super.dropInventory(); for (int i
		 * = 0; i < inventory.getSlots(); ++i) { ItemStack itemstack =
		 * inventory.getStackInSlot(i); if (!itemstack.isEmpty() &&
		 * !EnchantmentHelper.hasVanishingCurse(itemstack)) {
		 * this.entityDropItem(itemstack); } } }
		 */


		@Override
		public void writeAdditional(CompoundNBT compound) {
			super.writeAdditional(compound);
			compound.put("InventoryCustom", inventory.serializeNBT());
		}

		@Override
		public void readAdditional(CompoundNBT compound) {
			super.readAdditional(compound);
			INBT inventoryCustom = compound.get("InventoryCustom");
			if (inventoryCustom instanceof CompoundNBT)
				inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());
			if (sourceentity.isSecondaryUseActive()) {
				if (sourceentity instanceof ServerPlayerEntity) {
					NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new StringTextComponent("Landing Gear");
						}

						@Override
						public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
							PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
							packetBuffer.writeBlockPos(new BlockPos(sourceentity.getPosition()));
							packetBuffer.writeByte(0);
							packetBuffer.writeVarInt(CustomEntity.this.getEntityId());
							return new LandinggearGuiGui.GuiContainerMod(id, inventory, packetBuffer);
						}
					}, buf -> {
						buf.writeBlockPos(new BlockPos(sourceentity.getPosition()));
						buf.writeByte(0);
						buf.writeVarInt(this.getEntityId());
					});
				}
				return ActionResultType.func_233537_a_(this.world.isRemote());
			}
			super.func_230254_b_(sourceentity, hand);
			sourceentity.startRiding(this);
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			return retval;
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
				LandingGearOnEntityTickUpdateProcedure.executeProcedure($_dependencies);
			}
		}
	}

	// Made with Blockbench 3.6.5
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class ModelLander extends EntityModel<Entity> {
		private final ModelRenderer bone;
		private final ModelRenderer bone3;
		private final ModelRenderer bone5;
		private final ModelRenderer bone4;
		private final ModelRenderer bone6;
		private final ModelRenderer bone7;
		private final ModelRenderer bone2;
		private final ModelRenderer bb_main;
		public ModelLander() {
			textureWidth = 512;
			textureHeight = 512;
			bone = new ModelRenderer(this);
			bone.setRotationPoint(0.5F, 14.5F, 12.5F);
			setRotationAngle(bone, 0.0F, 2.3562F, 0.0F);
			bone.setTextureOffset(435, 110).addBox(-4.8051F, -6.5F, 5.0202F, 28.0F, 5.0F, 6.0F, 0.0F, false);
			bone.setTextureOffset(417, 52).addBox(6.4136F, -6.5F, -5.2035F, 6.0F, 5.0F, 27.0F, 0.0F, false);
			bone3 = new ModelRenderer(this);
			bone3.setRotationPoint(9.5F, -3.5F, 22.0858F);
			bone.addChild(bone3);
			setRotationAngle(bone3, -0.829F, 0.0F, 0.0F);
			bone3.setTextureOffset(18, 91).addBox(-2.0864F, -2.6756F, -2.4408F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bone3.setTextureOffset(18, 91).addBox(-2.0864F, 17.7146F, -20.8326F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bone3.setTextureOffset(18, 91).addBox(-2.0864F, -2.6756F, -2.4408F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, -1.6756F, 1.5592F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, 21.7146F, -19.8326F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, -1.6756F, 1.5592F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, -1.6756F, 12.5592F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, 33.7146F, -19.8326F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			bone3.setTextureOffset(398, 32).addBox(-1.0864F, -1.6756F, 12.5592F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			bone3.setTextureOffset(203, 344).addBox(-2.0864F, -2.6756F, 3.5592F, 4.0F, 4.0F, 9.0F, 0.0F, false);
			bone3.setTextureOffset(397, 109).addBox(-2.0864F, 23.7146F, -20.8326F, 4.0F, 10.0F, 4.0F, 0.0F, false);
			bone3.setTextureOffset(203, 344).addBox(-2.0864F, -2.6756F, 3.5592F, 4.0F, 4.0F, 9.0F, 0.0F, false);
			bone5 = new ModelRenderer(this);
			bone5.setRotationPoint(13.0852F, 23.4297F, 8.2285F);
			bone3.addChild(bone5);
			setRotationAngle(bone5, -0.829F, 2.3998F, -1.6144F);
			bone5.setTextureOffset(18, 91).addBox(-0.7374F, -37.3276F, -6.2515F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bone5.setTextureOffset(18, 91).addBox(0.3153F, -18.181F, 14.1458F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			bone5.setTextureOffset(388, 21).addBox(0.2626F, -36.3276F, -8.2515F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			bone5.setTextureOffset(388, 21).addBox(1.3153F, -14.181F, 15.1458F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			bone5.setTextureOffset(388, 21).addBox(1.3153F, -3.181F, 15.1458F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			bone5.setTextureOffset(482, 19).addBox(-0.7374F, -37.3276F, -18.2515F, 4.0F, 4.0F, 10.0F, 0.0F, false);
			bone5.setTextureOffset(397, 109).addBox(0.3153F, -12.181F, 14.1458F, 4.0F, 10.0F, 4.0F, 0.0F, false);
			bone5.setTextureOffset(397, 19).addBox(0.2626F, -36.3276F, -21.2515F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			bone4 = new ModelRenderer(this);
			bone4.setRotationPoint(16.0F, 22.0F, -18.0F);
			setRotationAngle(bone4, 0.0F, -0.7854F, 0.0F);
			bone4.setTextureOffset(189, 207).addBox(-1.3F, -1.0F, 48.7279F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone4.setTextureOffset(189, 207).addBox(-1.3F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone4.setTextureOffset(189, 207).addBox(-1.3F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone4.setTextureOffset(484, 96).addBox(-2.3F, 1.0F, 48.7279F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone4.setTextureOffset(251, 360).addBox(-2.3F, 1.0F, -5.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone4.setTextureOffset(251, 360).addBox(-2.3F, 1.0F, -5.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone4.setTextureOffset(251, 339).addBox(0.7F, -2.0F, 48.7279F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone4.setTextureOffset(251, 339).addBox(0.7F, -2.0F, -6.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone4.setTextureOffset(251, 339).addBox(0.7F, -2.0F, -6.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone6 = new ModelRenderer(this);
			bone6.setRotationPoint(-24.0864F, 1.0F, 24.1177F);
			bone4.addChild(bone6);
			setRotationAngle(bone6, 0.0F, -1.6144F, 0.0F);
			bone6.setTextureOffset(189, 207).addBox(-2.6695F, -2.0F, -1.8456F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone6.setTextureOffset(189, 207).addBox(-2.6695F, -2.0F, -1.8456F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone6.setTextureOffset(251, 339).addBox(-0.6695F, -3.0F, -1.8456F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone6.setTextureOffset(251, 339).addBox(-0.6695F, -3.0F, -1.8456F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone6.setTextureOffset(484, 96).addBox(-3.6695F, 0.0F, -1.8456F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone6.setTextureOffset(484, 96).addBox(-3.6695F, 0.0F, -1.8456F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone7 = new ModelRenderer(this);
			bone7.setRotationPoint(29.7421F, 1.0F, 24.5893F);
			bone4.addChild(bone7);
			setRotationAngle(bone7, 0.0F, 1.5272F, 0.0F);
			bone7.setTextureOffset(484, 96).addBox(-3.7578F, 0.0F, -3.3172F, 7.0F, 1.0F, 6.0F, 0.0F, false);
			bone7.setTextureOffset(189, 207).addBox(-2.7578F, -2.0F, -3.3172F, 5.0F, 2.0F, 4.0F, 0.0F, false);
			bone7.setTextureOffset(251, 339).addBox(-0.7578F, -3.0F, -3.3172F, 1.0F, 4.0F, 7.0F, 0.0F, false);
			bone2 = new ModelRenderer(this);
			bone2.setRotationPoint(-0.5F, 0.5F, -3.5F);
			bone2.setTextureOffset(217, 137).addBox(-4.0F, -16.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(5.0F, -16.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(3.0F, -15.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(2.0F, -12.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(0.0F, -11.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(-1.0F, -11.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(-2.0F, -11.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(217, 137).addBox(1.0F, -11.5F, -11.0F, 1.0F, 12.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(1.0F, -12.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(0.0F, -12.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(-1.0F, -12.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(-2.0F, -12.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(-4.0F, -17.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(5.0F, -17.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(3.0F, -16.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bone2.setTextureOffset(448, 268).addBox(2.0F, -13.0F, -11.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(335, 205).addBox(-9.0F, -15.0F, -9.0F, 18.0F, 4.0F, 18.0F, 0.0F, false);
			bb_main.setTextureOffset(422, 213).addBox(-5.0F, -17.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
			bb_main.setTextureOffset(391, 58).addBox(-3.0F, -19.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
			bb_main.setTextureOffset(326, 96).addBox(-8.0F, -33.0F, -13.0F, 16.0F, 14.0F, 16.0F, 0.0F, false);
			bb_main.setTextureOffset(217, 137).addBox(-10.0F, -35.0F, -10.0F, 20.0F, 14.0F, 2.0F, 0.0F, false);
			bb_main.setTextureOffset(395, 262).addBox(-5.0F, -25.0F, 3.0F, 10.0F, 5.0F, 5.0F, 0.0F, false);
			bb_main.setTextureOffset(395, 262).addBox(-5.0F, -25.0F, 8.0F, 10.0F, 5.0F, 5.0F, 0.0F, false);
			bb_main.setTextureOffset(422, 20).addBox(-12.0F, -36.0F, 12.0F, 24.0F, 17.0F, 2.0F, 0.0F, false);
			bb_main.setTextureOffset(422, 20).addBox(-12.0F, -36.0F, 8.0F, 24.0F, 17.0F, 2.0F, 0.0F, false);
			bb_main.setTextureOffset(217, 137).addBox(-5.0F, -29.0F, -15.0F, 11.0F, 7.0F, 2.0F, 0.0F, false);
			bb_main.setTextureOffset(391, 58).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
			bb_main.setTextureOffset(392, 20).addBox(-4.0F, -10.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
			bb_main.setTextureOffset(397, 18).addBox(3.0F, -10.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
			bb_main.setTextureOffset(397, 18).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(397, 18).addBox(-4.0F, -10.0F, 3.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(375, 20).addBox(-4.0F, -9.0F, 4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
			bb_main.setTextureOffset(378, 20).addBox(-5.0F, -9.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
			bb_main.setTextureOffset(376, 20).addBox(4.0F, -9.0F, -4.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
			bb_main.setTextureOffset(384, 20).addBox(-4.0F, -9.0F, -5.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			bone.render(matrixStack, buffer, packedLight, packedOverlay);
			bone4.render(matrixStack, buffer, packedLight, packedOverlay);
			bone2.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
		}
	}
}
