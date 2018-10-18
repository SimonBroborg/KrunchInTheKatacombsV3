package entity.tile_types;
import entity.Tile;
import map.TileMap;


/**
 *
 */
public class NormalTile extends Tile
{
    public NormalTile(String spritePath, int x, int y, TileMap tm) {
        super(true, false, spritePath, x, y, tm);
    }
}

