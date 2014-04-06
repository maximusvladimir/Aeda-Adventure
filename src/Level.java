import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Level extends Screen {
	protected Scene<Drawable> scene;
	private boolean showFlakes = true;
	private Sign[] signs;
	private Flakes flakes;
	private Rand rand;
	private static BufferedImage vignette = null;
	public Level(Main inst) {
		super(inst);
		PointTesselator.removeAlpha = false;
		if (vignette == null)
			vignette = Utility.generateVignette(getMain().getWidth(), getMain()
				.getHeight());
	}
	
	public Rand getRand() {
		return rand;
	}
	
	public Sign[] getSigns() {
		return signs;
	}
	
	public void setSigns(Sign[] s) {
		signs = s;
	}
	
	public boolean isFlakesVisible() {
		return showFlakes;
	}
	
	public void setFlakesVisible(boolean vis) {
		showFlakes = vis;
	}
	
	public Scene<Drawable> getScene() {
		return scene;
	}
	
	public void silentTick() {
		if (getTimeSinceInit() == -1 || System.currentTimeMillis() - getTimeSinceInit() < 500) {
			getScene().setPlayerMovable(false);
			return;
		}
		else
			getScene().setPlayerMovable(true);
		
		getScene().getPlayer().tick();
		
		for (int i = 0; i < scene.getSceneSize(); i++) {
			try {
				scene.get(i).hit(scene.getPlayer());
				scene.get(i).tick();
			}
			catch (Throwable t) {
				// Ocassionally the other thread removes the object in transit.
			}
		}
		scene.tick();
		sf += 0.01f;
		if (blinkTime != -1) {
			if (System.currentTimeMillis() - blinkTime > 260) {
				blinkTime = -1;
				scene.getPlayer().setEyesOpen(true);
				lastBlink = System.currentTimeMillis() + rand.nextInt(0,2500);
			}
		}
		else if (System.currentTimeMillis() - lastBlink > 4000) {
			scene.getPlayer().setEyesOpen(false);
			blinkTime = System.currentTimeMillis();
		}
		ArrayList<Lamppost> lamps = scene.getObjectsByType(Lamppost.class);
		int darkBuilder = 0;
		for (int i = 0; i < lamps.size(); i++) {
			Lamppost lamp = lamps.get(i);
			if (!lamp.isLampOn())
				continue;
			float dist = lamp.getDistToPlayer();
			if (dist < 700) {
				float ams = (float)(Math.sin(lamp.getWindDelta())*25) + 65;
				darkBuilder += -(ams-(int)(dist/700*ams));
			}
		}
		scene.getPlayer().setIndividualDarkness(darkBuilder);
	}
	long blinkTime = -1;
	long lastBlink = 0;
float sf = 0.0f;
	public void silentInit() {
		if (isFullscreen())
			hideCursor();
		rand = new Rand(4);
		flakes = new Flakes(getMain(), rand, 20);
		playerDelta = GameState.instance.playerDelta;
	}
	
	public abstract void init();

	public void resize(int width, int height) {
		vignette = Utility.generateVignette(getMain().getWidth(), getMain()
				.getHeight());
		scene.resize(width, height);
		if (isFullscreen())
			hideCursor();
		else
			getMain().getContentPane().setCursor(Cursor.getDefaultCursor());
	}
	
	public boolean drawWindows(Graphics g) {
		return false;
	}
	
	public void keyDown(int code) {
		if (!scene.canPlayerMove() || showWorldMap)
			return;
		if (code == KeyEvent.VK_W) {
			scene.movePlayer(true);
		} else if (code == KeyEvent.VK_S) {
			scene.movePlayer(false);
		} else if (code == KeyEvent.VK_A) {
			playerDelta = playerDelta + 0.1f;
		} else if (code == KeyEvent.VK_D) {
			playerDelta = playerDelta - 0.1f;
		} else if (code == KeyEvent.VK_ENTER) {
			scene.getPlayer().hit(); 
		} else if (code == KeyEvent.VK_SPACE) {
			scene.getPlayer().jump();
		} else if (code == KeyEvent.VK_P) {
			scene.useThisMethodSparsingly().setUseWireframeWithShading(!
					scene.useThisMethodSparsingly().getUseWireframeWithShader());
		} else if (code == KeyEvent.VK_G) {
			transition = 1;
		}
		GameState.instance.playerDelta = playerDelta;
		scene.setPlayerDelta(playerDelta);
	}
	
	public void controllerUpdate(ControllerSupport cs) {
		if (!scene.canPlayerMove())
			return;
		playerDelta = Utility.parseMoveDir(scene, cs, playerDelta);
		if (cs.isJumping()) {
			scene.getPlayer().jump();
		}
		if (cs.isAttacking()) {
			scene.getPlayer().hit();
		}
		GameState.instance.playerDelta = playerDelta;
		scene.setPlayerDelta(playerDelta);
	}
	
	private float playerDelta = 0.0f;
	
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_Q) {
			
			for (int i = 0; i < signs.length; i++) { signs[i].qPressed(); }
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

	public abstract void draw(Graphics g);

	public void startTransition(Screen s) {
		transition = 1;
		nextScreen = s;
	}
	
	private Screen nextScreen;
	private float transition;
	private boolean showWorldMap = false;
	public void drawHUD(Graphics g) {
		flakes.draw(g);
		Utility.drawMap(g, getMain(), scene);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		boolean flagForMovable = drawWindows(g);

		if (signs != null)
		for (int i = 0; i < signs.length; i++) {
			if (signs[i].doSigns(g, getMain())) {
				flagForMovable = false;
			}
		}
		if (showWorldMap)
			flagForMovable = false;
		scene.setPlayerMovable(flagForMovable);

		Utility.drawEnemyData(g, getMain(), getScene());
		Utility.drawHealth(g);
		if (GameState.doVignette)
			g.drawImage(vignette, 0, 0, null);
		
		if (showWorldMap) {
			Utility.drawWorldMap(g,getMain(),scene);
		}

		g.setFont(new Font("Arial", 0, 12));
		g.setColor(Color.white);
		g.drawString("FPS:" + getMain().getFPS(), 0, 10);
		if (getMain().isFullscreen()) {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0, getMain().getHeight() - 3);
		} else {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0, getMain().getHeight() - 30);
		}

		if (isFullscreen()) {
			drawCursor(g);
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		if (transition >= -0.1f) {
			transition -= 0.008f;
			// player.moving = false;
			// canMove = false;
			scene.getPlayer().moving = false;
			scene.setPlayerMovable(false);
			g.setColor(new Color(10,30,10, MathCalculator
					.colorLock(255 - (int) (255 * transition))));
			g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
			if (transition <= 0.00) {
				transition = -10;
				if (nextScreen != null) {
					if (!getMain().screenExists(nextScreen.getName()))
						getMain().addScreen(nextScreen);
					getMain().setActiveScreen(nextScreen.getName());
				}
			}
		}
	}

	public abstract String getName();

}
