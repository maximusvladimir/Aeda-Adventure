import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GamePlane extends Drawable {
	private PointTesselator tesselator;
	public static int size;
	private Color baseColor;
	private float[] points;
	private Color[] colors;
	private float px, pz;
	private static final float space = 375.0f;
	private float WORLDSIZE;
	private float WORLDSIZEHALF;
	private float WORLDSIZESIZE;
	private float FOGAMOUNT = 2550;
	private Rand random;
	private int variability = 14;
	private int variabilityhalf = variability / 2;
	private float colorHeight = 0.5f;
	
	public float getWorldSize() {
		return WORLDSIZE;
	}
	
	public float getWorldSizeHalf() {
		return WORLDSIZEHALF;
	}
	
	public float getWorldSizeSize() {
		return WORLDSIZESIZE;
	}
	
	public int getActualSize() {
		return size;
	}
	
	public GamePlane(Scene<Drawable> scene,Rand rand, int levelsize, Color baseColor, int colorVariance, float colorHeightMix) {
		super(scene,new Hitbox());
		random = rand;
		colorHeight = colorHeightMix;
		tesselator = new PointTesselator();
		variability = colorVariance;
		variabilityhalf = variability / 2;
		//size = 55;// 40;
		size = levelsize;
		WORLDSIZE = space * (size - 1);
		WORLDSIZESIZE = WORLDSIZE * size;
		WORLDSIZEHALF = WORLDSIZE * 0.5f;
		points = new float[size * size];
		/*
		 * for (int p = 0; p < points.length;p++) { points[p] =
		 * (float)(-rand.nextDouble() * 90); }
		 */
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				points[z * size + x] = getLocationPoint(x, z);
				// System.out.println(points[z*size+x]);
			}
		}
		//baseColor = new Color(110, 130, 110);
		this.baseColor = baseColor;
		colors = new Color[size * size * 4];
	}

	public float getLocationPoint(float x, float z) {
		return (float) (Berlin.noise(x * 0.2, z * 0.2) * space) - (space / 2);
	}

	public float getLocation(float x, float z) {
		return getLocationPoint(x / WORLDSIZESIZE, z / WORLDSIZESIZE);
	}

	public float getPlayerLocation(float x, float z) {
		float zshad = ((pz + WORLDSIZEHALF - 260) / WORLDSIZE * size);
		float xshad = ((px + WORLDSIZEHALF) / WORLDSIZE * size);
		return getLocationPoint(xshad, zshad);
	}

	float target = 0.0f;

	private void setPlayer(float x, float z) {
		px = x;
		pz = z;
		target = getPlayerLocation(px, pz) - 100;
		// points[zshad*size+xshad]-100;
		if (playerHeight < target) {
			playerHeight += (target - playerHeight) * 0.1f;
		}
		if (playerHeight > target) {
			playerHeight -= (playerHeight - target) * 0.1f;
		}
	}

	private float playerHeight = 0;
	private BufferedImage genWorld;

	public float getHeight() {
		return playerHeight;
	}

	public void genWorld() {
		Rand variance = random;
		genWorld = new BufferedImage(68, 68, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D)genWorld.getGraphics();
		float highestPoint = -10000;
		float lowestPoint = 10000;
		for (int i = 0; i < points.length; i++) {
			if (points[i] > highestPoint)
				highestPoint = points[i];
			if (points[i] < lowestPoint)
				lowestPoint = points[i];
		}
		if (lowestPoint < 0)
			highestPoint = highestPoint + (-lowestPoint);
		else
			highestPoint = highestPoint - lowestPoint;
		float s = 68.0f/size;
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				Color cs = new Color(
						MathCalculator.colorLock((variance.nextInt(variability) - variabilityhalf + baseColor
								.getRed())),
								MathCalculator.colorLock((variance.nextInt(variability) - variabilityhalf + baseColor
								.getGreen())),
								MathCalculator.colorLock((variance.nextInt(variability) - variabilityhalf + baseColor
								.getBlue())));
				float p = points[z * size + x];
				if (lowestPoint < 0)
					p = p + (-lowestPoint);
				else
					p = p - lowestPoint;
				p = MathCalculator.lock(p/highestPoint);
				int rgb = (int)(p*60)+65;
				Color cd = new Color(rgb,rgb,rgb);
				cs = MathCalculator.lerp(cs, cd, colorHeight);
				colors[2 * (z) * (size - 1) + (2 * (x))] = cs;
				colors[2 * (z + 1) * (size - 1) + (2 * (x + 1))] = cs;
				g.setColor(cs);
				float x3 = (x*68.0f/size);
				float y3 = (z*68.0f/size);
				//g.fillRect(x3,y3,s,s);
				g.fill(new Rectangle2D.Float(x3,y3,s,s));
			}
		}
	}

	public BufferedImage getGenWorld() {
		int size = 61;
		return genWorld;
	}

	public void setSize(int size) {
		this.size = size;
		points = new float[size * size];
	}

	public int getSize() {
		return size;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}

	public Color getBaseColor() {
		return baseColor;
	}

	public void setHeightPoint(int x, int z, float height) {
		int det = z * size + x;
		if (det < 0 || det > points.length - 1)
			return;
		points[det] = height;
	}

	// float sinz = 0.0f;
	public float getHeightPoint(int x, int z) {
		return points[z * size + x]; // + (float)(Math.sin(sinz + x) * 50);
	}

	public boolean isCullable() {
		return false;
	}
	
	public void draw(int darkness) {
		if (!isVisible())
			return;
		setPlayer(getScene().getPlayerX(),getScene().getPlayerZ());
		// sinz += 0.01f;
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x - (space * size * 0.5f), pos.y, pos.z
				- (space * size * 0.5f), false);
		int zlim = (int) ((pz + WORLDSIZEHALF) / WORLDSIZE * size) + 2;
		if (zlim > size - 1)
			zlim = size - 1;
		if (zlim < 1)
			zlim = 1;
		int zmin = (int) ((pz + WORLDSIZEHALF - FOGAMOUNT)
				/ WORLDSIZE * size) + 1;
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
		int zshad = (int) ((pz + WORLDSIZEHALF - 260)
				/ WORLDSIZE * size);
		int xshad = (int) ((px + WORLDSIZEHALF) / WORLDSIZE * size);
		for (int x = xmin; x < xlim; x++) {
			for (int z = zmin; z < zlim; z++) {
				Color sample1 = colors[z * 2 * (size - 1) + (x * 2)];
				if (xshad == x && zshad == z) {
					darkness = darkness + 20;
				}
				tesselator.color(sample1.getRed() - darkness,
						sample1.getGreen() - darkness, sample1.getBlue()
								- darkness);
				tesselator.point((x) * space, getHeightPoint(x, z), z * space);
				tesselator.point((x + 1) * space, getHeightPoint(x + 1, z), z
						* space);
				tesselator.point((x + 1) * space, getHeightPoint(x + 1, z + 1),
						(z + 1) * space);
				Color sample2 = colors[2 * (z + 1) * (size - 1) + (2 * (x + 1))];
				tesselator.color(sample2.getRed() - darkness,
						sample2.getGreen() - darkness, sample2.getBlue()
								- darkness);
				tesselator.point((x) * space, getHeightPoint(x, z), z * space);
				tesselator.point((x + 1) * space, getHeightPoint(x + 1, z + 1),
						(1 + z) * space);
				tesselator.point((x) * space, getHeightPoint(x, z + 1), (z + 1)
						* space);
				if (xshad == x && zshad == z) {
					darkness = darkness - 20;
				}
			}
		}
	}

	public void tick() {

	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
