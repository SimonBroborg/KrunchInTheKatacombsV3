package entity.movables;

import entity.Damageable;
import map.TileMap;

public abstract class Enemy extends Damageable {
    protected Player player;

    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Enemy(int x, int y, Player player, String spritePath, TileMap tm) {
        super(x, y, spritePath, tm);

        this.player = player;

        setRight(true);


        // movement
        moveSpeed = 0.7;
        maxSpeed = 2;
        stopSpeed = 0.4;
        fallSpeed = 0.5;
        maxFallSpeed = 10;
        jumpStart = -7;
        gravity = 0.3;
    }

    /**
     * @brief Besides doing normal movement, the enemy moves back and worth when colliding
     */
    @Override
    public void update() {
        super.update();
        // check if the player can be killed
        if(hasPlayerCollision()){
            onPlayerColl();
        }
    }

    private boolean hasPlayerCollision(){
        return player.getRectangle().intersects(this.getRectangle());
    }

    public abstract void onPlayerColl();

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void kill() {
        super.kill();
        remove = true;
    }
}
