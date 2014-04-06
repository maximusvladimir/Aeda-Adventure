import java.awt.Color;
import java.awt.Polygon;
// 3D Triangle class.

public class T3D {
	public int[] tri;
	public float zdepth;
	public Color c0i, c1i, c2i;
	public float tou, tov;
	public P3D p0, p1, p2;
	public P3D pr0, pr1, pr2;
	public PointTesselator root;
	public float darkness=-1;
	public float[] lightingColor;
	public float[] fogHintColor;
	public double dist;
	public P3D n;
	public Font3D font;
	public T3D() {
		
	}
	
	public T3D(PointTesselator root,int[] tri, float z0, float z1, float z2,
			Color c0, Color c1, Color c2, float tou, float tov,P3D p0, P3D p1, P3D p2, P3D n) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.n = n;
		this.root = root;
		this.tri = tri;
		zdepth = (z0 + z1 + z2) * 0.333333333333f;
		/*float hx = (p0.x + p1.x+ p2.x)*0.333333333333f;
		float hy = (p0.y + p1.y+ p2.y)*0.333333333333f;
		float hz = (p0.z + p1.z+ p2.z)*0.333333333333f;
		zdepth = (float)Math.sqrt(hx*hx+hy*hy+hz*hz);*/
		if (c1 == null)
			c1 = c0;
		if (c2 == null)
			c2 = c0;
		if (c0 == null)
			throw new IllegalArgumentException("Error! First color must be non-null!");
		c0i = c0;
		c1i = c1;
		c2i = c2;
		this.tou = tou;
		this.tov = tov;
	}

	public boolean calculateDistances(float zTranslateDepth) {
		P3D fp0 = p0;
		P3D fp1 = p1;
		P3D fp2 = p2;
		if (fp0 == null || fp1 == null || fp2 == null)
			return false;
		//double dis0 = Math.sqrt(fp0.x * fp0.x + fp0.y * fp0.y + fp0.z * fp0.z);
		//double dis1 = Math.sqrt(fp1.x * fp1.x + fp1.y * fp1.y + fp1.z * fp1.z);
		//double dis2 = Math.sqrt(fp2.x * fp2.x + fp2.y * fp2.y + fp2.z * fp2.z);
		float dis0 = fp0.z - zTranslateDepth;
		float dis1 = fp1.z - zTranslateDepth;
		float dis2 = fp2.z - zTranslateDepth;
		dist = (dis0 + dis1 + dis2) * 0.3333333333333333;
		return true;
	}
}
