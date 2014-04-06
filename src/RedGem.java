import java.awt.Color;


public class RedGem extends Gem {
	public RedGem(Scene<Drawable> scene, Rand rand) {
		super(scene,rand,buildHitbox());
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
	
	private final static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-50,-40,-25),new P3D(50,40,25));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				box.getDrawable().getScene().remove(box.getDrawable());
				GameState.instance.gems += 10;
				if (SoundManager.soundEnabled){
					Sound gem = new Sound("ding");
					gem.play();
				}
			}		
		});
		return box;
	}
	
	public void draw(int darkness) {
		if (!isVisible())
			return;
		darkness = (int)(Math.sin(dark) * 40);
		tesselator.translate(pos.x,pos.y+(float)(Math.sin(time1)*20),pos.z,false);
		tesselator.rotate(0,delta+spinOffset,0);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		
		Rand rand = new Rand(929);
		Color cs = Utility.adjustBrightness(new Color(220,15,15), -darkness);
		tesselator.color(rand.bright(cs, 40));
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		
		tesselator.color(rand.bright(cs, 45));
		tesselator.point(-50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(rand.bright(cs, 50));
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(rand.bright(cs, 40));
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,40,0);

		tesselator.color(rand.bright(cs, 55));
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		
		tesselator.color(rand.bright(cs, 40));
		tesselator.point(-50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(rand.bright(cs, 55));
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(rand.bright(cs, 40));
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,-40,0);
	}	
}
