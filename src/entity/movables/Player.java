package entity.movables;

import entity.Animation;
import entity.Damageable;
import flashlight.FlashLight;
import map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



/**
 *
 */
public class Player extends Damageable
{
	private FlashLight fl;
	private Animation animation;

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			2, 8, 1, 2, 4, 2, 5
	};

	private int currentAction;

	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	public Player(int x, int y, TileMap tm) {
		super(x, y, "resources/Sprites/Player/playersprites.gif", tm);
		animation = new Animation();
		// dimensions
		width = 30;
		height = 30;

		// movement
		moveSpeed = 0.7;
		maxSpeed = 6;
		stopSpeed = 0.4;
		airStopSpeed = 0.2;
		fallSpeed = 0.5;
		maxFallSpeed = 10;
		jumpStart = -10;
		gravity = 0.3;
		climbSpeed = 7;

		wallJumpStart = -7;

		// load sprites
		try {

			BufferedImage spritesheet = sprite.getImage();

			sprites = new ArrayList<>();
			for(int i = 0; i < 7; i++) {

				BufferedImage[] bi =
						new BufferedImage[numFrames[i]];

				for(int j = 0; j < numFrames[i]; j++) {

					if(i != 6) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width,
								height
						);
					}
				}
				sprites.add(bi);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		width = 60;
		height = 60;

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
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
		super.update();
		fl.update(mousePos);

		if(this.getDy() > 0) {
			if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
			}
		}
		else if(this.getDy() < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
			}
		}
		else if(left || right){
			if(currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
			}
		}
		else{
			if(currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
			}
		}

		animation.update();
	}

	@Override
	public void draw(Graphics2D g2d) {
		//fl.draw(g2d);

		if (facingRight) {
			g2d.drawImage(animation.getImage(), x + xMap, y + yMap, width, height, null);
		} else {
			g2d.drawImage(animation.getImage(), x + xMap + width, y + yMap, -width, height, null);
		}

	}

	public void toggleFlashlight(){
		fl.toggle();
	}

	public FlashLight getFlashLight() {
		return fl;
	}

	@Override
	public void kill() {
		super.kill();
		fl.setBatteryPower(100);
		fl.turnOff();
	}
}

