// 3D Rotation class.
public class R3D extends Operation3D {
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	public R3D() {
		id = 2;
	}
	
	public R3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		id = 2;
	}
}
