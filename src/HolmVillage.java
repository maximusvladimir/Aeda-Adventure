import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HolmVillage extends Level {

	public HolmVillage(IMain inst) {
		super(inst);
	}

	public static long mapDrawTime = 0;

	public void init() {
		Gem[] gems = new Gem[40];// 40
		Tree[] trees = new Tree[60];// 20
		Grass[] grass = new Grass[80];
		Barrel[] barrel = new Barrel[5];
		Sign[] signs = new Sign[2];
		House[] houses = new House[4];
		Lamppost[] lamps = new Lamppost[10];
		scene = new Scene<Drawable>(this, getRand());
		GamePlane plane = new GamePlane(scene,getRand(),55, new Color(99,126,61).darker(), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, plane.getHeightPoint(x,z)/4);
			}
		}
		scene.setPlane(plane);
		for (int z = 0; z < 55; z++) {
			int ds = 20;
			plane.setColorPoint(26, z, getRoadColor(plane.getColorPoint(26, z)));
			plane.setColorPoint(27, z, getRoadColor(plane.getColorPoint(27, z)));
			plane.setColorPoint(28, z, getRoadColor(plane.getColorPoint(28, z)));
			
			plane.setColorPoint(z, 27, getRoadColor(plane.getColorPoint(z, 27)));
			plane.setColorPoint(z, 28, getRoadColor(plane.getColorPoint(z, 28)));
			plane.setColorPoint(z, 29, getRoadColor(plane.getColorPoint(z, 29)));
		}
		plane.genWorld();
		scene.setFog(-2000, -2600);
		scene.setFogColor(new Color(93, 109, 120));
		for (int i = 0; i < gems.length; i++) {
			gems[i] = new Gem(scene, getRand());
			gems[i].setInstanceLoc(getRand().nextLocation(-170));
		}
		// an interesting grass placement algorithm that i came up with.
		int currentGrass = 0;
		while (currentGrass < grass.length) {
			int remaining = grass.length - currentGrass;
			int currentNum = getRand().nextInt(remaining / 6, remaining / 4);
			if (currentNum <= 0)
				currentNum = 1;
			int indexer = 0;
			P3D ps = getRand().nextLocation(0);
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
			trees[i].setInstanceLoc(getRand().nextLocation(-400));
		}
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
		}
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, getRand());
		}
		for (int i = 0; i < houses.length; i++) {
			houses[i] = new House(scene);
		}
		for (int i = 0; i < lamps.length; i++) {
			lamps[i] = new Lamppost(scene);
			if (i <= 5)
				lamps[i].setInstanceLoc(-600,-375,plane.getWorldSizeHalf()/4 + (i * 1500));
			else
				lamps[i].setInstanceLoc(-600,-375,-(plane.getWorldSizeHalf()/4 + (i-5) * 1500));
			
			lamps[i].updateInstLoc();
		}
		houses[0].setInstanceLoc(-1500,-350,-1000);
		houses[0].setHouseName("Grandma Gwendolynn");
		houses[0].setOwnerName("Grandma");
		
		if (!getMain().screenExists(houses[0].getHouseName())) {
		InsideHouse ggH = new InsideHouse(houses[0].getHouseName(),getMain()) {
			public void init() {
				super.init();
				// Additional init code goes here.
			}
		};
		getMain().addScreen(ggH);
		}
		
		houses[1].setInstanceLoc(1500,-350,-1000);
		houses[1].setHouseName("Swordsmaster Cassius");
		houses[1].setOwnerName("Master Cassius");
		
		houses[2].setInstanceLoc(1500,-350,1000);
		houses[2].setHouseName("Count Charles");
		houses[2].setOwnerName("Count Charles");
		
		houses[3].setInstanceLoc(-1500,-350,1000);
		houses[3].setHouseName("Rulf's Shop");
		houses[3].setOwnerName("Rulf");
		
		signs[0].setInstanceLoc(600, -220, 2500);
		signs[0].setSignMessage(Strings.inst.HOLM_VILLAGE_SIGN);
		
		signs[1].setInstanceLoc(600, -220, -9800);
		signs[1].setSignMessage(Strings.inst.HOLM_VILLAGE_NORTH_ENTRY);
		
		barrel[0].setInstanceLoc(600,-350,2000);
		barrel[1].setInstanceLoc(-900,-350,2075);
		barrel[2].setInstanceLoc(600,-350,3500);
		
		setSigns(signs);
		scene.add(grass);
		scene.add(houses);
		scene.add(gems);
		scene.add(trees);
		scene.add(signs);
		scene.add(barrel);
		scene.add(lamps);
		scene.add(new GameWalls(scene));
		Windmill windmill = new Windmill(getScene());
		windmill.setInstanceLoc(-1500, -350, 4000);
		scene.add(windmill);
		Well ohwell = new Well(scene) {
			private boolean na = false;
			public void tick() {
				super.tick();
				if (getDistToPlayer() < 400 && !na) {
					addMessage("This old well is very deep and filled with water.\nSomething appears to be moving in it.","WELL0");
					setActiveMessage("WELL0");
					na = true;
				}
				if (getDistToPlayer() > 700)
					na = false;
			}
		};
		ohwell.setInstanceLoc(-3000,-340,1000);
		scene.add(ohwell);
		
		scene.setSceneDarkness(50);
	}
	
	public void tick() {
		getScene().setPlayerY(-100);
		super.tick();
		if (isGameHalted())
			return;
			
		if (getScene().getPlayerZ() > 9500 && getScene().getPlayerX() < 600 && getScene().getPlayerX() > -600 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("level"),new P3D(0,0,-9200),4.712388980384689f);
			getScene().deportal();
			GameState.instance.playerLevel = 0;
			return;
		}
		if (getScene().getPlayerZ() < 9400 || getScene().getPlayerX() > 700 || getScene().getPlayerZ() < -700)
			getScene().reportal();
	}
	
	private Color getRoadColor(Color prev) {
		return MathCalculator.lerp(new Color(132,117,98), prev, 0.5f);
	}

	public String getName() {
		return "vbm";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		scene.draw(g);
	}

	public void mouseReleased(MouseEvent me) {
		super.mouseReleased(me);
		ArrayList<House> houses = scene.<House> getObjectsByType(House.class);
		for (int i = 0; i < houses.size(); i++) {
			houses.get(i).lightsOn = !houses.get(i).lightsOn;
		}
		ArrayList<Lamppost> lamps = scene
				.<Lamppost> getObjectsByType(Lamppost.class);
		for (int i = 0; i < lamps.size(); i++) {
			lamps.get(i).setLampOn(!lamps.get(i).isLampOn());
		}
	}

	public void keyReleased(KeyEvent ke) {
		super.keyReleased(ke);
		if (ke.getKeyCode() == KeyEvent.VK_B) {
			GameState.instance.health = 10;
		}
	}
}