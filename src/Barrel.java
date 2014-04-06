import java.awt.Color;
import java.util.ArrayList;


public class Barrel extends Drawable {

	private PointTesselator tesselator;
	private static P3D[] gens;
	private static Color[] colors;
	private float delta = 0.0f;
	public Barrel(Scene<Drawable> scene, Rand rand) {
		super(scene,new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		
		delta = rand.next2PI();
		
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
				float x = (float)Math.cos(theta * i) * radius;
			    float z = (float)Math.sin(theta * i) * radius;
			    float nx = (float)Math.cos(theta * (i+1)) * radius;
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
		if (turnedSideways)
			tesselator.rotate((float)(-Math.PI/2), delta, 0);
		else
			tesselator.rotate(0, delta, 0);
		for (int i = 0; i < gens.length / 3; i++) {
			tesselator.color(Utility.adjustBrightness(colors[i],-darkness));
			tesselator.point(gens[i*3+0]);
			tesselator.point(gens[i*3+1]);
			tesselator.point(gens[i*3+2]);
		}
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
