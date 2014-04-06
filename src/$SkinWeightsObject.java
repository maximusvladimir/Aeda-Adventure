public class $SkinWeightsObject {
	public String name;
	public int[] vertexIndices = new int[0];
	public float[] weights = new float[0];
	public float[] matrix4x4 = new float[16];
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("_____SkinWeightsObject_"+name+"_____\n");
		sb.append("\nvertexIndices\n");
		for (int i = 0; i < vertexIndices.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(vertexIndices[i]);
		}
		sb.append("\nweights\n");
		for (int i = 0; i < weights.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(weights[i]);
		}
		sb.append("\nmatrix\n");
		for (int i = 0; i < matrix4x4.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(matrix4x4[i]);
		}
		return(sb.toString());
	}
}