package entity;

import entity.movables.Movable;
import map.TileMap;

public abstract class Damageable extends Movable {
    private int hp;
    private boolean dead;

    /**
     * Creates an entity object
     *
     * @param x  The x-position
     * @param y  The y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected Damageable(int x, int y, TileMap tm) {
        super(x, y, tm);
        dead = false;
        hp = 100;
    }

    @Override
    public void update() {
        super.update();

        if(hp <= 0){
            kill();
        }
    }
    /**
     * Damage the entity
     * @param dmg The damage which the enemy takes
     */
    public void damage(int dmg){
        setHp(hp - dmg);
    }


    public void kill(){
        dead = true;
        hp = 0;
    }

    public void respawn(int x, int y){
        dead = false;
        hp = 100;
        setPosition(x, y);
    }

    public boolean isDead() {
        return dead;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

}
