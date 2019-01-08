package entity.Usable;

import entity.Entity;
import map.TileMap;

public class InfoSignMessage extends Entity {
    private String text;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected InfoSignMessage(String text, int x, int y, TileMap tm) {
        super(x, y, null, tm);

        this.text = text;
    }
}
