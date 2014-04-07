import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SailorHarbour extends Level implements IWaterLevel {
	private static final int waterStart = 35;
	private static final float indicator = (waterStart-3) * 10000.0f / 55.0f;
	private Raft raft;
	public SailorHarbour(IMain inst) {
		super(inst);
	}

	public static long mapDrawTime = 0;
	
	public void loadedLevel()  {
		if (inDeepWater() && !raftMode) {
			startRaftMode();
		}
		else {
			// A very evil way to ensure we are indeed not in deep water.
			Thread thread = new Thread(new Runnable() {
				public void run() {
					long s = System.currentTimeMillis();
					while (System.currentTimeMillis() - s < 300) {
						
					}
					if (inDeepWater() && !raftMode) {
						startRaftMode();
					}
				}		
			});
			thread.setName("DeepWaterEvilChecker");
			thread.start();
		}
	}
	
	public void init() {
		Tree[] trees = new Tree[20];// 20
		Grass[] grass = new Grass[20];
		scene = new Scene<Drawable>(this, getRand()) {
			public void movePlayer(boolean v) {
				if (raftMode && v) {
					super.movePlayer(v);
					SailorHarbour.this.movePlayer();
				}
				else if (!raftMode) {
					super.movePlayer(v);
				}
			}
		};
		GamePlane plane = new GamePlane(scene, getRand(), 55,new Color(38,55,85), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				final boolean statemental = (!(z < 10 - (x - (waterStart - 5)) && x > waterStart - 5) && !(z > 45 + (x - (waterStart - 5)) && x > waterStart - 5));
				if (x > waterStart - 5 && statemental) {
					if (x < waterStart + 3) {
						float dec = (x - (waterStart - 5)) / ((float)((waterStart + 3) - (waterStart - 5)));
						final boolean statemental2 = (!((z-1) < 10 - ((x-1) - (waterStart - 5)) && (x-1) > waterStart - 5) && !((z-1) > 45 + ((x-1) - (waterStart - 5)) && (x-1) > waterStart - 5));
						if (!statemental2)
							plane.setHeightPoint(x, z, 0);
						else
							plane.setHeightPoint(x, z, dec * 125.0f);
					}
					else
						plane.setHeightPoint(x,z,125);
				}
				else
					plane.setHeightPoint(x, z, (x / 55.0f * 350.0f)-400);
			}
		}
		plane.setWaterDraw(true);
		scene.setPlane(plane);
		Rand rad = new Rand();
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				final boolean statemental = (!(z < 10 - (x - (waterStart - 5)) && x > waterStart - 5) && !(z > 45 + (x - (waterStart - 5)) && x > waterStart - 5));
				if ((x > waterStart - 5 && statemental)) {
					Color g = getRand().variate(new Color(87,126,52), 25);
					Color sand = getRand().variate(new Color(153,156,127), 25);
					plane.setColorPoint(x,z,MathCalculator.lerp(sand,g,(x-(waterStart-2))/15.0f));
				}
				else
					plane.setColorPoint(x,z,rad.variate(new Color(38,55,85),20));//new Color(79,86,134));
			}
		}
		plane.genWorld();
		scene.setFog(-2000, -2600);
		scene.setFogColor(new Color(93, 109, 120));
		// an interesting grass placement algorithm that i came up with.
		int currentGrass = 0;
		while (currentGrass < grass.length) {
			int remaining = grass.length - currentGrass;
			int currentNum = getRand().nextInt(remaining / 6, remaining / 4);
			if (currentNum <= 0)
				currentNum = 1;
			int indexer = 0;
			P3D ps = getRand().nextLocation(0);
			while (ps.x < indicator) {
				ps = getRand().nextLocation(0);
			}
			while (indexer < currentNum) {
				indexer++;
				float offsetX = getRand().nextInt(-550, 550);
				float offsetZ = getRand().nextInt(-550, 550);
				P3D ms = new P3D(ps.x + offsetX, scene.getTerrainHeight(ps.x
						+ offsetX, ps.z + offsetZ) - 410, ps.z + offsetZ);
				if (currentGrass > grass.length - 1)
					break;
				grass[currentGrass] = new Grass(scene);
				grass[currentGrass++].setInstanceLoc(ms);
			}
		}
		for (int i = 0; i < trees.length; i++) {
			trees[i] = new Tree(scene, getRand());
			// Make sure the tree isn't in the middle of the road.
			P3D treeLoc = getRand().nextLocation(-400);
			boolean found = false;
			while (!found) {
				treeLoc = getRand().nextLocation(-400);
				if (treeLoc.x > indicator)
					found = true;
			}
			trees[i].setInstanceLoc(treeLoc);
		}
		for (int i = 0; i < 20; i++) {
			Enemy enemyOfTheRepublic = new Enemy(scene);
			float x = 0;
			float z = 0;
			boolean found = false;
			float height = -250;
			while (!found) {
				x = (float) (getRand().nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
				z = (float) (getRand().nextDouble() * -(scene.getWorldSize()-1200)) + scene.getWorldSizeHalf()-600;
				//System.out.println(x+","+((((x/scene.getWorldSize())+1)/2)*scene.getWorldActual()) );
				if (((((x/scene.getWorldSize())+1)/2)*scene.getWorldActual()) > waterStart) {
					found = true;
					break;
				}
				else {
					enemyOfTheRepublic = new Dunp(scene);
					height = 0;
					found = true;
					break;
				}
			}
			float y = getScene().getTerrainHeight(x, z);
			enemyOfTheRepublic.setInstanceLoc(x,y+height,z);
			getScene().add(enemyOfTheRepublic);
		}
		scene.add(grass);
		scene.add(trees);
		scene.add(new GameWalls(scene));
		raft = new Raft(scene);
		raft.setInstanceLoc(0, -350, 0);
		raft.setVisible(false);
		scene.add(raft);
		scene.setSceneDarkness(20);
	}
	
	public void keyDown(int key) {
		super.keyDown(key);
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
			adjustBalance();
	}
	
	private void adjustBalance() {
		if (SoundManager.backgroundSound != null) {
			float a = getScene().getPlayerX() / (getScene().getGamePlane().getWorldSize()*0.5f);
			if (a < 0)
				a = 0;
			SoundManager.backgroundSound.setVolume(a * 20);
			SoundManager.backgroundSound.setPosition(a);
		}
	}
	
	private void movePlayer() {
		if (raftMode) {
			raft.handleMove();
		}
	}
	
	public boolean inDeepWater() {
		return getScene().getGamePlane().getHeight()+100 <= -230;
	}
	
	public boolean inWater() {
		return getScene().getGamePlane().getHeight() + 100 <= 0;
	}
	
	private boolean raftMode = false;
	
	public void startRaftMode() {
		if (inWater()) {
			if (!raftMode) {
				startSpeed = getScene().getPlayerSpeed();
				raft.setVisible(true);
				//System.out.println("Starting raft mode.");
				raftMode = true;
				getScene().setPlayerSpeed(startSpeed * 2.6f);
				getScene().getPlayer().raftMode = true;
			}
			else {
				if (!inDeepWater()) {
					raftMode = false;
					getScene().getPlayer().raftMode = false;
					getScene().setPlayerSpeed(startSpeed);
					raft.setVisible(false);
					new Sound("fallwater").play();
				}
				else {
					addMessage(Strings.inst.SAIL_RAFT_CANT_LEAVE,"WATER40");
					setActiveMessage("WATER40");
				}
			}
		}
		else {
			addMessage(Strings.inst.SAIL_RAFT_RULE_NOTE,"RAFTUSAGERULE");
			setActiveMessage("RAFTUSAGERULE");
		}
	}
	
	private float startSpeed = 0;
	
	public void tick() {
		GameState.instance.playerLevel = 2;
		if (getScene().getPlayerZ() > 6900)
			getScene().setPlayerZ(6900);
		if (inDeepWater() && !raftMode) {
			//getScene().setPlayerX(getScene().getPlayerX() + 10);
			boolean doer = true;
			int incr = 0;
			while (doer) {
				incr++;
				getScene().setPlayerX(getScene().getPlayerX() + 1);
				if (incr > 20)
					doer = false;
				if (!inDeepWater())
					doer = false;
			}
		}
		float fw = getScene().getGamePlane().getHeight()+100;
		if (raftMode && fw > -212) {
			boolean doer = true;
			int incr = 0;
			while (doer) {
				incr++;
				getScene().setPlayerX(getScene().getPlayerX() - 30);
				if (incr > 20)
					doer = false;
				if (!inDeepWater())
					doer = false;
			}
		}
		if (raftMode) {
		//	getScene().movePlayer(true);
			//TODO : BUGGED
		}
		getScene().setPlayerY(getScene().getGamePlane().getHeight());
		super.tick();
		if (isGameHalted())
			return;
		
		if (getScene().getPlayerX() > 9100 && getScene().getPlayerZ() < 600
				&& getScene().getPlayerZ() > -600 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("vbm"), new P3D(-9200, 0, 300),
					4.712388980384689f);
			getScene().deportal();
			GameState.instance.playerLevel = 1;
			return;
		}
		if (getScene().getPlayerX() < -8000 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("lilo"), new P3D(6000, 0, getScene().getPlayerZ()), getScene().getPlayerDelta());
			getScene().deportal();
			GameState.instance.playerLevel = 3;
			return;
		}
		if (getScene().getPlayerX() < 8900 || getScene().getPlayerZ() > 700
				|| getScene().getPlayerZ() < -700)
			getScene().reportal();
	}

	public String getName() {
		return "yLENIN";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		//scene.setSceneDarkness(0);
		scene.draw(g);
	}

}