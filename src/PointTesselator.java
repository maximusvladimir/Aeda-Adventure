// Similar to Point3D.java, except removes the repetitive 
// trig. functions to improve performance.
// It also uses a makeshift Queue to render.

// Also has 3 drawing modes:
// 		- Points: Draws the given points as points on the screen.
//		- Polygon: Draws the given points as a wireframe
//					(connected by lines).
//		- Triangle: Draws the given points as a triangle. For
//					example: num of triangles = points / 3.
//					The triangles are drawn in order, and the
//					first point is connected with the last point.
//					Using Triangle draw mode allows shading to
//					be computed, as well as lighting.
//					It also allows rendering per vertex, so 
//					one vertex could be one color, while another
//					can be another (this took a long time to
//					figure out). This method takes the longest to
//					render, but has the best appearance.

// An example usage:
// PointTesselator t = new PointTesselator();
// t.setDrawType(DrawType.Triangle);
// float delta = 0.0f;
// ...
// public void paint(Graphics g) {
//		delta += 0.01f;
//		t.rotate(delta,0,0);
//		t.color(Color.red);
// 		t.point(-20, -20, -300);
//		t.color(Color.green);
//		t.point(20,  -20, -300);
//		t.color(Color.blue);
//		t.point(0, 20, -300);
//		t.draw(g);
// }
// This would render something like this:
//   *-----------*
//    \red green/
//     \       /
//      \blue /
//       \   /
//        \ /
//         *

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Random;

public class PointTesselator {
	// The Queue.
	private Queue<Operation3D> operationStack;
	// Originally was going to be used to sort the
	// triangles in an appropriate order.
	protected static final TriangleComparator cmp = new TriangleComparator();
	// The triangle storage.
	private final ArrayList<T3D> triangles = new ArrayList<T3D>();

	// Stuff that was pulled from Point3D.java
	protected static int maxWidth;
	protected static int maxHeight;
	static float midWidth;
	static float midHeight;
	static float focalLength;
	static float zDistance;
	private static BufferedImage img;
	
	private int transparency = 255;

	private static float[] stagedPoints;
	private static int stagedWidth;

	// =====================================
	// Options:
	// =====================================
	// Performs triangle checks when integral triangles are turned off.
	private final static boolean doTriangleSafetyChecks = true;
	// Very accurate and visually appealing triangle algorithm.
	private final static boolean userIntegralTriangles = true;
	// Uses the barycentric raterizor to render triangles (more accurate,
	// but might be slightly slower). Doesn't apply when triangles are
	// one color, or textured.
	protected static final boolean barycentricRasterizorOn = true;
	// When turned on, it will not use matrices. Matrices
	// currently do not support translations or z-depth (so they
	// don't work).
	private final static boolean useOldMath = true;
	//public static boolean useMathCache = true;
	// =====================================

	private boolean skipCull = false;

	//private static IdentityHashMap<P3D,>
	
	// Rotation values.
	protected float rotationX = 0.0f;
	protected float rotationY = 0.0f;
	protected float rotationZ = 0.0f;

	// Needed for hidden triangle detection.
	protected int backgroundColor = 0x0;

	// Translation values.
	protected float translatePostX = 0.0f;
	protected float translatePostY = 0.0f;
	protected float translatePostZ = 0.0f;

	protected float translatePreX = 0.0f;
	protected float translatePreY = 0.0f;
	protected float translatePreZ = 0.0f;

	private static boolean individualPixelCheck = false;
	private static boolean antialiasingOn = false;

	// Used for connecting lines in Polygon draw mode.
	private int previousX = 0;
	private int previousY = 0;

	// The original triangle vertex.
	private int tri0X = 0;
	private int tri0Y = 0;
	private float tri0Z = 0;
	private P3D preRotate0;
	private P3D preRotate1;

	private boolean optTriangle = false;

	// The original triangle vertex color.
	private Color tri0C;

	private P3D p0YY;
	private P3D p1YY;
	private P3D p2YY;
	
	private boolean reverseBackOpt = false;

	// private double backZ = 0;

	protected boolean faceLighting = false;

	protected R3D rotation = new R3D(0, 0, 0);

	private P3D latestNormal = new P3D(0, 0, 0);

	// The second triangle vertex.
	private int tri1X = 0;
	private int tri1Y = 0;
	private float tri1Z = 0;
	private float tri1ZT = 0;
	private float tri0ZT = 0;
	// The second triangle vertex color.
	private Color tri1C;

	private Color lastKnownGoodColor = Color.white;

	private float textureScaleU = 1.0f;
	private float textureScaleV = 1.0f;

	protected Matrix matrix;
	protected long numSkippedTriangles = 0;

	private P3D scale = new P3D(1, 1, 1);

	// The current drawing mode (Polygon, Points, Triangle)
	private DrawType currentDrawType;

	// The array of pixels of the buffer (used for triangle
	// rendering).
	private static int[] imgData;

	private DrawType stackDrawType = DrawType.Points;

	private float tOffsetU;
	private float tOffsetV;

	private Texture texture;
	private boolean useTexture = false;

	protected int lastSkippedTriangles = 0;

	public PointTesselator() {
		operationStack = new Queue<Operation3D>();
		currentDrawType = DrawType.Points;
		matrix = new Matrix();
	}
	
	public int getTransparency() {
		return transparency;
	}
	
	public void setTransparency(int val) {
		if (val > 255)
			val = 255;
		if (val < 0)
			val = 0;
		transparency = val;
	}

