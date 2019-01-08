package entity;

import map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class LightSource extends Entity {
    private Ellipse2D light;
    private int range;

    private float[] fractions;
    private Color[] colors;

    private RadialGradientPaint p;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public LightSource(int range, int x, int y, TileMap tm) {
        super(x, y, "resources/Sprites/Misc/lightsource.png", tm);

        this.range = range;

        colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        fractions = new float[]{0.0f, 1.0f};
        light  = new Ellipse2D.Float(x - range /2 + tm.getX(), y - range / 2 + tm.getY(), range -2, range -2);

    }

    @Override
    public void update() {
        super.update();

        // Set the rectangle in the center
        light  = new Ellipse2D.Float(x - range /2 + tm.getX(), y - range / 2 + tm.getY(), range -2, range -2);

        // The center of the rectangle
        Point2D center = new Point(x + tm.getX(), y + tm.getY());

        // The paint in the center of the rectangle
        p  = new RadialGradientPaint(center, range / 2 , fractions, colors);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(p);
        g2d.drawImage(sprite.getImage(), (int) light.getX(), (int)light.getY(), null);

    }

    public Area getLight() {
        return new Area(light);
    }
}
