
public class Strings {
	public static Strings inst = null;
	
	static {
		inst = new Strings();
	}
	
	public Strings() {
		HOLM_VILLAGE_SIGN = "Welcome to Holm Village.\n\u25B2 Graven Castle\n\u25BC Fiace Forest\n\u25C4 Sailor's Harbour\n\u25BA Prusa Cave";
		HOLM_VILLAGE_NORTH_ENTRY = "Thou shall not pass.";
	}
	
	public final String HOLM_VILLAGE_SIGN;
	public final String HOLM_VILLAGE_NORTH_ENTRY;
}
