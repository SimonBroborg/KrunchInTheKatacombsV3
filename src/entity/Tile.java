package entity;

import entity.movables.Movable;
import map.TileMap;

import java.awt.*;

/**
 * The tiles which makes up the map
 */
public abstract class Tile extends Entity {
    protected boolean solid; // If it can be collided with
    protected boolean transparent; // If it is visible

    protected Tile(boolean solid, boolean transparent, String spritePath, int x, int y, TileMap tm) {
        super(x, y, spritePath, tm);
        this.solid = solid;
        this.transparent = transparent;
    }

    public Rectangle getRectangle() {
        return new Rectangle(getXMap(), getYMap(), width, height);
    }

    /**
     * If anythings special should happen when it collides with a movable object
     *
     * @param o The movable object
     */
    public abstract void movableCollision(Movable o);

    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite.getImage(), getXMap(), getYMap(), width, height, null);
    }

    public boolean isSolid() {
        return solid;
    }
}
