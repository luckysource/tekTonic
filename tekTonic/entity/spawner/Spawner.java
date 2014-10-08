package tekTonic.entity.spawner;

import java.util.ArrayList;

import tekTonic.entity.Entity;
import tekTonic.level.Level;

public class Spawner extends Entity {
	private ArrayList<Entity> entities = new ArrayList<Entity> ();

	public enum Type {
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init (level);
		this.x = x;
		this.y = y;
		this.type = type;

	}
}
