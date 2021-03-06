import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CadenSea extends Level implements IWaterLevel {
	private Raft raft;
	public CadenSea(IMain inst) {
		super(inst);
	}

	public static long mapDrawTime = 0;

	public void init() {
		scene = new Scene<Drawable>(this, getRand()) {
			public void movePlayer(boolean v) {
				if (v) {
					super.movePlayer(v);
					CadenSea.this.movePlayer();
				}
			}
		};
		GamePlane plane = new GamePlane(scene, getRand(), 55,new Color(38,55,85), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, -300);
			}
		}
		plane.setWaterDraw(true);
		scene.setPlane(plane);
		Rand rad = new Rand();
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setColorPoint(x,z,rad.variate(new Color(38,55,85),20));//new Color(79,86,134));
			}
		}
		plane.genWorld();
		scene.setFog(-2000, -2600);
		scene.setFogColor(new Color(93, 109, 120));
		//scene.add(new GameWalls(scene));
		raft = new Raft(scene);
		raft.setInstanceLoc(0, -350, 0);
		scene.add(raft);
		scene.setSceneDarkness(20);
		getScene().setPlayerSpeed(startSpeed * 2.6f);
		getScene().getPlayer().raftMode = true;
		
		for (int i = 0; i < 20; i++) {
			Enemy enemyOfTheRepublic = new Enemy(scene);
			float x = 0;
			float z = 0;
			boolean found = false;
			float height = -250;
			//while (!found) {
				x = (float) (getRand().nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
				z = (float) (getRand().nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
				enemyOfTheRepublic = new Krake(scene);
			//}
			float y = getScene().getTerrainHeight(x, z);
			enemyOfTheRepublic.setInstanceLoc(x,y+height,z);
			getScene().add(enemyOfTheRepublic);
		}
		
		if (!GameState.instance.hasFishOil) {
			FishOil oil = new FishOil(getScene());
			oil.setInstanceLoc(-4000, 0, -2000);
			getScene().add(oil);
		}
	}
	
	private void movePlayer() {
		raft.handleMove();
	}
	
	public boolean inDeepWater() {
		return getScene().getGamePlane().getHeight()+100 <= -230;
	}
	
	public boolean inWater() {
		return getScene().getGamePlane().getHeight() + 100 <= 0;
	}
	
	public void startRaftMode() {
		addMessage(Strings.inst.SAIL_RAFT_CANT_LEAVE,"WATER40");
		setActiveMessage("WATER40");
	}
	
	private float startSpeed = 19;
	
	public void tick() {
		//System.out.println(getScene().getPlayerX()+","+getScene().getPlayerY()+","+getScene().getPlayerZ());
		GameState.instance.playerLevel = 3;
		getScene().setPlayerY(getScene().getGamePlane().getHeight());
		super.tick();
		if (isGameHalted())
			return;
		
		float space = getScene().getGamePlane().getSpacing();
		if (getScene().getPlayerX() > 21.33333 * space && getScene().canPortalize()) {
			startTransition(getMain().getScreen("yLENIN"), new P3D(-18.6666666f * space, 0, getScene().getPlayerZ()), getScene().getPlayerDelta());
			getScene().deportal();
			GameState.instance.playerLevel = 2;
			return;
		}
		if (getScene().getPlayerX() < 23.733333333333f * space) {
			Scene.camDist = Scene.regCamDist;
			getScene().reportal();
		}
	}

	public String getName() {
		return "lilo";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		//scene.setSceneDarkness(0);
		scene.draw(g);
	}
}
