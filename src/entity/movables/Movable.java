package entity.movables;

import entity.Entity;
import entity.Tile;
import map.TileMap;

import java.awt.*;

/**
 * ALl objects which are able to move
 */
public abstract class Movable extends Entity {
    // Vectors
    private double dx;
    private double dy;

    private double xTemp;
    private double yTemp;

    // Movement flags
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;
    private boolean leftColl;
    private boolean rightColl;


    // More movement
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double gravity;

    /**
     * Creates an entity object
     * @param x The x-position
     * @param y The y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected Movable(int x, int y, final TileMap tm) {
        super(x, y, tm);


    }

    public void update() {
        super.update();
        getNextPosition();
        checkTileMapCollision();
        setPosition((int) xTemp, (int) yTemp);

        if (right) {
            facingRight = true;
        } else if (left) {
            facingRight = false;
        }
    }

    // Sets the movement vectors based on the players current movement
    private void getNextPosition() {
        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }

        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        // jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling logic
        if (falling) {
            dy += fallSpeed;
            if (dy > 0) {
                jumping = false;
            }
            if (dy < 0 && !jumping) {
                dy += gravity;
            }
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
    }

    private void checkTileMapCollision() {

        // Makes sure not all the tiles are getting checked, only those around the object
        int xCordPos = x / tm.getTileWidth();
        int yCordPos = y / tm.getTileHeight();

        // Collision
        double xDest = x + dx;
        double yDest = y + dy;
        xTemp = x;
        yTemp = y;

        falling = true; // The object will fall unless there is a collision
        leftColl = false;
        rightColl = false;

        System.out.println(tm.getTiles().size());


        // Collision rectangle for the object
        Rectangle cRect = new Rectangle(x + tm.getX(), (int) yDest + tm.getY() + 1, width, height);

        for(Tile tile : tm.getTiles()) {

            if (cRect.intersects(tile.getRectangle()) && tile.isSolid() && solid) {
                // top collision
                if ((int) yDest - dy + height <= tile.getY()) {
                    yTemp = tile.getY() - height;
                    dy = 0;
                    falling = false;
                }
                // bottom collisions
                else if (yDest - dy >= tile.getY() + (int) tile.getRectangle().getHeight()) {
                    yTemp = tile.getY() + (int) tile.getRectangle().getHeight();
                    dy = fallSpeed;
                    falling = true;
                }
            }
        }

        cRect = new Rectangle((int) xDest + tm.getX(), y + tm.getY(), width, height);
        for(Tile tile : tm.getTiles()){
            if (cRect.intersects(tile.getRectangle()) && tile.isSolid() && solid) {
                // collision to the right
                if (x + width <= tile.getX()) {
                    xTemp = tile.getX() - width;
                    dx = 0;
                    rightColl = true;
                }
                // collision to the left
                else if (x >= tile.getX() + (int) tile.getRectangle().getWidth()) {
                    xTemp = tile.getX() + (int) tile.getRectangle().getWidth();
                    dx = 0;
                    leftColl = true;
                }
            }
        }

        // Move the object based on the collision
        yTemp += dy;
        xTemp += dx;
    }

    /**
     * Checks if the entity is on the ground
     *
     * @return Boolean telling if the entity is in ground
     */
    public boolean isOnGround() {
        return (!falling && !jumping && dy == 0);
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean b) {
        jumping = b;
    }

    public boolean isFalling() {
        return falling;
    }

    public double getDy() {
        return dy;
    }

    public double getDx() {
        return dx;
    }

    public boolean hasLeftColl() {
        return leftColl;
    }

    public boolean hasRightColl() {
        return rightColl;
    }
}
