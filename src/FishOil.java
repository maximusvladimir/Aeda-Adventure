import java.awt.Color;
import java.util.ArrayList;


public class FishOil extends Drawable {
	private PointTesselator tesselator;
	public FishOil(Scene<Drawable> scene) {
		super(scene, buildHitbox());
		tesselator = new PointTesselator();
		tesselator.setSkipCullCheck(true);
		tesselator.setDrawType(DrawType.Triangle);
		//tesselator.setTransparency(225);
	}
	
	private static Hitbox buildHitbox() {
		final Hitbox box = new Hitbox(new P3D(-110,-250,-110),new P3D(110,250,110));
		box.setHitAction(new HitAction() {
			public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
				//box.getDrawable().getScene().remove(box.getDrawable());
				if (SoundManager.soundEnabled){
				Sound item = new Sound("item");
				item.play();
				}
				d0.getScene().getLevel().addMessage("You've found fish oil!\nYou can now use a lamp to go into dark places.", "FISHOILLAMPYAY");
				d0.getScene().getLevel().setActiveMessage("FISHOILLAMPYAY");
				GameState.instance.hasFishOil = true;
				GameState.save();
				d0.getScene().remove(box.getDrawable());
			}
		});
		return box;
	}
	
	private float stxz = 0;
	private float wave = 0;
	public void draw(int darkness) {
		tesselator.translate(pos.x, pos.y, pos.z, false);
		for (int i = 0; i < header.length; i++) {
			P3D o = new P3D(header[i]);
			if (i % 3 == 0)
				tesselator.color(adjust(o.x*-0.015f,o.y*0.015f + stxz));
			float sic = MathCalculator.sin(wave + o.y);
			if (sic < 0)
				sic = -sic;
			sic = sic * 0.15f + 1;
			o.x *= sic;
			o.z *= sic;
			tesselator.point(o);
		}
	}
	
	public static Color adjust(float n1, float n2) {
		float a = (float)Berlin.noise(n1, n2);
		if (a < 0)
			a = -a;
		if (a > 1)
			a = 1;
		return MathCalculator.lerp(baseColor1, baseColor2, a);
	}
	
	private static int latitudeBands = 6;
	private static int longitudeBands = 6;
	private static final Color baseColor1 = new Color(237,205,80);
	private static final Color baseColor2 = new Color(173,114,24);
	
	private static P3D[] header = genConicalSphereCache();
	
	private static P3D[] genConicalSphereCache() {
		ArrayList<P3D> cache = new ArrayList<P3D>();
		float rad = 110;
		for (int latNumber = 0; latNumber <= latitudeBands; latNumber++) {
			for (int longNumber = 0; longNumber <= longitudeBands; longNumber++) {
				cache.add(getPoint(latNumber, longNumber, rad));
				cache.add(getPoint(latNumber - 1, longNumber, rad));
				cache.add(getPoint(latNumber - 1, longNumber + 1, rad));
				cache.add(getPoint(latNumber, longNumber, rad));
				cache.add(getPoint(latNumber, longNumber + 1, rad));
				cache.add(getPoint(latNumber - 1, longNumber + 1, rad));
			}
		}
		P3D[] arr = new P3D[cache.size()];
		arr = cache.toArray(arr);
		return arr;
	}

	private static P3D getPoint(double lat, double log, float radius) {
		double theta = lat * Math.PI / latitudeBands;
		double sinTheta = MathCalculator.sin(theta);
		double cosTheta = MathCalculator.cos(theta);
		double phi = log * 2 * Math.PI / longitudeBands;
		double sinPhi = MathCalculator.sin(phi);
		double cosPhi = MathCalculator.cos(phi);
		float x = (float) (cosPhi * sinTheta);
		float y = (float) cosTheta;
		float z = (float) (sinPhi * sinTheta);
		float zp = (z * radius);
		if (y > 0)
			y = (y * y) * 2;
		return new P3D(x * radius, y * radius, zp);
	}

	public void tick() {
		stxz += 0.02f;
		wave += 0.01f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}