import java.awt.Color;


public class Key extends Drawable {
	private PointTesselator tesselator;
	public Key(Scene<Drawable> scene) {
		super(scene, buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		delter = (float)(Rand.random()*6.5f);
	}
	
	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-100,-1000,-100),new P3D(100,1000,100));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				//box.getDrawable().getScene().remove(box.getDrawable());
				if (SoundManager.soundEnabled){
				Sound item = new Sound("item");
				item.play();
				}
				d0.getScene().getLevel().addMessage("You've found a key!\nYou now have access to a secret location.\nPress 'Z' to see the location of the secret level.", "FISHOILLAMPYAY");
				d0.getScene().getLevel().setActiveMessage("FISHOILLAMPYAY");
				GameState.instance.hasKey = true;
				GameState.save();
				d0.getScene().remove(box.getDrawable());
			}
		});
		return box;
	}
	
	private float delter = 0;
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0, delter, 0);
		
		Rand paul = new Rand(2); // haha (get it?, google it if you don't)
		Color keyColor = Utility.adjustBrightness(new Color(230,206,43), -darkness);
		
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(-100,30,0);
		tesselator.point(-25,30,0);
		tesselator.point(-25,-30,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(-100,30,0);
		tesselator.point(-100,-30,0);
		tesselator.point(-25,-30,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(-25,30,0);
		tesselator.point(-25,10,0);
		tesselator.point(100,10,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(-25,30,0);
		tesselator.point(100,30,0);
		tesselator.point(100,10,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(90,10,0);
		tesselator.point(100,10,0);
		tesselator.point(100,-20,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(90,10,0);
		tesselator.point(90,-20,0);
		tesselator.point(100,-20,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(70,10,0);
		tesselator.point(80,10,0);
		tesselator.point(80,-10,0);
		tesselator.color(paul.bright(keyColor, 40));
		tesselator.point(70,10,0);
		tesselator.point(70,-10,0);
		tesselator.point(80,-10,0);
	}

	public void tick() {
		delter += 0.04f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
