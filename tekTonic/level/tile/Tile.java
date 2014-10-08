package tekTonic.level.tile;

import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile grass = new GrassTile (Sprite.grass);
	public static Tile flower = new FlowerTile (Sprite.flower);
	public static Tile rock = new RockTile (Sprite.rock);
	public static Tile hedge = new HedgeTile (Sprite.hedge);
	public static Tile wall = new WallTile (Sprite.wall);
	public static Tile water = new WaterTile (Sprite.water);
	public static Tile floor = new FloorTile (Sprite.floor);
	public static Tile voidTile = new VoidTile (Sprite.voidSprite);

	public static final int col_grass = 0xFF00FF00;
	public static final int col_flower = 0xFFFFFF00;
	public static final int col_rock = 0xFF7F7F00;
	public static final int col_hedge = 0xFF267F00;
	public static final int col_wall = 0xFF000000;
	public static final int col_water = 0xFF267F00;
	public static final int col_floor = 0xFFFF0000;

	// spawn = FFA44F 34,60

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile (x << 4, y << 4, this);
	}

	public boolean solid() {
		return false;
	}

}
