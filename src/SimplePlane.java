import java.awt.Color;
import java.util.Random;

public class SimplePlane extends Drawable {
	private PointTesselator tesselator;
	private P3D[] points;
	private Color[] colors;
	private float hillHeight;
	private int resolution;
	private float WORLDSIZE;
	private float space;
	public SimplePlane(Scene<Drawable> scene, Color baseColor,float hillness, int variance, int resolution, float size) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		Rand rand = new Rand();
		
		this.resolution = resolution;
		WORLDSIZE= size;
		hillHeight = hillness;
		points = new P3D[resolution * resolution * 3 * 2];
		colors = new Color[resolution * resolution * 2];
		space = size / resolution;
		final float middle = size * 0.5f;
		for (int x = 0; x < resolution; x++) {
			for (int z = 0; z < resolution; z++) {
				colors[(z * resolution + x) * 2 + 0] = rand.variate(baseColor, variance);
				points[(z * resolution + x) * 6 + 0] = new P3D((x * space) - middle, lookup(x,z), (z * space) - middle);
				points[(z * resolution + x) * 6 + 1] = new P3D(((x+1) * space) - middle, lookup(x+1,z), (z * space) - middle);
				points[(z * resolution + x) * 6 + 2] = new P3D(((x+1) * space) - middle, lookup(x+1,z+1), ((z+1) * space) - middle);

				colors[(z * resolution + x) * 2 + 1] = rand.variate(baseColor, variance);
				points[(z * resolution + x) * 6 + 3] = new P3D((x * space) - middle, lookup(x,z), (z * space) - middle);
				points[(z * resolution + x) * 6 + 4] = new P3D((x * space) - middle, lookup(x,z+1), ((1+z) * space) - middle);
				points[(z * resolution + x) * 6 + 5] = new P3D(((x+1) * space) - middle, lookup(x+1,z+1), ((z+1) * space) - middle);
			}
		}
	}
	
	private float lookup(float x, float z) {
		if (Berlin.noise(x*0.4,-z*0.4) < 0.3)
			return -280;
		return (float) (Berlin.noise(x * 0.2, z * 0.2) * hillHeight);
	}
	
	public boolean isCullable() {
		return false;
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		float px = getScene().getPlayerX();
		float pz = getScene().getPlayerZ();
		int size = resolution;
		float WORLDSIZEHALF = WORLDSIZE / 2;
		int zlim = (int) ((pz + WORLDSIZEHALF) / WORLDSIZE * size) + 2;
		if (zlim > size - 1)
			zlim = size - 1;
		if (zlim < 1)
			zlim = 1;
		int zmin = (int) ((pz + WORLDSIZEHALF - 2550) / WORLDSIZE * size) + 1;
		if (zmin < 0)
			zmin = 0;
		if (zmin > size - 1)
			zmin = size - 1;

		int xlim = (int) ((px + WORLDSIZEHALF + (1400 / space * 400))
				/ WORLDSIZE * size) + 2;
		if (xlim > size - 1)
			xlim = size - 1;
		if (xlim < 1)
			xlim = 1;
		int xmin = (int) ((px + WORLDSIZEHALF - (2200 / space * 400))
				/ WORLDSIZE * size) + 1;
		if (xmin < 0)
			xmin = 0;
		if (xmin > size - 1)
			xmin = size - 1;
		int zshad = (int) ((pz + WORLDSIZEHALF - 260) / WORLDSIZE * size);
		int xshad = (int) ((px + WORLDSIZEHALF) / WORLDSIZE * size);
		Random ds = new Random(52);
		if (getScene().getFogEnd() == 0 && getScene().getFogStart() == 0) {
			xmin = zmin = 0;
			xlim = size;
		}
		for (int x = xmin; x < xlim; x++) {
			for (int z = zmin; z < zlim; z++) {
				tesselator.color(Utility.adjustBrightness(colors[(z * resolution + x) * 2 + 0],-darkness));
				tesselator.point(points[(z * resolution + x) * 6 + 0]);
				tesselator.point(points[(z * resolution + x) * 6 + 1]);
				tesselator.point(points[(z * resolution + x) * 6 + 2]);
				tesselator.color(Utility.adjustBrightness(colors[(z * resolution + x) * 2 + 1],-darkness));
				tesselator.point(points[(z * resolution + x) * 6 + 3]);
				tesselator.point(points[(z * resolution + x) * 6 + 4]);
				tesselator.point(points[(z * resolution + x) * 6 + 5]);
			}
		}
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}