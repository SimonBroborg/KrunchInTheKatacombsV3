package entity;

import entity.movables.Movable;
import map.TileMap;

import java.awt.*;

/**
 * Entities which has some way of getting damaged and has to have hp.
 */
public abstract class Damageable extends Movable {
    private int hp;
    private boolean dead;
    private static final int MAX_HP = 100;
    private Point spawnPoint;

    /**
     * Creates an entity object
     *
     * @param x  The x-position
     * @param y  The y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected Damageable(int x, int y, String spritePath, TileMap tm) {
	super(x, y, spritePath, tm);
	dead = false;
	hp = 100;

	spawnPoint = new Point(x, y - 70);
    }

    @Override public void update() {
	super.update();
	if(hp <= 0){
	    kill();
	}
    }

    /**
     * Damage the entity
     * @param dmg The damage which the enemy takes
     */
    public void damage(int dmg) {
	hp -= dmg;
    }

    public void kill(){
	dead = true;
	hp = 0;
    }

    public void respawn(){
	dead = false;
	hp = MAX_HP;
	setPosition((int) spawnPoint.getX(), (int) spawnPoint.getY());
    }

    public boolean isDead() {
	return dead;
    }

    public int getHp() {
	return hp;
    }

}
