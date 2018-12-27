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
        kill();
    }

    @Override
    public void update() {
        super.update();

        // Hunt the player horizontally
        // jump over obstacles
        if(this.x < player.getX()){
            setRight(true);
            setLeft(false);

            if(hasRightColl()){
                setJumping(true);
            }
        }
        else if(x > player.getX()) {
            setRight(false);
            setLeft(true);

            if (hasLeftColl()) {
                setJumping(true);
            }
        }

    }
}
