import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FiaceForest extends Level {

	public FiaceForest(IMain inst) {
		super(inst);
	}

	public static long mapDrawTime = 0;

	public void init() {
		Gem[] gems = new Gem[40];// 40
		Tree[] trees = new Tree[500];// 20, LATEST: 60
		Grass[] grass = new Grass[80];
		Barrel[] barrel = new Barrel[20];
		Sign[] signs = new Sign[10];
		scene = new Scene<Drawable>(this, getRand());
		scene.setPlane(new GamePlane(scene, getRand(), 55, new Color(110, 130,
				110), 14, 0.5f));
		scene.setFog(-2000, -2600);// -2550);
		// scene.setFogColor(new Color(140, 140, 165));
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
		/*for (int i = 0; i < signs.length; i++) {
			signs[i] = new Sign(scene);
			signs[i].setInstanceLoc(getRand().nextLocation(-220));
			signs[i].setSignMessage("Sign #" + (i + 1));
		}*/
		for (int i = 0; i < barrel.length; i++) {
			barrel[i] = new Barrel(scene, getRand());
			barrel[i].setInstanceLoc(getRand().nextLocation(-300));
		}
		//setSigns(signs);
		scene.add(grass);
		scene.add(gems);
		scene.add(trees);
//		scene.add(signs);
		scene.add(barrel);
		scene.add(new GameWalls(scene));
		for (int i = 0; i < 10; i++) {
		Enemy en = new Enemy(scene);
		en.setInstanceLoc(getRand().nextLocation(-300));
		scene.add(en);
		}
		/*Lamppost lamp = new Lamppost(scene);
		lamp.setInstanceLoc(new P3D(2000, -300, 0));
		lamp.updateInstLoc();
		scene.add(lamp);*/

		Well well = new Well(scene);
		well.setInstanceLoc(new P3D(2000, -300, 300));
		scene.add(well);

		PortalFront frontPortal = new PortalFront(scene);
		frontPortal.setInstanceLoc(0, -10500);
		scene.add(frontPortal);
		// Water feature = new Water(scene,10);
		// feature.setInstanceLoc(new P3D(2000,-200,1300));
		// scene.add(feature);
		if (!GameState.instance.talkedToGrandmaFiace) {
		Grandma grandma = new Grandma(scene) {
			private boolean showingMessage = false;
			private boolean askAnnoyingMessageAnymore = true;
			private boolean talkedTo = false;
			private boolean traveling = false;
			private boolean completedAction = false;
			private boolean showedCompletedAction = false;

			public void tick() {
				float pDist = getDistToPlayer();
				if (pDist < 400 && !showingMessage && !talkedTo && !traveling) {
					cancelMovement();
					talkedTo = true;
					setDelta((float) Math.atan2((-getScene().getPlayerZ()
							+ getInstanceLoc().z + 500), (-getScene()
							.getPlayerX() + getInstanceLoc().x)));
					showingMessage = true;
					if (askAnnoyingMessageAnymore)
						getScene().getLevel().addMessage(
								"Could you please help me with an errand?",
								"GRAND0", true, true, new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										Message m = (Message) e.getSource();
										if (m.getResult()) {
											makeAngry();
											getScene()
													.getLevel()
													.addMessage(
															"GOOD! Heheheh... Please follow me.",
															"GRAND1");
											getScene().getLevel()
													.setActiveMessage("GRAND1");
											askAnnoyingMessageAnymore = false;
											setMoveSpeed(3.5f);
											moveTowards(0, -7000);
											traveling = true;
										} else {
											getScene()
													.getLevel()
													.addMessage(
															"Okay... You can always help me later.",
															"GRAND2");
											getScene().getLevel()
													.setActiveMessage("GRAND2");
										}
									}
								});
					getScene().getLevel().setActiveMessage("GRAND0");
				} else if (talkedTo && pDist > 400)
					talkedTo = false;
				if (pDist > 400)
					showingMessage = false;
				if (showingMessage)
					return;

				if (traveling && !isMovingTowards()) {
					completedAction = true;
				}
				if (completedAction && !showedCompletedAction) {
					showedCompletedAction = true;
					getScene()
							.getLevel()
							.addMessage(
									"Thank you so much!\nAs a token of my gratitude, please take these 200 Gems!",
									"GRAND3", new ActionListener() {
										public void actionPerformed(
												ActionEvent e) {
											getScene()
													.getLevel()
													.addMessage(
															GameState.instance.playerGUID
																	+ "...",
															"GRAND4",
															new ActionListener() {
																public void actionPerformed(
																		ActionEvent arg0) {
																	GameState.instance.talkedToGrandmaFiace = true;
																	GameState.save();
																	getScene()
																			.getLevel()
																			.addMessage(
																					"This dark fog has taken over the land..."
																							+ "\nYou should go to Holm Village to see what you can do.<PAUSE>"
																							+ "\n(JK- The fog is actually here because this graphics engine isn't"
																							+ "\nfast enough to fully render the level.)"
																							+ "\nHolm town is just due north of here.",

																					"GRAND7");
																	getScene()
																			.getLevel()
																			.setActiveMessage(
																					"GRAND7");
																}
															});
											getScene().getLevel()
													.setActiveMessage("GRAND4");
										}
									});
					getScene().getLevel().setActiveMessage("GRAND3");
					GameState.instance.gems += 200;
					GameState.instance.talkedToGrandmaFiace = true;
					makeSweet();
				}
				if (completedAction)
					return;
				doMovement();
			}
		};
		grandma.setInstanceLoc(new P3D(2000, -350, 600));
		scene.add(grandma);
		}
		// scene.add(new Windmill(scene));
		/*
		 * House h = new House(scene); h.setHouseName("-- Blacksmith --");
		 * h.setInstanceLoc(new P3D(0,-300,0)); scene.add(h);
		 */

		if (GameState.instance.playerStage == 0) {
			GameState.instance.playerStage = 1;
			GameState.save();
			addMessage(
					"Press 'Q' to skip the dialogues or jump."
							+ "\nPress 'ENTER' to attack.\nPress 'WASD' to move around.\nThe game automatically saves every 10 seconds, so there is no\nneed to save.",
					"intro1",new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									setActiveMessage("intro2");
								}
							});
			addMessage("Your Grandma is around here somewhere.\nHelp her get back to her house.","intro2");
			addMessage("Welcome to Fi\u00E4ce Forest, "
					+ GameState.instance.playerGUID + ". Press 'Q'.", "intro0",
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setActiveMessage("intro1");
						}
					});
			setActiveMessage("intro0");
		}
	}
	
	public void tick() {
		GameState.instance.playerLevel = 0;
		getScene().setPlayerY(getScene().getGamePlane().getHeight());
		super.tick();
	}

	public String getName() {
		return "level";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		scene.setSceneDarkness(0);
		scene.draw(g);
	}
}
