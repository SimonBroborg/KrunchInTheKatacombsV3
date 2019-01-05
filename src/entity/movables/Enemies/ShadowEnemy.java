package entity.movables.Enemies;

import entity.movables.Enemy;
import entity.movables.Player;
import main.Sprite;
import map.TileMap;

public class ShadowEnemy extends Enemy {
    /**
     * Creates an entity object
     *
     * @param x      the x-position
     * @param y      the y-position
     * @param player
     * @param tm     the levels tiles, used to check collisions etc
     */
    public ShadowEnemy(int x, int y, Player player, TileMap tm) {
        super(x, y, player, tm);
        sprite = new Sprite("resources/Sprites/Enemies/shadow.png");
        jumpStart = -10;

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

        setJumping(hasLeftColl() || hasRightColl());
    }

    @Override
    public void onPlayerColl() {

    }
}
