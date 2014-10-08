package tekTonic.level.tile;

import tekTonic.graphics.Sprite;

public class HedgeTile extends Tile {

	public HedgeTile(Sprite sprite) {
		super (sprite);
		// TODO Auto-generated constructor stub
	}

	public boolean solid() {
		return true;
	}

	public boolean breakable() {
		return true;
	}

}
