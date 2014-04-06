import java.awt.Color;


public class Horse extends Drawable {
	private PointTesselator tesselator;
	public Horse(Scene<Drawable> scene) {
		super(scene, new Hitbox());
		
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}
	float rpw = 0.0f;
	public void draw(int darkness) {
		//tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x, pos.y, pos.z,false);
		
		Color horseColor = Utility.adjustBrightness(new Color(233,235,234), -darkness);
		
		Rand rand = new Rand(324);
		rpw += 0.1f;
		//generateSide(rand,horseColor,-60,rpw);
		generateSide(rand,horseColor,60,rpw);
	}
	
	private void generateSide(Rand rand, Color horseColor, float z, float rpw) {
		float speed = 1.1f;
		float zoff = 0.0f;
		if (z < 0)
			zoff = (float)(Math.sin(rpw*speed) * 40 * speed);
		else
			zoff = (float)(Math.cos(rpw*speed) * 40 * speed);
		float zof3 = (-zoff/1.8f)+20;
		float zof2 = (-zoff/2.75f)+20;
		float lift = (float)(Math.sin(rpw*speed) * 40);
		zoff += 20;
		
		// midbody
		tesselator.color(rand.bright(horseColor,25));
		tesselator.point(-200, 100,z);
		tesselator.point(160, 100,z);
		tesselator.point(140, -35,z);
		
		tesselator.color(rand.bright(horseColor,25));
		tesselator.point(140,-35,z);
		tesselator.point(-200,100,z);
		tesselator.point(-185,-35,z);
		
		tesselator.color(Utility.adjustBrightness(rand.bright(horseColor,25),10));
		tesselator.point(-200,100,z);
		tesselator.point(-315,-35,z);
		tesselator.point(-185,-35,z);
		
		
		//backpart of front leg
		tesselator.color(rand.bright(horseColor, 25));
		tesselator.point(-315,-35,z);
		tesselator.point(-185,-35,z);
		tesselator.point(-220+zof2,-100,z);
		
		tesselator.color(rand.bright(horseColor, -35,-20));
		tesselator.point(-315,-35,z);
		tesselator.point(-300+zof2,-100,z);
		tesselator.point(-220+zof2,-100,z);
		
		tesselator.color(rand.bright(horseColor, -35,-20));
		tesselator.point(-300+zof2,-100,z);
		tesselator.point(-220+zof2,-100,z);
		tesselator.point(-250+zof3,-150,z);
		
		tesselator.color(Utility.adjustBrightness(horseColor, -40));
		tesselator.point(-300+zof2,-100,z);
		tesselator.point(-250+zof3,-150,z);
		tesselator.point(-260+zoff,-225+lift,z);
		
		tesselator.color(Utility.adjustBrightness(horseColor, -45));
		tesselator.point(-260+zoff,-225+lift,z);
		tesselator.point(-300+zof2,-100,z);
		tesselator.point(-300+zoff,-225+lift,z);
		
		//front leg inner
		float fana = 35;
		if (z > 0)
			fana = -fana;
		tesselator.color(rand.bright(horseColor, -20,-10));
		tesselator.point(-315,-35,z+fana);
		tesselator.point(-185,-35,z+fana);
		tesselator.point(-220+zof2,-100,z+fana);
		
		tesselator.color(rand.bright(horseColor, -45,-35));
		tesselator.point(-315,-35,z+fana);
		tesselator.point(-300+zof2,-100,z+fana);
		tesselator.point(-220+zof2,-100,z+fana);
		
		tesselator.color(rand.bright(horseColor, -40,-30));
		tesselator.point(-300+zof2,-100,z+fana);
		tesselator.point(-220+zof2,-100,z+fana);
		tesselator.point(-250+zof3,-150,z+fana);
		
		tesselator.color(Utility.adjustBrightness(horseColor, -50));
		tesselator.point(-300+zof2,-100,z+fana);
		tesselator.point(-250+zof3,-150,z+fana);
		tesselator.point(-260+zoff,-225+lift,z+fana);
		
		tesselator.color(Utility.adjustBrightness(horseColor, -40));
		tesselator.point(-260+zoff,-225+lift,z+fana);
		tesselator.point(-300+zof2,-100,z+fana);
		tesselator.point(-300+zoff,-225+lift,z+fana);
		
		//outside part
		float mid = -250+zof3;
		float upp = -300+zof2;
		tesselator.color(rand.bright(horseColor, -30,-10));
		tesselator.point(-315,-35,z+fana);
		tesselator.point(-315,-35,z);
		tesselator.point(upp,-100,z);
		
		tesselator.color(rand.bright(horseColor, -30,-10));
		tesselator.point(-315,-35,z+fana);
		tesselator.point(upp,-100,z+fana);
		tesselator.point(upp,-100,z);
		
		tesselator.color(rand.bright(horseColor, -40,-25));
		tesselator.point(upp,-100,z);
		tesselator.point(mid,-150,z);
		tesselator.point(mid,-150,z+fana);
		
		tesselator.color(rand.bright(horseColor, -40,-25));
		tesselator.point(upp,-100,z);
		tesselator.point(upp,-100,z+fana);
		tesselator.point(mid,-150,z+fana);
				
		tesselator.color(rand.bright(horseColor, -50,-37));
		tesselator.point(-300+zoff,-225+lift,z);
		tesselator.point(mid,-150,z);
		tesselator.point(mid,-150,z+fana);
		
		tesselator.color(rand.bright(horseColor, -60,-37));
		tesselator.point(-300+zoff,-225+lift,z);
		tesselator.point(-300+zoff,-225+lift,z+fana);
		tesselator.point(mid,-150,z+fana);
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}