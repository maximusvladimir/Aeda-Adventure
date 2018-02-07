import java.awt.Color;
import java.util.ArrayList;


public class Barrel extends Drawable {

	private PointTesselator tesselator;
	private static P3D[] gens;
	private static Color[] colors;
	private float delta = 0.0f;
	public Barrel(Scene<Drawable> scene, Rand rand) {
		super(scene,new Hitbox(new P3D(-75,0,-75), new P3D(75,200,75)));
		//super(scene,new Hitbox(new P3D(-125,0,-125), new P3D(125,225,125)));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		
		delta = rand.next2PI();
		
		upI = rand.nextBoolean();
		
		float height = 200;
		float radius = 75;
		int numSections = 6;
		if (gens == null) {
			ArrayList<P3D> points = new ArrayList<P3D>();
			ArrayList<Color> colors2 = new ArrayList<Color>();
			double theta = Math.PI * 2 / numSections;
			float thirdHeight = height/3.0f;
			Color externalBinder = new Color(70,70,70);
			Color top = new Color(121,95,61);
			Color side = new Color(131,106,75);
			for (int i = 0; i < numSections; i++) {
				float x = (float)MathCalculator.cos(theta * i) * radius;
			    float z = (float)Math.sin(theta * i) * radius;
			    float nx = (float)MathCalculator.cos(theta * (i+1)) * radius;
			    float nz = (float)Math.sin(theta * (i+1)) * radius;
			    // top part of barrel.
			    colors2.add(Utility.adjustBrightness(top, rand.nextInt(-20, 5)));
			    points.add(new P3D(x*0.8f,height-14,z*0.8f));
			    points.add(new P3D(nx*0.8f,height-14,nz*0.8f));
			    points.add(new P3D(0,height-14,0));
			    
			    float ext = 1.35f;
			    // side of barrel.
			    Color sidePane = Utility.adjustBrightness(rand.variate(side, 10),rand.nextInt(-20, 4));
			    colors2.add(sidePane);
			    points.add(new P3D(x,height,z));
			    points.add(new P3D(nx,height,nz));
			    points.add(new P3D(nx*ext,height - thirdHeight,nz*ext));
			    colors2.add(sidePane);
			    points.add(new P3D(nx*ext,height - thirdHeight,nz*ext));
			    points.add(new P3D(x*ext,height - thirdHeight,z*ext));
			    points.add(new P3D(x,height,z));
			    
			    colors2.add(sidePane);
			    points.add(new P3D(nx*ext,height - thirdHeight,nz*ext));
			    points.add(new P3D(x*ext,height - thirdHeight,z*ext));
			    points.add(new P3D(x*ext,height - (2.5f*thirdHeight),z*ext));    
			    colors2.add(sidePane);
			    points.add(new P3D(nx*ext,height - thirdHeight,nz*ext));
			    points.add(new P3D(nx*ext,height - (2.5f*thirdHeight),nz*ext));
			    points.add(new P3D(x*ext,height - (2.5f*thirdHeight),z*ext));
			    
			    colors2.add(sidePane);
			    points.add(new P3D(x,height - (3.5f*thirdHeight),z));
			    points.add(new P3D(nx,height - (3.5f*thirdHeight),nz));
			    points.add(new P3D(nx*ext,height - (2.5f*thirdHeight),nz*ext));
			    colors2.add(sidePane);
			    points.add(new P3D(x,height - (3.5f*thirdHeight),z));
			    points.add(new P3D(x*ext,height - (2.5f*thirdHeight),z*ext));
			    points.add(new P3D(nx*ext,height - (2.5f*thirdHeight),nz*ext));
			    
			    float bind = ext + 0.15f;
			    colors2.add(externalBinder);
			    points.add(new P3D(x*bind,height - (2.6f*thirdHeight),z*bind));
			    points.add(new P3D(nx*bind,height - (2.6f*thirdHeight),nz*bind));
			    points.add(new P3D(nx*bind,height - (2.45f*thirdHeight),nz*bind));
			    colors2.add(externalBinder);
			    points.add(new P3D(x*bind,height - (2.6f*thirdHeight),z*bind));
			    points.add(new P3D(x*bind,height - (2.45f*thirdHeight),z*bind));
			    points.add(new P3D(nx*bind,height - (2.45f*thirdHeight),nz*bind));
			    colors2.add(externalBinder);
			    points.add(new P3D(x*bind,height - (0.965f*thirdHeight),z*bind));
			    points.add(new P3D(nx*bind,height - (0.965f*thirdHeight),nz*bind));
			    points.add(new P3D(nx*bind,height - (1.1f*thirdHeight),nz*bind));
			    colors2.add(externalBinder);
			    points.add(new P3D(x*bind,height - (0.965f*thirdHeight),z*bind));
			    points.add(new P3D(x*bind,height - (1.1f*thirdHeight),z*bind));
			    points.add(new P3D(nx*bind,height - (1.1f*thirdHeight),nz*bind));
			}
			gens = new P3D[points.size()];
			colors = new Color[colors2.size()];
			for (int i = 0; i < points.size();i++) {
				gens[i] = points.get(i);
			}
			points.clear();
			points = null;
			for (int i = 0; i < colors2.size(); i++) {
				colors[i] = colors2.get(i);
			}
			colors2.clear();
			colors2 = null;
		}
	}
	private boolean turnedSideways = false;
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		if (rotateOver != -43)
			tesselator.rotate(rotateOver, delta, 0);
		else
			tesselator.rotate(0, delta, 0);
		int trn = getTesselator().getTransparency();
		if (trn < 255)
			darkness = darkness - (255-trn);
		for (int i = 0; i < gens.length / 3; i++) {
			tesselator.color(Utility.adjustBrightness(colors[i],-darkness));
			tesselator.point(gens[i*3+0]);
			tesselator.point(gens[i*3+1]);
			tesselator.point(gens[i*3+2]);
		}
	}

	private float rotateOver = -43;
	private boolean done = false;
	private long deadTime = 0;
	private boolean upI = false;
	public void tick() {
		if (rotateOver != -43) {
			if (rotateOver >= 0 && rotateOver < MathCalculator.PIOVER2 * 0.7f)
				rotateOver += 0.03f;
			if (getInstanceLoc().y > -350)
				setInstanceLoc(getInstanceLoc().x,getInstanceLoc().y - 10, getInstanceLoc().z);
		}
		
		// fade it out (this is cool)
		if (deadTime != 0 && System.currentTimeMillis() - deadTime > 6000) {
			int adjust = (int)((System.currentTimeMillis() - deadTime) - 6000);
			getTesselator().setTransparency(255 - (int)((adjust/3500.0f) * 255));
			if (adjust > 3500)
				setVisible(false);
		}
		
		if (getDistToPlayer() < 390 && !done) {
			if (getScene().getPlayer().isHitting()) {
				getHitbox().disable();
				rotateOver = 0;
				GameState.instance.score += 20;
				done = true;
				dropGoodies();
				deadTime = System.currentTimeMillis();
			}
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}