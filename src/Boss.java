import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Boss extends Enemy {
	private PointTesselator tesselator;
	private static Rand rand = new Rand();
	public Boss(Scene<Drawable> scene) {
		super(scene, buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		setSpeed(3.2f);
		setHealthLoss(-0.0005f);
		setVisiblilityDistance(4000);
	}
	
	private static Hitbox buildHitbox() {
		float s = scale;//2.4f;
		final Hitbox box = new Hitbox(new P3D(-90*s, -80*s, -45*s), new P3D(90*s, 120*s, 75*s));
		box.scale = s;
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
					int sound = rand.nextInt(3)+1;
					if (SoundManager.soundEnabled) {
						Boss boss = (Boss)en;
						boss.laugh = new Sound("laugh" + sound);
						boss.laugh.play();
					}
					GameState.instance.health -= 0.35f;
					en.getScene().getPlayer().hitBlur();
					en.alreadyHit = true;
					en.timeSinceLastHit = System.currentTimeMillis();
				}
			}		
		});
		return box;
	}
	
	private Sound laugh;
	
	public void createPic() {
		enemyImage = new BufferedImage(9,15,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = enemyImage.getGraphics();
		g.setColor(new Color(125,38,23));
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
		float s = scale;//2.4f;
		final P3D hatTop = new P3D(0,(200+(float)(Math.cos(ssf*2)*15))*s,-40*s);
		Color clothColor = Utility.adjustBrightness(new Color(125,38,23),-darkness);//new Color(221,227,219);
		final float bha = 120;
		final float hes = -200;
		final float hhs = -80;
		//hat
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(hatTop);
		tesselator.point(-40*s,bha*s,20*s);
		tesselator.point(40*s,bha*s,20*s);	
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(hatTop);
		tesselator.point(40*s,bha*s,20*s);
		tesselator.point(40*s,bha*s,-20*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(40*s,bha*s,-20*s);
		tesselator.point(hatTop);
		tesselator.point(-40*s,bha*s,-20*s);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(-40*s,bha*s,-20*s);
		tesselator.point(hatTop);
		tesselator.point(-40*s,bha*s,20*s);
		
		//head
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(-40*s,bha*s,20*s);
		tesselator.point(-60*s,40*s,40*s);
		tesselator.point(60*s,40*s,40*s);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(60*s,40*s,40*s);
		tesselator.point(40*s,bha*s,20*s);
		tesselator.point(-40*s,bha*s,20*s);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(-40*s,bha*s,-20*s);
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.point(60*s,40*s,-30*s);
		tesselator.color(var.bright(clothColor, -35, 25));
		tesselator.point(60*s,40*s,-30*s);
		tesselator.point(40*s,bha*s,-20*s);
		tesselator.point(-40*s,bha*s,-20*s);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(-40*s,bha*s,-20*s);
		tesselator.point(-40*s,bha*s,20*s);
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.color(var.bright(clothColor, -35,25));
		tesselator.point(-40*s,bha*s,20*s);
		tesselator.point(-60*s,40*s,40*s);
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.color(var.bright(clothColor, 30));
		tesselator.point(40*s,bha*s,-20*s);
		tesselator.point(40*s,bha*s,20*s);
		tesselator.point(60*s,40*s,-30*s);
		tesselator.color(var.bright(clothColor, -35,25));
		tesselator.point(40*s,bha*s,20*s);
		tesselator.point(60*s,40*s,40*s);
		tesselator.point(60*s,40*s,-30*s);
		
		// face
		//mouth
		tesselator.color(Color.black);
		tesselator.point(-65*s,70*s,45*s);
		tesselator.point(65*s,70*s,45*s);
		tesselator.point(65*s,50*s,50*s);
		tesselator.point(-65*s,70*s,45*s);
		tesselator.point(-65*s,50*s,50*s);
		tesselator.point(65*s,50*s,50*s);
		tesselator.point(65*s,70*s,45*s);
		tesselator.point(65*s,50*s,50*s);
		tesselator.point(66*s,80*s,-10*s);
		tesselator.point(-65*s,70*s,45*s);
		tesselator.point(-65*s,50*s,50*s);
		tesselator.point(-66*s,80*s,-10*s);
		//eyes
		tesselator.point(40*s,95*s,37*s);
		tesselator.point(20*s,115*s,30*s);
		tesselator.point(10*s,80*s,40*s);
		tesselator.point(-40*s,95*s,37*s);
		tesselator.point(-20*s,115*s,30*s);
		tesselator.point(-10*s,80*s,40*s);
		
		//body
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60*s,40*s,40*s);
		tesselator.point(-60*s,40*s,40*s);
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60*s,40*s,40*s);
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.point((-90+bx)*s,hes*s,(60+bz)*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.point((90+bx)*s,hes*s,(60+bz)*s);
		tesselator.point((-90+bx)*s,hes*s,(60+bz)*s);
		
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60*s,40*s,-30*s);
		tesselator.point(60*s,40*s,40*s);
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(60*s,40*s,-30*s);
		tesselator.point((90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(75*s,hhs*s,75*s);
		tesselator.point((90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point((90+bx)*s,hes*s,(60+bz)*s);
	
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.point(-60*s,40*s,40*s);
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.point((-90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point(-75*s,hhs*s,75*s);
		tesselator.point((-90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point((-90+bx)*s,hes*s,(60+bz)*s);
		
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point((-90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point((90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point(60*s,40*s,-30*s);
		tesselator.color(var.bright(clothColor, -25, 25));
		tesselator.point((-90+bx)*s,hes*s,(-40+bz)*s);
		tesselator.point(-60*s,40*s,-30*s);
		tesselator.point(60*s,40*s,-30*s);
	}
	
	public void onDeadDrop() {
		// game over!
		getScene().getLevel().addMessage("Congrads! You've won!", "win", new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				getScene().getLevel().startTransition(getScene().getMain().getScreen("mainmenu"), new P3D(), 0);
				GameState.instance.health = 10;
				GameState.instance.score += 500;
				GameState.save();
			}
		});
		if (SoundManager.soundEnabled) {
			new Sound("item").play();
		}
		getScene().getLevel().setActiveMessage("win");
	}
	
	public void onPlayerHit() {
		if (laughRand.nextDouble() < 0.1) {
			dropGoodies();
		}
	}
	
	private boolean playLaugh = true;
	private int lastLaugh = 0;
	private Rand laughRand = new Rand();
	private static float scale = 1.6f;
	public void tick() {
		super.tick();
		ssf += 0.01f;
		
		
		if (SoundManager.soundEnabled && (laugh == null || laugh.isFinished()) && (laughRand.nextFloat() < 0.0008 || playLaugh)) {
			boolean cont = true;
			playLaugh = false;
			if (cont) {
			if (lastLaugh == 0) {
				laugh = new Sound("laugh1");
				lastLaugh = laughRand.nextInt(2) + 1;
			}
			else if (lastLaugh == 2){
				laugh = new Sound("laugh2");
				lastLaugh = laughRand.nextInt(2);
			}
			else {
				laugh = new Sound("laugh3");
				if (laughRand.nextDouble() < 0.5)
					lastLaugh = 0;
				else
					lastLaugh = 2;
			}
			}
			if (laugh != null)
				laugh.play();
		}
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

	public void uponArrival() {
		
	}
}