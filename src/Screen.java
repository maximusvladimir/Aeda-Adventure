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
import java.awt.image.BufferedImage;


public abstract class Screen {
	private IMain inst;
	private boolean inited = false;
	private int mx = 0, my = 0;
	private BufferedImage buffer;
	private long timeSinceInit = -1;
	private static long counter = 0;
	public Screen(IMain inst) {
		this.inst = inst;
		buffer = new BufferedImage(inst.getWidth(),inst.getHeight(),BufferedImage.TYPE_INT_RGB);
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
