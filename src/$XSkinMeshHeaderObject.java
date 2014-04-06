public class $XSkinMeshHeaderObject {
	public int maxSkinWeightsPerFace;
	public int maxSkinWeightsPerVertex;
	public int bones;//Number of bones in the model.
	
	public String toString() {
		return("XSkinMeshHeader "+maxSkinWeightsPerFace+" "+maxSkinWeightsPerVertex+" "+bones);
	}
}