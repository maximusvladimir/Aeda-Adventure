import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.ImageCapabilities;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JFrame implements IMain {
	private VolatileImage vRAMBuffer;
	private Thread updateThread;
	private long timeSinceUpdate = 0;
	private int framesDrawn = 0;
	private int FPS = 0;
	private boolean running = true;
	private boolean fullscreen = false;
	private boolean justEnteredFullscreen = false;
	private boolean painting = false;
	private BufferedImage buffer;
	private ArrayList<Screen> screens;
	private Screen active = null;
	private boolean tickSync = false;
	private long recordedSaves;
	private int drawTime;
	private long keyTicks = 0;
	private boolean[] keys = new boolean[525];
	private ControllerSupport cnt;
	public static boolean antialias = false;
	public static float blurAmount = 0;
	public static float redAmount = 0;
	private int activeIndex = -1;
	private boolean paused = false;
	private static float[] cel;

	public static void main(String[] args) {
		new Main();
		System.out.println("Shutting down.");
		Network.RUNNING = false;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public Main() {
		new ThreadDebugger();
		Utility.startInterfaceLookup();
		setSize(512, 384);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation(w / 2 - getWidth() / 2, h / 2 - getHeight() / 2);
		try {
			setIconImage(ImageIO.read(Main.class.getResource("icon.png")));
		} catch (Throwable t) {

		}
		setVisible(true);
		setResizable(false);
		setTitle("Aeda Adventure");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buffer = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		screens = new ArrayList<Screen>();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_F11) {
					if (fullscreen)
						leaveFullscreen();
					else
						goFullscreen();
					if (active != null && !active.isInConsoleMode()
							&& !isPaused())
						active.keyReleased(arg0);
				} else {
					if (active != null && !active.isInConsoleMode()
							&& !isPaused())
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
				if (arg0.getKeyChar() == '`')
					antialias = !antialias;
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
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				buffer = new BufferedImage(getWidth(), getHeight(),
						BufferedImage.TYPE_INT_RGB);
				justEnteredFullscreen = true;
				if (active != null) {
					active.resize(getWidth(), getHeight());
				}
			}
		});
		addScreen(new MainMenu(this));
		setActiveScreen("mainmenu");
		Timer timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (active != null && active.isActiveScreen()
						&& !active.isInConsoleMode() && !isPaused()) {
					if (active instanceof Level)
						((Level) active).silentTick();
					active.tick();
					if (active instanceof Level)
						((Level) active).postSilentTick();
				}
			}
		});
		timer.start();
		cnt = new ControllerSupport();
		if (cnt.isAvaliable())
			System.out.println("Valid controller found.");
		Timer keyFire = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (active != null && active.isActiveScreen()
						&& !active.isInConsoleMode() && !isPaused()) {
					keyTicks++;
					for (int i = 0; i < keys.length; i++) {
						if (keys[i]) {
							active.keyDown(i);
						}
					}
					if (cnt.isAvaliable()) {
						cnt.update();
						active.controllerUpdate(cnt);
					}
				}
			}
		});
		keyFire.start();
		/*
		 * Thread tickThread = new Thread(new Runnable() { public void run() {
		 * while (true) { while (!tickSync) {
		 * 
		 * } if (active != null) active.tick(); tickSync = false; } } });
		 * tickThread.start();
		 */
		Timer saveSchedule = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread saveThread = new Thread(new Runnable() {
					public void run() {
						GameState.save();
					}
				});
				recordedSaves++;
				saveThread.setName("GAMESTATESAVEINST" + recordedSaves);
				saveThread.start();
			}
		});
		saveSchedule.start();
		initThreads();
	}

	private void initThreads() {
		updateThread = new Thread(new Runnable() {
			public void run() {
				updater();
			}
		});
		updateThread.setDaemon(true);
		updateThread.setName("3DRENDERANDUPDATE");
		updateThread.setPriority(Thread.MAX_PRIORITY);
		updateThread.start();
		updateThread.setPriority(Thread.MAX_PRIORITY);
	}

	private void updater() {
		while (running) {
			if (vRAMBuffer == null || vRAMBuffer.contentsLost()
					|| justEnteredFullscreen) {
				try {
					vRAMBuffer = createVolatileImage(getWidth(), getHeight(),
							new ImageCapabilities(true));
					vRAMBuffer.setAccelerationPriority(1);
					System.out.println("Acquired video accelerated buffer.");
				} catch (AWTException e) {
					vRAMBuffer = createVolatileImage(getWidth(), getHeight());
					System.out
							.println("Failed to acquire video accelerated buffer.");
				}
				justEnteredFullscreen = false;
			}
			Graphics internalGraphics = vRAMBuffer.createGraphics();
			while (!vRAMBuffer.contentsLost() && !justEnteredFullscreen) {
				while (painting) {

				}
				if (vRAMBuffer.validate(getGraphicsConfiguration()) != VolatileImage.IMAGE_INCOMPATIBLE) {
					framesDrawn++;
					if (System.currentTimeMillis() - timeSinceUpdate >= 1000) {
						setFPS(framesDrawn);
						framesDrawn = 0;
						timeSinceUpdate = System.currentTimeMillis();
					}
					long qSt = System.currentTimeMillis();
					Graphics2D bufferedGraphics = (Graphics2D) buffer
							.getGraphics();
					if (antialias) {
						bufferedGraphics.setRenderingHint(
								RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
						bufferedGraphics.setRenderingHint(
								RenderingHints.KEY_TEXT_ANTIALIASING,
								RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					}
					draw(bufferedGraphics);
					if (antialias) {
						bufferedGraphics.setRenderingHint(
								RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_OFF);
						bufferedGraphics.setRenderingHint(
								RenderingHints.KEY_TEXT_ANTIALIASING,
								RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
					}
					bufferedGraphics.dispose();

					if (blurAmount > 0.001) {
						GaussianFilter filter = new GaussianFilter(blurAmount);
						BufferedImage dest = new BufferedImage(
								buffer.getWidth(), buffer.getHeight(),
								BufferedImage.TYPE_INT_RGB);
						filter.filter(buffer, dest);
						internalGraphics.drawImage(dest, 0, 0, null);
					} else
						internalGraphics.drawImage(buffer, 0, 0, null);

					if (framesDrawn == 2) {
						drawTime = (int) (System.currentTimeMillis() - qSt);
						GameState.DTIME = drawTime;
					}
					drawHUD(internalGraphics);
					/*
					 * Graphics2D sn = (Graphics2D)internalGraphics; int s =
					 * framesDrawn * 3 + 50; sn.setColor(Color.black);
					 * sn.fillRect(0, 0, s, s); sn.setColor(Color.red);
					 * sn.setStroke(new BasicStroke(s/64.0f));
					 * sn.drawLine((int)(s * 0.5f/8.0f),(int)(s *
					 * 2.0f/8.0f),(int)(s * 3.5f/8.0f),(int)(s * 7.5f/8.0f));
					 * sn.drawLine((int)(s * 0.5f/8.0f),(int)(s *
					 * 2.0f/8.0f),(int)(s * 3.5f/8.0f),(int)(s * 0.5f/8.0f));
					 * sn.drawLine((int)(s * 3.5f/8.0f),(int)(s *
					 * 0.5f/8.0f),(int)(s * 6.5f/8.0f),(int)(s * 6.0f/8.0f));
					 * sn.drawLine((int)(s * 6.5f/8.0f),(int)(s *
					 * 6.0f/8.0f),(int)(s * 3.5f/8.0f),(int)(s * 7.5f/8.0f));
					 * 
					 * //left arrow: sn.drawLine((int)(s * 1.5f/8.0f),(int)(s *
					 * 7.0f/8.0f),(int)(s * 2.0f/8.0f),(int)(s * 7.5f/8.0f));
					 * sn.drawLine((int)(s * 1.5f/8.0f),(int)(s *
					 * 7.0f/8.0f),(int)(s * 1.5f/8.0f),(int)(s * 6.5f/8.0f));
					 * sn.drawLine((int)(s * 1.0f/8.0f), (int)(s * 6.5f/8.0f),
					 * (int)(s * 2.0f/8.0f), (int)(s * 6.5f/8.0f));
					 * sn.drawLine((int)(s * 1.0f/8.0f), (int)(s * 6.5f/8.0f),
					 * (int)(s * 1.5f/8.0f), (int)(s * 6.0f/8.0f));
					 * sn.drawLine((int)(s * 2.0f/8.0f), (int)(s * 6.5f/8.0f),
					 * (int)(s * 1.5f/8.0f), (int)(s * 6.0f/8.0f));
					 * 
					 * //right arrow: sn.drawLine((int)(s * 7.0f/8.0f), (int)(s
					 * * 4.5f/8.0f), (int)(s * 6.5f/8.0f),(int)(s * 4.0f/8.0f));
					 * sn.drawLine((int)(s * 7.0f/8.0f), (int)(s *
					 * 4.5f/8.0f),(int)(s * 7.0f/8.0f),(int)(s * 5.0f/8.0f));
					 * sn.drawLine((int)(s * 6.5f/8.0f), (int)(s * 5.0f/8.0f),
					 * (int)(s * 7.5f/8.0f), (int)(s * 5.0f/8.0f));
					 * sn.drawLine((int)(s * 6.5f/8.0f), (int)(s *
					 * 5.0f/8.0f),(int)(s * 7.0f/8.0f),(int)(s * 5.5f/8.0f));
					 * sn.drawLine((int)(s * 7.5f/8.0f), (int)(s *
					 * 5.0f/8.0f),(int)(s * 7.0f/8.0f),(int)(s * 5.5f/8.0f));
					 */
					painting = true;
					repaint();
				}
			}
			System.err.println("Buffer lost! Attempting to reaquire.");
		}
	}

	public boolean isFullscreen() {
		return fullscreen;
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

	public int getActiveScreen() {
		return activeIndex;
	}

	public void removeScreen(Screen screen) {
		screens.remove(screen);
	}

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
				if (!screens.get(i).isInited()) {
					screens.get(i).internalInit();
				}
				if (SoundManager.soundEnabled) {
					if (SoundManager.backgroundSound != null)
						SoundManager.backgroundSound.halt();
					if (screens.get(i) instanceof SailorHarbour)
						SoundManager.backgroundSound = new Sound("harbour");
					if (screens.get(i) instanceof InsideHouse)
						SoundManager.backgroundSound = new Sound("piano");
					if (screens.get(i) instanceof HolmVillage)
						SoundManager.backgroundSound = new Sound("holm");
					if (screens.get(i) instanceof MainMenu)
						SoundManager.backgroundSound = new Sound("title");
					if (SoundManager.backgroundSound != null) {
						SoundManager.backgroundSound.setLooping(true);
						SoundManager.backgroundSound.play();
					}
				}
				active = screens.get(i);
				return;
			}
		}
		new IllegalStateException("Unable to find screen:" + name);
	}

	public void draw(Graphics g) {
		if (active != null && active.isActiveScreen()) {
			active.draw(g);
		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (redAmount > 0.0001f) {
			g.setColor(new Color(254, 219, 18, (int) (redAmount * 150)));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void drawHUD(Graphics g) {
		if (active != null && active.isActiveScreen()) {
			active.drawHUD(g);
			active.drawConsole(g);
		}
	}

	public void paint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		if (fullscreen)
			g.drawImage(vRAMBuffer, 0, 0, this);
		else {
			g.drawImage(vRAMBuffer, 3, 25, this);
			// g.drawImage(vRAMBuffer, 3, 25, getWidth(), getHeight()/2, this);
		}
		painting = false;
	}

	public void goFullscreen() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		int w = -1;
		int h = -1;
		GraphicsDevice device = ge.getScreenDevices()[0];
		for (int i = 0; i < ge.getScreenDevices().length; i++) {
			GraphicsDevice d = ge.getScreenDevices()[i];
			if (d.getDisplayMode().getWidth() > w
					|| d.getDisplayMode().getHeight() > h) {
				device = d;
				w = d.getDisplayMode().getWidth();
				h = d.getDisplayMode().getHeight();
			}
		}
		if (device.isFullScreenSupported()) {
			setVisible(false);
			dispose();
			setUndecorated(true);
			setVisible(true);
			device.setFullScreenWindow(this);
			/*
			 * for (int i = 0; i < device.getDisplayModes().length; i++) {
			 * DisplayMode m = device.getDisplayModes()[i];
			 * System.out.println(m.getWidth() + "," + m.getHeight() + "," +
			 * m.getBitDepth() + "," + m.getRefreshRate()); } System.exit(0);
			 */
			device.setDisplayMode(new DisplayMode(512, 384, 32, 60));
			// device.setDisplayMode(new DisplayMode(1280,768,32,60));
			justEnteredFullscreen = true;
			fullscreen = true;
		} else
			System.out.println("Sorry, fullscreen is not supported.");
	}

	public void leaveFullscreen() {
		if (fullscreen) {
			setVisible(false);
			dispose();
			setUndecorated(false);
			setVisible(true);
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice device = ge.getScreenDevices()[0];
			device.setFullScreenWindow(null);
			fullscreen = false;
		}
	}

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}

	public int getDrawTime() {
		return drawTime;
	}
}