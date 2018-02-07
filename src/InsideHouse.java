import java.awt.Color;
import java.awt.Graphics;


public class InsideHouse extends Level {
	private String na;
	public InsideHouse(String name,IMain m) {
		super(m);
		na = name;
	}
	
	private Level ths;
	
	public void setRootLevel(Level rt) {
		ths = rt;
	}
	
	public void init() {
		scene = new Scene<Drawable>(this, getRand());
		//scene.setMoveOverride(true); //Prevents the player location from being saved.
		GamePlane plane = new GamePlane(scene,getRand(),10, new Color(189,182,94), 5, 0.5f);
		for (int x = 0; x < 15; x++) {
			for (int z = 0; z < 15; z++) {
				plane.setHeightPoint(x, z, 0);
			}
		}
		scene.setPlane(plane);
		scene.setFog(0,0);
		
		GameWalls walls = new GameWalls(scene,500, new Color(175,166,131));
		scene.add(walls);
		
		PortalFront front = new PortalFront(scene);
		front.setLevelGotoName("vbm");
		front.setLevelGotoPos(ths.getScene().getPosition());
		front.setInstanceLoc(0, -5.33333333f * getScene().getGamePlane().getSpacing());
		scene.add(front);
		
		// Turn off "dust" for inside the house.
		setFlakesVisible(false);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(175,166,131));
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		scene.draw(g);
	}
	
	public void tick() {
		super.tick();
		//System.out.println(scene.getPlayerX() + "," + scene.getPlayerZ());
	}

	public String getName() {
		return na;
	}
}