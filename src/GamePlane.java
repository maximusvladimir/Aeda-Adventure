import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GamePlane extends Drawable {
	private PointTesselator tesselator;
	public int size;
	private Color baseColor;
	private float[] points;
	private Color[] colors;
	private Color[] colors2;
	private float[] waters;
	private float px, pz;
	private static final float space = 350.0f;
	private float WORLDSIZE;
	private float WORLDSIZEHALF;
	private float WORLDSIZESIZE;
	private float FOGAMOUNT = 2550;
	private Rand random;
	private int variability = 14;
	private int variabilityhalf = variability / 2;
	private float colorHeight = 0.5f;
	private boolean stop = false;
	private boolean water = false;
	
	public float getSpacing() {
		return space;
	}

	public void setWaterDraw(boolean v) {
		water = v;
	}

	public boolean isWaterDrawn() {
		return water;
	}

	public void stopFall() {
		stop = true;
	}

	public void allowFall() {
		stop = false;
	}

	public boolean isFallingAllowed() {
		return stop;
	}

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

	public GamePlane(Scene<Drawable> scene, Rand rand, int levelsize,
			Color baseColor, int colorVariance, float colorHeightMix) {
		super(scene, new Hitbox());
		random = rand;
		colorHeight = colorHeightMix;
		tesselator = new PointTesselator();
		variability = colorVariance;
		variabilityhalf = variability / 2;
		// size = 55;// 40;
		size = levelsize;
		WORLDSIZE = space * (size - 1);
		WORLDSIZESIZE = WORLDSIZE * size;
		WORLDSIZEHALF = WORLDSIZE * 0.5f;
		points = new float[size * size];
		waters = new float[size * size];
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
		// baseColor = new Color(110, 130, 110);
		this.baseColor = baseColor;
		colors = new Color[size * size * 4];
		colors2 = new Color[size * size * 4];
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
		if (pointsOverride) {
			if (zshad > size)
				zshad = size;
			if (xshad > size)
				xshad = size;
			if (zshad < 0)
				zshad = 0;
			if (xshad < 0)
				xshad = 0;
			int fx = (int) xshad;
			int fz = (int) zshad;
			int ux = fx + 1;
			int uz = fz + 1;
			if (ux > size || uz > size)
				return getHeightPoint(fx, fz);
			else {
				float dx = xshad - fx;
				float dz = zshad - fz;
				float lx = MathCalculator.lerp(getHeightPoint(fx, fz),
						getHeightPoint(ux, fz), dx);
				float lz = MathCalculator.lerp(getHeightPoint(fx, fz),
						getHeightPoint(fx, uz), dz);
				float a = ((lx + lz) * 0.5f);
				return a;
			}
		}
		return getLocationPoint(xshad, zshad);
	}
	
	public float getPlayerSparsalLocation(float x, float z) {
		float zshad = ((z + WORLDSIZEHALF - 260) / WORLDSIZE * size);
		float xshad = ((x + WORLDSIZEHALF) / WORLDSIZE * size);
		if (pointsOverride || isWaterDrawn()) {
			if (zshad > size)
				zshad = size;
			if (xshad > size)
				xshad = size;
			if (zshad < 0)
				zshad = 0;
			if (xshad < 0)
				xshad = 0;
			int fx = (int) xshad;
			int fz = (int) zshad;
			int ux = fx + 1;
			int uz = fz + 1;
			if (ux > size || uz > size)
				return getSparsalHeightPoint(fx, fz);
			else {
				float dx = xshad - fx;
				float dz = zshad - fz;
				float lx = MathCalculator.lerp(getSparsalHeightPoint(fx, fz),
						getSparsalHeightPoint(ux, fz), dx);
				float lz = MathCalculator.lerp(getHeightPoint(fx, fz),
						getSparsalHeightPoint(fx, uz), dz);
				float a = ((lx + lz) * 0.5f);
				return a;
			}
		}
		return getLocationPoint(xshad, zshad);
	}

	float target = 0.0f;

	private void setPlayer(float x, float z) {
		if (stop)
			return;
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
	
	private float nnsHeight = 0.0f;
	private float nnsTarget = 0.0f;
	private void setPlayerSparsal(float x, float z) {
		if (stop)
			return;
		px = x;
		pz = z;
		nnsTarget = getPlayerSparsalLocation(px, pz) - 100;
		// points[zshad*size+xshad]-100;
		if (nnsHeight < nnsTarget) {
			nnsHeight += (nnsTarget - nnsHeight) * 0.05f;
		}
		if (nnsHeight > target) {
			nnsHeight -= (nnsHeight - nnsTarget) * 0.05f;
		}
	}
	public float getSparsalHeight() {
		return nnsHeight;
	}

	public int[] getWorldPointAsGridPoint(Drawable d) {
		return getWorldPointAsGridPoint(d.getInstanceLoc().x,
				d.getInstanceLoc().z);
	}

	public int[] getWorldPointAsGridPoint(float x, float z) {
		/*
		 * float nx = ((x+WORLDSIZEHALF+ getScene().getPlayerX()) / space);
		 * float nz = 110-((z+WORLDSIZEHALF+ getScene().getPlayerZ()) / space);
		 */
		float nx = ((x + WORLDSIZEHALF) / WORLDSIZE * size);
		float nz = ((z + WORLDSIZEHALF) / WORLDSIZE * size);
		// System.out.println(nx+","+nz);
		return new int[] { (int) nx, (int) nz };
		// return new int[]{0,0};
	}

	private float playerHeight = 0;
	private BufferedImage genWorld;

	public void setPlayerHeightOverride(float h) {
		playerHeight = h;
	}

	public float getHeight() {
		return playerHeight;
	}

	public void genWorld() {
		Rand variance = random;
		genWorld = new BufferedImage(68, 68, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D) genWorld.getGraphics();
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
		float s = 68.0f / size;
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				int cDet0 = getColor(x, z);
				int cDet1 = cDet0;
				Color cs = null;
				if (colors[cDet0] == null || colors2[cDet1] == null) {
					cs = new Color(MathCalculator.colorLock((variance
							.nextInt(variability) - variabilityhalf + baseColor
							.getRed())), MathCalculator.colorLock((variance
							.nextInt(variability) - variabilityhalf + baseColor
							.getGreen())), MathCalculator.colorLock((variance
							.nextInt(variability) - variabilityhalf + baseColor
							.getBlue())));
					float p = points[z * size + x];
					if (lowestPoint < 0)
						p = p + (-lowestPoint);
					else
						p = p - lowestPoint;
					p = MathCalculator.lock(p / highestPoint);
					int rgb = (int) (p * 60) + 65;
					Color cd = new Color(rgb, rgb, rgb);
					cs = MathCalculator.lerp(cs, cd, colorHeight);
					colors[cDet0] = cs;
					colors2[cDet1] = variance.bright(cs, 20);
				} else
					cs = colors[cDet0];
				g.setColor(cs);
				float x3 = (x * 68.0f / size);
				float y3 = (z * 68.0f / size);
				// g.fillRect(x3,y3,s,s);
				g.fill(new Rectangle2D.Float(x3, y3, s, s));
			}
		}
	}

	public void setColorPoint(int x, int z, Color color) {
		int det = getColor(x, z);
		if (det < 0 || det > colors.length - 1)
			return;
		colors[det] = color;
		colors2[det] = color;
	}

	// float sinz = 0.0f;
	public Color getColorPoint(int x, int z) {
		int det = getColor(x, z);
		if (det < 0 || det > colors.length - 1 || colors[det] == null)
			return Color.white;
		return colors[det];// + (float)(Math.sin(sinz + x) * 50);
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
		pointsOverride = true;
		points[det] = height;
	}

	private boolean pointsOverride = false;

	// float sinz = 0.0f;
	public float getHeightPoint(int x, int z) {
		int det = z * size + x;
		if (det < 0 || det > points.length - 1)
			return -0.00000001f;
		return points[det]; // + (float)(Math.sin(sinz + x) * 50);
	}
	
	public float getSparsalHeightPoint(int x, int z) {
		int det = z * size + x;
		if (det < 0 || det > points.length - 1)
			return -0.00000001f;
		if (isWaterDrawn() && points[det] < 0)
			return waters[det];
		return points[det];
	}

	public float getHeightPointWater(int x, int z) {
		int det = z * size + x;
		if (det < 0 || det > points.length - 1)
			return -0.00000001f;
		return waters[det];// + (float)(Math.sin(sinz + x) * 50);
	}

	public boolean isCullable() {
		return false;
	}

	private int getColor(int x, int z) {
		int det = (z * (size * 2)) + x;
		if (det > colors.length - 1 || det < 0)
			return 0;
		else
			return det;
	}

	public void draw(int darkness) {
		if (!isVisible())
			return;
		setPlayer(getScene().getPlayerX(), getScene().getPlayerZ());
		setPlayerSparsal(getScene().getPlayerX(), getScene().getPlayerZ());
		// sinz += 0.01f;
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x - (space * size * 0.5f), pos.y, pos.z
				- (space * size * 0.5f), false);
		int zlim = (int) ((pz + WORLDSIZEHALF) / WORLDSIZE * size) + 4;
		if (zlim > size - 1)
			zlim = size - 1;
		if (zlim < 1)
			zlim = 1;
		int zmin = (int) ((pz + WORLDSIZEHALF - FOGAMOUNT) / WORLDSIZE * size);
		if (zmin < 0)
			zmin = 0;
		if (zmin > size - 1)
			zmin = size - 1;

		int xlim = (int) ((px + WORLDSIZEHALF + (1400 / space * 400))
				/ WORLDSIZE * size) + 3;
		if (xlim > size - 1)
			xlim = size - 1;
		if (xlim < 1)
			xlim = 1;
		int xmin = (int) ((px + WORLDSIZEHALF - (2200 / space * 400))
				/ WORLDSIZE * size) - 2;
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
		final float sn = 0.0f;
		for (int x = xmin; x < xlim; x++) {
			for (int z = zmin; z < zlim; z++) {
				if (isWaterDrawn()
						&& (getHeightPoint(x, z) < sn
								|| getHeightPoint(x + 1, z) < sn || getHeightPoint(
								x + 1, z + 1) < sn)) {
					int indi = getColor(x, z);
					Color sample1 = colors[indi];
					Color peak = Utility.adjustBrightness(sample1, 30);
					float t01 = getHeightPointWater(x + 1, z) / 100.0f;
					sample1 = MathCalculator.lerp(sample1, peak, t01);
					tesselator.color(sample1.getRed() - darkness,
							sample1.getGreen() - darkness, sample1.getBlue()
									- darkness);
					tesselator.point((x) * space, getHeightPointWater(x, z), z
							* space);
					tesselator.point((x + 1) * space,
							getHeightPointWater(x + 1, z), z * space);
					tesselator.point((x + 1) * space,
							getHeightPointWater(x + 1, z + 1), (z + 1) * space);
					Color sample2 = colors2[getColor(x, z)];
					float t02 = getHeightPointWater(x + 1, z + 1) / 100.0f;
					sample2 = MathCalculator.lerp(sample2, peak, t02);
					tesselator.color(sample2.getRed() - darkness,
							sample2.getGreen() - darkness, sample2.getBlue()
									- darkness);
					tesselator.point((x) * space, getHeightPointWater(x, z), z
							* space);
					tesselator.point((x + 1) * space,
							getHeightPointWater(x + 1, z + 1), (1 + z) * space);
					tesselator.point((x) * space,
							getHeightPointWater(x, z + 1), (z + 1) * space);
				} else {
					int indi = getColor(x, z);
					Color sample1 = colors[indi];
					/* Ignore player shadow for now.
					 * if (xshad == x && zshad == z) {
						if (getScene().getPlayer().flameSize > 0.001f) {
							darkness = darkness - (int)(getScene().getPlayer().flameSize * 40) - 20;
						}
						else {
							darkness = darkness + 20;
						}
					}*/
					tesselator.color(sample1.getRed() - darkness,
							sample1.getGreen() - darkness, sample1.getBlue()
									- darkness);
					tesselator.point((x) * space, getHeightPoint(x, z), z
							* space);
					tesselator.point((x + 1) * space, getHeightPoint(x + 1, z),
							z * space);
					tesselator.point((x + 1) * space,
							getHeightPoint(x + 1, z + 1), (z + 1) * space);
					Color sample2 = colors2[getColor(x, z)];
					tesselator.color(sample2.getRed() - darkness,
							sample2.getGreen() - darkness, sample2.getBlue()
									- darkness);
					tesselator.point((x) * space, getHeightPoint(x, z), z
							* space);
					tesselator.point((x + 1) * space,
							getHeightPoint(x + 1, z + 1), (1 + z) * space);
					tesselator.point((x) * space, getHeightPoint(x, z + 1),
							(z + 1) * space);
					/*if (xshad == x && zshad == z) {
						if (getScene().getPlayer().flameSize > 0.001f) {
							darkness = darkness + (int)(getScene().getPlayer().flameSize * 40) + 20;
						}
						else {
							darkness = darkness - 20;
						}
					}*/
				}
			}
		}
	}

	private float waterx = 0.0f;
	private float waterz = 0.0f;

	public void tick() {
		if (isWaterDrawn()) {
			waterx -= 0.04f;
			waterz += 0.01f;
			for (int x = 0; x < size; x++) {
				float waterXVal = (float) (Math.cos((waterx + x)*0.5f));
				for (int z = 0; z < size; z++) {
					float lookup = getHeightPoint(x, z);
					float nextDr = getHeightPoint(x, z - 1);
					if (lookup < 0 || nextDr > 0) {
						float waterZVal = (float) (Math.sin((waterz + z)*0.5f));
						if (nextDr > 0) {
							waters[z * size + x] = nextDr;
						} else {
							// TODO : replace with "better" code (the 100 - x part):
							float he = (100-x) * 1.4f;
							if (he < 50)
								he = 50;
							waters[z * size + x] = (float) (waterXVal
									* waterZVal * he);
						}
					}
				}
			}
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
