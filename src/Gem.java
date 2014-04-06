import java.awt.Color;


public class Gem extends Drawable {
	protected PointTesselator tesselator;
	protected float spinOffset;
	protected float dark = 0.0f;
	public Gem(Scene<Drawable> scene, Rand rand) {
		this(scene,rand,buildHitbox());
	}
	
	public Gem(Scene<Drawable> scene, Rand rand, Hitbox hitbox) {
		super(scene,hitbox);
		tesselator = new PointTesselator();
		spinOffset = (float)(rand.nextDouble() * Math.PI *2);
		dark = (float)(rand.nextDouble() * Math.PI *2);
	}
	
	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-50,-40,-25),new P3D(50,40,25));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				box.getDrawable().getScene().remove(box.getDrawable());
				if (SoundManager.soundEnabled){
					Sound gem = new Sound("ding");
					gem.play();
				}
				GameState.instance.gems++;
			}		
		});
		return box;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
	
	public void tick() {
		time1 += 0.01f;
		delta += 0.01f;
		dark += 0.05f;
	}
	protected float time1 = 0.0f;
	protected float delta = 0.0f;
	public void draw(int darkness) {
		if (!isVisible())
			return;
		darkness = (int)(Math.sin(dark) * 40);
		tesselator.setBackgroundColor(Color.black);
		tesselator.translate(pos.x,pos.y+(float)(Math.sin(time1)*20),pos.z,false);
		tesselator.rotate(0,delta+spinOffset,0);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.color(0,153-darkness,51-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		
		tesselator.color(0,143-darkness,41-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(0,163-darkness,61-darkness);
		tesselator.point(50,0,-25);
		tesselator.point(0,40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(0,200-darkness,40-darkness);
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,40,0);

		tesselator.color(0,130-darkness,50-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		
		tesselator.color(0,100-darkness,10-darkness);
		tesselator.point(-50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(-50,0,25);
		
		tesselator.color(0,150-darkness,40-darkness);
		tesselator.point(50,0,-25);
		tesselator.point(0,-40,0);
		tesselator.point(50,0,25);
		
		tesselator.color(0,175-darkness,70-darkness);
		tesselator.point(-50,0,25);
		tesselator.point(50,0,25);
		tesselator.point(0,-40,0);
	}	
}
