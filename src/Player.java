import java.awt.Color;

public class Player extends Drawable {
	// The player currently has 62 triangles, which is 186 points.
	
	private PointTesselator tesselator;
	public Player() {
		super(new P3D(-110,-225,-40),new P3D(110,140,40));
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
	}
	
	public void setPlayerDelta(float d) {
		delta = d + ni;
	}
public Color playerColor;
	private final float ni = (float)(Math.PI/180.0f*90);
	private final float re = (float)(Math.PI*2);
	float delta = 0.0f;
	float time0 = 0;
	float time1 = 0;
	float time2 = 0;
	float time3 = 0;
	float jump = 0.0f;
	float jumploss = 0.1f;
	public boolean moving = false;

	public void draw(int d) {
		if (!isVisible())
			return;
		delta = delta%re;
		time0 += 0.001f;
		if (moving) {
			time2 += 0.025f;
			time1 += 0.09f;
		}
		else
			time1 += 0.01f;
		float feet = (float) (Math.sin(time2) * 50);
		float feer = (float) (Math.cos(time2) * 20);
		float adj1 = (float) (Math.sin(time1 * 0.2) * 10) + 10;

		float feet2 = (float) (Math.sin(time2 + Math.PI) * 50);
		float feer2 = (float) (Math.cos(time2 + Math.PI) * 20);
		float adj2 = (float) (Math.cos((time1 + 295) * 0.2) * 10) - 10;
		float hatJump = (float)(Math.sin(time2*1.5) * 8);
		float selShak = (float)(Math.cos(time2*1.4) * 9);
		//float wind = (float)(Math.sin(time0*50.4)*9)-9;
		float wind =0.0f;
		float mouth = (float)(Math.sin(time0*39)*10);
		if (mouth < 0)
			mouth = -mouth;
		mouth = mouth-4;
		tesselator.rotate(0, (float) Math.PI + delta, 0);
		if (jump > 0) {
			jump -= jumploss;
			jumploss += 0.3f;
		}
		tesselator.translate(pos.x,pos.y+jump+selShak,pos.z, false);
		
		// hair and hat
		P3D pinnacle = new P3D(-34, 160+(8-hatJump), -62);
		//P3D hatExt = new P3D(wind,135+hatJump,140);
		P3D hatExt = new P3D(wind+60,260+hatJump,-20);
		//210-d, 184-d, 149-d
		Color clothBase = new Color(MathCalculator.colorLock(playerColor.getRed()-d),MathCalculator.colorLock(playerColor.getGreen()-d),MathCalculator.colorLock(playerColor.getBlue()-d));
		Color hairBase = new Color(MathCalculator.colorLock(136-d),MathCalculator.colorLock(109-d),MathCalculator.colorLock(60-d));
		Color skinBase = new Color(MathCalculator.colorLock(210-d),MathCalculator.colorLock(184-d),MathCalculator.colorLock(149-d));
		tesselator.color(clothBase);
		tesselator.point(pinnacle);
		tesselator.point(-40,100,50);
		tesselator.point(hatExt);
		tesselator.color(clothBase.getRed()+10,clothBase.getGreen()+10,clothBase.getBlue()+10);
		tesselator.point(hatExt);
		tesselator.point(pinnacle);
		tesselator.point(40,100,-50);//was +50
		tesselator.color(clothBase.getRed()-10,clothBase.getGreen()-23,clothBase.getBlue()+10);
		tesselator.point(40,100,50);
		tesselator.point(hatExt);
		tesselator.point(-40,100,50);
		
		tesselator.color(clothBase.getRed()+10,clothBase.getGreen()+14,clothBase.getBlue()+20);
		tesselator.point(hatExt);
		tesselator.point(40,100,50);
		tesselator.point(40,100,-50);
		
		//actual hair
		
		//tesselator.color(204-d, 190-d, 51-d);
		tesselator.color(hairBase);
		tesselator.point(-40, 100, -50);
		tesselator.point(40, 100, -50);
		tesselator.point(pinnacle);

		//tesselator.color(214-d, 206-d, 65-d);
		tesselator.color(hairBase.getRed()+15,hairBase.getGreen()+15,hairBase.getBlue()+20);
		tesselator.point(pinnacle);
		tesselator.point(40, 100, 50);
		tesselator.point(40, 100, -50);

		//tesselator.color(204-d, 194-d, 55-d);
		/*tesselator.color(hairBase.getRed()-20,hairBase.getGreen()-25,hairBase.getBlue()-20);
		tesselator.point(40, 100, 50);
		tesselator.point(pinnacle);
		tesselator.point(-40, 100, 50);*/

		//tesselator.color(222-d, 219-d, 79-d);
		tesselator.color(hairBase.getRed()-20,hairBase.getGreen()-20,hairBase.getBlue()-20);
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
		float faceDist = -56;
		tesselator.color(255,255,255);
		tesselator.point(-12,80,faceDist);
		tesselator.point(-12,70,faceDist);
		tesselator.point(-22,70,faceDist);
		tesselator.point(-32,80,faceDist);
		tesselator.point(-32,70,faceDist);
		tesselator.point(-22,70,faceDist);
		tesselator.point(12,80,faceDist);
		tesselator.point(12,70,faceDist);
		tesselator.point(22,70,faceDist);
		tesselator.point(32,80,faceDist);
		tesselator.point(32,70,faceDist);
		tesselator.point(22,70,faceDist);
		tesselator.color(Color.black);
		tesselator.point(-12,80,faceDist);
		tesselator.point(-22,70,faceDist);
		tesselator.point(-32,80,faceDist);
		tesselator.point(12,80,faceDist);
		tesselator.point(22,70,faceDist);
		tesselator.point(32,80,faceDist);
		
		tesselator.color(121,49,47);
		tesselator.point(-20,48,-60);
		tesselator.point(0,38+mouth,-60);
		tesselator.point(20,48,-60);

		// neck
		tesselator.color(190-d, 164-d, 129-d);
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

		// body
		// tesselator.color(200,200,200);
		//tesselator.color(-d, 255-d, -d);
		tesselator.color(clothBase);
		tesselator.point(-70, 2, -40);
		tesselator.point(70, 2, -40);
		tesselator.point(70, 2, 40);

		tesselator.point(70, 2, 40);
		tesselator.point(-70, 2, 40);
		tesselator.point(-70, 2, -40);

		// tesselator.color(0,100,150);
		//tesselator.color(-d, 100-d, 10-d);
		tesselator.color(clothBase.getRed()-70,clothBase.getGreen()-70,clothBase.getBlue()-70);
		//if (delta < 0.39 || delta > 2.32) {
			// tesselator.color(140,140,140);
			tesselator.point(-70, 0, -40);
			tesselator.point(-70, 0, 40);
			tesselator.point(-60, -100, 40);

			//tesselator.color(0, 150-d, 70-d);
			tesselator.color(clothBase.getRed()+50,clothBase.getGreen()+50,clothBase.getBlue()+50);
			tesselator.point(-60, -100, 40);
			tesselator.point(-60, -100, -40);
			tesselator.point(-70, 0, -40);
		//}

		//tesselator.color(-d, 130-d, 40-d);
			tesselator.color(clothBase.getRed()+20,clothBase.getGreen()+30,clothBase.getBlue()+40);
		tesselator.point(70, 0, -40);
		tesselator.point(70, 0, 40);
		tesselator.point(60, -100, 40);

		//tesselator.color(10-d, 140-d, 10-d);
		tesselator.color(clothBase.getRed()+10,clothBase.getGreen()+20,clothBase.getBlue()+10);
		tesselator.point(60, -100, 40);
		tesselator.point(60, -100, -40);
		tesselator.point(70, 0, -40);

		// back of body
		// 4.16-1.56
		//if (delta >= 4.16 || delta < 1.56) {
		tesselator.color(clothBase.getRed()-40,clothBase.getGreen()-40,clothBase.getBlue()-30);
			//tesselator.color(20-d, 160-d, 20-d);
			tesselator.point(70, 0, -40);
			tesselator.point(60, -101, -40);
			tesselator.point(-60, -101, -40);
			//tesselator.color(20-d, 140-d, 50-d);
			tesselator.color(clothBase.getRed()-10,clothBase.getGreen()-10,clothBase.getBlue()-10);
			tesselator.point(-60, -101, -40);
			tesselator.point(-71, 0, -40);
			tesselator.point(70, 0, -40);
		//} else {
			// front of body
			tesselator.color(clothBase.getRed()+30,clothBase.getGreen()+20,clothBase.getBlue()+40);
			//tesselator.color(20-d, 190-d, 40-d);
			tesselator.point(70, 0, 40);
			tesselator.point(60, -100, 40);
			tesselator.point(-60, -100, 40);
			//tesselator.color(20-d, 140-d, 60-d);
			tesselator.color(clothBase.getRed()+20,clothBase.getGreen()+20,clothBase.getBlue()+30);
			tesselator.point(-60, -100, 40);
			tesselator.point(-70, 0, 40);
			tesselator.point(70, 0, 40);
		//}
		// pants
		tesselator.color(80-d, 90-d, 110-d);
		tesselator.point(-60, -104, 40);
		tesselator.point(-60, -104, -40);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);

		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);
		tesselator.point(-60, -104, 40);

		tesselator.color(40-d, 50-d, 70-d);
		tesselator.point(0, -107, 40);
		tesselator.point(0, -107, -40);
		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);

		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, 40 + feet);
		tesselator.point(0, -107, 40);

		if (feet < 0)
			tesselator.color(50-d, 60-d, 80-d);
		else
			tesselator.color(60-d, 70-d, 90-d);
		tesselator.point(0, -107, 40);
		tesselator.point(-60, -104, 40);
		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);

		tesselator.point(-85 + adj1, -225 + feer, 40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, 40 + feet);
		tesselator.point(0, -107, 40);

		if (feet > 0)
			tesselator.color(50-d, 60-d, 80-d);
		else
			tesselator.color(60-d, 70-d, 90-d);
		tesselator.point(0, -107, -40);
		tesselator.point(-60, -104, -40);
		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);

		tesselator.point(-85 + adj1, -225 + feer, -40 + feet);
		tesselator.point(-45 + adj1, -225 + feer, -40 + feet);
		tesselator.point(0, -107, -40);

		tesselator.color(80-d, 90-d, 110-d);

		// other leg
		tesselator.point(60, -104, 40);
		tesselator.point(60, -104, -40);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);

		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(60, -104, 40);

		tesselator.color(40-d, 50-d, 70-d);
		tesselator.point(0, -107, 40);
		tesselator.point(0, -107, -40);
		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);

		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(0, -107, 40);

		if (feet2 < 0)
			tesselator.color(50-d, 60-d, 80-d);
		else
			tesselator.color(60-d, 70-d, 90-d);
		tesselator.point(0, -107, 40);
		tesselator.point(60, -104, 40);
		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);

		tesselator.point(85 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, 40 + feet2);
		tesselator.point(0, -107, 40);

		if (feet2 > 0)
			tesselator.color(50-d, 60-d, 80-d);
		else
			tesselator.color(60-d, 70-d, 90-d);
		tesselator.point(0, -107, -40);
		tesselator.point(60, -104, -40);
		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);

		tesselator.point(85 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(45 + adj2, -225 + feer2, -40 + feet2);
		tesselator.point(0, -107, -40);

		// arm
		//tesselator.color(-d, 130-d, 20-d);
		tesselator.color(clothBase.getRed()-10,clothBase.getGreen()-20,clothBase.getBlue()-20);
		tesselator.point(74, -10, -20);
		tesselator.point(74, -10, 20);
		tesselator.point(84, -10, 20);
		tesselator.point(84, -10, 20);
		tesselator.point(84, -10, -20);
		tesselator.point(74, -10, -20);

		tesselator.point(85, -10, 20);
		tesselator.point(110, -70, 10);
		tesselator.point(110, -70, -10);
		tesselator.point(110, -70, -10);
		tesselator.point(85, -10, -20);
		tesselator.point(85, -10, 20);

		tesselator.point(75, -10, 20);
		tesselator.point(100, -70, 10);
		tesselator.point(100, -70, -10);
		tesselator.point(100, -70, -10);
		tesselator.point(74, -10, -20);
		tesselator.point(75, -10, 20);

		tesselator.point(75, -10, 20);
		tesselator.point(84, -10, 20);
		tesselator.point(100, -70, 10);
		tesselator.point(100, -70, 10);
		tesselator.point(110, -70, 10);
		tesselator.point(75, -10, 20);

		tesselator.point(75, -10, -20);
		tesselator.point(84, -10, -20);
		tesselator.point(100, -70, -10);
		tesselator.point(100, -70, -10);
		tesselator.point(110, -70, -10);
		tesselator.point(75, -10, -20);

		// arm2
		tesselator.point(-74, -10, -20);
		tesselator.point(-74, -10, 20);
		tesselator.point(-84, -10, 20);
		tesselator.point(-84, -10, 20);
		tesselator.point(-84, -10, -20);
		tesselator.point(-74, -10, -20);

		tesselator.point(-85, -10, 20);
		tesselator.point(-110, -70, 10);
		tesselator.point(-110, -70, -10);
		tesselator.point(-110, -70, -10);
		tesselator.point(-85, -10, -20);
		tesselator.point(-85, -10, 20);

		tesselator.point(-75, -10, 20);
		tesselator.point(-100, -70, 10);
		tesselator.point(-100, -70, -10);
		tesselator.point(-100, -70, -10);
		tesselator.point(-74, -10, -20);
		tesselator.point(-75, -10, 20);

		tesselator.point(-75, -10, 20);
		tesselator.point(-84, -10, 20);
		tesselator.point(-100, -70, 10);
		tesselator.point(-100, -70, 10);
		tesselator.point(-110, -70, 10);
		tesselator.point(-75, -10, 20);

		tesselator.point(-75, -10, -20);
		tesselator.point(-84, -10, -20);
		tesselator.point(-100, -70, -10);
		tesselator.point(-100, -70, -10);
		tesselator.point(-110, -70, -10);
		tesselator.point(-75, -10, -20);
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
