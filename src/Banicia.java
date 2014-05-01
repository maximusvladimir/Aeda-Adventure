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
		Barrel[] barrel = new Barrel[5];
		Sign[] signs = new Sign[3];
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
		scene.setFogColor(new Color(75, 70, 54).darker());
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
		}
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, getRand());
		}
		signs[0].setInstanceLoc(600, -220, 2500);
		signs[0].setSignMessage(Strings.inst.HOLM_VILLAGE_SIGN);

		signs[1].setInstanceLoc(600, -220, -9800);
		signs[1].setSignMessage(Strings.inst.HOLM_VILLAGE_NORTH_ENTRY);

		signs[2].setInstanceLoc(300, -220, 7200);
		signs[2].setSignMessage("Don't forget to fight enemies with ENTER.\nYou can also break barrels as well.");

		barrel[0].setInstanceLoc(600, -350, 2000);
		barrel[1].setInstanceLoc(-900, -350, 2075);
		barrel[2].setInstanceLoc(600, -350, 3500);
		barrel[3].setInstanceLoc(850, -300, -450);
		barrel[4].setInstanceLoc(-1000, -300, -450);

		scene.add(new Bat(scene));
		scene.add(new Bat(scene));
		scene.add(new Bat(scene));
		scene.add(new Bat(scene));
		scene.add(new Bat(scene));
		scene.add(new Bat(scene));
		
		setSigns(signs);
		scene.add(signs);
		scene.add(barrel);
		// roughness, color variance, resolution
		SimplePlane roof = new SimplePlane(scene, new Color(135,111,52), 250, 30, 50, plane.getWorldSize());
		roof.setInstanceLoc(0,230,0);
		scene.add(roof);
		scene.add(new GameWalls(scene));
		Lamppost notfs = new Lamppost(scene);
		notfs.setInstanceLoc(-6000, -375, 0);
		notfs.updateInstLoc();
		scene.add(notfs);
	}

	public void tick() {
		GameState.instance.playerLevel = 4;
		super.tick();
		if (isGameHalted())
			return;
		
		scene.setSceneDarkness(100- ((int)(getScene().getPlayer().flameSize * 40)));
		
		if (getScene().getPlayerX() < -9500 && getScene().getPlayerZ() > -600
				&& getScene().getPlayerZ() < 1100 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("vbm"), new P3D(8500, 0,
					getScene().getPlayerZ()), getScene().getPlayerDelta());
			getScene().deportal();
			GameState.instance.playerLevel = 1;
			return;
		}
		if ((getScene().getPlayerZ() < 9400 || getScene().getPlayerX() > 700 || getScene()
				.getPlayerZ() < -700)
				&& (getScene().getPlayerZ() > 700
						|| getScene().getPlayerX() > 9400 || getScene()
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
