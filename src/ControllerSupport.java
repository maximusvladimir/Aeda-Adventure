import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerSupport {
	private Controller c;
	public ControllerSupport() {
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment()
				.getControllers();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i].getType() == Controller.Type.UNKNOWN || ca[i].getType() == Controller.Type.MOUSE || ca[i].getType() == Controller.Type.KEYBOARD)
				continue;
			c = ca[i];
			break;
			//System.out.println(ca[i].getName() + "," + ca[i].getType());
		}
	}
	
	public boolean isJumping() {
		return jump;
	}
	
	public boolean isAttacking() {
		return attack;
	}
	
	public float getMoveDir() {
		return moveVal;
	}

	public boolean isAvaliable() {
		return c != null;
	}
	private float moveVal = 0.0f;
	private boolean attack = false;
	private boolean jump = false;
	public void update() {
		c.poll();
		moveVal = 0.0f;
		attack = false;
		jump = false;
		for (int i = 0; i <c.getComponents().length;i++) {
			if (c.getComponents()[i].getName().toLowerCase().indexOf("hat") != -1) {
				float hatParse = c.getComponents()[i].getPollData();
				if (hatParse < 0.001)
					continue;
				else
					moveVal = hatParse;
			}
			if (c.getComponents()[i].getName().indexOf("n 2") != -1) {
				if (c.getComponents()[i].getPollData() == 1)
					attack = true;
			}
			if (c.getComponents()[i].getName().indexOf("n 1") != -1) {
				if (c.getComponents()[i].getPollData() == 1)
					jump = true;
			}
		}
	}
}