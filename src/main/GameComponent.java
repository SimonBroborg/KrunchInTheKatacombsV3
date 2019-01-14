package main;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class GameComponent extends JComponent
{
    /**
     * The height of the frame
     */
    public static final int HEIGHT = 480 * 2;
    /**
     * The width of the frame
     */
    public static final int WIDTH = 640 * 2;

    // Target FPS
    private static final int FPS = 60;
    // Target time for each frame
    private static final long TARGET_TIME = 1000 / FPS;

    /**
     * The fps
     */
    public static int ups = 0;

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
	final JFrame frame = new JFrame("Krunch in the Katacombs");
	Cursor gameCursor = Toolkit.getDefaultToolkit()
		.createCustomCursor(new Sprite("resources/Sprites/Misc/sketchedCursor.png").getImage(), new Point(0, 0),
				    "Game cursor");
	frame.setCursor(gameCursor);

	// Frame 'settings'

	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	frame.setFocusTraversalKeysEnabled(false); // this enables tab to be listened to
	frame.add(this, BorderLayout.CENTER);

	// Set the frame in the center of the screen
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);

	frame.pack();

	frame.addKeyListener(new InputHandler(gsm));
	this.addMouseListener(new MouseHandler(gsm));
	frame.setFocusable(true);
	frame.setVisible(true);
    }

    /**
     * The game loop. Updates the game and repaints it.
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
	    if (System.currentTimeMillis() - timer > 1000) {
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
     *
     * @param g The graphics object used
     */
    protected void paintComponent(Graphics g) {
	//super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;

	// Antialiasing for smoother lines
	g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	g2d.setClip(0, 0, WIDTH, HEIGHT);
	gsm.draw(g2d);
    }

}
