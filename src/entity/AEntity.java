package entity;

import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * All things on the screen which hasa position
 */
public abstract class AEntity
{
    // Position
    protected int x;
    protected int y;

    // Dimensions
    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;

    // Sprite
    protected Sprite sprite = null;

    // Flags
    protected boolean remove;
    protected boolean solid; // If it can be collided with
    protected boolean highlight; // If it should get highlighted
    protected boolean transparent; // If it is visible
    protected boolean facingRight;

    //Tile stuff
    protected TileMap tm;
    protected int xmap;
    protected int ymap;

    /**
     * Creates an entity object
     *
     * @param tm the levels tiles, used to check collisions etc
     */
    protected AEntity(TileMap tm) {
	this.tm = tm;
	solid = true;
	facingRight = true;
    }

    /**
     * Draws the entity to the frame
     *
     * @param g2d the drawing object
     */
    public void draw(Graphics2D g2d) {
	if (facingRight) {
	    g2d.drawImage(sprite.getImage(), x + xmap, y + ymap, width, height, null);
	} else {
	    g2d.drawImage(sprite.getImage(), x + xmap + width, y + ymap, -width, height, null);
	}
    }

    /**
     * Returns the collision rectangle of the entity
     *
     * @return a rectangle with the size and position of the entity
     * @see javafx.scene.shape.Rectangle
     */
    public Rectangle getRectangle() {
	return new Rectangle(x + xmap, y + ymap, width, height);
    }

    public void update(){
        setMapPosition();
    }
    /**
     * Set's the x-and y-positions
     *
     * @param x the x-position
     * @param y the y-position
     */
    public void setPosition(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Get's the maps position. Used to place the entity based on the "camera".
     */
    public void setMapPosition() {
	xmap = tm.getX();
	ymap = tm.getY();
    }

    public Sprite getSprite() {
	return sprite;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }


    /**
     * Tells if the object should be removed from the map.
     *
     * @return true of false based on if it should be removed.
     */
    public boolean shouldRemove() {
	return remove;
    }


    /**
     * Get the x-position.
     *
     * @return the x-position as an integer
     */
    public int getXMap() {
	return x + tm.getX();
    }

    /**
     * Get the y-position
     *
     * @return the y-position as an integer
     */
    public int getYMap() {
	return y + tm.getY();
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public boolean isSolid(){
        return solid;
    }

    public boolean isTransparent() {
	return transparent;
    }

    public boolean isHighlight() {
	return highlight;
    }
}
