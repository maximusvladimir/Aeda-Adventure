import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class SPCache {
	public int latitude;
	public int longitude;
	public float radius;
	public float[] points;
	public P3D center;
}

public class SceneTesselator extends PointTesselator {
	private ArrayList<PointTesselator> sceneObjects;
	private ArrayList<T3D> triangles;
	private ArrayList<SPCache> spherePointCache;
	private ArrayList<Light> lights;
	private static final TriangleComparator2 cmp2 = new TriangleComparator2();
	private Color fogColor = Color.white;
	private float fogStart = 0.0f;
	private float fogEnd = 0.0f;
	private boolean fogUsed = false;
	private boolean reverseFogEquation = false;
	private boolean useWireframeAdditionally = false;

	public SceneTesselator() {
		sceneObjects = new ArrayList<PointTesselator>();
		triangles = new ArrayList<T3D>();
		spherePointCache = new ArrayList<SPCache>();
		lights = new ArrayList<Light>();
	}

	public void setUseWireframeWithShading(boolean value) {
		useWireframeAdditionally = true;
	}

	public boolean getUseWireframeWithShader() {
		return useWireframeAdditionally;
	}

	public void setReverseFogEquation(boolean value) {
		reverseFogEquation = value;
	}

	public boolean isReverseFogEquation() {
		return reverseFogEquation;
	}

	public void addTesselator(PointTesselator tesselator) {
		sceneObjects.add(tesselator);
	}
	
	public void removeTesselator(PointTesselator tesselator) {
		sceneObjects.remove(tesselator);
	}

	public PointTesselator getTesselator(int index) {
		return sceneObjects.get(index);
	}

	public int size() {
		return sceneObjects.size();
	}

	public void removeTesselator(int index) {
		sceneObjects.remove(index);
	}

	private Polygon p = new Polygon();

