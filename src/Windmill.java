import java.awt.Color;


public class Windmill extends Drawable {
	private PointTesselator tesselator;
	
	public Windmill(Scene<Drawable> scene) {
		super(scene,buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		setInstanceLoc(new P3D(0,-300,400));
		//delta = (float)(Math.PI / 2);
	}
	
	private static Hitbox buildHitbox() {
		P3D[] ps = new P3D[2];
		ps[0] = new P3D(-600,0,-600);
		ps[1] = new P3D(600,433.333f,600);
		return new Hitbox(ps);
	}
	
	private float spinSpeed = 0.001f;
	private float spinDelta = 0.0f;
	private float delta = 0.0f;
	public void draw(int darkness) {
		tesselator.rotate(0, delta, 0);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		final int blades = 10;
		spinDelta += spinSpeed;
		float theta = (float)(Math.PI * 2 / blades);
		final float height = 1300.0f;
		Rand rand = new Rand(32);
		for (int i = 0; i < blades; i++) {
			if (i % 2 == 0)
				continue;
			final float rad = 600.0f;
			final float si = (float)(rand.nextDouble() * 0.25f) + 0.75f;
			float ax = (float)Math.cos(theta * i + spinDelta) * rad;
			float az = (float)Math.sin(theta * i + spinDelta) * rad;
			float nx = (float)Math.cos(theta * (i+si) + spinDelta) * rad;
			float nz = (float)Math.sin(theta * (i+si) + spinDelta) * rad;
			tesselator.color(Utility.adjustBrightness(new Color(200,195,190),rand.nextInt(-25,10)));
			tesselator.point(new P3D(0,height,100));
			tesselator.point(new P3D(ax,az+height,100));
			tesselator.point(new P3D(nx,nz+height,100));
		}
		Color step = new Color(90,90,85);
		Color roof = new Color(131,105,71);
		
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(-300,height/3,100);
		tesselator.point(-200,height,50);
		tesselator.point(0,height/3,100);	
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(0,height/3,100);
		tesselator.point(200,height,50);
		tesselator.point(300,height/3,100);	
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(200,height,50);
		tesselator.point(-200,height,50);
		tesselator.point(0,height/3,100);
		tesselator.color(Utility.adjustBrightness(roof,rand.nextInt(-20, 20)));
		tesselator.point(-300,height-50,100);
		tesselator.point(0,height+200,0);
		tesselator.point(300,height-50,100);
		
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(-300,height/3,-100);
		tesselator.point(-200,height,-50);
		tesselator.point(0,height/3,-100);	
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(0,height/3,-100);
		tesselator.point(200,height,-50);
		tesselator.point(300,height/3,-100);	
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(200,height,-50);
		tesselator.point(-200,height,-50);
		tesselator.point(0,height/3,-100);
		tesselator.color(Utility.adjustBrightness(roof,rand.nextInt(-20, 20)));
		tesselator.point(-300,height-50,-100);
		tesselator.point(0,height+200,0);
		tesselator.point(300,height-50,-100);
		
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(-300,height/3,-100);
		tesselator.point(-200,height,-100);
		tesselator.point(-300,height/3,100);
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(-300,height/3,100);
		tesselator.point(-200,height,100);
		tesselator.point(-200,height,-100);
		tesselator.color(Utility.adjustBrightness(roof,rand.nextInt(-20, 20)));
		tesselator.point(-300,height-50,-100);
		tesselator.point(0,height+200,0);
		tesselator.point(-300,height-50,100);
		
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(300,height/3,-100);
		tesselator.point(200,height,-100);
		tesselator.point(300,height/3,100);
		tesselator.color(Utility.adjustBrightness(step,rand.nextInt(-20, 20)));
		tesselator.point(300,height/3,100);
		tesselator.point(200,height,100);
		tesselator.point(200,height,-100);
		tesselator.color(Utility.adjustBrightness(roof,rand.nextInt(-20, 20)));
		tesselator.point(300,height-50,-100);
		tesselator.point(0,height+200,0);
		tesselator.point(300,height-50,100);
		
		int sides = 6;
		final float rad2 = 600.0f;
		theta = (float)(Math.PI * 2 / sides);
		for (int i = 0; i < sides; i++) {
			float rx = (float)(Math.cos(theta * i) * rad2);
			float rz = (float)(Math.sin(theta * i) * rad2);
			float nx = (float)(Math.cos(theta * (i+1)) * rad2);
			float nz = (float)(Math.sin(theta * (i+1)) * rad2);
			tesselator.color(Utility.adjustBrightness(Color.gray, rand.nextInt(-20,20)));
			tesselator.point(rx, height/3, rz);
			tesselator.point(nx, height/3, nz);
			tesselator.point(nx, 0, nz);
			
			tesselator.color(Utility.adjustBrightness(Color.gray, rand.nextInt(-20,20)));
			tesselator.point(nx,0,nz);
			tesselator.point(rx,0,rz);
			tesselator.point(rx,height/3,rz);
		}
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
