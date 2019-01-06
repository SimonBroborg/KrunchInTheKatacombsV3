package entity;

import entity.movables.Movable;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * The tiles which makes up the map
 */
public abstract class Tile extends Entity {
    protected Tile(boolean solid, boolean transparent, String spritePath, int x, int y, TileMap tm) {
        super(x, y, tm);
        this.solid = solid;
        this.transparent = transparent;
        this.sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
        this.width = sprite.getWidth(); // tm.getTileWidth();
        this.height =  sprite.getHeight(); // tm.getTileHeight();
        this.tm = tm;
        this.highlight = false;
    }

    public Rectangle getRectangle() {
        return new Rectangle(getXMap(), getYMap(), width, height);
    }

    public abstract void movableCollision(Movable o);

    public void draw(Graphics2D g2d) {
        if(isOnScreen())
            g2d.drawImage(sprite.getImage(), getXMap(), getYMap(), width, height, null);
    }
}
