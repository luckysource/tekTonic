package tekTonic.entity.mob;

import tekTonic.graphics.AnimatedSprite;
import tekTonic.graphics.Screen;
import tekTonic.graphics.SpriteSheet;

public class Dummy extends Mob {

	private int directionTimer = 0;
	private AnimatedSprite down = new AnimatedSprite (SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite (SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite (SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite (SpriteSheet.dummy_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		// sprite = Sprite.flower;
	}

	@Override
	public void update() {
		int xa = 0, ya = 0;
		directionTimer++ ;
		if (directionTimer > random.nextInt (70) + 40) {
			dirChange ();
			directionTimer = 0;
		}

		if (walking)
			animSprite.update ();
		else
			animSprite.setFrame (0);

		if (dir == Direction.UP) {
			ya-- ;
			animSprite = up;
		} else if (dir == Direction.DOWN) {
			ya++ ;
			animSprite = down;
		}
		if (dir == Direction.LEFT) {
			xa-- ;
			animSprite = left;
		} else if (dir == Direction.RIGHT) {
			xa++ ;
			animSprite = right;
		}

		/*if (random.nextInt (4) == 0) {
			xa = 0;
			ya = 0;
		}*/

		if (xa != 0 || ya != 0) {
			move (xa, ya);
			walking = true;
		} else {
			walking = false;
			animSprite.resetSprite ();
		}

	}

	public void move(int xa, int ya) {
		super.move (xa, ya);
	}

	public void dirChange() {
		double randDir = Math.random ();
		if (randDir < 0.25)
			dir = Direction.UP;
		else if (randDir < 0.5)
			dir = Direction.LEFT;
		else if (randDir < 0.75)
			dir = Direction.LEFT;
		else
			dir = Direction.RIGHT;
	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite ();
		screen.renderMob ((int) (x - 16), (int) (y - 16), this);
	}

}
