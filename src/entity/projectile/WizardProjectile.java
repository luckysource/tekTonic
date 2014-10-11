package tekTonic.entity.projectile;

import tekTonic.entity.spawner.ParticleSpawner;
import tekTonic.graphics.Screen;
import tekTonic.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public static final int FIRE_DELAY = 15;

	public WizardProjectile(double x, double y, double dir) {
		super (x, y, dir);
		range = random.nextInt (100) + 150;
		speed = 2.5;
		damage = 20;
		sprite = Sprite.projectile_wizard;
		nx = speed * Math.cos (angle);
		ny = speed * Math.sin (angle);

	}

	public void update() {
		if (level.tileCollision ((int) (x + nx), (int) (y + ny), 7, 5, 4)) {
			level.add (new ParticleSpawner ((int) x, (int) y, 50, 50, level));
			remove ();
		}
		move ();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance () > range)
			remove ();
	}

	private double distance() {
		double dist = 0;
		dist = Math.sqrt ((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile ((int) x - 12, (int) y - 2, this);
	}
}
