
public class Enemy extends Drawable {
	private PointTesselator tesselator;
	private boolean persuingPlayer = false;
	private float px = 0.0f;
	private float pz = 0.0f;
	public Enemy(Scene<Drawable> scene) {
		super(scene,new Hitbox(new P3D(-100,0,0),new P3D(100,100,0)));
		tesselator = new PointTesselator();
	}
	
	public Enemy getThis() {
		return this;
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
		if (new P3D(0,-100,-500).dist(new P3D(-x+getInstanceLoc().x,-200,-z+getInstanceLoc().z)) <= 1000) {
			persuingPlayer = true;
		}
		else
			persuingPlayer = false;
	}

	public void tick() {
		if (persuingPlayer) {
			float angle = (float)Math.atan2((-pz+getInstanceLoc().z) - 150, (-px+getInstanceLoc().x));
			getInstanceLoc().x -= 5 * Math.cos(angle);
			getInstanceLoc().z -= 5 * Math.sin(angle);
			// TODO see if legal.
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
