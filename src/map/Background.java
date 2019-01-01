package map;

import main.GameComponent;
import main.Sprite;

import java.awt.*;

/**
 * Creates a background for a level.
 */
public class Background {
    private Sprite sprite;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background(String s, double ms) {
        sprite = new Sprite(s);
        moveScale = ms;
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
            g2d.drawImage(sprite.getImage(), (int) x + GameComponent.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g2d.drawImage(sprite.getImage(), (int) x - GameComponent.WIDTH, (int) y, null);
        }
    }
}
