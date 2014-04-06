import java.awt.Color;
import java.awt.Font;


public class Font3D extends Operation3D {
	public Color color = Color.black;
	public Font font = new Font("Arial",0,12);
	public P3D loc = new P3D(0,0,0);
	public String str = "3D";
	public Font3D() {
		id = 7;
	}
}
