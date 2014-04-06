import java.awt.Color;


public class MathCalculator {
	public static float volumeCube(float centerX, float centerY,
			float centerZ, float width, float height, float depth) {
		float halfWidth = width * 0.5f;
		float halfHeight = height * 0.5f;
		float halfDepth = depth * 0.5f;
		float sx = centerX - halfWidth;
		float sy = centerY - halfHeight;
		float sz = centerZ - halfDepth;
		float ex = centerX + halfWidth;
		float ey = centerY + halfHeight;
		float ez = centerZ + halfDepth;
		return volumeCube(new P3D(sx,sy,sz), new P3D(ex,ey,ez));
	}
	
	public static float lock(float v) {
		if (v > 1)
			v = 1;
		if (v < 0)
			v = 0;
		return v;
	}
	
	public static Color lerp(Color c0, Color c1, float amount) {
		return new Color((int)lerp(c0.getRed(),c1.getRed(),amount),
				(int)lerp(c0.getGreen(),c1.getGreen(),amount),
				(int)lerp(c0.getBlue(),c1.getBlue(),amount),
				(int)lerp(c0.getAlpha(),c1.getAlpha(),amount));
	}
	
	public static float volumeCube(P3D frontTopLeftPoint,
			P3D backBottomRightPoint) {
		float width = Math.abs(frontTopLeftPoint.x - backBottomRightPoint.x);
		float height = Math.abs(frontTopLeftPoint.y - backBottomRightPoint.y);
		float depth = Math.abs(frontTopLeftPoint.z - backBottomRightPoint.z);
		return width*height*depth;
	}
	
	// Assumes perfect-sphere conditions.
	public static float volumeSphere(float radius) {
		float fT = 1.3333333333333333333333333333333f;
		return (float)(fT * Math.PI * (radius * radius * radius));
	}
	
	// "Better" estimation
	public static float volumeSphere(float radius, int lats, int longt) {
		float fT = 1.3333333333333333333333333333333f;
		float pureVol = (float)(fT * Math.PI * (radius * radius * radius));
		float floatLats = (float)lats;
		float floatLong = (float)longt;
		float subLats = (float)Math.cbrt((floatLats * Math.PI * 4 * radius));
		float subLong = (float)Math.cbrt((floatLong * Math.PI * 4 * radius));
		return pureVol - (subLats * subLong);
	}
	
	// Assumes perfect-cone conditions
	public static float volumeCone(float radius, float height) {
		return 0.333333333333333333333333333333333333333f * circleArea(radius) * height;
	}
	
	public static float volumePyramid(float height, float width) {
		return height * width * width * 0.3333333333333333333333333333333333f;
	}
	
	// Assumes perfect-cylinder conditions
	public static float volumeCylinder(float height, float radius) {
		return (float)(radius * radius * height * Math.PI);
	}
	
	public static float icosahedronConstant = (float)(5.0 * (3 + Math.sqrt(5)) / 12.0);
	
	public static float volumeIcosahedron(float radius) {
		// errr... (i don't think this should be here)
		radius = radius * radius;
		// 
		
		double sideLength = 
				(Math.sqrt(0.0852211291184773171713198623624001074389829148814502 * radius + 
						0.44721357712494718006225029347826 * radius));
		return (float)(icosahedronConstant  * (sideLength * sideLength * sideLength));
	}
	
	public static float circleArea(float radius) {
		return (float)(Math.PI * radius * radius);
	}
	
	public static int min(int q0, int q1, int q2) {
		if (q0 == q1)
			q0 = q0 - 1;
		if (q0 == q2)
			q2 = q2 - 1;
		if (q0 < q1 && q0 < q2)
			return q0;
		else if (q1 < q0 && q1 < q2)
			return q1;
		else if (q2 < q0 && q2 < q1)
			return q2;
		return 0;
	}

	public static int max(int q0, int q1, int q2) {
		if (q0 == q1)
			q1 = q1 + 1;
		if (q2 == q1)
			q2 = q2 + 1;
		if (q0 > q1 && q0 > q2)
			return q0;
		else if (q1 > q0 && q1 > q2)
			return q1;
		else if (q2 > q0 && q2 > q1)
			return q2;
		return 0;
	}

	
	public static int signum(int f) {
		if (f == 0)
			return 0;
		if (f > 0)
			return 1;
		else
			return -1;
	}

	public static int pixelMixer(int... rgb) {
		final int[] r = new int[rgb.length];
		final int[] g = new int[rgb.length];
		final int[] b = new int[rgb.length];
		for (int i = 0; i < rgb.length; i++) {
			final int c = rgb[i];
			r[i] = (c >> 16) & 0xFF;
			g[i] = (c >> 8) & 0xFF;
			b[i] = c & 0xFF;
		}
		int sumr = 0;
		int sumg = 0;
		int sumb = 0;
		for (int i = 0; i < rgb.length; i++) {
			sumr += r[i];
			sumg += g[i];
			sumb += b[i];
		}
		sumr = sumr / rgb.length;
		sumg = sumg / rgb.length;
		sumb = sumb / rgb.length;
		return (sumr << 16) | (sumg << 8) | sumb;
	}
	
	public static int colorLock(int value) {
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		return value;
	}
	public static float colorLock(float value) {
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		return value;
	}
	
	public static P3D normCalc(P3D p1,P3D p2, P3D p3) {
		P3D d1 = new P3D(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z); 
		P3D d2 = new P3D(p3.x - p2.x, p3.y - p2.y, p3.z - p2.z); 
		float xc = d1.y * d2.z - d1.z * d2.y;
		float yc = d1.z * d2.x - d1.x * d2.z;
		float zc = d1.x * d2.y - d1.y * d2.x;
		double length = 1.0/Math.sqrt(xc*xc + yc*yc + zc*zc);
		xc = (float)(xc * length);
		yc = (float)(yc * length);
		zc = (float)(zc * length);
		return new P3D(xc,yc,zc);
	}
	
	public static float lerp(float l0, float l1, float amount) {
		return l0 + amount * (l1 - l0);
	}
}
