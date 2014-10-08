package tekTonic.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet ("/textures/spritesheet.png", 256);
	public static SpriteSheet projectile_wizard = new SpriteSheet ("/textures/projectiles/wizard.png", 48);
	public static SpriteSheet player = new SpriteSheet ("/textures/sheets/player_sheet.png", 128, 96);
	public static SpriteSheet dummy = new SpriteSheet ("/textures/sheets/player_sheet.png", 128, 96);

	public static SpriteSheet player_up = new SpriteSheet (player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_down = new SpriteSheet (player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet (player, 3, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet (player, 1, 0, 1, 3, 32);

	public static SpriteSheet dummy_up = new SpriteSheet (dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummy_down = new SpriteSheet (dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummy_left = new SpriteSheet (dummy, 3, 0, 1, 3, 32);
	public static SpriteSheet dummy_right = new SpriteSheet (dummy, 1, 0, 1, 3, 32);

	public Sprite[] sprites;

	// public static SpriteSheet spawnLevel = new
	// SpriteSheet("/textures/levels/spawn.png",16);

	// sub SpriteSheet
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int pixelx = x * spriteSize;
		int pixely = y * spriteSize;
		int pixelw = width * spriteSize;
		int pixelh = height * spriteSize;
		if (width == height)
			SIZE = width;
		else
			SIZE = - 1;
		WIDTH = pixelw;
		HEIGHT = pixelh;

		pixels = new int[pixelw * pixelh];
		for (int y0 = 0; y0 < pixelh; y0++ ) {
			int yp = pixely + y0;
			for (int x0 = 0; x0 < pixelw; x0++ ) {
				int xp = pixelx + x0;
				pixels[x0 + y0 * pixelw] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++ ) {
			for (int xa = 0; xa < width; xa++ ) {

				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++ ) {
					for (int x0 = 0; x0 < spriteSize; x0++ ) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + spriteSize
								* (y0 + ya * spriteSize)];
						// check to see if it would work with a horizontal
						// spritesheet
					}
				}
				Sprite sprite = new Sprite (spritePixels, spriteSize, spriteSize);
				sprites[frame++ ] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load ();
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = - 1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		load ();
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read (SpriteSheet.class.getResource (path));
			int w = image.getWidth ();
			int h = image.getHeight ();
			image.getRGB (0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace ();
		}

	}
}
