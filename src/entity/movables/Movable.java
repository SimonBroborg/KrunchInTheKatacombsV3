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

    private double xDest;
    private double yDest;

    // Movement flags
    protected boolean left;
    protected boolean right;
    private boolean jumping;
    private boolean falling;
    private boolean leftColl;
    private boolean rightColl;

    protected boolean onLadder;
    protected boolean climbingUp;
    protected boolean climbDown;

    // More movement
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double airStopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double gravity;
    protected double climbSpeed;
    protected double wallJumpStart;

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

        if (right) {
            facingRight = true;
        } else if (left) {
            facingRight = false;
        }
    }

    // Sets the movement vectors based on the players current movement
    private void getNextPosition() {
        if(onLadder){
            dy = 0;
            if(climbingUp){
                dy = -climbSpeed;
            }
            else if(climbDown){
                dy = climbSpeed;
            }
        }

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
                if(falling) dx -= airStopSpeed;
                else dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                if(falling) dx += airStopSpeed;
                else dx += stopSpeed;
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

        // Where the movable will end up after its movement
        xDest = x + dx;
        yDest = y + dy;

        // Flags
        falling = true;
        onLadder = false;
        leftColl = false;
        rightColl = false;

        // Collision rectangle for the object when moving in the y-axis
        Rectangle verCRect = new Rectangle(x + tm.getX(), (int) yDest + tm.getY() + 1, width, height);
        Rectangle horCRect = new Rectangle((int) xDest + tm.getX(), y + tm.getY(), width, height);

        // Collision check for the y-axis
        for (int i = yCordPos - 3; i < yCordPos + 3; i++) {
            for (int j = xCordPos - 3; j < xCordPos + 3; j++) {
                if (i >= 0 && j >= 0 && i < tm.getNumRows() && j < tm.getNumCols()) {
                    Tile tile = tm.getTiles()[i][j];
                    if(tile != null) {
                        if (verCRect.intersects(tile.getRectangle())) {
                            // top collision
                            if(tile.isSolid() && solid) {
                                if (y - dy + height <= tile.getY()) {
                                    y = tile.getY() - height;
                                    dy = 0;
                                    falling = false;
                                }
                                // bottom collisions
                                else {
                                    y = tile.getY() + (int) tile.getRectangle().getHeight();
                                    dy = fallSpeed;
                                    falling = true;
                                }
                            }
                            tile.movableCollision(this);
                        }

                        else if (horCRect.intersects(tile.getRectangle())) {

                            if(tile.isSolid() && solid) {
                                // collision to the right
                                if (x + width <= tile.getX()) {
                                    x = tile.getX() - width;
                                    rightColl = true;
                                }
                                // collision to the left
                                else {
                                    x = tile.getX() + (int) tile.getRectangle().getWidth();
                                    leftColl = true;
                                }
                                dx = 0;
                            }

                            tile.movableCollision(this);
                        }
                    }

                }
            }
        }

        // Move the object based on the collision
        y += dy;
        x += dx;
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


    public void setClimbingUp(boolean climbing) {
        this.climbingUp = climbing;
    }

    public void setClimbDown(boolean climbDown) {
        this.climbDown = climbDown;
    }

    public void onLadder(boolean b){
        onLadder = b;
    }

}
