import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FiaceForest extends Level {

	public FiaceForest(Main inst) {
		super(inst);
	}

	private long timeSinceTheSecondGUI = Long.MAX_VALUE;
	public static long mapDrawTime = 0;

	public void init() {
		Gem[] gems = new Gem[40];// 40
		Tree[] trees = new Tree[60];// 20
		Grass[] grass = new Grass[80];
		Barrel[] barrel = new Barrel[20];
		Sign[] signs = new Sign[5];
		scene = new Scene<Drawable>(this, getRand(), 55, new Color(110, 130, 110),
				14, 0.5f);
		scene.setFog(-2100, -2600);// -2550);
		//scene.setFogColor(new Color(140, 140, 165));
		scene.setFogColor(new Color(93,109,120));
		for (int i = 0; i < gems.length; i++) {
			gems[i] = new Gem(scene, getRand());
			gems[i].setInstanceLoc(getRand().nextLocation(-170));
		}
		// an interesting grass placement algorithm that i came up with.
		int currentGrass = 0;
		while (currentGrass < grass.length) {
			int remaining = grass.length - currentGrass;
			int currentNum = getRand().nextInt(remaining / 6, remaining / 4);
			if (currentNum <= 0)
				currentNum = 1;
			int indexer = 0;
			P3D ps = getRand().nextLocation(0);
			while (indexer < currentNum) {
				indexer++;
				float offsetX = getRand().nextInt(-550, 550);
				float offsetZ = getRand().nextInt(-550, 550);
				P3D ms = new P3D(ps.x + offsetX, scene.getTerrainHeight(ps.x
						+ offsetX, ps.z + offsetZ) - 410, ps.z + offsetZ);
				if (currentGrass > grass.length - 1)
					break;
				grass[currentGrass] = new Grass(scene);
				grass[currentGrass++].setInstanceLoc(ms);
			}
		}
		for (int i = 0; i < trees.length; i++) {
			trees[i] = new Tree(scene, getRand());
			trees[i].setInstanceLoc(getRand().nextLocation(-400));
		}
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
			signs[i].setInstanceLoc(getRand().nextLocation(-180));
			signs[i].setSignMessage("Sign #" + (i + 1));
		}
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, getRand());
			barrel[i].setInstanceLoc(getRand().nextLocation(-300));
		}
		setSigns(signs);
		scene.add(grass);
		scene.add(gems);
		scene.add(trees);
		scene.add(signs);
		scene.add(barrel);
		scene.add(new GameWalls(scene));
		Enemy en = new Enemy(scene);
		en.setInstanceLoc(new P3D(-400,-300,0));
		scene.add(en);
		Lamppost lamp = new Lamppost(scene);
		lamp.setInstanceLoc(new P3D(2000,-300,0));
		scene.add(lamp);
		Well well = new Well(scene);
		well.setInstanceLoc(new P3D(2000,-300,300));
		scene.add(well);
		//scene.add(new Windmill(scene));
		/*House h = new House(scene);
		h.setHouseName("-- Blacksmith --");
		h.setInstanceLoc(new P3D(0,-300,0));
		scene.add(h);*/

		if (GameState.instance.playerStage == 0) {
			GameState.instance.playerStage = 1;
			GameState.save();
		} else
			displayFirstGUI = false;
	}
	
	public String getName() {
		return "level";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		scene.setSceneDarkness(0);
		scene.draw(g);
	}

	public void mouseReleased(MouseEvent me) {
		ArrayList<House> houses = scene.<House>getObjectsByType(House.class);
		for (int i = 0;  i< houses.size(); i++) {
			houses.get(i).lightsOn = !houses.get(i).lightsOn;
		}
		ArrayList<Lamppost> lamps = scene.<Lamppost>getObjectsByType(Lamppost.class);
		for (int i = 0;  i< lamps.size(); i++) {
			lamps.get(i).setLampOn(!lamps.get(i).isLampOn());
		}
	}
	
	public void keyReleased(KeyEvent ke) {
		super.keyReleased(ke);
		if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
			if (displayFirstGUI) {
				displayFirstGUI = false;
				displaySecondGUI = true;
			} else if (displaySecondGUI) {
				displaySecondGUI = false;
				displayThirdGUI = true;
				GameState.instance.playerStage = 1;
				GameState.save();
				timeSinceTheSecondGUI = System.currentTimeMillis();
			} else if (displayThirdGUI) {
				displayThirdGUI = false;
			} else {
				scene.getPlayer().jump();
			}
			if (GameState.instance.gems == 1 && !displayedFourthGUI)
				displayedFourthGUI = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_B) {
			GameState.instance.health = 10;
		}
	}
	
	private int menum = 0;
	private int eenum = 0;
	private boolean displayFirstGUI = true;
	private boolean displaySecondGUI = false;
	private boolean displayThirdGUI = false;
	private boolean displayedFourthGUI = false;

	public boolean drawWindows(Graphics g) {
		// TODO replace with Queue.
		boolean flagForMovable = false;
		if (displayFirstGUI) {
			Utility.showDialog("Welcome to Fi\u00E4ce Forest, "
					+ GameState.instance.playerGUID + ". Press 'Q'.", g,
					getMain());
			flagForMovable = false;
		} else if (displaySecondGUI) {
			Utility.showDialog(
					"Press 'Q' to skip the dialogues or jump."
							+ "\nPress 'E' to attack.\nPress 'WASD' to move around.\nThe game automatically saves every 10 seconds, so there is no\nneed to save.",
					g, getMain());
			flagForMovable = false;
		} else if (displayThirdGUI
				&& System.currentTimeMillis() - timeSinceTheSecondGUI > 4000) {
			Utility.showDialog(
					"You should try to find as many gems as you can.\nIf you encounter enemies along the way, remember to press 'E'.\nFind Aeda's Imperial Crown Piece to open the door to Holm Village.",
					g, getMain());
			flagForMovable = false;
		} else if (GameState.instance.gems == 1 && !displayedFourthGUI) {
			flagForMovable = false;
			Utility.showDialog(
					"You collected a gem. Each time you collect 20 gems, your health   meter increases by 1.",
					g, getMain());
		} else
			flagForMovable = true;
		return flagForMovable;
	}
}
