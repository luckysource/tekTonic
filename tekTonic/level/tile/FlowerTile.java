package tekTonic.level.tile;

import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super (sprite);
		// TODO Auto-generated constructor stub
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile (x << 4, y << 4, this);
	}
}
