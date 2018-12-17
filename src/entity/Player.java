package entity;

import main.Sprite;
import map.TileMap;

/**
 *
 */
public class Player extends Moveable
{
    public Player(int x, int y, TileMap tm) {
	super(x, y, tm);

	// dimensions
	width = 40;
	height = 40;

	// movement
	moveSpeed = 0.7;
	maxSpeed = 3;
	stopSpeed = 0.4;
	fallSpeed = 0.5;
	maxFallSpeed = 10;
	jumpStart = -10;
	stopJumpSpeed = 0.3;

	sprite = new Sprite("resources/Sprites/Objects/Pickups/test.png");
    }

    /**
         * Checks if an object is positioned within a specific range to the player.
         *
         * @param ox    the objects x-position
         * @param oy    the objects y-position
         * @param range the range (in pixels) which the objects has to be
         */
        public boolean inRange(int ox, int oy, int range) {
            return Math.hypot(ox - x + (float) width / 2, oy - y + (float) height / 2) < range;
        }
}
