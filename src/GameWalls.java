import java.awt.Color;

public class GameWalls extends Drawable {
	private PointTesselator tesselator;

	public GameWalls() {
		super(null, null);
		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
	}

	public void draw(int d) {
		tesselator.setSkipCullCheck(true);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		Rand rand = new Rand(453);
		for (int i = 0; i < GamePlane.size / 2; i++) {
			float over = -350;
			int dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF);
			dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF);
			tesselator.point(-GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i))
							- GamePlane.WORLDSIZEHALF);
		}

		for (int i = 0; i < (GamePlane.size+3) / 2; i++) {
			float over = -150;
			float over2 = -450;
			int dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF+over2);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF+over2);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF+over2);
			dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, 100,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF+over2);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF+over2);
			tesselator.point(GamePlane.WORLDSIZEHALF + over, -600,
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i))
							- GamePlane.WORLDSIZEHALF+over2);
		}

		for (int i = 0; i < (GamePlane.size+2) / 2; i++) {
			float over = -700;
			int dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF + GamePlane.space, -600,-GamePlane.WORLDSIZEHALF
							+ over);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * i)
							- GamePlane.WORLDSIZEHALF + GamePlane.space, 100,-GamePlane.WORLDSIZEHALF
							+ over);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF + GamePlane.space, 100,
							-GamePlane.WORLDSIZEHALF
							+ over);
			dns = rand.nextInt(-20, 10);
			tesselator.color(121 + dns, 121 + dns, 113 + dns);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF + GamePlane.space, 100,-GamePlane.WORLDSIZEHALF
							+ over);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i - 1))
							- GamePlane.WORLDSIZEHALF + GamePlane.space, -600,-GamePlane.WORLDSIZEHALF
							+ over);
			tesselator.point(
					(GamePlane.WORLDSIZE / (GamePlane.size / 2.0f) * (i))
							- GamePlane.WORLDSIZEHALF + GamePlane.space, -600,-GamePlane.WORLDSIZEHALF
							+ over);
		}
	}

	public void tick() {

	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}
