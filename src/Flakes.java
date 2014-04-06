import java.awt.Color;
import java.awt.Graphics;


public class Flakes {
	private Flake[] parts;
	private IMain main;
	private Rand rand;
	public Flakes(IMain main,Rand rand,int num) {
		parts = new Flake[num];
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Flake();
			parts[i].x = (float) (rand.nextDouble() * main.getWidth());
			parts[i].y = (float) (rand.nextDouble() * main.getHeight());
			parts[i].dx = (float) (rand.nextDouble() * 1.1f);
			parts[i].size = (int) (rand.nextDouble() * 4);
			parts[i].clr = (int) (rand.nextDouble() * 127) + 127;
			if (rand.nextDouble() < 0.5)
				parts[i].dx *= -1;
		}
		this.main = main;
		this.rand = rand;
	}
	
	public void tick() {
		for (int i = 0; i < parts.length; i++) {
			Flake flake = parts[i];
			flake.x += flake.dx * 0.3f;
			flake.y += flake.size * 0.05f;
			if (flake.dx < 0)
				if (flake.x < 0) {
					flake.x = (float) (main.getWidth() + (rand
							.nextDouble() * (main.getWidth() / 4.0f)));
				} else if (flake.x > main.getWidth())
					flake.x = (float) (-rand.nextDouble() * (main
							.getWidth() / 4.0f));
			if (rand.nextDouble() < 0.005)
				flake.size--;
			if (flake.size <= 0) {
				flake.y = (int) (rand.nextDouble() * main.getHeight());
				flake.size = (int) ((rand.nextDouble() * 3) + 4);
			}
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < parts.length; i++) {
			Flake flake = parts[i];
			g.setColor(new Color(255, 255, 255, 100));
			if (flake.size == 1)
				g.drawLine((int) flake.x, (int) flake.y, (int) flake.x,
						(int) flake.y);
			else
				g.drawOval((int) flake.x, (int) flake.y, flake.size, flake.size);
		}
	}
}
