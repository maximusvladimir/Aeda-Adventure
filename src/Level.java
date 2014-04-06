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
	
	public void reloadedLevel() {
		gameHalt = false;
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
		if (messages != null && activeMessageIndex != -1) {
			messages.get(activeMessageIndex).physicalTick();
		}
		
		if (getScene() == null)
			return;
		getScene().getGamePlane().allowFall();
		getScene().setPlayerY(getScene().getGamePlane().getHeight());
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
		
		if (showFlakes)
			flakes.tick();
	}
	private float decrement = 0.001f;
	public void postSilentTick() {
		//getScene().getPlayer().antiHit();
		if (Main.blurAmount > 0) {
			Main.blurAmount -= decrement;
			decrement += 0.001f;
			if (decrement > 0.05)
				decrement = 0.05f;
		}
		else {
			Main.blurAmount = 0;
			decrement = 0.001f;
		}
	}
	
	public void addMessage(String message, String tagName) {
		addMessage(message,tagName,null);
	}
	
	public void addMessage(String message, String tagName, boolean optionMessage) {
		addMessage(message,tagName,optionMessage,true,null);
	}
	
	public void addMessage(String message, String tagName, ActionListener listener) {
		addMessage(message,tagName,false,true,listener);
	}
	
	public void addMessage(String message, String tagName, boolean optionMessage, ActionListener listener) {
		addMessage(message,tagName,optionMessage,true,listener);
	}
	
	public void addMessage(String message, String tagName, boolean optionMessage, boolean removeAtFinish ,ActionListener exitEvent) {
		Message mess = new Message(tagName.toLowerCase(),this);
		mess.setMessage(message);
		mess.setCloseEvent(exitEvent);
		mess.setOptionMessage(optionMessage);
		mess.setRemoveAtFinish(removeAtFinish);
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
		if (tagName == null) {
			activeMessageIndex = -1;
			return;
		}
		if (activeMessageIndex != -1)
			return;
		tagName = tagName.toLowerCase();
		for (int i = 0; i < messages.size(); i++) {
			Message contract = messages.get(i);
			if (tagName.equals(contract.getName())) {
				activeMessageIndex = i;
				if (getScene() != null)
					getScene().setPlayerMovable(false);
				break;
			}
		}
	}
	
	public static ArrayList<Message> messages = new ArrayList<Message>();

	long blinkTime = -1;
	long lastBlink = 0;
	float sf = 0.0f;

	public void silentInit() {
		if (isFullscreen())
			hideCursor();
		rand = new Rand(4);
		flakes = new Flakes(getMain(), rand, 25);
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
		if (getScene() == null)
			return;
		if (!getScene().canPlayerMove() || showWorldMap)
			return;
		if (code == KeyEvent.VK_W) {
			scene.movePlayer(true);
		} else if (code == KeyEvent.VK_S) {
			scene.movePlayer(false);
		} else if (code == KeyEvent.VK_A) {
			playerDelta = playerDelta + 0.1f;
		} else if (code == KeyEvent.VK_D) {
			playerDelta = playerDelta - 0.1f;
		} else if (code == KeyEvent.VK_SPACE) {
			scene.getPlayer().jump();
		} else if (code == KeyEvent.VK_G) {
			transition = 1;
		}
		GameState.instance.playerDelta = playerDelta;
		scene.setPlayerDelta(playerDelta);
	}
	
	public int getMessageIndexer() {
		return activeMessageIndex;
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
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.save();
			getMain().setActiveScreen("mainmenu");
			return;
		}
		if (ke.getKeyCode() == KeyEvent.VK_Q) {
			if (activeMessageIndex != -1) {
				Message message = messages.get(activeMessageIndex);
				if (message.removingAtFinish())
					messages.remove(activeMessageIndex);
				activeMessageIndex = -1;
				if (getScene() != null)
					getScene().setPlayerMovable(true);
				message.doCloseEvent();
			}
			if (signs != null)
				for (int i = 0; i < signs.length; i++) {
					signs[i].qPressed();
				}
			return;
		}
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			if (scene != null) {
				scene.getPlayer().hit();
				scene.getPlayer().setHitNow();
			}
		}
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			if (activeMessageIndex != -1) {
				messages.get(activeMessageIndex).toggleOption();
			}
		}
		if (activeMessageIndex != -1)
			return;
		if ((ke.getKeyCode() == KeyEvent.VK_W
				|| ke.getKeyCode() == KeyEvent.VK_S) && getScene() != null) {
			scene.getPlayer().moving = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_Z && GameState.instance.hasRaft) {
			if (!(this instanceof SailorHarbour)) {
				addMessage("You can't use a raft here.","NOUSERAFT");
				setActiveMessage("NOUSERAFT");
			}
			else {
				// Really bad OOP here:
				((SailorHarbour)this).startRaftMode();
			}
		}
		if (ke.getKeyCode() == KeyEvent.VK_M && getScene() != null) {
			showWorldMap = !showWorldMap;
			if (showWorldMap) {
				scene.getPlayer().moving = false;
				scene.setPlayerMovable(false);
			} else {
				scene.setPlayerMovable(true);
			}
		}
		if (ke.getKeyCode() == KeyEvent.VK_T) {
			Main.blurAmount = 20;
		}
		/*if (ke.getKeyCode() == KeyEvent.VK_SHIFT && getScene() != null) {
			scene.setPlayerSpeed(19);
		}*/
	}

	public abstract void draw(Graphics g);

	public void startTransition(Screen s,P3D newPlayerLoc,float playerDelta) {
		transition = 1;
		nextPlayerLoc = newPlayerLoc;
		nextScreen = s;
		nextDelta = playerDelta;
		gameHalt = true;
		GameState.ORIGINS = new P3D(getScene().getPosition());
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
		if (showFlakes)
			flakes.draw(g);
		if (GameState.doVignette)
			g.drawImage(vignette, 0, 0, null);
		if (!(this instanceof InsideHouse) && !(this instanceof Shop)) {
			Utility.drawMap(g, getMain(), scene);
			
			if (GameState.instance.hasRaft) {
				int x092 = getMain().getWidth() - (int) (getMain().getWidth() * 0.14f) - 8;
				int y558 = (int) (getMain().getWidth() * 0.17f);
				g.drawImage(Shop.getRaftImage(), x092,y558,null);
			}
		}
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
		if (scene != null)
			scene.setPlayerMovable(flagForMovable);

		Utility.drawEnemyData(g, getMain(), getScene());
		Utility.drawHealth(g);

		if (showWorldMap && !(this instanceof InsideHouse) && !(this instanceof Shop)) {
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
			if (scene != null) {
			scene.getPlayer().moving = false;
			scene.setPlayerMovable(false);
			g.setColor(Utility.adjustAlpha(getScene().getFogColor(), 
					MathCalculator.colorLock(255 - (int) (255 * transition))));
			//g.setColor(new Color(10, 30, 10, MathCalculator
				//	.colorLock(255 - (int) (255 * transition))));
			g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
			}
			else {
				g.setColor(Utility.adjustAlpha(Color.black, 
						MathCalculator.colorLock(255 - (int) (255 * transition))));
				g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());

			}
			if (transition <= 0.00) {
				transition = -10;
				if (nextScreen != null) {
					boolean firstLoad = false;
					if (!getMain().screenExists(nextScreen.getName())) {
						getMain().addScreen(nextScreen);
						firstLoad = true;
					}
					getMain().setActiveScreen(nextScreen.getName());
					if (getScene() != null && !getScene().isMoveOverriden())
						GameState.instance.playerLocation = nextPlayerLoc;
					if (nextScreen instanceof Level && ((Level)nextScreen).getScene() != null) {
						Level lev = (Level)nextScreen;
						lev.getScene().setPlayerPosition(nextPlayerLoc);
						GameState.instance.playerDelta = nextDelta;
						lev.getScene().setPlayerDelta(nextDelta);
						if (!firstLoad)
							lev.reloadedLevel();
					}
					gameHalt = false;
					//getScene().setPlayerPosition(nextPlayerLoc);
				}
			}
		}
	}
	
	public boolean isMessageBeingShown() {
		return activeMessageIndex != -1;
	}

}
