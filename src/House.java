import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class House extends Drawable {
	private PointTesselator tesselator;
	private String houseName = null;

	public House(Scene<Drawable> scene) {
		super(scene, new Hitbox(new P3D(-890, 0, -600), new P3D(890, 600, 395)));
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
	}
	
	public void setDelta(float d) {
		delta = d;
	}
	
	public float getDelta() {
		return delta;
	}
	
	private String ownerName = "";

	public void setHouseName(String name) {
		houseName = name;
	}

	public String getHouseName() {
		return houseName;
	}
	
	public void setOwnerName(String owner) {
		ownerName = owner;
	}
	
	public String getOwnerName() {
		return ownerName;
	}

	private float delta = 0.0f;

	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0, delta, 0);

		sideColor = Utility.adjustBrightness(new Color(191, 181, 169), -darkness);
		
		// delta += 0.05f;
		drawPane(new P3D(-890, 600, 395), new P3D(0, 0, 395), new P3D(890, 600,
				395), new P3D(0, 0, 395));
		drawPane(new P3D(-895, 600, -395), new P3D(0, 0, -395), new P3D(895,
				600, -395), new P3D(0, 0, -395));
		drawPaneSide(new P3D(-895, 600, 0), new P3D(-895, 0, 400), new P3D(
				-895, 600, 0), new P3D(-895, 0, -400));
		drawPaneSide(new P3D(895, 600, 0), new P3D(895, 0, 400), new P3D(895,
				600, 0), new P3D(895, 0, -400));

		// door
		tesselator.color(60, 60, 60);
		tesselator.point(-150, 0, 404);
		tesselator.point(-150, 500, 404);
		tesselator.point(150, 500, 404);
		tesselator.point(-150, 0, 404);
		tesselator.point(150, 0, 404);
		tesselator.point(150, 500, 404);

		drawWindow(new P3D(-325, 100, -400), new P3D(-725, 500, -430));
		drawWindow(new P3D(355, 100, -400), new P3D(755, 500, -430));

		drawWindow(new P3D(-325, 100, 400), new P3D(-725, 500, 430));
		drawWindow(new P3D(355, 100, 400), new P3D(755, 500, 430));

		drawLengthSide(new P3D(-903, 0, -260), new P3D(-903, 600, -300));
		drawLengthSide(new P3D(-903, 0, 260), new P3D(-903, 600, 300));
		drawLengthSide(new P3D(-903, 0, -20), new P3D(-903, 600, 20));
		drawLengthSide(new P3D(-903, 500, -415), new P3D(-903, 460, 415));
		drawLengthSide(new P3D(-903, 100, -415), new P3D(-903, 140, 415));
		drawLengthSide(new P3D(903, 0, -260), new P3D(903, 600, -300));
		drawLengthSide(new P3D(903, 0, 260), new P3D(903, 600, 300));
		drawLengthSide(new P3D(903, 0, -20), new P3D(903, 600, 20));
		drawLengthSide(new P3D(903, 500, -415), new P3D(903, 460, 415));
		drawLengthSide(new P3D(903, 100, -415), new P3D(903, 140, 415));

		// drawLength(new P3D(-715,0, 403), new P3D(-760,600,403));
		// drawLength(new P3D(-545,0, 403), new P3D(-505,600,403));
		// drawLength(new P3D(-322,0, 403), new P3D(-367,600,403));
		drawLength(new P3D(-715, 0, 403), new P3D(-760, 650, 403));
		drawLength(new P3D(-545, 0, 403), new P3D(-505, 730, 403));
		drawLength(new P3D(-322, 0, 403), new P3D(-367, 820, 403));
		drawLength(new P3D(-900, 500, 403), new P3D(900, 460, 403));
		drawLength(new P3D(-900, 100, 403), new P3D(900, 140, 403));
		drawLength(new P3D(715, 0, 403), new P3D(760, 650, 403));
		drawLength(new P3D(560, 0, 403), new P3D(520, 730, 403));
		drawLength(new P3D(322, 0, 403), new P3D(367, 820, 403));

		tesselator.point(-900, 600, 403);
		tesselator.point(0, 1000, 403);
		tesselator.point(0, 950, 403);
		tesselator.point(0, 950, 403);
		tesselator.point(-900, 560, 403);
		tesselator.point(-900, 600, 403);
		tesselator.point(900, 600, 403);
		tesselator.point(0, 1000, 403);
		tesselator.point(0, 950, 403);
		tesselator.point(0, 950, 403);
		tesselator.point(900, 560, 403);
		tesselator.point(900, 600, 403);

		tesselator.point(-900, 600, -403);
		tesselator.point(0, 1000, -403);
		tesselator.point(0, 950, -403);
		tesselator.point(0, 950, -403);
		tesselator.point(-900, 560, -403);
		tesselator.point(-900, 600, -403);
		tesselator.point(900, 600, -403);
		tesselator.point(0, 1000, -403);
		tesselator.point(0, 950, -403);
		tesselator.point(0, 950, -403);
		tesselator.point(900, 560, -403);
		tesselator.point(900, 600, -403);
		// drawLength(new P3D(900,500,403), new P3D(150,460,403));
		// drawLength(new P3D(900,100,403), new P3D(150,140,403));

		drawSideRoof(new P3D(-895, 580, -400), new P3D(895, 580, -400), 400);
		drawSideRoof(new P3D(-895, 580, 400), new P3D(895, 580, 400), 400);

		// drawRoof(new P3D(-930,600,400), new P3D(930,600,-400),200);
		drawRoof(new P3D(900, 600, -450), new P3D(-900, 600, 450), 400);

		if (getHouseName() != null) {
			Font3D font = new Font3D();
			font.color = Color.black;
			font.loc = new P3D(-28.75f * 0.5f * getHouseName().length(), 600, 405);
			font.font = fontCache;
			font.str = getHouseName();
			tesselator.text(font);
		}
	}

	private static Font fontCache = new Font("Courier New", 0, 16);
	
	private boolean isShowing = false;
	private boolean alreadyShown = false;
	public void tick() {
		if (getScene().getLevel().isGameHalted())
			return;
		setInstanceLoc(getInstanceLoc().x,getInstanceLoc().z+500);
		float dist = getDistToPlayer();
		setInstanceLoc(getInstanceLoc().x,getInstanceLoc().z-500);
		if (dist < 290 && !alreadyShown && !isShowing) {
			if (!lightsOn) {
				isShowing = true;
				if (getOwnerName().equals("Grandma")) {
					getScene().getLevel().addMessage(getOwnerName() + " isn't here.\nPerhaps you should go back to Fiace Forest and get her.", getHouseName());
				}
				else
					getScene().getLevel().addMessage(getOwnerName() + " isn't home right now.", getHouseName());
				getScene().getLevel().setActiveMessage(getHouseName());
			}
			else if (getScene().canPortalize() && getScene().getLevel().getMain().screenExists(getHouseName())) {
				getScene().getLevel().startTransition(getScene().getLevel().getMain().getScreen(getHouseName()),new P3D(0,0,0),0);
				getScene().deportal();
				getScene().setPlayerZ(getScene().getPlayerZ()+400);
			}
		}
		else if (dist > 290) {
			alreadyShown = false;
			isShowing = false;
			getScene().reportal();
		}
	}

	private void drawRoof(P3D p0, P3D p1, float height) {
		tesselator.color(85, 80, 62);
		tesselator.point(p0);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, p0.z);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, 0);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, 0);
		tesselator.point(p0.x, p0.y, 0);
		tesselator.point(p0);

		tesselator.point(p0.x, p1.y, p1.z);
		tesselator.point((p0.x + p1.x) / 2, p1.y + height, p1.z);
		tesselator.point((p0.x + p1.x) / 2, p1.y + height, 0);
		tesselator.point((p0.x + p1.x) / 2, p1.y + height, 0);
		tesselator.point(p0.x, p1.y, 0);
		tesselator.point(p0.x, p1.y, p1.z);

		tesselator.point(p1);
		tesselator.point((p1.x + p0.x) / 2, p1.y + height, p1.z);
		tesselator.point((p1.x + p0.x) / 2, p1.y + height, 0);
		tesselator.point((p1.x + p0.x) / 2, p1.y + height, 0);
		tesselator.point(p1.x, p1.y, 0);
		tesselator.point(p1);

		tesselator.point(p1.x, p0.y, p0.z);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, p0.z);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, 0);
		tesselator.point((p0.x + p1.x) / 2, p0.y + height, 0);
		tesselator.point(p1.x, p0.y, 0);
		tesselator.point(p1.x, p0.y, p0.z);
	}

	/*
	 * private void drawRoof(P3D p0, P3D p1, float height) {
	 * tesselator.color(85,80,62); tesselator.point(p0);
	 * tesselator.point(p0.x,p0.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p0.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p0.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p0.y,p0.z); tesselator.point(p0);
	 * 
	 * tesselator.point(p1.x,p1.y,p0.z); tesselator.point(p1.x,p1.y+height,(p0.z
	 * + p1.z)/2); tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y,p0.z); tesselator.point(p1.x,p1.y,p0.z);
	 * 
	 * tesselator.point(p1); tesselator.point(p1.x,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y,p1.z); tesselator.point(p1);
	 * 
	 * tesselator.point(p0.x,p0.y,p1.z); tesselator.point(p0.x,p0.y+height,(p0.z
	 * + p1.z)/2); tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y+height,(p0.z + p1.z)/2);
	 * tesselator.point(0,p1.y,p1.z); tesselator.point(p0.x,p0.y,p1.z); }
	 */
	private void drawSideRoof(P3D p0, P3D p1, float height) {
		tesselator.color(sideColor);
		tesselator.point(p0.x, p0.y, p0.z);
		tesselator.point((p0.x + p1.x) / 2, (p0.y + p1.y) / 2 + height,
				(p0.z + p1.z) / 2);
		tesselator.point(p1.x, p1.y, p1.z);
	}

	private void drawPane(P3D tr0, P3D bl0, P3D tr1, P3D bl1) {
		tesselator.color(sideColor);
		tesselator.point(bl0.x, bl0.y, tr0.z);
		tesselator.point(tr0.x, bl0.y, tr0.z);
		tesselator.point(tr0.x, tr0.y, tr0.z);
		tesselator.point(tr0.x, tr0.y, tr0.z);
		tesselator.point(bl0.x, tr0.y, tr0.z);
		tesselator.point(bl0.x, bl0.y, tr0.z);

		tesselator.point(bl1.x, bl1.y, tr1.z);
		tesselator.point(tr1.x, bl1.y, tr1.z);
		tesselator.point(tr1.x, tr1.y, tr1.z);
		tesselator.point(tr1.x, tr1.y, tr1.z);
		tesselator.point(bl1.x, tr1.y, tr1.z);
		tesselator.point(bl1.x, bl1.y, tr1.z);
	}

	private void drawPaneSide(P3D tr0, P3D bl0, P3D tr1, P3D bl1) {
		tesselator.color(sideColor);
		tesselator.point(tr0.x, bl0.y, bl0.z);
		tesselator.point(tr0.x, bl0.y, tr0.z);
		tesselator.point(tr0.x, tr0.y, tr0.z);
		tesselator.point(tr0.x, tr0.y, tr0.z);
		tesselator.point(tr0.x, tr0.y, bl0.z);
		tesselator.point(tr0.x, bl0.y, bl0.z);

		tesselator.point(tr1.x, bl1.y, bl1.z);
		tesselator.point(tr1.x, bl1.y, tr1.z);
		tesselator.point(tr1.x, tr1.y, tr1.z);
		tesselator.point(tr1.x, tr1.y, tr1.z);
		tesselator.point(tr1.x, tr1.y, bl1.z);
		tesselator.point(tr1.x, bl1.y, bl1.z);
	}
	
	private Color sideColor = null;

	private void drawLengthSide(P3D tr, P3D bl) {
		tesselator.color(100, 92, 79);
		tesselator.point(tr.x, tr.y, tr.z);
		tesselator.point(tr.x, bl.y, tr.z);
		tesselator.point(tr.x, bl.y, bl.z);
		tesselator.point(tr.x, bl.y, bl.z);
		tesselator.point(tr.x, tr.y, bl.z);
		tesselator.point(tr.x, tr.y, tr.z);
	}

	private void drawLength(P3D tr, P3D bl) {
		tesselator.color(100, 92, 79);
		tesselator.point(tr.x, tr.y, tr.z);
		tesselator.point(tr.x, bl.y, tr.z);
		tesselator.point(bl.x, bl.y, tr.z);
		tesselator.point(bl.x, bl.y, tr.z);
		tesselator.point(bl.x, tr.y, tr.z);
		tesselator.point(tr.x, tr.y, tr.z);
	}
	private long offset = 0;
	private void drawWindow(P3D bottomRight, P3D topLeft) {
		P3D b = bottomRight;
		P3D t = topLeft;
		offset++;
		float px = Math.abs(b.x - t.x) / 2;
		if (t.x < b.x)
			px = -px;
		px = px + b.x;
		float bh = (b.z + t.z) / 2;
		tesselator.color(69, 55, 12);
		// windows
		// front of frame
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(t.x, b.y, t.z);
		tesselator.point(t.x, b.y + 30, t.z);
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(b.x, b.y + 30, t.z);
		tesselator.point(t.x, b.y + 30, t.z);
		// top of frame
		tesselator.point(t.x, b.y + 30, t.z);
		tesselator.point(b.x, b.y + 30, t.z);
		tesselator.point(b.x, b.y + 30, b.z);
		tesselator.point(t.x, b.y + 30, t.z);
		tesselator.point(t.x, b.y + 30, b.z);
		tesselator.point(b.x, b.y + 30, b.z);
		// left pane
		tesselator.point(t.x, b.y + 30, b.z);
		tesselator.point(t.x, b.y, b.z);
		tesselator.point(t.x, b.y, t.z);
		tesselator.point(t.x, b.y + 30, b.z);
		tesselator.point(t.x, b.y + 30, t.z);
		tesselator.point(t.x, b.y, t.z);
		// right pane
		tesselator.point(b.x, b.y + 30, b.z);
		tesselator.point(b.x, b.y, b.z);
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(b.x, b.y + 30, b.z);
		tesselator.point(b.x, b.y + 30, t.z);
		tesselator.point(b.x, b.y, t.z);
		// drawWindow(new P3D(-225,b.y,0), new P3D(t.x,t.y,t.z)bt);
		// front of frame
		tesselator.point(b.x, t.y, t.z);
		tesselator.point(t.x, t.y, t.z);
		tesselator.point(t.x, t.y - 30, t.z);
		tesselator.point(b.x, t.y, t.z);
		tesselator.point(b.x, t.y - 30, t.z);
		tesselator.point(t.x, t.y - 30, t.z);
		// bottom of frame
		tesselator.point(t.x, t.y - 30, t.z);
		tesselator.point(b.x, t.y - 30, t.z);
		tesselator.point(b.x, t.y - 30, b.z);
		tesselator.point(t.x, t.y - 30, t.z);
		tesselator.point(t.x, t.y - 30, b.z);
		tesselator.point(b.x, t.y - 30, b.z);
		// left pane
		tesselator.point(t.x, t.y - 30, b.z);
		tesselator.point(t.x, t.y, b.z);
		tesselator.point(t.x, t.y, t.z);
		tesselator.point(t.x, t.y - 30, b.z);
		tesselator.point(t.x, t.y - 30, t.z);
		tesselator.point(t.x, t.y, t.z);
		// right pane
		tesselator.point(b.x, b.y + 30, b.z);
		tesselator.point(b.x, b.y, b.z);
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(b.x, b.y + 30, b.z);
		tesselator.point(b.x, b.y + 30, t.z);
		tesselator.point(b.x, b.y, t.z);

		// front of frame
		tesselator.point(b.x, t.y, t.z);
		tesselator.point(b.x - 30, t.y, t.z);
		tesselator.point(b.x - 30, b.y, t.z);
		tesselator.point(b.x, t.y, t.z);
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(b.x - 30, b.y, t.z);
		// left pane
		tesselator.point(b.x, b.y, b.z);
		tesselator.point(b.x, t.y, b.z);
		tesselator.point(b.x, t.y, t.z);
		tesselator.point(b.x, b.y, b.z);
		tesselator.point(b.x, b.y, t.z);
		tesselator.point(b.x, t.y, t.z);
		// right pane
		tesselator.point(b.x - 30, t.y, b.z);
		tesselator.point(b.x - 30, b.y, b.z);
		tesselator.point(b.x - 30, b.y, t.z);
		tesselator.point(b.x - 30, t.y, b.z);
		tesselator.point(b.x - 30, t.y, t.z);
		tesselator.point(b.x - 30, b.y, t.z);

		// front of frame
		tesselator.point(t.x, t.y, t.z);
		tesselator.point(t.x - 30, t.y, t.z);
		tesselator.point(t.x - 30, b.y, t.z);
		tesselator.point(t.x, t.y, t.z);
		tesselator.point(t.x, b.y, t.z);
		tesselator.point(t.x - 30, b.y, t.z);
		// left pane
		tesselator.point(t.x, b.y, b.z);
		tesselator.point(t.x, t.y, b.z);
		tesselator.point(t.x, t.y, t.z);
		tesselator.point(t.x, b.y, b.z);
		tesselator.point(t.x, b.y, t.z);
		tesselator.point(t.x, t.y, t.z);
		// right pane
		tesselator.point(t.x - 30, t.y, b.z);
		tesselator.point(t.x - 30, b.y, b.z);
		tesselator.point(t.x - 30, b.y, t.z);
		tesselator.point(t.x - 30, t.y, b.z);
		tesselator.point(t.x - 30, t.y, t.z);
		tesselator.point(t.x - 30, b.y, t.z);

		// front of frame
		tesselator.point(px + 15, t.y, t.z);
		tesselator.point(px - 15, t.y, t.z);
		tesselator.point(px - 15, b.y, t.z);
		tesselator.point(px + 15, t.y, t.z);
		tesselator.point(px + 15, b.y, t.z);
		tesselator.point(px - 15, b.y, t.z);
		// left pane
		tesselator.point(px + 15, b.y, b.z);
		tesselator.point(px + 15, t.y, b.z);
		tesselator.point(px + 15, t.y, t.z);
		tesselator.point(px + 15, b.y, b.z);
		tesselator.point(px + 15, b.y, t.z);
		tesselator.point(px + 15, t.y, t.z);
		// right pane
		tesselator.point(px - 15, t.y, b.z);
		tesselator.point(px - 15, b.y, b.z);
		tesselator.point(px - 15, b.y, t.z);
		tesselator.point(px - 15, t.y, b.z);
		tesselator.point(px - 15, t.y, t.z);
		tesselator.point(px - 15, b.y, t.z);

		if (lightsOn) {
			windowGlow += 0.01f * Math.random();
			//tesselator.color(236, 200, 55);
			tesselator.color(Utility.adjustBrightness(new Color(236,200,55), 
					(int)(Math.cos(windowGlow+(offset%4)) * 30)));
		}
		else
			tesselator.color(70, 70, 96);
		tesselator.point(t.x - 30, b.y + 30, bh);
		tesselator.point(b.x - 30, b.y + 30, bh);
		tesselator.point(b.x - 30, t.y - 30, bh);
		tesselator.point(b.x - 30, t.y - 30, bh);
		tesselator.point(t.x - 30, t.y - 30, bh);
		tesselator.point(t.x - 30, b.y + 30, bh);
	}
	
	private float windowGlow = 0.0f;

	public boolean lightsOn = false;

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
