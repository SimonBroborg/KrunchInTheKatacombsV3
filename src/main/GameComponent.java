package main;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class GameComponent extends JComponent
{
    // Dimensions
    public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public static final int SCALE = 2;
    public static final int SCALED_HEIGHT = HEIGHT * SCALE;
    public static final int SCALED_WIDTH = WIDTH * SCALE;

    // FPS
    private static final int FPS = 60;
    private static final long TARGET_TIME = 1000 / FPS;


    // Game state manager
    private GameStateManager gsm = null;

    public GameComponent() {
	init();
    }

    private void init() {
	gsm = new GameStateManager();
	final JFrame frame = new JFrame("Krunch in the Katacombs");


	Cursor gameCursor = Toolkit.getDefaultToolkit()
		.createCustomCursor(new Sprite("resources/Sprites/Misc/sketchedCursor.png").getImage(), new Point(0, 0),
				    "Game cursor");
	frame.setCursor(gameCursor);

	// Frame 'settings'
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.setPreferredSize(new Dimension(SCALED_WIDTH, SCALED_HEIGHT));
	frame.setFocusTraversalKeysEnabled(false); // this enables tab to be listened to
	frame.add(this, BorderLayout.CENTER);
	frame.pack();
	frame.addKeyListener(new InputHandler(gsm));
	frame.setFocusable(true);
	frame.setVisible(true);
    }

    public void run() {

	boolean running = true;
	while (running) {
	    long start = System.nanoTime();

	    update();
	    this.repaint();

	    long elapsed = System.nanoTime() - start;
	    long wait = TARGET_TIME - elapsed / 1000000;

	    if (wait < 0) wait = 5;

	    try {
		Thread.sleep(wait);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

    private void update() {
	gsm.update(this.getMousePosition());
    }

    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	g2d.setClip(0, 0, SCALED_WIDTH, SCALED_HEIGHT);
	gsm.draw(g2d);
	g2d.setColor(Color.RED);

    }
}
