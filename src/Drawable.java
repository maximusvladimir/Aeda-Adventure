import java.awt.Color;

public abstract class Drawable {
	private Hitbox hitbox;
	protected P3D pos;
	private P3D staticPos = null;
	private boolean visible = true;
	private Scene<Drawable> scene;
	public Drawable(Scene<Drawable> scene, Hitbox hitbox) {
		if (hitbox == null)
			throw new IllegalArgumentException("For no hitbox, please provide a nullary constructor of the hitbox class.");
		this.hitbox = hitbox;
		this.hitbox.setDrawable(this);
		pos = new P3D(0,0,0);
		staticPos = new P3D(0,0,0);
		this.scene = scene;
	}
	
	public void setInstanceLoc(P3D st) {
		staticPos = st;
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
