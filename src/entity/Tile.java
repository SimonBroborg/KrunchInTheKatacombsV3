package entity;

import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * The tiles which makes up the map
 */
public abstract class Tile extends AEntity {
    protected Tile(boolean solid, boolean transparent, String spritePath, int x, int y, TileMap tm) {
        super(x, y, tm);
        this.solid = solid;
        this.transparent = transparent;
        this.sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
        this.width = tm.getTileWidth();
        this.height = tm.getTileHeight();
        this.tm = tm;
        this.highlight = false;
    }

    public Rectangle getRectangle() {
        return new Rectangle(getXMap(), getYMap(), width, height);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite.getImage(), getXMap(), getYMap(), width, height, null);
        g2d.setColor(Color.RED);

    }
}
