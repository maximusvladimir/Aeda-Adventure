import java.awt.Color;


public class GameWalls extends Drawable {
	private PointTesselator tesselator;
	private float height = 350;
	private Color wc;
	public GameWalls(Scene<Drawable> scene) {
		this(scene,350,new Color(121, 121, 113));
	}
	
	public GameWalls(Scene<Drawable> scene, float he, Color wallColor) {
		super(scene,new Hitbox());
		wc = wallColor;
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		height = he;
	}

	public boolean isCullable() {
		return false;
	}
	
	public void draw(int d) {
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		Rand rand = new Rand(453);
		int dr = -d;
		for (int i = 0; i < getScene().getWorldActual() / 2; i++) {
			float over = -350;
			int dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(-getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf());
			tesselator.point(-getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf());
			tesselator.point(-getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf());
			dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(-getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf());
			tesselator.point(-getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf());
			tesselator.point(-getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i))
							- getScene().getWorldSizeHalf());
		}

		for (int i = 0; i < (getScene().getWorldActual()+3) / 2; i++) {
			float over = -150;
			float over2 = -450;
			int dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf()+over2);
			tesselator.point(getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf()+over2);
			tesselator.point(getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf()+over2);
			dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(getScene().getWorldSizeHalf() + over, height,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf()+over2);
			tesselator.point(getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf()+over2);
			tesselator.point(getScene().getWorldSizeHalf() + over, -600,
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i))
							- getScene().getWorldSizeHalf()+over2);
		}

		for (int i = 0; i < (getScene().getWorldActual()+2) / 2; i++) {
			float over = -700;
			int dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), -600,-getScene().getWorldSizeHalf()
							+ over);
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * i)
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), height,-getScene().getWorldSizeHalf()
							+ over);
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), height,
							-getScene().getWorldSizeHalf()
							+ over);
			dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(Utility.adjustBrightness(wc, dns));
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), height,-getScene().getWorldSizeHalf()
							+ over);
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i - 1))
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), -600,-getScene().getWorldSizeHalf()
							+ over);
			tesselator.point(
					(getScene().getWorldSize() / (getScene().getWorldActual() / 2.0f) * (i))
							- getScene().getWorldSizeHalf() + getScene().getWorldActual(), -600,-getScene().getWorldSizeHalf()
							+ over);
		}
	}

	public void tick() {

	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
