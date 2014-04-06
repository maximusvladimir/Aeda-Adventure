import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.swing.JFrame;
import javax.swing.Timer;

public class ThreadDebugger extends JFrame {
	public ThreadDebugger() {
		setTitle("Thread Debugger");
		setSize(450, 240);
		setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 280);
		setVisible(true);
		Timer schedule = new Timer(1500, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		schedule.start();
	}

	public void paint(Graphics g) {
		g.setFont(new Font("Arial", 0, 10));
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.green);
		ThreadInfo[] threads = ManagementFactory.getThreadMXBean()
				.getThreadInfo(
						ManagementFactory.getThreadMXBean().getAllThreadIds());
		g.drawString("Threads:", 8, 40);
		for (int i = 0; i < threads.length; i++) {
			ThreadInfo thread = threads[i];
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
		g.drawString("OS archetecture: " + System.getProperty("os.arch"), 215,51);
		g.drawString("OS name: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")", 215,62);
		g.drawString("Processors: " + operatingSystemMXBean.getAvailableProcessors(), 215,73);
		g.drawString("Game saves: " + GameState.saveNum, 215,84);
		if (GameState.FIXEDLOC != null)
			g.drawString("Player location: " + (int)GameState.FIXEDLOC.x + "," + (int)GameState.FIXEDLOC.y + "," + (int)GameState.FIXEDLOC.z, 215,95);
		else
			g.drawString("Player location: none", 215,95);
		g.drawString("Random numbers generated:" + Rand.getNumRandoms(), 215,106);
		g.drawString("Java heap:" + (Runtime.getRuntime().totalMemory()) + " bytes", 215,117);
	}
}
