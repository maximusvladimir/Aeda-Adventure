import java.awt.Color;


public class C3D extends Operation3D {
	public Color color;
	public C3D() {
		color = Color.white;
		id = 3;
	}
	
	public C3D(int r, int g, int b) {
		if (r > 255)
			r = 255;
		if (r < 0)
			r = 0;
		if (g > 255)
			g = 255;
		if (g < 0)
			g = 0;
		if (b > 255)
			b = 255;
		if (b < 0)
			b = 0;
		color = new Color(r,g,b);
		id = 3;
	}
	
	public C3D(Color color) {
		this.color = color;
		id = 3;
	}
	
	public String toString() {
		return "Color: r-" + color.getRed() + " g-" + color.getGreen() + " b-" + color.getBlue() + " a-" + color.getAlpha();
	}
}
