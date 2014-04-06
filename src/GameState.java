import java.awt.Color;


public class GameState {
	public int playerStage = 0;
	public int playerLevel = 0;
	public P3D playerLocation = new P3D(0,0,0);
	public int score = 0;
	public float health = 10;
	public String playerGUID = "Aeda";
	public int gems = 0;
	public float playerDelta = 0.0f;
	public Color playerColor = Color.blue;
	public String toString() {
		return "playerStage = " + playerStage + "\n" +
				"playerLocation = " + playerLocation + "\n" + 
				"score = " + score + "\n" + 
				"health = " + health + "\n" + 
				"playerGUID = " + playerGUID + "\n" +
				"playerDelta = "+playerDelta+ "\n"+
				"gems = " + gems + "\n" +
				"playerColor = " + playerColor;
	}
	public static GameState instance;
	private static FileSave saver = new FileSave();
	public static long saveNum = 0;
	public static P3D FIXEDLOC = new P3D(0,0,0);
	public static boolean doVignette = false;
	public static int DISPLAYED3DOBJECTS = 0;
	public static int TOTAL3DOBJECTS = 0;
	public static int DTIME = 0;
	public static void save() {
		if (instance == null || Network.RUNNING || MainApplet.isApplet)
			return;
		saver.save(instance);
		saveNum++;
	}
}
