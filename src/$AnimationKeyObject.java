public class $AnimationKeyObject {
	//keytype can be one of the following
	final static int ROTATION = 0;
	final static int SCALE = 1;
	final static int POSITION = 2;
	
	public int keytype;
	public $TimedFloatKeyObject[] TimedFloatKeys;
	//Position keys
	//number of keys?
	//time; floatsperkey?; (keyframetime?;vector3f;;,)
	//ends with ;;;
	//int; int; float, float, float;;,
	//I'll probably want to parse each line in it's own parser for ease of
	//dealing with the ;; and stuff.
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AnimationKey\n");
		sb.append(keytype+" "+TimedFloatKeys.length+"\n");
		for (int i = 0; i < TimedFloatKeys.length; i++) {
			sb.append(TimedFloatKeys[i].toString()+"\n");
		}
		sb.append("______AnimationKey______\n");
		return(sb.toString());
	}
}