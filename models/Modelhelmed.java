// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

public static class Modelhelmed extends ModelBase {
	private final ModelRenderer bb_main;

	public Modelhelmed() {
		textureWidth = 16;
		textureHeight = 16;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(-1.0F, 24.0F, -2.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -5.72F, -8.86F, -3.72F, 2, 7, 11, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -5.72F, -2.0F, 1.86F, 2, 2, 5, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 5.44F, -2.0F, 1.86F, 2, 2, 5, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 5.44F, -8.86F, -3.72F, 2, 7, 11, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, 1.0F, -9.0F, 5.58F, 5, 9, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, -3.0F, -9.0F, 5.58F, 5, 9, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, -5.0F, -9.0F, 5.58F, 5, 9, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -9.44F, -3.72F, 10, 2, 11, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.42F, -11.3F, -2.86F, 9, 2, 8, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -7.58F, -3.72F, 10, 2, 1, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 5, 4, -0.14F, -5.86F, -3.72F, 2, 4, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
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