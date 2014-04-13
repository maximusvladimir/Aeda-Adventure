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
	private float adder = 0;
	private long amount = 0;
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
		g.drawString("Number of Jedi Knights: " + amount, 215, 216);
		long sd = MainApplet.getNumFramesDrawn();
		if (sd == 0)
			sd = Main.getNumFramesDrawn();
		g.drawString("Number of frames drawn: " + sd, 215, 227);
		if (adder <= 0 && Math.random() < 0.01) {
			adder = (float)(Math.random() * 10)+15;
		}
		else {
			if (adder > 0) {
			float lastAdder = adder;
			adder = adder - 0.5f;
			if ((int)adder != (int)lastAdder)
				amount++;
			}
		}
		g2.drawImage(b, 0, 0, null);
	}
}