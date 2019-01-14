package entity.movables.enemies;

import entity.movables.Enemy;
import entity.movables.Player;
import map.TileMap;

/**
 * Enemy type which hunts for the player of close enough. Changes direction on wall collision.
 */
public class ZombEnemy extends Enemy {

    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public ZombEnemy(int x, int y, Player player, TileMap tm) {
	super(x, y, player, "resources/Sprites/enemies/zombEnemy.png", tm);
	jumpStart = -10;
    }

    @Override public void onPlayerColl() {
	player.damage(1);
    }

    @Override public void update() {
	super.update();
	boolean alerted = false;

	// The hunter will find the player uses the flashlight on it
	if (player.getFl().getLightBulb().intersects(this.getRectangle())) {
	    alerted = true;
	}
	if(inRange(player, 200) || alerted) {
	    alerted = true;
	    // Hunt the player horizontally
	    // jump over obstacles
	    if (this.x < player.getX()) {
		moveRight();
	    } else if (x > player.getX()) {
		moveLeft();
	    }
	    if (player.getFl().getLightBulb().intersects(this.getRectangle())) {
		maxSpeed = 1;
	    }else {
		maxSpeed = 2;
	    }
	} else{
	    maxSpeed = 1;
	    if (hasLeftColl() || hasRightColl()) {
		turn();
	    }
	}
    }
}
