import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Grandma extends Character {

	private PointTesselator tesselator;
	private Rand rand;

	public Grandma(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		rand = new Rand();
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		setMoveSpeed(0.5f);
	}
	
	public void uponArrival() {
		
	}
	
	public void setDelta(float d) {
		delta = d;
	}

	protected float delta = 0;
	private Rand ra;
	private static final P3D[] trigDressCache = genDressCache();
	private static final P3D[] trigHeadCache = genHeadCache();

	public void draw(int darkness) {
		tesselator.rotate(0, -delta - MathCalculator.PIOVER2, (float)(MathCalculator.sin(walkDelta)/10));
		// tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		/*Color oldLadySkin = Utility.adjustBrightness(new Color(195, 195, 195),
				-darkness);*/
		Color oldLadySkin = Utility.adjustBrightness(new Color(196,187,166),
				-darkness);
		Color oldLadyLeggings = Utility.adjustBrightness(new Color(200, 190,
				190), -darkness);
		Color oldLadyDress = Utility.adjustBrightness(new Color(198, 28, 181),
				-darkness);

		ra = new Rand(0x8F50A);
		drawLeg(true, oldLadyLeggings);
		drawLeg(false, oldLadyLeggings);

		for (int i = 0; i < trigDressCache.length; i++) {
			if (i % 3 == 0)
				tesselator.color(ra.bright(oldLadyDress, 30));
			tesselator.point(trigDressCache[i]);
		}

		for (int i = 0; i < trigHeadCache.length; i++) {
			if (i % 3 == 0)
				tesselator.color(ra.bright(oldLadySkin, 30));
			tesselator.point(trigHeadCache[i]);
		}
		drawFace();
	}

	private void drawFace() {
		float dis = 35;
		if (!angryFace) {
			tesselator.color(Color.black);
			tesselator.point(-10, 270, dis);
			tesselator.point(-30, 262, dis);
			tesselator.point(-30, 254, dis);
			tesselator.point(-30, 254, dis);
			tesselator.point(-10, 262, dis);
			tesselator.point(-10, 270, dis);
			tesselator.point(10, 270, dis);
			tesselator.point(30, 262, dis);
			tesselator.point(30, 254, dis);
			tesselator.point(30, 254, dis);
			tesselator.point(10, 262, dis);
			tesselator.point(10, 270, dis);
			tesselator.color(Color.blue);
			tesselator.point(-10, 254, dis);
			tesselator.point(-30, 254, dis);
			tesselator.point(-20, 240, dis);
			tesselator.point(10, 254, dis);
			tesselator.point(30, 254, dis);
			tesselator.point(20, 240, dis);
			tesselator.color(Color.red);
			tesselator.point(-20, 210, dis);
			tesselator.point(0, 220, dis);
			tesselator.point(20, 210, dis);
			tesselator.color(Color.red.darker());
			tesselator.point(-20, 210, dis);
			tesselator.point(0, 200, dis);
			tesselator.point(20, 210, dis);
		} else {
			tesselator.color(Color.black);
			tesselator.point(-30, 270, dis);
			tesselator.point(-10, 262, dis);
			tesselator.point(-10, 254, dis);
			tesselator.point(-10, 254, dis);
			tesselator.point(-30, 262, dis);
			tesselator.point(-30, 270, dis);
			tesselator.point(30, 270, dis);
			tesselator.point(10, 262, dis);
			tesselator.point(10, 254, dis);
			tesselator.point(10, 254, dis);
			tesselator.point(30, 262, dis);
			tesselator.point(30, 270, dis);
			
			final float t = 6;
			tesselator.color(Color.red.darker());
			tesselator.point(-21,240,dis+t);
			tesselator.point(-15,250,dis+t);
			tesselator.point(-5,240,dis+t);
			tesselator.point(21,240,dis+t);
			tesselator.point(15,250,dis+t);
			tesselator.point(5,240,dis+t);
			
			tesselator.color(Color.yellow);
			tesselator.point(-35,245,dis);
			tesselator.point(-25,254,dis);
			tesselator.point(-5,240,dis);
			tesselator.point(35,245,dis);
			tesselator.point(25,254,dis);
			tesselator.point(5,240,dis);
			
			tesselator.color(Color.red);
			tesselator.point(-30,230,dis);
			tesselator.point(-15,215,dis);
			tesselator.point(-15,200,dis);
			
			tesselator.point(-15,200,dis);
			tesselator.point(-15,215,dis);
			tesselator.point(15,215,dis);
			tesselator.point(15,200,dis);
			tesselator.point(15,215,dis);
			tesselator.point(-15,200,dis);
			
			tesselator.point(30,230,dis);
			tesselator.point(15,215,dis);
			tesselator.point(15,200,dis);
		}
	}
	
	public void makeAngry() {
		angryFace = true;
	}
	
	public void makeSweet() {
		angryFace = false;
	}

	private boolean angryFace = false;
	private static final int latitudeBands = 5;
	private static final int longitudeBands = 5;

	private static P3D[] genDressCache() {
		ArrayList<P3D> nodes = new ArrayList<P3D>();
		int bottomSides = 5;
		float theta = MathCalculator.TWOPI / bottomSides;
		for (int b = 0; b < bottomSides; b++) {
			float px = (float) (MathCalculator.cos(theta * b));
			float pz = (float) (MathCalculator.sin(theta * b));
			float nx = (float) (MathCalculator.cos(theta * (b + 1)));
			float nz = (float) (MathCalculator.sin(theta * (b + 1)));
			nodes.add(new P3D(px * 70, 60, pz * 70));
			nodes.add(new P3D(px * 50, 170, pz * 50));
			nodes.add(new P3D(nx * 50, 170, nz * 50));
			nodes.add(new P3D(px * 70, 60, pz * 70));
			nodes.add(new P3D(nx * 70, 60, nz * 70));
			nodes.add(new P3D(nx * 50, 170, nz * 50));

			nodes.add(new P3D(nx * 50, 170, nz * 50));
			nodes.add(new P3D(0, 170, 0));
			nodes.add(new P3D(px * 50, 170, pz * 50));
		}
		P3D[] res = new P3D[nodes.size()];
		res = nodes.toArray(res);
		return res;
	}

	private static P3D[] genHeadCache() {
		ArrayList<P3D> cache = new ArrayList<P3D>();
		float rad = 60;
		for (int latNumber = 0; latNumber <= latitudeBands; latNumber++) {
			for (int longNumber = 0; longNumber <= longitudeBands; longNumber++) {
				cache.add(getPoint(latNumber, longNumber, rad));
				cache.add(getPoint(latNumber - 1, longNumber, rad));
				cache.add(getPoint(latNumber - 1, longNumber + 1, rad));
				cache.add(getPoint(latNumber, longNumber, rad));
				cache.add(getPoint(latNumber, longNumber + 1, rad));
				cache.add(getPoint(latNumber - 1, longNumber + 1, rad));
			}
		}
		P3D[] arr = new P3D[cache.size()];
		arr = cache.toArray(arr);
		return arr;
	}

	private static P3D getPoint(double lat, double log, float radius) {
		double theta = lat * Math.PI / latitudeBands;
		double sinTheta = MathCalculator.sin(theta);
		double cosTheta = MathCalculator.cos(theta);
		double phi = log * 2 * Math.PI / longitudeBands;
		double sinPhi = MathCalculator.sin(phi);
		double cosPhi = MathCalculator.cos(phi);
		float x = (float) (cosPhi * sinTheta);
		float y = (float) cosTheta;
		float z = (float) (sinPhi * sinTheta);
		float zp = ((z) * radius * 0.9f);
		if (zp > 26)
			zp = 26;
		return new P3D(x * radius, (y * radius * 0.85f) + 235, zp);
	}

	protected float walkDelta = 0.0f;

	private void drawLeg(boolean side, Color oldLadyLeggings) {
		byte bit = 1;
		if (side)
			bit = -1;
		float walk = 0.0f;
		if (side)
			walk = (float) (Math.abs(MathCalculator.cos(walkDelta)) * 20);
		else
			walk = (float) (Math.abs(MathCalculator.sin(walkDelta)) * 20);
		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-50 * bit, 0, -10 + walk);
		tesselator.point(-30 * bit, 0, -10 + walk);
		tesselator.point(-20 * bit, 70, -15);
		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-20 * bit, 70, -15);
		tesselator.point(-60 * bit, 70, -15);
		tesselator.point(-50 * bit, 0, -10 + walk);

		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-50 * bit, 0, 10 + walk);
		tesselator.point(-30 * bit, 0, 10 + walk);
		tesselator.point(-20 * bit, 70, 15);
		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-20 * bit, 70, 15);
		tesselator.point(-60 * bit, 70, 15);
		tesselator.point(-50 * bit, 0, 10 + walk);

		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-50 * bit, 0, -10 + walk);
		tesselator.point(-50 * bit, 0, 10 + walk);
		tesselator.point(-60 * bit, 70, -15);
		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-50 * bit, 0, 10 + walk);
		tesselator.point(-60 * bit, 70, 15);
		tesselator.point(-60 * bit, 70, -15);

		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-30 * bit, 0, -10 + walk);
		tesselator.point(-30 * bit, 0, 10 + walk);
		tesselator.point(-20 * bit, 70, -15);
		tesselator.color(ra.bright(oldLadyLeggings, 25));
		tesselator.point(-30 * bit, 0, 10 + walk);
		tesselator.point(-20 * bit, 70, 15);
		tesselator.point(-20 * bit, 70, -15);
	}

	public void tick() {
		super.tick();
	}
	
	public void doMovement() {
		super.tick();
		walkDelta += 0.02f;
		if (!isMovingTowards()) {
			if (dx == 0 && dz == 0) {
				dx = getInstanceLoc().x+rand.nextInt(-500,500);
				dz = getInstanceLoc().z+rand.nextInt(-500,500);
				turn = MathCalculator.reduceTrig((float)(Math.atan2(dz - getInstanceLoc().z, dx - getInstanceLoc().x)) - MathCalculator.PI);
				delta = MathCalculator.reduceTrig(delta);
				//delta = 0;
			}
			if (turn < 0 && delta > turn)
				delta -= 0.02f;
			else if (turn > 0 && delta < turn)
				delta += 0.02f;
			else
				alphaTele += 0.01f;
			if (alphaTele > 1) {
				moveTowards(dx,dz);
				alphaTele = 0;
				dx = 0;
				dz = 0;
			}
		}
	}
	
	protected float turn = 0;
	protected float dx = 0;
	protected float dz = 0;
	protected float alphaTele = 0.0f;

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
