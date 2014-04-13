import java.awt.Color;


public class Bed extends Drawable {
	private PointTesselator tesselator;
	public Bed(Scene<Drawable> scene) {
		super(scene, new Hitbox(new P3D(-100,-30,-200), new P3D(100,80,200)));
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
	}
	
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		Color sheets = new Color(130,30,30);
		Color pillow = new Color(237,220,213);
		Color frame = new Color(100,68,30);
		Rand var = new Rand(24);
		final float top = 80;
		final float mid = 30;
		final float fet = -30;
		final float wid = 100;
		final float fra = 0;
		final float leg = 40;
		//top
		tesselator.color(var.variate(sheets,15));
		tesselator.point(-wid,top,-100);
		tesselator.point(wid,top,-100);
		tesselator.point(wid,top,200);
		tesselator.color(var.bright(sheets,10,30));
		tesselator.point(wid,top,200);
		tesselator.point(-wid,top,200);
		tesselator.point(-wid,top,-100);
		//sides
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(-wid,top,-100);
		tesselator.point(-wid,top,200);
		tesselator.point(-wid,mid,200);
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(-wid,top,-100);
		tesselator.point(-wid,mid,-100);
		tesselator.point(-wid,mid,200);
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(wid,top,-100);
		tesselator.point(wid,top,200);
		tesselator.point(wid,mid,200);
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(wid,top,-100);
		tesselator.point(wid,mid,-100);
		tesselator.point(wid,mid,200);
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(wid,top,200);
		tesselator.point(-wid,top,200);
		tesselator.point(-wid,mid,200);
		tesselator.color(var.bright(sheets,-40,-15));
		tesselator.point(wid,top,200);
		tesselator.point(wid,mid,200);
		tesselator.point(-wid,mid,200);
		
		//top pillow
		tesselator.color(pillow);
		tesselator.point(-wid,top,-100);
		tesselator.point(wid,top,-100);
		tesselator.point(wid,top,-200);
		tesselator.color(var.bright(pillow,10,20));
		tesselator.point(-wid,top,-100);
		tesselator.point(-wid,top,-200);
		tesselator.point(wid,top,-200);
		//sides pillow
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(-wid,top,-100);
		tesselator.point(-wid,top,-200);
		tesselator.point(-wid,mid,-200);
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(-wid,top,-100);
		tesselator.point(-wid,mid,-100);
		tesselator.point(-wid,mid,-200);
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(wid,top,-100);
		tesselator.point(wid,top,-200);
		tesselator.point(wid,mid,-200);
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(wid,top,-100);
		tesselator.point(wid,mid,-100);
		tesselator.point(wid,mid,-200);
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(wid,top,-200);
		tesselator.point(-wid,top,-200);
		tesselator.point(-wid,mid,-200);
		tesselator.color(var.bright(pillow,-40,-15));
		tesselator.point(wid,top,-200);
		tesselator.point(wid,mid,-200);
		tesselator.point(-wid,mid,-200);
		
		//frame
		tesselator.color(frame);
		tesselator.point(-wid,mid,-200);
		tesselator.point(-wid,mid,200);
		tesselator.point(-wid,fra,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,mid,-200);
		tesselator.point(-wid,fra,-200);
		tesselator.point(-wid,fra,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,mid,-200);
		tesselator.point(wid,mid,200);
		tesselator.point(wid,fra,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,mid,-200);
		tesselator.point(wid,fra,-200);
		tesselator.point(wid,fra,200);
		
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,mid,200);
		tesselator.point(wid,mid,200);
		tesselator.point(wid,fra,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,mid,200);
		tesselator.point(-wid,fra,200);
		tesselator.point(wid,fra,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,mid,-200);
		tesselator.point(wid,mid,-200);
		tesselator.point(wid,fra,-200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,mid,-200);
		tesselator.point(-wid,fra,-200);
		tesselator.point(wid,fra,-200);
		
		//legs
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid-leg,fra,-200);
		tesselator.point(wid,fra,-200);
		tesselator.point(wid,fet,-200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid-leg,fra,-200);
		tesselator.point(wid-leg,fet,-200);
		tesselator.point(wid,fet,-200);
		tesselator.color(var.bright(frame,20));
		tesselator.point((-wid)+leg,fra,-200);
		tesselator.point(-wid,fra,-200);
		tesselator.point(-wid,fet,-200);
		tesselator.color(var.bright(frame,20));
		tesselator.point((-wid)+leg,fra,-200);
		tesselator.point((-wid)+leg,fet,-200);
		tesselator.point(-wid,fet,-200);
		
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid-leg,fra,200);
		tesselator.point(wid,fra,200);
		tesselator.point(wid,fet,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid-leg,fra,200);
		tesselator.point(wid-leg,fet,200);
		tesselator.point(wid,fet,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point((-wid)+leg,fra,200);
		tesselator.point(-wid,fra,200);
		tesselator.point(-wid,fet,200);
		tesselator.color(var.bright(frame,20));
		tesselator.point((-wid)+leg,fra,200);
		tesselator.point((-wid)+leg,fet,200);
		tesselator.point(-wid,fet,200);
		
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,fra,200);
		tesselator.point(wid,fra,200-leg);
		tesselator.point(wid,fet,200-leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,fra,200);
		tesselator.point(wid,fet,200);
		tesselator.point(wid,fet,200-leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,fra,-200);
		tesselator.point(wid,fra,-200+leg);
		tesselator.point(wid,fet,-200+leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(wid,fra,-200);
		tesselator.point(wid,fet,-200);
		tesselator.point(wid,fet,-200+leg);
		
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,fra,200);
		tesselator.point(-wid,fra,200-leg);
		tesselator.point(-wid,fet,200-leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,fra,200);
		tesselator.point(-wid,fet,200);
		tesselator.point(-wid,fet,200-leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,fra,-200);
		tesselator.point(-wid,fra,-200+leg);
		tesselator.point(-wid,fet,-200+leg);
		tesselator.color(var.bright(frame,20));
		tesselator.point(-wid,fra,-200);
		tesselator.point(-wid,fet,-200);
		tesselator.point(-wid,fet,-200+leg);
	}

	public void tick() {
		
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}