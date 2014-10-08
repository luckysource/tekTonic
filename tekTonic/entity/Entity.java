package tekTonic.entity;

import java.util.Random;

import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;
import tekTonic.level.Level;

public class Entity {

	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random ();

	public Entity() {

	}

	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void update() {

	}

	public void render(Screen screen) {
		if (sprite != null)
			screen.renderSprite ((int) x, (int) y, sprite, true);
	}

	public void remove() {
		// Remove from level

		removed = true;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isRemoved() {
		return this.removed;
	}

	public void init(Level level) {
		this.level = level;
	}
}