	/**
	 * Loads a texture into the system.
	 * 
	 * @param texture
	 */
	public void loadTexture(Texture texture) {
		if (texture == null) {
			useTexture = false;
			return;
		}
		if (texture.isLoaded())
			return;
		this.texture = texture;
		System.out.println("Loading texture to memory...");
		final long queryBegin = System.currentTimeMillis();
		this.texture.loadInternalDoNotTouch();
		final long queryResult = System.currentTimeMillis() - queryBegin;
		final long kilobytesAllocated = this.texture.textureData.length * 4 / 1024;
		System.out.println("Texture load complete. Took " + queryResult
				+ " ms, and allocated " + kilobytesAllocated + " kB of RAM.");
		useTexture = true;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setUseTexture(boolean value) {
		useTexture = value;
	}

	public boolean getUseTexture() {
		return useTexture;
	}

	public void setTextureScaleU(float u) {
		textureScaleU = u;
	}

	public float getTextureScaleU() {
		return textureScaleU;
	}

	public void setTextureScaleV(float v) {
		textureScaleV = v;
	}

	public float getTextureScaleV() {
		return textureScaleV;
	}

	public void normal(P3D normal) {
		latestNormal = normal;
	}

	public void normal(float x, float y, float z) {
		normal(new P3D(x, y, z));
	}

	public void setFaceLighting(boolean value) {
		faceLighting = value;
	}

	public boolean getFaceLighting() {
		return faceLighting;
	}

	public void setAntialiasingOn(boolean value) {
		antialiasingOn = value;
	}

	public boolean isAntialiasingOn() {
		return antialiasingOn;
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = (color.getRed() << 16 | color.getGreen() << 8 | color
				.getBlue()) - 16777216;
	}

	/**
	 * May not work.
	 * 
	 * @return
	 */
	public Color getBackgroundColor() {
		return new Color(backgroundColor);
	}

	public DrawType getDrawType() {
		return currentDrawType;
	}

	public void setDrawType(DrawType type) {
		if (type == null)
			return;
		operationStack.enqueue(new DT(type));
		currentDrawType = type;
	}

	public float getTextureOffsetU() {
		return tOffsetU;
	}

	public float getTextureOffsetV() {
		return tOffsetV;
	}

	public boolean isReverseBackOpt() {
		return reverseBackOpt;
	}
	
	public void setReverseBackOpt(boolean value) {
		reverseBackOpt = value;
	}
	
	public int getNumOfLastSkippedTriangles() {
		return lastSkippedTriangles;
	}

	public void setOptPixelCheck(boolean value) {
		individualPixelCheck = value;
	}

	public boolean getOptPixelCheck() {
		return individualPixelCheck;
	}

	public void setSkipCullCheck(boolean value) {
		skipCull = value;
	}

	public boolean isCullSkipped() {
		return skipCull;
	}
	
	public void text(Font3D font) {
		operationStack.enqueue(font);
	}
	
	public void text(String str, P3D pos) {
		text(str,pos,Color.black,new Font("Arial",0,12));
	}
	
	public void text(String str, P3D pos3d, Color textColor, Font fontToUse) {
		Font3D font = new Font3D();
		font.str = str;
		font.loc = pos3d;
		font.color = textColor;
		font.font = fontToUse;
		text(font);
	}

	public void point(float x, float y, float z) {
		operationStack.enqueue(new P3D(x, y, z));
	}

	public void point(P3D point) {
		operationStack.enqueue(point);
	}
public static boolean removeAlpha = true;
	public void color(Color c) {
		// Remove any alpha - It makes it slow and messes with the depth.
		c = new Color(c.getRed(), c.getGreen(), c.getBlue());
		operationStack.enqueue(new C3D(c));
	}
	
	public void color(C3D c) {
		operationStack.enqueue(c);
	}

	public void color(int r, int g, int b) {
		operationStack.enqueue(new C3D(r, g, b));
	}

	public void textureOffset(float u, float v) {
		operationStack.enqueue(new E3D(u, v));
	}

	public void textureOffset(E3D textureOffset) {
		operationStack.enqueue(textureOffset);
	}

	public void push(Operation3D op) {
		operationStack.enqueue(op);
	}

	protected ArrayList<T3D> getTriangles() {
		return triangles;
	}
	
	public void scale(float x, float y, float z) {
		scale.x *= x;
		scale.y *= y;
		scale.z *= z;
	}
	
	public P3D getScale() {
		return scale;
	}

	public void quad(float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
		point(x0, y0, z0);
		point(x1, y1, z1);
		point(x3, y3, z3);

		point(x1, y1, z1);
		point(x2, y2, z2);
		point(x3, y3, z3);
	}

	// public void textureCoord(float u, float v) {
	// textureCoord(new E3D(u, v));
	// }

	// public void textureCoord(E3D textureCoord) {
	// operationStack.enqueue(textureCoord);
	// }

	public void rotate(float x, float y, float z) {
		// System.out.println("before:");
		// System.out.println(matrix);
		// matrix.rotateX(x);
		if (!useOldMath)
			matrix.rotateY(y);
		else {
			operationStack.enqueue(new R3D(x, y, z));
		}
		// matrix.rotateZ(z);
		// System.out.println("after:");
		// System.out.println(matrix);
		// operationStack.enqueue(new R3D(x, y, z));
	}

	public void translate(float x, float y, float z, boolean beforeRotation) {
		if (!useOldMath)
			matrix.translate(x, y, z);
		else {
			if (beforeRotation) {
				translatePreX += x;
				translatePreY += y;
				translatePreZ += z;
			} else {
				translatePostX += x;
				translatePostY += y;
				translatePostZ += z;
			}
		}
	}

	/**
	 * Set the size of the "render area" each time the window is resized.
	 * 
	 * @param i
	 * @param width
	 * @param height
	 */
	public void setSize(BufferedImage i, int width, int height) {
		img = i;
		maxWidth = width;
		maxHeight = height;
		midWidth = maxWidth * 0.5f;
		midHeight = maxHeight * 0.5f;
		focalLength = maxHeight;//-((float)maxWidth)/((float)maxHeight) * 2.0f * 60.0f;
		zDistance = focalLength;
	}

	protected void partialDraw(Graphics g) {
		if (texture != null) {
			texture.INTERNALTEXTURESCALESIZEDONOTTOUCHU = textureScaleU;
			texture.INTERNALTEXTURESCALESIZEDONOTTOUCHV = textureScaleV;
		}
		imgData = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		float zdepthsum = 0.0f;
		int zdepthsize = 0;
		// int csin = 0;
		// System.out.println("break");
		// final ArrayList<Float> warmer = new ArrayList<Float>();
		if (!useOldMath) {
			matrix.perspective(60, maxWidth, maxHeight, 0.1f, 1000.0f);
		}
		boolean markAsBadDenominator = false;
		while (!operationStack.isEmpty()) {
			Operation3D pulled = operationStack.dequeue();
			if (pulled.getOperationType() == 1 || pulled.getOperationType() == 7) {
				// csin++;
				// It's a vertex/point!
				P3D point = null;
				Font3D font = null;
				if (pulled.getOperationType() == 7){
					font = ((Font3D)pulled);
					point = font.loc;
				}
				else
					point = (P3D) pulled;
				// dealWithRotation(point.x, point.y, point.z, rotation.x,
				// rotation.y, rotation.z);
				// System.out.println(matrix);
				// System.out.println(matrix);
				int screenX = 0;
				int screenY = 0;
				float screenZ = 0;
				if (useOldMath) {
					dealWithRotation(point.x + translatePreX, point.y
							+ translatePreY, point.z + translatePreZ,
							rotation.x, rotation.y, rotation.z);
					float den = (zDistance - (rotationZ + translatePostZ));
					screenX = (int) (((rotationX + translatePostX) * focalLength) / den);
					screenY = (int) -(((rotationY + translatePostY) * focalLength) / den);
					screenX = (int) (screenX + midWidth);
					screenY = (int) (screenY + midHeight);
					screenZ = (translatePostZ+rotationZ)/den;
					// Occurs when the triangle is behind the "camera".
					//System.out.println(zDistance-(rotationZ+translatePostZ+translatePreZ));
					if (!markAsBadDenominator) {
						//if (!isReverseBackOpt())
							markAsBadDenominator = (zDistance-(rotationZ+translatePostZ+translatePreZ) < 0);
						//else
							//markAsBadDenominator = (screenY - midHeight > 0);
					}
					//System.out.println(screenY - midHeight);
					// markAsBadDenominator = den < -translatePostZ;
					// float rotRR = rotationZ - translatePreZ;
					// System.out.println(rotRR);
					// if (rotRR <= 0)
					// optTriangle = true;
					// System.out.println("")
				} else {
					matrix.calculateScreenValues(point.x, point.y, point.z);
					screenX = (int) (matrix.getScreenX() + midWidth + point.x);
					screenY = (int) ((-matrix.getScreenY()) + midHeight + -point.y);
					screenZ = (int) (matrix.getScreenZ());
				}
				// System.out.println(screenX + "," + screenY);
				if (stackDrawType == DrawType.Points) {
					// Size points based on distance from camera.
					// double dist = (radiX + Math.sqrt(xpow + ypow + zpow)) /
					// (zDistance - radiZ);
					// int vd = (int)point.z;
					// System.out.println(vd);
					int size = 4;// (int)(dist * 25);
					int halfsize = size / 2;
					g.fillRect(screenX - halfsize, screenY - halfsize, size,
							size);
				} else if (stackDrawType == DrawType.Polygon) {
					if (previousX == 0 && previousY == 0) {
						previousX = screenX;
						previousY = screenY;
						if (markAsBadDenominator) {
							markAsBadDenominator = false;
							continue;
						}
						g.fillRect(screenX - 2, screenY - 2, 4, 4);
					} else {
						if (markAsBadDenominator) {
							markAsBadDenominator = false;
							continue;
						}
						g.drawLine(previousX, previousY, screenX, screenY);
						previousX = screenX;
						previousY = screenY;
						g.fillRect(screenX - 2, screenY - 2, 4, 4);
					}
				} else if (stackDrawType == DrawType.TriangleLines) {
					if (tri0X == 0 && tri0Y == 0) {
						tri0X = screenX;
						tri0Y = screenY;
					} else if (tri1X == 0 && tri1Y == 0) {
						tri1X = screenX;
						tri1Y = screenY;
					} else {
						if (optTriangle) {
							optTriangle = false;
							continue;
						}
						if (tri0X < 0 && tri1X < 0 && screenX < 0) {
							// skip this triangle
							numSkippedTriangles++;
						} else if (tri0Y < 0 && tri1Y < 0 && screenY < 0) {
							// skip this triangle
							numSkippedTriangles++;
						} else if (tri0X > maxWidth && tri1X > maxWidth
								&& screenX > maxWidth) {
							// skip this triangle
							numSkippedTriangles++;
						} else if (tri0Y > maxHeight && tri1Y > maxHeight
								&& screenY > maxHeight) {
							// skip this triangle
							numSkippedTriangles++;
						} else if (tri0X > -400 && tri1X > -400
								&& screenX > -400) {
							if (markAsBadDenominator) {
								markAsBadDenominator = false;
								continue;
							}
							Polygon polygonFormula = new Polygon();
							polygonFormula.addPoint(tri0X, tri0Y);
							polygonFormula.addPoint(tri1X, tri1Y);
							polygonFormula.addPoint(screenX, screenY);
							polygonFormula.addPoint(tri0X, tri0Y);
							g.drawPolygon(polygonFormula);
						}
						tri0X = tri1X = tri0Y = tri1Y = screenX = screenY = 0;
					}
				} else if (stackDrawType == DrawType.Triangle) {
					if (optTriangle) {
						optTriangle = false;
						continue;
					}
					if (tri0X == 0 && tri0Y == 0 && font == null) {
						tri0X = screenX;
						tri0Y = screenY;
						tri0Z = screenZ;// rotationZ;
						tri0C = currentStackColor;
						tri0ZT = point.z;
						p0YY = new P3D(rotationX, rotationY, rotationZ);
						preRotate0 = new P3D(rotationX, rotationY, rotationZ);
					} else if (tri1X == 0 && tri1Y == 0 && font == null) {
						tri1X = screenX;
						tri1Y = screenY;
						tri1C = currentStackColor;
						tri1Z = screenZ;// rotationZ;
						tri1ZT = point.z;
						p1YY = new P3D(rotationX, rotationY, rotationZ);
						preRotate1 = new P3D(rotationX, rotationY, rotationZ);
					} else {
						// if (tri0Z < 0 && tri1Z < 0 && rotation.z < 0) {
						int[] tri = { tri0X, tri0Y, tri1X, tri1Y, screenX,
								screenY };
						if (markAsBadDenominator) {
							tri0X = tri1X = tri0Y = tri1Y = screenX = screenY = 0;
							tri1C = tri0C = null;
							tri1Z = tri0Z = 0;
							tri1ZT = tri0ZT = 0;
							p1YY = p0YY = null;
							preRotate1 = preRotate0 = null;
							tri = null;
							numSkippedTriangles++;
							markAsBadDenominator = false;
						} else {
							// Once we get the final point of the triangle,
							// lets add it to the array to be sorted.
							if (tri0X < 0 && tri1X < 0 && screenX < 0 && font == null) {
								// skip this triangle
								numSkippedTriangles++;
							} else if((tri0X > maxWidth && tri1X > maxWidth && screenX > maxWidth && tri0Y >maxHeight&&
									tri1Y > maxHeight && screenY > maxHeight)||
									(tri0X < 0 && tri1X < 0 && screenX < 0 && tri0Y <0&&
											tri1Y < 0 && screenY < 0) && font == null) {
								numSkippedTriangles++;
							} else if (tri0Y < 0 && tri1Y < 0 && screenY < 0 && font == null) {
								// skip this triangle
								numSkippedTriangles++;
							} else if (tri0X > maxWidth && tri1X > maxWidth
									&& screenX > maxWidth && font == null) {
								// skip this triangle
								numSkippedTriangles++;
							} else if (tri0Y > maxHeight && tri1Y > maxHeight
									&& screenY > maxHeight && font == null) {
								// skip this triangle
								numSkippedTriangles++;
							} else if ((tri0X > -400 && tri1X > -400
									&& screenX > -400) || font != null) {
								// System.out.print("X"+tri0X + "," + tri1X +
								// "," +
								// screenX+",");
								// System.out.println(tri0Y + "," + tri1Y + ","
								// +
								// screenY);
								if (font != null)
									lastKnownGoodColor = font.color;
								if (tri0C == null)
									tri0C = lastKnownGoodColor;
								if (tri1C == null)
									tri1C = lastKnownGoodColor;
								Color other = currentStackColor;
								if (font != null)
									other = font.color;
								p2YY = new P3D(rotationX, rotationY, rotationZ);
								T3D ttr = new T3D(this, tri, tri0Z, tri1Z,
										screenZ, tri0C, tri1C, other,
										tOffsetU, tOffsetV, p0YY, p1YY, p2YY,
										latestNormal);
								ttr.pr2 = new P3D(rotationX, rotationY,
										rotationZ);
								ttr.pr0 = preRotate0;
								ttr.pr1 = preRotate1;
								if (ttr.calculateDistances(-(translatePreZ + translatePostZ))) {
									if (font != null) {
										//System.out.println(tri[4]+","+tri[5]);
										ttr.font = font;
										ttr.zdepth = screenZ;
									}
								triangles.add(ttr);
								zdepthsum += tri0ZT + tri1ZT + point.z;
								zdepthsize += 3;
								}
								else
									numSkippedTriangles++;
								// warmer.add((tri0ZT + tri1ZT +
								// point.z)*0.333333333f);
							} else
								numSkippedTriangles++;
							tri0X = tri0Y = tri1X = tri1Y = 0;
						}
						// tOffsetU = 0;
						// tOffsetV = 0;
						// }
					}
				}
			} else if (pulled.getOperationType() == 2) {
				// It's a rotation.
				R3D rotations = (R3D) pulled;
				rotationX += rotations.x;
				rotationY += rotations.y;
				rotationZ += rotations.z;
				if (rotation == null)
					rotation = rotations;
				else {
					rotation.x += rotations.x;
					rotation.y += rotations.y;
					rotation.z += rotations.z;
				}
				/*
				 * float desiredX = rotation.x; float desiredY = rotation.y;
				 * float desiredZ = rotation.z; if (desiredX != 0.0f) {
				 * rotateX(desiredX); } if (desiredY != 0.0f) {
				 * rotateY(desiredY); } if (desiredZ != 0.0f) {
				 * rotateZ(desiredZ); }
				 */
			} else if (pulled.getOperationType() == 3) {
				// It's a color.
				final C3D color = (C3D) pulled;
				if (color != null && color.color != null)
					lastKnownGoodColor = color.color;
				currentStackColor = color.color;
				g.setColor(color.color);
			} else if (pulled.getOperationType() == 4) {
				// It's a texture coordinate
				final E3D coord = (E3D) pulled;
				tOffsetU = coord.u;
				tOffsetV = coord.v;
			} else if (pulled.getOperationType() == 5) {
				stackDrawType = ((DT) pulled).getDrawType();
			} else
				throw new IllegalArgumentException(
						"Unsupported tesselation operation.");
		}
		if (texture != null && zdepthsize > 0) {
			zdepthsum = zdepthsum / zdepthsize;
			if (zdepthsum == 0)
				zdepthsum = 0.1f;
			texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ = zdepthsum;
		}
		operationStack.clear();
		//operationStack = new Queue<Operation3D>();
	}
	
	private Color currentStackColor = Color.white;

	public void draw(Graphics g) {
		partialDraw(g);

		Collections.sort(triangles, cmp);

		for (int i = 0; i < triangles.size(); i++) {
			final T3D t = triangles.get(i);
			final int[] tri = t.tri;
			final Color c0 = t.c0i;
			final Color c1 = t.c1i;
			final Color c2 = t.c2i;
			boolean breaker = false;
			for (int n = 0; n < tri.length; n++) {
				if (tri[n] < -800 || tri[n] > maxWidth + 800
						|| tri[n] > maxHeight + 800)
					breaker = true;
			}
			if (breaker) {
				numSkippedTriangles++;
				// Get out of the current part of the
				// loop, but continue running it.
				continue;
			}
			// System.out.println("("+tri[0]+","+tri[1]+"), (" +
			// tri[2]+","+tri[3]+"), ("+tri[4]+","+tri[5]+")");
			boolean ev = true;
			if (!skipCull)
				ev = drawableTriangle(tri);
			if (ev) {
				if (Utility.colorEqual(c0, c1, c2) && !useTexture) {
					// If they are all the same color,
					// then render it with basic Java graphics
					// methods.
					g.setColor(c0);
					Polygon p = new Polygon();
					p.addPoint(tri[0], tri[1]);
					p.addPoint(tri[2], tri[3]);
					p.addPoint(tri[4], tri[5]);
					p.addPoint(tri[0], tri[1]);
					g.fillPolygon(p);
				} else {
					Texture texture2 = texture;
					// texture2.INTERNALTEXTURESCALESIZEDONOTTOUCHZ =
					// warmer.get(i);
					final int bck = backgroundColor;
					if (!useTexture)
						texture2 = null;
					if (barycentricRasterizorOn)
						fillTriangle(t, texture2, bck);
					else
						fillTriangle(t, texture2, bck);
				}
			} else
				numSkippedTriangles++;
			// /else
			// System.out.println("not drawing" + i);
		}
		// System.out.println("Total skipped triangles:" + numSkippedTriangles);
		lastSkippedTriangles = (int) numSkippedTriangles;
		numSkippedTriangles = 0;
		// VERY IMPORTANT
		translatePreX = translatePreY = translatePreZ = 0;
		translatePostX = translatePostY = translatePostZ = 0;
		rotationX = rotationY = rotationZ = 0;
		rotation = new R3D(0, 0, 0);
		matrix.zero();
		triangles.clear();
	}

	/**
	 * A little hack to speed up performance. I've seen it able to reduce the
	 * number of triangles being drawn by half.
	 * 
	 * @param trianglePoints
	 * @return
	 */
	protected boolean drawableTriangle(int[] trianglePoints) {
		int x0 = trianglePoints[0];
		int y0 = trianglePoints[1];
		int x1 = trianglePoints[2];
		int y1 = trianglePoints[3];
		int x2 = trianglePoints[4];
		int y2 = trianglePoints[5];
		int index0 = y0 * maxWidth + x0;
		int index1 = y1 * maxWidth + x1;
		int index2 = y2 * maxWidth + x2;

		if (index0 < 0 || index0 > imgData.length - 1 || index1 < 0
				|| index1 > imgData.length - 1 || index2 < 0
				|| index2 > imgData.length - 1)
			return true;
		if (imgData[index0] != backgroundColor
				&& imgData[index1] != backgroundColor
				&& imgData[index2] != backgroundColor)
			return false;
		else
			return true;
	}

	public void triangle(P3D p0, P3D p1, P3D p2) {
		point(p0);
		point(p1);
		point(p2);
	}

	static final class microhelperpoint {
		public int x;
		public int y;

		public microhelperpoint(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private static void lowerTri(microhelperpoint v1, microhelperpoint v2,
			microhelperpoint v3, Color c1, Color c2, Color c3, T3D t3, int bck,
			int[] minmaxarr, Texture texture) {
		float slope1 = (float) (v2.x - v1.x) / (float) (v2.y - v1.y);
		float slope2 = (float) (v3.x - v1.x) / (float) (v3.y - v1.y);

		float x1 = v1.x;
		float x2 = v1.x + 0.5f;
		float v2v1Diff = (float) (v2.y - v1.y);
		float colorSlopeBlue1 = (float) (c2.getBlue() - c1.getBlue())
				/ v2v1Diff;
		float colorSlopeRed1 = (float) (c2.getRed() - c1.getRed()) / v2v1Diff;
		float colorSlopeGreen1 = (float) (c2.getGreen() - c1.getGreen())
				/ v2v1Diff;
		float v3v1Diff = (float) (v3.y - v1.y);
		float colorSlopeBlue2 = (float) (c3.getBlue() - c1.getBlue())
				/ v3v1Diff;
		float colorSlopeRed2 = (float) (c3.getRed() - c1.getRed()) / v3v1Diff;
		float colorSlopeGreen2 = (float) (c3.getGreen() - c1.getGreen())
				/ v3v1Diff;
		float cBlue1 = c1.getBlue();
		float cRed1 = c1.getRed();
		float cGreen1 = c1.getGreen();
		float cBlue2 = c1.getBlue();
		float cRed2 = c1.getRed();
		float cGreen2 = c1.getGreen();
		if (slope2 < slope1) {
			float slopeTmp = slope1;
			slope1 = slope2;
			slope2 = slopeTmp;

			slopeTmp = colorSlopeRed1;
			colorSlopeRed1 = colorSlopeRed2;
			colorSlopeRed2 = slopeTmp;

			slopeTmp = colorSlopeGreen1;
			colorSlopeGreen1 = colorSlopeGreen2;
			colorSlopeGreen2 = slopeTmp;

			slopeTmp = colorSlopeBlue1;
			colorSlopeBlue1 = colorSlopeBlue2;
			colorSlopeBlue2 = slopeTmp;
		}
		int minx = minmaxarr[0];
		int maxx = minmaxarr[1];
		int miny = minmaxarr[2];
		int maxy = minmaxarr[3];

		float xux = 0.0f;
		float xuy = 0.0f;
		int index = 0;
		if (texture != null) {
			int ope = maxx - minx;
			if (ope == 0)
				ope = 1;
			xux = (1.0f / ope) * texture.INTERNALTEXTURESCALESIZEDONOTTOUCHU
					* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
			ope = maxy - miny;
			if (ope == 0)
				ope = 1;
			xuy = (1.0f / ope) * texture.INTERNALTEXTURESCALESIZEDONOTTOUCHV
					* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
		}
		int ty = 0;
		for (int y = v1.y; y <= v2.y; y++) {
			if (y > maxHeight || y < 0)
				continue;
			if (texture != null) {
				ty = (int) ((y - miny) * xuy + t3.tov);
				if (ty < 0)
					ty = -ty;
				if (ty >= texture.textureHeight)
					ty = (ty % texture.textureHeight);
				index = ty * texture.textureHeight;
			}
			int xs = (int) Math.ceil(x1);
			int xe = (int) x2;
			if (xe > maxWidth || xs > maxWidth)
				continue;
			if (xe < xs) {
				int swaper = xs;
				xs = xe;
				xe = swaper;
			}
			if (xs < 0)
				xs = 0;
			float den = x2 - x1;
			int lastR = (bck << 16) & 0xFF;
			int lastG = (bck << 8) & 0xFF;
			int lastB = (bck) & 0xFF;
			while (xs < xe) {
				if (texture == null) {
					float t = (xs - x1) / den;
					int rc = (int) ((1 - t) * cRed1 + t * cRed2);
					int gc = (int) ((1 - t) * cGreen1 + t * cGreen2);
					int bc = (int) ((1 - t) * cBlue1 + t * cBlue2);
					if (t3.fogHintColor != null) {
						rc = (int) MathCalculator.lerp(rc, t3.fogHintColor[0],
								t3.fogHintColor[3]);
						gc = (int) MathCalculator.lerp(gc, t3.fogHintColor[1],
								t3.fogHintColor[3]);
						bc = (int) MathCalculator.lerp(bc, t3.fogHintColor[2],
								t3.fogHintColor[3]);
					}
					if (antialiasingOn) {
						rc = (rc + lastR) / 2;
						gc = (gc + lastG) / 2;
						bc = (bc + lastB) / 2;
					}
					setPixel(xs, y, rc, gc, bc, bck);
					if (antialiasingOn) {
						lastR = rc;
						lastG = gc;
						lastB = bc;
					}

				} else {
					int tx = (int) ((xs - minx) * xux + t3.tou);
					if (tx < 0)
						tx = -tx;
					if (tx >= texture.textureWidth)
						tx = (tx % texture.textureWidth);
					final int sindex = (int) (index + tx);
					if (sindex < 0 || sindex > texture.textureData.length - 1)
						return;
					else {
						if (t3.darkness == -1) {
							if (t3.fogHintColor != null) {
								int tRGB = texture.textureData[sindex];
								int tr = (tRGB >> 16) & 0xFF;
								int tg = (tRGB >> 8) & 0xFF;
								int tb = tRGB & 0xFF;
								tr = (int) MathCalculator.lerp(tr,
										t3.fogHintColor[0], t3.fogHintColor[3]);
								tg = (int) MathCalculator.lerp(tg,
										t3.fogHintColor[1], t3.fogHintColor[3]);
								tb = (int) MathCalculator.lerp(tb,
										t3.fogHintColor[2], t3.fogHintColor[3]);
								setPixel(xs, y, tr, tg, tb, bck);
							} else
								setDirectPixel(xs, y,
										texture.textureData[sindex]);
						} else {
							float da = t3.darkness + 1;
							final int tRGB = texture.textureData[sindex];
							int tr = (int) MathCalculator.lerp(
									((tRGB >> 16) & 0xFF) * da,
									t3.lightingColor[0],
									1 - t3.lightingColor[3]);
							int tg = (int) MathCalculator.lerp(
									((tRGB >> 8) & 0xFF) * da,
									t3.lightingColor[1],
									1 - t3.lightingColor[3]);
							int tb = (int) MathCalculator.lerp((tRGB & 0xFF)
									* da, t3.lightingColor[2],
									1 - t3.lightingColor[3]);
							if (t3.fogHintColor != null) {
								tr = (int) MathCalculator.lerp(tr,
										t3.fogHintColor[0], t3.fogHintColor[3]);
								tg = (int) MathCalculator.lerp(tg,
										t3.fogHintColor[1], t3.fogHintColor[3]);
								tb = (int) MathCalculator.lerp(tb,
										t3.fogHintColor[2], t3.fogHintColor[3]);
							}
							tr = MathCalculator.colorLock(tr);
							tg = MathCalculator.colorLock(tg);
							tb = MathCalculator.colorLock(tb);
							setPixel(xs, y, tr, tg, tb, bck);
						}
					}
				}
				xs++;
			}
			x1 += slope1;
			x2 += slope2;
			cRed1 += colorSlopeRed1;
			cGreen1 += colorSlopeGreen1;
			cBlue1 += colorSlopeBlue1;
			cRed2 += colorSlopeRed2;
			cGreen2 += colorSlopeGreen2;
			cBlue2 += colorSlopeBlue2;
		}
	}

	private static void upperTri(microhelperpoint v1, microhelperpoint v2,
			microhelperpoint v3, Color c1, Color c2, Color c3, T3D t3, int bck,
			int[] minmaxarr, Texture texture) {
		float slope1 = (float) (v3.x - v1.x) / (float) (v3.y - v1.y);
		float slope2 = (float) (v3.x - v2.x) / (float) (v3.y - v2.y);
		float x1 = v3.x;
		float x2 = v3.x + 0.5f;
		float v3v1Diff = (float) (v3.y - v1.y);
		float colorSlopeBlue1 = (float) (c3.getBlue() - c1.getBlue())
				/ v3v1Diff;
		float colorSlopeRed1 = (float) (c3.getRed() - c1.getRed()) / v3v1Diff;
		float colorSlopeGreen1 = (float) (c3.getGreen() - c1.getGreen())
				/ v3v1Diff;
		float v3v2Diff = (float) (v3.y - v2.y);
		float colorSlopeBlue2 = (float) (c3.getBlue() - c2.getBlue())
				/ v3v2Diff;
		float colorSlopeRed2 = (float) (c3.getRed() - c2.getRed()) / v3v2Diff;
		float colorSlopeGreen2 = (float) (c3.getGreen() - c2.getGreen())
				/ v3v2Diff;
		float cBlue1 = c3.getBlue();
		float cRed1 = c3.getRed();
		float cGreen1 = c3.getGreen();
		float cBlue2 = c3.getBlue();
		float cRed2 = c3.getRed();
		float cGreen2 = c3.getGreen();
		if (slope1 < slope2) {
			float slopeTmp = slope1;
			slope1 = slope2;
			slope2 = slopeTmp;

			slopeTmp = colorSlopeRed1;
			colorSlopeRed1 = colorSlopeRed2;
			colorSlopeRed2 = slopeTmp;

			slopeTmp = colorSlopeGreen1;
			colorSlopeGreen1 = colorSlopeGreen2;
			colorSlopeGreen2 = slopeTmp;

			slopeTmp = colorSlopeBlue1;
			colorSlopeBlue1 = colorSlopeBlue2;
			colorSlopeBlue2 = slopeTmp;
		}
		int minx = minmaxarr[0];
		int maxx = minmaxarr[1];
		int miny = minmaxarr[2];
		int maxy = minmaxarr[3];
		float xux = 0.0f;
		float xuy = 0.0f;
		int index = 0;
		if (texture != null) {
			int ope = maxx - minx;
			if (ope == 0)
				ope = 1;
			xux = (1.0f / ope) * texture.INTERNALTEXTURESCALESIZEDONOTTOUCHU
					* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
			ope = maxy - miny;
			if (ope == 0)
				ope = 1;
			xuy = (1.0f / ope) * texture.INTERNALTEXTURESCALESIZEDONOTTOUCHV
					* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
		}
		int ty = 0;
		for (int y = v3.y; y > v1.y; y--) {
			x1 -= slope1;
			x2 -= slope2;
			cRed1 -= colorSlopeRed1;
			cGreen1 -= colorSlopeGreen1;
			cBlue1 -= colorSlopeBlue1;
			cRed2 -= colorSlopeRed2;
			cGreen2 -= colorSlopeGreen2;
			cBlue2 -= colorSlopeBlue2;
			if (y > maxHeight || y < 0)
				continue;
			if (texture != null) {
				ty = (int) ((y - miny) * xuy + t3.tov);
				if (ty < 0)
					ty = -ty;
				if (ty >= texture.textureHeight)
					ty = (ty % texture.textureHeight);
				index = ty * texture.textureHeight;
			}
			int xs = (int) Math.ceil(x1);
			int xe = (int) x2;
			if (xs > maxWidth || xe > maxWidth)
				continue;
			if (xe < xs) {
				int swaper = xs;
				xs = xe;
				xe = swaper;
			}
			if (xs < 0)
				xs = 0;
			float den = x2 - x1;
			int lastR = (bck << 16) & 0xFF;
			int lastG = (bck << 8) & 0xFF;
			int lastB = (bck) & 0xFF;
			while (xs < xe) {
				if (texture == null) {
					float t = (xs - x1) / den;
					int rc = (int) ((1 - t) * cRed1 + t * cRed2);
					int gc = (int) ((1 - t) * cGreen1 + t * cGreen2);
					int bc = (int) ((1 - t) * cBlue1 + t * cBlue2);
					if (t3.fogHintColor != null) {
						rc = (int) MathCalculator.lerp(rc, t3.fogHintColor[0],
								t3.fogHintColor[3]);
						gc = (int) MathCalculator.lerp(gc, t3.fogHintColor[1],
								t3.fogHintColor[3]);
						bc = (int) MathCalculator.lerp(bc, t3.fogHintColor[2],
								t3.fogHintColor[3]);
					}
					if (antialiasingOn) {
						rc = (rc + lastR) / 2;
						gc = (gc + lastG) / 2;
						bc = (bc + lastB) / 2;
					}
					setPixel(xs, y, rc, gc, bc, bck);
					if (antialiasingOn) {
						lastR = rc;
						lastG = gc;
						lastB = bc;
					}
				} else {
					int tx = (int) ((xs - minx) * xux + t3.tou);
					if (tx < 0)
						tx = -tx;
					if (tx >= texture.textureWidth)
						tx = (tx % texture.textureWidth);
					final int sindex = (int) (index + tx);
					if (sindex < 0 || sindex > texture.textureData.length - 1)
						return;
					else {
						if (t3.darkness == -1)
							if (t3.fogHintColor != null) {
								int tRGB = texture.textureData[sindex];
								int tr = (tRGB >> 16) & 0xFF;
								int tg = (tRGB >> 8) & 0xFF;
								int tb = tRGB & 0xFF;
								tr = (int) MathCalculator.lerp(tr,
										t3.fogHintColor[0], t3.fogHintColor[3]);
								tg = (int) MathCalculator.lerp(tg,
										t3.fogHintColor[1], t3.fogHintColor[3]);
								tb = (int) MathCalculator.lerp(tb,
										t3.fogHintColor[2], t3.fogHintColor[3]);
								setPixel(xs, y, tr, tg, tb, bck);
							} else
								setDirectPixel(xs, y,
										texture.textureData[sindex]);
						else {
							float da = t3.darkness + 1;
							final int tRGB = texture.textureData[sindex];
							/*
							 * int tr = (int) MathCalculator.lerp(((tRGB >> 16)
							 * & 0xFF) * da),(t3.lightingColor[0]))*0.5f); int
							 * tg = (int) (((((tRGB >> 8) & 0xFF) * da) +
							 * (t3.lightingColor[1]))*0.5f); int tb = (int)
							 * ((((tRGB & 0xFF) * da) +
							 * (t3.lightingColor[2]))*0.5f);
							 */
							int tr = (int) MathCalculator.lerp(
									((tRGB >> 16) & 0xFF) * da,
									t3.lightingColor[0],
									1 - t3.lightingColor[3]);
							int tg = (int) MathCalculator.lerp(
									((tRGB >> 8) & 0xFF) * da,
									t3.lightingColor[1],
									1 - t3.lightingColor[3]);
							int tb = (int) MathCalculator.lerp((tRGB & 0xFF)
									* da, t3.lightingColor[2],
									1 - t3.lightingColor[3]);
							if (t3.fogHintColor != null) {
								tr = (int) MathCalculator.lerp(tr,
										t3.fogHintColor[0], t3.fogHintColor[3]);
								tg = (int) MathCalculator.lerp(tg,
										t3.fogHintColor[1], t3.fogHintColor[3]);
								tb = (int) MathCalculator.lerp(tb,
										t3.fogHintColor[2], t3.fogHintColor[3]);
							}
							tr = MathCalculator.colorLock(tr);
							tg = MathCalculator.colorLock(tg);
							tb = MathCalculator.colorLock(tb);
							setPixel(xs, y, tr, tg, tb, bck);
						}
					}
				}
				xs++;
			}
		}
	}

	protected static void fillTriangle(final T3D t, final Texture texture,
			final int bck) {
		int[] pt = t.tri;
		if (pt[0] == maxWidth)
			pt[0] = pt[0] - 1;
		if (pt[2] == maxWidth)
			pt[2] = pt[2] - 1;
		if (pt[4] == maxWidth)
			pt[4] = pt[4] - 1;
		if (pt[0] > maxWidth) {

		} else {
			fillTriangleActual(t, texture, bck);
		}
	}

	protected static void fillTriangleActual(final T3D t,
			final Texture texture, final int bck) {

		if (userIntegralTriangles) {
			final int[] xy = t.tri;
			final int x0 = xy[0];
			final int y0 = xy[1];
			final int x1 = xy[2];
			final int y1 = xy[3];
			final int x2 = xy[4];
			final int y2 = xy[5];
			microhelperpoint vt1 = new microhelperpoint(x0, y0);
			microhelperpoint vt2 = new microhelperpoint(x1, y1);
			microhelperpoint vt3 = new microhelperpoint(x2, y2);
			final int minx = MathCalculator.min(x0, x1, x2);
			final int maxx = MathCalculator.max(x0, x1, x2);
			final int miny = MathCalculator.min(y0, y1, y2);
			final int maxy = MathCalculator.max(y0, y1, y2);
			final int[] minmaxarr = new int[] { minx, maxx, miny, maxy };
			microhelperpoint vswap;
			Color cTmp;
			if (vt1.y > vt2.y) {
				cTmp = t.c0i;
				vswap = vt1;
				vt1 = vt2;
				t.c0i = t.c1i;
				vt2 = vswap;
				t.c1i = cTmp;
			}
			if (vt1.y > vt3.y) {
				cTmp = t.c0i;
				vswap = vt1;
				t.c0i = t.c2i;
				vt1 = vt3;
				vt3 = vswap;
				t.c2i = cTmp;
			}
			if (vt2.y > vt3.y) {
				cTmp = t.c1i;
				vswap = vt2;
				vt2 = vt3;
				t.c1i = t.c2i;
				vt3 = vswap;
				t.c2i = cTmp;
			}
			if (vt2.y == vt3.y) {
				lowerTri(vt1, vt2, vt3, t.c0i, t.c1i, t.c2i, t, bck, minmaxarr,
						texture);
			} else if (vt1.y == vt2.y) {
				upperTri(vt1, vt2, vt3, t.c0i, t.c1i, t.c2i, t, bck, minmaxarr,
						texture);
			} else {
				vswap = new microhelperpoint(
						(int) (vt1.x + ((float) (vt2.y - vt1.y) / (float) (vt3.y - vt1.y))
								* (vt3.x - vt1.x)), vt2.y);
				float cBlue = t.c0i.getBlue()
						+ ((float) (vt2.y - vt1.y) / (float) (vt3.y - vt1.y))
						* (t.c2i.getBlue() - t.c0i.getBlue());
				float cRed = t.c0i.getRed()
						+ ((float) (vt2.y - vt1.y) / (float) (vt3.y - vt1.y))
						* (t.c2i.getRed() - t.c0i.getRed());
				float cGreen = t.c0i.getGreen()
						+ ((float) (vt2.y - vt1.y) / (float) (vt3.y - vt1.y))
						* (t.c2i.getGreen() - t.c0i.getGreen());
				cTmp = new Color((int) cRed, (int) cGreen, (int) cBlue);
				lowerTri(vt1, vt2, vswap, t.c0i, t.c1i, cTmp, t, bck,
						minmaxarr, texture);
				upperTri(vt2, vswap, vt3, t.c1i, cTmp, t.c2i, t, bck,
						minmaxarr, texture);
			}
		} else {
			int[] xy = t.tri;
			int x0 = xy[0];
			int y0 = xy[1];
			int x1 = xy[2];
			int y1 = xy[3];
			int x2 = xy[4];
			int y2 = xy[5];
			if (doTriangleSafetyChecks) {
				// Still has some issues:
				if (x2 > x1) {
					int swap = x1;
					x1 = x0;
					x0 = swap;
					swap = y1;
					y1 = y0;
					y0 = swap;
				}
				if (x1 - x0 == 0)
					x1 = 1;
				final float slope = ((float) (y1 - y0)) / ((float) (x1 - x0));
				final float yint = y1 - (slope * x1);
				final boolean aboveLine = y2 < (slope * x2) + yint;
				if (aboveLine) {
					if (x2 < x1 && x2 > x0) {
						int swap = x1;
						x1 = x2;
						x2 = swap;
						swap = y1;
						y1 = y2;
						y2 = swap;

						swap = x0;
						x0 = x2;
						x2 = swap;
						swap = y0;
						y0 = y2;
						y2 = swap;
					}
					if (x2 > x1) {
						int swap = x1;
						x1 = x2;
						x2 = swap;
						swap = y1;
						y1 = y2;
						y2 = swap;
					}
				} else {
					if (x2 < x1 && x2 > x0) {
						int swap = x1;
						x1 = x2;
						x2 = swap;
						swap = y1;
						y1 = y2;
						y2 = swap;
					}
					if (x2 < x0) {
						int swap = x1;
						x1 = x2;
						x2 = swap;
						swap = y1;
						y1 = y2;
						y2 = swap;
					}
				}
			}
			final int bity1 = y0 * 16;
			final int bity2 = y1 * 16;
			final int bity3 = y2 * 16;
			final int bitx1 = x0 * 16;
			final int bitx2 = x1 * 16;
			final int bitx3 = x2 * 16;
			final int deltax12 = bitx1 - bitx2;
			final int deltax23 = bitx2 - bitx3;
			final int deltax31 = bitx3 - bitx1;
			final int deltay12 = bity1 - bity2;
			final int deltay23 = bity2 - bity3;
			final int deltay31 = bity3 - bity1;
			final int nonbitdeltax12 = deltax12 << 4;
			final int nonbitdeltax23 = deltax23 << 4;
			final int nonbitdeltax31 = deltax31 << 4;
			final int nonbitdeltay12 = deltay12 << 4;
			final int nonbitdeltay23 = deltay23 << 4;
			final int nonbitdeltay31 = deltay31 << 4;
			int minx = (MathCalculator.min(bitx1, bitx2, bitx3) + 0xF) >> 4;
			int maxx = (MathCalculator.max(bitx1, bitx2, bitx3) + 0xF) >> 4;
			int miny = (MathCalculator.min(bity1, bity2, bity3) + 0xF) >> 4;
			int maxy = (MathCalculator.max(bity1, bity2, bity3) + 0xF) >> 4;
			int widthHeightMultiplex = maxWidth * maxHeight;
			if (maxy < 0 || maxx < 0 || minx > maxx || miny > maxy)
				return;
			if (Math.abs(maxx - minx) * Math.abs(maxy - miny) > widthHeightMultiplex)
				return;
			int c1 = deltay12 * bitx1 - deltax12 * bity1;
			int c2 = deltay23 * bitx2 - deltax23 * bity2;
			int c3 = deltay31 * bitx3 - deltax31 * bity3;
			if (deltay12 < 0 || (deltax12 == 0 && deltax12 > 0))
				c1++;
			if (deltay23 < 0 || (deltay23 == 0 && deltax23 > 0))
				c2++;
			if (deltay31 < 0 || (deltay31 == 0 && deltax31 > 0))
				c3++;
			int cy1 = c1 + deltax12 * (miny << 4) - deltay12 * (minx << 4);
			int cy2 = c2 + deltax23 * (miny << 4) - deltay23 * (minx << 4);
			int cy3 = c3 + deltax31 * (miny << 4) - deltay31 * (minx << 4);
			final int white = Color.white.getRGB();
			long iters = 0;
			for (int y = miny; y < maxy; y++) {
				if (iters > widthHeightMultiplex)
					return;
				int cx1 = cy1;
				int cx2 = cy2;
				int cx3 = cy3;
				float xux = 0.0f;
				float xuy = 0.0f;
				int indexg = 0;
				if (texture != null) {
					int ope = maxx - minx;
					if (ope == 0)
						ope = 1;
					xux = (1.0f / ope)
							* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHU
							* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
					ope = maxy - miny;
					if (ope == 0)
						ope = 1;
					xuy = (1.0f / ope)
							* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHV
							* texture.INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
					int ty = (int) ((y - miny) * xuy + t.tov);
					if (ty < 0)
						ty = -ty;
					if (ty >= texture.textureHeight)
						ty = (ty % texture.textureHeight);
					indexg = ty * texture.textureHeight;
				}
				for (int x = minx; x < maxx; x++) {
					if (cx1 > 0 && cx2 > 0 && cx3 > 0) {
						iters++;
						if (x < maxWidth && y < maxHeight && x > 0 && y > 0) {
							if (texture == null) {
								setColor(x0, y0, x1, y1, x2, y2, t, x, y, bck);
							} else {
								int tx = (int) ((x - minx) * xux + t.tou);
								if (tx < 0)
									tx = -tx;
								if (tx >= texture.textureWidth)
									tx = (tx % texture.textureWidth);
								// System.out.println("tx "+tx+"ty "+ ty);
								final int sindex = indexg + tx;
								if (sindex < 0
										|| sindex > texture.textureData.length - 1)
									return;
								else {
									setDirectPixel(x, y,
											texture.textureData[sindex]);
								}
							}
						}
					}
					cx1 -= nonbitdeltay12;
					cx2 -= nonbitdeltay23;
					cx3 -= nonbitdeltay31;
				}
				cy1 += nonbitdeltax12;
				cy2 += nonbitdeltax23;
				cy3 += nonbitdeltax31;
			}
		}
	}

	private static void setPixel(int x, int y, int r, int g, int b,
			int bckgrndclr) {
		int index = y * maxWidth + x;
		if (index < 0 || index > imgData.length - 1)
			return;
		if (individualPixelCheck) {
			if (imgData[index] != bckgrndclr)
				return;
		}
		imgData[index] = r << 16 | g << 8 | b;
	}

	private static void setDirectPixel(int x, int y, int rgb) {
		int index = y * maxWidth + x;
		if (index < 0 || index > imgData.length - 1)
			return;
		imgData[index] = rgb;
	}

	private static void setColor(int tri0x, int tri0y, int tri1x, int tri1y,
			int tri2x, int tri2y, T3D t, int x, int y, int bck) {
		final Color c0 = t.c0i;
		final Color c1 = t.c1i;
		final Color c2 = t.c2i;
		if (barycentricRasterizorOn) {
			float ww0 = baryCentricize(tri1x, tri1y, tri2x, tri2y, x, y);
			float ww1 = baryCentricize(tri2x, tri2y, tri0x, tri0y, x, y);
			float ww2 = baryCentricize(tri0x, tri0y, tri1x, tri1y, x, y);
			// System.out.println(ww0 + "," + ww1 + "," + ww2);
			float mag = 1.0f / fastSqrt(ww0 * ww0 + ww1 * ww1 + ww2 * ww2);
			ww0 = ww0 * mag;
			ww1 = ww1 * mag;
			ww2 = ww2 * mag;
			final int fr = (int) ((c0.getRed() * ww0) + (c1.getRed() * ww1) + (c2
					.getRed() * ww2));
			final int fg = (int) ((c0.getGreen() * ww0) + (c1.getGreen() * ww1) + (c2
					.getGreen() * ww2));
			final int fb = (int) ((c0.getBlue() * ww0) + (c1.getBlue() * ww1) + (c2
					.getBlue() * ww2));
			/*
			 * if (fr > 255) fr = 255; if (fg > 255) fg = 255; if (fb > 255) fb
			 * = 255; if (fr < 0) fr = 0; if (fg < 0) fg = 0; if (fb < 0) fb =
			 * 0;
			 */
			setPixel(x, y, fr, fg, fb, bck);
		} else {
			final int r0 = c0.getRed();
			final int r1 = c1.getRed();
			final int r2 = c2.getRed();
			final int g0 = c0.getGreen();
			final int g1 = c1.getGreen();
			final int g2 = c2.getGreen();
			final int b0 = c0.getBlue();
			final int b1 = c1.getBlue();
			final int b2 = c2.getBlue();
			final int x0d = tri0x - x;
			final int x1d = tri1x - x;
			final int x2d = tri2x - x;
			final int y0d = tri0y - y;
			final int y1d = tri1y - y;
			final int y2d = tri2y - y;
			float d0 = (fastSqrt((x0d * x0d) + (y0d * y0d)));
			float d1 = (fastSqrt((x1d * x1d) + (y1d * y1d)));
			float d2 = (fastSqrt((x2d * x2d) + (y2d * y2d)));
			final float magnitude = 1.0f / fastSqrt((d0 * d0) + (d1 * d1)
					+ (d2 * d2));
			d0 = d0 * magnitude;
			d1 = d1 * magnitude;
			d2 = d2 * magnitude;
			final int fr = (int) ((r0 * d0) + (g0 * d0) + (b0 * d0));
			final int fg = (int) ((r1 * d1) + (g1 * d1) + (b1 * d1));
			int fb = (int) ((r2 * d2) + (g2 * d2) + (b2 * d2));
			/*
			 * if (fr > 255) fr = 255; if (fg > 255) fg = 255; if (fb > 255) fb
			 * = 255; if (fr < 0) fr = 0; if (fg < 0) fg = 0; if (fb < 0) fb =
			 * 0;
			 */
			setPixel(x, y, fr, fg, fb, bck);
		}
		// return new Color(fr,fg,fb).getRGB();
	}

	private static float baryCentricize(int x0, int y0, int x1, int y1, int x2,
			int y2) {
		int v = (x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0);
		if (v < 0)
			v = -v;
		return v;
	}

	private static float fastSqrt(float amount) {
		return (float) (Math.sqrt(amount));
		// if (amount < 0)
		// return 0;
		// if (amount >= 131072 || amount < 6)
		// return (float)(Math.sqrt(amount));
		// else
		// return (sqrtCache[(int)amount]);
	}

	private static final float halfPI = (float) (Math.PI * 0.5);
	private static final float threeHalvesPI = (float) (Math.PI * 1.5);

	private IdentityHashMap<TRIGSTORE,P3D> trigCache = new IdentityHashMap<TRIGSTORE,P3D>();
	class TRIGSTORE {
		public float x, y, z, dx, dy, dz;
		public boolean equals(Object o2) {
			TRIGSTORE o = (TRIGSTORE)o2;
			final float elip = 2.001f;
			/*if (o.dx > dx - elip && o.dx < dx + elip
					&& o.dy > dy - elip && o.dy < dy + elip &&
					o.dz > dz - elip && o.dz < dz + elip &&
					o.x > x - elip && o.x < x + elip
					&& o.y > y - elip && o.y < y + elip &&
					o.z > z - elip && o.z < z + elip) {*/
			if (o.dx == dx && o.dy == dy && o.dz == dz && o.x == x && o.y == y && o.z == z) {
				System.out.println("Equal");
				return true;
			}
			else
				return false;
		}
	}
	
	private void dealWithRotation(float x, float y, float z, float dx,
			float dy, float dz) {
		
		/*TRIGSTORE t = new TRIGSTORE();
		t.x = (int)x;
		t.y = (int)y;
		t.z = (int)z;
		t.dx = (int)dx;
		t.dy = (int)dy;
		t.dz = (int)dz;
		if (trigCache.size() > 8000)
			trigCache.clear();
		if (trigCache.containsKey(t)) {
			P3D sample = trigCache.get(t);
			rotationX = sample.x;
			rotationY = sample.y;
			rotationZ = sample.z;
			return;
		}*/
		/*
		 * dx += 0.0001f; dy += 0.0001f; dz += 0.0001f;
		 */
		/*
		 * setAngleRadiusX(x,y,z); setAngleRadiusY(x,y,z);
		 * setAngleRadiusZ(x,y,z); if (dx != 0 && dy == 0 && dz == 0)
		 * rotateX(dx); else if (dx == 0 && dy != 0 && dz == 0) rotateY(dy);
		 * else if (dx == 0 && dy == 0 && dz != 0) rotateZ(dz); else if (dx != 0
		 * && dy != 0 && dz == 0) rotateXY(x,y,z,dx,dy); else if (dx != 0 && dy
		 * == 0 && dz != 0) rotateXZ(x,y,z,dx,dz); else if (dx == 0 && dy != 0
		 * && dz != 0) rotateYZ(x,y,z,dy,dz); else if (dx != 0 && dy != 0 && dz
		 * != 0)
		 */
		if (dx != 0 && dy != 0 && dz != 0)
			rotateXYZ(x, y, z, dx, dy, dz);
		else if (dx != 0 && dz != 0)
			rotateXZ(x, y, z, dx, dz);
		else if (dx != 0 && dy != 0)
			rotateXY(x, y, z, dx, dy);
		else if (dz != 0 && dy != 0)
			rotateYZ(x, y, z, dy, dz);
		else if (dx != 0) {
			// There's something wrong with my code. Fallback to 2D rotation.

			// setAngleRadiusX(x,y,z);
			// rotateX(dx);
			rotateXZ(x, y, z, dx, 0);
		} else if (dy != 0) {
			// setAngleRadiusY(x,y,z);
			// rotateY(dy);
			rotateYZ(x, y, z, dy, 0);
		} else if (dz != 0) {
			// setAngleRadiusZ(x,y,z);
			// rotateZ(dz);
			rotateXZ(x, y, z, 0, dz);
		}
		else {
			rotateXZ(x,y,z,0.0001f,0.0001f);
		}
		//trigCache.put(t, new P3D(rotationX,rotationY,rotationZ));
	}

	private float angleX = 0, angleY = 0, angleZ = 0, radiusX = 0, radiusY = 0,
			radiusZ = 0;

	private void setAngleRadiusX(float x, float y, float z) {
		if (z != 0)
			angleX = (float) Math.atan(y / z);
		else if (y > 0)
			angleX = halfPI;
		else
			angleX = threeHalvesPI;
		if (z < 0)
			angleX += Math.PI;
		radiusX = fastSqrt(y * y + z * z);
	}

	private void setAngleRadiusY(float x, float y, float z) {
		if (z != 0)
			angleY = (float) Math.atan(x / z);
		else if (x > 0)
			angleY = halfPI;
		else
			angleY = threeHalvesPI;
		if (z < 0)
			angleY += Math.PI;
		radiusY = fastSqrt(x * x + z * z);
	}

	private void setAngleRadiusZ(float x, float y, float z) {
		if (x != 0)
			angleZ = (float) Math.atan(y / x);
		else if (y > 0)
			angleZ = halfPI;
		else
			angleZ = threeHalvesPI;
		if (x < 0)
			angleZ += Math.PI;
		radiusZ = fastSqrt(x * x + y * y);
	}

	private void rotateX(float deltaX) {
		rotationY = (float) (MathCalculator.sin(angleX + deltaX) * radiusX);
		rotationZ = (float) (MathCalculator.cos(angleX + deltaX) * radiusX);
	}

	private void rotateY(float deltaY) {
		rotationX = (float) (MathCalculator.sin(angleY + deltaY) * radiusY);
		rotationZ = (float) (MathCalculator.cos(angleY + deltaY) * radiusY);
	}

	private void rotateZ(float deltaZ) {
		rotationX = (float) (MathCalculator.cos(angleZ + deltaZ) * radiusZ);
		rotationY = (float) (MathCalculator.sin(angleZ + deltaZ) * radiusZ);
	}

	private void rotateXYZ(float x, float y, float z, float deltaX,
			float deltaY, float deltaZ) {
		setAngleRadiusX(x * scale.x, y * scale.y, z * scale.z);
		rotateX(deltaX);
		setAngleRadiusY(x * scale.x, rotationY, rotationZ);
		rotateY(deltaY);
		setAngleRadiusZ(rotationX, rotationY, rotationZ);
		rotateZ(deltaZ);
	}

	public void rotateXY(float x, float y, float z, float deltaX, float deltaY) {
		setAngleRadiusX(x * scale.x, y * scale.y, z * scale.z);
		rotateX(deltaX);
		setAngleRadiusY(x * scale.x, rotationY, rotationZ);
		rotateY(deltaY);
	}

	public void rotateYZ(float x, float y, float z, float deltaY, float deltaZ) {
		setAngleRadiusY(x * scale.x, y * scale.y, z * scale.z);
		rotateY(deltaY);
		setAngleRadiusZ(rotationX, y * scale.y, rotationZ);
		rotateZ(deltaZ);
	}

	public void rotateXZ(float x, float y, float z, float deltaX, float deltaZ) {
		setAngleRadiusX(x, y * scale.y, z * scale.z);
		rotateX(deltaX);
		setAngleRadiusZ(x * scale.x, rotationY, rotationZ);
		rotateZ(deltaZ);
	}

	// In reality this should probably be in its ownclass.
	public static void generateStagedPoints(long seed, int size,
			double roughness, double mountainess) {
		stagedWidth = size;
		System.out.println("Using seed:" + seed);
		// Some debugging/performance detections.
		long queryStart = System.currentTimeMillis();
		// Setup the seed.
		Random r = new Random(seed);
		if (size <= 0)
			throw new IllegalArgumentException(
					"Fatal terrain size. Terrain size must be at least 1.");
		double[][] data = new double[size][size];
		System.out.println("Generating grid " + size + "x" + size + ".");
		// Setup the number of iterations, based on roughness parameter.
		int operator = (int) (size * roughness);
		// Fill the array with random values, to help mix up the
		// data a little (less consistancy, more random).
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				data[x][z] = r.nextDouble() * 0.6;
			}
		}

		// foreach iteration, create a random "half-sphere",
		// and average it to the current terrain values.
		for (int i = 0; i < operator; i++) {
			// The "half-sphere"'s center
			int x = r.nextInt(size);
			int z = r.nextInt(size);
			// The radius, based on mountainness.
			double radius = r.nextDouble() * mountainess;
			double drad = radius * radius;
			int intRad = (int) (radius);
			// The starting values for the rectangle of
			// the sphere. I have no clue why I called
			// it xcorn and zcorn.
			int xcorn = x - intRad;
			int zcorn = z - intRad;
			// The maximum values for the rectangle of
			// the sphere.
			int xmax = xcorn + (2 * intRad);
			int zmax = zcorn + (2 * intRad);
			// Loop through each "point" of the rectangle
			for (int xl = xcorn; xl < xmax; xl++) {
				// This is a little performance fix
				// (rather than puting this in the
				// loop below this).
				double xacc = x - xl;
				xacc = xacc * xacc;
				for (int zl = zcorn; zl < zmax; zl++) {
					double zacc = z - zl;
					zacc = zacc * zacc;
					// Gets the height value of the sphere.
					double technicalHeight = (drad - (xacc + zacc)) / (size);
					// Absolute value
					if (technicalHeight < 0)
						technicalHeight = -technicalHeight;
					// If we are within the array, average it to
					// previously added values.
					if (xl < size && zl < size && zl >= 0 && xl >= 0)
						data[xl][zl] = (data[xl][zl] + technicalHeight) * 0.5;
				}
			}
		}
		// Convert the 2D array to a 1D
		float[] bytecode = new float[stagedWidth * stagedWidth];
		for (int x = 0; x < stagedWidth; x++) {
			for (int y = 0; y < stagedWidth; y++) {
				bytecode[y * stagedWidth + x] = (float) data[x][y];
			}
		}
		long rangeQuery = System.currentTimeMillis() - queryStart;
		System.out.println("Terrain generation took: " + rangeQuery + " ms.");
		stagedPoints = bytecode;
		data = null;
	}

	public static float getHeightData(int sampleX, int sampleZ) {
		int index = sampleZ * stagedWidth + sampleX;
		if (index < 0 || index > stagedPoints.length - 1)
			return 0.0f;
		return stagedPoints[index];
	}
}

class TriangleComparator implements Comparator<T3D> {
	@Override
	public int compare(T3D t0, T3D t1) {
		// float x = Math.abs(t0.midpoint.x) - Math.abs(t1.midpoint.x);
		// float y = Math.abs(t0.midpoint.y) - Math.abs(t1.midpoint.y);
		// float z = Math.abs(t0.midpoint.z) - Math.abs(t1.midpoint.z);
		// return (int)(x + y + z);
		return (int) ((t0.zdepth - t1.zdepth) * 1000);
	}
}

class Matrix {
	private float[] internalData;
	private float[] swapMemory;
	// private float[] retrievalMatrix;
	private float screenX = 0;
	private float screenY = 0;
	private float screenZ = 0;

