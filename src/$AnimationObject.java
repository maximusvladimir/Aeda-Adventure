import java.util.*;

public class $AnimationObject {
	
	public String name;
	public Vector AnimationKeyList = new Vector();
	public $FrameModel frame;//Referenced by animationset, for bone animations?
	//I probably won't support animation options for now as they seem
	//to be pretty dx specific.
	//Vector AnimationOptionsList = new Vector();
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Animation "+name+"\n");
		for (int i = 0; i < AnimationKeyList.size(); i++) {
			sb.append(AnimationKeyList.elementAt(i)+"\n");
		}
		sb.append("{"+frame.name+"}\n");
		sb.append("______Animation_"+name+"______\n");
		return(sb.toString());
	}
}