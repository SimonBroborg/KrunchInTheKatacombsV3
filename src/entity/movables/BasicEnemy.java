package entity.movables;

import map.TileMap;

public class BasicEnemy extends Enemy{
    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public BasicEnemy(int x, int y, Player player,  TileMap tm) {
        super(x, y, player, tm);
    }

    @Override
    public void onPlayerColl() {
       
    }

}
