import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ThreadDebugger extends JFrame {
	private static final long serialVersionUID = 2217557311939282837L;

	private long spinTime = System.currentTimeMillis();
	public ThreadDebugger() {
		setTitle("Thread Debugger");
		setSize(450, 240);
		try {
			setIconImage(ImageIO.read(Main.class.getResource("icon.png")));
		} catch (Throwable t) {

		}
		// setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height -
		// 280);
		setUndecorated(true);
		setLocation(0, 0);
		setVisible(true);
		Timer schedule = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		schedule.start();
		JPanel panel = new JPanel();
		panel.setSize(getWidth(), 30);
		add(panel, BorderLayout.NORTH);
		new ComponentMover(this, panel);
		MouseAdapter st = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getX() > getWidth() - 50 && me.getX() < getWidth()
						&& me.getY() <= 30) {
					setVisible(false);
					dispose();
				}
			}
		};
		addMouseListener(st);
		panel.addMouseListener(st);
		MouseMotionAdapter yr = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent me) {
				boolean prevState = mouseOver;
				if (me.getX() > getWidth() - 50 && me.getY() <= 30) {
					mouseOver = true;
				}
				else {
					mouseOver = false;
				}
				if (prevState != mouseOver)
					repaint();
			}
		};
		addMouseMotionListener(yr);
		panel.addMouseMotionListener(yr);
	}
	boolean mouseOver = false;
	public void paint(Graphics g2) {
		BufferedImage b = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = b.getGraphics();
		g.setFont(new Font("Arial", 0, 10));
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		//Graphics2D g3 = (Graphics2D)g;
		//g3.setPaint(new LinearGradientPaint(0,0,0,30,new float[]{0.0f,1.0f},new Color[]{new Color(70,70,70),new Color(60,60,60)}));
		g.setColor(Color.darkGray);
		g.fillRect(0,0,getWidth(),30);
		//g3.fillRect(0, 0, getWidth(), 30);

		if (!mouseOver)
			g.setColor(new Color(190, 45, 45));
		else
			g.setColor(new Color(190, 45, 45).darker());
		g.fillRect(getWidth() - 50, 0, 50, 30);
		g.setColor(Color.white);
		g.drawString("x", getWidth() - 30, 18);

		String str = "Aeda Adventure Debugger";
		int le = g.getFontMetrics().stringWidth(str);

		g.drawString(str, (getWidth() - 30) / 2 - (le / 2), 18);

		ThreadInfo[] threads = ManagementFactory.getThreadMXBean()
				.getThreadInfo(
						ManagementFactory.getThreadMXBean().getAllThreadIds());
		int num = ManagementFactory.getThreadMXBean().getDaemonThreadCount();
		g.setColor(Color.green);
		g.drawString("Threads ("+num+"):", 8, 40);
		for (int i = 0; i < threads.length; i++) {
			ThreadInfo thread = threads[i];
			if (thread == null) // Very rare bug.
				continue;
			if (Utility.isStringAllUpper(thread.getThreadName())) {
				g.setColor(Color.red);
			}
			else {
				g.setColor(Color.green);
			}
			g.drawString(
					thread.getThreadName()
							+ " "
							+ ManagementFactory.getThreadMXBean()
									.getThreadUserTime(thread.getThreadId()),
					8, i * 11 + 51);
		}
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
				.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass()
				.getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get")
					&& Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				if (method.getName().equals("getProcessCpuLoad")) {
					g.drawString("CPU Usage: " + (float) ((double) value * 100)
							+ "%", 215, 40);
				}
				// System.out.println(method.getName() + " = " + value);
			} // if
		}
		g.drawString("OS archetecture: " + System.getProperty("os.arch"), 215,
				51);
		g.drawString("OS name: " + System.getProperty("os.name") + " ("
				+ System.getProperty("os.version") + ")", 215, 62);
		g.drawString(
				"Processors: " + operatingSystemMXBean.getAvailableProcessors(),
				215, 73);
		g.drawString("Game saves: " + GameState.saveNum, 215, 84);
		if (GameState.FIXEDLOC != null)
			g.drawString("Player location: " + (int) GameState.FIXEDLOC.x + ","
					+ (int) GameState.FIXEDLOC.y + ","
					+ (int) GameState.FIXEDLOC.z, 215, 95);
		else
			g.drawString("Player location: none", 215, 95);
		g.drawString("Random numbers generated:" + Rand.getNumRandoms(), 215,
				106);
		g.drawString("Java heap:" + (Runtime.getRuntime().totalMemory())
				+ " bytes", 215, 117);
		
		// super rare bug.
		if (GameState.instance != null)
		g.drawString("Player delta:"
				+ ((int) (GameState.instance.playerDelta * 57.295)) % 360, 215,
				128);
		g.drawString("Total 3D objects:" + GameState.TOTAL3DOBJECTS, 215, 139);
		g.drawString("Rendered 3D objects:" + GameState.DISPLAYED3DOBJECTS, 215, 150);
		if (GameState.DISPLAYED3DOBJECTS > 0)
			g.drawString("Per-object draw time: " + ((float)GameState.DTIME/(float)GameState.DISPLAYED3DOBJECTS), 215,161);
		g.drawString("Num. of possible triangles:" + Scene.numTriangles,215,172);
		g.drawString("Num. of skipped triangles:" + Scene.skippedTriangles, 215, 183);
		long millis = (System.currentTimeMillis() - spinTime);
		g.drawString("Game runtime:" + millis + " (seconds: " + (millis/1000) + ")", 215, 194);
		g.drawString("Total distance overhead:" + (P3D.totalDistanceOverhead/1000000) + " ms", 215, 205);
		g2.drawImage(b, 0, 0, null);
	}
}