	public Matrix() {
		internalData = new float[16];
		zeroSwapMemory();
	}

	public Matrix(float[] original) {
		internalData = new float[16];
		// Ultra fast way to do a deep copy.
		System.arraycopy(original, 0, internalData, 0, 16);
	}

	public float getScreenX() {
		return screenX;
	}

	public float getScreenY() {
		return screenY;
	}

	public float getScreenZ() {
		return screenZ;
	}

	public void calculateScreenValues(float requestedX, float requestedY,
			float requestedZ) {
		P3D res = multiply(new P3D(requestedX, requestedY, requestedZ));
		screenX = res.x;
		screenY = res.y;
		screenZ = res.z;
	}

	public void rotateX(float amount) {
		float c = (float) Math.cos(amount * rad);
		float s = (float) Math.sin(amount * rad);
		zeroSwapMemory();
		swapMemory[5] = c;
		swapMemory[9] = -s;
		swapMemory[6] = s;
		swapMemory[10] = c;
		internalData = operatorAdd(swapMemory);
	}

	public void rotateY(float amount) {
		float c = (float) Math.cos(amount * rad);
		float s = (float) Math.sin(amount * rad);
		zeroSwapMemory();
		swapMemory[0] = c;
		swapMemory[8] = s;
		swapMemory[2] = -s;
		swapMemory[10] = c;
		internalData = operatorAdd(swapMemory);
	}

