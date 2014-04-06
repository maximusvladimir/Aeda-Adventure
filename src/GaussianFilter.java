import java.awt.Color;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import java.util.Random;

/**
 * A filter which applies Gaussian blur to an image. This is a subclass of ConvolveFilter
 * which simply creates a kernel with a Gaussian distribution for blurring.
 * @author Jerry Huxtable
 */
public class GaussianFilter extends ConvolveFilter {

	static final long serialVersionUID = 5377089073023183684L;

	protected float radius;
	protected Kernel kernel;
	
	/**
	 * Construct a Gaussian filter
	 */
	public GaussianFilter() {
		this(2);
	}

	/**
	 * Construct a Gaussian filter
	 * @param radius blur radius in pixels
	 */
	public GaussianFilter(float radius) {
		setRadius(radius);
	}

	/**
	 * Set the radius of the kernel, and hence the amount of blur. The bigger the radius, the longer this filter will take.
	 * @param radius the radius of the blur in pixels.
	 */
	public void setRadius(float radius) {
		this.radius = radius;
		kernel = makeKernel(radius);
	}
	
	/**
	 * Get the radius of the kernel.
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        src.getRGB( 0, 0, width, height, inPixels, 0, width );

		convolveAndTranspose(kernel, inPixels, outPixels, width, height, alpha, CLAMP_EDGES);
		convolveAndTranspose(kernel, outPixels, inPixels, height, width, alpha, CLAMP_EDGES);

        dst.setRGB( 0, 0, width, height, inPixels, 0, width );
        return dst;
    }

	public static void convolveAndTranspose(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		float[] matrix = kernel.getKernelData( null );
		int cols = kernel.getWidth();
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			int index = y;
			int ioffset = y*width;
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset+col];

					if (f != 0) {
						int ix = x+col;
						if ( ix < 0 ) {
							if ( edgeAction == CLAMP_EDGES )
								ix = 0;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						} else if ( ix >= width) {
							if ( edgeAction == CLAMP_EDGES )
								ix = width-1;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						}
						int rgb = inPixels[ioffset+ix];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? PixelUtils.clamp((int)(a+0.5)) : 0xff;
				int ir = PixelUtils.clamp((int)(r+0.5));
				int ig = PixelUtils.clamp((int)(g+0.5));
				int ib = PixelUtils.clamp((int)(b+0.5));
				outPixels[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
                index += height;
			}
		}
	}

	/**
	 * Make a Gaussian blur kernel.
	 */
	public static Kernel makeKernel(float radius) {
		int r = (int)Math.ceil(radius);
		int rows = r*2+1;
		float[] matrix = new float[rows];
		float sigma = radius/3;
		float sigma22 = 2*sigma*sigma;
		float sigmaPi2 = 2*(float)Math.PI*sigma;
		float sqrtSigmaPi2 = (float)Math.sqrt(sigmaPi2);
		float radius2 = radius*radius;
		float total = 0;
		int index = 0;
		for (int row = -r; row <= r; row++) {
			float distance = row*row;
			if (distance > radius2)
				matrix[index] = 0;
			else
				matrix[index] = (float)Math.exp(-(distance)/sigma22) / sqrtSigmaPi2;
			total += matrix[index];
			index++;
		}
		for (int i = 0; i < rows; i++)
			matrix[i] /= total;

		return new Kernel(rows, 1, matrix);
	}

	public String toString() {
		return "Blur/Gaussian Blur...";
	}
}

class PixelUtils {

	public final static int REPLACE = 0;
	public final static int NORMAL = 1;
	public final static int MIN = 2;
	public final static int MAX = 3;
	public final static int ADD = 4;
	public final static int SUBTRACT = 5;
	public final static int DIFFERENCE = 6;
	public final static int MULTIPLY = 7;
	public final static int HUE = 8;
	public final static int SATURATION = 9;
	public final static int VALUE = 10;
	public final static int COLOR = 11;
	public final static int SCREEN = 12;
	public final static int AVERAGE = 13;
	public final static int OVERLAY = 14;
	public final static int CLEAR = 15;
	public final static int EXCHANGE = 16;
	public final static int DISSOLVE = 17;
	public final static int DST_IN = 18;
	public final static int ALPHA = 19;
	public final static int ALPHA_TO_GRAY = 20;

