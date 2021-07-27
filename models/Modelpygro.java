// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public static class Modelpygro extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer bipedHead_r1;
	private final ModelRenderer fang;
	private final ModelRenderer cube_r1;
	private final ModelRenderer fang2;
	private final ModelRenderer cube_r2;
	private final ModelRenderer ear2;
	private final ModelRenderer ear;
	private final ModelRenderer body;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;

	public Modelpygro() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, 0.0F, false);

		bipedHead_r1 = new ModelRenderer(this);
		bipedHead_r1.setRotationPoint(0.0F, -7.5F, -4.0F);
		head.addChild(bipedHead_r1);
		setRotationAngle(bipedHead_r1, 0.3054F, 0.0F, 0.0F);
		bipedHead_r1.setTextureOffset(44, 26).addBox(-4.5F, -4.5F, -0.75F, 9.0F, 7.0F, 0.0F, 0.0F, false);

		fang = new ModelRenderer(this);
		fang.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.addChild(fang);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-2.0F, -25.0F, -4.5F);
		fang.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0631F, 0.3435F, 0.1855F);
		cube_r1.setTextureOffset(43, 0).addBox(-1.25F, -1.35F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		fang2 = new ModelRenderer(this);
		fang2.setRotationPoint(4.5F, 24.0F, 0.0F);
		head.addChild(fang2);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-2.0F, -25.0F, -4.5F);
		fang2.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0631F, -0.3435F, -0.1855F);
		cube_r2.setTextureOffset(28, 0).addBox(-2.15F, -1.45F, -0.35F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		ear2 = new ModelRenderer(this);
		ear2.setRotationPoint(-5.5F, -6.5F, 0.0F);
		head.addChild(ear2);
		setRotationAngle(ear2, 0.0F, 0.0F, 0.1309F);
		ear2.setTextureOffset(48, 5).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 6.0F, 5.0F, 0.0F, false);

		ear = new ModelRenderer(this);
		ear.setRotationPoint(5.5F, -6.5F, 0.0F);
		head.addChild(ear);
		setRotationAngle(ear, 0.0F, 0.0F, -0.1309F);
		ear.setTextureOffset(36, 5).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 6.0F, 5.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-4.0F, 0.0F, 0.0F);
		RightArm.setTextureOffset(32, 32).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(4.0F, 0.0F, 0.0F);
		LeftArm.setTextureOffset(16, 32).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		RightLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		LeftLeg.setTextureOffset(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
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