	private static final float rad = (float) (180.0 / Math.PI);

	public void rotateZ(float amount) {
		float c = (float) Math.cos(amount * rad);
		float s = (float) Math.sin(amount * rad);
		zeroSwapMemory();
		swapMemory[0] = c;
		swapMemory[4] = -s;
		swapMemory[1] = s;
		swapMemory[5] = c;
		internalData = operatorAdd(swapMemory);
	}

	public void rotate(float x, float y, float z, float amount) {
		float c = (float) Math.cos(amount * rad);
		float ci = 1.0f - c;
		float s = (float) Math.sin(amount * rad);
		float zs = z * s;
		float ys = y * s;
		float xs = x * s;
		zeroSwapMemory();
		swapMemory[0] = x * x * ci + c;
		swapMemory[1] = y * x * ci + zs;
		swapMemory[2] = x * z * ci - ys;
		swapMemory[3] = 0;
		swapMemory[4] = x * y * ci - zs;
		swapMemory[5] = y * y * ci + c;
		swapMemory[6] = y * z * ci + xs;
		swapMemory[7] = 0;
		swapMemory[8] = x * z * ci + ys;
		swapMemory[9] = y * z * ci - xs;
		swapMemory[10] = z * z * ci + c;
		swapMemory[11] = 0;
		swapMemory[12] = 0;
		swapMemory[13] = 0;
		swapMemory[14] = 0;
		swapMemory[15] = 0;
		internalData = operatorAdd(swapMemory);
	}

