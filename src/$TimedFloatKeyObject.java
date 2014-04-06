public class $TimedFloatKeyObject {
	int time;
	float[] keys;//This will probably always be size 3 but no gaurentee of that
	//from the format specification?
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(time+" "+keys.length+" ");
		for (int i = 0; i < keys.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(keys[i]);
		}
		return(sb.toString());
	}
}