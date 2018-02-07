import java.awt.Color;
import java.util.Random;


public class Grass extends Drawable {
	private PointTesselator tesselator;
	private float delta = 0.0f;
	public Grass(Scene<Drawable> scene) {
		super(scene,new Hitbox());//new P3D(-60,0,-60),new P3D(60,900,60));
		tesselator = new PointTesselator();
	}
	public void draw(int darkness) {
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		Random r2 = new Random(22);
		for (int i = 0; i < r2.nextInt(15)+10;i++) {
			drawStrand(new P3D(r2.nextInt(150)-75,0,r2.nextInt(150)-75),r2.nextInt(),darkness);
		}
	}
	
	private void drawStrand(P3D center,int seed,int darkness) {
		Rand r = new Rand(seed);
		float miny = getScene().getGamePlane().getPlayerLocation(pos.x, pos.z) + 20;
		//System.out.println(height);
		float height = (float)(r.nextDouble()*280)+10;
		float w = height/33.0f;
		float px = (float)(Math.sin(delta + (r.nextDouble()*Math.PI*2)) * (w*1.8));
		
		tesselator.color(genColor(r,darkness));
		tesselator.point(-w+center.x,miny,-w+center.z);
		tesselator.point(center.x+px,miny+height,center.z);
		tesselator.point(w+center.x,miny,-w+center.z);
		
		tesselator.color(genColor(r,darkness));
		tesselator.point(w+center.x,miny,-w+center.z);
		tesselator.point(center.x+px,miny+height,center.z);
		tesselator.point(w+center.x,miny,w+center.z);
		
		tesselator.color(genColor(r,darkness));
		tesselator.point(w+center.x,miny,w+center.z);
		tesselator.point(center.x+px,miny+height,center.z);
		tesselator.point(-w+center.x,miny,w+center.z);
		
		tesselator.color(genColor(r,darkness));
		tesselator.point(-w+center.x,miny,w+center.z);
		tesselator.point(center.x+px,miny+height,center.z);
		tesselator.point(-w+center.x,miny,-w+center.z);
	}
	
	private Color genColor(Rand r, int darkness) {
		//return new Color(MathCalculator.colorLock(80+r.nextInt(25)-darkness),
			//	MathCalculator.colorLock(160+r.nextInt(25)-darkness),
				//MathCalculator.colorLock(r.nextInt(25)-darkness));
		return Utility.adjustBrightness(r.variate(new Color(80,160,0), 25), -darkness);
	}

	public void tick() {
		delta += 0.02f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}