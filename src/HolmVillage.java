import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class HolmVillage extends Level {

	private House[] houses;
	private Lamppost[] lamps;
	private P3D lastLoc = new P3D();

	private static BufferedImage glow = null;

	public House[] getHouses() {
		return houses;
	}
	
	public HolmVillage(IMain inst) {
		super(inst);
	}

	public void loadedLevel() {
		super.loadedLevel();
		if (GameState.instance.hasFishOil && !GameState.instance.hasSword
				&& getScene().getObjectsByType(Cassius.class).size() == 0) {
			Cassius cas = new Cassius(getScene()) {
				public void tick() {
					super.tick();
					tickAction();
				}
			};
			cas.setInstanceLoc(-8000, -350, 0);
			getScene().add(cas);
		}
		if (GameState.instance.hasSword)
			houses[1].lightsOn = true;
		else
			houses[1].lightsOn = false;
		if (glow == null) {
			try {
				glow = ImageIO.read(HolmVillage.class.getResource("/glow.png"));
			} catch (Throwable t) {

			}
		}
	}

	public static long mapDrawTime = 0;

	public void init() {
		Tree[] trees = new Tree[120];// 20
		Grass[] grass = new Grass[80];
		Barrel[] barrel = new Barrel[5];
		Sign[] signs = new Sign[3];
		houses = new House[4];
		lamps = new Lamppost[10];
		Enemy[] enemies = new Enemy[4];
		scene = new Scene<Drawable>(this, getRand());
		GamePlane plane = new GamePlane(scene, getRand(), 55, new Color(99,
				126, 61).darker(), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, plane.getHeightPoint(x, z) / 4);
			}
		}
		scene.setPlane(plane);
		for (int z = 0; z < 55; z++) {
			if (z > 26) {
				plane.setColorPoint(26, z, getRoadColor(plane.getColorPoint(26, z)));
				plane.setColorPoint(27, z, getRoadColor(plane.getColorPoint(27, z)));
				plane.setColorPoint(28, z, getRoadColor(plane.getColorPoint(28, z)));
			}
			
			plane.setColorPoint(z, 27, getRoadColor(plane.getColorPoint(z, 27)));
			plane.setColorPoint(z, 28, getRoadColor(plane.getColorPoint(z, 28)));
			plane.setColorPoint(z, 29, getRoadColor(plane.getColorPoint(z, 29)));
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
				int[] pts = plane
						.getWorldPointAsGridPoint(treeLoc.x, treeLoc.z);
				if ((pts[0] < 25 || pts[0] > 29)
						&& (pts[1] < 26 || pts[1] > 30)) {
					found = true;
					break;
				}
				treeLoc = getRand().nextLocation(-400);
			}
			trees[i].setInstanceLoc(treeLoc);
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
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(scene);
		}
		for (int i = 0; i < lamps.length; i++) {
			lamps[i] = new Lamppost(scene);
			if (i <= 5)
				lamps[i].setInstanceLoc(-600, -375, plane.getWorldSizeHalf()
						/ 4 + (i * 1500));
			else
				lamps[i].setInstanceLoc(-600, -375,
						-(plane.getWorldSizeHalf() / 4 + (i - 5) * 1500));

			lamps[i].updateInstLoc();
		}
		houses[0].setInstanceLoc(-1500, -350, -1000);
		houses[0].setHouseName(Strings.inst.HOLM_VILLAGE_GRANDMA_H);
		houses[0].setOwnerName(Strings.inst.HOLM_VILLAGE_GRANDMA_O);

		if (!getMain().screenExists(houses[0].getHouseName())) {
			final InsideHouse ggH = new InsideHouse(houses[0].getHouseName(),
					getMain()) {
				private Grandma grandma;
				private boolean grandmaMessage = false;

				public void reloadedLevel() {
					grandmaMessage = false;
				}

				public void init() {
					setRootLevel(HolmVillage.this);
					super.init();
					Couch co = new Couch(scene);
					co.setInstanceLoc(-1100, -300, -500);
					co.setDelta(MathCalculator.PIOVER2);
					co.getHitbox().rotate90deg();
					getScene().add(co);
					Couch co2 = new Couch(scene);
					co2.setInstanceLoc(-500, -300, -500);
					co2.setDelta(MathCalculator.PIOVER2 + MathCalculator.PI);
					co2.getHitbox().rotate270deg();
					getScene().add(co2);
					Couch co3 = new Couch(scene);
					co3.setInstanceLoc(-850, -300, -800);
					getScene().add(co3);
					Bed bed = new Bed(scene);
					bed.setInstanceLoc(200, -300, -400);
					getScene().add(bed);
					grandma = new Grandma(getScene()) {
						private boolean shownMessage = false;

						public void tick() {
							super.tick();
							float dist = getDistToPlayer();
							if (dist < 400 && !grandmaMessage) {
								grandmaMessage = true;
								if (GameState.instance.hasSword) {
									addMessage(
											"It was very nice of Master Cassius to give you that beautiful\nsword. Be careful with it, it looks sharp.",
											"nicesword");
									setActiveMessage("nicesword");
								} else if (GameState.instance.hasFishOil
										&& !GameState.instance.hasSword) {
									addMessage(
											GameState.instance.playerGUID
													+ " there's somebody that want to talk\nto you by the road near Sailor's Harbour.",
											"noswordnotify");
									setActiveMessage("noswordnotify");
								} else if (GameState.instance.hasRaft) {
									addMessage(
											Strings.inst.HOLM_HAUZ_GRAND_PURCHASED_RAFT,
											"raftGrandma");
									setActiveMessage("raftGrandma");
								} else {
									addMessage(
											Strings.inst.HOLM_HAUZ_GRAND_NOT_PURCHASED_RAFT,
											"antiRaftGrandma");
									setActiveMessage("antiRaftGrandma");
								}
							}
						}
					};
					grandma.setMoveSpeed(10.0f);
					grandma.setInstanceLoc(200, -300, 100);
					grandma.moveTowards(getRand().nextLocation(-300));
					getScene().add(grandma);
					// Additional init code goes here.
				}
			};
			getMain().addScreen(ggH);
		}

		houses[1].setInstanceLoc(1500, -350, -1000);
		houses[1].setHouseName(Strings.inst.HOLM_VILLAGE_CASSIUS_H);
		houses[1].setOwnerName(Strings.inst.HOLM_VILLAGE_CASSIUS_O);
		
		if (GameState.instance.hasSword)
			houses[1].lightsOn = true;
		else
			houses[1].lightsOn = false;
		
		if (!getMain().screenExists(houses[1].getHouseName())) {
			final InsideHouse ggH = new InsideHouse(houses[1].getHouseName(),
					getMain()) {
				private Cassius cass;
				private boolean cassMessage = false;

				public void reloadedLevel() {
					cassMessage = false;
				}

				public void init() {
					setRootLevel(HolmVillage.this);
					super.init();
					cass = new Cassius(getScene()) {
						private boolean shownMessage = false;

						public void tick() {
							super.tick();
							float dist = getDistToPlayer();
							if (dist < 400 && !cassMessage) {
								cassMessage = true;
								
							}
						}
					};
					cass.setMoveSpeed(10.0f);
					cass.setInstanceLoc(200, -300, 100);
					Rand guih = new Rand();
					guih.setScene(getScene());
					cass.moveTowards(guih.nextLocation(-300));
					getScene().add(cass);
				}
			};
			getMain().addScreen(ggH);
		}

		houses[2].setInstanceLoc(1500, -350, 1000);
		houses[2].setHouseName(Strings.inst.HOLM_VILLAGE_CASSIUS_H);
		houses[2].setOwnerName(Strings.inst.HOLM_VILLAGE_CASSIUS_O);

		houses[3].setInstanceLoc(-1500, -350, 1000);
		houses[3].setHouseName(Strings.inst.HOLM_VILLAGE_RULF_H);
		houses[3].setOwnerName(Strings.inst.HOLM_VILLAGE_RULF_O);
		houses[3].lightsOn = true;

		if (!getMain().screenExists(houses[3].getHouseName())) {
			final Shop ssd = new Shop(houses[3].getHouseName(), getMain());
			ssd.setSender(getName());
			getMain().addScreen(ssd);
		}

		signs[0].setInstanceLoc(600, -220, 2500);
		signs[0].setSignMessage(Strings.inst.HOLM_VILLAGE_SIGN);

		signs[1].setInstanceLoc(600, -220, -9800);
		signs[1].setSignMessage(Strings.inst.HOLM_VILLAGE_NORTH_ENTRY);

		signs[2].setInstanceLoc(300, -220, 7200);
		signs[2].setSignMessage("Don't forget to fight enemies with ENTER.\nYou can also break barrels as well.");

		barrel[0].setInstanceLoc(600, -350, 2000);
		barrel[1].setInstanceLoc(-900, -350, 2075);
		barrel[2].setInstanceLoc(600, -350, 3500);
		barrel[3].setInstanceLoc(850, -300, -450);
		barrel[4].setInstanceLoc(-1000, -300, -450);

		enemies[0].setInstanceLoc(0, -350, 4000);
		enemies[1].setInstanceLoc(0, -350, 5000);
		enemies[2].setInstanceLoc(0, -350, 6000);
		enemies[3].setInstanceLoc(0, -350, 6800);

		// Very "heavy" object
		Horse horse = new Horse(scene);
		horse.setInstanceLoc(-6000, -350, -850);
		scene.add(horse);

		RedGem jewel = new RedGem(getScene(), getRand());
		jewel.setInstanceLoc(-2000, -170, 5000);
		scene.add(jewel);

		Heart heart = new Heart(getScene());
		heart.setInstanceLoc(0, -170, 2500);
		scene.add(heart);

		setSigns(signs);
		scene.add(enemies);
		scene.add(grass);
		scene.add(houses);
		scene.add(trees);
		scene.add(signs);
		scene.add(barrel);
		scene.add(lamps);
		scene.add(new GameWalls(scene));

		// scene.add(new Krake(scene));

		Windmill windmill = new Windmill(getScene());
		windmill.setInstanceLoc(-1500, -350, 4000);
		scene.add(windmill);
		Well ohwell = new Well(scene) {
			private boolean na = false;

			public void tick() {
				super.tick();
				if (getDistToPlayer() < 400 && !na) {
					addMessage(Strings.inst.HOLM_VILLAGE_WELL_MSG_0, "WELL0");
					setActiveMessage("WELL0");
					na = true;
				}
				if (getDistToPlayer() > 700)
					na = false;
			}
		};
		ohwell.setInstanceLoc(-3000, -340, 1000);
		scene.add(ohwell);

		scene.setSceneDarkness(50);
	}

	public void tick() {
		GameState.instance.playerLevel = 1;
		lastLoc = getScene().getPosition();
		if (GameState.instance.talkedToGrandmaFiace) {
			houses[0].lightsOn = true;
		} else
			houses[0].lightsOn = false;

		if (Rand.random() < 0.0002)
			getScene().makeLightning();

		// System.out.println(getScene().getPlayerZ());

		super.tick();
		if (isGameHalted())
			return;

		if (getScene().getPlayerX() < -9500 && getScene().getPlayerZ() > -600
				&& getScene().getPlayerZ() < 1100 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("yLENIN"), new P3D(9000, 0,
					getScene().getPlayerZ()), getScene().getPlayerDelta());
			getScene().deportal();
			GameState.instance.playerLevel = 2;
			return;
		}

		if (getScene().getPlayerZ() > 9500 && getScene().getPlayerX() < 600
				&& getScene().getPlayerX() > -600 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("level"), new P3D(0, 0, -9200),
					4.712388980384689f);
			getScene().deportal();
			GameState.instance.playerLevel = 0;
			return;
		}
		if ((getScene().getPlayerZ() < 9400 || getScene().getPlayerX() > 700 || getScene()
				.getPlayerZ() < -700)
				&& (getScene().getPlayerZ() > 700
						|| getScene().getPlayerX() > 9400 || getScene()
						.getPlayerZ() < -700))
			getScene().reportal();
	}

	private Color getRoadColor(Color prev) {
		return MathCalculator.lerp(new Color(132, 117, 98), prev, 0.5f);
	}

	public String getName() {
		return "vbm";
	}

	public void draw(Graphics g) {
		scene.draw(g);
	}

	private ArrayList<P3D> lightHints = new ArrayList<P3D>();

	public void drawHUD(Graphics g) {
		if (glow != null) {
			if (Main.findNumFramesDrawn() % 4 == 0) {
				lightHints.clear();
				Color[] screenColors = null;
				for (int i = 0; i < lamps.length; i++) {
					Lamppost post = lamps[i];
					if (getScene().isVisible(post)) {
						if (screenColors == null) {
							int[] dbi = getMain().getDBI().getData();
							screenColors = new Color[dbi.length];
							for (int j = 0; j < dbi.length; j++) {
								screenColors[j] = new Color(dbi[j]);
							}
						}
						Color sample = post.lightColor;
						int lx = 0;
						int ly = 0;
						for (int x = 0; x < getMain().getWidth(); x++) {
							for (int y = 0; y < getMain().getHeight(); y++) {
								int det = y * getMain().getWidth() + x;
								if (det < 0 || det > screenColors.length)
									continue;
								if (MathCalculator.compareColor(
										screenColors[det], sample) < 20) {
									lx = x;
									ly = y;
									x = getMain().getWidth() + 100;
									y = getMain().getHeight() + 100;
									break;
								}
							}
						}
						if (lx == 0 && ly == 0) {
							continue;
						}
						float size = 0;
						float dist = post.getDistToCamera();
						size = dist * 0.001f;
						size = 1 / size;
						size = size * glow.getWidth() * 4;
						lightHints.add(new P3D(lx,ly,size));
					}
				}
			}

			for (int i = 0; i < lightHints.size(); i++) {
				P3D p = lightHints.get(i);
				float size = p.z;
				if (size < 0)
					continue;
				int half = (int) (size * 0.5f);
				g.drawImage(glow, (int) p.x - half, (int) p.y - half,
						(int) size, (int) size, null);
			}
		}
		super.drawHUD(g);
	}
}
