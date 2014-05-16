import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BossLevel extends Level {
	private P3D lastLoc = new P3D();
	
	public BossLevel(IMain inst) {
		super(inst);
	}	
	
	public void reloadedLevel() {
		super.reloadedLevel();
		getScene().setPlayerX(200);
		getScene().setPlayerZ(1800);
		
		GameState.instance.health = 10;
		boss.setInstanceLoc(0, 0);
		boss.setHealth(1);
		this.addMessage("Hehehehehe", "evillaugh", new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (SoundManager.soundEnabled)
					new Sound("laugh1").play();
			}
		});
		this.setActiveMessage("evillaugh");
	}

	public static long mapDrawTime = 0;

	private int ssz = 25;
	private float depth = -500;
	private Boss boss;
	public void init() {
		scene = new Scene<Drawable>(this, getRand());
		GamePlane plane = new GamePlane(scene, getRand(), ssz, new Color(112,92,65), 14, 0.5f);
		for (int x = 0; x < ssz; x++) {
			for (int z = 0; z < ssz; z++) {
				double dist = Math.sqrt(Math.pow(x-(ssz*0.5),2)+Math.pow(z-(ssz*0.5),2));
				if (dist < ssz * 0.2)
					plane.setHeightPoint(x, z, plane.getHeightPoint(x, z) / 3);
				else if (dist < ssz * 0.25)
					plane.setHeightPoint(x,z,MathCalculator.lerp(plane.getHeightPoint(x, z) / 3, depth, (float)Rand.random() * 0.4f));
				else
					plane.setHeightPoint(x,z,depth);
			}
		}
		scene.setPlane(plane);
		for (int x = 0; x < ssz; x++) {
			for (int z = 0; z < ssz; z++) {
				if (plane.getHeightPoint(x, z) == depth) {
					plane.setColorPoint(x,z,MathCalculator.lerp(new Color(255,243,118), new Color(193,50,3), (float)Berlin.noise(x*0.2, z*0.2)));
				}
			}
		}
		plane.genWorld();
		scene.setFog(-2000, -2700);
		boss = new Boss(scene);
		//boss.setInstanceLoc(0, 300, 0);
		scene.add(boss);
		//scene.setFogColor(new Color(93, 109, 120));
		scene.setFogColor(new Color(125,92,87));
		scene.add(new GameWalls(scene));
		scene.setSceneDarkness(40);
	}
	
	private Sound laugh;
	private boolean playLaugh = true;
	private int lastLaugh = 0;
	private Rand laughRand = new Rand();
	public void tick() {
		GameState.instance.playerLevel = 1; //don't want the player to save in a fight.
		lastLoc = getScene().getPosition();
		
		if (Rand.random() < 0.0004)
			getScene().makeLightning();
		/*
		if (SoundManager.soundEnabled && (laugh == null || laugh.isFinished()) && (laughRand.nextFloat() < 0.007 || playLaugh)) {
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
		}*/

		// System.out.println(getScene().getPlayerZ());

		super.tick();
		
		if (isGameHalted())
			return;
		if (getScene().getPlayerY() < -250) {
			if (SoundManager.soundEnabled) {
				new Sound("hurt").play();
				new DelayedThread(new Runnable() {
					public void run() {
						new Sound("laugh2").play();
					}
				},1000).start();
			}
			startTransition(getMain().getScreen("vbm"), new P3D(0,400,0), 0);
			GameState.instance.health = 10;
		}
		
		for (int x = 0; x < ssz; x++) {
			float waterXVal = (float) (Math.cos((planex + x)*0.75f));
			for (int z = 0; z < ssz; z++) {
				if (getScene().getGamePlane().getHeightPoint(x, z) <= depth) {
					float waterZVal = (float) (Math.sin((planez + z)*0.75f));
					getScene().getGamePlane().setHeightPoint(x, z, Math.abs(waterZVal * waterXVal * 50) + ((depth - 110)));
					//getScene().getGamePlane().setColorPoint(x,z,MathCalculator.lerp(new Color(255,243,118), new Color(193,50,3), waterZVal * waterXVal));//(float)Berlin.noise(x*0.2, z*0.2)));
				}
			}
		}
		//getScene().getGamePlane().genWorld();
		
		planex += 0.008f;
		planez += 0.01f;
			
		
		getScene().getPlayer().setEyebrowExpression(FacialExpression.EYEBROWS_INNOCENTLIKE);
		getScene().getPlayer().setMouthExpression(FacialExpression.MOUTH_FROWN);
		if (isGameHalted())
			return;
	}
	private float planex, planez;

	private Color getRoadColor(Color prev) {
		return MathCalculator.lerp(new Color(132, 117, 98), prev, 0.5f);
	}

	public String getName() {
		return "sauce";
	}

	public void draw(Graphics g) {
		scene.draw(g);
	}

	public void drawHUD(Graphics g) {
		
		super.drawHUD(g);
	}
}