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

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Level extends Screen {

	public Level(Main inst) {
		super(inst);
	}

	private long timeSinceTheSecondGUI = Long.MAX_VALUE;
	private Sign[] signs;
	public static long mapDrawTime = 0;
	private Scene<Drawable> scene;

	public void init() {
		if (isFullscreen())
			hideCursor();
		vignette = Utility.generateVignette(getMain().getWidth(), getMain()
				.getHeight());
		Gem[] gems = new Gem[40];// 40
		Tree[] trees = new Tree[60];// 20
		Grass[] grass = new Grass[80];
		Barrel[] barrel = new Barrel[20];
		signs = new Sign[5];
		rand = new Rand(4);
		flakes = new Flakes(getMain(), rand, 25);
		scene = new Scene<Drawable>(this, rand, 55, new Color(110, 130, 110),
				14, 0.5f);
		scene.setFog(-2100, -2600);//-2550);
		scene.setFogColor(new Color(140, 140, 165));
		//scene.setFogColor(new Color(101,101,116));
		playerDelta = GameState.instance.playerDelta;
		for (int i = 0; i < gems.length; i++) {
			gems[i] = new Gem(scene, rand);
			gems[i].setInstanceLoc(rand.nextLocation(-170));
		}
		// an interesting grass algorithm.
		int currentGrass = 0;
		while (currentGrass < grass.length) {
			int remaining = grass.length - currentGrass;
			int currentNum = rand.nextInt(remaining / 6, remaining / 4);
			if (currentNum <= 0)
				currentNum = 1;
			int indexer = 0;
			P3D ps = rand.nextLocation(0);
			while (indexer < currentNum) {
				indexer++;
				float offsetX = rand.nextInt(-550, 550);
				float offsetZ = rand.nextInt(-550, 550);
				P3D ms = new P3D(ps.x+offsetX,scene.getTerrainHeight(ps.x+offsetX,ps.z+offsetZ)-410,ps.z+offsetZ);
				if (currentGrass > grass.length - 1)
					break;
				grass[currentGrass] = new Grass(scene);
				grass[currentGrass++].setInstanceLoc(ms);
			}
		}
		for (int i = 0; i < trees.length; i++) {
			trees[i] = new Tree(scene, rand);
			trees[i].setInstanceLoc(rand.nextLocation(-400));
		}
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
			signs[i].setInstanceLoc(rand.nextLocation(-180));
			signs[i].setSignMessage("Sign #" + (i + 1));
		}
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, rand);
			barrel[i].setInstanceLoc(rand.nextLocation(-350));
		}
		scene.add(grass);
		scene.add(gems);
		scene.add(trees);
		scene.add(signs);
		scene.add(barrel);
		scene.add(new GameWalls(scene));

		if (GameState.instance.playerStage == 0) {
			GameState.instance.playerStage = 1;
			GameState.save();
		} else
			displayFirstGUI = false;
	}

	public void tick() {
		/*
		 * if (System.currentTimeMillis() - timeSinceRan > 4000) { for (int i =
		 * 0; i < gems.length; i++) { if (gems[i].staticPos.z > walkZ ||
		 * gems[i].staticPos.z + 2550 < walkZ) continue; gems[i].tick(); if
		 * (player.hit(gems[i])) { gems[i].setVisible(false);
		 * SoundManager.playGem = true; GameState.instance.gems++;
		 * GameState.instance.score += 2; if (GameState.instance.gems % 20 == 0)
		 * { if (GameState.instance.health < 10) GameState.instance.health++; }
		 * } } }
		 */
		/*
		 * for (int i = 0; i < signs.length; i++) { if (signs[i].staticPos.z >
		 * walkZ || signs[i].staticPos.z + 2550 < walkZ) continue;
		 * signs[i].setUserPosition(walkX, walkZ); signs[i].tick(); }
		 */
		if (attackTicks <= 0.01f) {
			attackTicks = 0.0f;
		} else {
			attackTicks -= 0.07f;
		}
		GameState gs = new GameState();
		gs.playerGUID = GameState.instance.playerGUID;
		gs.gems = GameState.instance.gems;
		gs.health = GameState.instance.health;
		// gs.playerLocation = new P3D(walkX, 0, walkZ);
		// gs.playerDelta = playerDelta;
		gs.playerStage = GameState.instance.playerStage;
		gs.score = GameState.instance.score;
		Network.pushPlayerInfo(gs);
	}

	private Rand rand;

	public static BufferedImage vignette;

	public void resize(int width, int height) {
		vignette = Utility.generateVignette(getMain().getWidth(), getMain()
				.getHeight());
		scene.resize(width, height);
		if (isFullscreen())
			hideCursor();
		else
			getMain().getContentPane().setCursor(Cursor.getDefaultCursor());
	}

	public String getName() {
		return "level";
	}

	float delta = 0.0f;
	float theta = 0.0f;
	float test = 0;

	float attackTicks = 0.0f;

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		delta += 0.005f;
		test += 0.05f;
		scene.setSceneDarkness(0);
		scene.draw(g);
	}

	public void mouseReleased(MouseEvent me) {
		if (inButton != 0) {
			int id = (inButton - 120) / 60;
			// System.out.println("you pressed button# " + id);
			if (id == 0) {
				// if (getMain().getNumScreens() == 1)
				// getMain().addScreen(new CreateGameMenu(getMain()));
				// getMain().setActiveScreen(1);
			}
			if (id == 2)
				System.exit(0);
		}
	}

	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_Q) {
			/*
			 * for (int i = 0; i < signs.length; i++) { signs[i].qPressed(); }
			 */
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
		} else if (ke.getKeyCode() == KeyEvent.VK_W
				|| ke.getKeyCode() == KeyEvent.VK_S) {
			scene.getPlayer().moving = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.save();
			getMain().setActiveScreen("mainmenu");
		}
		if (ke.getKeyCode() == KeyEvent.VK_M) {
			showWorldMap = !showWorldMap;
			if (showWorldMap) {
				scene.getPlayer().moving = false;
				scene.setPlayerMovable(false);
			}
			else {
				scene.setPlayerMovable(true);
			}
		}
	}

	public void keyDown(int code) {
		if (!scene.canPlayerMove())
			return;
		if (code == KeyEvent.VK_W) {
			scene.movePlayer(true);
		} else if (code == KeyEvent.VK_S) {
			scene.movePlayer(false);
		} else if (code == KeyEvent.VK_A) {
			playerDelta = playerDelta + 0.1f;
		} else if (code == KeyEvent.VK_D) {
			playerDelta = playerDelta - 0.1f;
		} else if (code == KeyEvent.VK_E) {
			attackTicks = 1;
		}
		GameState.instance.playerDelta = playerDelta;
		scene.setPlayerDelta(playerDelta);
	}

	public void keyTyped(KeyEvent ke) {
	}

	private float playerDelta = 0.0f;
	private float transition = 0.0f;
	private boolean displayFirstGUI = true;
	private boolean displaySecondGUI = false;
	private boolean displayThirdGUI = false;
	private boolean displayedFourthGUI = false;
	private Flakes flakes;
	private boolean showWorldMap = false;

	public void drawHUD(Graphics g) {
		flakes.draw(g);
		Utility.drawMap(g, getMain(), scene);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

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

		for (int i = 0; i < signs.length; i++) {
			if (signs[i].doSigns(g, getMain())) {
				flagForMovable = false;
			}
		}
		if (showWorldMap)
			flagForMovable = false;
		scene.setPlayerMovable(flagForMovable);

		Utility.qS();
		Utility.drawHealth(g);
		if (GameState.doVignette)
			g.drawImage(vignette, 0, 0, null);
		mapDrawTime = Utility.qEL();
		
		if (showWorldMap) {
			Utility.drawWorldMap(g,getMain());
		}

		g.setFont(new Font("Arial", 0, 12));
		g.setColor(Color.white);
		g.drawString("FPS:" + getMain().getFPS(), 0, 10);
		if (getMain().isFullscreen()) {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms"
					+ mapDrawTime, 0, getMain().getHeight() - 3);
		} else {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms"
					+ mapDrawTime, 0, getMain().getHeight() - 30);
		}

		if (isFullscreen()) {
			drawCursor(g);
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		if (transition >= 0.05f) {
			transition -= 0.01f;
			// player.moving = false;
			// canMove = false;
			scene.getPlayer().moving = false;
			scene.setPlayerMovable(false);
			g.setColor(new Color(0, 0, 0, MathCalculator
					.colorLock(255 - (int) (255 * transition))));
			g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		}
	}
}
