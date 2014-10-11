package tekTonic.entity.mob;

import tekTonic.entity.Entity;
import tekTonic.entity.projectile.Projectile;
import tekTonic.entity.projectile.WizardProjectile;
import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;

public abstract class Mob extends Entity {
	// abstract cannot be instantiated, we will not be creating a new mob class
	// mob is a template for all actual mobs we create

	// protected - the sprite can only be used within the mob class and its
	// subclasses

	protected Sprite sprite;
	protected boolean moving = false;
	protected boolean walking = false;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	protected Direction dir;

	public abstract void update();

	public abstract void render(Screen screen);

	public Sprite getSprite() {
		return sprite;
	}

	public void move(double xa, double ya) {
		if (xa > 0)
			dir = Direction.RIGHT;
		if (xa < 0)
			dir = Direction.LEFT;
		if (ya > 0)
			dir = Direction.DOWN;
		if (ya < 0)
			dir = Direction.UP;

		while (xa != 0) {
			if ((Math.abs (xa) - 1) > 0) {
				if (! collision (signed (xa), ya)) {
					this.x += signed (xa);
				}
				xa -= signed (xa);
			} else {
				if (! collision (signed (xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}

		while (ya != 0) {
			if ((Math.abs (ya) - 1) > 0) {
				if (! collision (xa, signed (ya))) {
					this.y += signed (ya);
				}
				ya -= signed (ya);
			} else {
				if (! collision (xa, signed (ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}

	}

	private int signed(double value) {
		if (value > 0)
			return 1;
		else
			return - 1;
	}

	protected void shoot(double x, double y, double pDir) {
		Projectile p = new WizardProjectile (x, y, pDir);
		level.add (p);
	}

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++ ) {
			double xt = ((x + xa) - c % 2 * 16) / 16; // ((x+xa)+c%2*14 -8)/16
			double yt = ((y + ya) - c / 2 * 16) / 16; // *12+3
			int ix = (int) Math.floor (xt);
			int iy = (int) Math.floor (yt);

			if (level.getTile (ix, iy).solid ()) {
				solid = true;
			}
		}
		return solid;

	}

}
