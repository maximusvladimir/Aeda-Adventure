// This is a dummy class for drawables- copying this code and pasting it in a file
// to create a new drawable is easier (hopefully) thanks to it.

public class BaseDrawable extends Drawable {
	private PointTesselator tesselator;
	public BaseDrawable(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}