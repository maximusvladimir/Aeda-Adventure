import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Level extends Screen {

	public Level(Main inst) {
		super(inst);
	}

	private SceneTesselator scene;
	private Gem[] gems;
	private Tree[] trees;
	private Grass[] grass;
	private Rock rock;
	private Player player;
	private Player otherPlayer;
	private GamePlane plane;
	private Flake[] parts;
	private long timeSinceTheSecondGUI = Long.MAX_VALUE;
	private Enemy enemy;
	private boolean canMove = true;
	private long timeSinceRan = 0;
	private float prevWalkX = -10000000.0f;
	private float prevWalkZ = -10000000.0f;
	private GameWalls walls;
	private Sign[] signs;

	public void tick() {
		if (System.currentTimeMillis() - timeSinceRan > 4000) {
			for (int i = 0; i < gems.length; i++) {
				if (gems[i].staticPos.z > walkZ || gems[i].staticPos.z + 2550 < walkZ)
					continue;
				gems[i].tick();
				if (player.hit(gems[i])) {
					gems[i].setVisible(false);
					SoundManager.playGem = true;
					GameState.instance.gems++;
					GameState.instance.score += 2;
					if (GameState.instance.gems % 20 == 0) {
						if (GameState.instance.health < 10)
							GameState.instance.health++;
					}
				}
			}
			/*enemy.findPlayer(walkX, walkZ);
			if (enemy.hit(player)) {
				GameState.instance.health -= 0.005f;
				if (GameState.instance.health <= 0.0f) {
					getMain().removeScreen(this);
					getMain().removeScreen(getMain().getScreen(0));
					getMain().addScreen(new MainMenu(getMain()));
					GameState.instance = null;
					new FileSave().delete();
					getMain().setActiveScreen(0);
					JOptionPane.showMessageDialog(getMain(), "Sorry. You lose.");
				}
			}
			enemy.tick();*/
		}
		for (int i = 0; i < signs.length; i++) {
			if (signs[i].staticPos.z > walkZ || signs[i].staticPos.z + 2550 < walkZ)
				continue;
			signs[i].setUserPosition(walkX,walkZ);
			signs[i].tick();
		}
		player.tick();
		GameState gs = new GameState();
		gs.playerGUID = GameState.instance.playerGUID;
		gs.gems = GameState.instance.gems;
		gs.health= GameState.instance.health;
		gs.playerLocation = new P3D(walkX,0,walkZ);
		gs.playerDelta = playerDelta;
		gs.playerStage = GameState.instance.playerStage;
		gs.score = GameState.instance.score;
		Network.pushPlayerInfo(gs);
	}
	private Rand rand;

	public void init() {
		if (isFullscreen())
			hideCursor();
		timeSinceRan = System.currentTimeMillis();
		gems = new Gem[40];//40
		rand = new Rand(4);
		trees = new Tree[60];//20
		grass = new Grass[20];
		signs = new Sign[5];
		plane = new GamePlane(rand);
		scene = new SceneTesselator();
		enemy = new Enemy();
		scene.addTesselator(enemy.getTesselator());
		for (int i = 0; i < gems.length; i++) {
			gems[i] = new Gem(rand);
			float dx = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dz = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dy = -150 + plane.getLocation(dx, dz);
			gems[i].staticPos = new P3D(dx,dy,dz);
			scene.addTesselator(gems[i].getTesselator());
		}
		for (int i = 0; i < grass.length; i++) {
			grass[i] = new Grass();
			float dx = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dz = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dy = -410 + plane.getLocation(dx, dz);
			grass[i].staticPos = new P3D(dx,dy,dz);
			scene.addTesselator(grass[i].getTesselator());
		}
		for (int i = 0; i < trees.length; i++) {
			trees[i] = new Tree(rand);
			float dx = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dz = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dy = -400 + plane.getLocation(dx, dz);
			trees[i].staticPos = new P3D(dx,dy,dz);
			scene.addTesselator(trees[i].getTesselator());
		}
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign();
			float dx = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dz = (float) (rand.nextDouble() * -GamePlane.WORLDSIZE) + GamePlane.WORLDSIZEHALF;
			float dy = -250 + plane.getLocation(dx, dz);
			signs[i].staticPos = new P3D(dx,dy,dz);
			signs[i].setSignMessage("Sign #" + (i+1));
			scene.addTesselator(signs[i].getTesselator());
		}
		walls = new GameWalls();
		scene.addTesselator(walls.getTesselator());
		player = new Player();
		plane.setPlayer(player);
		otherPlayer = new Player();
		scene.addTesselator(otherPlayer.getTesselator());
		player.delta = GameState.instance.playerDelta;
		playerDelta = GameState.instance.playerDelta;
		player.playerColor = GameState.instance.playerColor;
		scene.addTesselator(player.getTesselator());
		rock = new Rock(rand);
		scene.addTesselator(rock.getTesselator());
		enemy.staticPos = new P3D((float) (rand.nextDouble() * -GamePlane.WORLDSIZEHALF), 0,
				(float) (rand.nextDouble() * -GamePlane.WORLDSIZEHALF));
		/*try {
			plane.getTesselator().loadTexture(new Texture(ImageIO.read(Level.class.getResource("icon.png"))));
		}
		catch (Throwable t) {
			
		}*/
		scene.addTesselator(plane.getTesselator());
		//scene.setUseWireframeWithShading(true);

		parts = new Flake[25];
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Flake();
			parts[i].x = (float) (rand.nextDouble() * getMain().getWidth());
			parts[i].y = (float) (rand.nextDouble() * getMain().getHeight());
			parts[i].dx = (float) (rand.nextDouble() * 1.1f);
			parts[i].size = (int) (rand.nextDouble() * 4);
			parts[i].clr = (int) (rand.nextDouble() * 127) + 127;
			if (rand.nextDouble() < 0.5)
				parts[i].dx *= -1;
		}

		if (GameState.instance.playerStage == 1000) {
			GameState.instance.playerStage = 0;
			GameState.save();
		}
		if (GameState.instance.playerStage >= 1)
			displayFirstGUI = false;
		walkX = GameState.instance.playerLocation.x;
		walkZ = GameState.instance.playerLocation.z;
	}

	public void resize(int width, int height) {
		// terrain.setSize(getCompatabilityBuffer(), width, height);
		player.getTesselator().setSize(getCompatabilityBuffer(),
				getMain().getWidth(), getMain().getHeight());
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

	float walkX = 0.0f;
	float walkZ = 0.0f;
	float playerDelta = (float) (Math.PI / 180.0f * 90);

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		//plane.getTesselator().setSize(getMain().getBuffer(), getMain().getWidth(), getMain().getHeight());
		delta += 0.005f;
		test += 0.05f;
		for (int i = 0; i < gems.length; i++) {
			if (gems[i].staticPos.z > walkZ || gems[i].staticPos.z + 2550 < walkZ)
				continue;
			if (gems[i].staticPos.x-1700 > walkX || gems[i].staticPos.x + 1700 < walkX)
				continue;
			gems[i].setPositon(new P3D(-walkX, 0, -walkZ));
			gems[i].draw((int) (Math.sin(test) * 40));
		}

		player.setPlayerDelta(playerDelta);
		GameState.FIXEDLOC = new P3D(-walkX, plane.getHeight(), -walkZ);
		player.setPositon(new P3D(0, plane.getHeight(), -600));//was 0,-100,-500// CHANGEDTO-150
		player.draw(0);//(int) (Math.sin(test) * 40));
		float playH = 0.0f;//-plane.getHeight();
		
		if (Network.getPlayerGameState() != null) {
			GameState other = Network.getPlayerGameState();
			otherPlayer.setPlayerDelta(other.playerDelta);
			otherPlayer.playerColor = other.playerColor;
			//System.out.println(other.playerLocation);
			otherPlayer.setPositon(new P3D(other.playerLocation.x+-walkX, -100,-walkZ+other.playerLocation.z+-500));//was 0,-100,-500// CHANGEDTO-150
			otherPlayer.draw(0);
		}

		rock.setPositon(new P3D(-walkX, -300+playH, -50 + -walkZ));
		rock.draw(0);

		//enemy.setPositon(new P3D(-walkX, -200+playH, -500 + -walkZ));
		//enemy.draw(0);
		for (int i = 0; i < trees.length; i++) {
			if (trees[i].staticPos.z > walkZ || trees[i].staticPos.z + 2550 < walkZ)
				continue;
			if (trees[i].staticPos.x-1700 > walkX || trees[i].staticPos.x + 1700 < walkX)
				continue;
			trees[i].setPositon(new P3D(-walkX, playH, -walkZ));
			trees[i].draw(0);
		}
		for (int i = 0; i < grass.length; i++) {
			if (grass[i].staticPos.z > walkZ || grass[i].staticPos.z + 2550 < walkZ)
				continue;
			if (grass[i].staticPos.x-1700 > walkX || grass[i].staticPos.x + 1700 < walkX)
				continue;
			grass[i].setPositon(new P3D(-walkX, playH, -walkZ));
			grass[i].draw(0);//(int) (Math.sin(test) * 40));
		}
		
		for (int i = 0; i < signs.length; i++) {
			if (signs[i].staticPos.z > walkZ || signs[i].staticPos.z + 2550 < walkZ)
				continue;
			if (signs[i].staticPos.x-1700 > walkX || signs[i].staticPos.x + 1700 < walkX)
				continue;
			signs[i].setPositon(new P3D(-walkX, playH, -walkZ));
			signs[i].draw(0);//(int) (Math.sin(test) * 40));
		}
		
		plane.setPlayer(walkX, walkZ);
		plane.setPositon(new P3D(-walkX, -390+playH, -500 + -walkZ));
		plane.draw(0);
		
		walls.setPositon(new P3D(-walkX,playH,-walkZ));
		walls.draw(0);
		//water.draw(0);

		// scene.fog(new Color(140,140,180), -1400, -1550);
		// scene.fog(new Color(140,140,165), -1700, -1890);
		Color fogColor = new Color(140, 140, 165);
		scene.fog(fogColor, -2200, -2550);//-2300,-2550
		scene.setReverseFogEquation(true);
		//Utility.qS();
		scene.draw(g);
		//Utility.qE();
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
			for (int i = 0; i < signs.length; i++) {
				signs[i].qPressed();
			}
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
				player.jump();
			}
			if (GameState.instance.gems == 1 && !displayedFourthGUI)
				displayedFourthGUI = true;
		}
		else if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_S) {
			player.moving = false;
		}
		else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.save();
			getMain().setActiveScreen("mainmenu");
		}
	}

	private float walkSpeed = 19;//was21

	public void movePlayer(boolean forward) {
		float te = GamePlane.WORLDSIZEHALF;
		if (!SoundManager.playFootstep1 && !SoundManager.playFootstep2) {
			if (rand.nextDouble() < 0.5)
				SoundManager.playFootstep1 = true;
			else
				SoundManager.playFootstep2 = true;
		}
		if (forward) {
			walkX += walkSpeed * Math.cos(playerDelta);
			walkZ -= walkSpeed * Math.sin(playerDelta);
		}
		else {
			walkX -= walkSpeed * Math.cos(playerDelta) * 0.75f;
			walkZ += walkSpeed * Math.sin(playerDelta) * 0.75f;
		}
		for (int i = 0; i < trees.length; i++) {
			if (trees[i].hit(player)) {
				//walkX = walkX - Math.abs((walkX - prevWalkX) * 2);
				//walkZ = walkZ - Math.abs((walkZ - prevWalkZ) * 2);
				
				break;
			}
		}
		for (int i = 0; i < signs.length; i++) {
			if (signs[i].hit(player)) {
				walkX = walkX - Math.abs((walkX - prevWalkX) * 2);
				walkZ = walkZ - Math.abs((walkZ - prevWalkZ) * 2);
				break;
			}
		}
		if (walkX < -te+500)
			walkX = -te+500;
		if (walkZ < -te+200)
			walkZ = -te+200;
		if (walkX > te-850)
			walkX = te-850;
		if (walkZ > te-310)
			walkZ = te-310;
		//System.out.println(walkX + "," + walkZ);
		prevWalkX = walkX;
		prevWalkZ = walkZ;
		GameState.instance.playerLocation = new P3D(walkX, 0, walkZ);
		player.moving = true;
	}

	public void keyDown(int code) {
		if (!canMove)
			return;
		if (code == KeyEvent.VK_W) {
			movePlayer(true);
		} else if (code == KeyEvent.VK_S) {
			movePlayer(false);
		} else if (code == KeyEvent.VK_A) {
			playerDelta += 0.1f;
			GameState.instance.playerDelta = playerDelta;
		} else if (code == KeyEvent.VK_D) {
			playerDelta -= 0.1f;
			GameState.instance.playerDelta = playerDelta;
		}
	}
	
	public void keyTyped(KeyEvent ke) {
	}

	private boolean displayFirstGUI = true;
	private boolean displaySecondGUI = false;
	private boolean displayThirdGUI = false;
	private boolean displayedFourthGUI = false;

	public void drawHUD(Graphics g) {
		for (int i = 0; i < parts.length; i++) {
			Flake flake = parts[i];
			g.setColor(new Color(255, 255, 255, 100));
			flake.x += flake.dx * 0.3f;
			flake.y += flake.size * 0.05f;
			if (flake.dx < 0)
				if (flake.x < 0) {
					flake.x = (float) (getMain().getWidth() + (rand.nextDouble() * (getMain()
							.getWidth() / 4.0f)));
				} else if (flake.x > getMain().getWidth())
					flake.x = (float) (-rand.nextDouble() * (getMain().getWidth() / 4.0f));
			if (rand.nextDouble() < 0.005)
				flake.size--;
			if (flake.size <= 0) {
				flake.y = (int) (rand.nextDouble() * getMain().getHeight());// (float)(-Math.random()
																		// *
																		// (getMain().getHeight()
																		// *
																		// 0.2f));
				flake.size = (int) ((rand.nextDouble() * 3) + 4);
			}
			if (flake.size == 1)
				g.drawLine((int) flake.x, (int) flake.y, (int) flake.x,
					(int) flake.y);
			else
				g.drawOval((int) flake.x, (int) flake.y, flake.size, flake.size);
		}

		Utility.drawMap(g, getMain(), player, gems, trees,signs,null, walkX, walkZ);
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		boolean flagForMovable = false;
		if (displayFirstGUI) {
			Utility.showDialog("Hello " + GameState.instance.playerGUID
					+ ". Press 'Q'.", g, getMain());
			canMove = false;
			flagForMovable = false;
		} else if (displaySecondGUI) {
			Utility.showDialog(
					"Press 'Q' to skip the dialogues or jump."
							+ "\nPress 'E' to attack.\nPress 'WASD' to move around.\nThe game automatically saves every 10 seconds, so there is no\nneed to save.",
					g, getMain());
			canMove = false;
			flagForMovable = false;
		} else if (displayThirdGUI
				&& System.currentTimeMillis() - timeSinceTheSecondGUI > 4000) {
			Utility.showDialog(
					"You should try to find as many gems as you can.\nIf you encounter enemies along the way, remember to press 'E'.\nFind Aeda's Imperial Crown to move to the next level.",
					g, getMain());
			canMove = false;
			flagForMovable = false;
		} else if (GameState.instance.gems == 1 && !displayedFourthGUI) {
			canMove = false;
			flagForMovable = false;
			Utility.showDialog(
					"You collected a gem. Each time you collect 20 gems, your health   meter increases by 1.",
					g, getMain());
		} else
			flagForMovable = true;
		
		for (int i = 0; i < signs.length; i++) {
			if (signs[i].doSigns(g,getMain())) {
				canMove = false;
				flagForMovable = false;
			}
		}
		canMove = flagForMovable;

		//Utility.drawHealth(g);

		g.setFont(new Font("Arial", 0, 12));
		g.setColor(Color.white);
		g.drawString("FPS:" + getMain().getFPS(), 0, 10);
		if (getMain().isFullscreen()) {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0,getMain().getHeight()-3);
		}
		else {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0,getMain().getHeight()-30);
		}

		if (isFullscreen()) {
			drawCursor(g);
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}
