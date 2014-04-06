
public class Strings {
	public static Strings inst = null;
	
	static {
		inst = new Strings();
	}
	
	public Strings() {
		HOLM_VILLAGE_SIGN = "Welcome to Holm Village.\n\u25B2 Graven Castle\n\u25BC Fiace Forest\n\u25C4 Sailor's Harbour\n\u25BA Prusa Cave";
		HOLM_VILLAGE_NORTH_ENTRY = "Thou shall not pass.";
		HOLM_VILLAGE_GRANDMA_H = "Grandma Gwendolynn";
		HOLM_VILLAGE_GRANDMA_O = "Grandma";
		HOLM_VILLAGE_CASSIUS_H = "Swordsmaster Cassius";
		HOLM_VILLAGE_CASSIUS_O = "Master Cassius";
		HOLM_VILLAGE_CHARLES_O = "Count Charles";
		HOLM_VILLAGE_CHARLES_H = "Count Charles";
		HOLM_VILLAGE_RULF_H = "Rulf's Shop";
		HOLM_VILLAGE_RULF_O = "Rulf";
		HOLM_VILLAGE_WELL_MSG_0 = "This old well is very deep and filled with water.\nSomething appears to be moving in it.";
		HOLM_HAUZ_GRAND_PURCHASED_RAFT = GameState.instance.playerGUID + " that's a nice raft you have there.\nYour Grandpa had one just like it.\nHe use to go to Sailor's Harbour and fish.";
		HOLM_HAUZ_GRAND_NOT_PURCHASED_RAFT = "Have you considered buying a raft? I hear they're really cheap at\nRulf's Shop";
		SHOP_ENTRY_MSG = "Welcome to Rulf's Shop!\nI'm in the back, but you can toggle options with \"W\".\nYou can perform a transaction with \"E\".\nExit with \"ESC\".";
		SHOP_LEAVE_MSG = "Thanks for coming. Come again any time!";
		SHOP_DISPL_RAFT = "Raft (200 Gems)";
		SHOP_DISPL_MOON = "Moonstone (400 Gems)";
		SHOP_DISPL_HART = "Heart Piece (800 Gems)";
		SHOP_DISPL_LAMP = "Lamp (600 Gems)";
		SHOP_NO_FUNDS = "Sorry. Insufficent funds.";
	}
	
	public final String HOLM_VILLAGE_SIGN;
	public final String HOLM_VILLAGE_NORTH_ENTRY;
	public final String HOLM_VILLAGE_CASSIUS_O;
	public final String HOLM_VILLAGE_CASSIUS_H;
	public final String HOLM_VILLAGE_GRANDMA_O;
	public final String HOLM_VILLAGE_GRANDMA_H;
	public final String HOLM_VILLAGE_CHARLES_O;
	public final String HOLM_VILLAGE_CHARLES_H;
	public final String HOLM_VILLAGE_RULF_O;
	public final String HOLM_VILLAGE_RULF_H;
	public final String HOLM_VILLAGE_WELL_MSG_0;
	public final String HOLM_HAUZ_GRAND_PURCHASED_RAFT;
	public final String HOLM_HAUZ_GRAND_NOT_PURCHASED_RAFT;
	public final String SHOP_ENTRY_MSG;
	public final String SHOP_LEAVE_MSG;
	public final String SHOP_DISPL_RAFT;
	public final String SHOP_DISPL_MOON;
	public final String SHOP_DISPL_LAMP;
	public final String SHOP_DISPL_HART;
	public final String SHOP_NO_FUNDS;
}
