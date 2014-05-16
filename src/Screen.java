import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public abstract class Screen {
	private IMain inst;
	private boolean inited = false;
	private int mx = 0, my = 0;
	private BufferedImage buffer;
	private long timeSinceInit = -1;
	private static long counter = 0;
	private boolean consoleMode = false;
	private String consoleString = "";
	private float consoleFlash = 0;
	private String consoleDisplay = "";
	private long consoleDisplayTime = 0;
	
	public Screen(IMain inst) {
		this.inst = inst;
		buffer = new BufferedImage(inst.getWidth(), inst.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		if (wordsToColor == null) {
			wordsToColor = new Hashtable<String, Color>();
			wordsToColor.put("speed", Color.red);
			wordsToColor.put("gem", new Color(0, 100, 0));
			wordsToColor.put("vignette", Color.green);
			wordsToColor.put("save", Color.blue);
			wordsToColor.put("health", Color.magenta);
			wordsToColor.put("score", Color.orange);
			wordsToColor.put("lightning", Color.yellow.darker());
			wordsToColor.put("wireframe", Color.gray);
			wordsToColor.put("help", Color.pink);
			wordsToColor.put("teleport", Color.cyan);
			wordsToColor.put("garrote", new Color(85, 198, 130));
			wordsToColor.put("fog", new Color(91, 107, 117));
			wordsToColor.put("antialias", new Color(120, 150, 255));
			wordsToColor.put("modelmerger", new Color(45, 100, 200));
			wordsToColor.put("whoami", Color.magenta.darker());
			wordsToColor.put("time", new Color(122, 59, 155));
			wordsToColor.put("sound", new Color(34, 180, 60));
			wordsToColor.put("recorder", Color.red.darker().darker());
			final Color numerics = Color.red;
			wordsToColor.put("0", numerics);
			wordsToColor.put("1", numerics);
			wordsToColor.put("2", numerics);
			wordsToColor.put("3", numerics);
			wordsToColor.put("4", numerics);
			wordsToColor.put("5", numerics);
			wordsToColor.put("6", numerics);
			wordsToColor.put("7", numerics);
			wordsToColor.put("8", numerics);
			wordsToColor.put("9", numerics);
		}
	}

	public float lerp(float a0, float a1, float amount) {
		return a0 + (amount * (a1 - a0));
	}

	public boolean isActiveScreen() {
		return (getMain().getScreen(getMain().getActiveScreen()) == this);
	}

	public long getTimeSinceInit() {
		return timeSinceInit;
	}

	public void controllerUpdate(ControllerSupport controller) {

	}

	public void keyDown(int i) {

	}

	public void tick() {

	}

	public int lerp(int a0, int a1, float amount) {
		return (int) lerp((float) a0, (float) a1, amount);
	}

	public Color lerp(Color a0, Color a1, float amount) {
		int r = lerp(a0.getRed(), a1.getRed(), amount);
		int g = lerp(a0.getGreen(), a1.getGreen(), amount);
		int b = lerp(a0.getBlue(), a1.getBlue(), amount);
		return new Color(r, g, b);
	}

	public IMain getMain() {
		return inst;
	}

	public void internalInit() {
		inited = true;
		if (this instanceof Level) {
			((Level) this).silentInit();
		}
		init();
		timeSinceInit = System.currentTimeMillis();
	}

	public BufferedImage getCompatabilityBuffer() {
		return buffer;
	}

	public void refreshCompatabilityBuffer() {
		buffer = new BufferedImage(inst.getWidth(), inst.getHeight(),
				BufferedImage.TYPE_INT_RGB);
	}

	public boolean isInited() {
		return inited;
	}

	public abstract void init();

	public abstract void resize(int width, int height);

	public abstract void draw(Graphics g);

	public abstract void drawHUD(Graphics g);

	private String n = "screen" + counter++;

	public void setName(String n) {
		this.n = n;
	}

	public String getName() {
		return n;
	}

	public void setMouse(int x, int y) {
		if (isFullscreen()) {
			mx = x;
			my = y;
		} else {
			mx = x - 3;
			my = y - 25;
		}
	}

	public int getMouseX() {
		return mx;
	}

	public int getMouseY() {
		return my;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {
		if (arg0.getKeyChar() == '/') {
			consoleMode = !consoleMode;
			if (consoleMode)
				consoleDisplay("You've opened up the console!\nPress '/' to close.");
		} else if (consoleMode) {
			char c = arg0.getKeyChar();
			if (c == '\u0008' && consoleString.length() >= 1)
				consoleString = consoleString.substring(0,
						consoleString.length() - 1);
			else if (c == '\n' || c == '\r') {
				executeCommand(consoleString);
				consoleString = "";
			} else if (java.lang.Character.isLetterOrDigit(c) || c == ' '
					|| c == '.') {
				consoleString += arg0.getKeyChar();
			}
		}
	}

	public void consoleDisplay(String str) {
		// System.out.println(str.length());
		consoleDisplay = str;
		consoleDisplayTime = System.currentTimeMillis();
		messageTime = (long) (str.length() * 28.7f);
		if (consoleDisplay.indexOf("http:") > -1) {
			messageTime = messageTime + 10000;
		}
		if (messageTime < 8000)
			messageTime = 8000;
	}

	public boolean isInConsoleMode() {
		return consoleMode;
	}

	public void executeCommand(String cmd) {
		String noSpace = cmd.trim().toLowerCase();
		if (noSpace.indexOf(" ") > -1)
			noSpace.replace(" ", "");
		if (noSpace.equals("health"))
			consoleDisplay(GameState.instance.health + "");
		else if (noSpace.equals("score"))
			consoleDisplay(GameState.instance.score + "");
		else if (noSpace.equals("gem"))
			consoleDisplay(GameState.instance.gems + "");
		else if (noSpace.equals("garrote")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot garrote enemies in non debugging mode.");
				return;
			}
			if (this instanceof Level) {
				Level l = (Level) this;
				if (l.getScene() != null) {
					ArrayList<Enemy> ens = l.getScene()
							.getObjectsByTypeAndParented(Enemy.class);
					for (int e = 0; e < ens.size(); e++) {
						ens.get(e).kill();
					}
					consoleDisplay("Garroted " + ens.size() + " enemies.");
				} else
					consoleDisplay("There can't be enemies here.");
			} else
				consoleDisplay("Currently not in a level.");
		} else if (noSpace.equals("save")) {
			GameState.save();
			consoleDisplay("Game saved.");
		} else if (noSpace.equals("speed")) {
			if (this instanceof Level) {
				Level l = (Level) this;
				if (l.getScene() != null)
					consoleDisplay(l.getScene().getPlayerSpeed() + "");
			}
		} else if (noSpace.startsWith("teleport")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot teleport in non debugging mode.");
				return;
			}
			String hs = noSpace.replace("teleport", "");
			String telNames = "Try: \"teleport <name of location>\" (no quotes)\nThe following is accepted:\nholm, fiace, sailor, harbour, harbor, caden, sea, banicia, cave";
			try {
				if (hs.indexOf("holm") > -1) {
					if (this instanceof HolmVillage)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("vbm")) {
							getMain().setActiveScreen("vbm");
						} else {
							getMain().addScreen(new HolmVillage(getMain()));
							getMain().setActiveScreen("vbm");
						}
					}
				} else if (hs.indexOf("fiace") > -1) {
					if (this instanceof FiaceForest)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("level")) {
							getMain().setActiveScreen("level");
						} else {
							getMain().addScreen(new FiaceForest(getMain()));
							getMain().setActiveScreen("level");
						}
					}
				} else if (hs.indexOf("sailor") > -1
						|| hs.indexOf("harbour") > -1
						|| hs.indexOf("harbor") > -1) {
					if (this instanceof SailorHarbour)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("yLENIN")) {
							getMain().setActiveScreen("yLENIN");
						} else {
							getMain().addScreen(new SailorHarbour(getMain()));
							getMain().setActiveScreen("yLENIN");
						}
					}
				} else if (hs.indexOf("caden") > -1 || hs.indexOf("sea") > -1) {
					if (this instanceof CadenSea)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("lilo")) {
							getMain().setActiveScreen("lilo");
						} else {
							getMain().addScreen(new CadenSea(getMain()));
							getMain().setActiveScreen("lilo");
						}
					}
				} else if (hs.indexOf("banicia") > -1 || hs.indexOf("cave") > -1) {
					if (this instanceof Banicia)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("tumalarda")) {
							getMain().setActiveScreen("tumalarda");
						} else {
							getMain().addScreen(new Banicia(getMain()));
							getMain().setActiveScreen("tumalarda");
						}
					}
				} else {
					consoleDisplay(telNames);
				}
			} catch (Throwable t) {
				consoleDisplay(telNames);
			}
		} else if (noSpace.startsWith("health")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot set health in non debugging mode.");
				return;
			}
			String hs = noSpace.replace("health", "");
			try {
				float h = Float.parseFloat(hs);
				consoleDisplay("Set health to: " + h);
				GameState.instance.health = h;
			} catch (Throwable t) {
				consoleDisplay("Try: \"health <floating point number>\" (no quotes)");
			}
		} else if (noSpace.startsWith("solver")) {
			consoleDisplay("Under construction.");
		} else if (noSpace.startsWith("score")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot set score in non debugging mode.");
				return;
			}
			String hs = noSpace.replace("score", "");
			try {
				int h = (int) Float.parseFloat(hs);
				consoleDisplay("Set score to: " + h);
				GameState.instance.score = h;
			} catch (Throwable t) {
				consoleDisplay("Try: \"score <integer>\" (no quotes)");
			}
		} else if (noSpace.startsWith("speed")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot set speed in non debugging mode.");
				return;
			}
			String hs = noSpace.replace("speed", "");
			try {
				float h = Float.parseFloat(hs);
				consoleDisplay("Set player speed to: " + h);
				if (this instanceof Level) {
					Level l = (Level) this;
					if (l.getScene() != null)
						l.getScene().setPlayerSpeed(h);
				}
				// GameState.instance.score = h;
			} catch (Throwable t) {
				consoleDisplay("Try: \"speed <floating point number>\" (no quotes)");
			}
		} else if (noSpace.startsWith("gem")) {
			if (!GameState.DEBUGMODE) {
				consoleDisplay("You cannot set gems in non debugging mode.");
				return;
			}
			String hs = noSpace.replace("gem", "");
			try {
				int h = (int) Float.parseFloat(hs);
				consoleDisplay("Set number of gems to: " + h);
				GameState.instance.gems = h;
			} catch (Throwable t) {
				consoleDisplay("Try: \"gem <integer>\" (no quotes)");
			}
		} else if (noSpace.equals("messages")) {
			String appender = "";
			for (int i = 0; i < Level.messages.size(); i++) {
				String msg = Level.messages.get(i).getMessage();
				String nam = Level.messages.get(i).getName();
				appender += nam + ": " + msg + "\n";
			}
			if (appender.length() == 0)
				consoleDisplay("No messages in storage.");
			else
				consoleDisplay(appender);
		} else if (noSpace.equals("vignette")) {
			GameState.doVignette = !GameState.doVignette;
			if (GameState.doVignette)
				consoleDisplay("Vignette turned on.");
			else
				consoleDisplay("Vignette turned off.");
		} else if (noSpace.equals("recorder")) {
			if (MainApplet.isApplet) {
				consoleDisplay("Screen recorder doesn't work in Applet Mode.");
			}
			else if (System.getProperty("os.name", "generic").toLowerCase().indexOf("win") == -1) {
				consoleDisplay("Screen recorder is only capable of running on Windows currently.");
			} else {
			Main.screenRecorder = !Main.screenRecorder;
			if (Main.screenRecorder) {
				consoleDisplay("Screen recorder turned on.");
				FPSUtil.screenRecorderStart();
			}
			else {
				FPSUtil.screenRecorderEnd();
				try {
					// create the .bat file for compiling.
					File builder = new File(Main.screenRecorderPath + "build.bat");
					builder.createNewFile();
					FileWriter writer = new FileWriter(builder);
					int fps = ((int)(FPSUtil.getAverageFPS()));
					String outputName = "output.wmv";
					writer.write("ffmpeg -r " + fps + " -f image2 -i img%%05d.png -vcodec mpeg4 -qscale 1 -b 20000 -r 30 " + outputName + "\n\r");
					writer.write("move /Y " + outputName + " ..\n\r");
					writer.write("set startdir=%cd%\n\r");
					writer.write("cd ..\n\r");
					writer.write("del /q %startdir%\n\r");
					writer.write("rmdir /q %startdir%\n\r");
					writer.close();
				}
				catch (Throwable t) {
					consoleDisplay("Fatal error creating bat file!");
				}
				finally {
					String bits = "32";
					if (System.getProperty("sun.arch.data.model") != null && System.getProperty("sun.arch.data.model").equals("64"))
						bits = "64";
					consoleDisplay("Screen recorder turned off.\nData recorded to:\n" + new File(Main.screenRecorderPath).getAbsolutePath() + "\nUse build.bat within folder to build movie, after copying ffmpeg.exe\nto the directory.\nIt can be downloaded at http://ffmpeg.zeranoe.com/builds/ look for \n\"Download FFmpeg git-XXXXXXX " + bits + "-bit Static\".");
				}
			}
			}
		} else if (noSpace.equals("fog")) {
			if (this instanceof Level) {
				Level lev = (Level) this;
				if (lev.getScene() != null) {
					lev.getScene().setFogState(!lev.getScene().isFogEnabled());
					if (lev.getScene().isFogEnabled())
						consoleDisplay("Fog turned on.");
					else
						consoleDisplay("Fog turned off.");
				}
			}
		} else if (noSpace.equals("lightning")) {
			if (this instanceof Level) {
				Level lev = (Level) this;
				if (lev.getScene() != null) {
					lev.getScene().makeLightning();
				}
			}
		} else if (noSpace.equals("modelmerger")) {
			if (!MainApplet.isApplet) {
				if (!JVMCodeReloader.reloadCode(ComponentMover.class))
					System.err.println("Failed to reload component mover. Model merger's window mover may have issues.");
				new ModelMerger();
			}
		} else if (noSpace.equals("antialias")) {
			Main.antialias = !Main.antialias;
			if (Main.antialias) {
				consoleDisplay("Antialiasing turned on.");
			} else
				consoleDisplay("Antialiasing turned off.");
		} else if (noSpace.equals("wireframe")) {
			if (this instanceof Level) {
				Level lev = (Level) this;
				if (lev.getScene() != null) {
					lev.getScene()
							.useThisMethodSparsingly()
							.setUseWireframeWithShading(
									!lev.getScene().useThisMethodSparsingly()
											.getUseWireframeWithShader());
					if (lev.getScene().useThisMethodSparsingly()
							.getUseWireframeWithShader())
						consoleDisplay("Wireframe turned on.");
					else
						consoleDisplay("Wireframe turned off.");
				}
			}
		} else if (noSpace.equals("whoami")) {
			consoleDisplay("You are " + GameState.instance.playerGUID + ".");
		} else if (noSpace.equals("time")) {
			int seconds = (int) (GameState.instance.timePlayed / 1000) % 60;
			int minutes = (int) ((GameState.instance.timePlayed / 60000) % 60);
			int hours = (int) ((GameState.instance.timePlayed / 3600000) % 24);
			consoleDisplay("You have played for (updated every 10 seconds):\n"
					+ hours + " hour(s) " + minutes + " minute(s) " + seconds
					+ " second(s)");
		} else if (noSpace.equals("sound")) {
			if (SoundManager.soundEnabled) {
				if (SoundManager.backgroundSound != null) {
					SoundManager.backgroundSound.halt();
				}
				SoundManager.soundEnabled = false;
			}
			else {
				SoundManager.soundEnabled = true;
				Utility.doSound(this);
			}
			if (SoundManager.soundEnabled)
				consoleDisplay("Sound engine started!");
			else
				consoleDisplay("Sound engine halted.");
		} else if (noSpace.equals("help")) {
			String additive = "";
			if (!GameState.DEBUGMODE)
				additive = "\nNOTICE: You are not in debug mode, which disables some console features.";
			consoleDisplay("-Aeda Adventure Console-"
					+ additive
					+ "\nhealth - gets or sets health.\ngem - gets or sets number of gems.\nscore - gets or sets score.\nspeed - gets or sets player speed.\nvignette - enables or disables vignette.\nhelp - displays this message.\nmessages - displays any active messages.\nsave - force saves the game.\nteleport - teleports you to a location.\ngarrote - kills all enemies.\nfog - toggles fog on or off.\nlightning - makes lightning.\nwireframe - toggles wireframe moded on or off.\nantialias - turns on or off antialiasing\nmodelmerger - Opens the model converting utility.\nwhoami - determines player name.\ntime - indicates how long the game has been played.\nsound - enables/disables sound.\nrecorder - enables/disables the screen recorder.");
		} else
			consoleDisplay("Sorry, I didn't understand \""
					+ cmd
					+ "\".\nType 'help' (no quotes) for help, or press '/' to exit the console.");
	}

	private static Hashtable<String, Color> wordsToColor;
	private long messageTime = 8000;

	public void drawConsole(Graphics g) {
		if (consoleMode) {
			consoleFlash += 0.05f;
			int y = getMain().getHeight();
			if (MainApplet.isApplet)
				y = getMain().getHeight() - 20;
			else if (!isFullscreen())
				y = y - 46;
			else
				y = y - 20;
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, y, getMain().getWidth(), 20);
			g.setColor(Color.white);
			g.setFont(new Font("Courier New", 0, 12));// stroke characters: 7x12
			AttributedString as1 = new AttributedString(consoleString);
			if (consoleString.length() > 0)
				as1.addAttribute(TextAttribute.FONT, g.getFont());
			/*
			 * if (consoleString.indexOf("dump") > -1) {
			 * as1.addAttribute(TextAttribute.FOREGROUND, Color.green,
			 * consoleString.indexOf("dump"),consoleString.indexOf("dump")+4); }
			 */
			Set<String> keys = wordsToColor.keySet();
			String[] ars = new String[keys.size()];
			ars = keys.toArray(ars);
			for (int i = 0; i < ars.length; i++) {
				String key = ars[i];
				Color val = wordsToColor.get(key);
				for (int index = consoleString.indexOf(key); index >= 0; index = consoleString
						.indexOf(key, index + 1)) {
					try {
						as1.addAttribute(TextAttribute.FOREGROUND, val, index,
								index + key.length());
					} catch (Throwable t) {

					}
				}
			}
			g.drawString(as1.getIterator(), 0, y + 14);
			if ((int) consoleFlash % 2 == 0) {
				int len = g.getFontMetrics().stringWidth(consoleString);
				g.drawLine(len, y + 14, len + 7, y + 14);
			}
			long disTime = System.currentTimeMillis() - consoleDisplayTime;
			if (consoleDisplay != null && disTime < messageTime) {
				String[] trs = consoleDisplay.split("\n");
				for (int i = 0; i < trs.length; i++) {
					int sn = (14 * (trs.length - 1)) - (i * 14);
					String sa = trs[i];
					int l = g.getFontMetrics().stringWidth(sa);
					int alp = 200;
					int al2 = 255;
					if (disTime > (messageTime - 2000)) {
						alp = 200 - (int) (((disTime - (messageTime - 2000)) * 200) / 2000);
						al2 = 255 - (int) (((disTime - (messageTime - 2000)) * 255) / 2000);
					}
					g.setColor(new Color(255, 255, 0, alp));
					g.fillRect(0, y - 14 - sn, l, 14);
					g.setColor(new Color(0, 0, 0, al2));
					g.drawString(sa, 0, y - 2 - sn);
				}
			}
			// g.drawLine(consolePos)
		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent arg0) {

	}

	public void mouseDragged(MouseEvent arg0) {

	}

	public int inButton = 0;

	public Object[] fitStringToRect(Graphics g, String str, String fontName,
			int width, int height) {
		boolean fits = true;
		int size = 1;
		int fixWidth = width;
		int fixHeight = height;
		Font sample = new Font(fontName, 0, size);
		while (fits) {
			size++;
			sample = new Font(fontName, 0, size);
			FontMetrics m = g.getFontMetrics(sample);
			fixWidth = m.stringWidth(str);
			fixHeight = m.getMaxAscent();
			if (fixWidth > width || fixHeight > height - 20)
				fits = false;
		}
		if (size > 4)
			size = size - 3;
		sample = new Font(fontName, 0, size);
		fixWidth = g.getFontMetrics(sample).stringWidth(str);
		return new Object[] { sample, fixWidth, (int) (fixHeight * 0.8f) };
	}

	public void drawButton(Graphics g, String s, int x, int y, int width,
			int height) {
		Font original = g.getFont();
		Color topRect = new Color(125, 93, 59);
		Color bottomRect = new Color(100, 76, 49);
		Color border = new Color(84, 56, 30);
		Color innerBorder = new Color(166, 130, 126);
		if (getMouseX() <= x + width && getMouseX() >= x
				&& getMouseY() <= y + height && getMouseY() >= y) {
			topRect = new Color(115, 83, 49);
			bottomRect = new Color(90, 66, 39);
			border = new Color(74, 46, 20);
			innerBorder = new Color(156, 120, 116);
			inButton = y;
		}
		for (int i = 0; i < height; i++) {
			g.setColor(lerp(topRect, bottomRect, ((float) i) / height));
			if (i < 4)
				g.drawLine(x + (4 - i), y + i, x + width - (4 - i), y + i);
			else if (i > height - 4)
				g.drawLine(x + 4 - (height - i), y + i, x + width
						- (4 - (height - i)), y + i);
			else
				g.drawLine(x, y + i, x + width, y + i);
		}
		g.setColor(innerBorder);
		g.drawRoundRect(x, y + 1, width, height - 1, 8, 8);
		g.setColor(border);
		g.drawRoundRect(x, y, width, height, 8, 8);

		g.setColor(Color.white);
		Object[] t = fitStringToRect(g, s, "Arial", width, height);
		g.setFont((Font) t[0]);
		int whalf = (width - (int) t[1]) / 2;
		int hhalf = (height + (int) t[2]) / 2;
		g.drawString(s, x + 5 + whalf, y + hhalf);
		g.setFont(original);
	}

	public void hideCursor() {
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "blank");
		if (getMain() instanceof Main)
			((Main) getMain()).getContentPane().setCursor(blank);
	}

	public boolean isFullscreen() {
		if (getMain() instanceof Main)
			return ((Main) getMain()).isFullscreen();
		else
			return false;
	}

	public void drawCursor(Graphics g) {
		g.setColor(Color.black);
		g.setXORMode(Color.white);
		Polygon polygon = new Polygon();
		polygon.addPoint(getMouseX(), getMouseY());
		polygon.addPoint(getMouseX(), getMouseY() + 13);
		polygon.addPoint(getMouseX() + 2, getMouseY() + 11);
		polygon.addPoint(getMouseX() + 5, getMouseY() + 15);
		polygon.addPoint(getMouseX() + 7, getMouseY() + 14);
		polygon.addPoint(getMouseX() + 4, getMouseY() + 10);
		polygon.addPoint(getMouseX() + 6, getMouseY() + 8);
		polygon.addPoint(getMouseX(), getMouseY());
		g.fillPolygon(polygon);
		g.setPaintMode();
		g.drawPolygon(polygon);
	}
}
