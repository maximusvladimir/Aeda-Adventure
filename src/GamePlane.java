import java.awt.Color;
import java.util.Random;


public class GamePlane extends Drawable {
	private PointTesselator tesselator;
	public static int size;
	private Color baseColor;
	private float[] points;
	private Color[] colors;
	private float px,pz;
	public static final float space = 375.0f;
	public static float WORLDSIZE;
	public static float WORLDSIZEHALF;
	public static float WORLDSIZESIZE;
	public GamePlane(Rand rand) {
		super(null,null);
		tesselator = new PointTesselator();
		size = 55;//40;
		WORLDSIZE = space * (size-1);
		WORLDSIZESIZE = WORLDSIZE*size;
		WORLDSIZEHALF = WORLDSIZE*0.5f;
		points = new float[size*size];
		/*for (int p = 0; p < points.length;p++) {
			points[p] = (float)(-rand.nextDouble() * 90);
		}*/
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				points[z*size+x] = getLocationPoint(x,z);
				//System.out.println(points[z*size+x]);
			}
		}
		baseColor = new Color(110,130,110);
		colors = new Color[size*size*4];
		final int variability = 14;
		final int variabilityhalf = variability/2;
		Random variance = new Random(3);
		for (int c = 0; c < colors.length; c++) {
			colors[c] = new Color((variance.nextInt(variability)-variabilityhalf+baseColor.getRed()),
					(variance.nextInt(variability)-variabilityhalf+baseColor.getGreen()),
					(variance.nextInt(variability)-variabilityhalf+baseColor.getBlue()));
		}
	}
	
	public float getLocationPoint(float x, float z) {
		return(float)(Berlin.noise(x*0.2,z*0.2)*space)-(space/2);
	}
	public float getLocation(float x, float z) {
		return getLocationPoint(x/WORLDSIZESIZE,z/WORLDSIZESIZE);
	}
	public float getPlayerLocation(float x, float z) {
		float zshad = ((pz+WORLDSIZEHALF-260)/WORLDSIZE*size);
		float xshad = ((px+WORLDSIZEHALF)/WORLDSIZE*size);
		return getLocationPoint(xshad,zshad);
	}
	float target = 0.0f;
	public void setPlayer(float x, float z) {
		px = x;
		pz = z;
		target = getPlayerLocation(px,pz)-100;
		//points[zshad*size+xshad]-100;
		if (playerHeight < target){
			playerHeight += (target-playerHeight)*0.1f;
		}
		if (playerHeight > target) {
			playerHeight -= (playerHeight-target)*0.1f;
		}
	}
	private float playerHeight = 0;
	public float getHeight() {
		return playerHeight;
	}
	
	public void setSize(int size) {
		this.size = size;
		points = new float[size*size];
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
		points[z*size+x] = height;
	}
	//float sinz = 0.0f;
	public float getHeightPoint(int x, int z) {
		return points[z*size+x]; //+ (float)(Math.sin(sinz + x) * 50);
	}
	
	public void draw(int darkness) {
		if (!isVisible())
			return;
		//sinz += 0.01f;
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x-(space*size*0.5f),pos.y,pos.z-(space*size*0.5f),false);
		int zlim = (int)((pz+GamePlane.WORLDSIZEHALF)/GamePlane.WORLDSIZE*size)+2;
		if (zlim > size-1)
			zlim = size-1;
		if (zlim < 1)
			zlim = 1;
		int zmin = (int)((pz+GamePlane.WORLDSIZEHALF-2550)/GamePlane.WORLDSIZE*size)+1;
		if (zmin < 0)
			zmin = 0;
		if (zmin > size-1)
			zmin = size-1;
		
		int xlim = (int)((px+GamePlane.WORLDSIZEHALF+(1400/space*400))/GamePlane.WORLDSIZE*size)+2;
		if (xlim > size-1)
			xlim = size-1;
		if (xlim < 1)
			xlim = 1;
		int xmin = (int)((px+GamePlane.WORLDSIZEHALF-(2200/space*400))/GamePlane.WORLDSIZE*size)+1;
		if (xmin < 0)
			xmin = 0;
		if (xmin > size-1)
			xmin = size-1;
		int zshad = (int)((pz+GamePlane.WORLDSIZEHALF-260)/GamePlane.WORLDSIZE*size);
		int xshad = (int)((px+GamePlane.WORLDSIZEHALF)/GamePlane.WORLDSIZE*size);
		for (int x = xmin; x < xlim; x++) {
			for (int z = zmin; z < zlim; z++) {
				Color sample1 = colors[z*2 * (size-1) + (x*2)];
				if (xshad == x && zshad == z) {
					darkness = darkness + 20;
				}
				tesselator.color(sample1.getRed()-darkness,
						sample1.getGreen()-darkness,
						sample1.getBlue()-darkness);
				tesselator.point((x)*space, getHeightPoint(x,z), z*space);
				tesselator.point((x+1)*space, getHeightPoint(x+1,z), z*space);
				tesselator.point((x+1)*space, getHeightPoint(x+1,z+1), (z+1)*space);
				Color sample2 = colors[2*(z+1) * (size-1) + (2*(x+1))];
				tesselator.color(sample2.getRed()-darkness,
						sample2.getGreen()-darkness,
						sample2.getBlue()-darkness);
				tesselator.point((x)*space, getHeightPoint(x,z), z*space);
				tesselator.point((x+1)*space, getHeightPoint(x+1,z+1), (1+z)*space);
				tesselator.point((x)*space, getHeightPoint(x,z+1), (z+1)*space);
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
