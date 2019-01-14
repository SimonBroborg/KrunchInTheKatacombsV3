package map;

import main.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Creates a background for a level.
 */
public class Background {
    private Sprite sprite;

    private double x;
    private double y;
    private double dx;
    private double dy;

    public Background(String s, double ms) {
        sprite = new Sprite(s);
        dx = 0;
        dy = 0;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite.getImage(), (int) x, (int) y, null);
        if (x < 0) {
	    g2d.drawImage(sprite.getImage(), (int) x + ImageObserver.WIDTH, (int) y, null);
        }
        if (x > 0) {
	    g2d.drawImage(sprite.getImage(), (int) x - ImageObserver.WIDTH, (int) y, null);
        }
    }
}
