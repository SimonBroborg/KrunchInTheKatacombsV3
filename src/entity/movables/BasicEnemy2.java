package entity.movables;

import main.Sprite;
import map.TileMap;

public class BasicEnemy2 extends Enemy {


    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public BasicEnemy2(int x, int y, Player player, TileMap tm) {
        super(x, y, player, tm);
        sprite = new Sprite("resources/Sprites/Enemies/be2.png");
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    @Override
    public void onPlayerColl() {
        player.damage(50);
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

        setJumping(hasLeftColl() || hasRightColl()); 
    }
}
