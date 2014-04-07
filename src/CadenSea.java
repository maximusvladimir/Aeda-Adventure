import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CadenSea extends Level {
	private Raft raft;
	public CadenSea(IMain inst) {
		super(inst);
	}

	public static long mapDrawTime = 0;

	public void init() {
		scene = new Scene<Drawable>(this, getRand()) {
			public void movePlayer(boolean v) {
				if (raftMode && v) {
					super.movePlayer(v);
					CadenSea.this.movePlayer();
				}
				else if (!raftMode) {
					super.movePlayer(v);
				}
			}
		};
		GamePlane plane = new GamePlane(scene, getRand(), 55,new Color(38,55,85), 14, 0.5f);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setHeightPoint(x, z, 0);
			}
		}
		plane.setWaterDraw(true);
		scene.setPlane(plane);
		for (int x = 0; x < 55; x++) {
			for (int z = 0; z < 55; z++) {
				plane.setColorPoint(x,z,new Color(38,55,85));//new Color(79,86,134));
			}
		}
		plane.genWorld();
		scene.setFog(-2000, -2600);
		scene.setFogColor(new Color(93, 109, 120));
		scene.add(new GameWalls(scene));
		raft = new Raft(scene);
		raft.setInstanceLoc(0, -350, 0);
		raft.setVisible(false);
		scene.add(raft);
		scene.setSceneDarkness(20);
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
	
	private boolean raftMode = true;
	
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
		
		if (getScene().getPlayerX() > 9100 && getScene().canPortalize()) {
			startTransition(getMain().getScreen("yLENIN"), new P3D(9200, 0, 300),
					4.712388980384689f);
			getScene().deportal();
			GameState.instance.playerLevel = 1;
			return;
		}
		if (getScene().getPlayerX() < 8900 || getScene().getPlayerZ() > 700
				|| getScene().getPlayerZ() < -700)
			getScene().reportal();
	}

	public String getName() {
		return "lilo";
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
		scene.setSceneDarkness(0);
		scene.draw(g);
	}
}
