package entity.tile_types;

import entity.Tile;
import map.TileMap;

/**
 *
 */
public class EmptyTile extends Tile
{
    public EmptyTile(String spritePath, int x, int y, TileMap tm) {
        super(false, true, spritePath, x, y, tm);
    }
}
