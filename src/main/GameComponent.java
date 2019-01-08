package main;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class GameComponent extends JComponent
{
	// Dimensions
	public static int HEIGHT = 480;
	public static int WIDTH = 640;
	public static int SCALE = 2;

	private static Dimension fullSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

	public static int ups;

	/**
	 * The height of the frame
	 */
	public static int SCALED_HEIGHT = (int) fullSize.getHeight();
	/**
	 * The width of the frame
	 */
	public static int SCALED_WIDTH = (int) fullSize.getWidth();



	// FPS
	public static final int FPS = 60;
	private static final long TARGET_TIME = 1000 / FPS;

	JFrame frame;


	// Game state manager
	private GameStateManager gsm = null;

	public GameComponent() {
		init();
	}

	private void init() {
		gsm = new GameStateManager();
		setFrame();
	}

	/**
	 * Create a frame from the game and add the component and listeners
	 */
	private void setFrame() {
		frame = new JFrame("Krunch in the Katacombs");
		Cursor gameCursor = Toolkit.getDefaultToolkit()
				.createCustomCursor(new Sprite("resources/Sprites/Misc/sketchedCursor.png").getImage(), new Point(0, 0),
						"Game cursor");
		frame.setCursor(gameCursor);


		SCALED_WIDTH = fullSize.width;
		SCALED_HEIGHT = fullSize.height;
		// Frame 'settings'
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(fullSize);
		frame.setFocusTraversalKeysEnabled(false); // this enables tab to be listened to
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.addKeyListener(new InputHandler(gsm));
		this.addMouseListener(new MouseHandler(gsm));
		frame.setFocusable(true);
		frame.setVisible(true);
	}

	/**
	 *  The game loop. Updates the game and repaints it.
	 */
	public void run() {

		// Used to count the fps: https://www.youtube.com/watch?v=rh31YOZh5ZM

		long timer = System.currentTimeMillis();
		int updates = 0;
		while (true) {
			long start = System.nanoTime();

			update();
			updates++;
			this.repaint();

			long elapsed = System.nanoTime() - start;
			long wait = TARGET_TIME - elapsed / 1000000;

			if (wait < 0) wait = 5;

			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			// Check the fps
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				ups = updates;
				updates = 0;
			}
		}
	}

	/**
	 * Update the game state
	 */
	private void update() {
		gsm.update(this.getMousePosition());
	}

	/**
	 * Paint everything to the screen
	 * @param g The graphics object used
	 */
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Antialiasing for smoother lines
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setClip(0, 0, SCALED_WIDTH, SCALED_HEIGHT);
		gsm.draw(g2d);
	}

	public static void setHEIGHT(int HEIGHT) {
		GameComponent.SCALED_HEIGHT = HEIGHT;
	}

	public static void setWIDTH(int WIDTH) {
		GameComponent.SCALED_WIDTH = WIDTH;
	}
}
