// Made with Blockbench 3.8.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelrover extends EntityModel<Entity> {
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer wellback1;
	private final ModelRenderer wellfront1;
	private final ModelRenderer wellfront2;
	private final ModelRenderer wellback2;
	private final ModelRenderer bb_main;

	public Modelrover() {
		textureWidth = 128;
		textureHeight = 128;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-7.0F, 18.0F, 8.0F);
		setRotationAngle(bone, -0.2618F, 0.0F, 0.0F);
		bone.setTextureOffset(32, 0).addBox(-3.0F, -21.2187F, -5.3173F, 20.0F, 24.0F, 2.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(-7.0F, 7.0F, 11.0F);
		setRotationAngle(bone2, -0.0873F, 0.0F, 0.0F);
		bone2.setTextureOffset(100, 34).addBox(1.0F, -18.3593F, -3.5791F, 12.0F, 8.0F, 2.0F, 0.0F, false);

		wellback1 = new ModelRenderer(this);
		wellback1.setRotationPoint(21.0F, 19.0F, 18.0F);
		wellback1.setTextureOffset(50, 28).addBox(-3.0F, -5.0F, -5.0F, 6.0F, 10.0F, 10.0F, 0.0F, false);

		wellfront1 = new ModelRenderer(this);
		wellfront1.setRotationPoint(21.0F, 19.0F, -44.0F);
		wellfront1.setTextureOffset(50, 28).addBox(-3.0F, -5.0F, -5.0F, 6.0F, 10.0F, 10.0F, 0.0F, false);

		wellfront2 = new ModelRenderer(this);
		wellfront2.setRotationPoint(-21.0F, 19.0F, -44.0F);
		wellfront2.setTextureOffset(50, 28).addBox(-3.0F, -5.0F, -5.0F, 6.0F, 10.0F, 10.0F, 0.0F, false);

		wellback2 = new ModelRenderer(this);
		wellback2.setRotationPoint(-21.0F, 19.0F, 18.0F);
		wellback2.setTextureOffset(50, 28).addBox(-3.0F, -5.0F, -5.0F, 6.0F, 10.0F, 10.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 76).addBox(-16.0F, -6.0F, -19.0F, 32.0F, 2.0F, 18.0F, 0.0F, false);
		bb_main.setTextureOffset(37, 84).addBox(-22.0F, -6.0F, -45.0F, 10.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(18, 77).addBox(-12.0F, -6.0F, -27.0F, 24.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(38, 80).addBox(12.0F, -6.0F, -45.0F, 10.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 32).addBox(-10.0F, -8.0F, -15.0F, 20.0F, 2.0F, 18.0F, 0.0F, false);
		bb_main.setTextureOffset(39, 83).addBox(12.0F, -6.0F, -21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(44, 84).addBox(-14.0F, -6.0F, -21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(44, 98).addBox(-14.0F, -22.0F, 11.0F, 28.0F, 16.0F, 14.0F, 0.0F, false);
		bb_main.setTextureOffset(56, 0).addBox(16.0F, -6.0F, 17.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(56, 0).addBox(-22.0F, -6.0F, 17.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 120).addBox(-8.0F, -10.0F, -29.0F, 18.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 106).addBox(-4.0F, -8.0F, -55.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 76).addBox(-16.0F, -6.0F, -1.0F, 32.0F, 2.0F, 18.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 60).addBox(-16.0F, -6.0F, 17.0F, 32.0F, 2.0F, 10.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 120).addBox(-8.0F, -10.0F, -33.0F, 18.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 120).addBox(-8.0F, -10.0F, -37.0F, 18.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 120).addBox(-8.0F, -10.0F, -41.0F, 18.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 120).addBox(-8.0F, -10.0F, -45.0F, 18.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(18, 79).addBox(-12.0F, -6.0F, -35.0F, 24.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(18, 80).addBox(-12.0F, -6.0F, -43.0F, 24.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(18, 80).addBox(-12.0F, -6.0F, -51.0F, 24.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 120).addBox(-10.0F, -10.0F, -29.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 120).addBox(-10.0F, -10.0F, -33.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(104, 20).addBox(-6.0F, -8.0F, -55.0F, 2.0F, 4.0F, 10.0F, 0.0F, false);
		bb_main.setTextureOffset(33, 82).addBox(16.0F, -14.0F, -45.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(34, 82).addBox(16.0F, -12.0F, -37.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(35, 82).addBox(16.0F, -6.0F, -43.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(33, 83).addBox(16.0F, -12.0F, -45.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 83).addBox(16.0F, -14.0F, 17.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(34, 86).addBox(16.0F, -12.0F, 25.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(36, 82).addBox(16.0F, -12.0F, 17.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(32, 80).addBox(16.0F, -6.0F, 19.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(33, 80).addBox(-24.0F, -14.0F, 17.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(29, 81).addBox(-24.0F, -12.0F, 25.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(33, 78).addBox(-18.0F, -12.0F, 17.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(30, 84).addBox(-18.0F, -6.0F, 19.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(28, 82).addBox(-24.0F, -12.0F, -37.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(23, 77).addBox(-24.0F, -14.0F, -45.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(33, 82).addBox(-18.0F, -12.0F, -45.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(39, 83).addBox(-18.0F, -6.0F, -43.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 120).addBox(-10.0F, -10.0F, -37.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 120).addBox(-10.0F, -10.0F, -41.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(22, 120).addBox(-10.0F, -10.0F, -45.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
		wellback1.render(matrixStack, buffer, packedLight, packedOverlay);
		wellfront1.render(matrixStack, buffer, packedLight, packedOverlay);
		wellfront2.render(matrixStack, buffer, packedLight, packedOverlay);
		wellback2.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
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