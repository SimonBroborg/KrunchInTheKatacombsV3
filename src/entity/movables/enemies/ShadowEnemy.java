package entity.movables.enemies;

import entity.movables.Enemy;
import entity.movables.Player;
import map.TileMap;

/**
 * Enemy which is hidden in the shadows.
 */
public class ShadowEnemy extends Enemy {
    /**
     * Creates an entity object
     *
     * @param x      the x-position
     * @param y      the y-position
     * @param player the player
     * @param tm     the levels tiles, used to check collisions etc
     */
    public ShadowEnemy(int x, int y, Player player, TileMap tm) {
	super(x, y, player, "resources/Sprites/enemies/shadow.png", tm);
	jumpStart = -10;

    }

    @Override public void update() {
	super.update();
	if(hasLeftColl()){
	    moveRight();
	} else if(hasRightColl()){
	    moveLeft();
	}

	setJumping(hasLeftColl() || hasRightColl());
    }

    @Override public void onPlayerColl() {

    }
}
