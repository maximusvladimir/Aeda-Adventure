import java.awt.Color;
import java.awt.Graphics;


public class InsideHouse extends Level {
	private String na;
	public InsideHouse(String name,IMain m) {
		super(m);
		na = name;
	}
	
	public void init() {
		scene = new Scene<Drawable>(this, getRand());
		scene.setMoveOverride(true); //Prevents the player location from being saved.
		GamePlane plane = new GamePlane(scene,getRand(),10, new Color(189,182,94), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, plane.getHeightPoint(x,z)/4);
			}
		}
		scene.setPlane(plane);
		plane.genWorld();
		scene.setFog(-2000, -2600);
		scene.setFogColor(new Color(93, 109, 120));
	}

	public void draw(Graphics g) {
		scene.draw(g);
	}

	public String getName() {
		return na;
	}
}