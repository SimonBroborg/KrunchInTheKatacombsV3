package entity.tile_types;

import entity.Tile;
import entity.movables.Movable;
import map.TileMap;

public class BackgroundTile extends Tile {
    public BackgroundTile(String spritePath, int x, int y, TileMap tm) {
        super(false, false, spritePath, x, y, tm);
    }

    @Override
    public void movableCollision(Movable o) {
    }
}
