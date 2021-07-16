
package net.mrscauthd.boss_tools;

import net.mrscauthd.boss_tools.item.OxygenMaskItem;
import net.mrscauthd.boss_tools.entity.renderer.OxygenGearModel;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.nbt.ListNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.Maps;

@OnlyIn(Dist.CLIENT) // SpaceSuitRenderer
public class SpaceSuitRenderer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("boss_tools:textures/spacesuitmode__layer_1_head.png");
	private final OxygenGearModel<T> modelElytra = new OxygenGearModel<>(1);
	private static final HashMap<String, ResourceLocation> TEXTURES = Maps.newHashMap();
	public LivingEntity entity;
	// private EquipmentSlotType slot;
	public SpaceSuitRenderer(IEntityRenderer<T, M> rendererIn) {
		super(rendererIn);
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing,
			float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
		// don't render enchant if only enchant is from set effect
		ItemStack stack = itemstack;
		boolean renderEnchant = false;
		if (stack != null && stack.isEnchanted()) {
			ListNBT enchantNbt = stack.getEnchantmentTagList();
			for (int i = 0; i < enchantNbt.size(); ++i)
				if (!enchantNbt.getCompound(i).getBoolean("boss_tools" + " enchant")) {
					renderEnchant = true;
					break;
				}
		}
		// use different vertex builder to allow translucency
		if (stack != null) {
			// get texture location
			String str = TEXTURE_ELYTRA.toString();
			ResourceLocation loc = TEXTURES.get(str);
			if (loc == null) {
				loc = new ResourceLocation(str);
				TEXTURES.put(str, loc);
			}
			// normal render - use translucent vertex
			if (shouldRender(itemstack, entitylivingbaseIn)) {
				ResourceLocation resourcelocation;
				if (entitylivingbaseIn instanceof AbstractClientPlayerEntity) {
					AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) entitylivingbaseIn;
					if (abstractclientplayerentity.isPlayerInfoSet() && abstractclientplayerentity.getLocationElytra() != null) {
						resourcelocation = abstractclientplayerentity.getLocationElytra();
					} else if (abstractclientplayerentity.hasPlayerInfo() && abstractclientplayerentity.getLocationCape() != null
							&& abstractclientplayerentity.isWearing(PlayerModelPart.CAPE)) {
						resourcelocation = abstractclientplayerentity.getLocationElytra();
					} else {
						resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
					}
				} else {
					resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
				}
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 0.0D, 0.125D);
				this.getEntityModel().copyModelAttributesTo(this.modelElytra);
				this.modelElytra.bipedHead.setRotationPoint(0,0,0);
				this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(resourcelocation), false,
						itemstack.hasEffect());
				ivertexbuilder = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getEntityTranslucent(loc));
				this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
			// bufferIn = (IRenderTypeBuffer)
			// Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getEntityTranslucent(loc));
			// this.actualRender(matrix, vertex, false, lightMapUV, overlayUV, ageInTicks,
			// netHeadYaw, headPitch, scale);
			// if enchanted - use entity glint vertex on top
			if (renderEnchant) {
				// System.out.println("tset");
				if (shouldRender(itemstack, entitylivingbaseIn)) {
					ResourceLocation resourcelocation;
					if (entitylivingbaseIn instanceof AbstractClientPlayerEntity) {
						AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) entitylivingbaseIn;
						if (abstractclientplayerentity.isPlayerInfoSet() && abstractclientplayerentity.getLocationElytra() != null) {
							resourcelocation = abstractclientplayerentity.getLocationElytra();
						} else if (abstractclientplayerentity.hasPlayerInfo() && abstractclientplayerentity.getLocationCape() != null
								&& abstractclientplayerentity.isWearing(PlayerModelPart.CAPE)) {
							resourcelocation = abstractclientplayerentity.getLocationElytra();
						} else {
							resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
						}
					} else {
						resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
					}
					matrixStackIn.push();
					matrixStackIn.translate(0.0D, 0.0D, 0.125D);
					this.getEntityModel().copyModelAttributesTo(this.modelElytra);
					this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
					IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(resourcelocation),
							false, itemstack.hasEffect());
					ivertexbuilder = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getEntityGlintDirect());
					this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
					matrixStackIn.pop();
				}
				// bufferIn = (IRenderTypeBuffer)
				// Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getEntityGlintDirect());
				// this.actualRender(matrix, vertex, false, lightMapUV, overlayUV, ageInTicks,
				// netHeadYaw, headPitch, scale);
			}
		}
	}

	/**
	 * Determines if the ElytraLayer should render. ItemStack and Entity are
	 * provided for modder convenience, For example, using the same ElytraLayer for
	 * multiple custom Elytra.
	 *
	 * @param stack
	 *            The Elytra ItemStack
	 * @param entity
	 *            The entity being rendered.
	 * @return If the ElytraLayer should render.
	 */
	public boolean shouldRender(ItemStack stack, T entity) {
		return stack.getItem() == OxygenMaskItem.block;
	}

	/**
	 * Gets the texture to use with this ElytraLayer. This assumes the vanilla
	 * Elytra model.
	 *
	 * @param stack
	 *            The Elytra ItemStack.
	 * @param entity
	 *            The entity being rendered.
	 * @return The texture.
	 */
	public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
		return TEXTURE_ELYTRA;
	}
}
