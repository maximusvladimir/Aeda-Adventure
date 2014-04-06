import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
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
	
	public Level(IMain screen) {
		super(screen);
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

	private boolean stTickLoad = false;
	public void silentTick() {
		if (getTimeSinceInit() == -1
				|| System.currentTimeMillis() - getTimeSinceInit() < 500) {
			getScene().setPlayerMovable(false);
			return;
		} else {
			if (!stTickLoad) {
				stTickLoad = true;
				getScene().setPlayerMovable(true);
			}
		}

		getScene().getPlayer().tick();

		for (int i = 0; i < scene.getSceneSize(); i++) {
			try {
				scene.get(i).hit(scene.getPlayer());
				scene.get(i).tick();
			} catch (Throwable t) {
				// Ocassionally the other thread removes the object in transit.
			}
		}
		scene.tick();
		sf += 0.01f;
		if (blinkTime != -1) {
			if (System.currentTimeMillis() - blinkTime > 260) {
				blinkTime = -1;
				scene.getPlayer().setEyesOpen(true);
				lastBlink = System.currentTimeMillis() + rand.nextInt(0, 2500);
			}
		} else if (System.currentTimeMillis() - lastBlink > 4000) {
			scene.getPlayer().setEyesOpen(false);
			blinkTime = System.currentTimeMillis();
		}
		ArrayList<Lamppost> lamps = scene.getObjectsByType(Lamppost.class);
		int darkBuilder = 0;
		final float mul5 = (getScene().getSceneDarkness()*0.3333333f)+20;
		final int mul6 = (getScene().getSceneDarkness()) + 120;
		for (int i = 0; i < lamps.size(); i++) {
			Lamppost lamp = lamps.get(i);
			if (!lamp.isLampOn())
				continue;
			float dist = lamp.getDistToPlayer();
			if (dist < 700) {
				float ams = (float) (Math.sin(lamp.getWindDelta()) * 40) + 100;//was 25, 65
				darkBuilder += -(ams - (int) (dist / 700 * ams));
			}
		}
		scene.getPlayer().setIndividualDarkness(darkBuilder);
	}
	
	public void addMessage(String message, String tagName) {
		addMessage(message,tagName,null);
	}
	
	public void addMessage(String message, String tagName, boolean optionMessage) {
		addMessage(message,tagName,optionMessage,null);
	}
	
	public void addMessage(String message, String tagName, ActionListener listener) {
		addMessage(message,tagName,false,listener);
	}
	
	public void addMessage(String message, String tagName, boolean optionMessage, ActionListener exitEvent) {
		Message mess = new Message(tagName.toLowerCase(),this);
		mess.setMessage(message);
		mess.setCloseEvent(exitEvent);
		mess.setOptionMessage(optionMessage);
		for (int i = 0; i < messages.size(); i++) {
			if (messages.get(i).getName().equals(mess.getName())) {
				System.err.println("Message already added!");
				return;
			}
		}
		messages.add(mess);
	}
	
	private int activeMessageIndex = -1;
	public void setActiveMessage(String tagName) {
		if (activeMessageIndex != -1)
			return;
		tagName = tagName.toLowerCase();
		for (int i = 0; i < messages.size(); i++) {
			Message contract = messages.get(i);
			if (tagName.equals(contract.getName())) {
				activeMessageIndex = i;
				getScene().setPlayerMovable(false);
				break;
			}
		}
	}
	
	private ArrayList<Message> messages = new ArrayList<Message>();

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
		if (getMain() instanceof MainApplet)
			return;
		if (isFullscreen())
			hideCursor();
		else
			((Main)getMain()).getContentPane().setCursor(Cursor.getDefaultCursor());
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
			scene.useThisMethodSparsingly().setUseWireframeWithShading(
					!scene.useThisMethodSparsingly()
							.getUseWireframeWithShader());
		} else if (code == KeyEvent.VK_G) {
			transition = 1;
		} else if (code == KeyEvent.VK_L) {
			scene.makeLightning();
		}
		if (code == KeyEvent.VK_SHIFT) {
			scene.setPlayerSpeed(50);
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
			if (activeMessageIndex != -1) {
				Message message = messages.get(activeMessageIndex);
				messages.remove(activeMessageIndex);
				activeMessageIndex = -1;
				getScene().setPlayerMovable(true);
				message.doCloseEvent();
			}
			if (signs != null)
				for (int i = 0; i < signs.length; i++) {
					signs[i].qPressed();
				}
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
			} else {
				scene.setPlayerMovable(true);
			}
		}
		if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
			scene.setPlayerSpeed(19);
		}
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			if (activeMessageIndex != -1) {
				messages.get(activeMessageIndex).toggleOption();
			}
		}
	}

	public abstract void draw(Graphics g);

	public void startTransition(Screen s,P3D newPlayerLoc,float playerDelta) {
		transition = 1;
		nextPlayerLoc = newPlayerLoc;
		nextScreen = s;
		nextDelta = playerDelta;
		gameHalt = true;
	}

	private P3D nextPlayerLoc;
	private Screen nextScreen;
	private float nextDelta;
	private float transition;
	private boolean showWorldMap = false;
	private boolean gameHalt = false;
	
	public boolean isGameHalted() {
		return gameHalt;
	}

	public void drawHUD(Graphics g) {
		flakes.draw(g);
		Utility.drawMap(g, getMain(), scene);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		boolean flagForMovable = true;

		if (signs != null)
			for (int i = 0; i < signs.length; i++) {
				if (signs[i].doSigns(g, getMain())) {
					flagForMovable = false;
				}
			}
		if (showWorldMap)
			flagForMovable = false;
		if (activeMessageIndex != -1)
			flagForMovable = false;
		scene.setPlayerMovable(flagForMovable);

		Utility.drawEnemyData(g, getMain(), getScene());
		Utility.drawHealth(g);
		if (GameState.doVignette)
			g.drawImage(vignette, 0, 0, null);

		if (showWorldMap) {
			Utility.drawWorldMap(g, getMain(), scene);
		}

		g.setFont(new Font("Arial", 0, 12));
		g.setColor(Color.white);
		g.drawString("FPS:" + getMain().getFPS(), 0, 10);
		if (getMain().isFullscreen()) {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0,
					getMain().getHeight() - 3);
		} else {
			g.drawString("Draw time: " + getMain().getDrawTime() + " ms", 0,
					getMain().getHeight() - 30);
		}
		
		if (activeMessageIndex != -1) {
			Message message = messages.get(activeMessageIndex);
			message.tick(g, getMain());
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
			g.setColor(Utility.adjustAlpha(getScene().getFogColor(), 
					MathCalculator.colorLock(255 - (int) (255 * transition))));
			//g.setColor(new Color(10, 30, 10, MathCalculator
				//	.colorLock(255 - (int) (255 * transition))));
			g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
			if (transition <= 0.00) {
				transition = -10;
				if (nextScreen != null) {
					if (!getMain().screenExists(nextScreen.getName()))
						getMain().addScreen(nextScreen);
					getMain().setActiveScreen(nextScreen.getName());
					if (!getScene().isMoveOverriden())
						GameState.instance.playerLocation = nextPlayerLoc;
					if (nextScreen instanceof Level) {
						Level lev = (Level)nextScreen;
						lev.getScene().setPlayerPosition(nextPlayerLoc);
						GameState.instance.playerDelta = nextDelta;
						lev.getScene().setPlayerDelta(nextDelta);
						gameHalt = false;
					}
					//getScene().setPlayerPosition(nextPlayerLoc);
				}
			}
		}
	}
	
	public boolean isMessageBeingShown() {
		return activeMessageIndex != -1;
	}

	public abstract String getName();

}
