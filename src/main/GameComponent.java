package main;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class GameComponent extends JComponent
{
    // Dimensions
    private static final int HEIGHT = 480;
    private static final int WIDTH = 640;
    private static final int SCALE = 2;
    private static final int SCALED_HEIGHT = HEIGHT * SCALE;
    private static final int SCALED_WIDTH = WIDTH * SCALE;

    // FPS
    private static final int FPS = 60;
    private static final long TARGET_TIME = 1000 / FPS;


    public GameComponent() {
	init();
    }

    private void init() {
	final JFrame frame = new JFrame("Krunch in the Katacombs");

	// Frame 'settings'
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.setPreferredSize(new Dimension(SCALED_WIDTH, SCALED_HEIGHT));
	frame.setFocusTraversalKeysEnabled(false); // this enables tab to be listened to
	frame.add(this, BorderLayout.CENTER);
	frame.pack();
	frame.setFocusable(true);
	frame.setVisible(true);
    }

    public void run() {

	while (true) {
	    long start = System.nanoTime();

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

    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	g2d.setClip(0, 0, SCALED_WIDTH, SCALED_HEIGHT);

    }
}