	public void translate(float x, float y, float z) {
		zeroSwapMemory();
		swapMemory[12] = x;
		swapMemory[13] = y;
		swapMemory[14] = z;
		internalData = operatorAdd(swapMemory);
	}

	public void scale(float x, float y, float z) {
		zeroSwapMemory();
		swapMemory[0] = x;
		swapMemory[5] = y;
		swapMemory[10] = z;
		internalData = operatorAdd(swapMemory);
	}

	private void zeroSwapMemory() {
		if (swapMemory == null)
			swapMemory = new float[16];
		swapMemory[0] = 0.0f;
		swapMemory[1] = 0.0f;
		swapMemory[2] = 0.0f;
		swapMemory[3] = 0.0f;
		swapMemory[4] = 0.0f;
		swapMemory[5] = 0.0f;
		swapMemory[6] = 0.0f;
		swapMemory[7] = 0.0f;
		swapMemory[8] = 0.0f;
		swapMemory[9] = 0.0f;
		swapMemory[10] = 0.0f;
		swapMemory[11] = 0.0f;
		swapMemory[12] = 0.0f;
		swapMemory[13] = 0.0f;
		swapMemory[14] = 0.0f;
		swapMemory[15] = 0.0f;
	}

	public void zero() {
		internalData[0] = perpMat0;
		internalData[1] = 0.0f;
		internalData[2] = 0.0f;
		internalData[3] = 0.0f;
		internalData[4] = 0.0f;
		internalData[5] = perpMat5;
		internalData[6] = 0.0f;
		internalData[7] = 0.0f;
		internalData[8] = 0.0f;
		internalData[9] = 0.0f;
		internalData[10] = perpMat10;
		internalData[11] = perpMat11;
		internalData[12] = 0.0f;
		internalData[13] = 0.0f;
		internalData[14] = perpMat14;
		internalData[15] = 0.0f;
	}

