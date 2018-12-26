package entity.movables;

import main.Sprite;
import map.TileMap;

public class BasicEnemy extends AMovable {
    private Player player;



    /**
     * Creates an entity object
     *
     * @param x
     * @param y
     * @param tm the levels tiles, used to check collisions etc
     */
    public BasicEnemy(int x, int y, Player player,  TileMap tm) {
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
        jumpStart = -10;
        stopJumpSpeed = 0.3;

        sprite = new Sprite("resources/Sprites/Objects/Pickups/test.png");
    }

    @Override
    public void update() {
        super.update();

        // Hunt the player
        if(this.x < player.getX()){
            setRight(true);
            setLeft(false);

            if(hasRightColl()){
                setJumping(true);
            }
        }
        else if(x > player.getX()){
            setRight(false);
            setLeft(true);

            if(hasLeftColl()){
                setJumping(true);
            }
        }

        if(player.getY() < y && x > player.getX() - 100 && x < player.getX() + 100){
            setJumping(true);
        }
    }
}
