import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MainApplet extends Applet implements IMain {
	public static boolean isApplet = false;
	private Screen active = null;
	private ArrayList<Screen> screens = new ArrayList<Screen>();
	private BufferedImage buffer;
	private boolean[] keys = new boolean[525];
	private boolean paused = false;
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void init() {
		setSize(512, 384);
		setVisible(true);
		isApplet = true;
		buffer = new BufferedImage(512, 384, BufferedImage.TYPE_INT_RGB);
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Timer repainter = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkFirstInvoke();
				repaint();
			}
		});
		repainter.start();

		moreInit();

		Timer timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkFirstInvoke();
				if (active != null && !active.isInConsoleMode()
						&& active.isActiveScreen() && !isPaused()) {
					if (active instanceof Level)
						((Level) active).silentTick();
					active.tick();
					if (active instanceof Level)
						((Level) active).postSilentTick();
				}
			}
		});
		timer.start();
		Timer keyFire = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkFirstInvoke();
				if (active != null && !active.isInConsoleMode()
						&& active.isActiveScreen() && !isPaused()) {
					for (int i = 0; i < keys.length; i++) {
						if (keys[i]) {
							active.keyDown(i);
						}
					}
				}
			}
		});
		keyFire.start();

		addScreen(new MainMenu(this));
		setActiveScreen(0);
	}

	private boolean start = false;

	private void checkFirstInvoke() {
		if (!start) {
			GameState.instance = new GameState();
			start = true;
			FileSave save = new FileSave();
			long timeout = System.currentTimeMillis();
			String data = "";
			while (System.currentTimeMillis() - timeout < 5000) {
				data = MainApplet.readData;
				if (data == null || !data.equals("EMPTY"))
					break;
			}
			if (data == null || data.equals("") || data.equals("EMPTY")) {
				GameState.instance.playerGUID = "Aeda";
				GameState.instance.playerLocation = new P3D(0, 0, 0);
			} else {
				//GameState.instance.playerGUID = "UNICAST";
				GameState.instance = save.loadApplet(data);
			}
		}
	}

	private void moreInit() {
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_F11) {
					JOptionPane.showMessageDialog(MainApplet.this,
							"Please execute runnable jar to use this feature.");
					if (active != null && !active.isInConsoleMode() && !isPaused())
						active.keyReleased(arg0);
				} else {
					if (active != null && !active.isInConsoleMode() && !isPaused())
						active.keyReleased(arg0);
				}
				keys[arg0.getKeyCode()] = false;
			}

			public void keyPressed(KeyEvent arg0) {
				if (active != null && !active.isInConsoleMode() && !isPaused())
					active.keyPressed(arg0);
				keys[arg0.getKeyCode()] = true;
			}

			public void keyTyped(KeyEvent arg0) {
				if (active != null)
					active.keyTyped(arg0);
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (active != null) {
					active.setMouse(arg0.getX(), arg0.getY());
					active.mouseClicked(arg0);
				}
			}

			public void mousePressed(MouseEvent arg0) {
				if (active != null) {
					active.setMouse(arg0.getX(), arg0.getY());
					active.mousePressed(arg0);
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				if (active != null) {
					active.setMouse(arg0.getX(), arg0.getY());
					active.mouseReleased(arg0);
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent arg0) {
				if (active != null) {
					active.setMouse(arg0.getX(), arg0.getY());
					active.mouseDragged(arg0);
				}
			}

			public void mouseMoved(MouseEvent arg0) {
				if (active != null) {
					active.setMouse(arg0.getX(), arg0.getY());
					active.mouseMoved(arg0);
				}
			}
		});
	}

	public void update(Graphics g) {
		paint(buffer.getGraphics());
		g.drawImage(buffer, 0, 0, this);
	}

	private int framesDrawn = 0;
	private long queryTime = 0;

	public void paint(Graphics g) {
		if (active != null) {
			if (framesDrawn == 0)
				queryTime = System.currentTimeMillis();
			active.draw(g);
			active.drawHUD(g);
			active.drawConsole(g);
			framesDrawn++;
			if (System.currentTimeMillis() - queryTime >= 1000) {
				FPS = framesDrawn;
				framesDrawn = 0;
			}
		}
	}

	public boolean isFullscreen() {
		// I know this is weird.
		return true;
	}

	public int getNumScreens() {
		return screens.size();
	}

	public boolean screenExists(String name) {
		for (int i = 0; i < screens.size(); i++) {
			if (screens.get(i).getName().toLowerCase()
					.equals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static String invokeAndWaitOnPulse() {
		GameState.save();
		return GameState.appletInstance;
	}

	public static String readData = "EMPTY";

	public static void pulsateSignature(String data) {
		readData = data;
	}

	public int getActiveScreen() {
		return activeIndex;
	}

	public void addScreen(Screen scr) {
		screens.add(scr);
	}

	public Screen getScreen(int index) {
		return screens.get(index);
	}

	public Screen getScreen(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < screens.size(); i++) {
			if (screens.get(i).getName().toLowerCase().equals(name)) {
				return screens.get(i);
			}
		}
		return null;
	}

	public void removeScreen(Screen screen) {
		screens.remove(screen);
	}

	private int activeIndex = -1;

	public void setActiveScreen(int index) {
		Screen s = screens.get(index);
		activeIndex = index;
		if (!s.isInited()) {
			s.internalInit();
		}
		active = s;
	}

	public void setActiveScreen(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < screens.size(); i++) {
			if (screens.get(i).getName().toLowerCase().equals(name)) {
				activeIndex = i;
				if (!screens.get(i).isInited())
					screens.get(i).internalInit();
				active = screens.get(i);
				return;
			}
		}
		new IllegalStateException("Unable to find screen:" + name);
	}

	private int FPS = 1;
	private int drawTime = 1;

	public int getFPS() {
		return FPS;
	}

	@Override
	public int getDrawTime() {
		return drawTime;
	}

}