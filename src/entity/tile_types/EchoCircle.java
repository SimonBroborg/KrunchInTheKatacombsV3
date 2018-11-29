package entity.tile_types;

import entity.AEntity;
import main.Sprite;
import map.TileMap;

import java.awt.*;

public class EchoCircle extends AEntity {
    private int radius;
    private int minRadius;
    private int maxRadius;
    private int growSpeed;

    /**
     * Creates an entity object
     *
     * @param tm the levels tiles, used to check collisions etc
     */
    public EchoCircle(int x, int y, TileMap tm) {
        super(x, y, tm);
        minRadius = 5;
        maxRadius = 50;
        radius = minRadius;
        growSpeed = 1;

        sprite = new Sprite("resources/Sprites/misc/echocircle.png");
    }

    @Override
    public void update() {

        setMapPosition();

        radius += growSpeed;
        x -= growSpeed / 2;
        y -= growSpeed / 2;

        if (radius > maxRadius) {
            remove = true;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite.getImage(), x + xmap - radius, y + ymap - radius, radius * 2, radius * 2, null);
    }
}
