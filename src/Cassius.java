import java.awt.Color;
import java.util.ArrayList;

public abstract class Cassius extends Character {

	private PointTesselator tesselator;
	private Rand rand;

	public Cassius(Scene<Drawable> scene) {
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

	protected float delta = -MathCalculator.PIOVER2;
	private Rand ra;
	private static final P3D[] trigDressCache = genDressCache();
	private static final P3D[] trigHeadCache = genHeadCache();

	public void draw(int darkness) {
		tesselator.rotate(0, -delta - MathCalculator.PIOVER2, (float)(MathCalculator.sin(walkDelta)/10));
		// tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x, pos.y, pos.z, false);

		ra = new Rand(0x8F50A);
		
		CassiusModel.drawModel0(tesselator, 250, 0, ra);
		/*
		drawLeg(true, oldLadyLeggings);
		drawLeg(false, oldLadyLeggings);

		tesselator.color(ra.variate(oldLadyLeggings, 20));
		tesselator.point(50,faceh-50,-15);
		tesselator.point(100,faceh-130,-15);
		tesselator.point(100,faceh-160,-15);
		tesselator.color(ra.variate(oldLadyLeggings, 20));
		tesselator.point(100,faceh-160,-15);
		tesselator.point(50,faceh-80,-15);
		tesselator.point(50,faceh-50,-15);
		tesselator.color(ra.variate(oldLadyLeggings, 20));
		tesselator.point(50,faceh-50,15);
		tesselator.point(100,faceh-130,15);
		tesselator.point(100,faceh-160,15);
		tesselator.color(ra.variate(oldLadyLeggings, 20));
		tesselator.point(100,faceh-160,15);
		tesselator.point(50,faceh-80,15);
		tesselator.point(50,faceh-50,15);
		
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
		drawFace();*/
	}
private static final float faceh = 300;
	private void drawFace() {
		float dis = 35;
		float height = faceh;
		tesselator.color(Color.black);
		tesselator.point(-10, height+90, dis);
		tesselator.point(-30, height + 82, dis);
		tesselator.point(-30, height+74, dis);
		tesselator.point(-30, height+74, dis);
		tesselator.point(-10, height+82, dis);
		tesselator.point(-10, height+90, dis);
		tesselator.point(10, height + 90, dis);
		tesselator.point(30, height + 82, dis);
		tesselator.point(30, height + 74, dis);
		tesselator.point(30, height + 74, dis);
		tesselator.point(10, height + 82, dis);
		tesselator.point(10, height + 90, dis);
		tesselator.color(new Color(22,107,41));
		tesselator.point(-10, height + 74, dis);
		tesselator.point(-30, height + 74, dis);
		tesselator.point(-20, height + 60, dis);
		tesselator.point(10, height + 74, dis);
		tesselator.point(30, height + 74, dis);
		tesselator.point(20, height + 60, dis);
		tesselator.color(Color.red);
		tesselator.point(-20, height + 30, dis);
		tesselator.point(0, height + 40, dis);
		tesselator.point(20, height + 30, dis);
		tesselator.color(Color.red.darker());
		tesselator.point(-20, height + 30, dis);
		tesselator.point(0, height + 20, dis);
		tesselator.point(20, height + 30, dis);
	}
	private static final int latitudeBands = 5;
	private static final int longitudeBands = 5;

	private static final transient float mypeg = 3.91858789789739573897589374598170919419075902f;
	
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
			nodes.add(new P3D(px * 50, faceh-10, pz * 50));
			nodes.add(new P3D(nx * 50, faceh-10, nz * 50));
			nodes.add(new P3D(px * 70, 60, pz * 70));
			nodes.add(new P3D(nx * 70, 60, nz * 70));
			nodes.add(new P3D(nx * 50, faceh-10, nz * 50));

			nodes.add(new P3D(nx * 50, faceh-10, nz * 50));
			nodes.add(new P3D(0, faceh-10, 0));
			nodes.add(new P3D(px * 50, faceh-10, pz * 50));
		}
		P3D[] res = new P3D[nodes.size()];
		res = nodes.toArray(res);
		return res;
	}

	private static P3D[] genHeadCache() {
		ArrayList<P3D> cache = new ArrayList<P3D>();
		float rad = 70;
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
		if (zp > radius * 0.4f)
			zp = radius * 0.4f;
		return new P3D(x * radius, (y * radius * 0.85f) + (radius + faceh-10), zp);
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
