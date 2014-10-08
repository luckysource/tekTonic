package tekTonic.level;

import java.util.ArrayList;
import java.util.List;

import tekTonic.entity.Entity;
import tekTonic.entity.mob.Player;
import tekTonic.entity.particle.Particle;
import tekTonic.entity.projectile.Projectile;
import tekTonic.graphics.Screen;
import tekTonic.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	// protected int tile_size;

	private List<Entity> entities = new ArrayList<Entity> ();
	private List<Projectile> projectiles = new ArrayList<Projectile> ();
	private List<Particle> particles = new ArrayList<Particle> ();

	private List<Player> players = new ArrayList<Player> ();

	public static Level spawn = new SpawnLevel ("/levels/spawnLevel.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel ();

	}

	public Level(String path) {
		loadLevel (path);
		generateLevel ();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {

	}

	public void update() {
		for (int i = 0; i < entities.size (); i++ ) {
			entities.get (i).update ();
		}
		for (int i = 0; i < projectiles.size (); i++ ) {
			projectiles.get (i).update ();
		}
		for (int i = 0; i < particles.size (); i++ ) {
			particles.get (i).update ();
		}
		for (int i = 0; i < players.size (); i++ ) {
			players.get (i).update ();
		}
		remove ();
	}

	private void remove() {
		for (int i = 0; i < entities.size (); i++ ) {
			if (entities.get (i).isRemoved ())
				entities.remove (i);
		}
		for (int i = 0; i < projectiles.size (); i++ ) {
			if (projectiles.get (i).isRemoved ())
				projectiles.remove (i);
		}
		for (int i = 0; i < particles.size (); i++ ) {
			if (particles.get (i).isRemoved ())
				particles.remove (i);
		}
		for (int i = 0; i < players.size (); i++ ) {
			if (players.get (i).isRemoved ())
				players.remove (i);
		}
	}

	private void time() {

	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;

		for (int c = 0; c < 4; c++ ) {
			double xt = (x - c % 2 * size + xOffset) >> 4;
			double yt = (y - c % 2 * size + yOffset) >> 4;
			if (getTile ((int) xt, (int) yt).solid ()) {
				solid = true;
			}
		}
		return solid;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset (xScroll, yScroll);
		// define render region of our screen
		int x0 = xScroll >> 4; // divided by 16, the pixel size of a tile
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++ ) {
			for (int x = x0; x < x1; x++ ) {
				getTile (x, y).render (x, y, screen);
			}
		}

		for (int i = 0; i < entities.size (); i++ ) {
			entities.get (i).render (screen);
		}
		for (int i = 0; i < projectiles.size (); i++ ) {
			projectiles.get (i).render (screen);
		}
		for (int i = 0; i < particles.size (); i++ ) {
			particles.get (i).render (screen);
		}
		for (int i = 0; i < players.size (); i++ ) {
			players.get (i).render (screen);
		}
	}

	public void add(Entity e) {
		e.init (this);
		if (e instanceof Particle) {
			particles.add ((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add ((Projectile) e);
		} else if (e instanceof Player) {
			players.add ((Player) e);
		} else {
			entities.add (e);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player> ();
		double ex = (int) e.getX ();
		double ey = (int) e.getY ();
		for (int i = 0; i < players.size (); i++ ) {
			Player player = players.get (i);
			double x = player.getX ();
			double y = player.getY ();

			double dist = Math.sqrt ((x - ex) * (x - ex) + (y - ey) * (y - ey));
			if (dist <= radius) {
				result.add (player);
			}
		}
		return result;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity> ();
		double ex = e.getX ();
		double ey = e.getY ();
		for (int i = 0; i < entities.size (); i++ ) {
			Entity entity = entities.get (i);
			double x = entity.getX ();
			double y = entity.getY ();

			double dist = Math.sqrt ((x - ex) * (x - ex) + (y - ey) * (y - ey));
			if (dist <= radius) {
				result.add (entity);
			}
		}
		return result;
	}

	public Player getPlayerAt(int index) {
		return players.get (index);
	}

	public Player getClientPlayer() {
		return players.get (0);
	}

	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_grass)
			return Tile.grass;
		if (tiles[x + y * width] == Tile.col_flower)
			return Tile.flower;
		if (tiles[x + y * width] == Tile.col_rock)
			return Tile.rock;
		if (tiles[x + y * width] == Tile.col_wall)
			return Tile.wall;
		if (tiles[x + y * width] == Tile.col_water)
			return Tile.water;
		if (tiles[x + y * width] == Tile.col_hedge)
			return Tile.hedge;
		if (tiles[x + y * width] == Tile.col_floor)
			return Tile.floor;
		return Tile.voidTile;
	}

}
