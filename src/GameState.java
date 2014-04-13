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
	public Color playerColor = Color.green.darker();
	public static P3D ORIGINS = new P3D(0,0,0);
	public int healthPieces = 10;
	public long timePlayed = 0;
	
	public boolean talkedToGrandmaFiace = false;
	public boolean hasMoonstone = false;
	public boolean hasRaft = false;
	public boolean hasLantern = false;
	public boolean hasFishOil = false;
	public boolean hasSword = false;
	
	public String toString() {
		return  "playerStage     = " + playerStage + "\n" +
				"playerLocation = " + playerLocation + "\n" + 
				"score          = " + score + "\n" + 
				"health         = " + health + "\n" + 
				"playerGUID     = " + playerGUID + "\n" +
				"playerDelta    = " + playerDelta+ "\n"+
				"gems           = " + gems + "\n" +
				"playerColor    = " + playerColor + "\n" +
				"grandFiace     = " + talkedToGrandmaFiace + "\n" +
				"healthPieces   = " + healthPieces + "\n" +
				"hasMoonstone   = " + hasMoonstone + "\n" +
				"hasRaft        = " + hasRaft + "\n" +
				"hasLantern     = " + hasLantern + "\n" +
				"timePlayed     = " + timePlayed + "\n" + 
				"hasFishOil     = " + hasFishOil + "\n" +
				"hasSword       = " + hasSword;
	}
	public static GameState instance;
	private static FileSave saver = new FileSave();
	public static long saveNum = 0;
	public static P3D FIXEDLOC = new P3D(0,0,0);
	public static boolean doVignette = false;
	public static int DISPLAYED3DOBJECTS = 0;
	public static int TOTAL3DOBJECTS = 0;
	public static int DTIME = 0;
	public static String appletInstance = null;
	public static final boolean DEBUGMODE = true;
	public static void save() {
		if (instance == null || Network.RUNNING)
			return;
		if (MainApplet.isApplet) {
			appletInstance = saver.saveApplet(instance);
			return;
		}
		saver.save(instance);
		saveNum++;
	}
}
