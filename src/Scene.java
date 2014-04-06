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
	private float playerDestX = 0.0f;
	private float playerDestZ = 0.0f;
	public Scene(Screen screen, Rand rand,int levelsize,Color baseTerrainColor, int colorVariance, float colorMix) {
		playerX = GameState.instance.playerLocation.x;
		playerZ = GameState.instance.playerLocation.z;
		this.rand = rand;
		this.screen = screen;
		rand.setScene(getThis());
		player = new Player(getThis());
		player.playerColor = GameState.instance.playerColor;
		playerDelta = GameState.instance.playerDelta;
		plane = new GamePlane(getThis(),rand,levelsize,baseTerrainColor,colorVariance,colorMix);
		plane.genWorld();
		plane.setInstanceLoc(new P3D(0,-390,-500));
		scene = new SceneTesselator();
		scene.addTesselator(player.getTesselator());
		add(plane);
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
	
	public Screen getScreen() {
		return screen;
	}
	
	public Main getMain() {
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
	
	public void movePlayer(boolean forward) {
		float te = plane.getWorldSizeHalf();
		if (!SoundManager.playFootstep1 && !SoundManager.playFootstep2) {
			if (rand.nextDouble() < 0.5)
				SoundManager.playFootstep1 = true;
			else
				SoundManager.playFootstep2 = true;
		}
		if (forward) {
			playerX += walkSpeed * Math.cos(playerDelta);
			playerZ -= walkSpeed * Math.sin(playerDelta);
		} else {
			playerX -= walkSpeed * Math.cos(playerDelta) * 0.75f;
			playerZ += walkSpeed * Math.sin(playerDelta) * 0.75f;
		}
		if (playerX < -te + 500)
			playerX = -te + 500;
		if (playerZ < -te + 200)
			playerZ = -te + 200;
		if (playerX > te - 850)
			playerX = te - 850;
		if (playerZ > te - 310)
			playerZ = te - 310;
		GameState.instance.playerLocation = new P3D(playerX, 0, playerZ);
		player.delta = GameState.instance.playerDelta;
		playerDelta = GameState.instance.playerDelta;
		player.playerColor = GameState.instance.playerColor;
		player.moving = true;
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
	
	public void add(Drawable d) {
		objs.add(d);
		scene.addTesselator(d.getTesselator());
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
	
	float dist = 0.0f;
	public void tick() {
		if (playerDestX != 0.0f || playerDestZ != 0.0f) {
			float lx = playerDestX - playerX;
			float lz = playerDestZ - playerZ;
			float length = (float)(Math.sqrt(lx * lx + lz * lz));
			float unitPDX = lx / length;
			float unitPDZ = lz / length;
			playerX = playerX + unitPDX * dist;
			playerZ = playerZ + unitPDZ * dist;
			dist += 0.5f;
			if (dist >= 10) {
				playerDestX = 0.0f;
				playerDestZ = 0.0f;
				dist = 0.0f;
				setPlayerMovable(true);
			}
		}
	}
	
	public GamePlane getGamePlane() {
		return plane;
	}
	
	public void draw(Graphics g) {
		int total = getSceneSize()+1;
		int display = 1;
		for (int i = 0; i < getSceneSize(); i++) {
			Drawable d = get(i);
			d.setPosition(new P3D(-playerX, 0, -playerZ));
			if (d.isVisible()) {
				if (d.isCullable()) {
					if (isVisible(d)) {
						display++;
						d.draw(getSceneDarkness());
					}
				}
				else {
					display++;
					d.draw(getSceneDarkness());
				}
			}
		}
		GameState.DISPLAYED3DOBJECTS = display;
		GameState.TOTAL3DOBJECTS = total;
		player.setPlayerDelta(playerDelta);
		GameState.FIXEDLOC = new P3D(-playerX, plane.getHeight(), -playerZ);
		player.setPosition(new P3D(0, plane.getHeight(), -600));
		player.draw(getSceneDarkness());
		
		scene.fog(getFogColor(), getFogStart(), getFogEnd());
		scene.setReverseFogEquation(true);
		scene.draw(g);
	}
	
	public float getTerrainHeight(float x, float z) {
		return plane.getLocation(x, z);
	}
	
	public boolean isVisible(Drawable d) {
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
	
	public void movePlayerTo(P3D pos) {
		playerDestX = pos.x;
		playerDestZ = pos.z;
	}
}