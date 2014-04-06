import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.JOptionPane;


public class MainMenu extends Screen {
	private PointTesselator terrain;
	private SceneTesselator scene;
	private Flake[] parts;
	public MainMenu(Main inst) {
		super(inst);
	}

	public void init() {
		if (isFullscreen())
			hideCursor();
		
		terrain = new PointTesselator();
		scene = new SceneTesselator();
		parts = new Flake[15];
		clouds = new float[60];
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Flake();
			parts[i].x = (float)(Math.random() * getMain().getWidth());
			parts[i].y = (float)(Math.random() * getMain().getHeight());
			parts[i].dx = (float)(Math.random() * 1.1f);
			parts[i].size = (int)(Math.random() * 4);
			parts[i].clr = (int)(Math.random() * 127) +127;
			if (Math.random() < 0.5)
				parts[i].dx *= -1;
		}
		for (int i = 0; i < clouds.length; i++) {
			clouds[i] = (float)(getMain().getWidth() * 1.2f * Math.random()) - (getMain().getWidth() * 0.2f);
		}
		scene.addTesselator(terrain);
		terrain.setSize(getCompatabilityBuffer(), getMain().getWidth(),getMain().getHeight());
		heightInit();
		
		FileSave save = new FileSave();
		if (!save.exists()) {
			GameState.instance = new GameState();
			String res = "";
			while (res == null || res.equals("") || res.length() > 8) {
				 res = JOptionPane.showInputDialog(getMain(), "Please enter your name. (Less than 9 chars)");
			}
			res = Utility.capitalizeEnumerator(res);
			GameState.instance.playerGUID = res;
			res = null;
			Color pC = null;
			if (GameState.instance.playerGUID.equals("Sheik") || GameState.instance.playerGUID.equals("Zelda")) {
				pC = new Color(50,127,218);
			}
			else {
			while (res == null || res.equals("") || (pC = Utility.validateColor(res)) == null) {
				 res = JOptionPane.showInputDialog(getMain(), 
						 "Please enter your favorite color." +
						"\n(Red,Green,Blue,Orange,Yellow,Magneta)." +
						"\nYou can also enter an RGB like (no quotes): \"255,0,0\"");
			}
			}
			GameState.instance.playerColor = pC;
			GameState.save();
		}
		else {
			GameState.instance = save.load();
		}
		