class ComponentMover extends MouseAdapter {
	private Class<?> destinationClass;
	private Component destinationComponent;
	private Component destination;
	private Component source;
	private boolean changeCursor = true;
	private Point pressed;
	private Point location;
	private Cursor originalCursor;
	private boolean autoscrolls;
	private Insets dragInsets = new Insets(0, 0, 0, 0);
	private Dimension snapSize = new Dimension(1, 1);
	
	public ComponentMover() {
	}
	
	public ComponentMover(Class<?> destinationClass, Component... components) {
		this.destinationClass = destinationClass;
		registerComponent(components);
	}
	
	public ComponentMover(Component destinationComponent,
			Component... components) {
		this.destinationComponent = destinationComponent;
		registerComponent(components);
	}

	public boolean isChangeCursor() {
		return changeCursor;
	}

	public void setChangeCursor(boolean changeCursor) {
		this.changeCursor = changeCursor;
	}

	public Insets getDragInsets() {
		return dragInsets;
	}

	public void setDragInsets(Insets dragInsets) {
		this.dragInsets = dragInsets;
	}

	public void deregisterComponent(Component... components) {
		for (Component component : components)
			component.removeMouseListener(this);
	}

	public void registerComponent(Component... components) {
		for (Component component : components)
			component.addMouseListener(this);
	}

	public Dimension getSnapSize() {
		return snapSize;
	}

	public void setSnapSize(Dimension snapSize) {
		this.snapSize = snapSize;
	}

	public void mousePressed(MouseEvent e) {
		source = e.getComponent();
		int width = source.getSize().width - dragInsets.left - dragInsets.right;
		int height = source.getSize().height - dragInsets.top
				- dragInsets.bottom;
		Rectangle r = new Rectangle(dragInsets.left, dragInsets.top, width,
				height);

		if (r.contains(e.getPoint()))
			setupForDragging(e);
	}

	private void setupForDragging(MouseEvent e) {
		source.addMouseMotionListener(this);
		if (destinationComponent != null) {
			destination = destinationComponent;
		} else if (destinationClass == null) {
			destination = source;
		} else
		{
			destination = SwingUtilities.getAncestorOfClass(destinationClass,
					source);
		}

		pressed = e.getLocationOnScreen();
		location = destination.getLocation();

		if (changeCursor) {
			originalCursor = source.getCursor();
			source.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
		if (destination instanceof JComponent) {
			JComponent jc = (JComponent) destination;
			autoscrolls = jc.getAutoscrolls();
			jc.setAutoscrolls(false);
		}
	}

	public void mouseDragged(MouseEvent e) {
		Point dragged = e.getLocationOnScreen();
		int dragX = getDragDistance(dragged.x, pressed.x, snapSize.width);
		int dragY = getDragDistance(dragged.y, pressed.y, snapSize.height);
		destination.setLocation(location.x + dragX, location.y + dragY);
	}

	private int getDragDistance(int larger, int smaller, int snapSize) {
		int halfway = snapSize / 2;
		int drag = larger - smaller;
		drag += (drag < 0) ? -halfway : halfway;
		drag = (drag / snapSize) * snapSize;

		return drag;
	}

	public void mouseReleased(MouseEvent e) {
		source.removeMouseMotionListener(this);

		if (changeCursor)
			source.setCursor(originalCursor);

		if (destination instanceof JComponent) {
			((JComponent) destination).setAutoscrolls(autoscrolls);
		}
	}
}
