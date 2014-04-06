public class $VertexDuplicationIndicesObject {
	public int originalVertices;
	public int[] indices = new int[0];
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("_____VertexDuplicationIndices_____\n"+originalVertices+"\n");
		for (int i = 0; i < indices.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(indices[i]);
		}
		return(sb.toString());
	}
}