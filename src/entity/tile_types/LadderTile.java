package entity.tile_types;

import entity.Tile;
import map.TileMap;

public class LadderTile extends Tile {
    protected LadderTile(String spritePath, int x, int y, TileMap tm) {
        super(false, false, spritePath, x, y, tm);
    }


}
