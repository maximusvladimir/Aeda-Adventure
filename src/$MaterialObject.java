public class $MaterialObject {
	public String materialName;
	
	public String textureName;
	
	public float power;
	//power is the specular exponent of the material, shininess?
	
	public float r;//RGBa colors
	public float g;
	public float b;
	public float a;
	
	public float sr;//Specular red
	public float sg;
	public float sb;
	public float er;//Emissive red
	public float eg;
	public float eb;
	
	public String toString() {
		return("Material name:"+materialName+" tex:"+textureName+" "+power+" "+r+","+g+","+b+","+a+" "+
		sr+","+sg+","+sb+","+er+","+eg+","+eb);
	}
	
}