	public void draw(Graphics g) {
		// Compiler.disable();
		if (fogUsed) {
			g.setColor(fogColor);
			g.fillRect(0, 0, maxWidth, maxHeight);
		}
		for (int i = 0; i < size(); i++) {
			getTesselator(i).translate(translatePreX, translatePreY,
					translatePreZ, true);
			getTesselator(i).translate(translatePostX, translatePostY,
					translatePostZ, false);
			getTesselator(i).rotate(rotationX, rotationY, rotationZ);
			getTesselator(i).scale(getScale().x, getScale().y, getScale().z);
			getTesselator(i).partialDraw(g);
			triangles.addAll(getTesselator(i).getTriangles());
		}

		// rare bug:
		// Exception in thread "main" java.lang.IllegalArgumentException:
		// Comparison method violates its general contract!
		try {
			Collections.sort(triangles, cmp2);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		for (int i = 0; i < triangles.size(); i++) {
			final T3D t = triangles.get(i);
			final int[] tri = t.tri;
			Color c0 = t.c0i;
			Color c1 = t.c1i;
			Color c2 = t.c2i;
			if (t.font != null) {
				c0 = c1 = c2 = t.font.color;
			}
			boolean breaker = false;
			for (int n = 0; n < tri.length; n++) {
				if (tri[n] < -800 || tri[n] > maxWidth + 800
						|| tri[n] > maxHeight + 800)
					breaker = true;
			}
			if (breaker) {
				numSkippedTriangles++;
				continue;
			}
			float[] colors = null;
			float[] lcolors = null;
			float darkness = -1;
			if (t.root.getFaceLighting()) {
				lcolors = new float[4];
				colors = new float[9];
				for (int l = 0; l < lights.size(); l++) {
					Light sampler = lights.get(l);
					P3D sf = sampler.position.normalise();
					P3D norm = MathCalculator.normCalc(t.pr0, t.pr1, t.pr2);
					if (norm.x < 0)
						norm.x = -norm.x;
					if (norm.y < 0)
						norm.y = -norm.y;
					if (norm.z < 0)
						norm.z = -norm.z;
					for (int j = 0; j < 1; j++) {
						Color sma = t.c0i;
						if (j == 1)
							sma = t.c1i;
						if (j == 2)
							sma = t.c2i;
						float dot = P3D.dot(norm, sf);

						// Specular calculation:
						if (sampler.shinyness > 0)
							dot = ((float) Math.pow(dot, sampler.shinyness) + dot);

						// Artificial specular highlite:
						// if (dot > 0.8f)
						// dot = 1;

						float red = MathCalculator.lerp(sampler.r,
								sma.getRed(), sampler.intensity) * dot;
						float green = MathCalculator.lerp(sampler.g,
								sma.getGreen(), sampler.intensity)
								* dot;
						float blue = MathCalculator.lerp(sampler.b,
								sma.getBlue(), sampler.intensity)
								* dot;

						lcolors[0] += sampler.r * dot;
						lcolors[1] += sampler.g * dot;
						lcolors[2] += sampler.b * dot;
						lcolors[3] += sampler.intensity;

						darkness += dot;
						colors[0 + (j * 3)] += red;
						colors[1 + (j * 3)] += green;
						colors[2 + (j * 3)] += blue;
					}
					// darkness = darkness * 0.3333333333333333f;
				}
				for (int v = 0; v < 9; v++) {
					colors[v] = MathCalculator.colorLock(colors[v]);// *
																	// 0.33333333f);
				}
			}
			boolean ev = true;
			if (!t.root.isCullSkipped())
				ev = drawableTriangle(tri);
			if (ev) {
				if ((Utility.colorEqual(c0, c1, c2) && !t.root.getUseTexture())
						|| (lcolors != null && lcolors[3] < 0.06f && !t.root
								.getUseTexture())) {
					// If they are all the same color,
					// then render it with basic Java graphics
					// methods.
					if (lcolors != null && lcolors[3] < 0.06f) {
						lcolors[0] = MathCalculator.colorLock(lcolors[0]);
						lcolors[1] = MathCalculator.colorLock(lcolors[1]);
						lcolors[2] = MathCalculator.colorLock(lcolors[2]);
						c0 = new Color((int) lcolors[0], (int) lcolors[1],
								(int) lcolors[2]);
					}
					if (t.root.getFaceLighting() && fogUsed) {
						float fogEquation = 1 - (float) ((fogEnd - t.dist) / (fogEnd - fogStart));
						if (fogEquation > 1.4) {
							numSkippedTriangles++;
							continue;
						}
						if (fogEquation > 1)
							fogEquation = 1;
						if (fogEquation < 0)
							fogEquation = 0;
						if (isReverseFogEquation())
							fogEquation = 1 - fogEquation;
						colors[0] = MathCalculator.lerp(colors[0],
								fogColor.getRed(), fogEquation);
						colors[1] = MathCalculator.lerp(colors[1],
								fogColor.getGreen(), fogEquation);
						colors[2] = MathCalculator.lerp(colors[2],
								fogColor.getBlue(), fogEquation);
						g.setColor(new Color((int) colors[0], (int) colors[1],
								(int) colors[2]));
					} else if (fogUsed) {
						float fogEquation = 1 - (float) ((fogEnd - t.dist) / (fogEnd - fogStart));
						if (fogEquation > 1.4) {
							numSkippedTriangles++;
							continue;
						}
						if (fogEquation > 1)
							fogEquation = 1;
						if (fogEquation < 0)
							fogEquation = 0;
						if (isReverseFogEquation())
							fogEquation = 1 - fogEquation;
						int red = (int) MathCalculator.lerp(fogColor.getRed(),
								c0.getRed(), fogEquation);
						int green = (int) MathCalculator
								.lerp(fogColor.getGreen(), c0.getGreen(),
										fogEquation);
						int blue = (int) MathCalculator.lerp(
								fogColor.getBlue(), c0.getBlue(), fogEquation);
						g.setColor(new Color(red, green, blue));
					} else if (t.root.getFaceLighting())
						g.setColor(new Color((int) colors[0], (int) colors[1],
								(int) colors[2]));
					else
						g.setColor(c0);
					if (t.font == null) {
						// p.reset();
						p = new Polygon();
						p.addPoint(tri[0], tri[1]);
						p.addPoint(tri[2], tri[3]);
						p.addPoint(tri[4], tri[5]);
						// p.addPoint(tri[0], tri[1]);
						g.fillPolygon(p);
						if (useWireframeAdditionally) {
							g.setColor(g.getColor().darker());
							g.drawPolygon(p);
						}
					} else {
						// System.out.println("DRAWING"+t.zdepth);
						// g.setColor(t.font.color);
						float size = (1 - Math.abs(t.zdepth))
								* t.font.font.getSize() * 3;
						g.setFont(t.font.font.deriveFont(size));
						String[] trims = new String[] { t.font.str };
						if (t.font.str.indexOf("\n") > -1) {
							trims = t.font.str.split("\n");
						}
						for (int b = 0; b < trims.length; b++) {
							g.drawString(trims[b], tri[4], tri[5]
									+ (int) ((size * b) + 1.2f));
						}
					}
				} else {
					if (fogUsed) {
						float fogEquation = 1 - (float) ((fogEnd - t.dist) / (fogEnd - fogStart));
						if (fogEquation > 1.4) {
							numSkippedTriangles++;
							continue;
						}
						if (fogEquation > 1)
							fogEquation = 1;
						if (fogEquation < 0)
							fogEquation = 0;
						t.fogHintColor = new float[4];
						t.fogHintColor[0] = fogColor.getRed();
						t.fogHintColor[1] = fogColor.getGreen();
						t.fogHintColor[2] = fogColor.getBlue();
						if (!isReverseFogEquation())
							t.fogHintColor[3] = fogEquation;
						else
							t.fogHintColor[3] = 1 - fogEquation;
					}
					Texture texture2 = t.root.getTexture();
					final int bck = backgroundColor;
					t.darkness = darkness;
					if (!t.root.getUseTexture())
						texture2 = null;
					if (texture2 == null && t.root.getFaceLighting()) {
						/*
						 * t.c0i = new Color((int) colors[0], (int) colors[1],
						 * (int) colors[2]); t.c1i = new Color((int) colors[3],
						 * (int) colors[4], (int) colors[5]); t.c2i = new
						 * Color((int) colors[6], (int) colors[7], (int)
						 * colors[8]);
						 */
					} else if (t.root.getFaceLighting()) {
						t.lightingColor = lcolors;
					}
					if (barycentricRasterizorOn) {
						fillTriangle(t, texture2, bck);
					} else
						fillTriangle(t, texture2, bck);
					if (useWireframeAdditionally) {
						p.reset();
						p.addPoint(tri[0], tri[1]);
						p.addPoint(tri[2], tri[3]);
						p.addPoint(tri[4], tri[5]);
						p.addPoint(tri[0], tri[1]);
						g.setColor(Color.black);
						g.drawPolygon(p);
					}
				}
			} else
				numSkippedTriangles++;
		}
		for (int i = 0; i < size(); i++) {
			final PointTesselator sample = getTesselator(i);
			sample.translatePreX = sample.translatePreY = sample.translatePreZ = 0;
			sample.translatePostX = sample.translatePostY = sample.translatePostZ = 0;
			sample.rotationX = sample.rotationY = sample.rotationZ = 0;
			sample.rotation = new R3D(0, 0, 0);
			sample.matrix.zero();
			numSkippedTriangles += sample.numSkippedTriangles;
			sample.numSkippedTriangles = 0;
			sample.getTriangles().clear();
		}
		lastSkippedTriangles = (int) numSkippedTriangles;
		numSkippedTriangles = 0;
		triangles.clear();
		lights.clear();
		fogUsed = false;
		// Compiler.enable();
	}

	public void cube(PointTesselator tesselator, float centerX, float centerY,
			float centerZ, float width, float height, float depth,
			Color[] faceColors) {
		float halfWidth = width * 0.5f;
		float halfHeight = height * 0.5f;
		float halfDepth = depth * 0.5f;
		float sx = centerX - halfWidth;
		float sy = centerY - halfHeight;
		float sz = centerZ - halfDepth;
		float ex = centerX + halfWidth;
		float ey = centerY + halfHeight;
		float ez = centerZ + halfDepth;
		cube(tesselator, new P3D(sx, sy, sz), new P3D(ex, ey, ez), faceColors);
	}

	public void cube(PointTesselator tesselator, float centerX, float centerY,
			float centerZ, float width, float height, float depth) {
		cube(tesselator, centerX, centerY, centerZ, width, height, depth,// I
																			// don't
																			// know.
																			// I
																			// just
																			// picked
																			// random
																			// colors.
				new Color[] { Color.red, Color.cyan, Color.green, Color.blue,
						Color.magenta, Color.orange });
	}

	public void cube(PointTesselator tesselator, P3D frontTopLeftPoint,
			P3D backBottomRightPoint) {
		cube(tesselator, frontTopLeftPoint, backBottomRightPoint,
		// I don't know. I just picked random colors.
				new Color[] { Color.red, Color.cyan, Color.green, Color.blue,
						Color.magenta, Color.orange });
	}

	public void cube(PointTesselator tesselator, P3D frontTopLeftPoint,
			P3D backBottomRightPoint, Color[] faceColors) {

		// TODO: Use rendering order in Texturing05,
		// to texture the cube better.

		// MMMMMMMMMMMMMMMMMMM
		// MM................7M
		// M..M..............I..M
		// M....M....TOP.....I...M
		// M.....M...........I....M
		// M......MMMMMMMMMMMMMMMMMM
		// M......M..........I.....M
		// M.L....M...BACK...I.....M
		// M..E...M..........I.....M <- Right
		// M...F..M..........I.....M
		// M....T.M...FRONT..I.....M
		// M777777M7777777777I.....M
		// ..M....M..........I.....M
		// ...M...M..........I.....M
		// ....M..M..........7.....M
		// ......MMMMMMMMMMMMMMMMMMM
		// .............^
		// ..........BOTTOM

		boolean useSColor = false;
		if (faceColors == null || faceColors.length == 0)
			faceColors = new Color[] { Color.red, Color.cyan, Color.green,
					Color.blue, Color.magenta, Color.orange };
		if (faceColors.length == 1) {
			useSColor = true;
			tesselator.color(faceColors[0]);
		}

		// Front Quad.
		if (!useSColor)
			tesselator.color(faceColors[0]);
		tesselator.normal(0, 0, -1);
		tesselator.point(frontTopLeftPoint);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(frontTopLeftPoint);

		// Back Quad.
		if (!useSColor && faceColors.length >= 2)
			tesselator.color(faceColors[1]);
		tesselator.normal(0, 0, 1);
		tesselator.point(frontTopLeftPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);

		// Left Quad.
		if (!useSColor && faceColors.length >= 3)
			tesselator.color(faceColors[2]);
		tesselator.normal(-1, 0, 0);
		tesselator.point(frontTopLeftPoint);
		tesselator.point(frontTopLeftPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(frontTopLeftPoint);

		// Right Quad.
		if (!useSColor && faceColors.length >= 4)
			tesselator.color(faceColors[3]);
		tesselator.normal(1, 0, 0);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				frontTopLeftPoint.z);

		// Top Quad
		if (!useSColor && faceColors.length >= 5)
			tesselator.color(faceColors[4]);
		tesselator.normal(0, 0, 1);
		tesselator.point(frontTopLeftPoint);
		tesselator.point(frontTopLeftPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, frontTopLeftPoint.y,
				backBottomRightPoint.z);

		// Bottom Quad
		if (!useSColor && faceColors.length >= 6)
			tesselator.color(faceColors[5]);
		tesselator.normal(0, 0, -1);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				frontTopLeftPoint.z);
		tesselator.point(backBottomRightPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
		tesselator.point(frontTopLeftPoint.x, backBottomRightPoint.y,
				backBottomRightPoint.z);
	}

	public void sphere(PointTesselator tesselator, P3D center, float radius,
			int latitudes, int longitudes) {
		sphere(tesselator, center, radius, latitudes, longitudes, null);
	}

	public void sphere(PointTesselator tesselator, P3D center, float radius) {
		sphere(tesselator, center, radius, null);
	}

	public void sphere(PointTesselator tesselator, P3D center, float radius,
			Color[] colors) {
		sphere(tesselator, center, radius, 10, 10, colors);
	}

	public void sphere(PointTesselator tesselator, P3D center, float radius,
			int latitudes, int longitudes, Color[] colors) {

		// ................MMMMMMM.................
		// ..............MMM.....MMMM..............
		// ...........M...M.......M...M............
		// .........M.....M.......M.....M..........
		// ........M..777M777777777M7I...M.........
		// ......MM......M.........M......M........
		// .......MMMM...M.........M..MMMMM........
		// ......M.......M.........M.......M.......
		// ......M.......M.........M.......M.......
		// ......M.......M.........M.......M.......
		// ......M....777M777777777M777....M.......
		// .......M777...M.........M...777M........
		// .......MMMM...M.........M...MMMM........
		// .........M....M.........M....M..........
		// ..........M....M.......M....M...........
		// ............MM.M.......M.MM.............
		// ..............MM.......MM...............
		// ................MMMMMMM.................

		if (colors == null) {
			int max = latitudes * longitudes * 9;
			colors = new Color[max];
			// Super evil:
			Random randy = new Random(45);
			for (int c = 0; c < max; c++) {
				int randomSign = 1;
				if (randy.nextBoolean())
					randomSign = -1;
				colors[c] = new Color(randomSign * randy.nextInt());
			}
		}
		// Why should we recompute a sphere each time?
		// Why not cache them?
		// There's (24 * (latitudes + 1) * (longitudes + 1)) trig functions that
		// we have to calculate each time, if we don't.
		for (int i = 0; i < spherePointCache.size(); i++) {
			SPCache inst = spherePointCache.get(i);
			// Check to see if this is the sphere that matches out params.
			if (inst.radius == radius && inst.latitude == latitudes
					&& inst.longitude == longitudes) {
				float halfRadius = radius * 0.5f;
				P3D radCen = new P3D(center.x - halfRadius, center.y
						- halfRadius, center.z - halfRadius);
				// P3D normAdder = new P3D();
				// This is a really evil for loop.
				for (int d = 0; d < inst.points.length;) {
					if (d % 9 == 0 && d / 9 < colors.length - 1)
						tesselator.color(colors[d / 9]);
					if (colors.length == 1)
						tesselator.color(colors[0]);
					float contractedX = inst.points[d++];
					float contractedY = inst.points[d++];
					float contractedZ = inst.points[d++];
					float x = contractedX + radCen.x;
					float y = contractedY + radCen.y;
					float z = contractedZ + radCen.z;
					P3D norm = new P3D(contractedX, contractedY, contractedZ);
					tesselator.normal(norm.normalise());
					tesselator.point(x, y, z);
				}
				return;
			}
		}

		P3D[] pointStore = new P3D[(latitudes + 1) * (longitudes + 1) * 6];
		int indexer = 0;
		for (int lat = 0; lat < latitudes + 1; lat++) {
			for (int lon = 0; lon < longitudes + 1; lon++) {
				pointStore[indexer++] = getSphericalPoint(lat, lon, radius,
						latitudes, longitudes);
				pointStore[indexer++] = getSphericalPoint(lat - 1, lon, radius,
						latitudes, longitudes);
				pointStore[indexer++] = getSphericalPoint(lat - 1, lon + 1,
						radius, latitudes, longitudes);
				pointStore[indexer++] = getSphericalPoint(lat, lon, radius,
						latitudes, longitudes);
				pointStore[indexer++] = getSphericalPoint(lat, lon + 1, radius,
						latitudes, longitudes);
				pointStore[indexer++] = getSphericalPoint(lat - 1, lon + 1,
						radius, latitudes, longitudes);
			}
		}
		indexer = 0;
		float[] store = new float[pointStore.length * 3];
		for (int i = 0; i < pointStore.length; i++) {
			store[indexer++] = pointStore[i].x;
			store[indexer++] = pointStore[i].y;
			store[indexer++] = pointStore[i].z;
		}
		pointStore = null;
		SPCache cacher = new SPCache();
		cacher.center = center;
		cacher.radius = radius;
		cacher.latitude = latitudes;
		cacher.longitude = longitudes;
		cacher.points = store;
		// Limit how many we can cache.
		if (spherePointCache.size() > 64)
			spherePointCache.clear();

		spherePointCache.add(cacher);

		float halfRadius = radius * 0.5f;
		P3D radCen = new P3D(center.x - halfRadius, center.y - halfRadius,
				center.z - halfRadius);
		// This is a really evil for loop.
		for (int d = 0; d < cacher.points.length;) {
			if (d % 9 == 0 && d / 9 < colors.length - 1)
				tesselator.color(colors[d / 9]);
			if (colors.length == 1)
				tesselator.color(colors[0]);
			float x = cacher.points[d++] + radCen.x;
			float y = cacher.points[d++] + radCen.y;
			float z = cacher.points[d++] + radCen.z;
			tesselator.point(x, y, z);
		}
		return;
	}

	public void cone(PointTesselator tesselator, P3D apex, float height,
			float radius, int latitudes, Color[] colors) {
		// .................Apex.............-.....
		// ...................M...............-....
		// ..................M.M..............-....
		// .................M...M.............-....
		// .................M...M.............-....
		// ................M.....M...........-.....
		// ...............M.......M...........-.... H
		// ...............M.......M............-... E
		// ..............M.........M............-.. I
		// ..............M.........M............-.. G
		// .............M...........M............-. H
		// ............M.............M..........-.. T
		// ............M.............M..........-..
		// ...........M...............M........-...
		// ..........M.................M......-....
		// ..........M.................M.....-.....
		// .........M...................M.....-....
		// ........M.....................M....-....
		// ........M.....................M....-....
		// .......M...~~~~~~~~~~~~~~~~....M...-....
		// ......M~~~~.................~~~~M..-....
		// ........MM...................MM....-....
		// ..........MMM.............MMM......-....
		// .................MMMMM............-.....
		if (colors == null) {
			int max = latitudes + 1;
			colors = new Color[max];
			// Super evil:
			Random randy = new Random(45);
			for (int c = 0; c < max; c++) {
				int randomSign = 1;
				if (randy.nextBoolean())
					randomSign = -1;
				colors[c] = new Color(randomSign * randy.nextInt());
			}
		}
		Color defa = null;
		if (colors.length == 1)
			defa = colors[0];
		for (int s = 0; s < latitudes; s++) {
			P3D curConical = getConicalPoint(radius, s, latitudes, apex, height);
			P3D preConical = getConicalPoint(radius, s - 1, latitudes, apex,
					height);
			if (defa != null)
				tesselator.color(defa);
			else if (s < colors.length - 1)
				tesselator.color(colors[s]);
			tesselator.point(curConical);
			tesselator.point(apex);
			tesselator.point(preConical);
		}
	}

	public void cone(PointTesselator tesselator, P3D apex, float height,
			float radius, int latitudes) {
		cone(tesselator, apex, height, radius, latitudes, null);
	}

	public void pyram(PointTesselator tesselator, P3D apex, float height,
			float radius, Color[] colors) {
		// A pyramid (square), it simply a cone that has a latitude of 4.
		// ........................................
		// ..................MMM...................
		// ..................MMM...................
		// .................M.M.M..................
		// ................M..M..M.................
		// ...............M...M...M................
		// ..............M....M....M...............
		// .............M.....M.....M..............
		// ............M......M......M.............
		// ............M......M......M.............
		// ...........M.......M.......M............
		// .........M.........M.........M..........
		// .........M.........M.........M..........
		// ........M.........~M~.........M.........
		// .......M........~~.M.~~........M........
		// ......M...~~~......M......~~~...M.......
		// .....M~~...........M...........~~M......
		// ......MM...........M...........MM.......
		// ..........MM.......M.......MM...........
		// ............MM.....M.....MM.............
		// ................MM.M.MM.................
		cone(tesselator, apex, height, radius, 4, colors);
	}

	public void pyram(PointTesselator tesselator, P3D apex, float height,
			float radius) {
		pyram(tesselator, apex, height, radius, null);
	}

	public void cylinder(PointTesselator tesselator, P3D top, float height,
			int latitudes, float radius, Color[] colors) {
		// ........................................
		// .............MMM.~..MMM.................
		// ..........MMM....~.....MMM..............
		// ......M..........~...........M..........
		// ......MM.........~..........MM..........
		// ......M..M.......~........M..M..........
		// ......M...MMMMMMMMMMMMMMMM...M..........
		// ......M...M......~.......M...M..........
		// ......M...M......~.......M...M..........
		// ......M...M......~.......M...M..........
		// ......M...M......~.......M...M..........
		// ......M...M......~.......M...M..........
		// ......M...M......~.......M...M..........
		// ......M...M.....~~~~.....M...M..........
		// ......M...M..~~~....~~~..M...M..........
		// ......M~~~M..............M~~~M..........
		// .......M..M..............M..M...........
		// ........M.M..............M.M............
		// ..........M..............M..............
		// ..........MMMMMMMMMMMMMMMM..............
		if (colors == null) {
			int max = latitudes + 1;
			colors = new Color[max];
			// Super evil:
			Random randy = new Random(45);
			for (int c = 0; c < max; c++) {
				int randomSign = 1;
				if (randy.nextBoolean())
					randomSign = -1;
				colors[c] = new Color(randomSign * randy.nextInt());
			}
		}
		Color defa = null;
		if (colors.length == 1)
			defa = colors[0];
		for (int s = 0; s < latitudes; s++) {
			P3D curConical = getConicalPoint(radius, s, latitudes, top, height);
			P3D preConical = getConicalPoint(radius, s - 1, latitudes, top,
					height);
			if (defa != null)
				tesselator.color(defa);
			else if (s < colors.length - 1)
				tesselator.color(colors[s]);
			tesselator.point(curConical);
			tesselator.point(curConical.x, curConical.y - height, curConical.z);
			tesselator.point(preConical);

			if (s + 1 < colors.length - 1)
				tesselator.color(colors[s + 1]);
			tesselator.point(preConical);
			tesselator.point(preConical.x, preConical.y - height, preConical.z);
			tesselator.point(curConical.x, curConical.y - height, curConical.z);
		}
	}

	public void cylinder(PointTesselator tesselator, P3D top, float height,
			int latitudes, float radius) {
		cylinder(tesselator, top, height, latitudes, radius, null);
	}

	public void plane(PointTesselator tesselator, P3D frontLeft, P3D backRight,
			Color c0, Color c1) {
		// V Back right
		// .......~~~~~~~~~~~~...............
		// .......7..........I...............
		// ......7............7..............
		// .....7..............I.............
		// ....I................7............
		// ....I................7............
		// ...7..................I...........
		// ..I....................7..........
		// ..I....................7..........
		// .7......................I.........
		// .MMMMMMMMMMMMMMMMMMMMMMMMMM.......
		// ^ Front left

		tesselator.color(c0);
		tesselator.normal(0, 1, 0);
		tesselator.point(frontLeft);
		tesselator.point(backRight.x, frontLeft.y, frontLeft.z);
		tesselator.point(backRight);

		tesselator.color(c1);
		tesselator.normal(0, 1, 0);
		tesselator.point(backRight);
		tesselator.point(frontLeft.x, backRight.y, backRight.z);
		tesselator.point(frontLeft);
	}

	public void plane(PointTesselator tesselator, P3D frontLeft, P3D backRight,
			Color c0, Color c1, Color c2, Color c3) {
		// V Back right
		// .......~~~~~~~~~~~~...............
		// .......7..........I...............
		// ......7............7..............
		// .....7..............I.............
		// ....I................7............
		// ....I................7............
		// ...7..................I...........
		// ..I....................7..........
		// ..I....................7..........
		// .7......................I.........
		// .MMMMMMMMMMMMMMMMMMMMMMMMMM.......
		// ^ Front left

		tesselator.color(c0);
		tesselator.normal(0, 1, 0);
		tesselator.point(frontLeft);
		tesselator.color(c1);
		tesselator.point(backRight.x, frontLeft.y, frontLeft.z);
		tesselator.color(c2);
		tesselator.point(backRight);

		tesselator.normal(0, 1, 0);
		tesselator.point(backRight);
		tesselator.color(c3);
		tesselator.point(frontLeft.x, backRight.y, backRight.z);
		tesselator.color(c0);
		tesselator.point(frontLeft);
	}

	public void plane(PointTesselator tesselator, P3D frontLeftPoint,
			int segments, float scale, Color[] colors) {
		if (colors == null) {
			int max = (segments + 1) * (segments + 1);
			colors = new Color[max];
			// Super evil:
			Random randy = new Random(45);
			for (int c = 0; c < max; c++) {
				int randomSign = 1;
				if (randy.nextBoolean())
					randomSign = -1;
				colors[c] = new Color(randomSign * randy.nextInt());
			}
		}
		Color defa = null;
		if (colors.length == 1) {
			defa = colors[0];
			tesselator.color(defa);
		}
		for (int x = 0; x < segments; x++) {
			for (int z = 0; z < segments; z++) {
				if (defa == null && z * segments + x < colors.length - 1)
					tesselator.color(colors[z * segments + x]);
				tesselator.point((scale * x) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * z) + frontLeftPoint.z);
				tesselator.point((scale * (x - 1)) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * z) + frontLeftPoint.z);
				tesselator.point((scale * (x - 1)) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * (z - 1)) + frontLeftPoint.z);

				tesselator.point((scale * (x - 1)) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * (z - 1)) + frontLeftPoint.z);
				tesselator.point((scale * x) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * (z - 1)) + frontLeftPoint.z);
				tesselator.point((scale * x) + frontLeftPoint.x,
						frontLeftPoint.y, (scale * z) + frontLeftPoint.z);
			}
		}
	}

	public void plane(PointTesselator tesselator, P3D frontLeftPoint,
			int segments, float scale) {
		plane(tesselator, frontLeftPoint, segments, scale, null);
	}

	public void icosahedron(PointTesselator tesselator, P3D center, float radius) {
		P3D[] vertices = new P3D[12];
		double r = radius;
		double phia = 0.46364758845691964927456691449438;
		double theb = 0.6283185307179586476925286766559;
		double the72 = 1.2566370614359172953850573533118;
		vertices[0] = new P3D(0, 0, (float) r);
		vertices[11] = new P3D(0, 0, -(float) r);
		double the = 0.0;
		int i;
		double x, y, z;
		for (i = 1; i < 6; i++) {
			x = r * Math.cos(the) * Math.cos(phia);
			y = r * Math.sin(the) * Math.cos(phia);
			z = r * Math.sin(phia);
			vertices[i] = new P3D((float) x, (float) y, (float) z);
			the = the + the72;
		}
		the = theb;
		for (i = 6; i < 11; i++) {
			x = r * Math.cos(the) * Math.cos(-phia);
			y = r * Math.sin(the) * Math.cos(-phia);
			z = r * Math.sin(-phia);
			vertices[i] = new P3D((float) x, (float) y, (float) z);
			the = the + the72;
		}
		Random random = new Random(45);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[0]);
		tesselator.point(vertices[1]);
		tesselator.point(vertices[2]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[0]);
		tesselator.point(vertices[2]);
		tesselator.point(vertices[3]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[0]);
		tesselator.point(vertices[3]);
		tesselator.point(vertices[4]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[0]);
		tesselator.point(vertices[4]);
		tesselator.point(vertices[5]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[0]);
		tesselator.point(vertices[5]);
		tesselator.point(vertices[1]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[11]);
		tesselator.point(vertices[6]);
		tesselator.point(vertices[7]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[11]);
		tesselator.point(vertices[7]);
		tesselator.point(vertices[8]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[11]);
		tesselator.point(vertices[8]);
		tesselator.point(vertices[9]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[11]);
		tesselator.point(vertices[9]);
		tesselator.point(vertices[10]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[11]);
		tesselator.point(vertices[10]);
		tesselator.point(vertices[6]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[1]);
		tesselator.point(vertices[2]);
		tesselator.point(vertices[6]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[2]);
		tesselator.point(vertices[3]);
		tesselator.point(vertices[7]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[3]);
		tesselator.point(vertices[4]);
		tesselator.point(vertices[8]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[4]);
		tesselator.point(vertices[5]);
		tesselator.point(vertices[9]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[5]);
		tesselator.point(vertices[1]);
		tesselator.point(vertices[10]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[6]);
		tesselator.point(vertices[7]);
		tesselator.point(vertices[2]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[7]);
		tesselator.point(vertices[8]);
		tesselator.point(vertices[3]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[8]);
		tesselator.point(vertices[9]);
		tesselator.point(vertices[4]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[9]);
		tesselator.point(vertices[10]);
		tesselator.point(vertices[5]);
		tesselator.color(new Color(random.nextInt()));
		tesselator.point(vertices[10]);
		tesselator.point(vertices[6]);
		tesselator.point(vertices[1]);
	}

	public void light(Color color) {
		light(new P3D(0, 0, 1), color);
	}

	public void light(P3D position) {
		light(position, 0.5f);
	}

	public void light(P3D position, Color color) {
		light(position, color, 0.5f);
	}

	public void light(P3D position, float intensity) {
		light(position, Color.white, intensity);
	}

	public void light(P3D position, Color color, float intensity) {
		light(position, color, intensity, 0);
	}

	public void light(P3D position, Color color, float intensity,
			float shinyness) {
		Light st = new Light();
		float computeRed = color.getRed();
		float computeGreen = color.getGreen();
		float computeBlue = color.getBlue();
		/*
		 * boolean canCompute = true; if (intensity == 0.0f) canCompute = false;
		 * float expansion = 0.0f; if (intensity < 0) intensity = -intensity;
		 * while (canCompute && expansion < intensity) { expansion += 0.01f;
		 * computeRed += 0.01f; computeGreen += 0.01f; computeBlue += 0.01f; if
		 * (computeRed >= 255.0f || computeGreen >= 255.0f || computeBlue >=
		 * 255.0f) canCompute = false; }
		 */
		int red = (int) computeRed;
		int green = (int) computeGreen;
		int blue = (int) computeBlue;
		/*
		 * if (red > 255) red = 255; if (green > 255) green = 255; if (blue >
		 * 255) blue = 255; if (red < 0) red = 0; if (green < 0) green = 0; if
		 * (blue < 0) blue = 0;
		 */
		st.position = position;
		st.b = blue;
		st.r = red;
		st.g = green;
		st.intensity = 1 - intensity;
		st.shinyness = shinyness;
		lights.add(st);
	}

	public void fog(Color fogColor, float start, float end) {
		// if (start <= end)
		// throw new
		// IllegalArgumentException("The fog must end further away than the start.");
		fogUsed = true;
		this.fogColor = fogColor;
		this.fogStart = start;
		this.fogEnd = end;
	}

	private P3D getConicalPoint(float radius, int side, int latitudes,
			P3D apex, float height) {
		float circleX = (float) (radius * Math.cos(Math.PI * 2.0 * side
				/ latitudes))
				+ apex.x;
		float circleY = apex.y - height;
		float circleZ = (float) (radius * Math.sin(Math.PI * 2.0 * side
				/ latitudes))
				+ apex.z;
		return new P3D(circleX, circleY, circleZ);
	}

	private P3D getSphericalPoint(double lat, double log, float radius,
			int totallatitude, int totallongitude) {
		double theta = lat * Math.PI / totallatitude;
		double sinTheta = Math.sin(theta);
		double cosTheta = Math.cos(theta);
		double phi = log * 2 * Math.PI / totallongitude;
		double sinPhi = Math.sin(phi);
		double cosPhi = Math.cos(phi);
		float x = (float) (cosPhi * sinTheta);
		float y = (float) cosTheta;
		float z = (float) (sinPhi * sinTheta);
		return new P3D(x * radius, y * radius, ((z) * radius));
	}

	public void model(Model model, P3D position) {
		if (model == null)
			return;
		PointTesselator tess = model
				.doNotPlayAroundWithThisMethodPleaseThanksIReallyAppreciateIt();
		while (!model.isCompletelyQueried()) {
			tess.push(model.next());
		}
	}
}

class Light {
	public int r, g, b;
	public float intensity;
	public P3D position;
	public float shinyness = 0;
}

class TriangleComparator2 implements Comparator<T3D> {
	@Override
	public int compare(T3D t0, T3D t1) {
		// float x = Math.abs(t0.midpoint.x) - Math.abs(t1.midpoint.x);
		// float y = Math.abs(t0.midpoint.y) - Math.abs(t1.midpoint.y);
		// float z = Math.abs(t0.midpoint.z) - Math.abs(t1.midpoint.z);
		// return (int)(x + y + z);
		// return (int) ((t0.zdepth - t1.zdepth) * 4000);
		// return (int)((t0.))
		// return (int)((t0.dist - t1.dist)*1000);
		// System.out.println(t0.zdepth + "," + t1.zdepth);
		if (t0.zdepth < t1.zdepth)
			return -1;
		else if (t0.zdepth == t1.zdepth)
			return 0;
		else
			return 1;
	}
}