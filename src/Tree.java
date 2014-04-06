import java.awt.Color;


public class Tree extends Drawable {
	private PointTesselator tesselator;
	public Tree(Scene<Drawable> scene,Rand rand) {
		super(scene,new Hitbox(new P3D(-20,0,-20),new P3D(40,900,40)));
		tesselator = new PointTesselator();
		delta = (float)(rand.nextDouble() * Math.PI * 2);
		swayx = (float)(rand.nextDouble() * 6.5f);
		swayz = (float)(rand.nextDouble() * 6.5f);
		height = (float)(rand.nextDouble() * 600) + 900;
		swayr = height/35.0f;
		leafColor = new Color(67,100,0);
		if (rand.nextFloat() < 0.2) {
			leafColor = new Color(174,150,13);
			swayx *= 0.5f;
			swayz *= 0.5f;
		}

		//leaf1Offset = 100;//(float)(Math.random()*100)+80;//70
		//leaf2Offset = -120;//-(float)(Math.random()*120)+80;//-120
		//leaf3Offset = (float)(Math.random() * 70) + 90;//90
		//leaf4Offset = -(float)(Math.random() * 130)+90;//-160
	}
	private Color leafColor = Color.green;
	private float delta = 0.0f;
	private float swayx = 0.0f;
	private float swayz = 0.0f;
	private float height = 1100;
	private float swayr = 0.0f;
	private Rand ran;
	//private final float leaf1Offset;
	public void draw(int darkness) {
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0,delta,0);
		ran = new Rand(34);
		Color leaf = Utility.adjustBrightness(leafColor, -darkness);
		float halfH = height/2.2f;
		swayx+=0.02f;
		swayz+=0.03f;
		float asx = (float)(MathCalculator.cos(swayx)*swayr);
		float asz = (float)(MathCalculator.sin(swayz)*swayr);
		tesselator.color(85-darkness,62-darkness,47-darkness);
		//tesselator.color(new Color(85,62,47));
		tesselator.point(-20,0,-20);
		tesselator.point(-10,halfH,-20);
		tesselator.point(20,0,-20);
		
		tesselator.point(20,0,-20);
		tesselator.point(-10,halfH,20);
		tesselator.point(-20,0,20);
		
		tesselator.point(20,0,-20);
		tesselator.point(-10,halfH,20);
		tesselator.point(-10,halfH,-20);
		
		tesselator.point(-20,0,-20);
		tesselator.point(-10,halfH,-20);
		tesselator.point(-50,0,20);
		
		
		tesselator.point(-50,0,20);
		tesselator.point(-10,halfH,20);
		tesselator.point(-20,0,20);
		
		tesselator.point(-50,0,20);
		tesselator.point(-10,halfH,20);
		tesselator.point(-40,halfH,-10);
		
		tesselator.point(-40,halfH,-10);
		tesselator.point(-10,halfH,-20);
		tesselator.point(-50,0,20);
		
		tesselator.point(-10,halfH,-20);
		tesselator.point(-40,halfH,-10);
		tesselator.point(-30+asx,height,0+asz);
		
		tesselator.point(-10,halfH,20);
		tesselator.point(-40,halfH,-10);
		tesselator.point(-30+asx,height,0+asz);
		
		tesselator.point(-10,halfH,-20);
		tesselator.point(-10,halfH,20);
		tesselator.point(-30+asx,height,0+asz);
		
		//tesselator.color(0,100-darkness,20-darkness);
		tesselator.color(ran.bright(leaf, 45));
		tesselator.point(-31+asx,height,0+asz);
		tesselator.point(-200+(asx*0.55f),height*0.45f,0+(asz*0.55f));//-160
		tesselator.point(-35,halfH,0);
		
		tesselator.color(ran.bright(leaf, 45));
		tesselator.point(-30+asx,height,0+asz);
		tesselator.point(140+(asx*0.6166666f),height*0.516666666666f,0+(asz*0.6166666f));//90
		tesselator.point(-10,halfH,0);
		
		tesselator.color(ran.bright(leaf, 45));
		tesselator.point(-31+asx,height,0+asz);
		tesselator.point(-33+(asx*0.75f),height*0.65f,-200+(asz*0.75f));//90//-120
		tesselator.point(-35,halfH,-15);
		
		tesselator.color(ran.bright(leaf, 45));
		tesselator.point(-30+asx,height,0+asz);
		tesselator.point(-15+(asx*0.6666666f),height*0.5666666666666f,200+(asz*0.666666f));//70//100
		tesselator.point(-10,halfH,15);
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
