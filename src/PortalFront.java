import java.awt.Color;


public class PortalFront extends Drawable {
	private PointTesselator tesselator;
	
	public PortalFront(Scene<Drawable> scene) {
		super(scene, genHitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	
	private static Hitbox genHitbox() {
		Hitbox box = new Hitbox(new P3D(-400,-500,-900), new P3D(-250,150,300),
				new P3D(250,-500,-900), new P3D(400,150,300));
		return box;
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x,pos.y,pos.z,false);
		
		final float depth = -200;
		final float top = 300;
		Rand rands = new Rand(52);
		Color base = Utility.adjustBrightness(new Color(121,121,112), -darkness);
		tesselator.color(rands.bright(base, 25));
		// roof
		tesselator.point(-300,top,depth);
		tesselator.point(-300,150,300);
		tesselator.point(300,150,300);
		tesselator.color(rands.bright(base, 25));
		tesselator.point(-300,top,depth);
		tesselator.point(300,top,depth);
		tesselator.point(300,150,300);
		
		// sides
		tesselator.color(rands.bright(base, 25));
		tesselator.point(-300,-500,depth);
		tesselator.point(-300,top,depth);
		tesselator.point(-300,150,300);
		tesselator.point(-300,-500,depth);
		tesselator.point(-300,-500,300);
		tesselator.point(-300,150,300);
		
		tesselator.color(rands.bright(base, 25));
		tesselator.point(300,-500,depth);
		tesselator.point(300,top,depth);
		tesselator.point(300,150,300);
		tesselator.point(300,-500,depth);
		tesselator.point(300,-500,300);
		tesselator.point(300,150,300);
		
		tesselator.color(Utility.adjustBrightness(getScene().getFogColor(),-25));
		tesselator.point(300,-500,depth);
		tesselator.point(300,top,depth);
		tesselator.point(-300,top,depth);
		tesselator.point(-300,top,depth);
		tesselator.point(-300,-500,depth);
		tesselator.point(300,-500,depth);
	}

	public void tick() {
		if (getScene().getLevel().isGameHalted())
			return;
		
		float d = getDistToPlayer();
		if (d < 300 && getScene().canPortalize()) {
			IMain lev = getScene().getLevel().getMain();
			if (lev.screenExists("vbm")) {
				lev.addScreen(new HolmVillage(lev));
			}
			getScene().getLevel().startTransition(lev.getScreen("vbm"),new P3D(0,0,9500),4.712388980384f+MathCalculator.PI);
			GameState.instance.playerLevel = 1;
			getScene().deportal();
		}
		if (d > 600)
			getScene().reportal();
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
