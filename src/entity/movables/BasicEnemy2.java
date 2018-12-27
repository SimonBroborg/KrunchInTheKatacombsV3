package entity.movables;

import map.TileMap;

public class BasicEnemy2 extends Enemy{


    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public BasicEnemy2(int x, int y, Player player, TileMap tm) {
        super(x, y, player, tm);

    }

    @Override
    public void onPlayerColl() {
        player.kill();
    }

    @Override
    public void update() {
        super.update();

        if(hasLeftColl()){
            setLeft(false);
            setRight(true);
        }
        else if(hasRightColl()){
            setLeft(true);
            setRight(false);
        }
    }
}
