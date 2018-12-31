package entity.movables;

import main.Sprite;
import map.TileMap;

public class HunterEnemy extends Enemy{
    private boolean alerted;

    /**
     * Creates an entity object
     *
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public HunterEnemy(int x, int y, Player player, TileMap tm) {
        super(x, y, player, tm);
        jumpStart = -10;
    }

    @Override
    public void onPlayerColl() {
        player.damage(1);
    }

    @Override
    public void update() {
        super.update();
        alerted = false;

        // The hunter will find the player uses the flashlight on it
        if(player.getFlashLight().getLightBulb().intersects(this.getRectangle())){
            alerted = true;
        }
        if(inRange(player, 200) || alerted) {
            alerted = true;
            // Hunt the player horizontally
            // jump over obstacles
            if (this.x < player.getX()) {
                setRight(true);
                setLeft(false);
            } else if (x > player.getX()) {
                setRight(false);
                setLeft(true);
            }
            if(player.getFlashLight().getLightBulb().intersects(this.getRectangle())){
                maxSpeed = 1;
            }else {
                maxSpeed = 2;
            }
        }
        else{
            maxSpeed = 1;
            if(hasLeftColl()){
                setLeft(false);
                setRight(true);
            }
            else if(hasRightColl()){
                setLeft(true);
                setRight(false);
            }
        }

        if(alerted){
            sprite = new Sprite("resources/Sprites/Enemies/hunterEnemyAlerted.png");
            setJumping(hasLeftColl() || hasRightColl());
        } else{
            sprite = new Sprite("resources/Sprites/Enemies/hunterEnemy.png");
        }
    }

    @Override
    public void kill() {
        super.kill();
        remove = true;
    }
}
