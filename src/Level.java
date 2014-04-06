import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class Level extends Screen {
	protected Scene<Drawable> scene;
	private boolean showFlakes = true;
	private Sign[] signs;
	private Flakes flakes;
	private Rand rand;
	private static BufferedImage vignette = null;
	public Level(Main inst) {
		super(inst);
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
	
	public void silentTick() {
		if (getTimeSinceInit() == -1 || System.currentTimeMillis() - getTimeSinceInit() < 1000)
			return;
		
		for (int i = 0; i < scene.getSceneSize(); i++) {
			scene.get(i).hit(scene.getPlayer());
			scene.get(i).tick();
		}
		scene.tick();
	}

	public void silentInit() {
		if (isFullscreen())
			hideCursor();
		rand = new Rand(4);
		flakes = new Flakes(getMain(), rand, 30);
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
			scene.getPlayer().hit();
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

		for (int i = 0; i < signs.length; i++) {
			if (signs[i].doSigns(g, getMain())) {
				flagForMovable = false;
			}
		}
		if (showWorldMap)
			flagForMovable = false;
		scene.setPlayerMovable(flagForMovable);

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

	public abstract String getName();

}
