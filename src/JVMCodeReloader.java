
public class JVMCodeReloader {
	public static boolean reloadCode(Class klass) {
		String className = klass.toString();
		if (className.indexOf("class ") > -1)
			className = className.replace("class ", "");
		try {
			ClassLoader.getSystemClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
}