	private static Random randomGenerator = new Random();

	/**
	 * Clamp a value to the range 0..255
	 */
	public static int clamp(int c) {
		if (c < 0)
			return 0;
		if (c > 255)
			return 255;
		return c;
	}

	public static int interpolate(int v1, int v2, float f) {
		return clamp((int)(v1+f*(v2-v1)));
	}
	
	public static int brightness(int rgb) {
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		return (r+g+b)/3;
	}
	
	public static boolean nearColors(int rgb1, int rgb2, int tolerance) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1-r2) <= tolerance && Math.abs(g1-g2) <= tolerance && Math.abs(b1-b2) <= tolerance;
	}
	
	private final static float hsb1[] = new float[3];//FIXME-not thread safe
	private final static float hsb2[] = new float[3];//FIXME-not thread safe
	
	// Return rgb1 painted onto rgb2
	public static int combinePixels(int rgb1, int rgb2, int op) {
		return combinePixels(rgb1, rgb2, op, 0xff);
	}
	
	public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha, int channelMask) {
		return (rgb2 & ~channelMask) | combinePixels(rgb1 & channelMask, rgb2, op, extraAlpha);
	}
	
	public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha) {
		if (op == REPLACE)
			return rgb1;
		int a1 = (rgb1 >> 24) & 0xff;
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int a2 = (rgb2 >> 24) & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;

		switch (op) {
		case NORMAL:
			break;
		case MIN:
			r1 = Math.min(r1, r2);
			g1 = Math.min(g1, g2);
			b1 = Math.min(b1, b2);
			break;
		case MAX:
			r1 = Math.max(r1, r2);
			g1 = Math.max(g1, g2);
			b1 = Math.max(b1, b2);
			break;
		case ADD:
			r1 = clamp(r1+r2);
			g1 = clamp(g1+g2);
			b1 = clamp(b1+b2);
			break;
		case SUBTRACT:
			r1 = clamp(r2-r1);
			g1 = clamp(g2-g1);
			b1 = clamp(b2-b1);
			break;
		case DIFFERENCE:
			r1 = clamp(Math.abs(r1-r2));
			g1 = clamp(Math.abs(g1-g2));
			b1 = clamp(Math.abs(b1-b2));
			break;
		case MULTIPLY:
			r1 = clamp(r1*r2/255);
			g1 = clamp(g1*g2/255);
			b1 = clamp(b1*b2/255);
			break;
		case DISSOLVE:
			if ((randomGenerator.nextInt() & 0xff) <= a1) {
				r1 = r2;
				g1 = g2;
				b1 = b2;
			}
			break;
		case AVERAGE:
			r1 = (r1+r2)/2;
			g1 = (g1+g2)/2;
			b1 = (b1+b2)/2;
			break;
		case HUE:
		case SATURATION:
		case VALUE:
		case COLOR:
			Color.RGBtoHSB(r1, g1, b1, hsb1);
			Color.RGBtoHSB(r2, g2, b2, hsb2);
			switch (op) {
			case HUE:
				hsb2[0] = hsb1[0];
				break;
			case SATURATION:
				hsb2[1] = hsb1[1];
				break;
			case VALUE:
				hsb2[2] = hsb1[2];
				break;
			case COLOR:
				hsb2[0] = hsb1[0];
				hsb2[1] = hsb1[1];
				break;
			}
			rgb1 = Color.HSBtoRGB(hsb2[0], hsb2[1], hsb2[2]);
			r1 = (rgb1 >> 16) & 0xff;
			g1 = (rgb1 >> 8) & 0xff;
			b1 = rgb1 & 0xff;
			break;
		case SCREEN:
			r1 = 255 - ((255 - r1) * (255 - r2)) / 255;
			g1 = 255 - ((255 - g1) * (255 - g2)) / 255;
			b1 = 255 - ((255 - b1) * (255 - b2)) / 255;
			break;
		case OVERLAY:
			int m, s;
			s = 255 - ((255 - r1) * (255 - r2)) / 255;
			m = r1 * r2 / 255;
			r1 = (s * r1 + m * (255 - r1)) / 255;
			s = 255 - ((255 - g1) * (255 - g2)) / 255;
			m = g1 * g2 / 255;
			g1 = (s * g1 + m * (255 - g1)) / 255;
			s = 255 - ((255 - b1) * (255 - b2)) / 255;
			m = b1 * b2 / 255;
			b1 = (s * b1 + m * (255 - b1)) / 255;
			break;
		case CLEAR:
			r1 = g1 = b1 = 0xff;
			break;
		case DST_IN:
			r1 = clamp((r2*a1)/255);
			g1 = clamp((g2*a1)/255);
			b1 = clamp((b2*a1)/255);
			a1 = clamp((a2*a1)/255);
			return (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
		case ALPHA:
			a1 = a1*a2/255;
			return (a1 << 24) | (r2 << 16) | (g2 << 8) | b2;
		case ALPHA_TO_GRAY:
			int na = 255-a1;
			return (a1 << 24) | (na << 16) | (na << 8) | na;
		}
		if (extraAlpha != 0xff || a1 != 0xff) {
			a1 = a1*extraAlpha/255;
			int a3 = (255-a1)*a2/255;
			r1 = clamp((r1*a1+r2*a3)/255);
			g1 = clamp((g1*a1+g2*a3)/255);
			b1 = clamp((b1*a1+b2*a3)/255);
			a1 = clamp(a1+a3);
		}
		return (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
	}

}

class ConvolveFilter extends AbstractBufferedImageOp {

	static final long serialVersionUID = 2239251672685254626L;
	
	public static int ZERO_EDGES = 0;
	public static int CLAMP_EDGES = 1;
	public static int WRAP_EDGES = 2;

	protected Kernel kernel = null;
	public boolean alpha = true;
	private int edgeAction = CLAMP_EDGES;

	/**
	 * Construct a filter with a null kernel. This is only useful if you're going to change the kernel later on.
	 */
	public ConvolveFilter() {
		this(new float[9]);
	}

	/**
	 * Construct a filter with the given 3x3 kernel.
	 * @param matrix an array of 9 floats containing the kernel
	 */
	public ConvolveFilter(float[] matrix) {
		this(new Kernel(3, 3, matrix));
	}
	
	/**
	 * Construct a filter with the given kernel.
	 * @param rows	the number of rows in the kernel
	 * @param cols	the number of columns in the kernel
	 * @param matrix	an array of rows*cols floats containing the kernel
	 */
	public ConvolveFilter(int rows, int cols, float[] matrix) {
		this(new Kernel(cols, rows, matrix));
	}
	
	/**
	 * Construct a filter with the given 3x3 kernel.
	 * @param matrix an array of 9 floats containing the kernel
	 */
	public ConvolveFilter(Kernel kernel) {
		this.kernel = kernel;	
	}

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public Kernel getKernel() {
		return kernel;
	}

	public void setEdgeAction(int edgeAction) {
		this.edgeAction = edgeAction;
	}

	public int getEdgeAction() {
		return edgeAction;
	}

    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        getRGB( src, 0, 0, width, height, inPixels );

		convolve(kernel, inPixels, outPixels, width, height, alpha, edgeAction);

        setRGB( dst, 0, 0, width, height, outPixels );
        return dst;
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }
    
    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }
    
    public Point2D getPoint2D( Point2D srcPt, Point2D dstPt ) {
        if ( dstPt == null )
            dstPt = new Point2D.Double();
        dstPt.setLocation( srcPt.getX(), srcPt.getY() );
        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

	public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, int edgeAction) {
		convolve(kernel, inPixels, outPixels, width, height, true, edgeAction);
	}
	
	public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		if (kernel.getHeight() == 1)
			convolveH(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
		else if (kernel.getWidth() == 1)
			convolveV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
		else
			convolveHV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
	}
	
	/**
	 * Convolve with a 2D kernel
	 */
	public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int cols = kernel.getWidth();
		int rows2 = rows/2;
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if (0 <= iy && iy < height)
						ioffset = iy*width;
					else if ( edgeAction == CLAMP_EDGES )
						ioffset = y*width;
					else if ( edgeAction == WRAP_EDGES )
						ioffset = ((iy+height) % height) * width;
					else
						continue;
					int moffset = cols*(row+rows2)+cols2;
					for (int col = -cols2; col <= cols2; col++) {
						float f = matrix[moffset+col];

						if (f != 0) {
							int ix = x+col;
							if (!(0 <= ix && ix < width)) {
								if ( edgeAction == CLAMP_EDGES )
									ix = x;
								else if ( edgeAction == WRAP_EDGES )
									ix = (x+width) % width;
								else
									continue;
							}
							int rgb = inPixels[ioffset+ix];
							a += f * ((rgb >> 24) & 0xff);
							r += f * ((rgb >> 16) & 0xff);
							g += f * ((rgb >> 8) & 0xff);
							b += f * (rgb & 0xff);
						}
					}
				}
				int ia = alpha ? PixelUtils.clamp((int)(a+0.5)) : 0xff;
				int ir = PixelUtils.clamp((int)(r+0.5));
				int ig = PixelUtils.clamp((int)(g+0.5));
				int ib = PixelUtils.clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}

	/**
	 * Convolve with a kernel consisting of one row
	 */
	public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int cols = kernel.getWidth();
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			int ioffset = y*width;
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset+col];

					if (f != 0) {
						int ix = x+col;
						if ( ix < 0 ) {
							if ( edgeAction == CLAMP_EDGES )
								ix = 0;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						} else if ( ix >= width) {
							if ( edgeAction == CLAMP_EDGES )
								ix = width-1;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						}
						int rgb = inPixels[ioffset+ix];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? PixelUtils.clamp((int)(a+0.5)) : 0xff;
				int ir = PixelUtils.clamp((int)(r+0.5));
				int ig = PixelUtils.clamp((int)(g+0.5));
				int ib = PixelUtils.clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}

	/**
	 * Convolve with a kernel consisting of one column
	 */
	public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int rows2 = rows/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if ( iy < 0 ) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = 0;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else if ( iy >= height) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = (height-1)*width;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else
						ioffset = iy*width;

					float f = matrix[row+rows2];

					if (f != 0) {
						int rgb = inPixels[ioffset+x];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? PixelUtils.clamp((int)(a+0.5)) : 0xff;
				int ir = PixelUtils.clamp((int)(r+0.5));
				int ig = PixelUtils.clamp((int)(g+0.5));
				int ib = PixelUtils.clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}

	public String toString() {
		return "Blur/Convolve...";
	}
}

abstract class AbstractBufferedImageOp implements BufferedImageOp {

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }
    
    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }
    
    public Point2D getPoint2D( Point2D srcPt, Point2D dstPt ) {
        if ( dstPt == null )
            dstPt = new Point2D.Double();
        dstPt.setLocation( srcPt.getX(), srcPt.getY() );
        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

	/**
	 * A convenience method for getting ARGB pixels from an image. This tries to avoid the performance
	 * penalty of BufferedImage.getRGB unmanaging the image.
	 */
	public int[] getRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
		return image.getRGB( x, y, width, height, pixels, 0, width );
    }

	/**
	 * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
	 * penalty of BufferedImage.setRGB unmanaging the image.
	 */
	public void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB( x, y, width, height, pixels, 0, width );
    }
}