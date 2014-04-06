import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Message {
	private String msg = "AN UNKNOWN ERROR HAS OCCURED.";
	private String name = "";
	private ActionListener closeEvent = null;
	private Level level;
	private float characters = 0;
	private boolean rem;
	public Message(String tag,Level level) {
		name = tag;
		this.level = level;
	}
	
	public void setRemoveAtFinish(boolean b) {
		rem = b;
	}
	
	public boolean removingAtFinish() {
		return rem;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
	}
	
	public void setCloseEvent(ActionListener l) {
		closeEvent = l;
	}
	
	public void doCloseEvent() {
		if (closeEvent == null)
			return;
		ActionEvent event = new ActionEvent(this,0,null);
		closeEvent.actionPerformed(event);
	}
	
	private boolean currentOption = false;
	private boolean optionMessage = false;
	public void toggleOption() {
		currentOption = !currentOption;
	}
	
	public void setOptionMessage(boolean val) {
		optionMessage = val;
	}
	
	public boolean getResult() {
		return currentOption;
	}
	
	public void physicalTick() {
		characters += 0.25f;
	}
	
	public void tick(Graphics g, IMain m) {
		String messageBuilder = getMessage();
		if (messageBuilder.indexOf("<PAUSE>") != -1) {
			messageBuilder = messageBuilder.replaceAll("<PAUSE>", "¢¢¢¢¢¢¢¢¢¢¢¢¢¢¢¢¢¢¢");
		}
		if (characters < messageBuilder.length()) {
			String oth = "";
			for (int i = 0; i < (int)characters; i++) {
				char c = messageBuilder.charAt(i);
				if (c == '¢')
					continue;
				oth += c;
			}
			if (optionMessage) {
				oth += "\n\n";
				if(currentOption) {
					oth += ">Yes< No  (Press W to toggle)";
				}
				else {
					oth += "Yes >No<  (Press W to toggle)";
				}
			}
			Utility.showDialog(oth, g, m);
		}
		else {
			if (optionMessage) {
				messageBuilder += "\n\n";
				if(currentOption) {
					messageBuilder += ">Yes< No  (Press W to toggle)";
				}
				else {
					messageBuilder += "Yes >No<  (Press W to toggle)";
				}
			}
			Utility.showDialog(messageBuilder, g, m);
		}
	}
}
