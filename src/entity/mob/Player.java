package tekTonic.entity.mob;

import tekTonic.Game;
import tekTonic.entity.projectile.WizardProjectile;
import tekTonic.graphics.AnimatedSprite;
import tekTonic.graphics.Screen;
import tekTonic.graphics.SpriteSheet;
import tekTonic.input.Keyboard;
import tekTonic.input.Mouse;

public class Player extends Mob {
	// note that Mob only has access to one sprite

	private Keyboard input;
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite (SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite (SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite (SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite (SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	public int fireDelay = 0;

	public Player(Keyboard input) {
		this.input = input;
		// sprite = animSprite.getSprite ();
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		fireDelay = WizardProjectile.FIRE_DELAY;
	}

	public void update() {

		if (walking)
			animSprite.update ();
		else
			animSprite.resetSprite ();

		if (fireDelay > 0)
			fireDelay-- ;
		double xa = 0, ya = 0;
		double speed = 1;

		if (input.up) {
			ya -= speed;
			animSprite = up;
		} else if (input.down) {
			ya += speed;
			animSprite = down;
		}
		if (input.left) {
			xa -= speed;
			animSprite = left;
		} else if (input.right) {
			xa += speed;
			animSprite = right;
		}

		if (xa != 0 || ya != 0) {
			move (xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear ();
		updateShooting ();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles ().size (); i++ ) {
			if (level.getProjectiles ().get (i).isRemoved ()) {
				level.getProjectiles ().remove (i);
			}
		}

	}

	private void updateShooting() {

		if (Mouse.getButton () == 1 && fireDelay == 0) {
			double dx = (Mouse.getX () - Game.getWindowWidth () / 2);
			double dy = (Mouse.getY () - Game.getWindowHeight () / 2);
			double pDir = Math.atan2 (dy, dx);
			shoot (x, y, pDir);
			fireDelay = WizardProjectile.FIRE_DELAY;

		}
	}

	// screen is very important - we have passed it in so we know what object to
	// manipulate
	// rendering at a specific location - not fixed to just being centre
	// we keep track of the player by use of x and y inherited from entity class
	public void render(Screen screen) {
		sprite = animSprite.getSprite ();
		screen.renderMob ((int) (x - 16), (int) (y - 16), this);
	}
}
