package entity;

import main.GameComponent;
import map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class LightSource extends Entity {
    private Rectangle light;
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
    public LightSource(int x, int y, TileMap tm) {
        super(x, y, tm);

        range = 400;

        colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        fractions = new float[]{0.0f, 1.0f};
    }

    @Override
    public void update() {
        super.update();


        // Set the rectangle in the center
        light  = new Rectangle(x - range /2 + tm.getX(), y - range / 2 + tm.getY(), range, range);

        // The center of the rectangle
        Point2D center = new Point(x + tm.getX(), y + tm.getY());

        // The paint in the center of the rectangle
        p  = new RadialGradientPaint(center, range / 2 , fractions, colors);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(p);
        g2d.fillOval(light.x, light.y, light.width, light.height);
    }

    public Area getLight() {
        return new Area(light);
    }
}
