import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class Shop extends Level {

	public Shop(String n, IMain inst) {
		super(inst);
		setName(n);
	}

	public void init() {
		setFlakesVisible(false);
		addMessage(Strings.inst.SHOP_ENTRY_MSG,"shopEntryPoint",false,false, new ActionListener() {
			public void actionPerformed(ActionEvent ar) {
				shownMessage = true;
			}
		});
		addMessage(Strings.inst.SHOP_LEAVE_MSG, "shopExit", false, false,new ActionListener() {
			public void actionPerformed(ActionEvent ar) {
				startPrivateTransition = true;
				setActiveMessage(null);
			}
		});
	}
	
	public void reloadedLevel() {
		 
	}

	public void resize(int width, int height) {

	}

	public void draw(Graphics g) {
		Rand swap = new Rand(0);
		g.setColor(new Color(46, 37, 27));
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());

		g.setColor(new Color(228, 192, 143));
		g.fillRect(0, (int) (getMain().getHeight() * 0.36), getMain()
				.getWidth(), (int) (getMain().getHeight() * 0.13));
		g.setColor(new Color(217, 150, 89));
		g.fillRect(0, (int) (getMain().getHeight() * 0.49), getMain()
				.getWidth(), (int) (getMain().getHeight() * 0.2));
		g.setColor(new Color(134, 98, 30));
		g.fillRect(0, (int) (getMain().getHeight() * 0.69), getMain()
				.getWidth(), (int) (getMain().getHeight() * 0.4));

		g.setColor(new Color(30,30,40));
		g.fillOval((int)(getMain().getWidth()*0.65)-10,(int)(getMain().getHeight()*0.1)-20,95,70);
		g.setColor(new Color(40,0,0));
		g.fillOval((int)(getMain().getWidth()*0.65),(int)(getMain().getHeight()*0.1),30,12);
		g.fillOval((int)(getMain().getWidth()*0.65)+45,(int)(getMain().getHeight()*0.1),30,12);
		if (kreepySmile == null) {
			kreepySmile = new Polygon();
			kreepySmile.addPoint((int)(getMain().getWidth()*0.65),(int)(getMain().getHeight()*0.1)+20);
			kreepySmile.addPoint((int)(getMain().getWidth()*0.65)+37,(int)(getMain().getHeight()*0.1)+30);
			kreepySmile.addPoint((int)(getMain().getWidth()*0.65)+75,(int)(getMain().getHeight()*0.1)+20);
			kreepySmile.addPoint((int)(getMain().getWidth()*0.65)+37,(int)(getMain().getHeight()*0.1)+40);
		}
		g.fillPolygon(kreepySmile);
		
		g.setColor(new Color(104, 68, 0));
		for (int i = 0; i < 30; i++) {
			float level = swap.nextInt(8) * 0.05f;
			int y = (int) (getMain().getHeight() * (0.69 + level));
			int x = swap.nextInt(getMain().getWidth() - 50);
			g.drawLine(x, y, x + 100, y);
		}

		if (scaleAdjust > 0)
			scaleAdjust -= 0.01f;
		
		float scale = ((float) getMain().getWidth()) / 584.0f;
		final Color fontColor = Color.red;
		
		if (selectedItem % 4 == 0) {
			windDelta += 0.01f;
			drawRaft((Graphics2D)g,windDelta,20-(scaleAdjust*50), getMain().getHeight() * 0.37f, scale+scaleAdjust);
			g.setColor(fontColor);
			g.drawString(Strings.inst.SHOP_DISPL_RAFT, 13, (int)(getMain().getHeight() * 0.52f));
		}
		else
			drawRaft((Graphics2D)g,windDelta, 20, getMain().getHeight() * 0.37f, scale);
		
		if (selectedItem % 4 == 1) {
			glowDelta += 0.01f;
			drawMoonstone(swap,(Graphics2D)g,150-(scaleAdjust*25),getMain().getHeight()*0.37f,scale+scaleAdjust);
			g.setColor(fontColor);
			g.drawString(Strings.inst.SHOP_DISPL_MOON, 125, (int)(getMain().getHeight() * 0.52f));
		}
		else
			drawMoonstone(swap,(Graphics2D)g,150,getMain().getHeight()*0.37f,scale);
		
		if (selectedItem % 4 == 2) {
			drawLamp((Graphics2D)g,270-(scaleAdjust*23),getMain().getHeight()*0.37f,scale+scaleAdjust);
			g.setColor(fontColor);
			g.drawString(Strings.inst.SHOP_DISPL_LAMP, 260, (int)(getMain().getHeight() * 0.52f));
			lampDelta += 0.01f * Math.random();
		}
		else
			drawLamp((Graphics2D)g,270,getMain().getHeight()*0.37f,scale);
		
		if (selectedItem % 4 == 3) {
			drawPiece((Graphics2D)g,450,getMain().getHeight()*0.37f,scale+scaleAdjust);
			g.setColor(fontColor);
			g.drawString(Strings.inst.SHOP_DISPL_HART, 385, (int)(getMain().getHeight() * 0.52f));
		}
		else
			drawPiece((Graphics2D)g,450,getMain().getHeight()*0.37f,scale);
		
	}
	
	private static Polygon kreepySmile = null;
	private boolean startPrivateTransition = false;
	private boolean shownMessage = false;
	
	private String sender;
	public void setSender(String n) {
		sender = n;
	}
	
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			setActiveMessage("shopExit");
			//this.startTransition(getMain().getScreen(sender), GameState.ORIGINS,0);
		}
		else if (ke.getKeyCode() == KeyEvent.VK_E && !isMessageBeingShown()) {
			assertShop();
		}
		else
			super.keyReleased(ke);
	}
	
	private void assertShop() {
		int item = ((int)selectedD) % 4;
		switch (item) {
			case 0:
				if (GameState.instance.gems >= 200 && !GameState.instance.hasRaft) {
					addMessage("Purchase a raft for 200 Gems?", "RAFTBUY",true,new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Message m = (Message) arg0.getSource();
							if (m.getResult()) {
								GameState.instance.hasRaft = true;
								GameState.instance.gems -= 200;
								GameState.save();
							}
						}		
					});
					setActiveMessage("RAFTBUY");
				} else if (GameState.instance.hasRaft) {
						addMessage("You already have a raft.","RAFTHAVE");
						setActiveMessage("RAFTHAVE");
				}
				else {
					addMessage(Strings.inst.SHOP_NO_FUNDS,"RAFTBUYFAILURE");
					setActiveMessage("RAFTBUYFAILURE");
				}
			break;
			
			case 1:
				if (GameState.instance.gems >= 400 && !GameState.instance.hasMoonstone) {
					addMessage("Purchase a moonstone for 400 Gems?", "MOONBUY",true,new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Message m = (Message) arg0.getSource();
							if (m.getResult()) {
								GameState.instance.hasMoonstone = true;
								GameState.instance.gems -= 400;
								GameState.save();
							}
						}		
					});
					setActiveMessage("MOONBUY");
				} else if (GameState.instance.hasMoonstone) {
					addMessage("You already have a moonstone.","MOONHAVE");
					setActiveMessage("MOONHAVE");
				}
				else {
					addMessage(Strings.inst.SHOP_NO_FUNDS,"MOONBUYFAILURE");
					setActiveMessage("MOONBUYFAILURE");
				}
			break;
			
			case 2:
				if (GameState.instance.gems >= 600 && !GameState.instance.hasLantern) {
					addMessage("Purchase a lantern for 600 Gems?", "LANBUY",true,new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Message m = (Message) arg0.getSource();
							if (m.getResult()) {
								GameState.instance.hasLantern = true;
								GameState.instance.gems -= 600;
								GameState.save();
							}
						}		
					});
					setActiveMessage("LANBUY");
				} else if (GameState.instance.hasLantern) {
					addMessage("You already have a moonstone.","LANHAVE");
					setActiveMessage("LANHAVE");
				}
				else {
					addMessage(Strings.inst.SHOP_NO_FUNDS,"LANBUYFAILURE");
					setActiveMessage("LANBUYFAILURE");
				}
			break;
			
			case 3:
				if (GameState.instance.gems >= 800) {
					addMessage("Purchase a heart piece for 800 Gems?", "HEARTBUY",true,new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Message m = (Message) arg0.getSource();
							if (m.getResult()) {
								GameState.instance.healthPieces++;
								GameState.instance.gems -= 800;
								GameState.save();
							}
						}		
					});
					setActiveMessage("HEARTBUY");
				} else {
					addMessage(Strings.inst.SHOP_NO_FUNDS,"HEARTBUYFAILURE");
					setActiveMessage("HEARTBUYFAILURE");
				}
			break;
		}
	}
	
	public void keyDown(int code) {
		if (getMessageIndexer() != -1)
			return;
		if (code == KeyEvent.VK_W) {
			//selectedItem++;
			selectedD += 0.175f;
			if ((int)selectedD != selectedItem) {
				selectedItem = (int)selectedD;
				scaleAdjust = 0.5f;
			}
		}
	}
	
	private void drawPiece(Graphics2D g, float x, float y, float scale) {
			Color alter;
			float w = scale * 50;
			float h = scale * 50;
			Polygon builder = new Polygon();
			builder.addPoint((int)((scale*25)),(int)((scale*25)));
			builder.addPoint((int)-((scale*25)),0);
			builder.addPoint((int)((scale*25)),(int)(-(scale*25)));
			float size = (float) Math.sqrt(w * h) * 0.15f;
			for (int i = (int) size; i > 0; i--) {
				int t = i + 1;
				alter = new Color(67,148,228,
						MathCalculator
								.colorLock(((int) ((size - t) / size * 255.0f))));
				g.setColor(alter);
				
				AffineTransform def = (AffineTransform)g.getTransform().clone();
				float z = i*0.1f;
				g.translate(x+z, y-z);
				g.setStroke(new BasicStroke(25-i));
				g.scale(z+0.6f,z+0.4f);
				g.drawPolygon(builder);
				g.setTransform(def);
			}
			g.setStroke(new BasicStroke(1));
			g.setColor(Color.red);
			g.translate(x, y);
			g.fillPolygon(builder);
			g.translate(-x, -y);
	}
	
	private void drawLamp(Graphics2D g, float x, float y, float scale) {
		g.setColor(new Color(142,119,49));
		g.fillOval((int)x,(int)y,(int)(scale*55),(int)(scale*26));	
		g.setColor(new Color(187,156,64));
		g.fillOval((int)x,(int)(y*1.02),(int)(scale*55),(int)(scale*12));	
		
		g.fillRect((int)x,(int)(y-(scale*50)),(int)(scale * 9),(int)(scale * 60));
		g.fillRect((int)(x+(scale*55)-(scale * 9)),(int)(y-(scale*50)),(int)(scale * 9),(int)(scale * 60));
		
		float hue = 1-(float)(Math.abs((MathCalculator.cos(lampDelta)*0.1666666666666)));
		Color lam = new Color(Color.HSBtoRGB(1-hue, 1, 1));
		g.setColor(lam);
		g.fillOval((int)(x+(scale*22)),(int)(y-(scale*10)),(int)(scale*10),(int)(scale*16));
		
		Color sc = MathCalculator.lerp(Color.white,lam, ((float)MathCalculator.sin(lampDelta*0.2+309)) * 0.285f);
		LinearGradientPaint lgp = new LinearGradientPaint(x,(y-(scale*55)),x+(scale*36),y,new float[] {0.0f,1.0f},
				new Color[]{Utility.adjustAlpha(sc, 40),Utility.adjustAlpha(sc, 100)});
		g.setPaint(lgp);
		g.fillRoundRect((int)(x+(scale*9)),(int)(y-(scale*55)),(int)(scale*38),(int)(65*scale),(int)(scale*30),(int)(scale*12));
		
		g.setColor(new Color(187,156,64));
		g.fillOval((int)x,(int)(y-(scale*60)),(int)(scale*53),(int)(scale*20));
	}
	
	private void drawMoonstone(Rand rand,Graphics2D g, float x, float y, float scale) {
		//g.setColor(new Color(54,180,216,200));
		float glow = (float)(Math.sin(glowDelta) * 20) - 20;
		Color s = Utility.adjustBrightness(new Color(94,220,255,200),(int)glow);
		Color e = Utility.adjustBrightness(new Color(24,150,186,200),(int)glow);
		LinearGradientPaint lgp = new LinearGradientPaint(x,y,x+50,y,new float[] {0.0f,1.0f},
				new Color[]{s,e});
		g.setPaint(lgp);
		g.fillOval((int)x,(int)(y-(scale*80)),(int)(scale*50),(int)(scale*100));
		g.setColor(new Color(200,200,200,200));
		g.drawOval((int)x,(int)(y-(scale*80)),(int)(scale*50),(int)(scale*100));
	}

	private static void drawRaft(Graphics2D g,float windDelta2, float x, float y, float scale) {
		for (int i = 0; i < 5; i++) {
			int offset = (int)(i * 4 * scale);
			g.setColor(new Color(108, 91, 60));
			g.fillRoundRect((int) x-offset, (int) y+offset, (int) (100 * scale),
					(int) (20 * scale), (int) (10 * scale), (int) (5 * scale));
			g.setColor(new Color(0, 0, 0, 100));
			g.drawRoundRect((int) x-offset, (int) y+offset, (int) (100 * scale),
					(int) (20 * scale), (int) (10 * scale), (int) (5 * scale));
			if (i == 2) {
				g.setColor(new Color(78,97,107));
				g.fillRect((int) (x-offset+(50 * scale)), (int)(y+offset-(scale*75)+(10 * scale)),(int)(scale*7),(int)(scale*75));
			}
		}
		float wind = (float)(Math.sin(windDelta2) * (scale*15));
		float windY = (float)(Math.cos(windDelta2 * 0.5f) * (scale*10));
		float windO = (float)(Math.cos(windDelta2*0.59) * ((scale*3)));
		float offset = 8 * scale;
		GeneralPath sail = new GeneralPath();
		sail.moveTo((x-offset+(50 * scale))+3+windO, (y+offset-(scale*75)+(10 * scale)));
		sail.curveTo((x-offset+(50 * scale))+3+windO, (y+offset-(scale*75)+(10 * scale)),
				(x-offset+(50 * scale))+55+wind, (y+offset-(scale*50)+windY+(10 * scale)),
				(x-offset+(50 * scale))+3+windO, (y+offset-(scale*26)+(10 * scale)));
		sail.closePath();
		g.setColor(new Color(209,200,191));
		g.fill(sail);
	}
	
	private int selectedItem = 0;
	private float scaleAdjust = 0;
	private float selectedD = 0;
	
	private float windDelta = 0.0f;
	private float glowDelta = 0.0f;
	private float lampDelta = 0.0f;
	private float transitionDelta = 0.0f;
	private static BufferedImage lanternImage = null;
	public static BufferedImage getLanternImage() {
		if (lanternImage == null) {
			
		}
		return lanternImage;
	}
	private static BufferedImage moonImage = null;
	public static BufferedImage getMoonstoneImage() {
		if (moonImage == null) {
			
		}
		return moonImage;
	}
	private static BufferedImage raftImage = null;
	public static BufferedImage getRaftImage() {
		if (raftImage == null) {
			raftImage = new BufferedImage(48,48,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = (Graphics2D)raftImage.getGraphics();
			final float scaler = 48.0f / 200.0f * 1.5f;
			drawRaft(g,98.4f,8,28,scaler);
			g.setColor(Color.red);
			g.setFont(new Font("Courier New",0,20));
			g.drawString("Z",36,46);
			g.dispose();
		}
		return raftImage;
	}

	public void drawHUD(Graphics g) {
		super.drawHUD(g);
		if (!shownMessage) {
			setActiveMessage("shopEntryPoint");
		}
		if (startPrivateTransition) {
			transitionDelta += 3.5f;
			if (transitionDelta >= 255.0f) {
				startPrivateTransition = false;
				transitionDelta = 0;
				shownMessage = false;
				getMain().setActiveScreen(sender);
			}
			g.setColor(Utility.adjustAlpha(Color.black, (int)(transitionDelta)));
			g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		}
	}
}