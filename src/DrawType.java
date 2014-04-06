
public enum DrawType {
	Points,
	Polygon,
	Triangle,
	TriangleLines;
	
	public static DrawType getFromInt(int i) {
		int u = i % 4;
		if (u == 1)
			return Polygon;
		else if (u == 2)
			return Points;
		else if (u == 3)
			return TriangleLines;
		else
			return Triangle;
	}
}
