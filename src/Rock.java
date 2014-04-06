
public class Rock extends Drawable {
	public Rock(Rand rand) {
		super(null,null);
		tesselator = new PointTesselator();
		de = (float)(rand.nextDouble() * 2 * Math.PI);
	}
	float de;
	private PointTesselator tesselator;
	public void draw(int darkness) {
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0,de,0);
		
		tesselator.color(190,200,200);
		tesselator.point(-100,0,-40);
		tesselator.point(-60,0,30);
		tesselator.point(-80,20,0);
		
		tesselator.color(140,140,140);
		tesselator.point(-80,20,0);
		tesselator.point(-100,0,-40);
		tesselator.point(-75,0,-50);
		
		tesselator.color(130,130,150);
		tesselator.point(-75,0,-50);
		tesselator.point(-80,40,-30);
		tesselator.point(-10,0,40);
		
		tesselator.color(200,190,197);
		tesselator.point(-80,40,-30);
		tesselator.point(-80,20,0);
		tesselator.point(-75,0,-50);
		
		tesselator.color(160,160,157);
		tesselator.point(-10,0,40);
		tesselator.point(-80,40,-30);
		tesselator.point(-60,0,60);
		
		tesselator.color(170,170,160);
		tesselator.point(-60,0,60);
		tesselator.point(-80,40,-30);
		tesselator.point(-80,20,0);
		
		tesselator.color(190,190,197);
		tesselator.point(-80,20,0);
		tesselator.point(-60,0,30);
		tesselator.point(-60,0,60);
		//tesselator.point()
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
