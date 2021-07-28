// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public static class Modelmoglerium extends EntityModel<Entity> {
	private final ModelRenderer body;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer head;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer jaw2;
	private final ModelRenderer cube_r8;
	private final ModelRenderer jaw1;
	private final ModelRenderer cube_r9;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;
	private final ModelRenderer leg4;

	public Modelmoglerium() {
		textureWidth = 256;
		textureHeight = 256;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-8.0F, -15.0F, -13.0F, 16.0F, 9.0F, 32.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.2182F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(85, 30).addBox(-9.0F, -20.0F, 12.0F, 18.0F, 9.0F, 11.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 41).addBox(-10.0F, -18.0F, 5.0F, 20.0F, 9.0F, 16.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3927F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(58, 52).addBox(-11.0F, -18.0F, 2.0F, 22.0F, 9.0F, 14.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.5236F, 0.0F, 0.0F);
		cube_r4.setTextureOffset(66, 2).addBox(-12.0F, -22.0F, -4.0F, 24.0F, 11.0F, 11.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(1.0F, 9.0F, -8.0F);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 13.0F, 8.0F);
		head.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.829F, 0.0F, 0.0F);
		cube_r5.setTextureOffset(0, 66).addBox(-10.0F, -26.0F, -3.0F, 20.0F, 11.0F, 11.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 1.5F, -12.0F);
		head.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.0F, 0.0F);
		cube_r6.setTextureOffset(0, 22).addBox(-7.5F, -8.5F, -1.75F, 15.0F, 8.0F, 0.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0.0F, 1.5F, -12.0F);
		head.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.6545F, 0.0F, 0.0F);
		cube_r7.setTextureOffset(62, 75).addBox(-9.0F, -8.5F, -1.75F, 18.0F, 13.0F, 11.0F, 0.0F, false);

		jaw2 = new ModelRenderer(this);
		jaw2.setRotationPoint(0.0F, 1.5F, -12.0F);
		head.addChild(jaw2);
		setRotationAngle(jaw2, -0.6545F, 0.0F, 0.0F);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(0.0F, 0.0F, 0.0F);
		jaw2.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, 0.5672F);
		cube_r8.setTextureOffset(96, 99).addBox(5.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, 0.0F, false);

		jaw1 = new ModelRenderer(this);
		jaw1.setRotationPoint(0.0F, 1.5F, -12.0F);
		head.addChild(jaw1);
		setRotationAngle(jaw1, -0.6545F, 0.0F, 0.0F);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(0.0F, 0.0F, 0.0F);
		jaw1.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 0.0F, -0.5672F);
		cube_r9.setTextureOffset(0, 110).addBox(-10.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-9.5F, 11.5F, -6.5F);
		leg1.setTextureOffset(64, 99).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(9.5F, 11.5F, -6.5F);
		leg2.setTextureOffset(32, 90).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(9.5F, 11.5F, 12.5F);
		leg3.setTextureOffset(0, 88).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(-9.5F, 11.5F, 12.5F);
		leg4.setTextureOffset(0, 0).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay);
		leg4.render(matrixStack, buffer, packedLight, packedOverlay);
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