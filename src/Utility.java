import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
	
	public static void doSound(Screen s) { 
		if (SoundManager.soundEnabled) {
			if (SoundManager.backgroundSound != null)
				SoundManager.backgroundSound.halt();
			if (s instanceof SailorHarbour || s instanceof CadenSea)
				SoundManager.backgroundSound = new Sound("harbour");
			if (s instanceof InsideHouse)
				SoundManager.backgroundSound = new Sound("piano");
			if (s instanceof HolmVillage)
				SoundManager.backgroundSound = new Sound("holm");
			if (s instanceof MainMenu)
				SoundManager.backgroundSound = new Sound("title");
			if (s instanceof FiaceForest)
				SoundManager.backgroundSound = new Sound("fiace");
			if (SoundManager.backgroundSound != null) {
				SoundManager.backgroundSound.setLooping(true);
				SoundManager.backgroundSound.play();
			}
		}
	}

	public static String capitalizeEnumerator(String str) {
		// capitalizes the first letter of every word in a phrase.
		try {
			String[] twm = str.split(" ");
			String build = "";
			for (int i = 0; i < twm.length; i++) {
				if (twm[i].length() >= 2)
					build += twm[i].substring(0, 1).toUpperCase()
							+ twm[i].substring(1);
				else
					build += twm[i].toUpperCase();
				if (twm.length - 1 != i)
					build += " ";
			}
			return build;
		} catch (Throwable t) {
			return str;
		}
	}

	public static float parseMoveDir(Scene<Drawable> scene,
			ControllerSupport cs, float playerDelta) {
		if (cs.getMoveDir() == 1.0) {
			playerDelta = playerDelta + 0.1f;
		} else if (cs.getMoveDir() == 0.125) {
			playerDelta = playerDelta + 0.05f;
			scene.movePlayer(true);
		} else if (cs.getMoveDir() == 0.25) {
			scene.movePlayer(true);
		} else if (cs.getMoveDir() == 0.375) {
			scene.movePlayer(true);
			playerDelta = playerDelta - 0.05f;
		} else if (cs.getMoveDir() == 0.5) {
			playerDelta = playerDelta - 0.1f;
		} else if (cs.getMoveDir() == 0.625) {
			playerDelta = playerDelta - 0.05f;
			scene.movePlayer(false);
		} else if (cs.getMoveDir() == 0.75) {
			scene.movePlayer(false);
		} else if (cs.getMoveDir() == 0.875) {
			playerDelta = playerDelta - 0.1f;
			scene.movePlayer(false);
		}
		return playerDelta;
	}

	public static boolean isStringAllUpper(String test) {
		try {
			test = test.replaceAll("[0-9]", "");
			String upper = test.replaceAll("[^A-Z]+", "");
			if (upper.equals(test))
				return true;
			else
				return false;
		} catch (Throwable t) {
			return false;
		}
	}

	public static void drawWorldMap(Graphics g, IMain m, Scene<Drawable> s) {
		g.setColor(new Color(0, 0, 0, 175));
		int sx = (int) (m.getWidth() * 0.075f);
		int sy = (int) (m.getHeight() * 0.075f);
		int sw = m.getWidth() - (int) (m.getWidth() * 0.15f) - 8;
		int sh = m.getHeight() - (int) (m.getHeight() * 0.15f) - 32;
		g.fillRoundRect(sx, sy, sw, sh, 15, 15);
		g.setFont(new Font("Arial", 0, 9));
		Color current = new Color(255, 255, 255, 45);
		Color currentBack = new Color(127,200,127,127);
		current = MathCalculator.lerp(current, new Color(255, 255, 255, 200),
				(float) Math.abs(Math.sin(flash)));
		int rectSize = (int) (m.getWidth() * 0.14f);
		int rectSizeHalf = rectSize / 2;
		Color def1 = new Color(255, 255, 255, 45);
		Color def2 = new Color(255, 255, 255, 45);
		Color def3 = new Color(255, 255, 255, 45);
		Color def4 = new Color(255, 255, 255, 45);
		Color def1a = new Color(255, 255, 255, 45);
		Color def2a = new Color(255, 255, 255, 45);
		Color def3a = new Color(255, 255, 255, 45);
		Color def4a = new Color(255, 255, 255, 45);
		float defpx = 0;
		float defpy = 0;
		float def1px = (m.getWidth() * 0.5f) - (rectSizeHalf);
		float def1py = (m.getHeight() * 0.72f) - (rectSizeHalf);
		float def2px = (m.getWidth() * 0.5f) - (rectSizeHalf);
		float def2py = (m.getHeight() * 0.72f) - (rectSizeHalf * 3);
		float def3px = (m.getWidth() * 0.5f) - (rectSizeHalf * 3);
		float def3py = (m.getHeight() * 0.72f) - (rectSizeHalf * 3);
		float def4px = (m.getWidth() * 0.5f) - (rectSizeHalf * 5.5f);
		float def4py = (m.getHeight() * 0.72f) - (rectSizeHalf * 3);
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Arial", 0, 14));
		if (s.getLevel().getName().equals("vbm")) {
			def2 = current;
			def2a = currentBack;
			defpx = def2px;
			defpy = def2py;
			g.drawString(Strings.inst.NAME_HOLM, sx + 6, sy + 18);
		}
		if (s.getLevel().getName().equals("level")) {
			def1 = current;
			def1a = currentBack;
			defpx = def1px;
			defpy = def1py;
			g.drawString(Strings.inst.NAME_FIACE, sx + 6, sy + 18);
		}
		if (s.getLevel().getName().equals("yLENIN")) {
			def3 = current;
			def3a = currentBack;
			defpx = def3px;
			defpy = def3py;
			g.drawString(Strings.inst.NAME_HARBOUR, sx + 6, sy + 18);
		}
		if (s.getLevel().getName().equals("lilo")) {
			def4 = current;
			def4a = currentBack;
			defpx = def4px;
			defpy = def4py;
			g.drawString(Strings.inst.NAME_CADEN_SEA, sx + 6, sy + 18);
		}
		
		Utility.drawBorderRect(g, def1, def1a, def1px, def1py, rectSize, rectSize, 4);
		Utility.drawBorderRect(g, def2, def2a, def2px, def2py, rectSize, rectSize, 4);
		Utility.drawBorderRect(g, def3, def3a, def3px, def3py, rectSize, rectSize, 4);
		Utility.drawBorderRect(g, def4, def4a, def4px, def4py, rectSize, rectSize, 4);
		
		flash += 0.013f;

		int entityx = (int) ((s.getPlayerX() + s.getWorldSizeHalf()) * rectSize / s
				.getWorldSize());
		int entityz = (int) ((s.getPlayerZ() + s.getWorldSizeHalf()) * rectSize / s
				.getWorldSize());
		entityx += defpx;
		entityz += defpy;
		g.setColor(Color.yellow);
		Polygon p = new Polygon();
		float delt = -(float) (s.getPlayerDelta() + Math.PI / 2);
		p.addPoint(rotatePointX(-3, entityx, -5, entityz, delt),
				rotatePointY(-3, entityx, -5, entityz, delt));
		p.addPoint(rotatePointX(3, entityx, -5, entityz, delt),
				rotatePointY(3, entityx, -5, entityz, delt));
		p.addPoint(rotatePointX(0, entityx, 5, entityz, delt),
				rotatePointY(0, entityx, 5, entityz, delt));
		g.fillPolygon(p);
		g.setColor(Color.black);
		g.drawPolygon(p);
		g.setFont(new Font("Arial", 0, 10));
		g.drawString(Strings.inst.NAME_FIACE,6 + (int)def1px,rectSizeHalf + (int)def1py);
		g.drawString(Strings.inst.NAME_HOLM,5 + (int)def2px,rectSizeHalf + (int)def2py);
		g.drawString(Strings.inst.NAME_HARBOUR_SHORT,15 + (int)def3px,rectSizeHalf + (int)def3py);
		g.drawString(Strings.inst.NAME_CADEN_SEA,8 + (int)def4px,rectSizeHalf + (int)def4py);

	}

	public static Color adjustAlpha(Color base, int alpha) {
		return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
	}

	public static int rotatePointX(int x, int cx, int y, int cy, double rad) {
		double cosAngle = MathCalculator.cos(rad);
		double sinAngle = MathCalculator.sin(rad);
		double dx = x;
		double dy = y;
		return cx + (int) (dx * cosAngle - dy * sinAngle);
	}

	public static int rotatePointY(int x, int cx, int y, int cy, double rad) {
		double cosAngle = MathCalculator.cos(rad);
		double sinAngle = MathCalculator.sin(rad);
		double dx = x;
		double dy = y;
		return cy + (int) (dx * sinAngle + dy * cosAngle);
	}

	private static float flash = 0.0f;

	public static void drawBorderRect(Graphics g, Color border, Color fore,
			float f, float i, int w, int h, int s) {
		g.setColor(border);
		g.fillRect((int)f, (int)i, w, h);
		g.setXORMode(border);
		g.fillRect((int)f + s, (int)i + s, w - (s * 2), h - (s * 2));
		g.setPaintMode();
		g.setColor(fore);
		g.fillRect((int)f + s, (int)i + s, w - (s * 2), h - (s * 2));
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
				} catch (Throwable t) {

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
			for (NetworkInterface interface_ : Collections.list(interfaces)) {
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
						// ex.printStackTrace();
						try {
							socket.close();
						} catch (Throwable t) {

						}
						continue;
					}
					break;
					// System.out.format("ni: %s, ia: %s\n", interface_,
					// address);
					// stops at the first *working* solution
					// break OUTER;
				}
				System.out.println("Default network interface: " + interface_.getDisplayName());
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
			return new Color(32, 190, 66);
		} else if (value.toLowerCase().equals("blue")) {
			return Color.blue.darker();
		} else if (value.toLowerCase().equals("orange")) {
			return Color.orange.darker();
		} else if (value.toLowerCase().equals("yellow")) {
			return Color.yellow.darker();
		} else if (value.toLowerCase().indexOf("magenta") > -1) {
			return Color.magenta.darker();
		} else if (value.toLowerCase().indexOf("brown") > -1) {
			return new Color(107,92,61);
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
	
	public static void drawEnemyData(Graphics g, IMain m, Scene<Drawable> scene) {
		if (scene == null)
			return;
		ArrayList<Enemy> ens = scene.<Enemy>getObjectsByTypeAndParented(Enemy.class);
		int w = m.getWidth() - (int) (m.getWidth() * 0.16f) - 16;
		int gulf = 0;
		for (int i = 0; i < ens.size(); i++) {
			Enemy e = ens.get(i);
			if (e.isPersuingPlayer()) {
				g.setColor(MathCalculator.lerp(new Color(60,175,45), new Color(175,19,39), 1-e.getHealth()));
				g.fillRect(w - 100,25*gulf+10,(int)(100 * e.getHealth()),15);
				g.setColor(new Color(46,62,175));
				g.drawRect(w - 100,25*gulf+10,100,15);
				g.drawImage(e.getEnemyIcon(), w - 114,25*gulf+10,null);
				gulf++;
			}
		}
	}

	public static void drawMap(Graphics g, IMain m, Scene<Drawable> scene) {
		if (scene == null)
			return;
		
		int mapx = m.getWidth() - (int) (m.getWidth() * 0.16f) - 8;
		int mapy = (int) (m.getWidth() * 0.02f);
		int mapw2 = (int) (m.getWidth() * 0.14f);
		int mapw = mapw2 - 10;
		g.setColor(new Color(0, 0, 0, 90));
		g.drawRect(mapx + 2, mapy + 2, mapw2 - 1, mapw2 - 1);

		g.drawRect(mapx + 1, mapy + 1, mapw2 - 1, mapw2 - 1);
		g.setColor(new Color(113, 79, 55));
		g.fill3DRect(mapx, mapy, mapw2, mapw2, true);
		// g.setColor(new Color(84, 109, 58));
		// g.fillRect(mapx + 2, mapy + 2, mapw + 6, mapw + 6);
		if (mapw + 7 == 68) {
			g.drawImage(scene.getGamePlane().getGenWorld(), mapx + 2, mapy + 2,
					null);
		} else {
			g.drawImage(scene.getGamePlane().getGenWorld(), mapx + 2, mapy + 2,
					mapw + 6, mapw + 6, null);
		}

		g.setColor(new Color(26, 244, 52));
		ArrayList<Gem> gems = scene.<Gem> getObjectsByType(Gem.class);
		for (int i = 0; i < gems.size(); i++) {
			if (!gems.get(i).isVisible())
				continue;
			int entityx = (int) ((gems.get(i).getInstanceLoc().x + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((gems.get(i).getInstanceLoc().z + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		g.setColor(new Color(120,0,0));
		ArrayList<RedGem> redgems = scene.<RedGem> getObjectsByType(RedGem.class);
		for (int i = 0; i < redgems.size(); i++) {
			if (!redgems.get(i).isVisible())
				continue;
			int entityx = (int) ((redgems.get(i).getInstanceLoc().x + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((redgems.get(i).getInstanceLoc().z + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		g.setColor(new Color(144, 109, 74));
		ArrayList<Tree> trees = scene.<Tree> getObjectsByType(Tree.class);
		for (int i = 0; i < trees.size(); i++) {
			if (!trees.get(i).isVisible())
				continue;
			int entityx = (int) ((trees.get(i).getInstanceLoc().x + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((trees.get(i).getInstanceLoc().z + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		g.setColor(Color.orange);
		ArrayList<House> houses = scene.<House> getObjectsByType(House.class);
		for (int i = 0; i < houses.size(); i++) {
			if (!houses.get(i).isVisible())
				continue;
			int entityx = (int) ((houses.get(i).getInstanceLoc().x + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((houses.get(i).getInstanceLoc().z + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
		
		ArrayList<FishOil> oil = scene.<FishOil> getObjectsByType(FishOil.class);
		if (oil.size() == 1) {
			FishOil f = oil.get(0);
			if (f.isVisible()) {
				int entityx = (int) ((f.getInstanceLoc().x + scene
						.getWorldSizeHalf()) * mapw / scene.getWorldSize());
				int entityz = (int) ((f.getInstanceLoc().z + scene
						.getWorldSizeHalf()) * mapw / scene.getWorldSize());
				int sx = mapx + entityx + 5;
				int sy = mapy + entityz + 5;
				g.drawLine(sx - 1, sy, sx + 1, sy);
				g.drawLine(sx, sy - 1, sx, sy + 1);
			}
		}
		
		g.setColor(Color.red);
		ArrayList<Cassius> cassius = scene.<Cassius> getObjectsByType(Cassius.class);
		if (cassius.size() == 1) {
			Cassius f = cassius.get(0);
			if (f.isVisible()) {
				int entityx = (int) ((f.getInstanceLoc().x + scene
						.getWorldSizeHalf()) * mapw / scene.getWorldSize());
				int entityz = (int) ((f.getInstanceLoc().z + scene
						.getWorldSizeHalf()) * mapw / scene.getWorldSize());
				int sx = mapx + entityx + 5;
				int sy = mapy + entityz + 5;
				g.drawLine(sx - 1, sy, sx + 1, sy);
				g.drawLine(sx, sy - 1, sx, sy + 1);
			}
		}
			
		g.setColor(Color.red);
		ArrayList<Water> water = scene.<Water>getObjectsByType(Water.class); 
		for (int i = 0; i < water.size(); i++) {
			Water wat = water.get(i);
			if (!wat.isVisible())
				continue;
			int entityx = (int) ((wat.getInstanceLoc().x-(wat.getSequence()*wat.getSize()*0.5f) + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((wat.getInstanceLoc().z-(wat.getSequence()*wat.getSize()*0.5f) + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			int size = (int)(((wat.getSize()) + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize())/2;
			g.fillRect(sx,sy,size,size);
		}
		
		g.setColor(Color.white);
		g.fillRect(
				(int) ((scene.getPlayerX() + scene.getWorldSizeHalf()) * mapw / scene
						.getWorldSize()) + mapx + 4,
				mapy
						+ (int) ((scene.getPlayerZ() + scene.getWorldSizeHalf())
								* mapw / scene.getWorldSize()) + 4, 5, 5);
		g.setColor(GameState.instance.playerColor);
		g.fillRect(
				(int) ((scene.getPlayerX() + scene.getWorldSizeHalf()) * mapw / scene
						.getWorldSize()) + mapx + 5,
				mapy
						+ (int) ((scene.getPlayerZ() + scene.getWorldSizeHalf())
								* mapw / scene.getWorldSize()) + 5, 3, 3);
		
		ArrayList<Enemy> enemiesOfTheRepublic = scene.<Enemy>getObjectsByTypeAndParented(Enemy.class);
		g.setColor(Color.red);
		for (int i = 0; i < enemiesOfTheRepublic.size(); i++) {
			Enemy wat = enemiesOfTheRepublic.get(i);
			if (!wat.isVisible() || !wat.isPersuingPlayer())
				continue;
			int entityx = (int) ((wat.getInstanceLoc().x + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int entityz = (int) ((wat.getInstanceLoc().z + scene
					.getWorldSizeHalf()) * mapw / scene.getWorldSize());
			int sx = mapx + entityx + 5;
			int sy = mapy + entityz + 5;
			g.drawLine(sx - 1, sy, sx + 1, sy);
			g.drawLine(sx, sy - 1, sx, sy + 1);
		}
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
		// g.fillArc(15, 15, 45, 45, 0,
		// (int) (GameState.instance.health / 10.0f * 360.0f));
		// g.drawOval(15, 15, 45, 45);
		final int sides = 10;
		Polygon p24 = new Polygon();
		float tt2 = (float) ((Math.PI * 2 / sides));
		for (int i = 0; i < (int) (GameState.instance.health / 10.0f * sides); i++) {
			float x2 = (float) (MathCalculator.cos(tt2 * i) * 30);
			float y2 = (float) (MathCalculator.sin(tt2 * i) * 30);
			float x3 = (float) (MathCalculator.cos(tt2 * (1 + i)) * 30);
			float y3 = (float) (MathCalculator.sin(tt2 * (1 + i)) * 30);
			p24.addPoint((int) x2 + 40, (int) y2 + 40);
			p24.addPoint(40, 40);
			p24.addPoint((int) x3 + 40, (int) y3 + 40);
			g.fillPolygon(p24);
			p24.reset();
		}
		if (healthThing == null) {
			healthThing = new Polygon();
			float ee = (float) (Math.PI * 2 / sides);
			for (int i = 0; i < sides; i++) {
				float x2 = (float) (MathCalculator.cos(ee * i) * 30);
				float y2 = (float) (MathCalculator.sin(ee * i) * 30);
				healthThing.addPoint((int) x2 + 40, (int) y2 + 40);
			}
		}
		g.setColor(Color.black);
		g.drawPolygon(healthThing);
		
		g.setFont(new Font("Courier New", 0, 14));
		String health = MathCalculator.reduceDigits(GameState.instance.health, 1);
		g.drawString(health, 34, 42);
		g.setColor(Color.white);
		g.drawString(health, 33, 43);
		g.setFont(new Font("Courier New", 0, 20));
		g.drawString(GameState.instance.gems + "", 40, 86);
		g.drawString(GameState.instance.score + "", 40, 108);
		g.setColor(Color.green);
		g.fillPolygon(gem);
		g.setColor(new Color(0, 120, 0));
		g.drawPolygon(gem);
	}

	private static Polygon healthThing = null;

	public static Color adjustBrightness(Color base, int amount) {
		int dr = base.getRed() + amount;
		int dg = base.getGreen() + amount;
		int db = base.getBlue() + amount;
		return new Color(MathCalculator.colorLock(dr),
				MathCalculator.colorLock(dg), MathCalculator.colorLock(db));
	}

	private static ArrayList<String> evilTracker = new ArrayList<String>();
	private static Font messageFont = new Font("Courier New", 0, 12);

	public static void showDialog(String message, Color textColor, Graphics g, IMain mainInst) {
		if (evilTracker.indexOf(message) == -1) {
			// This is evil in the sense that it stores already shown messages.
			evilTracker.add(message);
			//SoundManager.playClick = true;
		}
		int alpha = 125;
		if (mainInst.getScreen(mainInst.getActiveScreen()) instanceof MainMenu) {
			alpha = 255;
		}
		if (message.indexOf("¢") > -1)
			message = message.replace("¢", "");
		g.setColor(new Color(185, 163, 124, alpha));
		g.fillRoundRect(10, mainInst.getHeight() - 125,
				mainInst.getWidth() - 26, 85, 8, 8);
		g.setColor(new Color(109, 88, 57,alpha));
		g.fillRoundRect(13, mainInst.getHeight() - 122,
				mainInst.getWidth() - 33, 78, 8, 8);
		g.setFont(messageFont);
		g.setColor(textColor);
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
		BufferedImage vignette = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) vignette.getGraphics();
		Point2D center = new Point2D.Float(width / 2, height / 2);
		float radius = (float) Math.sqrt(width * width + height * height);
		float[] dist = { 0.3f, 0.5f };
		Color[] colors = { new Color(0, 0, 0, 0),new Color(0, 0, 0, 120) };
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist,
				colors);
		g.setPaint(p);
		g.fillRect(0, 0, width, height);
		g.dispose();
		return vignette;
	}
}
