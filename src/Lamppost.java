import java.awt.Color;


public class Lamppost extends Drawable implements IGlowable {
	private PointTesselator tesselator;
	
	private Color[] startTileColor = null;
	private Color[] endTileColor = null;
	private int tile0X, tile0Z;
	public Lamppost(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		//delta = -3.1415926535f / 1.5f;
		lampDelta = (float)(Math.random() * 23847);
		windX = (float)(Math.PI * 2 * Math.random());
	}
	
	public void updateInstLoc() {
		int[] t = getScene().getGamePlane().getWorldPointAsGridPoint(this);
		tile0X = t[0];
		tile0Z = t[1];
		startTileColor = new Color[4];
		endTileColor = new Color[4];
		startTileColor[0] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z);
		endTileColor[0] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z).brighter();
		startTileColor[1] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z+1);
		endTileColor[1] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z+1).brighter();
		startTileColor[2] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z+1);
		endTileColor[2] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z+1).brighter();
		startTileColor[3] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z);
		endTileColor[3] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z).brighter();
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
	private float side = 0.0f;
	public void draw(int darkness) {
		if (lampOn)
			darkness = darkness - 30;
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0,delta,0);
		final float height = 600;
		float winX = (float)(MathCalculator.sin(windX) * 20);
		side = winX;
		float winY = 20-(float)(Math.abs(MathCalculator.cos(windX)) * 10);
		float offsetX = (float)(MathCalculator.cos(windX * 0.75) * 10);
		float sizer0 = (float)(MathCalculator.sin(windX * 1.2f) * 5);
		float sizer1 = (float)(MathCalculator.sin(windX * 1.2f + 0.452) * 5);
		float sizer2 = (float)(MathCalculator.sin(windX * 1.2f + 41.2349) * 5);
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
			getScene().getGamePlane().setColorPoint(tile0X, tile0Z, getTileColor(0,0,false));
			getScene().getGamePlane().setColorPoint(tile0X, tile0Z+1, getTileColor(0,1,false));
			getScene().getGamePlane().setColorPoint(tile0X+1, tile0Z+1, getTileColor(1,1,false));
			getScene().getGamePlane().setColorPoint(tile0X+1, tile0Z, getTileColor(1,0,false));
			interpolationDisappearValue = 1;
		}
		else {
			if (interpolationDisappearValue == 1) {
				resetLightingValues();
			}
			getScene().getGamePlane().setColorPoint(tile0X, tile0Z, getTileColor(0,0,true));
			getScene().getGamePlane().setColorPoint(tile0X, tile0Z+1, getTileColor(0,1,true));
			getScene().getGamePlane().setColorPoint(tile0X+1, tile0Z+1, getTileColor(1,1,true));
			getScene().getGamePlane().setColorPoint(tile0X+1, tile0Z, getTileColor(1,0,true));
			if (interpolationDisappearValue > 0)
				interpolationDisappearValue -= 0.01f;
		}
	}
	
	private Color getTileColor(int tileX, int tileZ,boolean stop) {
		float s = (side + 20)/40;
		int n = locToIndex(tileX,tileZ);
		if (stop && interpolationDisappearValue <= 0.0f)
			return startTileColor[n];
		else if (stop)
			return MathCalculator.lerp(startTileColor[n],endTileColor[n],interpolationDisappearValue);
		else
			return MathCalculator.lerp(startTileColor[n], new Color(253,234,159), s*0.25f + 0.25f);
	}
	
	private int locToIndex(int tileX, int tileZ){
		int n = 0;
		if (tileX == 0 && tileZ == 1)
			n = 1;
		if (tileX == 1 && tileZ == 1)
			n = 2;
		if (tileX == 1 && tileZ == 0)
			n = 3;
		return n;
	}
	
	private void resetLightingValues() {
		int n = locToIndex(0,0);
		endTileColor[n] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z);
		n = locToIndex(1,0);
		endTileColor[n] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z);
		n = locToIndex(1,1);
		endTileColor[n] = getScene().getGamePlane().getColorPoint(tile0X+1, tile0Z+1);
		n = locToIndex(0,1);
		endTileColor[n] = getScene().getGamePlane().getColorPoint(tile0X, tile0Z+1);
	}
	
	private float interpolationDisappearValue = 0.0f;
	
	public float getWindDelta() {
		return windX;
	}
	
	public Color getGlowColor() {
		return lightColor;
	}

	public Color lightColor = Color.yellow;
	public void tick() {
		windX += 0.025f;
		lampDelta += 0.02f * Math.random();
		float hue = 1-(float)(Math.abs((MathCalculator.cos(lampDelta)*0.1666666666666)));
		lightColor = new Color(Color.HSBtoRGB(1-hue, 1, 1));
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
