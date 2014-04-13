
public class Strings {
	public static Strings inst = null;
	
	static {
		inst = new ENUS_Strings();
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
		
		SAIL_RAFT_RULE_NOTE = "You must be in the water to use your raft.";
		SAIL_RAFT_CANT_LEAVE = "You can not leave a raft in deep water.";
		
		NAME_FIACE = "Fi\u00E4ce Forest";
		NAME_HOLM = "Holm Village";
		NAME_HARBOUR = "Sailor's Harbour";
		NAME_HARBOUR_SHORT = "Harbour";
		NAME_CADEN_SEA = "Caden Sea";
	}
	
	public String NAME_FIACE;
	public String NAME_HOLM;
	public String NAME_HARBOUR;
	public String NAME_HARBOUR_SHORT;
	public String NAME_CADEN_SEA;
	
	public String HOLM_VILLAGE_SIGN;
	public String HOLM_VILLAGE_NORTH_ENTRY;
	public String HOLM_VILLAGE_CASSIUS_O;
	public String HOLM_VILLAGE_CASSIUS_H;
	public String HOLM_VILLAGE_GRANDMA_O;
	public String HOLM_VILLAGE_GRANDMA_H;
	public String HOLM_VILLAGE_CHARLES_O;
	public String HOLM_VILLAGE_CHARLES_H;
	public String HOLM_VILLAGE_RULF_O;
	public String HOLM_VILLAGE_RULF_H;
	public String HOLM_VILLAGE_WELL_MSG_0;
	public String HOLM_HAUZ_GRAND_PURCHASED_RAFT;
	public String HOLM_HAUZ_GRAND_NOT_PURCHASED_RAFT;
	public String SHOP_ENTRY_MSG;
	public String SHOP_LEAVE_MSG;
	public String SHOP_DISPL_RAFT;
	public String SHOP_DISPL_MOON;
	public String SHOP_DISPL_LAMP;
	public String SHOP_DISPL_HART;
	public String SHOP_NO_FUNDS;
	
	public String SAIL_RAFT_RULE_NOTE;
	public String SAIL_RAFT_CANT_LEAVE;
}

class ENUS_Strings extends Strings {
	public ENUS_Strings() {
		super();
	}
}