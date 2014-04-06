public class $Face {
	//index to the individual verts used.
	public int vertIndex[];
	//sides = vertIndex.length
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vertIndex.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(vertIndex[i]);
		}
		return(sb.toString());
	}
}