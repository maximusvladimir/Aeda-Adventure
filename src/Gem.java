import java.awt.Color;


public class Gem extends Drawable {
	private PointTesselator tesselator;
	private float spinOffset;
	private float dark = 0.0f;
	public Gem(Scene<Drawable> scene, Rand rand) {
		super(scene,new P3D(-50,-40,-25),new P3D(50,40,25));
		tesselator = new PointTesselator();
		spinOffset = (float)(rand.nextDouble() * Math.PI *2);
		dark = (float)(rand.nextDouble() * Math.PI *2);
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
	
	public void tick() {
		time1 += 0.01f;
		delta += 0.01f;
		dark += 0.05f;
	}
	private float time1 = 0.0f;
	private float delta = 0.0f;
	public void draw(int darkness) {
		if (!isVisible())
			return;
		darkness = (int)(Math.sin(dark) * 40);
		tesselator.setBackgroundColor(Color.black);
		tesselator.translate(pos.x,pos.y+(float)(Math.sin(time1)*20),pos.z,false);
		tesselator.rotate(0,delta+spinOffset,0);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.color(0,153-darkness,51-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		
		tesselator.color(0,143-darkness,41-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(0,163-darkness,61-darkness);
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(0,200-darkness,40-darkness);
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,40,0);

		tesselator.color(0,130-darkness,50-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		
		tesselator.color(0,100-darkness,10-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(0,150-darkness,40-darkness);
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(0,175-darkness,70-darkness);
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,-40,0);
	}	
}
