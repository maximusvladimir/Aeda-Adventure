import java.awt.Color;


public class Couch extends Drawable {
	private PointTesselator tesselator;
	
	public Couch(Scene<Drawable> scene) {
		super(scene,genHitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	
	private static Hitbox genHitbox() {
		final Hitbox bas = new Hitbox(new P3D(-200,0,-50),new P3D(200,75,50));
		// let the player jump on top of the couch.
		bas.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				d0.getScene().getGamePlane().stopFall();
				d0.getScene().getGamePlane().setPlayerHeightOverride(0);
			}	
		});
		return bas;
	}
	
	private float delta = 0.0f;
	
	public void setDelta(float d) {
		delta = d;
	}
	
	public float getDelta() {
		return delta;
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0, delta, 0);
		
		Rand rand = new Rand(429);
		Color parch = Utility.adjustBrightness(new Color(103,96,80), -darkness);
		Color coushin = Utility.adjustBrightness(new Color(141,13,10), -darkness);
		Color legs = Utility.adjustBrightness(new Color(88,62,44), -darkness);
		
		tesselator.color(rand.bright(parch,15));
		tesselator.point(-200,0,-50);
		tesselator.point(200,0,-50);
		tesselator.point(200,75,-50);
		tesselator.color(rand.variate(parch,15));
		tesselator.point(200,75,-50);
		tesselator.point(-200,75,-50);
		tesselator.point(-200,0,-50);
		
		tesselator.color(rand.variate(parch,15));
		tesselator.point(-200,0,50);
		tesselator.point(200,0,50);
		tesselator.point(200,75,50);
		tesselator.color(rand.variate(parch,15));
		tesselator.point(200,75,50);
		tesselator.point(-200,75,50);
		tesselator.point(-200,0,50);
		
		// armrest
		tesselator.color(rand.bright(parch, 25));
		tesselator.point(-200,0,50);
		tesselator.point(-200,0,-50);
		tesselator.point(-225,150,-50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(-225,150,-50);
		tesselator.point(-225,125,50);
		tesselator.point(-200,0,50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(-200,75,50);
		tesselator.point(-200,75,-50);
		tesselator.point(-225,150,-50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(-225,150,-50);
		tesselator.point(-225,125,50);
		tesselator.point(-200,75,50);
		
		tesselator.color(parch);
		tesselator.point(-225,125,50);
		tesselator.point(-200,75,50);
		tesselator.point(-200,0,50);
		tesselator.color(rand.variate(parch,15));
		tesselator.point(-225,150,-50);
		tesselator.point(-200,75,-50);
		tesselator.point(-200,0,-50);
		
		
		// armrests
		tesselator.color(rand.bright(parch, 25));
		tesselator.point(200,0,50);
		tesselator.point(200,0,-50);
		tesselator.point(225,150,-50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(225,150,-50);
		tesselator.point(225,125,50);
		tesselator.point(200,0,50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(200,75,50);
		tesselator.point(200,75,-50);
		tesselator.point(225,150,-50);
		tesselator.color(rand.variate(parch, 25));
		tesselator.point(225,150,-50);
		tesselator.point(225,125,50);
		tesselator.point(200,75,50);
		
		tesselator.color(parch);
		tesselator.point(225,125,50);
		tesselator.point(200,75,50);
		tesselator.point(200,0,50);
		tesselator.color(rand.variate(parch,15));
		tesselator.point(225,150,-50);
		tesselator.point(200,75,-50);
		tesselator.point(200,0,-50);
		
		// back rest
		tesselator.color(rand.bright(parch.darker(), 25));
		tesselator.point(-200,75,-50);
		tesselator.point(-225,150,-50);
		tesselator.point(225,150,-50);
		tesselator.color(rand.bright(parch.darker(), 25));
		tesselator.point(225,150,-50);
		tesselator.point(200,75,-50);
		tesselator.point(-200,75,-50);
		
		// legs
		final float hu = -70;
		tesselator.color(rand.variate(legs, 25));
		tesselator.point(-175,0,-45);
		tesselator.point(-155,0,-35);
		tesselator.point(-155,hu,-35);
		tesselator.color(rand.variate(legs, 25));
		tesselator.point(-175,0,-45);
		tesselator.point(-175,hu,-45);
		tesselator.point(-155,hu,-35);
		
		tesselator.color(rand.variate(legs, 25));
		tesselator.point(175,0,-45);
		tesselator.point(155,0,-35);
		tesselator.point(155,hu,-35);
		tesselator.color(rand.bright(legs, 25));
		tesselator.point(175,0,-45);
		tesselator.point(175,hu,-45);
		tesselator.point(155,hu,-35);
		
		tesselator.color(rand.bright(legs, 25));
		tesselator.point(-175,0,45);
		tesselator.point(-155,0,35);
		tesselator.point(-155,hu,35);
		tesselator.color(rand.variate(legs, 25));
		tesselator.point(-175,0,45);
		tesselator.point(-175,hu,45);
		tesselator.point(-155,hu,35);
		
		tesselator.color(rand.bright(legs, 25));
		tesselator.point(175,0,45);
		tesselator.point(155,0,35);
		tesselator.point(155,hu,35);
		tesselator.color(rand.variate(legs, 25));
		tesselator.point(175,0,45);
		tesselator.point(175,hu,45);
		tesselator.point(155,hu,35);
		
		tesselator.color(rand.variate(coushin, 25));
		tesselator.point(-200,75,-50);
		tesselator.point(200,75,-50);
		tesselator.point(200,75,50);
		tesselator.color(rand.variate(coushin, 25));
		tesselator.point(-200,75,-50);
		tesselator.point(-200,75,50);
		tesselator.point(200,75,50);
	}
	
	public boolean isCullable() {
		return false;
	}

	public void tick() {
		
	}
	
	public PointTesselator getTesselator() {
		return tesselator;
	}

}
