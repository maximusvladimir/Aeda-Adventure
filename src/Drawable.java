import java.awt.Color;

public abstract class Drawable {
	private P3D boundStart;
	private P3D boundEnd;
	protected P3D pos;
	private P3D staticPos = null;
	private boolean visible = true;
	private Scene<Drawable> scene;
	public Drawable(Scene<Drawable> scene, P3D boundStart, P3D boundEnd) {
		this.boundStart = boundStart;
		this.boundEnd = boundEnd;
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
	public abstract void draw(int darkness);
	public abstract void tick();
	public abstract PointTesselator getTesselator();
	
	public boolean hit(Drawable d) {
		if (d.boundEnd == null || d.boundStart == null || boundStart == null || boundEnd == null || !isVisible() || !d.isVisible())
			return false;
		if (d.boundStart.x+d.pos.x > boundEnd.x+pos.x)
			return false;
		if (d.boundStart.y+d.pos.y > boundEnd.y+pos.y)
			return false;
		if (d.boundStart.z+d.pos.z > boundEnd.z+pos.z)
			return false;
		if (d.boundEnd.x+d.pos.x < boundStart.x+pos.x)
			return false;
		if (d.boundEnd.y+d.pos.y < boundStart.y+pos.y)
			return false;
		if (d.boundEnd.z+d.pos.z < boundStart.z+pos.z)
			return false;
		return true;
	}
}
