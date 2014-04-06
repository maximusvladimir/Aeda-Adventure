import java.awt.Color;

public class Enemy extends Character {
	private PointTesselator tesselator;

	public Enemy(Scene<Drawable> scene) {
		super(scene, buildHitbox());
		tesselator = new PointTesselator();
		//tesselator.setTransparency(50);
	}

	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-80, 0, -140), new P3D(80, 250, 0));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				if (!(d0 instanceof Player) && !(d1 instanceof Player))
					return;
				Enemy en = null;
				if (d0 instanceof Enemy)
					en = (Enemy)d0;
				else
					en = (Enemy)d1;
				en.persueHalt = true;
				if (d0.getScene().getPlayer().isHitting()) {
					if (Math.random() < 0.01) {
						GameState.instance.score += 20;
						en.addHealth(-0.2f);
						if (en.getHealth() <= 0.00001)
							en.getScene().remove(en);
					}
				}
				else {
					GameState.instance.health -= 0.0075f;
					//Hitbox.getDefaultHitAction().onHit(d0, d1, indexd0, indexd1);
				}
			}		
		});
		return box;
	}

	public Enemy getThis() {
		return this;
	}

	public void draw(int darkness) {
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		// tesselator.rotate(0, (float)(Math.PI / 2.5f), 0);
		int d = darkness;
		tesselator.rotate(0, -delta - (3.1415926535f/2), 0);
		if (isPersuingPlayer())
			tesselator.color(255, 0, 0);
		else
			tesselator.color(0, 200, 160);

		if (isPersuingPlayer())
			theta += 0.05f;
		P3D toe1 = new P3D(-70, 0, -40);
		P3D toe2 = new P3D(70, 0, -40);
		float head = (float) (Math.sin(theta) * 10);
		float bod = (float)(Math.cos(theta/1.2f) * 25)+45;
		toe1.z += (float) (Math.sin(theta) * 40);
		toe2.z += (float) (Math.sin(theta + (Math.PI)) * 40);
		// Color bodColor = new Color(102, 40, 1);
		Color bodColor = Utility.adjustBrightness(new Color(209, 185, 153),-d);
		Color mouth = Utility.adjustBrightness(new Color(160,30,30),-d);
		rand = new Rand(24);

		int bodTol = 15;
		float hn = 45.0f;

		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe1);
		tesselator.point(-70, 100 + bod, -100);
		tesselator.point(-30, 100 + bod, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe1);
		tesselator.point(-70, 100 + bod, -100);
		tesselator.point(-50, 120 + bod, -140);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe1);
		tesselator.point(-50, 120 + bod, -140);
		tesselator.point(-30, 100 + bod, -100);

		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe2);
		tesselator.point(70, 100 + bod, -100);
		tesselator.point(30, 100 + bod, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe2);
		tesselator.point(70, 100 + bod, -100);
		tesselator.point(50, 120 + bod, -140);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(toe2);
		tesselator.point(50, 120 + bod, -140);
		tesselator.point(30, 100 + bod, -100);

		// bottom of body
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(-30, 100 + bod, -100);
		tesselator.point(30, 100 + bod, -100);
		tesselator.point(-50, 120 + bod, -140);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(30, 100 + bod, -100);
		tesselator.point(50, 120 + bod, -140);
		tesselator.point(-50, 120 + bod, -140);

		// top ofbody
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(-30, 180 + bod+hn, -40);
		tesselator.point(30, 180 + bod+hn, -40);
		tesselator.point(-50, 200 + bod+hn, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(30, 180 + bod+hn, -40);
		tesselator.point(50, 200 + bod+hn, -100);
		tesselator.point(-50, 200 + bod+hn, -100);

		// leftside
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(-50, 120 + bod, -140);
		tesselator.point(-30, 180 + bod+hn, -40);
		tesselator.point(-70, 100 + bod, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(-50, 120 + bod, -140);
		tesselator.point(-50, 200 + bod+hn, -100);
		tesselator.point(-30, 180 + bod+hn, -40);

		// right side
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(50, 120 + bod, -140);
		tesselator.point(30, 180 + bod+hn, -40);
		tesselator.point(70, 100 + bod, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(50, 120 + bod, -140);
		tesselator.point(50, 200 + bod+hn, -100);
		tesselator.point(30, 180 + bod+hn, -40);

		// front
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(30, 180 + bod+hn, -40);
		tesselator.point(-70, 100 + bod, -100);
		tesselator.point(70, 100 + bod, -100);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(30, 180 + bod+hn, -40);
		tesselator.point(-70, 100 + bod, -100);
		tesselator.point(-30, 180 + bod+hn, -40);

		// back
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(50, 200 + bod+hn, -100);
		tesselator.point(-50, 120 + bod, -140);
		tesselator.point(50, 120 + bod, -140);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(50, 200 + bod+hn, -100);
		tesselator.point(-50, 120 + bod, -140);
		tesselator.point(-50, 200 + bod+hn, -100);

		// neck
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)-40));
		tesselator.point(-20, 180 + bod+hn, -40);
		tesselator.point(20, 180 + bod+hn, -40);
		tesselator.point(20, 200 + bod+hn, -40);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)-40));
		tesselator.point(-20, 180 + bod+hn, -40);
		tesselator.point(-20, 200 + bod+hn, -40);
		tesselator.point(20, 200 + bod+hn, -40);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)-40));
		tesselator.point(20, 200 + bod+hn, -100);
		tesselator.point(20, 180 + bod+hn, -40);
		tesselator.point(20, 200 + bod+hn, -40);
		tesselator.color(Utility.adjustBrightness(bodColor,
				rand.nextInt(-bodTol, bodTol)-40));
		tesselator.point(-20, 200 + bod+hn, -100);
		tesselator.point(-20, 180 + bod+hn, -40);
		tesselator.point(-20, 200 + bod+hn, -40);
		
		// head thing
		tesselator.color(Utility.adjustBrightness(mouth,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(-40, 200 + bod+hn, -100);
		tesselator.point(0, 230 + bod+hn, -100);
		tesselator.point(0, 220 + bod+hn + head, -20);
		tesselator.color(Utility.adjustBrightness(mouth,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(40, 200 + bod+hn, -100);
		tesselator.point(0, 230 + bod+hn, -100);
		tesselator.point(0, 220 + bod+hn + head, -20);
		tesselator.color(Utility.adjustBrightness(mouth,
				rand.nextInt(-bodTol, bodTol)));
		tesselator.point(40, 200 + bod+hn, -100);
		tesselator.point(-40, 200 + bod+hn, -100);
		tesselator.point(0, 230 + bod+hn, -100);
		
		tesselator.color(Utility.adjustBrightness(mouth,
				rand.nextInt(-bodTol, bodTol)-60));
		tesselator.point(35,210+bod+hn,-90);
		tesselator.point(0,200+bod+hn,-20);
		tesselator.point(-35,210+bod+hn,-90);
		
		//eyes
		if (isPersuingPlayer()) {
			if (interpolator < 1.0f) {
				interpolator += 0.0075f;
				if (interpolator > 1)
					interpolator = 1;
			}
		}
		else {
			if (interpolator > 0) {
				interpolator -= 0.0075f;
				if (interpolator < 0)
					interpolator = 0;
			}
		}
		tesselator.color(Utility.adjustBrightness(MathCalculator.lerp(Color.yellow, Color.black, interpolator), -d));
		tesselator.point(35,210+bod+hn,-90);
		tesselator.point(10,225+bod+hn,-90);
		tesselator.point(40,235+bod+hn,-90);
		tesselator.point(-35,210+bod+hn,-90);
		tesselator.point(-10,225+bod+hn,-90);
		tesselator.point(-40,235+bod+hn,-90);
	}

	float delta = 0.0f;
	float theta = 0.0f;
	float interpolator = 0.0f;
	Rand rand = new Rand(3);
	boolean persueHalt = false;
	float lastHitDist = 0.0f;
	
	public void tick() {
		float dist = getDistToPlayer();
		if (dist > 300 && persueHalt) {
			persueHalt = false;
		}
		setPersuingPlayer(dist <= 1000);
		//if (dist <= 1500) {
		delta = (float) Math.atan2((-getScene().getPlayerZ() + getInstanceLoc().z+500),
					(-getScene().getPlayerX() + getInstanceLoc().x));
		//}
		setMoveSpeed(2.1f);
		if (isPersuingPlayer()) {
			if (!checker)
				moveTowards(new P3D(getScene().getPlayerX(),0,getScene().getPlayerZ()-500));
			super.tick();
			checker = true;
			if (!isMovingTowards())
				checker = false;
		}
		/*if (isPersuingPlayer() && !persueHalt) {
			if (!checker)
				moveTowards(new P3D(getScene().getPlayerX(),0,getScene().getPlayerZ()));
			super.tick();
			checker = true;
			getScene().getPlayer().setFaceEmotion(FacialExpression.EMOTION_SAD);
			// TODO see if legal.
		}
		else {
			checker = false;
			getScene().getPlayer().setFaceEmotion(FacialExpression.EMOTION_PLAIN);
		}*/
	}

	boolean checker = false;
	
	public PointTesselator getTesselator() {
		return tesselator;
	}

}
