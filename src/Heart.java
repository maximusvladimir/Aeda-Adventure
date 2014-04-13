import java.awt.Color;


public class Heart extends Drawable {
	private PointTesselator tesselator;
	private float delta;
	private Color cnnsr;
	private float destructionDelta = 0.0f;
	private Color cnstart;
	public Heart(Scene<Drawable> scene) {
		super(scene,buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		cnnsr = new Color(170,20,20);
	}
	
	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-75,-50,-75),new P3D(75,100,75));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				if (((Heart)box.getDrawable()).doingDestroy)
					return;
				((Heart)box.getDrawable()).doDestroy();
			}		
		});
		return box;
	}
	
	private void doDestroy() {
		cnstart = cnnsr;
		doingDestroy = true;
		if (SoundManager.soundEnabled){
		Sound item = new Sound("item");
		item.play();
		}
		if ((int)GameState.instance.health < GameState.instance.healthPieces)
			GameState.instance.health++;
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0,delta,0);
		
		Color s = Utility.adjustBrightness(cnnsr, -darkness);
		
		tesselator.color(s);
		tesselator.point(-50,100,0);
		tesselator.point(0,48,0);
		tesselator.point(-100,50,0);
		
		tesselator.point(50,100,0);
		tesselator.point(0,48,0);
		tesselator.point(100,50,0);
		
		tesselator.point(-100,50,0);
		tesselator.point(100,50,0);
		tesselator.point(0,-50,10);
	}

	private float lastPoll = (float)(Math.random());
	
	public void tick() {
		if (!doingDestroy) {
			if (Main.findNumFramesDrawn() % 60 == 0)
				lastPoll = (float)(Math.random());
			delta += 0.02f * lastPoll + 0.01f;
			float ale = (float)(Math.sin(delta*0.5f)+1)*0.5f;
			tesselator.setTransparency((int)(ale * 190)+65);
			cnnsr = MathCalculator.lerp(new Color(170,20,20), Color.white, (1-ale)/3);
		}
		else {
			destructionDelta += 0.005f;
			if (destructionDelta >= 1) {
				getScene().remove(this);
			}
			else {
				cnnsr = MathCalculator.lerp(cnstart, Color.white, destructionDelta);
				tesselator.setTransparency(255-((int)(destructionDelta * 255)));
			}
		}
	}
	
	private boolean doingDestroy = false;

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
