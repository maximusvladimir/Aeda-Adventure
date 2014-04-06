import java.awt.Color;

public abstract class Drawable {
	private Hitbox hitbox;
	protected P3D pos;
	private P3D staticPos = null;
	private boolean visible = true;
	private Scene<Drawable> scene;
	private int iddark = 0;
	private boolean dead = false;
	public Drawable(Scene<Drawable> scene, Hitbox hitbox) {
		if (hitbox == null)
			throw new IllegalArgumentException("For no hitbox, please provide a nullary constructor of the hitbox class.");
		this.hitbox = hitbox;
		this.hitbox.setDrawable(this);
		pos = new P3D(0,0,0);
		staticPos = new P3D(0,0,0);
		this.scene = scene;
	}
	
	public void dropGoodies() {
		int csindexer = 0;
		if (Math.random() < 0.8)
			csindexer = (int) (Math.rint(Math.random() * 4) + 1);
		int heartsProvided = 0;
		for (int i = 0; i < csindexer; i++) {
			if (heartsProvided < 2 && GameState.instance.health < 5 && Math.random() < 0.4) {
				heartsProvided++;
				GameState.instance.health++;
				continue;
			}
			else if (heartsProvided < 2 && Math.random() < 0.05) {
				heartsProvided++;
				GameState.instance.health++;
				continue;
			}
			P3D poll = P3D.add(getInstanceLoc(), 
					new P3D((float)(Math.random() * 400) - 200,0,(float)(Math.random() * 400) - 200));
			poll.y = -170;
			Gem gem = null;
			if (Math.random() < 0.05)
				gem = new RedGem(getScene(),new Rand());
			else
				gem = new Gem(getScene(),new Rand());
			gem.setInstanceLoc(poll);
			getScene().add(gem);
		}
	}
	
	public boolean dead() {
		return dead;
	}
	
	public void makeDead() {
		dead = true;
	}
	
	public float getDistToPlayer() {
		float dist = new P3D(0, -100, -625).dist(new P3D(-getScene().getPlayerX() + getInstanceLoc().x, getInstanceLoc().y,
				-getScene().getPlayerZ() + getInstanceLoc().z));
		return dist;
	}
	
	public float getDistToPlayerZ() {
		return -625 + getScene().getPlayerZ() + getInstanceLoc().z;
	}
	
	public int getIndividualDarkness() {
		return iddark;
	}
	
	public void setIndividualDarkness(int value) {
		iddark = value;
	}
	
	public void setInstanceLoc(P3D st) {
		staticPos = st;
	}
	
	public void setInstanceLoc(float x, float y, float z) {
		setInstanceLoc(new P3D(x,y,z));
	}
	
	public void setInstanceLoc(float x, float z) {
		setInstanceLoc(x,getInstanceLoc().y,z);
	}
	
	public P3D getInstanceLoc() {
		return staticPos;
	}
	
	public Color getMapColor() {
		return Color.blue;
	}
	
	public boolean isCullable() {
		return true;
	}
	
	public Scene<Drawable> getScene() {
		return scene;
	}
	
	public void setPosition(float x, float y, float z) {
		setPosition(new P3D(x,y,z));
	}
	
	public void setPosition(P3D pos) {
		if (staticPos !=null) {
			this.pos = new P3D(pos.x+staticPos.x,pos.y+staticPos.y,pos.z+staticPos.z);
		}
		else
			this.pos =pos;
		hitbox.updatePosition(this.pos);
	}
	
	public P3D getPosition() {
		return pos;
	}
	
	public void setVisible(boolean value) {
		visible = value;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public abstract void draw(int darkness);
	public abstract void tick();
	public abstract PointTesselator getTesselator();
	
	public boolean hit(Drawable d) {
		return getHitbox().hit(d.getHitbox());
	}
}
