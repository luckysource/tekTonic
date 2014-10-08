package tekTonic.graphics;

import java.util.Random;

import tekTonic.entity.mob.Chaser;
import tekTonic.entity.mob.Mob;
import tekTonic.entity.projectile.Projectile;
import tekTonic.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int xOffset, yOffset;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	private Random random = new Random ();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < tiles.length; i++ ) {
			tiles[i] = random.nextInt (0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++ ) {
			pixels[i] = 0;
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++ ) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++ ) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					break;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight (); y++ ) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth (); x++ ) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					break;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth ()];
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++ ) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++ ) {
				int xa = x + xp;
				if (xa < - tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}

	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize (); y++ ) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize (); x++ ) {
				int xa = x + xp;
				if (xa < - p.getSpriteSize () || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = p.getSprite ().pixels[x + y * p.getSpriteSize ()];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}

	}

	public void renderSprite(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight (); y++ ) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth (); x++ ) {
				int xa = x + xp;
				if (xa < sprite.getWidth () || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * sprite.getWidth ()];
				if (col != 0x000000ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < mob.getSprite ().getHeight (); y++ ) {
			int ya = y + yp;
			for (int x = 0; x < mob.getSprite ().getWidth (); x++ ) {
				int xa = x + xp;
				if (xa < mob.getSprite ().getWidth () || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite ().pixels[x + y * mob.getSprite ().getWidth ()];
				if (mob instanceof Chaser && col == 0xffFFFFFF)
					col = 0xff816BFF;
				if (col != 0xffFF00DC)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
