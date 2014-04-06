import java.awt.Color;
import java.util.Random;

public class Player extends Drawable {
	// The player currently has 62 triangles, which is 186 points.

	private PointTesselator tesselator;
	private FacialExpression mouthExpression;
	private FacialExpression eyeExpression;
	public Player(Scene<Drawable> scene) {
		super(scene, new P3D(-110, -225, -40), new P3D(110, 140, 40));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		mouthExpression = FacialExpression.EYEBROWS_NEUTRAL;
		eyeExpression = FacialExpression.EYEBROWS_NEUTRAL;
	}

	public void setPlayerDelta(float d) {
		delta = d + ni;
	}

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
			time2 += 0.025f;
			time1 += 0.09f;
		} else
			time1 += 0.01f;
		float feet = (float) (Math.sin(time2) * 50);
		float feer = (float) (Math.cos(time2) * 10);
		float adj1 = (float) (Math.sin(time1 * 0.2) * 5) + 10;

		float feet2 = (float) (Math.sin(time2 + Math.PI) * 50);
		float feer2 = (float) (Math.cos(time2 + Math.PI) * 10);
		float adj2 = (float) (Math.cos((time1 + 295) * 0.2) * 5) - 10;
		float hatJump = (float) (Math.sin(time2 * 1.5) * 8);
		float selShak = (float) (Math.cos(time2 * 1.4) * 9);
		//float offsetEyes = 5;// (float)(Math.cos(time0*20)*6);
		float offsetEyes = 0;
		if (eyeExpression == FacialExpression.EYEBROWS_ANGRY)
			offsetEyes = 6;
		else if (eyeExpression == FacialExpression.EYEBROWS_INNOCENTLIKE)
			offsetEyes = -4;
		else
			offsetEyes = 6;
		float wind = (float) (Math.sin(time0 * 50.4) * 9);
		// float wind =0.0f;
		//float mouth = (float) (Math.sin(time0 * 39) * 10);
		float arm1 = (float) (Math.sin(time2) * 20);
		float arm2 = (float) (Math.sin(time2 + Math.PI) * 20);
		//if (mouth < 0)
			//mouth = -mouth;
		//mouth = mouth - 4;
		tesselator.rotate(0, (float) Math.PI + delta, 0);
		if (jump > 0) {
			jump -= jumploss;
			jumploss += 0.3f;
		}
		tesselator.translate(pos.x, pos.y + jump + selShak, pos.z, false);

		// hair and hat
		P3D pinnacle = new P3D(-34, 160 + (8 - hatJump), -62);
		// P3D hatExt = new P3D(wind,135+hatJump,140);
		// P3D hatExt = new P3D(wind+60,240+hatJump,-20);
		P3D hatExt = new P3D(wind - 15, 170 + hatJump, 20);
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
		tesselator.color(clothBase);
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
		tesselator.point(40, 25, -50);
		tesselator.point(35, 25, 49);

		tesselator.point(35, 25, 49);
		tesselator.point(40, 100, 50);
		tesselator.point(40, 100, -50);

		tesselator.point(35, 25, 49);
		tesselator.point(-35, 25, 49);
		tesselator.point(-40, 100, 50);

		tesselator.point(-40, 100, 50);
		tesselator.point(40, 100, 50);
		tesselator.point(35, 25, 49);

		tesselator.point(35, 25, -41);
		tesselator.point(-35, 25, -41);
		tesselator.point(-40, 100, -50);

		tesselator.point(-40, 100, -50);
		tesselator.point(40, 100, -50);
		tesselator.point(35, 25, -41);

		tesselator.point(-40, 100, -50);
		tesselator.point(-40, 25, -50);
		tesselator.point(-35, 25, 49);

		tesselator.point(-35, 25, 49);
		tesselator.point(-40, 100, 50);
		tesselator.point(-40, 100, -50);

		// face
		float faceDist = -59;
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
		tesselator.color(Color.black);
		tesselator.point(-12, 80, faceDist);
		tesselator.point(-22, 70, faceDist);
		tesselator.point(-32, 80, faceDist);
		tesselator.point(12, 80, faceDist);
		tesselator.point(22, 70, faceDist);
		tesselator.point(32, 80, faceDist);

		/*tesselator.color(121, 49, 47);
		tesselator.point(-20, 48, -60);
		tesselator.point(0, 38 + mouth, -60);
		tesselator.point(20, 48, -60);*/
		tesselator.color(121, 49, 47);
		if (mouthExpression == FacialExpression.MOUTH_FROWN) {
			tesselator.point(-20,38,-60);
			tesselator.point(-10,48,-60);
			tesselator.point(0,38,-60);
			tesselator.point(-10,48,-60);
			tesselator.point(10,48,-60);
			tesselator.point(0,38,-60);
			tesselator.point(20,38,-60);
			tesselator.point(10,48,-60);
			tesselator.point(0,38,-60);
		}
		else if (mouthExpression == FacialExpression.EYEBROWS_INNOCENTLIKE) {
			tesselator.point(-20,48,-60);
			tesselator.point(-10,38,-60);
			tesselator.point(0,48,-60);
			tesselator.point(-10,38,-60);
			tesselator.point(10,38,-60);
			tesselator.point(0,48,-60);
			tesselator.point(20,48,-60);
			tesselator.point(10,38,-60);
			tesselator.point(0,48,-60);
		}
		else {
			tesselator.point(-20,48,-60);
			tesselator.point(-20,40,-60);
			tesselator.point(20,40,-60);
			
			tesselator.point(20,48,-60);
			tesselator.point(20,40,-60);
			tesselator.point(-20,48,-60);
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
		tesselator.color(80 - d, 90 - d, 110 - d);
		tesselator.point(-60, -100, 40);
		tesselator.point(-60, -100, -40);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);
		tesselator.point(-60, -100, 40);

		tesselator.color(40 - d, 50 - d, 70 - d);
		tesselator.point(0, -100, 40);
		tesselator.point(0, -100, -40);
		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, 40 + feet);
		tesselator.point(0, -100, 40);

		if (feet < 0)
			tesselator.color(50 - d, 60 - d, 80 - d);
		else
			tesselator.color(60 - d, 70 - d, 90 - d);
		tesselator.point(0, -100, 40);
		tesselator.point(-60, -100, 40);
		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);
		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, 40 + feet);
		tesselator.point(0, -100, 40);

		if (feet > 0)
			tesselator.color(50 - d, 60 - d, 80 - d);
		else
			tesselator.color(60 - d, 70 - d, 90 - d);
		tesselator.point(0, -100, -40);
		tesselator.point(-60, -100, -40);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);
		tesselator.point(0, -100, -40);

		tesselator.color(80 - d, 90 - d, 110 - d);

		// other leg
		tesselator.point(60, -100, 40);
		tesselator.point(60, -100, -40);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(60, -100, 40);

		tesselator.color(40 - d, 50 - d, 70 - d);
		tesselator.point(0, -100, 40);
		tesselator.point(0, -100, -40);
		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(0, -100, 40);

		if (feet2 < 0)
			tesselator.color(50 - d, 60 - d, 80 - d);
		else
			tesselator.color(60 - d, 70 - d, 90 - d);
		tesselator.point(0, -100, 40);
		tesselator.point(60, -100, 40);
		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(0, -100, 40);

		if (feet2 > 0)
			tesselator.color(50 - d, 60 - d, 80 - d);
		else
			tesselator.color(60 - d, 70 - d, 90 - d);
		tesselator.point(0, -100, -40);
		tesselator.point(60, -100, -40);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(0, -100, -40);

		// arm
		// tesselator.color(-d, 130-d, 20-d);
		tesselator.color(clothBase.getRed() - 10, clothBase.getGreen() - 20,
				clothBase.getBlue() - 20);
		tesselator.point(110, -10, -20);
		tesselator.point(110, -10, 20);
		tesselator.point(65, -10, 20);
		tesselator.point(65, -10, 20);
		tesselator.point(65, -10, -20);
		tesselator.point(110, -10, -20);

		tesselator.point(65, -10, -20);
		tesselator.point(95, -90, -20 + arm1);
		tesselator.point(95, -90, 20 + arm1);
		tesselator.point(95, -90, 20 + arm1);
		tesselator.point(65, -10, 20);
		tesselator.point(65, -10, -20);

		tesselator.point(110, -10, -20);
		tesselator.point(120, -100, -20 + arm1);
		tesselator.point(120, -100, 20 + arm1);
		tesselator.point(120, -100, 20 + arm1);
		tesselator.point(110, -10, 20);
		tesselator.point(110, -10, -20);

		tesselator.point(110, -10, -20);
		tesselator.point(65, -10, -20);
		tesselator.point(95, -100, -20 + arm1);
		tesselator.point(95, -100, -20 + arm1);
		tesselator.point(120, -100, -20 + arm1);
		tesselator.point(110, -10, -20);

		tesselator.point(110, -10, 20);
		tesselator.point(65, -10, 20);
		tesselator.point(95, -100, 20 + arm1);
		tesselator.point(95, -100, 20 + arm1);
		tesselator.point(120, -100, 20 + arm1);
		tesselator.point(110, -10, 20);

		// arm2
		tesselator.point(-110, -10, -20);
		tesselator.point(-110, -10, 20);
		tesselator.point(-65, -10, 20);
		tesselator.point(-65, -10, 20);
		tesselator.point(-65, -10, -20);
		tesselator.point(-110, -10, -20);

		tesselator.point(-65, -10, -20);
		tesselator.point(-95, -90, -20 + arm2);
		tesselator.point(-95, -90, 20 + arm2);
		tesselator.point(-95, -90, 20 + arm2);
		tesselator.point(-65, -10, 20);
		tesselator.point(-65, -10, -20);

		tesselator.point(-110, -10, -20);
		tesselator.point(-120, -100, -20 + arm2);
		tesselator.point(-120, -100, 20 + arm2);
		tesselator.point(-120, -100, 20 + arm2);
		tesselator.point(-110, -10, 20);
		tesselator.point(-110, -10, -20);

		tesselator.point(-110, -10, -20);
		tesselator.point(-65, -10, -20);
		tesselator.point(-95, -100, -20 + arm2);
		tesselator.point(-95, -100, -20 + arm2);
		tesselator.point(-120, -100, -20 + arm2);
		tesselator.point(-110, -10, -20);

		tesselator.point(-110, -10, 20);
		tesselator.point(-65, -10, 20);
		tesselator.point(-95, -100, 20 + arm2);
		tesselator.point(-95, -100, 20 + arm2);
		tesselator.point(-120, -100, 20 + arm2);
		tesselator.point(-110, -10, 20);
	}

	public void jump() {
		jump = 110.0f;
		jumploss = 0.0f;
	}

	public void tick() {

	}

	@Override
	public PointTesselator getTesselator() {
		return tesselator;
	}

}
