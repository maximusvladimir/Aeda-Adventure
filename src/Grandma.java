import java.awt.Color;
import java.util.ArrayList;


public class Grandma extends Drawable {
	
	private PointTesselator tesselator;
	private Rand rand;
	public Grandma(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		rand = new Rand();
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	private float delta = 0.0f;
	private Rand ra;
	private static final P3D[] trigDressCache = genDressCache();
	private static final P3D[] trigHeadCache = genHeadCache();
	public void draw(int darkness) {
		tesselator.rotate(0, delta, 0);
		//tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		Color oldLadySkin = Utility.adjustBrightness(new Color(195,195,195),-darkness);
		Color oldLadyLeggings = Utility.adjustBrightness(new Color(200,190,190), -darkness);
		Color oldLadyDress = Utility.adjustBrightness(new Color(198,28,181),-darkness);
		
		ra = new Rand(0x8F50A);
		drawLeg(true,oldLadyLeggings);
		drawLeg(false,oldLadyLeggings);
		
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
		
		float dis = 40;
		tesselator.color(Color.black);
		tesselator.point(-10,270,dis);
		tesselator.point(-30,262,dis);
		tesselator.point(-30,254,dis);
		tesselator.point(-30,254,dis);
		tesselator.point(-10,262,dis);
		tesselator.point(-10,270,dis);
		
		tesselator.point(10,270,dis);
		tesselator.point(30,262,dis);
		tesselator.point(30,254,dis);
		tesselator.point(30,254,dis);
		tesselator.point(10,262,dis);
		tesselator.point(10,270,dis);
		
		
		tesselator.color(Color.blue);
		tesselator.point(-10,254,dis);
		tesselator.point(-30,254,dis);
		tesselator.point(-20,240,dis);
		tesselator.point(10,254,dis);
		tesselator.point(30,254,dis);
		tesselator.point(20,240,dis);
		
		
		tesselator.color(Color.red);
		tesselator.point(-20,210,dis);
		tesselator.point(0,220,dis);
		tesselator.point(20,210,dis);
		tesselator.color(Color.red.darker());
		tesselator.point(-20,210,dis);
		tesselator.point(0,200,dis);
		tesselator.point(20,210,dis);
		walkDelta += 0.02f;
	}
	
	private static final int latitudeBands = 5;
	private static final int longitudeBands = 5;
	
	private static P3D[] genDressCache() {
		ArrayList<P3D> nodes = new ArrayList<P3D>();
		int bottomSides = 5;
		float theta = MathCalculator.TWOPI / bottomSides;
		for (int b = 0; b < bottomSides; b++) {
			float px = (float)(Math.cos(theta * b));
			float pz = (float)(Math.sin(theta * b));
			float nx = (float)(Math.cos(theta * (b+1)));
			float nz = (float)(Math.sin(theta * (b+1)));
			nodes.add(new P3D(px*70,60,pz*70));
			nodes.add(new P3D(px*50,170,pz*50));
			nodes.add(new P3D(nx*50,170,nz*50));
			nodes.add(new P3D(px*70,60,pz*70));
			nodes.add(new P3D(nx*70,60,nz*70));
			nodes.add(new P3D(nx*50,170,nz*50));
			
			nodes.add(new P3D(nx*50,170,nz*50));
			nodes.add(new P3D(0,170,0));
			nodes.add(new P3D(px*50,170,pz*50));
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
					cache.add(getPoint(latNumber,longNumber,rad));
					cache.add(getPoint(latNumber-1,longNumber,rad));
					cache.add(getPoint(latNumber-1,longNumber+1,rad));
					cache.add(getPoint(latNumber,longNumber,rad));
					cache.add(getPoint(latNumber,longNumber+1,rad));
					cache.add(getPoint(latNumber-1,longNumber+1,rad));
			}
		}
		P3D[] arr = new P3D[cache.size()];
		arr = cache.toArray(arr);
		return arr;
	}
	
