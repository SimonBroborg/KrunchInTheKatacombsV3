package entity;

import map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * A transparent ellipse which is representing a light source
 */
public class LightSource extends Entity {
    private Ellipse2D light;

    private float diameter;

    private RadialGradientPaint p;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public LightSource(float diameter, int x, int y, TileMap tm) {
        super(x, y, "resources/Sprites/Misc/lightsource.png", tm);

        this.diameter = diameter;

        this.x = (int) (x - diameter / 2);
        this.y = (int) (y - diameter / 2);

        light = new Ellipse2D.Float(x + tm.getX(), y + tm.getY(), diameter - 1, diameter - 1);
    }

    @Override
    public void update() {
        super.update();

        // The circle which will be subtracted from the lightmap
        light = new Ellipse2D.Float(x + tm.getX(), y + tm.getY(), diameter - 1, diameter - 1);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite.getImage(), (int) light.getX(), (int) light.getY(), (int) diameter, (int) diameter, null);
    }

    Area getLight() {
        return new Area(light);
    }
}
