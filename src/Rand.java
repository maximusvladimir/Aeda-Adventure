import java.util.Random;


public class Rand extends Random {
	private static long queries =0;
	
	public static long getNumRandoms() {
		return queries;
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
}
