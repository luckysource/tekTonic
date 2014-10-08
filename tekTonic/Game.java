package tekTonic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import tekTonic.entity.mob.Player;
import tekTonic.graphics.Screen;
import tekTonic.input.Keyboard;
import tekTonic.input.Mouse;
import tekTonic.level.Level;
import tekTonic.level.TileCoord;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int winWidth = 300;
	private static int winHeight = winWidth / 16 * 9;
	private static int scale = 3;
	public static String title = "Rain";

	private Thread gameThread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private Screen screen;

	private BufferedImage image = new BufferedImage (winWidth, winHeight, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster ().getDataBuffer ()).getData ();

	public Game() {
		Dimension size = new Dimension (winWidth * scale, winHeight * scale);
		setPreferredSize (size);

		screen = new Screen (winWidth, winHeight);
		frame = new JFrame ();
		key = new Keyboard ();
		level = Level.spawn;
		TileCoord playerSpawn = new TileCoord (34, 60);
		player = new Player (playerSpawn.getX (), playerSpawn.getY (), key);
		level.add (player);
		addKeyListener (key);

		Mouse mouse = new Mouse ();
		addMouseListener (mouse);
		addMouseMotionListener (mouse);

	}

	public static int getWindowWidth() {
		return winWidth * scale;
	}

	public static int getWindowHeight() {
		return winHeight * scale;
	}

	// creates a new thread, which executes the run() method
	// start() runs parallel tasks
	// synchronized prevents thread interferences and memory inconsistency
	// errors
	public synchronized void start() {
		running = true;
		gameThread = new Thread (this, "Display thread");
		gameThread.start (); // thread implements runnable, so calls run()
	}

	public synchronized void stop() {
		running = false;
		try {
			gameThread.join ();
		} catch (InterruptedException e) {
			e.printStackTrace ();
		}
	}

	// Game class implements runnable, is called by start()
	public void run() {
		long lastTime = System.nanoTime ();
		long timer = System.currentTimeMillis ();
		final double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int updates = 0;
		double delta = 0;
		requestFocus ();// makes canvas focused so you don't have to click each
						// time!
		while (running) {
			long now = System.nanoTime ();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update ();
				updates++ ;
				delta-- ;
			}
			render ();
			frames++ ;

			if (System.currentTimeMillis () - timer > 1000) {
				timer += 1000;
				System.out.println (updates + " ups, " + frames + " fps");
				frame.setTitle (title + "  |   " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	public void update() {
		key.update ();
		level.update ();
		// update player by self - in case anything goes wrong player is still
		// able to update itself
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy ();
		if (bs == null) {
			createBufferStrategy (3);
			return;
		}

		screen.clear ();
		// renders level centring around player's coordinates
		double xScroll = player.getX () - screen.width / 2;
		double yScroll = player.getY () - screen.height / 2;
		level.render ((int) xScroll, (int) yScroll, screen);

		// UI
		// Sprite sprite = new Sprite (80, 80, 0xff00ff);
		// screen.renderSprite (0, 0, sprite, false);
		for (int i = 0; i < pixels.length; i++ ) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics (); // create graphics
		// any graphics to display on screen have to be called here
		g.setColor (Color.BLACK);
		g.fillRect (0, 0, getWidth (), getHeight ());
		g.drawImage (image, 0, 0, getWidth (), getHeight (), null);
		g.setColor (Color.WHITE);
		g.fillRect (Mouse.getX (), Mouse.getY (), 25, 15);
		g.dispose (); // remove graphics not being used anymore
		bs.show ();
	}

	public static void main(String[] args) {
		Game game = new Game ();
		game.frame.setResizable (false);
		game.frame.setTitle (Game.title);
		game.frame.add (game);
		game.frame.pack (); // use the preferred size for window
		game.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo (null); // centers
		game.frame.setVisible (true); // makes sure window is not hidden
		game.start ();
	}

}
