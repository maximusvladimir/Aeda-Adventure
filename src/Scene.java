import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Scene<T> {
	private ArrayList<Drawable> objs = new ArrayList<Drawable>();
	private Color fogColor;
	private float fogStart;
	private float fogEnd;
	private float playerX;
	private float playerZ;
	private int darkness;
	private float walkSpeed = 19;
	private Rand rand;
	private float playerDelta;
	private Player player;
	private SceneTesselator scene;
	private GamePlane plane;
	private boolean canMove = true;
	private Screen screen;
	private Level level;
	private boolean portalize = true;
	private float playerY = 0;
	private boolean fogEnabled = true;
	public Scene(Level level, Rand rand) {
		playerX = GameState.instance.playerLocation.x;
		playerZ = GameState.instance.playerLocation.z;
		this.rand = rand;
		this.level = level;
		this.screen = level;
		rand.setScene(getThis());
		player = new Player(getThis());
		player.playerColor = GameState.instance.playerColor;
		playerDelta = GameState.instance.playerDelta;
		fogColor = Color.black;
		/*for (int i = 0; i < 10; i++) {
			int x = (int)(Math.cos(Math.PI * 2 / 10 * i) * 4) + 10;
			int z = (int)(Math.sin(Math.PI * 2 / 10 * i) * 4) + 10;
			plane.setHeightPoint(x,z , i * 200);
			System.out.println(x + "," + z + ":" + i * 200);
		}*/
		scene = new SceneTesselator();
		scene.addTesselator(player.getTesselator());
	}
	
	public boolean isFogEnabled() {
		return fogEnabled;
	}
	
	public void setFogState(boolean state) {
		fogEnabled = state;
	}
	
	public void deportal() {
		portalize = false;
	}
	
	public void reportal() {
		portalize = true;
	}
	
	public boolean canPortalize() {
		return portalize;
	}
	
	public void setPlane(GamePlane plane2) {
		plane = plane2;
		plane.genWorld();
		plane.setInstanceLoc(new P3D(0,-390,-500));
		ArrayList<GamePlane> gp = (this.<GamePlane>getObjectsByType(GamePlane.class));
		if (gp == null || gp.size() < 1)
			add(plane);
	}
	
	public Level getLevel() {
		return level;
	}
	
	public SceneTesselator useThisMethodSparsingly() {
		return scene;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@SuppressWarnings("unchecked")
	public <E> ArrayList<E> getObjectsByType(Class<E> klass) {
		ArrayList<E> builder = new ArrayList<E>();
		for (int i = 0; i < getSceneSize(); i++) {
			if (get(i).getClass().equals(klass)) {
				builder.add((E) get(i));
			}
		}
		return builder;
	}
	
	/**
	 * Gets all objects by the type, or if they inherit that type.
	 * @param klass The class type to search for.
	 * @return An arraylist containing the found elements.
	 */
	@SuppressWarnings("unchecked")
	public <E> ArrayList<E> getObjectsByTypeAndParented(Class<E> klass) {
		ArrayList<E> builder = new ArrayList<E>();
		for (int i = 0; i < getSceneSize(); i++) {
			if (get(i).getClass().equals(klass) || klass.isInstance(get(i))) {
				builder.add((E) get(i));
			}
		}
		return builder;
	}

	public Screen getScreen() {
		return screen;
	}
	
	public IMain getMain() {
		return screen.getMain();
	}
	
	@SuppressWarnings("unchecked")
	private Scene<Drawable> getThis(){
		return (Scene<Drawable>) this;
	}
	
	public void setPlayerMovable(boolean value) {
		canMove = value;
	}
	
	public boolean canPlayerMove() {
		return canMove;
	}
	
	public void setPlayerSpeed(float speed) {
		walkSpeed = speed;
	}
	
	public float getPlayerSpeed() {
		return walkSpeed;
	}
	
	public float getPlayerDelta() {
		return playerDelta;
	}
	
	public void setPlayerDelta(float t) {
		playerDelta = t;
		player.delta = t;
	}
	
	public float getWorldSize() {
		return plane.getWorldSize();
	}
	
	public float getWorldSizeHalf() {
		return plane.getWorldSizeHalf();
	}
	
	public int getWorldActual() {
		return plane.getActualSize();
	}
	
	private boolean moveOverride = false;
	public void setMoveOverride(boolean b) {
		moveOverride = b;
	}
	
	public boolean isMoveOverriden() {
		return moveOverride;
	}
	private Sound footstep;
	public void movePlayer(boolean forward) {
		float te = plane.getWorldSizeHalf();
		if ((footstep == null || footstep.isFinished()) && Math.random() < 0.76) {
			boolean cont = true;
			if (getScreen() instanceof IWaterLevel) {
				IWaterLevel lev = (IWaterLevel)getScreen();
				if (lev.inDeepWater() || lev.inWater()) {
					cont = false;
					footstep = new Sound("watersplash");
				}
			}
			if (cont) {
			if (Math.random() < 0.5)
				footstep = new Sound("footsteps2");
			else
				footstep = new Sound("footsteps3");
			}
			if (footstep != null)
				footstep.play();
		}
		if (forward) {
			playerX += walkSpeed * MathCalculator.cos(playerDelta);
			playerZ -= walkSpeed * MathCalculator.sin(playerDelta);
		} else {
			playerX -= walkSpeed * MathCalculator.cos(playerDelta) * 0.75f;
			playerZ += walkSpeed * MathCalculator.sin(playerDelta) * 0.75f;
		}
		if (playerX < -te + 500)
			playerX = -te + 500;
		if (playerZ < -te + 200)
			playerZ = -te + 200;
		if (playerX > te - 850)
			playerX = te - 850;
		if (playerZ > te - 310)
			playerZ = te - 310;
		player.delta = GameState.instance.playerDelta;
		playerDelta = GameState.instance.playerDelta;
		player.playerColor = GameState.instance.playerColor;
		player.moving = true;
		if (isMoveOverriden())
			return;
		GameState.instance.playerLocation = new P3D(playerX, 0, playerZ);
	}
	
	public void resize(int width, int height) {
		if (player == null)
			return;
		player.getTesselator().setSize(screen.getCompatabilityBuffer(),
				getMain().getWidth(), getMain().getHeight());
	}
	
	public float getPlayerX() {
		return playerX;
	}
	
	public float getPlayerZ() {
		return playerZ;
	}
	
	public float getPlayerY() {
		return playerY;
	}
	
	public void setPlayerY(float b) {
		playerY = b;
	}
	
	public void setPlayerX(float x) {
		playerX = x;
	}
	
	public void setPlayerZ(float z) {
		playerZ = z;
	}
	
	public void add(Drawable d) {
		objs.add(d);
		scene.addTesselator(d.getTesselator());
	}
	
	public void add(ArrayList<T> d) {
		for (int i = 0; i < d.size(); i++) {
			objs.add((Drawable)d.get(i));
			scene.addTesselator(((Drawable)d.get(i)).getTesselator());
		}
	}
	
	public void add(T[] d) {
		for (int i = 0; i < d.length; i++) {
			objs.add((Drawable)d[i]);
			scene.addTesselator(((Drawable)d[i]).getTesselator());
		}
	}
	
	public void remove(Drawable d) {
		objs.remove(d);
		scene.removeTesselator(d.getTesselator());
	}
	
	public void remove(int index) {
		Drawable d = objs.remove(index);
		scene.removeTesselator(d.getTesselator());
	}
	
	public Drawable get(int index) {
		return objs.get(index);
	}
	
	public int getSceneSize() {
		return objs.size();
	}
	
	public void setFog(float start, float end) {
		fogStart = start;
		fogEnd = end;
	}
	
	public float getFogStart() {
		return fogStart;
	}
	
	public float getFogEnd() {
		return fogEnd;
	}
	
	public void setFogColor(Color color) {
		fogColor = color;
	}
	
	public Color getFogColor() {
		return fogColor;
	}
	
	public void setSceneDarkness(int d) {
		darkness = d;
	}
	
	public int getSceneDarkness() {
		return darkness;
	}
	
	public P3D getPosition() {
		return new P3D(playerX,playerY,playerZ);
	}
	
	private float dist = 0.0f;
	public void tick() {
		
	}
	
	public GamePlane getGamePlane() {
		return plane;
	}
	private static final float FPI = (float)(4 / Math.PI);
	public void draw(Graphics g) {
		int total = getSceneSize()+1;
		int display = 1;
		int lightningAmount = 0;
		if (lightn > 0) {
			float am = (float)(Math.pow(10,lightn));
			lightningAmount = (int)(am * 5);
			lightn -= 0.005f;
			if (lightn < 0.4 && Math.random() < 0.02) {
				lightn = (float)(Math.random()*0.4f + 0.6f);
				new Sound("thunder").play();
			}
		}
		for (int i = 0; i < getSceneSize(); i++) {
			Drawable d = get(i);
			d.setPosition(new P3D(-playerX, 0, -playerZ));
			if (d.isVisible()) {
				if (d.isCullable()) {
					if (isVisible(d)) {
						display++;
						d.draw(getSceneDarkness()+d.getIndividualDarkness()-lightningAmount);
					}
				}
				else {
					display++;
					d.draw(getSceneDarkness()+d.getIndividualDarkness()-lightningAmount);
				}
			}
		}
		GameState.DISPLAYED3DOBJECTS = display;
		GameState.TOTAL3DOBJECTS = total;
		player.setPlayerDelta(playerDelta);
		GameState.FIXEDLOC = new P3D(-playerX, playerY, -playerZ);
		player.setPosition(new P3D(0, playerY, camDist));
		player.draw(getSceneDarkness()+player.getIndividualDarkness()-lightningAmount);
		
		if (!((getFogStart() == 0 && getFogEnd() == 0) || !fogEnabled))
			scene.fog(Utility.adjustBrightness(getFogColor(),lightningAmount*2), getFogStart(), getFogEnd());
		scene.setReverseFogEquation(true);
		scene.draw(g);
		numTriangles = scene.getNumAvaiableTriangles();
		skippedTriangles = scene.getNumOfLastSkippedTriangles();
	}

	public float getCameraDistance() {
		return camDist;
	}
	
	public void setCameraDistance(float v) {
		camDist = v;
	}
	
	private float camDist = -625;
	
	public void makeLightning() {
		lightn = 1;
		new Sound("thunder").play();
	}
	private float lightn = 0.0f;
	public static long numTriangles = 0;
	public static int skippedTriangles = 0;
	
	public float getTerrainHeight(float x, float z) {
		return plane.getLocation(x, z);
	}
	
	public boolean isVisible(Drawable d) {
		if (getFogStart() == 0 && getFogEnd() == 0)
			return true;
		if (d.getInstanceLoc().z > playerZ || d.getInstanceLoc().z + -getFogEnd() < playerZ)
			return false;
		if (d.getInstanceLoc().x - 1700 > playerX || d.getInstanceLoc().x + 1700 < playerX)
			return false;
		return true;
	}

	public void setPlayerPosition(P3D pos) {
		playerX = pos.x;
		playerZ = pos.z;
	}
}