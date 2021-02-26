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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}