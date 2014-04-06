
public class Enemy extends Drawable {
	private PointTesselator tesselator;
	private boolean persuingPlayer = false;
	private float px = 0.0f;
	private float pz = 0.0f;
	public Enemy() {
		super(new P3D(-100,0,0),new P3D(100,100,0));
		tesselator =new PointTesselator();
	}
	
	public void draw(int darkness) {
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		if (persuingPlayer)
			tesselator.color(255,0,0);
		else
			tesselator.color(0,200,160);
		tesselator.point(-100,0,0);
		tesselator.point(100,0,0);
		tesselator.point(0,100,0);
	}
	
	public void findPlayer(float x, float z) {
		px = x;
		pz = z;
		if (new P3D(0,-100,-500).dist(new P3D(-x+staticPos.x,-200,-z+staticPos.z)) <= 1000) {
			persuingPlayer = true;
		}
		else
			persuingPlayer = false;
	}

	public void tick() {
		if (persuingPlayer) {
			float angle = (float)Math.atan2((-pz+staticPos.z) - 150, (-px+staticPos.x));
			staticPos.x -= 5 * Math.cos(angle);
			staticPos.z -= 5 * Math.sin(angle);
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
