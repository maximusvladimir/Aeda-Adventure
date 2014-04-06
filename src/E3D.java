// Texture coordinate class.
public class E3D extends Operation3D{
	public float u, v;
	public E3D(float u, float v) {
		this.u = u;
		this.v = v;
		id = 4;
	}
	
	public E3D() {
		id = 4;
	}
}
