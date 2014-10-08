package tekTonic.entity.particle;

import tekTonic.entity.Entity;
import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;

public class Particle extends Entity {
	// we create a lot of particles together
	// we can manage all these in an array
	private Sprite sprite;
	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(int x, int y, int life) {
		this.life = life + random.nextInt (20) - 10;
		this.x = x;
		this.xx = x;
		this.y = y;
		this.yy = y;
		sprite = Sprite.particle_normal;

		this.xa = random.nextGaussian ();
		this.ya = random.nextGaussian ();
		this.zz = random.nextFloat () + 2.0;

	}

	public void update() {
		time++ ;
		if (time >= 10000)
			time = 0;
		if (time > life)
			remove ();
		za -= 0.1;

		if (zz < 0) {
			zz = 0;
			za *= - 0.55;
			xa *= 0.4;
			ya *= 0.4;
		}

		move (xx + xa, (yy + ya) + (zz + za));
	}

	private void move(double x, double y) {
		if (collision (x, y)) {
			this.xa *= - 0.5;
			this.ya *= - 0.5;
			this.za *= - 0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}

	public boolean collision(double x, double y) {
		boolean solid = false;

		for (int c = 0; c < 4; c++ ) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c % 2 * 16) / 16;
			int ix = (int) Math.ceil (xt);
			int iy = (int) Math.ceil (yt);
			if (c % 2 == 0) {
				ix = (int) Math.floor (xt);
				iy = (int) Math.floor (yt);
			}
			if (level.getTile (ix, iy).solid ()) {
				solid = true;
			}
		}
		return solid;
	}

	/*
		for (int c = 0; c < 4; c++ ) {
			int xt = ((x + xa) + c % 2 * 16 - 8) / 16;
			if (level.getTile (xt, y / 16).solid ()) {
				solid = true;
			}
		}
	 */

	public void render(Screen screen) {
		screen.renderSprite ((int) xx, (int) yy - (int) zz, sprite, true);
	}

}
