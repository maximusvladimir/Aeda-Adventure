import java.awt.Color;


public class Well extends Drawable {
	private PointTesselator tesselator;
	private static P3D[] trigCache;
	public Well(Scene<Drawable> scene) {
		super(scene,new Hitbox(new P3D(-145,0,-145),new P3D(145,300,75)));
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		if (trigCache == null) {
			final int sides = 8;
			trigCache = new P3D[sides];
			float pTheta = (float)(Math.PI * 2 / sides);
			for (int r = 0; r < sides; r++) {
				trigCache[r] = new P3D();
				trigCache[r].x = (float)MathCalculator.cos(pTheta * r);
				trigCache[r].z = (float)MathCalculator.sin(pTheta * r);
			}
		}
	}
	
	private Rand ran;
	float cosic = 0.0f;
	public void draw(int darkness) {
		tesselator.translate(pos.x,pos.y,pos.z,false);
		tesselator.rotate(0, 0.3f, 0);
		
		ran = new Rand(35);
		
		float height = 150f;
		final float outerR = 165;
		final float innerR = 125;
		final float co = 2.75f;
		final float nis = (float)(MathCalculator.cos(cosic) * 15);
		final float nin = (float)(MathCalculator.sin(cosic) * 15);
		
		drawCylinder(outerR,innerR,height, Utility.adjustBrightness(new Color(144,136,124), -darkness),true);
		//drawCylinder(innerR,0,height,new Color(80,85,120),false);
		
		Color wood = Utility.adjustBrightness(new Color(121,92,67),-darkness);
		drawPost(wood,height,outerR+30,co);
		drawPost(wood,height,-outerR,co);
		
		float heightCompounder = height * 2.1f;
		tesselator.color(wood.darker());
		tesselator.point(-outerR+-30,heightCompounder,0);
		tesselator.point(outerR+30,heightCompounder,0);
		tesselator.point(outerR+30,heightCompounder - 12, 0);	
		tesselator.point(outerR+30,heightCompounder - 12, 0);
		tesselator.point(-outerR+-30,heightCompounder - 12, 0);
		tesselator.point(-outerR+-30,heightCompounder,0);
		
		// the rope
		tesselator.color(Utility.adjustBrightness(new Color(138,116,102), -darkness));
		tesselator.point(-8,heightCompounder-12,-4);
		tesselator.point(8,heightCompounder-12,4);
		tesselator.point(nin,0,nis);
		
		Color roof =Utility.adjustBrightness(new Color(90,93,82), -darkness);
		P3D pinnacle = new P3D(0,height * 3.5f,0);
		final float hie = height * co;
		final float out = outerR * 1.2f;
		tesselator.color(ran.bright(roof,30));
		tesselator.point(-out,hie,-out);
		tesselator.point(pinnacle);
		tesselator.point(out,hie,-out);
		tesselator.color(ran.bright(roof,30));
		tesselator.point(-out,hie,out);
		tesselator.point(pinnacle);
		tesselator.point(out,hie,out);
		tesselator.color(ran.bright(roof,30));
		tesselator.point(-out,hie,-out);
		tesselator.point(pinnacle);
		tesselator.point(-out,hie,out);
		tesselator.color(ran.bright(roof,30));
		tesselator.point(out,hie,-out);
		tesselator.point(pinnacle);
		tesselator.point(out,hie,out);
		
		
		roof = roof.darker();
		tesselator.color(ran.bright(roof,20));
		tesselator.point(-out,hie,-out);
		tesselator.point(out,hie,-out);
		tesselator.point(-out,hie,out);
		tesselator.color(ran.bright(roof,20));
		tesselator.point(out,hie,out);
		tesselator.point(-out,hie,out);
		tesselator.point(out,hie,-out);
		//Color cs = new Color(100,105,100);
		//Color cs = new Color(0,255,0);
	}

	private void drawPost(Color wood, float height, float outerR,float co) {
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,-12);
		tesselator.point(outerR+-30,0,-12);
		tesselator.point(outerR+-30,height*co,-12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,-10);
		tesselator.point(outerR+-5,height*co,-12);
		tesselator.point(outerR+-30,height*co,-12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,12);
		tesselator.point(outerR+-30,0,12);
		tesselator.point(outerR+-30,height*co,12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,10);
		tesselator.point(outerR+-5,height*co,12);
		tesselator.point(outerR+-30,height*co,12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-30,0,-10);
		tesselator.point(outerR+-30,height*co,-12);
		tesselator.point(outerR+-30,height*co,12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-30,0,-12);
		tesselator.point(outerR+-30,0,12);
		tesselator.point(outerR+-30,height*co,12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,-12);
		tesselator.point(outerR+-5,height*co,-12);
		tesselator.point(outerR+-5,height*co,12);
		tesselator.color(ran.variate(wood, 25));
		tesselator.point(outerR+-5,0,-12);
		tesselator.point(outerR+-5,0,12);
		tesselator.point(outerR+-5,height*co,12);
	}
	
	private void drawCylinder(float rad, float inner, float height,Color cs,boolean leanInward) {
		for (int i = 0; i < trigCache.length-1; i++) {
			tesselator.color(ran.bright(cs, 30));
			tesselator.point(trigCache[i].x * rad, 0, trigCache[i].z * rad);
			tesselator.point(trigCache[i+1].x * rad, 0, trigCache[i+1].z * rad);
			if (leanInward)
				tesselator.point(trigCache[i+1].x * inner, height, trigCache[i+1].z * inner);
			else
				tesselator.point(trigCache[i+1].x * rad, height, trigCache[i+1].z * rad);
			
			if (leanInward)
				tesselator.point(trigCache[i+1].x * inner, height, trigCache[i+1].z * inner);
			else
				tesselator.point(trigCache[i+1].x * rad, height, trigCache[i+1].z * rad);
			if (leanInward)
				tesselator.point(trigCache[i].x * inner, height, trigCache[i].z * inner);
			else
				tesselator.point(trigCache[i].x * rad, height, trigCache[i].z * rad);
			tesselator.point(trigCache[i].x * rad, 0, trigCache[i].z * rad);
		}
		int li = trigCache.length-1;
		tesselator.color(ran.bright(cs, 30));
		tesselator.point(trigCache[0].x * rad, 0, trigCache[0].z * rad);
		tesselator.point(trigCache[li].x * rad, 0, trigCache[li].z * rad);
		if (leanInward)
			tesselator.point(trigCache[li].x * inner, height, trigCache[li].z * inner);
		else
			tesselator.point(trigCache[li].x * rad, height, trigCache[li].z * rad);
		
		if (leanInward)
			tesselator.point(trigCache[li].x * inner, height, trigCache[li].z * inner);
		else
			tesselator.point(trigCache[li].x * rad, height, trigCache[li].z * rad);
		if (leanInward)
			tesselator.point(trigCache[0].x * inner, height, trigCache[0].z * inner);
		else
			tesselator.point(trigCache[0].x * rad, height, trigCache[0].z * rad);
		tesselator.point(trigCache[0].x * rad, 0, trigCache[0].z * rad);
	}

	public void tick() {
		cosic += 0.02f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}

}