	private float perpMat0 = 0.0f;
	private float perpMat5 = 0.0f;
	private float perpMat10 = 0.0f;
	private float perpMat14 = 0.0f;
	private float perpMat11 = 0.0f;
	public static final float RADIANSTODEGREES = (float) (180.0 / Math.PI);
	public static final float DEGREESTORADIANS = (float) (Math.PI / 180.0);

	public void perspective(float fov, float width, float height, float near,
			float far) {
		float rview = fov * DEGREESTORADIANS;
		float f = (float) (1.0 / Math.tan(rview * 0.5));
		zeroSwapMemory();
		float ratio = width / height;
		swapMemory[0] = f / ratio;
		swapMemory[5] = f;
		swapMemory[10] = (far + near) / (near - far);
		swapMemory[14] = (2.0f * far * near) / (near - far);
		swapMemory[11] = -1;
		internalData = multiply(swapMemory);
	}

	public P3D multiply(P3D point) {
		float x = point.x;
		float y = point.y;
		float z = point.z;
		// float w = 1;

		float[] result = new float[4];
		result[0] = (internalData[0] * x) + (internalData[1] * y)
				+ (internalData[2] * z) + internalData[3];
		result[1] = (internalData[4] * x) + (internalData[5] * y)
				+ (internalData[6] * z) + internalData[7];
		result[2] = (internalData[8] * x) + (internalData[9] * y)
				+ (internalData[10] * z) + internalData[11];
		result[3] = (internalData[12] * x) + (internalData[13] * y)
				+ (internalData[14] * z) + internalData[15];
		// System.out.println(result[0] + "," + result[1] + "," + result[2] +
		// "," + result[3]);
		return new P3D(result[0], result[1], result[2]);
	}

