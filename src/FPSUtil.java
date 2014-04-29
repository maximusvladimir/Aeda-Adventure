
public class FPSUtil {
	private static long query;
	private static int lastFPS;
	private static long totals;
	private static long samples;
	
	private static int avg;
	public static void queryStart() {
		query = System.currentTimeMillis();
	}
	
	public static void queryEnd() {
		long dist = System.currentTimeMillis() - query;
		if (dist != 0) {
			lastFPS = (int)(1000.0 / dist);
			totals += lastFPS;
			samples++;
		}
	}
	
	public static void screenRecorderStart() {
		totals = 0;
		samples = 0;
	}
	
	public static void screenRecorderEnd() {
		final double a = ((double)totals) / ((double)samples);
		avg = (int)a;
	}
	
	public static int getAverageFPS() {
		return avg;
	}
}
