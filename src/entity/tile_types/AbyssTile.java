package entity.tile_types;

import entity.Damageable;
import entity.Tile;
import entity.movables.Movable;
import map.TileMap;

/**
 * Tile type. Kills entity on collision
 */
public class AbyssTile extends Tile {
    public AbyssTile(String spritePath, int x, int y, TileMap tm) {
	super(false, false, spritePath, x, y, tm);
    }

    @SuppressWarnings("InstanceofConcreteClass") @Override public void movableCollision(Movable o) {
	if(o instanceof Damageable){
	    ((Damageable) o).kill();
	}
    }
}
