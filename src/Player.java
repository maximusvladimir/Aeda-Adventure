import java.awt.Color;
import java.util.Random;

public class Player extends Character {
	// The player currently has 62 triangles, which is 186 points.

	private PointTesselator tesselator;
	private FacialExpression mouthExpression;
	private FacialExpression eyeExpression;
	private float armsLag = 0.0f;
	public Player(Scene<Drawable> scene) {
		super(scene, new Hitbox(new P3D(-110, -225, -40), new P3D(110, 140, 40)));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		mouthExpression = FacialExpression.MOUTH_SMILE;
		eyeExpression = FacialExpression.EYEBROWS_INNOCENTLIKE;
		if (GameState.instance.playerGUID.equals("Zelda") || GameState.instance.playerGUID.equals("Sheik")) {
			isASheik = true;
		}
		else
			isASheik = false;
	}
	
	public void setFaceEmotion(FacialExpression f) {
		switch (f) {
		case EMOTION_ANGRY:
			setEyebrowExpression(FacialExpression.EYEBROWS_ANGRY);
			setMouthExpression(FacialExpression.MOUTH_FROWN);
			break;
		case EMOTION_FOCUSED:
			setEyebrowExpression(FacialExpression.EYEBROWS_NEUTRAL);
			setMouthExpression(FacialExpression.MOUTH_FROWN);
			break;
		case EMOTION_HAPPY:
			setEyebrowExpression(FacialExpression.EYEBROWS_INNOCENTLIKE);
			setMouthExpression(FacialExpression.MOUTH_SMILE);
			break;
		case EMOTION_PLAIN:
			setEyebrowExpression(FacialExpression.EYEBROWS_NEUTRAL);
			setMouthExpression(FacialExpression.MOUTH_NEUTRAL);
			break;
		case EMOTION_SAD:
			setEyebrowExpression(FacialExpression.EYEBROWS_INNOCENTLIKE);
			setMouthExpression(FacialExpression.MOUTH_FROWN);
			break;
		case EMOTION_REASSURING:
			setEyebrowExpression(FacialExpression.EYEBROWS_INNOCENTLIKE);
			setMouthExpression(FacialExpression.MOUTH_SMILE);
		default:
			break;
		}
	}
	
	public void setMouthExpression(FacialExpression f) {
		mouthExpression = f;
	}
	
	public void setEyebrowExpression(FacialExpression f) {
		eyeExpression = f;
	}
	
	public FacialExpression getMouthExpression() {
		return mouthExpression;
	}
	
	public FacialExpression getEyebrowExpression() {
		return eyeExpression;
	}

	public void setPlayerDelta(float d) {
		delta = d + ni;
	}
	
	public void hit() {
		if (isHitting())
			return;
		hitDelta = 1.0f;
	}
	
	public void setEyesOpen(boolean value) {
		eyesOpen = value;
	}
	
	public boolean isEyesOpen() {
		return eyesOpen;
	}
	private boolean eyesOpen = true;
	public boolean isHitting() {
		if (hitDelta <= 0.0001f) {
			return false;
		}
		return true;
	}
	private float hitDelta = 0.0f;
	public Color playerColor;
	private final float ni = (float) (Math.PI / 180.0f * 90);
	private final float re = (float) (Math.PI * 2);
	float delta = 0.0f;
	float time0 = 0;
	float time1 = 0;
	float time2 = 0;
	float time3 = 0;
	float jump = 0.0f;
	float jumploss = 0.1f;
	public boolean moving = false;
	Rand va = new Rand(2);
	private boolean isASheik = true;
	public float getPlayerDelta() {
		return delta;
	}
	
	private void drawCurlThing(Color base, P3D startT0, P3D startT1, P3D startB0, P3D outward) {
		tesselator.color(va.variate(Utility.adjustBrightness(base,va.nextInt(20)), 15));
		tesselator.point(startT0);
		tesselator.point(outward);
		tesselator.point(startT1);
		
		tesselator.color(va.variate(Utility.adjustBrightness(base,va.nextInt(20)), 15));
		tesselator.point(startT1);
		tesselator.point(outward);
		tesselator.point(startB0);
		
		tesselator.color(va.variate(Utility.adjustBrightness(base,va.nextInt(20)), 15));
		tesselator.point(startT0);
		tesselator.point(outward);
		tesselator.point(startB0);
	}
	
