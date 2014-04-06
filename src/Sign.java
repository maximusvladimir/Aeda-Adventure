import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Sign extends Drawable {
	private PointTesselator tesselator;
	public Sign(Scene<Drawable> scene) {
		super(scene,new P3D(-75,-100,-10), new P3D(75,80,10));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x,pos.y,pos.z,false);
		
		tesselator.color(137,91,46);
		tesselator.point(-6,-5,0);
		tesselator.point(-6,-100,0);
		tesselator.point(6,-100,0);
		tesselator.point(6,-100,0);
		tesselator.point(6,-5,0);
		tesselator.point(-6,-5,0);
		
		tesselator.color(156,129,39);
		tesselator.point(-75,70, 10);
		tesselator.point(75,70,10);
		tesselator.point(75,35,10);
		tesselator.point(75,35,10);
		tesselator.point(-75,35,10);
		tesselator.point(-75,70,10);
		
		tesselator.color(146,119,29);
		tesselator.point(-75,105,10);
		tesselator.point(75,105,10);
		tesselator.point(75,70,10);
		tesselator.point(75,70,10);
		tesselator.point(-75,70,10);
		tesselator.point(-75,105,10);
		
		tesselator.color(164,141,71);
		tesselator.point(-75,35, 10);
		tesselator.point(75,35,10);
		tesselator.point(75,-5,10);
		tesselator.point(75,-5,10);
		tesselator.point(-75,-5,10);
		tesselator.point(-75,35,10);
		
		Font3D font = new Font3D();
		font.color = Color.black;
		font.loc = new P3D(-70,80,20);
		font.font = fontCache;
		//font.str = "\uFB39\uFB31\uFB40\uFB44\uFB1F\n\uFB2A\uFB48\uFB4F\n\uFB20\uFB2A\uFB2E\uFB41\uFB49\uFB4E";
		// Says "I am a sign" in Greek.
		font.str="\u0395\u03AF\u03BC\u03B1\u03B9\n\u03AD\u03BD\u03B1\n\u03C3\u03B7\u03BC\u03AC\u03B4\u03B9";
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
	public boolean doSigns(Graphics g, Main main) {
		if (isShowing)
			Utility.showDialog(getSignMessage(), g,main);
		return isShowing;
	}
	public void setUserPosition(float x, float z) {
		usx = x;
		usz = z;
	}
	private float usx, usz;
	private boolean isShowing = false;
	private boolean alreadyShown = false;
	public void tick() {
		//System.out.println(usz + "," + staticPos.z);
		float dx = getInstanceLoc().x - usx;
		//float dy = pos.y;
		float dz = (getInstanceLoc().z - usz)+850; // make it slightly in front of the sign.
		float dist = (float)Math.sqrt(dx*dx+dz*dz);
		//System.out.println(dist);
		if (dist < 270 && !alreadyShown) {
			isShowing = true;
		}
		else if (dist > 270) {
			alreadyShown = false;
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
