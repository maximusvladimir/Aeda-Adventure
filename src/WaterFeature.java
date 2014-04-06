
public class WaterFeature extends Drawable {
	private PointTesselator tesselator;
	private P3D[] controlPoints;
	private P3D center;
	public WaterFeature(P3D[] controlPoints) {
		super(null,null);
		tesselator = new PointTesselator();
		this.controlPoints = controlPoints;
		float avgx = 0.0f;
		float avgz = 0.0f;
		for (int i = 0; i < this.controlPoints.length; i++) {
			this.controlPoints[i].y = -330;
			avgx += this.controlPoints[i].x;
			avgz += this.controlPoints[i].z;
		}
		center = new P3D(avgx/controlPoints.length,-330,avgz/controlPoints.length);
	}
	
	public P3D[] getControlPoints() {
		return controlPoints;
	}
	
	public void draw(int darkness) {
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.color(49,56,128);
		for (int i = 0; i < this.controlPoints.length-1; i++) {
			tesselator.point(controlPoints[i]);
			tesselator.point(center);
			tesselator.point(controlPoints[i+1]);
		}
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
