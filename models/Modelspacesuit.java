// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

public static class Modelspacesuit extends EntityModel<Entity> {
	private final ModelRenderer kopf;
	private final ModelRenderer Body;
	private final ModelRenderer armr;
	private final ModelRenderer arml;
	private final ModelRenderer Left_Foot;
	private final ModelRenderer Right_Foot;

	public Modelspacesuit() {
		textureWidth = 64;
		textureHeight = 64;

		kopf = new ModelRenderer(this);
		kopf.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(kopf, -0.0175F, 0.0873F, 0.0F);
		kopf.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		kopf.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.setTextureOffset(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		Body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
		Body.setTextureOffset(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, 0.25F, false);
		Body.setTextureOffset(0, 55).addBox(-2.5F, 1.0F, 2.55F, 5.0F, 8.0F, 1.0F, 0.5F, false);

		armr = new ModelRenderer(this);
		armr.setRotationPoint(-5.0F, 2.0F, 0.0F);
		armr.setTextureOffset(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		arml = new ModelRenderer(this);
		arml.setRotationPoint(5.0F, 2.0F, 0.0F);
		arml.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		Left_Foot = new ModelRenderer(this);
		Left_Foot.setRotationPoint(2.0F, 12.0F, 0.0F);
		Left_Foot.setTextureOffset(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
		Left_Foot.setTextureOffset(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		Right_Foot = new ModelRenderer(this);
		Right_Foot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		Right_Foot.setTextureOffset(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
		Right_Foot.setTextureOffset(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		kopf.render(matrixStack, buffer, packedLight, packedOverlay);
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		armr.render(matrixStack, buffer, packedLight, packedOverlay);
		arml.render(matrixStack, buffer, packedLight, packedOverlay);
		Left_Foot.render(matrixStack, buffer, packedLight, packedOverlay);
		Right_Foot.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}