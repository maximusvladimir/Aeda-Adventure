import java.awt.Color;
import java.util.Random;


public class Rand extends Random {
	private static final long serialVersionUID = -2247503840546694056L;
	private static long queries =0;
	private Scene<Drawable> scene;
	private static final Rand internalRandom = new Rand();
	public static long getNumRandoms() {
		return queries;
	}
	
	public static double random() {
		return internalRandom.nextDouble();
	}
	
	public void setScene(Scene<Drawable> scene) {
		this.scene = scene;
	}
	
	public boolean chance(float probability) {
		if (probability > 1 || probability < 0)
			throw new IllegalArgumentException("Probability must be between 0 and 1");
		
		return nextFloat() < probability;
	}
	
	public float next2PI() {
		return (float)(nextDouble() * Math.PI * 2);
	}
	
	public int nextNegate() {
		return nextBoolean() ? -1 : 1;
	}
	
	public Rand() {
		super();
	}
	
	public Rand(long seed) {
		super(seed);
	}
	
	public Rand(String seed) {
		super(seed.hashCode());
	}
	
	public double nextDouble() {
		queries++;
		return super.nextDouble();
	}
	
	public int nextInt() {
		queries++;
		return super.nextInt();
	}
	
	public int nextInt(int max) {
		queries++;
		return super.nextInt(max);
	}
	
	public int nextInt(int min, int max) {
		queries++;
		return (int)(super.nextDouble() * (max-min) + min);
	}
	
	public float nextFloat() {
		queries++;
		return super.nextFloat();
	}
	
	
	public P3D nextLocation(float yDisplacement) {
		float dx = (float) (nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
		float dz = (float) (nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
		float dy = yDisplacement + scene.getTerrainHeight(dx, dz);
		return new P3D(dx,dy,dz);
	}
	
	public Color variate(Color base, int tolerance) {
		if (tolerance == 0)
			return base;
		int halfT = tolerance / 2;
		int dr = MathCalculator.colorLock(base.getRed() + nextInt(-halfT,halfT));
		int dg = MathCalculator.colorLock(base.getGreen() + nextInt(-halfT,halfT));
		int db = MathCalculator.colorLock(base.getBlue() + nextInt(-halfT,halfT));
		return new Color(dr,dg,db);
	}
	
	public Color bright(Color base, int tolerance) {
		int halfT = tolerance / 2;
		int rgbreduction = nextInt(-halfT,halfT);
		return new Color(MathCalculator.colorLock(rgbreduction+base.getRed()),
				MathCalculator.colorLock(rgbreduction+base.getGreen()),
				MathCalculator.colorLock(rgbreduction+base.getBlue()));
	}
	
	public Color bright(Color base, int min,int max) {
		int rgbreduction = nextInt(min,max);
		return new Color(MathCalculator.colorLock(rgbreduction+base.getRed()),
				MathCalculator.colorLock(rgbreduction+base.getGreen()),
				MathCalculator.colorLock(rgbreduction+base.getBlue()));
	}
}
