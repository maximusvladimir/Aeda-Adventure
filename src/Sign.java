import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Sign extends Drawable {
	private PointTesselator tesselator;
	public Sign(Scene<Drawable> scene) {
		super(scene,new Hitbox(new P3D(-75,-100,-10), new P3D(75,80,10)));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x,pos.y,pos.z,false);
		int d = darkness;
		tesselator.color(137-d,91-d,46-d);
		tesselator.point(-6,-5,0);
		tesselator.point(-6,-100,0);
		tesselator.point(6,-100,0);
		tesselator.point(6,-100,0);
		tesselator.point(6,-5,0);
		tesselator.point(-6,-5,0);
		
		tesselator.color(156-d,129-d,39-d);
		tesselator.point(-75,70, 10);
		tesselator.point(75,70,10);
		tesselator.point(75,35,10);
		tesselator.point(75,35,10);
		tesselator.point(-75,35,10);
		tesselator.point(-75,70,10);
		
		tesselator.color(146-d,119-d,29-d);
		tesselator.point(-75,105,10);
		tesselator.point(75,105,10);
		tesselator.point(75,70,10);
		tesselator.point(75,70,10);
		tesselator.point(-75,70,10);
		tesselator.point(-75,105,10);
		
		tesselator.color(164-d,141-d,71-d);
		tesselator.point(-75,35, 10);
		tesselator.point(75,35,10);
		tesselator.point(75,-5,10);
		tesselator.point(75,-5,10);
		tesselator.point(-75,-5,10);
		tesselator.point(-75,35,10);
		
		Font3D font = new Font3D();
		font.color = Utility.adjustBrightness(Color.black, (int)(-d * 1.75f));
		font.loc = new P3D(-70,80,20);
		font.font = fontCache;
		//font.str = "\uFB39\uFB31\uFB40\uFB44\uFB1F\n\uFB2A\uFB48\uFB4F\n\uFB20\uFB2A\uFB2E\uFB41\uFB49\uFB4E";
		font.str = "\u046A\u046F\u0472\u0466\u046C\n\u0462\u0496\u049C\n\u0464\u04DC\u050A";
		// Says "I am a sign" in Greek.
		//font.str="\u0395\u03AF\u03BC\u03B1\u03B9\n\u03AD\u03BD\u03B1\n\u03C3\u03B7\u03BC\u03AC\u03B4\u03B9";
		// Says "Hello there" in Arabic.
		//font.str = "\u0645\u0631\u062D\u0628\u0627\n\u0647\u0646\u0627\u0643";
		tesselator.text(font);
	}
	private Font fontCache = new Font("Courier New",0,13);
	private String message = "Hello";
	public void setSignMessage(String message) {
		this.message = message;
	}
	public String getSignMessage() {
		return message;
	}
	public void qPressed() {
		isShowing = false;
		alreadyShown = true;
	}
	public boolean doSigns(Graphics g, IMain main) {
		if (isShowing)
			Utility.showDialog(getSignMessage(), g,main);
		return isShowing;
	}

	private boolean isShowing = false;
	private boolean alreadyShown = false;
	public void tick() {
		setInstanceLoc(getInstanceLoc().x,getInstanceLoc().z+250);
		float dist = getDistToPlayer();
		setInstanceLoc(getInstanceLoc().x,getInstanceLoc().z-250);
		if (dist < 220 && !alreadyShown) {
			isShowing = true;
		}
		else if (dist > 220) {
			alreadyShown = false;
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
