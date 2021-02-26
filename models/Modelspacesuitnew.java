// Made with Blockbench 3.6.0
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelspacesuitnew extends EntityModel<Entity> {
	private final ModelRenderer kopf;
	private final ModelRenderer Body;
	private final ModelRenderer armr;
	private final ModelRenderer arml;

	public Modelspacesuitnew() {
		textureWidth = 64;
		textureHeight = 32;

		kopf = new ModelRenderer(this);
		kopf.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(kopf, -0.0175F, 0.0873F, 0.0F);
		kopf.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		kopf.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-1.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(2.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-1.5F, 3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(2.5F, 3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 5.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 5.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 6.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 6.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 7.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 7.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-1.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(2.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-1.5F, 3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(2.5F, 3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 4.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 4.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 5.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 5.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 6.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 6.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 7.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 7.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-1.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(2.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-1.5F, 3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(2.5F, 3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-1.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(2.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-2.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(1.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-2.5F, 3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 5.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 6.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 7.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-2.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(1.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-2.5F, 3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 4.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 5.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 6.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 7.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-2.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(1.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-2.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(1.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, 3.0F, 5.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, 3.0F, 5.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, 2.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, 2.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, 1.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, 1.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, 0.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, 0.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, -1.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, -1.0F, 6.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, -3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, -3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, -3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, -3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(-2.5F, -3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 2).addBox(1.5F, -3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-2.5F, 3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-2.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(1.5F, 8.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-3.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(0.5F, 2.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-3.5F, 3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(0.5F, 3.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 5.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 5.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 6.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 6.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 7.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 7.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 8.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-3.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(0.5F, 2.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-3.5F, 3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(0.5F, 3.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 4.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 4.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 5.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 5.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 6.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 6.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 7.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 7.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 8.0F, 3.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(-3.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 0).addBox(0.5F, 2.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(-3.5F, 3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(56, 0).addBox(0.5F, 3.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 4.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 5.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 6.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(-3.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(60, 2).addBox(0.5F, 7.0F, 4.2F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		armr = new ModelRenderer(this);
		armr.setRotationPoint(-5.0F, 2.0F, 0.0F);
		armr.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		armr.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		arml = new ModelRenderer(this);
		arml.setRotationPoint(5.0F, 2.0F, 0.0F);
		arml.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		arml.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		kopf.render(matrixStack, buffer, packedLight, packedOverlay);
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		armr.render(matrixStack, buffer, packedLight, packedOverlay);
		arml.render(matrixStack, buffer, packedLight, packedOverlay);
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