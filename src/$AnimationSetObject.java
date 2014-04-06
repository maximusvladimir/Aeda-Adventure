import java.util.*;

public class $AnimationSetObject {
	public String name;
	public Vector AnimationList = new Vector();
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AnimationSet name: "+name+"\n");
		for (int i = 0; i < AnimationList.size(); i++) {
			sb.append(AnimationList.elementAt(i).toString()+"\n");
		}
		sb.append("\n______AnimationSet_"+name+"_______\n");
		return(sb.toString());
	}
}