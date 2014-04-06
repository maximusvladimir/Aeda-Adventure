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
		buffer = new BufferedImage(inst.getWidth(),inst.getHeight(),BufferedImage.TYPE_INT_RGB);
		if (wordsToColor == null) {
			wordsToColor = new Hashtable<String,Color>();
			wordsToColor.put("speed", Color.red);
			wordsToColor.put("gem", new Color(0,100,0));
			wordsToColor.put("vignette",Color.green);
			wordsToColor.put("save", Color.blue);
			wordsToColor.put("health", Color.magenta);
			wordsToColor.put("score", Color.orange);
			wordsToColor.put("lighting", Color.yellow);
			wordsToColor.put("wireframe", Color.gray);
			wordsToColor.put("help",Color.pink);
			wordsToColor.put("teleport",Color.cyan);
			wordsToColor.put("garrote",new Color(85,198,130));
			final Color numerics = Color.red;
			wordsToColor.put("0",numerics);
			wordsToColor.put("1",numerics);
			wordsToColor.put("2",numerics);
			wordsToColor.put("3",numerics);
			wordsToColor.put("4",numerics);
			wordsToColor.put("5",numerics);
			wordsToColor.put("6",numerics);
			wordsToColor.put("7",numerics);
			wordsToColor.put("8",numerics);
			wordsToColor.put("9",numerics);
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
		return (int)lerp((float)a0,(float)a1,amount);
	}
	
	public Color lerp(Color a0, Color a1, float amount) {
		int r = lerp(a0.getRed(),a1.getRed(), amount);
		int g = lerp(a0.getGreen(),a1.getGreen(),amount);
		int b = lerp(a0.getBlue(),a1.getBlue(),amount);
		return new Color(r,g,b);
	}
	
	public IMain getMain() {
		return inst;
	}
	
	public void internalInit() {
		inited = true;
		if (this instanceof Level) {
			((Level)this).silentInit();
		}
		init();
		timeSinceInit = System.currentTimeMillis();
	}
	
	public BufferedImage getCompatabilityBuffer() {
		return buffer;
	}
	
	public void refreshCompatabilityBuffer() {
		buffer = new BufferedImage(inst.getWidth(),inst.getHeight(),BufferedImage.TYPE_INT_RGB);
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
		}
		else {
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
		}
		else if (consoleMode) {
			char c = arg0.getKeyChar();
			if (c == '\u0008' && consoleString.length() >= 1)
				consoleString = consoleString.substring(0,consoleString.length() - 1);
			else if (c == '\n' || c == '\r') {
				executeCommand(consoleString);
				consoleString = "";
			}
			else if (java.lang.Character.isLetterOrDigit(c) || c == ' ' || c == '.') {	
				consoleString += arg0.getKeyChar();
			}
		}
	}
	
	public void consoleDisplay(String str) {
		consoleDisplay = str;
		consoleDisplayTime = System.currentTimeMillis();
	}
	
	public boolean isInConsoleMode() {
		return consoleMode;
	}
	
	public void executeCommand(String cmd) {
		String noSpace = cmd.trim().toLowerCase();
		if (noSpace.indexOf(" ") > -1)
			noSpace.replace(" ", "");
		if (noSpace.equals("health"))
			consoleDisplay(GameState.instance.health+"");
		else if (noSpace.equals("score"))
			consoleDisplay(GameState.instance.score+"");
		else if (noSpace.equals("gem"))
			consoleDisplay(GameState.instance.gems+"");
		else if (noSpace.equals("garrote")) {
			if (this instanceof Level) {
				Level l = (Level)this;
				if (l.getScene() != null) {
					ArrayList<Enemy> ens = l.getScene().getObjectsByType(Enemy.class);
					for (int e = 0; e < ens.size(); e++) {
						ens.get(e).kill();
					}
					consoleDisplay("Garroted " + ens.size() + " enemies.");
				}
				else
					consoleDisplay("There can't be enemies here.");
			}
			else
				consoleDisplay("Currently not in a level.");
		}
		else if (noSpace.equals("save")) {
			GameState.save();
			consoleDisplay("Game saved.");
		} else if (noSpace.equals("speed")) {
			if (this instanceof Level) {
				Level l = (Level)this;
				if (l.getScene() != null)
					consoleDisplay(l.getScene().getPlayerSpeed()+"");
			}
		}
		else if (noSpace.startsWith("teleport")) {
			String hs = noSpace.replace("teleport", "");
			try {
				if (hs.indexOf("holm") > -1) {
					if (this instanceof HolmVillage)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("vbm")) {
							getMain().setActiveScreen("vbm");
						}
						else {
							getMain().addScreen(new HolmVillage(getMain()));
							getMain().setActiveScreen("vbm");
						}
					}
				}
				else if (hs.indexOf("fiace") > -1) {
					if (this instanceof FiaceForest)
						consoleDisplay("You are already there/here.");
					else {
						if (getMain().screenExists("level")) {
							getMain().setActiveScreen("level");
						}
						else {
							getMain().addScreen(new FiaceForest(getMain()));
							getMain().setActiveScreen("level");
						}
					}
				}
				else {
					consoleDisplay("Try: \"teleport <name of location>\" (no quotes)");
				}
			}
			catch (Throwable t) {
				consoleDisplay("Try: \"teleport <name of location>\" (no quotes)");
			}
		}
		else if (noSpace.startsWith("health")) {
			String hs = noSpace.replace("health", "");
			try {
				float h = Float.parseFloat(hs);
				consoleDisplay("Set health to: " + h);
				GameState.instance.health = h;
			}
			catch (Throwable t) {
				consoleDisplay("Try: \"health <floating point number>\" (no quotes)");
			}
		}
		else if (noSpace.startsWith("score")) {
			String hs = noSpace.replace("score", "");
			try {
				int h = (int)Float.parseFloat(hs);
				consoleDisplay("Set score to: " + h);
				GameState.instance.score = h;
			}
			catch (Throwable t) {
				consoleDisplay("Try: \"score <integer>\" (no quotes)");
			}
		}
		else if (noSpace.startsWith("speed")) {
			String hs = noSpace.replace("speed", "");
			try {
				float h = Float.parseFloat(hs);
				consoleDisplay("Set player speed to: " + h);
				if (this instanceof Level) {
					Level l = (Level)this;
					if (l.getScene() != null)
						l.getScene().setPlayerSpeed(h);
				}
				//GameState.instance.score = h;
			}
			catch (Throwable t) {
				consoleDisplay("Try: \"speed <floating point number>\" (no quotes)");
			}
		}
		else if (noSpace.startsWith("gem")) {
			String hs = noSpace.replace("gem", "");
			try {
				int h = (int)Float.parseFloat(hs);
				consoleDisplay("Set number of gems to: " + h);
				GameState.instance.gems = h;
			}
			catch (Throwable t) {
				consoleDisplay("Try: \"gem <integer>\" (no quotes)");
			}
		}
		else if (noSpace.equals("messages")) {
			String appender = "";
			for (int i = 0; i < Level.messages.size(); i++) {
				String msg = Level.messages.get(i).getMessage();
				String nam = Level.messages.get(i).getName();
				appender += nam+": " + msg + "\n";
			}
			if (appender.length() == 0)
				consoleDisplay("No messages in storage.");
			else
				consoleDisplay(appender);
		}
		else if (noSpace.equals("vignette")) {
			GameState.doVignette = !GameState.doVignette;
			if (GameState.doVignette)
				consoleDisplay("Vignette turned on.");
			else
				consoleDisplay("Vignette turned off.");
		}
		else if (noSpace.equals("help")) {
			consoleDisplay("-Aeda Adventure Console-\nhealth - gets or sets health.\ngem - gets or sets number of gems.\nscore - gets or sets score.\nspeed - gets or sets player speed.\nvignette - enables or disables vignette.\nhelp - displays this message.\nmessages - displays any active messages.\nsave - force saves the game.\nteleport - teleports you to a location.\ngarrote - kills all enemies");
		}
		else
			consoleDisplay("Sorry, I didn't understand your command.\nType 'help' (no quotes) for help.");
	}
	
	private static Hashtable<String,Color> wordsToColor;
	
	public void drawConsole(Graphics g) {
		if (consoleMode) {
			consoleFlash += 0.05f;
			int y = getMain().getHeight();
			if (!isFullscreen())
				y = y - 46;
			else
				y = y - 20;
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0,y,getMain().getWidth(),20);
			g.setColor(Color.white);
			g.setFont(new Font("Courier New",0,12));// stroke characters: 7x12
			AttributedString as1 = new AttributedString(consoleString);
			if (consoleString.length() > 0)
				as1.addAttribute(TextAttribute.FONT, g.getFont());
			/*if (consoleString.indexOf("dump") > -1) {
				as1.addAttribute(TextAttribute.FOREGROUND, Color.green, consoleString.indexOf("dump"),consoleString.indexOf("dump")+4);
			}*/
			Set<String> keys = wordsToColor.keySet();
			String[] ars = new String[keys.size()];
			ars = keys.toArray(ars);
			for (int i = 0; i < ars.length; i++) {
				String key = ars[i];
				Color val = wordsToColor.get(key);
				for (int index = consoleString.indexOf(key); index >= 0; index = consoleString.indexOf(key, index + 1))
				{
					try {
						as1.addAttribute(TextAttribute.FOREGROUND, val, index,index+key.length());
					}
					catch (Throwable t) {
						
					}
				}
			}
			g.drawString(as1.getIterator(),0,y+14);
			if ((int)consoleFlash % 2 == 0) {
				int len = g.getFontMetrics().stringWidth(consoleString);
				g.drawLine(len, y+14,len+7,y+14);
			}
			long disTime = System.currentTimeMillis() - consoleDisplayTime;
			if (consoleDisplay != null && disTime < 8000) {
				String[] trs = consoleDisplay.split("\n");
				for (int i = 0; i < trs.length; i++) {
					int sn = (14 * (trs.length-1)) - (i * 14);
					String sa = trs[i];
					int l = g.getFontMetrics().stringWidth(sa);
					int alp = 200;
					int al2 = 255;
					if (disTime > 6000) {
						alp = 200 - (int)(((disTime-6000) * 200) / 2000);
						al2 = 255 - (int)(((disTime-6000) * 255) / 2000);
					}
					g.setColor(new Color(255,255,0,alp));
					g.fillRect(0,y-14-sn,l,14);
					g.setColor(new Color(0,0,0,al2));
					g.drawString(sa, 0,y-2-sn);
				}
			}
			//g.drawLine(consolePos)
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
	
	public Object[] fitStringToRect(Graphics g, String str, String fontName, int width,int height) {
		boolean fits = true;
		int size = 1;
		int fixWidth = width;
		int fixHeight = height;
		Font sample = new Font(fontName,0,size);
		while (fits) {
			size++;
			sample = new Font(fontName,0,size);
			FontMetrics m = g.getFontMetrics(sample);
			fixWidth = m.stringWidth(str);
			fixHeight = m.getMaxAscent();
			if (fixWidth > width || fixHeight > height-20)
				fits = false;
		}
		if (size > 4)
			size = size - 3;
		sample = new Font(fontName,0,size);
		fixWidth = g.getFontMetrics(sample).stringWidth(str);
		return new Object[]{sample,fixWidth,(int)(fixHeight*0.8f)};
	}
	
	public void drawButton(Graphics g, String s,int x, int y, int width, int height) {
		Font original = g.getFont();
		Color topRect = new Color(125,93,59);
		Color bottomRect =new Color(100,76,49);
		Color border = new Color(84,56,30);
		Color innerBorder = new Color(166,130,126);
		if (getMouseX() <= x+width && getMouseX() >= x && getMouseY() <= y+height && getMouseY() >= y) {
			topRect = new Color(115,83,49);
			bottomRect = new Color(90,66,39);
			border = new Color(74,46,20);
			innerBorder = new Color(156,120,116);
			inButton = y;
		}
		for (int i = 0; i < height; i++) {
			g.setColor(lerp(topRect,bottomRect,((float)i)/height));
			if (i < 4)
				g.drawLine(x+(4-i),y+i,x+width-(4-i),y+i);
			else if (i > height-4)
				g.drawLine(x+4-(height-i),y+i,x+width-(4-(height-i)),y+i);
			else
				g.drawLine(x,y+i,x+width,y+i);
		}
		g.setColor(innerBorder);
		g.drawRoundRect(x,y+1,width,height-1,8,8);
		g.setColor(border);
		g.drawRoundRect(x,y,width,height,8,8);
		
		g.setColor(Color.white);
		Object[] t = fitStringToRect(g,s,"Arial",width,height);
		g.setFont((Font)t[0]);
		int whalf = (width-(int)t[1])/2;
		int hhalf = (height+(int)t[2])/2;
		g.drawString(s,x+5+whalf,y+hhalf);
		g.setFont(original);
	}
	
	public void hideCursor() {
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank");
		if (getMain() instanceof Main)
			((Main)getMain()).getContentPane().setCursor(blank);
	}
	
	public boolean isFullscreen() {
		if (getMain() instanceof Main)
			return ((Main)getMain()).isFullscreen();
		else
			return false;
	}
	
	public void drawCursor(Graphics g) {
		g.setColor(Color.black);
		g.setXORMode(Color.white);
		Polygon polygon = new Polygon();
		polygon.addPoint(getMouseX(),getMouseY());
		polygon.addPoint(getMouseX(),getMouseY()+13);
		polygon.addPoint(getMouseX()+2, getMouseY()+11);
		polygon.addPoint(getMouseX()+5,getMouseY()+15);
		polygon.addPoint(getMouseX()+7,getMouseY()+14);
		polygon.addPoint(getMouseX()+4,getMouseY()+10);
		polygon.addPoint(getMouseX()+6,getMouseY()+8);
		polygon.addPoint(getMouseX(), getMouseY());
		g.fillPolygon(polygon);
		g.setPaintMode();
		g.drawPolygon(polygon);
	}
}
