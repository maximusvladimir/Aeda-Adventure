import java.awt.Color;


public class Lamppost extends Drawable {
	private PointTesselator tesselator;
	
	public Lamppost(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		delta = -3.1415926535f / 1.5f;
	}
	
	public void setLampOn(boolean value) {
		lampOn = value;
	}
	
	public boolean isLampOn() {
		return lampOn;
	}
	
	private boolean lampOn = true;
	private float windX = 0.0f;
	private float lampDelta = 0.0f;
	private float delta = 0.0f;
	public void draw(int darkness) {
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0,delta,0);
		final float height = 600;
		float winX = (float)(Math.sin(windX) * 20);
		float winY = 20-(float)(Math.abs(Math.cos(windX)) * 10);
		float offsetX = (float)(Math.cos(windX * 0.75) * 10);
		float sizer0 = (float)(Math.sin(windX * 1.2f) * 5);
		float sizer1 = (float)(Math.sin(windX * 1.2f + 0.452) * 5);
		float sizer2 = (float)(Math.sin(windX * 1.2f + 41.2349) * 5);
		Color postColor = Utility.adjustBrightness(new Color(90,70,60), -darkness);
		Color lampFrameColor = Utility.adjustBrightness(Color.darkGray, -darkness);
		// post
		tesselator.color(Utility.adjustBrightness(postColor, -15));
		tesselator.point(-50,0,-20);
		tesselator.point(-90,0,-20);
		tesselator.point(-90,height,-20);
		tesselator.color(Utility.adjustBrightness(postColor, 5));
		tesselator.point(-90,height,-20);
		tesselator.point(-50,height,-20);
		tesselator.point(-50,0,-20);
		
		tesselator.color(Utility.adjustBrightness(postColor, 10));
		tesselator.point(-50,0,20);
		tesselator.point(-90,0,20);
		tesselator.point(-90,height,20);
		tesselator.color(Utility.adjustBrightness(postColor, -5));
		tesselator.point(-90,height,20);
		tesselator.point(-50,height,20);
		tesselator.point(-50,0,20);
		
		tesselator.color(Utility.adjustBrightness(postColor, 2));
		tesselator.point(-50,0,20);
		tesselator.point(-50,0,-20);
		tesselator.point(-50,height,-20);
		tesselator.color(Utility.adjustBrightness(postColor, 15));
		tesselator.point(-50,height,-20);
		tesselator.point(-50,height,20);
		tesselator.point(-50,0,20);
		
		tesselator.color(Utility.adjustBrightness(postColor, 2));
		tesselator.point(-90,0,20);
		tesselator.point(-90,0,-20);
		tesselator.point(-90,height,-20);
		tesselator.color(Utility.adjustBrightness(postColor, 15));
		tesselator.point(-90,height,-20);
		tesselator.point(-90,height,20);
		tesselator.point(-90,0,20);
		
		// cross post
		tesselator.color(Utility.adjustBrightness(postColor, -15));
		tesselator.point(-150,height-70,-25);
		tesselator.point(80,height-70,-25);
		tesselator.point(80,height-90,-25);
		tesselator.color(Utility.adjustBrightness(postColor, 4));
		tesselator.point(-150,height-70,-25);
		tesselator.point(-150,height-90,-25);
		tesselator.point(80,height-90,-25);
		
		tesselator.color(Utility.adjustBrightness(postColor, -2));
		tesselator.point(-150,height-70,25);
		tesselator.point(80,height-70,25);
		tesselator.point(80,height-90,25);
		tesselator.color(Utility.adjustBrightness(postColor, -8));
		tesselator.point(-150,height-70,25);
		tesselator.point(-150,height-90,25);
		tesselator.point(80,height-90,25);
		
		tesselator.color(Utility.adjustBrightness(postColor, -30));
		tesselator.point(-150,height-90,25);
		tesselator.point(-150,height-90,-25);
		tesselator.point(80,height-90,-25);
		tesselator.color(Utility.adjustBrightness(postColor, -20));
		tesselator.point(80,height-90,-25);
		tesselator.point(80,height-90,25);
		tesselator.point(-150,height-90,25);
		
		tesselator.color(Utility.adjustBrightness(postColor, -10));
		tesselator.point(-150,height-90,25);
		tesselator.point(-150,height-70,25);
		tesselator.point(-150,height-70,-25);
		tesselator.color(Utility.adjustBrightness(postColor, 10));
		tesselator.point(-150,height-70,-25);
		tesselator.point(-150,height-90,-25);
		tesselator.point(-150,height-90,25);
		
		tesselator.color(Utility.adjustBrightness(postColor, -10));
		tesselator.point(80,height-90,25);
		tesselator.point(80,height-70,25);
		tesselator.point(80,height-70,-25);
		tesselator.color(Utility.adjustBrightness(postColor, 10));
		tesselator.point(80,height-70,-25);
		tesselator.point(80,height-90,-25);
		tesselator.point(80,height-90,25);
		
		
		// lamp
		// TODO adjust color based on light
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness));
		tesselator.point(75,height-90,20);
		tesselator.point(65,height-90,25);
		tesselator.point(55+offsetX,height-160+winY,winX);
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness - 10));
		tesselator.point(80,height-90,-20);
		tesselator.point(65,height-90,-25);
		tesselator.point(55+offsetX,height-160+winY,winX);
		
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness - 15));
		tesselator.point(10,height-90,20);
		tesselator.point(20,height-90,25);
		tesselator.point(35+offsetX,height-160+winY,winX);
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness - 5));
		tesselator.point(10,height-90,-20);
		tesselator.point(20,height-90,-25);
		tesselator.point(35+offsetX,height-160+winY,winX);
		
		//lamp base
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness - 20));
		tesselator.point(35+offsetX,height-160+winY,winX-20);
		tesselator.point(35+offsetX,height-160+winY,winX+20);
		tesselator.point(55+offsetX,height-160+winY,winX+20);
		tesselator.color(Utility.adjustBrightness(lampFrameColor, -darkness - 10));
		tesselator.point(35+offsetX,height-160+winY,winX-20);
		tesselator.point(55+offsetX,height-160+winY,winX-20);
		tesselator.point(55+offsetX,height-160+winY,winX+20);
		
		// light
		if (isLampOn()) {
			tesselator.color(lightColor);
			tesselator.point(55+offsetX-4+sizer0,height-155+winY,winX-10+sizer2);
			tesselator.point(45+offsetX+sizer1,height-130+winY,winX+sizer1);
			tesselator.point(35+offsetX+5+sizer2,height-155+winY,winX+20+sizer0);
		}
	}
	
	public float getWindDelta() {
		return windX;
	}

	private Color lightColor = Color.yellow;
	public void tick() {
		windX += 0.025f;
		lampDelta += 0.02f * Math.random();
		float hue = 1-(float)(Math.abs((Math.cos(lampDelta)*0.1666666666666)));
		lightColor = new Color(Color.HSBtoRGB(1-hue, 1, 1));
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
