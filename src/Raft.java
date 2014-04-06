import java.awt.Color;

public class Raft extends Drawable {
	private PointTesselator tesselator;
	private Rand worker;
	public Raft(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
	}
	
	private float prevDelta = -10000;
	private float deltaShake = 0.0f;
	private float sailDeltaX = 0.0f;
	public void draw(int darkness) {
		float wveHeight = getScene().getGamePlane().getSparsalHeight()-160;
		//System.out.println(wveHeight);
		tesselator.translate(0, wveHeight, -700, false);
		/*float curDelta = getScene().getPlayer().delta * MathCalculator.TWOPIINVERSE;
		float amm = ((curDelta) - ((int)curDelta)) * MathCalculator.TWOPI;
		//System.out.println(amm + "," + prevDelta);
		if ((amm > prevDelta - 0.3f && amm < prevDelta + 0.3f) || (prevDelta == -10000))
			prevDelta = amm;
		else if (amm > MathCalculator.PI - 0.25f && prevDelta < 0.25f)
			prevDelta = amm;
		else if (amm < 0.25f && prevDelta > MathCalculator.PI - 0.25f)
			prevDelta = amm;
		tesselator.rotate(0, prevDelta-MathCalculator.PIOVER2, (float)(Math.sin(deltaShake)*0.04f));*/
		tesselator.rotate(0, getScene().getPlayer().actualDelta, (float)(Math.sin(deltaShake)*0.04f));
		worker = new Rand(90001);
		Color baseBar = new Color(100,92,79);
		Color baseSail = new Color(215,207,191);
		
		int num = 10;
		for (int i = 0; i < 10; i++) {
			Color bar = worker.variate(baseBar, 20);
			drawBar((i-(num*0.5f)) * 60 + worker.nextInt(10, 20), -300,worker.nextInt(600,800)*0.5f,worker.nextInt(30,55),bar);
		}
		
		float dist = -600;
		tesselator.color(new Color(78,97,107));
		tesselator.point(25,60,dist);
		tesselator.point(-25,60,dist);
		tesselator.point(-25,500,dist);
		tesselator.point(-25,500,dist);
		tesselator.point(25,500,dist);
		tesselator.point(25,60,dist);
		
		int lla = 8;
		final float w = 7.5f;
		float offsetZ = (float)(Math.sin(sailDeltaX) * 70);
		if (offsetZ < 0)
			offsetZ = -offsetZ;
		if (offsetZ < 10)
			offsetZ = 10;
		float sailZ = -(full * 200 + offsetZ);
		for (int length = 0; length < lla; length++) {
			float poleX = (length*50.0f/lla)-25;
			float nextPole = ((1+length)*50.0f/lla)-25;
			tesselator.color(worker.bright(baseSail, 30));
			//tesselator.point(poleX*0.8f,100,dist);
			tesselator.point(0,100,dist);
			tesselator.point(poleX * w,275,dist+sailZ);
			tesselator.point(nextPole*w,275,dist+sailZ);
			tesselator.color(worker.bright(baseSail, 30));
			tesselator.point(0,500,dist);
			//tesselator.point(poleX*0.8f,500,dist);
			tesselator.point(poleX * w,275,dist+sailZ);
			tesselator.point(nextPole*w,275,dist+sailZ);
		}
	}
	
	public boolean isCullable() {
		return false;
	}
	
	private void drawBar(final float x, final float z,final float length, final float size, final Color color) {
		final float lengthHalf = length;
		//tesselator.color(worker.variate(color, 20));
		/*tesselator.point(x,0,-lengthHalf);
		tesselator.point(x,0,lengthHalf);
		tesselator.point(x+size,0,lengthHalf);
		tesselator.point(x+size,0,lengthHalf);
		tesselator.point(x+size,0,-lengthHalf);
		tesselator.point(x,0,-lengthHalf);*/
		
		tesselator.color(worker.bright(color, 20));
		tesselator.point(x,size,-lengthHalf+z);
		tesselator.point(x,size,lengthHalf+z);
		tesselator.point(x+size,size,lengthHalf+z);
		tesselator.point(x+size,size,lengthHalf+z);
		tesselator.point(x+size,size,-lengthHalf+z);
		tesselator.point(x,size,-lengthHalf+z);
		
		tesselator.color(worker.bright(color, 25));
		tesselator.point(x,size,-lengthHalf+z);
		tesselator.point(x,size,lengthHalf+z);
		tesselator.point(x,0,lengthHalf+z);
		tesselator.point(x,0,lengthHalf+z);
		tesselator.point(x,0,-lengthHalf+z);
		tesselator.point(x,size,-lengthHalf+z);
		
		tesselator.color(worker.bright(color, 25));
		tesselator.point(x+size,size,-lengthHalf+z);
		tesselator.point(x+size,size,lengthHalf+z);
		tesselator.point(x+size,0,lengthHalf+z);
		tesselator.point(x+size,0,lengthHalf+z);
		tesselator.point(x+size,0,-lengthHalf+z);
		tesselator.point(x+size,size,-lengthHalf+z);
		
		tesselator.color(worker.bright(color, 25));
		tesselator.point(x,0,lengthHalf+z);
		tesselator.point(x+size,0,lengthHalf+z);
		tesselator.point(x+size,size,lengthHalf+z);
		tesselator.point(x,0,lengthHalf+z);
		tesselator.point(x,size,lengthHalf+z);
		tesselator.point(x+size,size,lengthHalf+z);
	}
	
	public void handleMove() {
		full = 1.0f;
	}

	private float full = 0.0f;
	
	public void tick() {
		deltaShake += 0.04f;
		sailDeltaX += 0.02f;
		if (full > 0)
			full -= 0.01f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
