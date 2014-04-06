import java.awt.Color;


public class Water extends Drawable {
	private PointTesselator tesselator;
	private int size;
	public Water(Scene<Drawable> s, int size) {
		super(s,new Hitbox());
		this.size = size;
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	
	public float getSequence() {
		return sq;
	}
	
	public float getSize() {
		return size;
	}
	private float sq = 400;
	private float delta = 0;
	private float period = 1.5f;
	public void draw(int darkness) {
		// TODO special condition when in water (transparency).
		tesselator.translate(pos.x-(sq*size*0.5f),pos.y,pos.z-(sq*size*0.5f),false);
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				float he0 = (float)((Math.cos(period*(x+delta))*50));
				float he1 = (float)((Math.cos(period*(x+1+delta))*50));
				//tesselator.color(49,56,128);
				float avg = (he0 + he1)/150;
				tesselator.color(MathCalculator.lerp(new Color(49,56,128), new Color(160,180,210), avg));
				tesselator.point((x)*sq,he0,(z)*sq);
				tesselator.point((x+1)*sq,he1,(z)*sq);
				tesselator.point((x+1)*sq,he1,(z+1)*sq);
				avg = (he1 + he0 + he0)/150;
				tesselator.color(MathCalculator.lerp(new Color(49,56,128), new Color(160,180,210), avg));
				tesselator.point((x)*sq,he0,(z)*sq);
				tesselator.point((x)*sq,he0,(z+1)*sq);
				tesselator.point((x+1)*sq,he1,(z+1)*sq);
			}
		}
	}
	
	public boolean isCullable() {
		return false;
	}

	public void tick() {
		delta -= 0.02f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
