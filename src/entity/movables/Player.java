package entity.movables;

import flashlight.FlashLight;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 *
 */
public class Player extends AMovable
{
	private FlashLight fl;
	private boolean dead;

	private int number;

	public Player(int x, int y, TileMap tm) {
		super(x, y, tm);

		// dimensions
		width = 40;
		height = 40;

		number = 10;

		// movement
		moveSpeed = 0.7;
		maxSpeed = 3;
		stopSpeed = 0.4;
		fallSpeed = 0.5;
		maxFallSpeed = 10;
		jumpStart = -10;
		gravity = 0.3;

		dead = false;

		sprite = new Sprite("resources/Sprites/Player/player.png");

		fl = new FlashLight(tm, this);
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

	public void update(Point mousePos) {
		fl.update(mousePos);
		super.update();

	}

	@Override
	public void draw(Graphics2D g2d) {
		fl.draw(g2d);
		super.draw(g2d);

	}

	public void toggleFlashlight(){
		fl.toggle();
	}

	public void kill(){
		dead = true;
	}

	public void respawn(int x, int y){
		dead = false;
		setPosition(x, y);
	}

	public boolean isDead() {
		return dead;
	}

}

