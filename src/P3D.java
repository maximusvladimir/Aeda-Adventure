// 3D Point class.
public class P3D extends Operation3D {
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	private boolean isnormalised = false;

	public P3D() {
		id = 1;
	}
	
	public float dist(P3D other) {
		float dx = other.x - x;
		dx = dx*dx;
		float dy = other.y - y;
		dy = dy*dy;
		float dz = other.z - z;
		dz = dz*dz;
		return (float)Math.sqrt(dx+dy+dz);
	}

	public P3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		id = 1;
	}

	public P3D(P3D copy) {
		x = copy.x;
		y = copy.y;
		z = copy.z;
		id = 1;
	}

	public static P3D midpoint(P3D... points) {
		if (points.length == 0)
			return new P3D(0, 0, 0);
		float sumX = 0.0f;
		float sumY = 0.0f;
		float sumZ = 0.0f;
		for (int i = 0; i < points.length; i++) {
			sumX = sumX + points[i].x;
			sumY = sumY + points[i].y;
			sumZ = sumZ + points[i].z;
		}
		sumX = sumX / points.length;
		sumY = sumY / points.length;
		sumZ = sumZ / points.length;
		return new P3D(sumX, sumY, sumZ);
	}

	public boolean isNormalised() {
		return isnormalised;
	}

	public static P3D mult(P3D p0, P3D p1) {
		return new P3D(p0.x * p1.x, p0.y * p1.y, p0.z * p1.z);
	}

	public P3D mult(float amount) {
		x = x * amount;
		y = y * amount;
		z = z * amount;
		return this;
	}

	public P3D mult(P3D p0) {
		x = x * p0.x;
		y = y * p0.y;
		z = z * p0.z;
		return this;
	}

	public P3D inverse() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public P3D normalise() {
		double size = Math.sqrt(x * x + y * y + z * z);
		if (size != 0) {
			size = 1 / size;
			x = (float) (x * size);
			y = (float) (y * size);
			z = (float) (z * size);
		}
		isnormalised = true;
		return this;
	}

	public static P3D computeNormal(P3D p0, P3D p1, P3D p2) {
		float DX4, DX5, DY4, DY5, DZ4, DZ5; // used to calculate normal
		P3D Normal = new P3D();
		DX4 = p1.x - p0.x;
		DX5 = p1.x - p2.x;
		DY4 = p1.y - p0.y;
		DY5 = p1.y - p2.y;
		DZ4 = p1.z - p0.z;
		DZ5 = p1.z - p2.z;
		Normal.x = (DY4 * DZ5) - (DZ4 * DY5);
		Normal.y = (DZ4 * DX5) - (DX4 * DZ5);
		Normal.z = (DX4 * DY5) - (DY4 * DX5);
		return Normal;
	}

	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	public static float dot(P3D p0, P3D p1) {
		return p0.x * p1.x + p0.y * p1.y + p0.z * p1.z;
	}

	public static P3D add(P3D p0, P3D p1) {
		return new P3D(p0.x + p1.x, p0.y + p1.y, p0.z + p1.z);
	}
}