	public void draw(int d) {
		if (!isVisible())
			return;
		va.setSeed(2);
		delta = delta % re;
		time0 += 0.001f;
		if (moving) {
			time2 += 0.04f;//was 0.025f
			time1 += 0.09f;
			armsLag = (float)(Math.abs(Math.sin(time1*0.2)));
		} else {
			if (Math.sin(time2) < -0.1 || Math.sin(time2) > 0.1) {
				time2 += 0.053f;
				// don't leave the player in a weird position when they
				// stop moving.
			}
			time1 += 0.01f;
		}
		float feet = (float) (Math.sin(time2) * 80);
		float feer = (float) (Math.cos(time2) * 10);
		float feera = (float) (Math.cos(time2+(Math.PI)) * 10);
		float adj1 = (float) (Math.sin(time1 * 0.2) * 5) + 10;

		float feet2 = (float) (Math.sin(time2 + Math.PI) * 80);
		float feer2 = (float) (Math.cos(time2 + Math.PI) * 10);
		float feer2a = (float) (Math.cos(time2 + (2*Math.PI)) * 10);
		float adj2 = (float) (Math.cos((time1 + 295) * 0.2) * 5) - 10;
		
		float alt1 = feet2;
		if (alt1 < 0)
			alt1 = 0;
		float alt2 = feet;
		if (alt2 < 0)
			alt2 = 0;
		
		//float hatJump = (float) (Math.sin(time2 * 1.5) * 8);
		float hatJump = (float) (Math.sin(time2 * 1.5) * 16);
		float selShak = (float) (Math.cos(time2 * 1.4) * 9);
		//float offsetEyes = 5;// (float)(Math.cos(time0*20)*6);
		float offsetEyes = 0;
		if (eyeExpression == FacialExpression.EYEBROWS_ANGRY)
			offsetEyes = 6;
		else if (eyeExpression == FacialExpression.EYEBROWS_INNOCENTLIKE)
			offsetEyes = -4;
		else
			offsetEyes = 0;
		float wind = (float) (Math.sin(time0 * 30.4) * 20)-10;
		// float wind =0.0f;
		//float mouth = (float) (Math.sin(time0 * 39) * 10);
		float armLift = 0.0f;
		float arm1 = (float) (Math.sin(time2) * 20);
		float arm2 = (float) (Math.sin(time2 + Math.PI) * 20);
		if (hitDelta > 0.0f) {
			hitDelta -= 0.04f;
			arm2 = -Math.abs((float)(hitDelta * 20));
			//armLift = Math.abs((float)(hitDelta * 40));
			if (hitDelta <= 0.05f)
				hitDelta = 0.0f;
		}
		//if (mouth < 0)
			//mouth = -mouth;
		//mouth = mouth - 4;
		tesselator.rotate(0, (float) Math.PI + delta, 0);
		if (jump > 0) {
			jump -= jumploss;
			jumploss += 2*(9.8/getScene().getScreen().getMain().getFPS());
		}
		tesselator.translate(pos.x, pos.y + jump + selShak, pos.z, false);

		// hair and hat
		//P3D pinnacle = new P3D(-34, 160 + (8 - hatJump), -62);
		P3D pinnacle = new P3D(-34, 160 + (8 - (hatJump/1.6f)), -62);
		 P3D hatExt = new P3D(wind,135+hatJump,140);
		 if (isASheik) {
			 hatExt = new P3D(wind,170+hatJump,20);
			}
		// P3D hatExt = new P3D(wind+60,240+hatJump,-20);
		//P3D hatExt = new P3D(wind - 15, 170 + hatJump, 20);
		// 210-d, 184-d, 149-d
		Color clothBase = new Color(MathCalculator.colorLock(playerColor
				.getRed() - d), MathCalculator.colorLock(playerColor.getGreen()
				- d), MathCalculator.colorLock(playerColor.getBlue() - d));
		Color hairBase = new Color(MathCalculator.colorLock(126 - d),
				MathCalculator.colorLock(99 - d),
				MathCalculator.colorLock(50 - d));
		Color skinBase = new Color(MathCalculator.colorLock(205 - d),
				MathCalculator.colorLock(179 - d),
				MathCalculator.colorLock(144 - d));
		hairBase = hairBase.brighter();
		Color swapBase = new Color(clothBase.getRGB());
		if (isASheik) {
			clothBase = sheikWhite;
		}
		tesselator.color(clothBase);
		tesselator.point(pinnacle);
		tesselator.point(-40, 100, 50);
		tesselator.point(hatExt);
		tesselator.color(clothBase.getRed() + 10, clothBase.getGreen() + 10,
				clothBase.getBlue() + 10);
		tesselator.point(hatExt);
		tesselator.point(pinnacle);
		tesselator.point(40, 100, 50);// was +50
		tesselator.color(clothBase.getRed() - 10, clothBase.getGreen() - 23,
				clothBase.getBlue() + 10);
		tesselator.point(40, 100, 50);
		tesselator.point(hatExt);
		tesselator.point(-40, 100, 50);
		if (isASheik) {
			clothBase = swapBase;
		}
		/*
		 * tesselator.point(pinnacle); tesselator.point(-40,100,50);
		 * tesselator.point(hatExt);
		 * tesselator.color(clothBase.getRed()+10,clothBase
		 * .getGreen()+10,clothBase.getBlue()+10); tesselator.point(hatExt);
		 * tesselator.point(pinnacle); tesselator.point(40,100,-50);//was +50
		 * tesselator
		 * .color(clothBase.getRed()-10,clothBase.getGreen()-23,clothBase
		 * .getBlue()+10); tesselator.point(40,100,50);
		 * tesselator.point(hatExt); tesselator.point(-40,100,50);
		 * tesselator.color
		 * (clothBase.getRed()+10,clothBase.getGreen()+14,clothBase
		 * .getBlue()+20); tesselator.point(hatExt);
		 * tesselator.point(40,100,50); tesselator.point(40,100,-50);
		 */

		// actual hair

		// tesselator.color(204-d, 190-d, 51-d);
		tesselator.color(hairBase);
		tesselator.point(-40, 100, -50);
		tesselator.point(40, 100, -50);
		tesselator.point(pinnacle);

		// tesselator.color(214-d, 206-d, 65-d);
		tesselator.color(hairBase.getRed() + 15, hairBase.getGreen() + 15,
				hairBase.getBlue() + 20);
		tesselator.point(pinnacle);
		tesselator.point(40, 100, 50);
		tesselator.point(40, 100, -50);

		// curl things:
		drawCurlThing(hairBase, new P3D(-40,130,-20), new P3D(-40,110,20), new P3D(-40,100,0),new P3D(-60,90+wind,0));
		drawCurlThing(hairBase, new P3D(-38,130,-40), new P3D(-38,140,-30), new P3D(-40,110,-35),new P3D(-55,160+wind,-30));
		
		drawCurlThing(hairBase, new P3D(-10,130,-60),new P3D(15,120,-60),new P3D(0,100,-60),new P3D(25,140+wind,-90));
		
		
		drawCurlThing(hairBase, new P3D(-35,100,-30), new P3D(-35,90,10), new P3D(-35,60,0), new P3D(-70,50,-10));
		drawCurlThing(hairBase, new P3D(-35,70,20), new P3D(-35,90,40), new P3D(-35,50,30), new P3D(-75,30,20));

		// tesselator.color(204-d, 194-d, 55-d);
		tesselator.color(hairBase.getRed() - 20, hairBase.getGreen() - 25,
				hairBase.getBlue() - 20);
		tesselator.point(40, 100, 50);
		tesselator.point(pinnacle);
		tesselator.point(-40, 100, 50);

		// tesselator.color(222-d, 219-d, 79-d);
		tesselator.color(hairBase.getRed() - 20, hairBase.getGreen() - 20,
				hairBase.getBlue() - 20);
		tesselator.point(-40, 100, 50);
		tesselator.point(pinnacle);
		tesselator.point(-40, 100, -50);

		// head
		tesselator.color(skinBase);
		tesselator.point(40, 100, -50);
		tesselator.point(30, 25, -50);
		tesselator.point(35, 25, 30);

		tesselator.point(35, 25, 30);
		tesselator.point(40, 100, 50);
		tesselator.point(40, 100, -50);

		tesselator.point(35, 25, 30);
		tesselator.point(-35, 25, 30);
		tesselator.point(-40, 100, 50);

		tesselator.point(-40, 100, 50);
		tesselator.point(40, 100, 50);
		tesselator.point(35, 25, 30);//49

		tesselator.point(35, 25, -41);
		tesselator.point(-35, 25, -41);
		tesselator.point(-40, 100, -30);

		tesselator.point(-40, 100, -50);
		tesselator.point(40, 100, -50);
		tesselator.point(35, 25, -41);

		tesselator.point(-40, 100, -50);
		tesselator.point(-30, 25, -50);
		tesselator.point(-35, 25, 30);

		tesselator.point(-35, 25, 30);
		tesselator.point(-40, 100, 30);
		tesselator.point(-40, 100, -50);
		
		// chin things
		tesselator.point(-30,25,-50);
		tesselator.point(0,15,-45);
		tesselator.point(30,25,-50);
		tesselator.point(0,15,-45);
		tesselator.point(-30,25,-50);
		tesselator.point(-30,25,-20);
		tesselator.point(0,15,-45);
		tesselator.point(30,25,-50);
		tesselator.point(30,25,-20);

		// face
		float faceDist = -57;
		if (isEyesOpen()) {
		tesselator.color(255, 255, 255);
		tesselator.point(-12, 80, faceDist);
		tesselator.point(-12, 70, faceDist);
		tesselator.point(-22, 70, faceDist);
		tesselator.point(-32, 80, faceDist);
		tesselator.point(-32, 70, faceDist);
		tesselator.point(-22, 70, faceDist);
		tesselator.point(12, 80, faceDist);
		tesselator.point(12, 70, faceDist);
		tesselator.point(22, 70, faceDist);
		tesselator.point(32, 80, faceDist);
		tesselator.point(32, 70, faceDist);
		tesselator.point(22, 70, faceDist);
		if (isASheik) {
			tesselator.color(206,30,28);
		}
		else
			tesselator.color(0,0,180);
		tesselator.point(-12, 80, faceDist);
		tesselator.point(-22, 70, faceDist);
		tesselator.point(-32, 80, faceDist);
		tesselator.point(12, 80, faceDist);
		tesselator.point(22, 70, faceDist);
		tesselator.point(32, 80, faceDist);
		}
		else {
			tesselator.color(0,0,0);
			tesselator.point(-28,80,faceDist);
			tesselator.point(-28,75,faceDist);
			tesselator.point(-4,75,faceDist);
			
			tesselator.point(28,80,faceDist);
			tesselator.point(28,75,faceDist);
			tesselator.point(4,75,faceDist);
		}
		
		//mouth cover.
		if (isASheik) {
			float w4w = wind / 3.5f;
			tesselator.color(sheikWhite);
			tesselator.point(-45,48,-62);
			tesselator.point(45,56,-66);
			tesselator.point(-26,15,-68 + w4w);
			
			tesselator.point(-26,15,-68 + w4w);
			tesselator.point(-45,48,-62);
			tesselator.point(-50,25,-62);
			
			tesselator.point(-45,48,-62);
			tesselator.point(-50,25,-62);
			tesselator.point(-45,54,10);
			
			tesselator.point(-26,15,-68 + w4w);
			tesselator.point(45,56,-66);
			tesselator.point(45,25,-60 + w4w);
			
			tesselator.point(45,25,-60 + w4w);
			tesselator.point(45,56,-66);
			tesselator.point(45,60,15);
		}
		

		/*tesselator.color(121, 49, 47);
		tesselator.point(-20, 48, -60);
		tesselator.point(0, 38 + mouth, -60);
		tesselator.point(20, 48, -60);*/
		tesselator.color(121-d, 49-d, 47-d);
		float mouthDist = -54;
		if (mouthExpression == FacialExpression.MOUTH_FROWN) {
			tesselator.point(-20,38,mouthDist);
			tesselator.point(-10,48,mouthDist);
			tesselator.point(0,38,mouthDist);
			tesselator.point(-10,48,mouthDist);
			tesselator.point(10,48,mouthDist);
			tesselator.point(0,38,mouthDist);
			tesselator.point(20,38,mouthDist);
			tesselator.point(10,48,mouthDist);
			tesselator.point(0,38,mouthDist);
		}
		else if (mouthExpression == FacialExpression.MOUTH_SMILE) {
			tesselator.point(-25,52,mouthDist);
			tesselator.point(-10,38,mouthDist);
			tesselator.point(0,48,mouthDist);
			tesselator.point(-10,38,mouthDist);
			tesselator.point(10,38,mouthDist);
			tesselator.point(0,48,mouthDist);
			tesselator.point(20,48,mouthDist);
			tesselator.point(10,38,mouthDist);
			tesselator.point(0,48,mouthDist);
		}
		else {
			tesselator.point(-20,48,mouthDist);
			tesselator.point(-20,40,mouthDist);
			tesselator.point(20,40,mouthDist);
			
			tesselator.point(20,48,mouthDist);
			tesselator.point(20,40,mouthDist);
			tesselator.point(-20,48,mouthDist);
		}

		// eyebros
		tesselator.color(hairBase.darker());
		tesselator.point(-32, 85 + offsetEyes, faceDist);
		tesselator.point(-6, 85, faceDist);
		tesselator.point(-6, 92, faceDist);
		tesselator.point(-6, 92, faceDist);
		tesselator.point(-32, 92 + offsetEyes, faceDist);
		tesselator.point(-32, 85 + offsetEyes, faceDist);

		tesselator.point(32, 85 + offsetEyes, faceDist);
		tesselator.point(6, 85, faceDist);
		tesselator.point(6, 92, faceDist);
		tesselator.point(6, 92, faceDist);
		tesselator.point(32, 92 + offsetEyes, faceDist);
		tesselator.point(32, 85 + offsetEyes, faceDist);

		// neck
		tesselator.color(190 - d, 164 - d, 129 - d);
		tesselator.point(-20, 25, -20);
		tesselator.point(20, 25, -20);
		tesselator.point(20, 5, -20);

		tesselator.point(20, 5, -20);
		tesselator.point(-20, 5, -20);
		tesselator.point(-20, 25, -20);

		tesselator.point(-20, 25, 20);
		tesselator.point(20, 25, 20);
		tesselator.point(20, 5, 20);

		tesselator.point(20, 5, 20);
		tesselator.point(-20, 5, 20);
		tesselator.point(-20, 25, 20);

		tesselator.point(-20, 5, 20);
		tesselator.point(-20, 25, 20);
		tesselator.point(-20, 25, -20);

		tesselator.point(-20, 25, -20);
		tesselator.point(-20, 5, -20);
		tesselator.point(-20, 5, 20);

		final float spread = 65;
		// body
		// tesselator.color(200,200,200);
		// tesselator.color(-d, 255-d, -d);
		tesselator.color(clothBase);
		tesselator.point(-spread, 2, -40);
		tesselator.point(spread, 2, -40);
		tesselator.point(spread, 2, 40);

		tesselator.point(spread, 2, 40);
		tesselator.point(-spread, 2, 40);
		tesselator.point(-spread, 2, -40);

		// tesselator.color(0,100,150);
		// tesselator.color(-d, 100-d, 10-d);
		tesselator.color(clothBase.getRed() - 40, clothBase.getGreen() - 40,
				clothBase.getBlue() - 40);
		// if (delta < 0.39 || delta > 2.32) {
		// tesselator.color(140,140,140);
		tesselator.point(-spread, 0, -40);
		tesselator.point(-spread, 0, 40);
		tesselator.point(-60, -100, 40);

		// tesselator.color(0, 150-d, 70-d);
		tesselator.color(clothBase.getRed() + 50, clothBase.getGreen() + 50,
				clothBase.getBlue() + 50);
		tesselator.point(-60, -100, 40);
		tesselator.point(-60, -100, -40);
		tesselator.point(-spread, 0, -40);
		// }

		// tesselator.color(-d, 130-d, 40-d);
		tesselator.color(clothBase.getRed() + 20, clothBase.getGreen() + 30,
				clothBase.getBlue() + 40);
		tesselator.point(spread, 0, -40);
		tesselator.point(spread, 0, 40);
		tesselator.point(60, -100, 40);

		// tesselator.color(10-d, 140-d, 10-d);
		tesselator.color(clothBase.getRed() + 10, clothBase.getGreen() + 20,
				clothBase.getBlue() + 10);
		tesselator.point(60, -100, 40);
		tesselator.point(60, -100, -40);
		tesselator.point(spread, 0, -40);

		// back of body
		// 4.16-1.56
		// if (delta >= 4.16 || delta < 1.56) {
		tesselator.color(clothBase.getRed() - 40, clothBase.getGreen() - 40,
				clothBase.getBlue() - 30);
		// tesselator.color(20-d, 160-d, 20-d);
		tesselator.point(spread, 0, -40);
		tesselator.point(60, -101, -40);
		tesselator.point(-60, -101, -40);
		// tesselator.color(20-d, 140-d, 50-d);
		tesselator.color(clothBase.getRed() - 10, clothBase.getGreen() - 10,
				clothBase.getBlue() - 10);
		tesselator.point(-60, -101, -40);
		tesselator.point(-spread, 0, -40);
		tesselator.point(spread, 0, -40);
		// } else {
		// front of body
		tesselator.color(clothBase.getRed() + 30, clothBase.getGreen() + 20,
				clothBase.getBlue() + 40);
		// tesselator.color(20-d, 190-d, 40-d);
		tesselator.point(spread, 0, 40);
		tesselator.point(60, -100, 40);
		tesselator.point(-60, -100, 40);
		// tesselator.color(20-d, 140-d, 60-d);
		tesselator.color(clothBase.getRed() + 20, clothBase.getGreen() + 20,
				clothBase.getBlue() + 30);
		tesselator.point(-60, -100, 40);
		tesselator.point(-spread, 0, 40);
		tesselator.point(spread, 0, 40);
		// }
		// pants
		final float feetHeight = -175;//total will be 250
		final float feetTotal = -225;
		final float consanFeet = 0.5f;
		Color pants = Utility.adjustBrightness(new Color(60,70,90), -d);
		Color bootsBase = Utility.adjustBrightness(new Color(134,101,68), -d);
		drawLeg(pants,bootsBase,adj1,alt1,feetHeight,feetTotal,feer,feera,feet,consanFeet,false);
		drawLeg(pants,bootsBase,adj2,alt2,feetHeight,feetTotal,feer2,feer2a,feet2,consanFeet,true);
		//drawLeg(Color pants, Color bootsBase, float adj1, float alt1, float feetHeight, float feetTotal,float feer,
				//float feera, float feet, float consanFeet);

		// arm
		// tesselator.color(-d, 130-d, 20-d);
		Color foreArmColor = new Color(bootsBase.getRGB());
		if (isASheik) {
			foreArmColor = sheikWhite;
		}
		drawArm(foreArmColor,clothBase,skinBase,arm1,0.0f+armsLag,true,d);
		drawArm(foreArmColor,clothBase,skinBase,arm2,1-hitDelta,false,d);
	}
	private static final Color sheikWhite = new Color(242,225,208);

