import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class Network {
	public static boolean RUNNING = false;
	private static GameState myGameState;
	private static GameState otherGameState;
	private static boolean connectionEstablished = false;
	private static final String group = "225.4.5.6";
	private static final int port = 5201;
	private static final long clientId = (long) (Long.MAX_VALUE * Math.random());

	public static void start() {
		if (RUNNING)
			return;
		RUNNING = true;
		connectionEstablished = false;

		Thread multiplayerThread = new Thread(new Runnable() {
			public void run() {
				workDataSend();
			}
		});
		multiplayerThread.setDaemon(false);
		multiplayerThread.setName("MULTIPLAYERSEND0");
		multiplayerThread.start();
		multiplayerThread.setPriority(Thread.MIN_PRIORITY);
		Thread multiplayerThread2 = new Thread(new Runnable() {
			public void run() {
				workDataRecieve();
			}
		});
		multiplayerThread2.setDaemon(false);
		multiplayerThread2.setName("MULTIPLAYERCHECK0");
		multiplayerThread2.start();
		multiplayerThread2.setPriority(Thread.MIN_PRIORITY);
	}

	public static boolean createLevelIsOkay = false;
	private static MulticastSocket internalSocket;
	private static String MCAST_ADDR = "225.0.0.1";
	private static int DEST_PORT = 4620;
	private static int doLastSend = 0;

	private static void workDataSend() {
		try {
			DatagramSocket socket = new DatagramSocket();
			String welcomeMessage = "Hello from "
					+ new FileSave().load().playerGUID;
			byte[] b = welcomeMessage.getBytes();
			DatagramPacket dgram;
			dgram = new DatagramPacket(b, b.length,
					InetAddress.getByName(MCAST_ADDR), DEST_PORT);
			FileSave save = new FileSave();
			while (RUNNING) {
				StringBuilder builder = new StringBuilder();
				if (myGameState != null) {
					save.save(myGameState, builder);
				}
				else {
					builder.append("HELLO");
				}
				dgram.setData(send(builder.toString()));
				socket.send(dgram);
				//Thread.sleep(100);
			}
			socket.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void workDataRecieve() {
		try {
			byte[] b = new byte[1024];
			FileSave save = new FileSave();
			DatagramPacket dgram = new DatagramPacket(b, b.length);
			MulticastSocket socket = new MulticastSocket(DEST_PORT);
			// socket.setLoopbackMode(true);
			//socket.connect(InetAddress.getByName(MCAST_ADDR), DEST_PORT+1);
			//socket.bind(new InetSocketAddress(MCAST_ADDR,DEST_PORT));
			NetworkInterface intf = Utility.getDefaultNetworkAdapter();
			System.out.println(intf.getName());
			socket.joinGroup(new InetSocketAddress(MCAST_ADDR,DEST_PORT),intf);
			while (RUNNING) {
				socket.receive(dgram);
				Object[] data = get(b);
				String msg = (String)data[1];
				if ((long)data[0] != clientId) {
					createLevelIsOkay = true;
					BufferedReader br = new BufferedReader(new StringReader(msg));
					try {
						otherGameState = save.read(br);
					}
					catch (Throwable t) {
						//System.out.println("Couldn't parse: " + msg + " msg=" +t.getMessage());
						//t.printStackTrace();
					}
				}
				dgram.setLength(b.length);
			}
			socket.close();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static byte[] send(String data) {
		byte[] buf2 = (clientId + "%" + data).getBytes();
		try {
			// return new DatagramPacket(buf2, buf2.length,
			// InetAddress.getByName(group), port);
			return buf2;
		} catch (Throwable t) {
			return null;
		}
	}

	private static Object[] get(byte[] buf) {
		// try {
		/*
		 * byte buf[] = new byte[1024]; DatagramPacket pack = new
		 * DatagramPacket(buf, buf.length); s.receive(pack);
		 */
		String data = new String(buf);
		long id = Long.parseLong(data.substring(0, data.indexOf("%")));
		data = data.substring(data.indexOf("%") + 1);
		return new Object[] { id, data.trim()};
		// return new Object[] { id, data, pack.getLength() };
		/*
		 * } catch (SocketTimeoutException ste) {
		 * System.out.println("Socket time out."); return null; } catch
		 * (Throwable t) { t.printStackTrace(); return null; }
		 */
	}

	public static void pushPlayerInfo(GameState gs) {
		myGameState = gs;
	}

	public static GameState getPlayerGameState() {
		return otherGameState;
	}

	public static boolean isOtherPlayerReady() {
		return false;
	}
}
