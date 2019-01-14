package entity.tile_types;
import entity.Tile;
import entity.movables.Movable;
import map.TileMap;


/**
 *
 */
public class NormalTile extends Tile
{
    public NormalTile(String spritePath, int x, int y, TileMap tm) {
        super(true, false, spritePath, x, y, tm);
    }


    @Override
    public void movableCollision(Movable o) {

    }
}