	private void drawArm(Color bootsBase, Color clothBase, Color skinBase, float arm1, float amount909, boolean left,int dar) {
		float delta909 = amount909;
		float armPosX2 = -((float)(Math.abs(Math.cos(delta909)) * 40));
		float armPosY2 = -(float)(Math.abs(Math.sin(delta909)) * 50)+50;
		
		float armPosX = -((float)(Math.abs(Math.cos(delta909)) * 65));
		float armPosY = -(float)(Math.abs(Math.sin(delta909)) * 25)+25;
		
		float armSwayMid = 0.5f * arm1;
		byte bit = -1;
		if (left)
			bit = 1;
		// shoulder
		Color sheikSwap = new Color(clothBase.getRGB());
		if (isASheik) {
			clothBase = clothBase.darker().darker();
		}
		tesselator.color(va.bright(clothBase, 30));
		tesselator.point(-65*bit,0,-40);
		tesselator.point(-110*bit, -30, -20);
		tesselator.point(-65*bit,-40,-20);
		tesselator.color(va.variate(clothBase, 20));
		tesselator.point(-65*bit,0,40);
		tesselator.point(-110*bit, -30, 20);
		tesselator.point(-65*bit,-40,20);
		tesselator.color(va.bright(clothBase,30));
		tesselator.point(-65*bit,0,40);
		tesselator.point(-65*bit,0,-40);
		tesselator.point(bit*-110,-30,-20);
		tesselator.color(va.bright(clothBase,50));
		tesselator.point(bit*-65,0,40);
		tesselator.point(bit*-110,-30,20);
		tesselator.point(bit*-110,-30,-20);
		clothBase = sheikSwap;
		
		// physical arm
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-100,-30,20);
		tesselator.point(bit*-100,-30,-20);
		tesselator.point(bit*-105,-60,-20+armSwayMid);
		tesselator.color(va.variate(skinBase, 30));
		tesselator.point(bit*-100,-30,20);
		tesselator.point(bit*-105,-60,20+armSwayMid);
		tesselator.point(bit*-105,-60,-20+armSwayMid);
		
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-70,-30,20);
		tesselator.point(bit*-70,-30,-20);
		tesselator.point(bit*-75,-60,-20+armSwayMid);
		tesselator.color(va.variate(skinBase, 30));
		tesselator.point(bit*-70,-30,20);
		tesselator.point(bit*-75,-60,20+armSwayMid);
		tesselator.point(bit*-75,-60,-20+armSwayMid);
		
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-70,-34,-20);
		tesselator.point(bit*-100,-30,-20);
		tesselator.point(bit*-105,-60,-20+armSwayMid);
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-70,-34,-20);
		tesselator.point(bit*-75,-60,-20+armSwayMid);
		tesselator.point(bit*-105,-60,-20+armSwayMid);
		
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-70,-34,20);
		tesselator.point(bit*-100,-30,20);
		tesselator.point(bit*-105,-60,20+armSwayMid);
		tesselator.color(va.bright(skinBase, -30));
		tesselator.point(bit*-70,-34,20);
		tesselator.point(bit*-75,-60,20+armSwayMid);
		tesselator.point(bit*-105,-60,20+armSwayMid);
		// lower arm
		tesselator.color(va.variate(bootsBase, 30));
		tesselator.point(bit*-70,-60,-20+armSwayMid);
		tesselator.point(bit*-75,-120+armPosY2,-20+armSwayMid+armPosX2);
		tesselator.point(bit*-75,-120+armPosY,20+armSwayMid+armPosX);
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-70,-60,-20+armSwayMid);
		tesselator.point(bit*-75,-60,20+armSwayMid);
		tesselator.point(bit*-75,-120+armPosY,20+armSwayMid+armPosX);
		
		tesselator.color(va.variate(bootsBase, 20));
		tesselator.point(bit*-110,-60,-20+armSwayMid);
		tesselator.point(bit*-100,-120+armPosY2,-20+armSwayMid+armPosX2);
		tesselator.point(bit*-100,-120+armPosY,20+armSwayMid+armPosX);
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-110,-60,-20+armSwayMid);
		tesselator.point(bit*-100,-60,20+armSwayMid);
		tesselator.point(bit*-100,-120+armPosY,20+armSwayMid+armPosX);
		
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-110,-60,-20+armSwayMid);
		tesselator.point(bit*-70,-60,-20+armSwayMid);
		tesselator.point(bit*-75,-120+armPosY2,-20+armSwayMid+armPosX2);
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-110,-60,-20+armSwayMid);
		tesselator.point(bit*-100,-120+armPosY2,-20+armSwayMid+armPosX2);
		tesselator.point(bit*-75,-120+armPosY2,-20+armSwayMid+armPosX2);
		
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-110,-60,20+armSwayMid);
		tesselator.point(bit*-70,-60,20+armSwayMid);
		tesselator.point(bit*-75,-120+armPosY,20+armSwayMid+armPosX);
		tesselator.color(va.bright(bootsBase, 35));
		tesselator.point(bit*-110,-60,20+armSwayMid);
		tesselator.point(bit*-100,-120+armPosY,20+armSwayMid+armPosX);
		tesselator.point(bit*-75,-120+armPosY,20+armSwayMid+armPosX);
		
		if (!left)
			return;
		final Color shieldBase = Utility.adjustBrightness(new Color(80,90,80), -dar);
		tesselator.color(va.bright(shieldBase, 20));
		tesselator.point(bit*-130,-120+(armPosY*1.3f),35+(armSwayMid+armPosX)*1.3f);
		tesselator.point(bit*-93,-60+(armPosY*1.3f),5+(armSwayMid+armPosX)*1.3f);
		tesselator.point(bit*-55,-120+(armPosY*1.3f),35+(armSwayMid+armPosX)*1.3f);
		
		tesselator.color(va.bright(shieldBase, 20));
		tesselator.point(bit*-130,-120+(armPosY*1.3f),35+(armSwayMid+armPosX)*1.3f);
		tesselator.point(bit*-93,-200+(armPosY*1.3f),50+(armSwayMid+armPosX)*1.3f);
		tesselator.point(bit*-55,-120+(armPosY*1.3f),35+(armSwayMid+armPosX)*1.3f);
	}
	
	private void drawLeg(Color pants, Color bootsBase, float adj1, float alt1, float feetHeight, float feetTotal,float feer,
			float feera, float feet, float consanFeet, boolean right) {
		float adj1c = adj1 * consanFeet;
		float feerc = feer * consanFeet;
		float feerac = feera * consanFeet;
		float feetc = feet * consanFeet;
		byte bit = 1;
		if (right)
			bit = -1;
		tesselator.color(pants);
		tesselator.point(-60*bit, -100, 40);
		tesselator.point(-60*bit, -100, -40);
		tesselator.point(-65*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-65*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-65*bit + adj1c, feetHeight + feerc, 25 + feetc);
		tesselator.point(-60*bit, -100, 40);

		tesselator.color(Utility.adjustBrightness(pants, va.nextInt(-20, -10)));
		tesselator.point(0, -100, 40);
		tesselator.point(0, -100, -40);
		tesselator.point(-25*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-25*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-25*bit + adj1c, feetHeight + feerc, 25 + feetc);
		tesselator.point(0, -100, 40);

		if (feet < 0)
			tesselator.color(Utility.adjustBrightness(pants, va.nextInt(-30, -20)));
		else
			tesselator.color(Utility.adjustBrightness(pants, va.nextInt(-20, -10)));
		tesselator.point(0, -100, 40);
		tesselator.point(-60*bit, -100, 40);
		tesselator.point(-65*bit + adj1c, feetHeight + feerc, 25 + feetc);
		tesselator.point(-65*bit + adj1c, feetHeight + feerc, 25 + feetc);
		tesselator.point(-25*bit + adj1c, feetHeight + feerc, 25 + feetc);
		tesselator.point(0, -100, 40);

		if (feet > 0)
			tesselator.color(Utility.adjustBrightness(pants, va.nextInt(-30, -20)));
		else
			tesselator.color(Utility.adjustBrightness(pants, va.nextInt(-20, -10)));
		tesselator.point(0, -100, -40);
		tesselator.point(-60*bit, -100, -40);
		tesselator.point(-65*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-65*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(-25*bit + adj1c, feetHeight + feerac, -30 + feetc);
		tesselator.point(0, -100, -40);
		
		// front boot
		tesselator.color(bootsBase);
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.point(-15*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.point(-30*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-30*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-60*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		
		// foot top
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-10, -15)));
		tesselator.point(-30*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-60*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-55*bit + adj1, feetTotal+(feera*1.5f), -90 + feet + alt1);	
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-10, -15)));
		tesselator.point(-30*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-35*bit + adj1, feetTotal+(feera*1.5f), -90 + feet + alt1);
		tesselator.point(-55*bit + adj1, feetTotal+(feera*1.5f), -90 + feet + alt1);
		
		// back boot
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, 30 + feetc);
		tesselator.point(-15*bit + adj1c, feetHeight + feerac + 5, 30 + feetc);
		tesselator.point(-30*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-30*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		tesselator.point(-60*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, 30 + feetc);
		
		//inner side
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-30*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		tesselator.point(-30*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-15*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-15*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.point(-15*bit + adj1c, feetHeight + feerac + 5, 30 + feetc);
		tesselator.point(-30*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		
		//outer side
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-60*bit + adj1, feetTotal + feera, 25 + feet + alt1);
		tesselator.point(-60*bit + adj1, feetTotal + feera, -45 + feet + alt1);
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.color(Utility.adjustBrightness(bootsBase, va.nextInt(-20, -10)));
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, -35 + feetc);
		tesselator.point(-75*bit + adj1c, feetHeight + feerac + 5, 30 + feetc);
		tesselator.point(-60*bit + adj1, feetTotal + feera, 25 + feet + alt1);
	}

	public void jump() {
		if (jump > 0.001)
			return;
		if (getScene().canPlayerMove()) {
			jump = 110.0f;
			jumploss = 0.0f;
		}
	}

	private float destX = 0.0f;
	private float destZ = 0.0f;
	private float startX = 0.0f;
	private float startZ = 0.0f;
	private float dist = 0.0f;
	private float destAlt = 0.0f;
	public void tick() {
		if (movingTowards) {
			setMoveSpeed(3.0f);
			 float lx = startX - destX;
			 float lz = startZ - destZ;
			 float l = (float)(Math.sqrt(lx * lx + lz * lz));
			 float ux = lx / l;
			 float uz = lz / l;
			 destAlt += getMoveSpeed();
			 getScene().setPlayerPosition(new P3D(startX - ux * destAlt, 0,startZ - uz * destAlt));
			 if (destAlt >= dist) {
				 movingTowards = false;
				 destX = 0;
				 destZ = 0;
			 }
		}
	}
	
	public void moveTowards(P3D p) {
		destX = p.x;
		destZ = p.z;
		p.y = 0;
		startX = getScene().getPlayerX();
		startZ = getScene().getPlayerZ();
		dist = p.dist(new P3D(getScene().getPlayerX(),0,getScene().getPlayerZ()));
		destAlt = 0;
		movingTowards = true;
	}

	@Override
	public PointTesselator getTesselator() {
		return tesselator;
	}

}
