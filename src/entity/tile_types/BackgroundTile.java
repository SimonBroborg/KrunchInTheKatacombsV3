package entity.tile_types;

import entity.Tile;
import entity.movables.Movable;
import map.TileMap;

/**
 * TIle type. Movables can't collide with it and it has no special interactions.
 */
public class BackgroundTile extends Tile {
    public BackgroundTile(String spritePath, int x, int y, TileMap tm) {
	super(false, false, spritePath, x, y, tm);
    }

    @Override public void movableCollision(Movable o) {
    }
}
