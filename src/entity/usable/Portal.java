package entity.usable;

import map.TileMap;

/**
 * Tile type. Moves the player between levels.
 */
public class Portal extends Usable {

    private boolean used;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Portal(int x, int y, TileMap tm) {
	super(x, y, "resources/Sprites/Tiles/eventPortalNext.png", tm);
    }

    @Override public void use() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }
}