	public float[] operatorAdd(float[] a) {
		// a = multiply(a);
		// System.out.println(Matrix.float4x4ArrayToString(internalData));
		for (int i = 0; i < a.length; i++) {
			internalData[i] = a[i] + internalData[i];
		}
		internalData[5] = -internalData[5];
		// System.out.println(Matrix.float4x4ArrayToString(internalData));
		return internalData;
	}

	public float[] multiply(float[] otherMatrix) {
		float[] result = new float[16];
		result[0] = (internalData[0] * otherMatrix[0])
				+ (internalData[4] * otherMatrix[1])
				+ (internalData[8] * otherMatrix[2])
				+ (internalData[12] * otherMatrix[3]);
		result[4] = (internalData[0] * otherMatrix[4])
				+ (internalData[4] * otherMatrix[5])
				+ (internalData[8] * otherMatrix[6])
				+ (internalData[12] * otherMatrix[7]);
		result[8] = (internalData[0] * otherMatrix[8])
				+ (internalData[4] * otherMatrix[9])
				+ (internalData[8] * otherMatrix[10])
				+ (internalData[12] * otherMatrix[11]);
		result[12] = (internalData[0] * otherMatrix[12])
				+ (internalData[4] * otherMatrix[13])
				+ (internalData[8] * otherMatrix[14])
				+ (internalData[12] * otherMatrix[15]);
		result[1] = (internalData[1] * otherMatrix[0])
				+ (internalData[5] * otherMatrix[1])
				+ (internalData[9] * otherMatrix[2])
				+ (internalData[13] * otherMatrix[3]);
		result[5] = (internalData[1] * otherMatrix[4])
				+ (internalData[5] * otherMatrix[5])
				+ (internalData[9] * otherMatrix[6])
				+ (internalData[13] * otherMatrix[7]);
		result[9] = (internalData[1] * otherMatrix[8])
				+ (internalData[5] * otherMatrix[9])
				+ (internalData[9] * otherMatrix[10])
				+ (internalData[13] * otherMatrix[11]);
		result[13] = (internalData[1] * otherMatrix[12])
				+ (internalData[5] * otherMatrix[13])
				+ (internalData[9] * otherMatrix[14])
				+ (internalData[13] * otherMatrix[15]);
		result[2] = (internalData[2] * otherMatrix[0])
				+ (internalData[6] * otherMatrix[1])
				+ (internalData[10] * otherMatrix[2])
				+ (internalData[14] * otherMatrix[3]);
		result[6] = (internalData[2] * otherMatrix[4])
				+ (internalData[6] * otherMatrix[5])
				+ (internalData[10] * otherMatrix[6])
				+ (internalData[14] * otherMatrix[7]);
		result[10] = (internalData[2] * otherMatrix[8])
				+ (internalData[6] * otherMatrix[9])
				+ (internalData[10] * otherMatrix[10])
				+ (internalData[14] * otherMatrix[11]);
		result[14] = (internalData[2] * otherMatrix[12])
				+ (internalData[6] * otherMatrix[13])
				+ (internalData[10] * otherMatrix[14])
				+ (internalData[14] * otherMatrix[15]);
		result[3] = (internalData[3] * otherMatrix[0])
				+ (internalData[7] * otherMatrix[1])
				+ (internalData[11] * otherMatrix[2])
				+ (internalData[15] * otherMatrix[3]);
		result[7] = (internalData[3] * otherMatrix[4])
				+ (internalData[7] * otherMatrix[5])
				+ (internalData[11] * otherMatrix[6])
				+ (internalData[15] * otherMatrix[7]);
		result[11] = (internalData[3] * otherMatrix[8])
				+ (internalData[7] * otherMatrix[9])
				+ (internalData[11] * otherMatrix[10])
				+ (internalData[15] * otherMatrix[11]);
		result[15] = (internalData[3] * otherMatrix[12])
				+ (internalData[7] * otherMatrix[13])
				+ (internalData[11] * otherMatrix[14])
				+ (internalData[15] * otherMatrix[15]);
		return result;
	}

