import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Banicia extends Level {
	public Banicia(IMain inst) {
		super(inst);
	}

	public void loadedLevel() {
		super.loadedLevel();
	}

	public static long mapDrawTime = 0;

	public void init() {
		Barrel[] barrel = new Barrel[15];
		scene = new Scene<Drawable>(this, getRand());
		GamePlane plane = new GamePlane(scene, getRand(), 55, new Color(113,77,53), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, plane.getHeightPoint(x, z) / 4);
			}
		}
		scene.setPlane(plane);
		/*for (int z = 0; z < 55; z++) {
			if (z > 26) {
				plane.setColorPoint(26, z, getRoadColor(plane.getColorPoint(26, z)));
				plane.setColorPoint(27, z, getRoadColor(plane.getColorPoint(27, z)));
				plane.setColorPoint(28, z, getRoadColor(plane.getColorPoint(28, z)));
			}
			
			plane.setColorPoint(z, 27, getRoadColor(plane.getColorPoint(z, 27)));
			plane.setColorPoint(z, 28, getRoadColor(plane.getColorPoint(z, 28)));
			plane.setColorPoint(z, 29, getRoadColor(plane.getColorPoint(z, 29)));
		}*/
		plane.genWorld();
		scene.setFog(-2000, -2600);
		//scene.setFogColor(new Color(75, 70, 54).darker());
		scene.setFogColor(new Color(75, 70, 54).darker().darker().darker());
		/*for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
		}*/
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, getRand());
			barrel[i].setInstanceLoc(getRand().nextLocation(-350));
		}

		
		for (int i = 0; i < 30; i++) {
			Drawable gem = new Gem(getScene(),getRand());
			if (getRand().nextFloat() < 0.2) {
				gem = new RedGem(getScene(),getRand());
			}
			gem.setInstanceLoc(getRand().nextLocation(-150));
			scene.add(gem);
		}
		
		for (int i = 0; i < 27; i++) {
			Bat bat = new Bat(scene);
			bat.setInstanceLoc(getRand().nextLocation(-75));
			scene.add(bat);
		}
		
		//setSigns(signs);
		//scene.add(signs);
		scene.add(barrel);
		// roughness, color variance, resolution
		SimplePlane roof = new SimplePlane(scene, new Color(135,111,52), 250, 30, 50, plane.getWorldSize());
		roof.setInstanceLoc(0,650,0);
		scene.add(roof);
		scene.add(new GameWalls(scene));
		Lamppost notfs = new Lamppost(scene);
		notfs.setInstanceLoc(-6000, -375, 0);
		notfs.updateInstLoc();
		scene.add(notfs);
		
		if (!GameState.instance.hasKey) {
			Key key = new Key(scene);
			Rand rand = new Rand();
			rand.setScene(scene);
			key.setInstanceLoc(rand.nextLocation(-50));
			scene.add(key);
		}
	}

	public void tick() {
		GameState.instance.playerLevel = 4;
		super.tick();
		if (isGameHalted())
			return;
		
		scene.setSceneDarkness(100- ((int)(getScene().getPlayer().flameSize * 40)));
		
		float space = getScene().getGamePlane().getSpacing();
		if (getScene().getPlayerX() < -25.3333 * space && getScene().getPlayerZ() > -600
				&& getScene().getPlayerZ() < 1100 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("vbm"), new P3D(22.6666666666f * space, 0,
					getScene().getPlayerZ()), getScene().getPlayerDelta());
			getScene().deportal();
			GameState.instance.playerLevel = 1;
			return;
		}
		if ((getScene().getPlayerZ() < 25.0666666 * space || getScene().getPlayerX() > 700 || getScene()
				.getPlayerZ() < -700)
				&& (getScene().getPlayerZ() > 700
						|| getScene().getPlayerX() > 25.0666666 * space || getScene()
						.getPlayerZ() < -700))
			getScene().reportal();
	}

	private Color getRoadColor(Color prev) {
		return MathCalculator.lerp(new Color(132, 117, 98), prev, 0.5f);
	}

	public String getName() {
		return "tumalarda";
	}

	public void draw(Graphics g) {
		scene.draw(g);
	}

	public void drawHUD(Graphics g) {
		
		super.drawHUD(g);
	}
}
