package entity;

import map.TileMap;

import java.awt.*;

/**
 * ALl objects which are able to move
 */
public abstract class MovingObject extends AEntity {
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

    // More movement
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    private boolean counted;

    /**
     * Creates an entity object
     *
     * @param tm the levels tiles, used to check collisions etc
     */
    protected MovingObject(int x, int y, final TileMap tm) {
        super(x, y, tm);
        counted = false;
    }
    public void update() {
        setMapPosition();
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
        // falling
        if (falling) {
            dy += fallSpeed;
            if (dy > 0) {
                jumping = false;
            }
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
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
        double xdest = x + dx;
        double ydest = y + dy;
        xTemp = x;
        yTemp = y;

        falling = true; // The object will fall unless there is a collision


        // Collision rectangle for the object
        Rectangle cRect = new Rectangle(x + tm.getX(), (int) ydest + tm.getY() + 1, width, height);

        for (int i = yCordPos - 5; i < yCordPos + 5; i++) {
            for (int j = xCordPos - 5; j < xCordPos + 5; j++) {
                if (i >= 0 && j >= 0 && i < tm.getHeight() && j < tm.getWidth()) {
                    Tile tile = tm.getTiles()[i][j];
                    if (cRect.intersects(tile.getRectangle()) && tile.isSolid() && solid) {
                        if ((int) ydest - dy + height <= tile.getY()) {
                            yTemp = tile.getY() - height;
                            dy = 0;
                            falling = false;
                        } else if (ydest - dy >= tile.getY() + (int) tile.getRectangle().getHeight()) {
                            yTemp = tile.getY() + (int) tile.getRectangle().getHeight();
                            dy = fallSpeed;
                            falling = true;
                        }
                    }
                }
            }
        }

        cRect = new Rectangle((int) xdest + tm.getX(), y + tm.getY(), width, height);
        for (int i = yCordPos - 5; i < yCordPos + 5; i++) {
            for (int j = xCordPos - 5; j < xCordPos + 5; j++) {
                if (i >= 0 && j >= 0 && i < tm.getHeight() && j < tm.getWidth()) {

                    Tile tile = tm.getTiles()[i][j];
                    if (cRect.intersects(tile.getRectangle()) && tile.isSolid() && solid) {
                        if (x + width <= tile.getX()) {
                            xTemp = tile.getX() - width;
                            dx = 0;
                        }
                        // Moving to the left
                        else if (x >= tile.getX() + (int) tile.getRectangle().getWidth()) {
                            xTemp = tile.getX() + (int) tile.getRectangle().getWidth();
                            dx = 0;
                        }
                    }
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
}
