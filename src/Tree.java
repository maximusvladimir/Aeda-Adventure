
public class Tree extends Drawable {
	private PointTesselator tesselator;
	public Tree(Rand rand) {
		super(new P3D(-60,0,-60),new P3D(60,900,60));
		tesselator = new PointTesselator();
		delta = (float)(rand.nextDouble() * Math.PI * 2);
		swayx = (float)(rand.nextDouble() * 6);
		swayz = (float)(rand.nextDouble() * 6);
		height = (float)(rand.nextDouble() * 600) + 900;
		swayr = height/35.0f;

		//leaf1Offset = 100;//(float)(Math.random()*100)+80;//70
		//leaf2Offset = -120;//-(float)(Math.random()*120)+80;//-120
		//leaf3Offset = (float)(Math.random() * 70) + 90;//90
		//leaf4Offset = -(float)(Math.random() * 130)+90;//-160
	}
	private float delta = 0.0f;
	private float swayx = 0.0f;
	private float swayz = 0.0f;
	private float height = 1100;
	private float swayr = 0.0f;
	//private final float leaf1Offset;
	public void draw(int darkness) {
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0,delta,0);
		float halfH = height/2.2f;
		swayx+=0.02f;
		swayz+=0.03f;
		float asx = (float)(Math.cos(swayx)*swayr);
		float asz = (float)(Math.sin(swayz)*swayr);
		tesselator.color(85-darkness,62-darkness,47-darkness);
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
		
		tesselator.color(0,100-darkness,20-darkness);
		tesselator.point(-31+asx,height,0+asz);
		tesselator.point(-200+(asx*0.55f),height*0.55f,0+(asz*0.55f));//-160
		tesselator.point(-35,halfH,0);
		
		tesselator.color(30-darkness,70-darkness,0);
		tesselator.point(-30+asx,height,0+asz);
		tesselator.point(140+(asx*0.6166666f),height*0.616666666666f,0+(asz*0.6166666f));//90
		tesselator.point(-10,halfH,0);
		
		tesselator.color(30-darkness,140-darkness,0);
		tesselator.point(-31+asx,height,0+asz);
		tesselator.point(-33+(asx*0.75f),height*0.75f,-200+(asz*0.75f));//90//-120
		tesselator.point(-35,halfH,-15);
		
		tesselator.color(30-darkness,100-darkness,40-darkness);
		tesselator.point(-30+asx,height,0+asz);
		tesselator.point(-15+(asx*0.6666666f),height*0.6666666666666f,200+(asz*0.666666f));//70//100
		tesselator.point(-10,halfH,15);
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
