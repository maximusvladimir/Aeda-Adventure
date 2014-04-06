import java.util.*;

public class $FrameModel {
	public String name;
	public float[] transformMatrix = {
		1f,0f,0f,0f,
		0f,1f,0f,0f,
		0f,0f,1f,0f,
		0f,0f,0f,1f
	};
	public Vector FrameModelList = new Vector();
	public Vector MeshObjectList = new Vector();
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Frame name: "+name+"\n");
		for (int i = 0; i < transformMatrix.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(transformMatrix[i]);
		}
		for (int i = 0; i < MeshObjectList.size(); i++) {
			sb.append(MeshObjectList.elementAt(i).toString()+"\n");
		}
		sb.append("\n______Frame_"+name+"_______\n");
		return(sb.toString());
	}
}