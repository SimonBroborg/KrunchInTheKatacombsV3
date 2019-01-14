package entity.usable;

import entity.Entity;
import map.TileMap;

/**
 * The message which will be displayed on the information sign.
 */
public class InfoSignMessage extends Entity {

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected InfoSignMessage(String text, int x, int y, TileMap tm) {
	super(x, y, null, tm);

    }
}
