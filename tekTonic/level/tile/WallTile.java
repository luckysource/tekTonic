package tekTonic.level.tile;

import tekTonic.graphics.Sprite;

public class WallTile extends Tile {

	public WallTile(Sprite sprite) {
		super (sprite);
		// TODO Auto-generated constructor stub
	}

	public boolean solid() {
		return true;
	}

	public boolean breakable() {
		return false;
	}
}
