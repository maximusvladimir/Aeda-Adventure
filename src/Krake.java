import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Krake extends Enemy {
	private PointTesselator tesselator;
	private static P3D[] cache = null;
	private static P3D[] headBase = null;
	private static float xEye = 0;
	private static float zEye = 0;
	public Krake(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		
		setSpeed(13);
		setHealthLoss(-0.02222222222222222222222f);
		setVisiblilityDistance(3000);
		
		if (cache == null) {
			cache = genBody();
			headBase = genHeadBase();
		}
	}
	
	public void createPic() {
		enemyImage = new BufferedImage(9,15,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D)enemyImage.getGraphics();
		g.translate(0,2);
		g.setColor(new Color(47,100,71));
		g.drawLine(2,2,2,7);
		g.drawLine(3,1,3,7);
		g.drawLine(4,0,4,7);
		g.drawLine(5,1,5,7);
		g.drawLine(6,2,6,7);
		g.setColor(new Color(31,67,47));
		g.drawLine(1, 8, 7, 8);
		g.drawLine(0,9,8,9);
		g.drawLine(0, 10, 0, 10);
		g.drawLine(8,10,8,10);
		g.setColor(new Color(73,156,111));
		g.drawLine(3, 9, 3, 10);
		g.drawLine(4,8,4,11);
		g.drawLine(5,9,5,10);
		g.setColor(Color.white);
		g.fillRect(3,3,3,3);
		g.setColor(Color.black);
		g.drawLine(4,4,4,4);
	}
	
	private float deltaArms = 0;
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0, -delta - MathCalculator.PIOVER2, 0);
		//tesselator.rotate(MathCalculator.PIOVER2,0,0);
		//tesselator.rotate(0,MathCalculator.PIOVER2*0.5f,0);
		
		Color skin = Utility.adjustBrightness(new Color(66,98,82),-darkness);
		Color eye = Utility.adjustBrightness(Color.white,-darkness);
		Rand var = new Rand(493578029);
		
		for (int i = 0; i < cache.length / 3; i++) {
			tesselator.color(var.bright(skin, 20));
			P3D other = new P3D(cache[i * 3]);
			float armWave = (float)(Math.sin(deltaArms + other.x) * 20);
			other.y += armWave;
			tesselator.point(other);
			tesselator.point(cache[i * 3 + 1]);
			tesselator.point(cache[i * 3 + 2]);
		}
		
		for (int i = 0; i < headBase.length / 3; i++) {
			tesselator.color(var.bright(skin, 20));
			tesselator.point(headBase[i * 3]);
			tesselator.point(headBase[i * 3 + 1]);
			tesselator.point(headBase[i * 3 + 2]);
		}
		
		tesselator.color(Utility.adjustBrightness(new Color(200,200,200), (-darkness)));
		tesselator.point(xEye-30, 100, zEye+5);
		tesselator.point(xEye+30, 100, zEye+5);
		tesselator.point(xEye, 125, zEye+15);
		tesselator.color(eye);
		tesselator.point(xEye-30, 150, zEye+5);
		tesselator.point(xEye+30, 150, zEye+5);
		tesselator.point(xEye, 125, zEye+15);
		tesselator.color(Utility.adjustBrightness(new Color(240,240,240), (-darkness)));
		tesselator.point(xEye, 125, zEye+15);
		tesselator.point(xEye+30, 150, zEye+5);
		tesselator.point(xEye+30, 100, zEye+5);
		//tesselator.color(Utility.adjustBrightness(new Color(230,230,230), (-darkness)));
		tesselator.point(xEye, 125, zEye+15);
		tesselator.point(xEye-30, 150, zEye+5);
		tesselator.point(xEye-30, 100, zEye+5);
		
		
		tesselator.color(Color.black);
		tesselator.point(xEye-10, 115, zEye+15);
		tesselator.point(xEye+10, 115, zEye+15);
		tesselator.point(xEye, 125, zEye+30);
		tesselator.point(xEye-10, 135, zEye+15);
		tesselator.point(xEye+10, 135, zEye+15);
		tesselator.point(xEye, 125, zEye+30);
		tesselator.point(xEye, 125, zEye+30);
		tesselator.point(xEye+10, 135, zEye+15);
		tesselator.point(xEye+10, 115, zEye+15);
		//tesselator.color(Utility.adjustBrightness(new Color(230,230,230), (-darkness)));
		tesselator.point(xEye, 125, zEye+30);
		tesselator.point(xEye-10, 135, zEye+15);
		tesselator.point(xEye-10, 115, zEye+15);
	}
	
	private static P3D[] genHeadBase() {
		ArrayList<P3D> points = new ArrayList<P3D>();
		int sides = 8;
		float radius = 150;
		float t = MathCalculator.TWOPI / sides;
		for (int i = 0; i < sides; i++) {
			float cx = (float)(Math.cos(t * i) * radius);
			float cz = (float)(Math.sin(t * i) * radius);
			float px = (float)(Math.cos(t * (i + 1)) * radius);
			float pz = (float)(Math.sin(t * (i + 1)) * radius);
			points.add(new P3D(cx * 0.5f, 50, cz * 0.5f));
			points.add(new P3D(cx, 50, cz));
			points.add(new P3D(px, 50, pz));
			
			points.add(new P3D(cx * 0.5f, 50, cz * 0.5f));
			points.add(new P3D(px * 0.5f, 50, pz * 0.5f));
			points.add(new P3D(px, 50, pz));
			
			points.add(new P3D(cx * 0.5f, 50, cz * 0.5f));
			points.add(new P3D(px * 0.5f, 50, pz * 0.5f));
			points.add(new P3D(px * 0.4f, 170, pz * 0.4f));
			points.add(new P3D(cx * 0.5f, 50, cz * 0.5f));
			points.add(new P3D(cx * 0.4f, 170, cz * 0.4f));
			points.add(new P3D(px * 0.4f, 170, pz * 0.4f));
			
			points.add(new P3D(cx * 0.4f, 170, cz * 0.4f));
			points.add(new P3D(px * 0.4f, 170, pz * 0.4f));
			points.add(new P3D(0, 190, 0));
			if (i == 2) {
				xEye = cx * 0.45f;
				zEye = cz * 0.45f;
			}
		}
		P3D[] res = new P3D[points.size()];
		res = points.toArray(res);
		return res;
	}
	
	private static P3D[] genBody() {
		ArrayList<P3D> points = new ArrayList<P3D>();
		int sides = 8;
		float radius = 150;
		float t = MathCalculator.TWOPI / sides;
		for (int i = 0; i < sides; i++) {
			float cx = (float)(Math.cos(t * i) * radius);
			float cz = (float)(Math.sin(t * i) * radius);
			float mx = (float)(Math.cos(t * (i + 0.5f)) * radius);
			float mz = (float)(Math.sin(t * (i + 0.5f)) * radius);
			float px = (float)(Math.cos(t * (i + 1)) * radius);
			float pz = (float)(Math.sin(t * (i + 1)) * radius);
			points.add(new P3D(mx * 1.6f, -70, mz * 1.6f));
			points.add(new P3D(cx, 50, cz));
			points.add(new P3D(px, 50, pz));
			
			points.add(new P3D(mx * 1.6f, -70, mz * 1.6f));
			points.add(new P3D(px, 0, pz));
			points.add(new P3D(px, 50, pz));
			
			points.add(new P3D(mx * 1.6f, -70, mz * 1.6f));
			points.add(new P3D(cx, 0, cz));
			points.add(new P3D(cx, 50, cz));
		}
		P3D[] res = new P3D[points.size()];
		res = points.toArray(res);
		return res;
	}

	public void tick() {
		deltaArms += 0.02f;
		if (!(getScene().getScreen() instanceof IWaterLevel) || !((IWaterLevel)getScene().getScreen()).inDeepWater())
			return;
		super.tick();
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}