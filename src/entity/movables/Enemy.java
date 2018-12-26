package entity.movables;

import main.Sprite;
import map.TileMap;

public abstract class Enemy extends AMovable {
    protected Player player;

    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Enemy(int x, int y, Player player,  TileMap tm) {
        super(x, y, tm);

        this.player = player;

        setRight(true);

        // dimensions
        width = 40;
        height = 40;

        // movement
        moveSpeed = 0.7;
        maxSpeed = 2;
        stopSpeed = 0.4;
        fallSpeed = 0.5;
        maxFallSpeed = 10;
        jumpStart = -7;
        gravity = 0.3;

        sprite = new Sprite("resources/Sprites/Objects/Pickups/test.png");
    }

    /**
     * @brief Besides doing normal movement, the enemy moves back and worth when colliding
     */
    @Override
    public void update() {
        super.update();

        // Hunt the player horizontally
        // jump over obstacles
        if(this.x < player.getX()){
            setRight(true);
            setLeft(false);

            if(hasRightColl()){
                setJumping(true);
            }
        }
        else if(x > player.getX()) {
            setRight(false);
            setLeft(true);

            if (hasLeftColl()) {
                setJumping(true);
            }
        }

        // check if the player can be killed
        if(hasPlayerCollision()){
            onPlayerColl();
        }
    }

    private boolean hasPlayerCollision(){
        return player.getRectangle().intersects(this.getRectangle());
    }

    public abstract void onPlayerColl();

    public void kill(){
        remove = true;
    }
}
