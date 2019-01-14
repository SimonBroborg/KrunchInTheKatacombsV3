package entity.tile_types;

import entity.Tile;
import entity.movables.Movable;
import map.TileMap;

/**
 * Tile type. Movables can move up and dwon freely.
 */
public class LadderTile extends Tile {
    public LadderTile(String spritePath, int x, int y, TileMap tm) {
        super(false, false, spritePath, x, y, tm);
    }

    @Override public void movableCollision(Movable o) {
        o.onLadder(true);
    }
}
