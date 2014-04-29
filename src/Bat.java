import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Bat extends Enemy {
	private PointTesselator tesselator;
	public Bat(Scene<Drawable> scene) {
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
		tesselator.translate(pos.x, pos.y+(float)(Math.cos(ssf) * 30), pos.z, false);
		
		Color cn = Utility.adjustBrightness(new Color(60,60,60), -darkness);
		Rand rand = new Rand(294881);
		
		float beat = (float)(Math.sin(ssf*7) * 20);
		
		float xbeat = -200 + (beat);
		
		final int brd = 50;
		
		tesselator.color(rand.bright(cn, brd));
		tesselator.point(xbeat,100,50 + beat);
		tesselator.point(-20,90,0);
		tesselator.point(-20,0,-25);
		tesselator.color(rand.bright(cn, brd));
		tesselator.point(xbeat,100,50 + beat);
		tesselator.point(xbeat,50,20 + beat);
		tesselator.point(-20,0,-25);
		
		tesselator.color(rand.bright(cn, brd));
		tesselator.point(-xbeat,100,50 + beat);
		tesselator.point(20,90,0);
		tesselator.point(20,0,-25);
		tesselator.color(rand.bright(cn, brd));
		tesselator.point(-xbeat,100,50 + beat);
		tesselator.point(-xbeat,50,20 + beat);
		tesselator.point(20,0,-25);
		
		//body base
		tesselator.color(rand.bright(cn, brd*3/4,brd*4/3));
		tesselator.point(-20,90,10);
		tesselator.point(20,90,10);
		tesselator.point(20,0,-15);
		tesselator.color(rand.bright(cn, brd*3/4,brd*4/3));
		tesselator.point(-20,90,10);
		tesselator.point(-20,0,-15);
		tesselator.point(20,0,-15);
		
		tesselator.color(rand.bright(cn, brd*3/4,brd*4/3));
		tesselator.point(-20,90,-10);
		tesselator.point(20,90,-10);
		tesselator.point(20,0,-35);
		tesselator.color(rand.bright(cn, brd*3/4,brd*4/3));
		tesselator.point(-20,90,-10);
		tesselator.point(-20,0,-35);
		tesselator.point(20,0,-15);
	}

	public void tick() {
		super.tick();
		ssf += 0.02f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

	public void uponArrival() {
		
	}
}