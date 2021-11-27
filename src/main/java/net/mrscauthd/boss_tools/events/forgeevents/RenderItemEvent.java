package net.mrscauthd.boss_tools.events.forgeevents;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderItemEvent extends Event {
    private LivingEntity entity;
    private HandSide handSide;
    private ItemStack heldItem;
    private final ItemCameraTransforms.TransformType transformType;
    private final MatrixStack matrixStack;
    private final IRenderTypeBuffer renderTypeBuffer;
    private final int light;

    public RenderItemEvent(LivingEntity entity, HandSide handSide, ItemStack heldItem, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
    {
        this.entity = entity;
        this.handSide = handSide;
        this.heldItem = heldItem;
        this.transformType = transformType;
        this.matrixStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.light = light;
    }

    public LivingEntity getEntity()
    {
        return this.entity;
    }

    public HandSide getHandSide()
    {
        return this.handSide;
    }

    public ItemStack getItem()
    {
        return this.heldItem;
    }

    public ItemCameraTransforms.TransformType getTransformType()
    {
        return this.transformType;
    }

    public MatrixStack getMatrixStack()
    {
        return this.matrixStack;
    }

    public IRenderTypeBuffer getRenderTypeBuffer()
    {
        return this.renderTypeBuffer;
    }

    public int getLight()
    {
        return this.light;
    }

    @Cancelable
    public static class Pre extends RenderItemEvent
    {
        public Pre(LivingEntity entity, HandSide handSide, ItemStack heldItem, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
        {
            super(entity, handSide, heldItem, transformType, matrixStack, renderTypeBuffer, light);
        }
    }

    public static class Post extends RenderItemEvent
    {
        public Post(LivingEntity entity, HandSide handSide, ItemStack heldItem, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
        {
            super(entity, handSide, heldItem, transformType, matrixStack, renderTypeBuffer, light);
        }
    }
}