	public float[] operatorAdd(float[] a, float[] b) {
		// return a;
		// System.out.println(Matrix.float4x4ArrayToString(b)+"btr\n");
		float[] res = new float[16];
		res[0] = a[0] + b[0];
		res[1] = a[1] + b[1];
		res[2] = a[2] + b[2];
		res[3] = a[3] + b[3];
		res[4] = a[4] + b[4];
		res[5] = a[5] + b[5];
		res[6] = a[6] + b[6];
		res[7] = a[7] + b[7];
		res[8] = a[8] + b[8];
		res[9] = a[9] + b[9];
		res[10] = a[10] + b[10];
		res[11] = a[11] + b[11];
		res[12] = a[12] + b[12];
		res[13] = a[13] + b[13];
		res[14] = a[14] + b[14];
		res[15] = a[15] + b[15];
		return a;

	}

	private static final String float4x4ArrayToString(float[] arr) {
		String c = ",";
		String line0 = "[" + fch(arr[0]) + c + fch(arr[1]) + c + fch(arr[2])
				+ c + fch(arr[3]) + "]\n";
		String line1 = "[" + fch(arr[4]) + c + fch(arr[5]) + c + fch(arr[6])
				+ c + fch(arr[7]) + "]\n";
		String line2 = "[" + fch(arr[8]) + c + fch(arr[9]) + c + fch(arr[10])
				+ c + fch(arr[11]) + "]\n";
		String line3 = "[" + fch(arr[12]) + c + fch(arr[13]) + c + fch(arr[14])
				+ c + fch(arr[15]) + "]";
		return line0 + line1 + line2 + line3;
	}

	public String toString() {
		return float4x4ArrayToString(internalData);
	}

	private static String fch(float v) {
		return String.format("%05f", v);
	}
}