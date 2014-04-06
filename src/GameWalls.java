
public class GameWalls extends Drawable {
	private PointTesselator tesselator;
	public GameWalls(Scene<Drawable> scene) {
		super(scene,new Hitbox());
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
	}

	public boolean isCullable() {
		return false;
	}
	
	public void draw(int d) {
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		Rand rand = new Rand(453);
		int dr = -d;
		final float height = 350;
		for (int i = 0; i < getScene().getWorldActual() / 2; i++) {
			float over = -350;
			int dns = rand.nextInt(-20, 10)+dr;
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
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