		SoundManager.start();
	}

	public void resize(int width, int height) {
		terrain.setSize(getCompatabilityBuffer(), width, height);
		if (isFullscreen())
			hideCursor();
		else
			getMain().getContentPane().setCursor(Cursor.getDefaultCursor());
	}

	
	float normalizerMax = -100.0f;
	float normalizerMin = 100.0f;
	float[] clouds;
	private Color computeColor(float value) {
		// An evil hack to put the
		// height values between 0 and 1.
		float norm = (value) / heightCoefficent;
		if (norm < normalizerMin)
			normalizerMin = norm;
		norm = (norm) + Math.abs(normalizerMin);
		if (norm > normalizerMax)
			normalizerMax = norm;
		norm = (norm / normalizerMax);

		// If the values are still out of range,
		// force them between 0 and 1.
		if (norm > 1)
			norm = 1;
		if (norm < 0)
			norm = 0;

		//int rgb = (int)MathCalculator.lerp(150,240,norm);
		//return new Color(rgb,rgb,rgb);
		return MathCalculator.lerp(new Color(20,20,15), new Color(70,70,56), norm);
	}

	private void heightInit() {
		PointTesselator.generateStagedPoints(2811432349363113984L,width,10.0,5.0);
				//(long) (Long.MAX_VALUE * Math.random()), width, 10.0, 5.0);
	}

	private float getHeight(float x, float z) {
		float s = 0.0f;//(float)(Math.sin(delta + x) * 16);
		return PointTesselator.getHeightData((int) x, (int) z)
				* heightCoefficent + s;
	}
	
	private void glow(Color baseColor,Graphics2D g, float x, float y, float w,float h) {
		Color alter;
		float size = (float)Math.sqrt(w*h)*0.1f;
		g.setColor(baseColor);
		g.fill(new Ellipse2D.Float(x-1,y-1,w+2,h+2));
		for (int i = (int)size; i > 0; i--) {
			int t = i+1;
			alter = new Color(baseColor.getRed(),baseColor.getGreen(),baseColor.getBlue(),MathCalculator.colorLock(((int)((size-t)/size*255.0f))));
			g.setColor(alter);
			g.draw(new Ellipse2D.Float(x-i,y-i,w+(i*2),h+(i*2)));
		}
	}
	
	float heightCoefficent = 20;
	int width = 16;
	float delta = 0.0f;
	float theta = 0.0f;
	float moon = 0.0f;
	public void draw(Graphics g) {
		//Network.RUNNING = false;
		g.setColor(Color.black);
		g.fillRect(0,0,getMain().getWidth(),getMain().getHeight());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(new GradientPaint(0,0,new Color(33,39,71),0,getMain().getHeight()*0.7f,new Color(50,67,115)));
		g2d.fillRect(0,0,getMain().getWidth(),(int)(getMain().getHeight()*0.7f));
		glow(new Color(180,188,149),g2d,(getMain().getWidth() * 0.6f),(-getMain().getHeight() * 0.05f + moon),(getMain().getWidth() * 0.2f),(getMain().getWidth() * 0.2f));
		if (moon < getMain().getHeight() * 0.15f)
			moon += 0.063f;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		delta += 0.005f;
		terrain.setDrawType(DrawType.Triangle);
		terrain.setSkipCullCheck(true);
		terrain.setBackgroundColor(g.getColor());
		//terrain.rotate(0, delta, 0);
		terrain.rotate(0, 3.1415926535f, 0);
		float spacing = 24;
		float xs = 1.6f;
		theta += 0.01f + (Math.random() * 0.01f);
		float terrainOffset = (float)(Math.sin(theta) * 10);
		terrain.translate(0, 0, 100, true);
		terrain.translate(270+terrainOffset, -100, (-width * spacing) + 720, false);
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < width; z++) {
				terrain.color(computeColor(getHeight(x,z)));
				terrain.point((x)*spacing*xs, getHeight(x,z), z*spacing);
				terrain.point((x+1)*spacing*xs, getHeight(x+1,z), z*spacing);
				terrain.point((x+1)*spacing*xs, getHeight(x+1,z+1), (z+1)*spacing);
				terrain.color(computeColor(getHeight(x,z)));
				terrain.point((x)*spacing*xs, getHeight(x,z), z*spacing);
				terrain.point((x+1)*spacing*xs, getHeight(x+1,z+1), (1+z)*spacing);
				terrain.point((x)*spacing*xs, getHeight(x,z+1), (z+1)*spacing);
			}
		}
		Random te = new Random(509271);
		for (int i = 0; i < clouds.length; i++) {
			clouds[i] += 0.2f;
			if (clouds[i] > getMain().getWidth() + 50)
				clouds[i] = (int)-((getMain().getWidth() * 0.15f) * Math.random());
			g.setColor(new Color(50,50,50,120));
			g.fillOval((int)clouds[i],(i*50)%150 + te.nextInt(50),te.nextInt(50)+75,te.nextInt(15)+5);
		}
		//scene.fog(new Color(230,230,230), 200,-50);
		//scene.setReverseFogEquation(true);
		scene.draw(g);
		for (int i = 0; i < parts.length; i++) {
			Flake flake = parts[i];
			g.setColor(new Color(255,255,255,50));
			flake.x += flake.dx * 0.3f;
			flake.y += flake.size*0.05f;
			if (flake.dx < 0)
				if (flake.x < 0) {
					flake.x = (float)(getMain().getWidth() + (Math.random() * (getMain().getWidth()/4.0f)));
				}
			else
				if (flake.x > getMain().getWidth())
					flake.x = (float)(-Math.random() * (getMain().getWidth()/4.0f));
			if (Math.random() < 0.005)
				flake.size--;
			if (flake.size <= 0) {
				flake.y = (int)(Math.random() * getMain().getHeight());//(float)(-Math.random() * (getMain().getHeight() * 0.2f));
				flake.size = (int)((Math.random() * 5)+2);
			}
			if (flake.size == 1)
				g.drawLine((int)flake.x,(int)flake.y,(int)flake.x,(int)flake.y);
			else
				g.drawOval((int)flake.x,(int)flake.y,flake.size,flake.size);
		}
	}
	
	public String getName() {
		return "mainmenu";
	}
	private boolean startFade = false;
	private float fade = 0.0f;
	public void mouseReleased(MouseEvent me) {
		if (inButton != 0) {
			int id = (inButton-120)/60;
			//System.out.println("you pressed button# " + id);
			if (id == 0) {
				//SoundManager.playClick = true;
				startFade = true;
				//getMain().setActiveScreen(1);
			}
			if (id == 1) {
				//getMain().addScreen(new Multiplayer(getMain()));
				if (!getMain().screenExists("multiplayer")) {
					getMain().addScreen(new Multiplayer(getMain()));
				}
				//getMain().setActiveScreen(0);
				getMain().setActiveScreen("multiplayer");
			}
			if (id == 2) {
				SoundManager.playClick = true;
				System.exit(0);
			}
		}
	}

	public void drawHUD(Graphics g) {
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		inButton = 0;
		if (GameState.instance == null || GameState.instance.playerStage == 0) {
			drawButton(g,"Create new game",125,120,250,40);
		}
		else {
			drawButton(g,"Play as " + GameState.instance.playerGUID,125,120,250,40);
		}
		//g.setXORMode(Color.red);
		drawButton(g,"Multiplayer",125,180,250,40);
		//g.setPaintMode();
		drawButton(g,"Quit",125,240,250,40);
		
		g.setFont(new Font("Arial",0,12));
		g.setColor(Color.white);
		g.drawString("FPS:"+getMain().getFPS(), 0,10);
		
		g.drawString("Coded and designed by Max Kirkby",0,getMain().getHeight()-44);
		g.drawString("Thanks to freesound.org for beautiful legally free sound and music.", 0,getMain().getHeight()-30);
		
		g.setFont(new Font("Levenim MT",0,48));
		g.drawString("Aeda Adventure", 50,70);
		
		if (isFullscreen()) {
			drawCursor(g);
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		if (startFade) {
			if (fade >= 250) {
				if (GameState.instance.playerLevel == 0) {
					if (!getMain().screenExists("level")) {
						getMain().addScreen(new FiaceForest(getMain()));
					}
					getMain().setActiveScreen("level");
				}
				startFade = false;
				fade = 0.0f;
			}
			if (fade > 255)
				fade = 255;
			if (fade < 0)
				fade = 0;
			g.setColor(new Color(0,0,0,(int)fade));
			g.fillRect(0,0,getMain().getWidth(),getMain().getHeight());
			//fade += 2.0f;
			if (GameState.instance == null || GameState.instance.playerStage == 0) {
				fade += 2.3f;
			}
			else {
				fade += 9.0f;
			}
		}
	}
}
