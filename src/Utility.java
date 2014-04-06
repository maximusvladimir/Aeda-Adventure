import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Utility {
	private static NetworkInterface interfacer;
	public static final P3D findMidpoint(P3D p0, P3D p1, P3D p2) {
		float px = p0.x + p1.x + p2.x;
		float py = p0.y + p1.y + p2.y;
		float pz = p0.z + p1.z + p2.z;
		return new P3D(px * 0.333333f, py * 0.333333f, pz * 0.333333f);
	}
	
	public static boolean isStringAllUpper(String test) {
		try {
			test = test.replaceAll("[0-9]","");
			String upper = test.replaceAll("[^A-Z]+", "");
			if (upper.equals(test))
				return true;
			else
				return false;
		}
		catch (Throwable t) {
			return false;
		}
	}
	
	public static void drawWorldMap(Graphics g,Main m) {
		g.setColor(new Color(0,0,0, 175));
		int sx = (int)(m.getWidth()*0.075f);
		int sy = (int)(m.getHeight()*0.075f);
		int sw = m.getWidth()-(int)(m.getWidth()*0.15f)-8;
		int sh = m.getHeight()-(int)(m.getHeight()*0.15f)-32;
		g.fillRoundRect(sx,sy,sw,sh, 15, 15);
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Arial",0,14));
		g.drawString("Fi\u00E4ce Forest", sx+6,sy+18);
		
		int rectSize = (int)(m.getWidth()*0.14f);
		int rectSizeHalf = rectSize/2;
		drawBorderRect(g,new Color(255,255,255,45),new Color(255,0,0,125),sx+(sw/2)-rectSizeHalf,sy+sh-rectSize,rectSize,rectSize,5);
		drawBorderRect(g,new Color(255,255,255,45),new Color(0,255,0,125),sx+(sw/2)-(int)(0.7f*rectSizeHalf),sy+sh-rectSize-rectSize,(int)(0.7f*rectSize),rectSize,5);
	}
	
	public static void drawBorderRect(Graphics g,Color border, Color fore, int x, int y, int w, int h, int s) {
		g.setColor(border);
		g.fillRect(x,y,w,h);
		g.setXORMode(border);
		g.fillRect(x+s,y+s,w-(s*2),h-(s*2));
		g.setPaintMode();
		g.setColor(fore);
		g.fillRect(x+s,y+s,w-(s*2),h-(s*2));
	}
	
	public static NetworkInterface getDefaultNetworkAdapter() {
		while (interfacer == null) {
			Thread.yield();
		}
		return interfacer;
	}
	
	public static void startInterfaceLookup() {
		if (interfacer != null)
			return;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				interfacer = findNetworkAdapter();
				try {
					Thread.sleep(5000);
				}
				catch (Throwable t) {
					
				}
			}
		});
		thread.setName("NETWORKCARDLOOKUP");
		thread.setDaemon(true);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	private static NetworkInterface findNetworkAdapter() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			for (NetworkInterface interface_ : Collections
					.list(interfaces)) {
				if (interface_.isLoopback())
					continue;
				if (!interface_.isUp())
					continue;
				Enumeration<InetAddress> addresses = interface_
						.getInetAddresses();
				for (InetAddress address : Collections.list(addresses)) {
					if (address instanceof java.net.Inet6Address)
						continue;
					if (!address.isReachable(3000))
						continue;
					SocketChannel socket = SocketChannel.open();
					try {
						socket.socket().setSoTimeout(3000);
						socket.bind(new InetSocketAddress(address, 8080));
						socket.connect(new InetSocketAddress("google.com", 80));
						socket.close();
					} catch (IOException ex) {
						//ex.printStackTrace();
						try {
							socket.close();
						}
						catch (Throwable t) {
							
						}
						continue;
					}
					break;
					//System.out.format("ni: %s, ia: %s\n", interface_, address);
					// stops at the first *working* solution
					//break OUTER;
				}
				return interface_;
			}
		} catch (Throwable t) {

		}
		return null;
	}

	public static Color validateColor(String value) {
		value = value.trim();
		// (Red,Green,Blue,Orange,Yellow,Magneta)
		if (value.toLowerCase().equals("red")) {
			return Color.red.darker();
		} else if (value.toLowerCase().equals("green")) {
			return Color.green.darker();
		} else if (value.toLowerCase().equals("blue")) {
			return Color.blue.darker();
		} else if (value.toLowerCase().equals("orange")) {
			return Color.orange.darker();
		} else if (value.toLowerCase().equals("yellow")) {
			return Color.yellow.darker();
		} else if (value.toLowerCase().equals("magenta")) {
			return Color.magenta.darker();
		} else {
			if (value.indexOf(",") > -1) {
				String[] rgb = value.split(",");
				if (rgb.length != 3)
					return null;
				int r = 0;
				int g = 0;
				int b = 0;
				try {
					r = Integer.parseInt(rgb[0]);
					g = Integer.parseInt(rgb[1]);
					b = Integer.parseInt(rgb[2]);
				} catch (Throwable t) {
					return null;
				}
				if (r > 220)
					r = 220;
				if (r < 40)
					r = 40;
				if (g > 220)
					g = 220;
				if (g < 40)
					g = 40;
				if (b > 220)
					b = 220;
				if (b < 40)
					b = 40;
				return new Color(r, g, b);
			} else
				return null;
		}
	}

	private static long lastDotDuration;
	private static float dotDelta;

	public static String getDots() {
		if (System.currentTimeMillis() - lastDotDuration > 33) {
			lastDotDuration = System.currentTimeMillis();
			dotDelta += 0.1f;
		}
		int ddots = (int) (dotDelta) % 4;
		String end = "";
		for (int i = 0; i < ddots; i++) {
			end += ".";
		}
		return end;
	}

	/**
	 * About 3x faster than Math.sin(), but only produces number -1 through 1
	 * 
	 * @param x
	 * @return
	 */
	public static final double fastSin(double x) {
		final double x2 = x * x;
		return ((((.00015148419 * x2 - .00467376557) * x2 + .07968967928) * x2 - .64596371106)
				* x2 + 1.57079631847)
				* x;
	}

	public static final boolean colorEqual(Color c0, Color c1, Color c2) {
		return (c0.getRGB() == c1.getRGB() && c1.getRGB() == c2.getRGB());
	}

	public static final int[] crossProduct(int x0, int y0, int x1, int y1) {
		int cx = y0 - y1;
		int cy = x1 - x0;
		int cz = x0 * y1 - y0 * x1;
		return new int[] { cx, cy };
	}

	public static void drawMap(Graphics g, Main m, Scene<Drawable> scene) {
		int mapx = m.getWidth() - (int) (m.getWidth() * 0.16f) - 8;
		int mapy = (int) (m.getWidth() * 0.02f);
		int mapw2 = (int) (m.getWidth() * 0.14f);
		int mapw = mapw2 - 10;
		g.setColor(new Color(0, 0, 0, 90));
		g.drawRect(mapx + 2, mapy + 2, mapw2 - 1, mapw2 - 1);
		
		g.drawRect(mapx + 1, mapy + 1, mapw2 - 1, mapw2 - 1);
		g.setColor(new Color(113, 79, 55));
		g.fill3DRect(mapx, mapy, mapw2, mapw2, true);
		//g.setColor(new Color(84, 109, 58));
		//g.fillRect(mapx + 2, mapy + 2, mapw + 6, mapw + 6);
		if (mapw + 7 == 68) {
			g.drawImage(scene.getGamePlane().getGenWorld(), mapx + 2, mapy + 2, null);
		}
		else {
			g.drawImage(scene.getGamePlane().getGenWorld(), mapx + 2, mapy + 2, mapw + 6, mapw + 6, null);
		}

		g.setColor(new Color(26, 244, 52));
		ArrayList<Gem> gems = scene.<Gem>getObjectsByType(Gem.class);
		for (int i = 0; i < gems.size(); i++) {
			if (!gems.get(i).isVisible())
				continue;
			int entityx = (int) ((gems.get(i).getInstanceLoc().x + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((gems.get(i).getInstanceLoc().z + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		g.setColor(new Color(144, 109, 74));
		ArrayList<Tree> trees = scene.<Tree>getObjectsByType(Tree.class);
		for (int i = 0; i < trees.size(); i++) {
			if (!trees.get(i).isVisible())
				continue;
			int entityx = (int) ((trees.get(i).getInstanceLoc().x + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((trees.get(i).getInstanceLoc().z + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		g.setColor(Color.white);
		g.fillRect((int) ((scene.getPlayerX() + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize()) + mapx + 4, mapy
				+ (int) ((scene.getPlayerZ() + scene.getWorldSizeHalf()) * mapw /scene.getWorldSize()) + 4, 5,5);
		g.setColor(GameState.instance.playerColor);
		g.fillRect((int) ((scene.getPlayerX() + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize()) + mapx + 5, mapy
				+ (int) ((scene.getPlayerZ() + scene.getWorldSizeHalf()) * mapw / scene.getWorldSize()) + 5, 3, 3);
	}

	private static Polygon gem = null;

	public static void drawHealth(Graphics g) {
		if (GameState.instance == null)
			return;
		if (gem == null) {
			gem = new Polygon();
			gem.addPoint(20, 80);
			gem.addPoint(28, 72);
			gem.addPoint(36, 80);
			gem.addPoint(28, 88);
			gem.addPoint(20, 80);
		}
		g.setColor(new Color(170, 50, 20));
		g.fillArc(15, 15, 45, 45, 0,
				(int) (GameState.instance.health / 10.0f * 360.0f));
		g.setColor(Color.black);
		g.drawOval(15, 15, 45, 45);
		g.setFont(new Font("Courier New", 0, 14));
		g.drawString((int) (GameState.instance.health) + "", 29, 42);
		g.setColor(Color.white);
		g.drawString((int) (GameState.instance.health) + "", 28, 43);
		g.setFont(new Font("Courier New", 0, 20));
		g.drawString(GameState.instance.gems + "", 40, 86);
		g.drawString(GameState.instance.score + "", 40, 108);
		g.setColor(Color.green);
		g.fillPolygon(gem);
		g.setColor(new Color(0, 120, 0));
		g.drawPolygon(gem);
	}
	
	public static Color adjustBrightness(Color base, int amount) {
		int dr = base.getRed() + amount;
		int dg = base.getGreen() + amount;
		int db = base.getBlue() + amount;
		return new Color(MathCalculator.colorLock(dr),MathCalculator.colorLock(dg),MathCalculator.colorLock(db));
	}
	
	private static ArrayList<String> evilTracker = new ArrayList<String>();
	private static Font messageFont = new Font("Courier New", 0, 12);

	public static void showDialog(String message, Graphics g, Main mainInst) {
		if (evilTracker.indexOf(message) == -1) {
			// This is evil in the sense that it stores already shown messages.
			evilTracker.add(message);
			SoundManager.playClick = true;
		}
		g.setColor(new Color(185, 163, 124, 125));
		g.fillRoundRect(10, mainInst.getHeight() - 125,
				mainInst.getWidth() - 26, 85, 8, 8);
		g.setColor(new Color(109, 88, 57));
		g.drawRoundRect(13, mainInst.getHeight() - 122,
				mainInst.getWidth() - 33, 78, 8, 8);
		g.setFont(messageFont);
		g.setColor(Color.black);
		if (message.indexOf('\n') == -1) {
			if (message.length() <= 66) {
				g.drawString(message, 21, mainInst.getHeight() - 106);
			} else {
				if (message.length() <= 133) {
					g.drawString(message.substring(0, 66), 21,
							mainInst.getHeight() - 106);
					g.drawString(message.substring(67), 21,
							mainInst.getHeight() - 92);
				} else if (message.length() <= 200) {
					g.drawString(message.substring(0, 66), 21,
							mainInst.getHeight() - 106);
					g.drawString(message.substring(67, 133), 21,
							mainInst.getHeight() - 92);
					g.drawString(message.substring(134), 21,
							mainInst.getHeight() - 78);
				}
			}
		} else {
			String[] lines = message.split("\n");
			for (int i = 0; i < lines.length; i++) {
				g.drawString(lines[i], 21, mainInst.getHeight()
						- (106 - (i * 14)));
			}
		}
		// g.drawString(message, 21,mainInst.getHeight()-96);
	}

	private static long startTime;

	public static void qS() {
		startTime = System.currentTimeMillis();
	}

	public static long qEL() {
		return System.currentTimeMillis() - startTime;
	}
	
	public static void qE() {
		System.out.println(System.currentTimeMillis() - startTime);
	}
	
	public static BufferedImage generateVignette(int width, int height) {
		BufferedImage vignette = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) vignette.getGraphics();
		Point2D center = new Point2D.Float(width/2,height/2);
		float radius = (float)Math.sqrt(width*width+height*height);
		float[] dist = { 0.0f, 1.0f };
		Color[] colors = { new Color(0,0,0,0),new Color(0,0,0,200)};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist,
				colors);
		g.setPaint(p);
		g.fillRect(0,0,width,height);
		g.dispose();
		return vignette;
	}
}