import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Texture {
	public final int textureWidth;
	public final int textureHeight;
	public int[] textureData;
	public float INTERNALTEXTURESCALESIZEDONOTTOUCHU;
	public float INTERNALTEXTURESCALESIZEDONOTTOUCHV;
	public float INTERNALTEXTURESCALESIZEDONOTTOUCHZ;
	private BufferedImage texture;
	private boolean loaded = false;

	public Texture(BufferedImage buffer) {
		textureWidth = buffer.getWidth();
		textureHeight = buffer.getHeight();
		texture = buffer;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void deload() {
		loaded = false;
	}

	public void forceSetMemory(int[] data) {
		if (textureWidth * textureHeight != data.length)
			throw new IllegalStateException("Error: Image must be same size.");
		textureData = data;
	}

	public void forceSetMemory(BufferedImage img) {
		// We must have an integer type image in order to cast
		// it to a DataBufferInt.
		if (img.getType() == BufferedImage.TYPE_INT_RGB)
			forceSetMemory(((DataBufferInt) img.getRaster().getDataBuffer())
					.getData());
		else if (img.getType() == BufferedImage.TYPE_INT_ARGB)
			throw new IllegalArgumentException(
					"Although the image type specified is supported, please change it to a RGB type instead of ARGB. Transparency may be supported in the future.");
	}

	public void loadInternalDoNotTouch() {
		if (loaded)
			return;
		textureData = new int[textureWidth * textureHeight];
		for (int x = 0; x < textureWidth; x++) {
			for (int y = 0; y < textureHeight; y++) {
				final Color c = new Color(texture.getRGB(x, y));
				textureData[y * textureWidth + x] = c.getRed() << 16
						| c.getGreen() << 8 | c.getBlue();
			}
		}
		texture = null;
		loaded = true;
		System.gc();
	}
}
