import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Dunp extends Enemy {
	private PointTesselator tesselator;
	public Dunp(Scene<Drawable> scene) {
		super(scene, buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		setSpeed(4.5f);
		setHealthLoss(-0.011f);
	}
	
	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-90, -80, -45), new P3D(90, 120, 75));
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
				if (!en.alreadyHit){
					GameState.instance.health -= 0.35f;
					en.getScene().getPlayer().hitBlur();
					en.alreadyHit = true;
					en.timeSinceLastHit = System.currentTimeMillis();
				}
			}		
		});
		return box;
	}
	
	public void createPic() {
		enemyImage = new BufferedImage(9,15,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = enemyImage.getGraphics();
		g.setColor(new Color(70,84,65));
		g.drawLine(0,11,0,14);
		g.drawLine(1,5,1,14);
		g.drawLine(2,2,2,14);
		g.drawLine(3,1,3,14);
		g.drawLine(4,0,4,14);
		g.drawLine(5,1,5,14);
		g.drawLine(6,2,6,14);
		g.drawLine(7,5,7,14);
		g.drawLine(8,11,8,14);
		
		g.setColor(Color.black);
		g.drawLine(2,4,2,5);
		g.drawLine(2,5,6,5);
		g.drawLine(6,5,6,4);
		g.drawLine(3,2,4,3);
		g.drawLine(4,3,5,2);
	}
	
	private float ssf = 0.0f;
	public void draw(int darkness) {
		tesselator.rotate(0, -delta - MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x, pos.y+(float)(Math.cos(ssf * 0.8f) * 30), pos.z, false);
		
		Rand var = new Rand(39205);
		
		final float bx = (float)(Math.cos(ssf*4) * 20);
		final float bz = (float)(Math.sin(ssf * 3) * 30) - 35;
		final P3D hatTop = new P3D(0,200+(float)(Math.cos(ssf*2)*15),-40);
		Color clothColor = Utility.adjustBrightness(new Color(70,84,65),-darkness);//new Color(221,227,219);
		final float bha = 120;
		final float hes = -200;
		final float hhs = -80;
		//hat
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(hatTop);
		tesselator.point(-40,bha,20);
		tesselator.point(40,bha,20);	
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(hatTop);
		tesselator.point(40,bha,20);
		tesselator.point(40,bha,-20);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(40,bha,-20);
		tesselator.point(hatTop);
		tesselator.point(-40,bha,-20);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(-40,bha,-20);
		tesselator.point(hatTop);
		tesselator.point(-40,bha,20);
		
		//head
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(-40,bha,20);
		tesselator.point(-60,40,40);
		tesselator.point(60,40,40);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(60,40,40);
		tesselator.point(40,bha,20);
		tesselator.point(-40,bha,20);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(-40,bha,-20);
		tesselator.point(-60,40,-30);
		tesselator.point(60,40,-30);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(60,40,-30);
		tesselator.point(40,bha,-20);
		tesselator.point(-40,bha,-20);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(-40,bha,-20);
		tesselator.point(-40,bha,20);
		tesselator.point(-60,40,-30);
		tesselator.color(var.bright(clothColor, -35,25));
		tesselator.point(-40,bha,20);
		tesselator.point(-60,40,40);
		tesselator.point(-60,40,-30);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(40,bha,-20);
		tesselator.point(40,bha,20);
		tesselator.point(60,40,-30);
		tesselator.color(var.bright(clothColor, -35,25));
		tesselator.point(40,bha,20);
		tesselator.point(60,40,40);
		tesselator.point(60,40,-30);
		
		// face
		//mouth
		tesselator.color(Color.black);
		tesselator.point(-65,70,45);
		tesselator.point(65,70,45);
		tesselator.point(65,50,50);
		tesselator.point(-65,70,45);
		tesselator.point(-65,50,50);
		tesselator.point(65,50,50);
		tesselator.point(65,70,45);
		tesselator.point(65,50,50);
		tesselator.point(66,80,-10);
		tesselator.point(-65,70,45);
		tesselator.point(-65,50,50);
		tesselator.point(-66,80,-10);
		//eyes
		tesselator.point(40,95,37);
		tesselator.point(20,115,30);
		tesselator.point(10,80,40);
		tesselator.point(-40,95,37);
		tesselator.point(-20,115,30);
		tesselator.point(-10,80,40);
		
		//body
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60,40,40);
		tesselator.point(-60,40,40);
		tesselator.point(-75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60,40,40);
		tesselator.point(75,hhs,75);
		tesselator.point(-75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75,hhs,75);
		tesselator.point(-75,hhs,75);
		tesselator.point(-90+bx,hes,60+bz);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75,hhs,75);
		tesselator.point(90+bx,hes,60+bz);
		tesselator.point(-90+bx,hes,60+bz);
		
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60,40,-30);
		tesselator.point(60,40,40);
		tesselator.point(75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60,40,-30);
		tesselator.point(90+bx,hes,-40+bz);
		tesselator.point(75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75,hhs,75);
		tesselator.point(90+bx,hes,-40+bz);
		tesselator.point(90+bx,hes,60+bz);
	
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-60,40,-30);
		tesselator.point(-60,40,40);
		tesselator.point(-75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-60,40,-30);
		tesselator.point(-90+bx,hes,-40+bz);
		tesselator.point(-75,hhs,75);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-75,hhs,75);
		tesselator.point(-90+bx,hes,-40+bz);
		tesselator.point(-90+bx,hes,60+bz);
		
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-90+bx,hes,-40+bz);
		tesselator.point(90+bx,hes,-40+bz);
		tesselator.point(60,40,-30);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-90+bx,hes,-40+bz);
		tesselator.point(-60,40,-30);
		tesselator.point(60,40,-30);
	}

	public void tick() {
		super.tick();
		ssf += 0.01f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

	public void uponArrival() {
		
	}
}