	private static P3D getPoint(double lat, double log, float radius) {
		double theta = lat * Math.PI / latitudeBands;
		double sinTheta = Math.sin(theta);
		double cosTheta = Math.cos(theta);
		double phi = log * 2 * Math.PI / longitudeBands;
		double sinPhi = Math.sin(phi);
		double cosPhi = Math.cos(phi);
		float x = (float)(cosPhi * sinTheta);
		float y = (float)cosTheta;
		float z = (float)(sinPhi * sinTheta);
		float zp = ((z)*radius*0.9f);
		if (zp > 35)
			zp = 35;
		return new P3D(x*radius,(y*radius*0.85f)+235,zp);
	}
	
	private float walkDelta = 0.0f;
	
	private void drawLeg(boolean side, Color oldLadyLeggings) {
		byte bit = 1;
		if (side)
			bit = -1;
		float walk = 0.0f;
		if (side)
			walk = (float)(Math.abs(Math.cos(walkDelta)) * 20);
		else
			walk = (float)(Math.abs(Math.sin(walkDelta)) * 20);
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-50*bit,0,-10+walk);
		tesselator.point(-30*bit,0,-10+walk);
		tesselator.point(-20*bit,70,-15);
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-20*bit,70,-15);
		tesselator.point(-60*bit,70,-15);
		tesselator.point(-50*bit,0,-10+walk);
		
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-50*bit,0,10+walk);
		tesselator.point(-30*bit,0,10+walk);
		tesselator.point(-20*bit,70,15);
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-20*bit,70,15);
		tesselator.point(-60*bit,70,15);
		tesselator.point(-50*bit,0,10+walk);
		
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-50*bit,0,-10+walk);
		tesselator.point(-50*bit,0,10+walk);
		tesselator.point(-60*bit,70,-15);
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-50*bit,0,10+walk);
		tesselator.point(-60*bit,70,15);
		tesselator.point(-60*bit,70,-15);
		
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-30*bit,0,-10+walk);
		tesselator.point(-30*bit,0,10+walk);
		tesselator.point(-20*bit,70,-15);
		tesselator.color(ra.bright(oldLadyLeggings,25));
		tesselator.point(-30*bit,0,10+walk);
		tesselator.point(-20*bit,70,15);
		tesselator.point(-20*bit,70,-15);
	}

	public void tick() {
		if (arrived) {
			targetX = getInstanceLoc().x + rand.nextInt(-500, 500);
			targetZ = getInstanceLoc().z + rand.nextInt(-500, 500);
			startX = getInstanceLoc().x;
			startZ = getInstanceLoc().z;
			alphaDist = new P3D(targetX,0,targetZ).dist(new P3D(startX,0,startZ));
			last = 1.3f + (rand.nextFloat() * 2);
			alpha = 0.0f;
			arrived = false;
			delta = 0.0f;
			turn = (float)Math.atan2(targetZ-startZ,targetX-startX);
			if (turn < 0)
				turn = MathCalculator.TWOPI + turn;
			return;
		}
		
		if (delta < turn) {
			delta += 0.03f;
			return;
		}
		
		alpha+= 0.003f;
		if (alpha < 1){
			float lx = targetX - startX;
			float lz = targetZ - startZ;
			float length = (float)(Math.sqrt(lx * lx + lz * lz));
			float unitPDX = lx / length;
			float unitPDZ = lz / length;
			setInstanceLoc(new P3D(startX + unitPDX * alpha * alphaDist,
					getInstanceLoc().y,startZ + unitPDZ * alpha * alphaDist));
		}
		else if (alpha > last) {
			arrived = true;
		}
	}
	
	private float turn = 0.0f;
	private float alphaDist = 0.0f;
	private float startX,startZ;
	private float last = 0.0f;
	private float alpha = 0.0f;
	private boolean arrived = true;
	private float targetX = 0.0f;
	private float targetZ = 0.0f;

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
