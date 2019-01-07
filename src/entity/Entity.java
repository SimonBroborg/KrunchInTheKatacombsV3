package entity;

import animation.Fade;
import main.GameComponent;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * All things on the screen which has a position
 */
public abstract class Entity
{
    // Position
    protected int x;
    protected int y;

    // Dimensions
    protected int width;
    protected int height;

    // Sprite and animation
    protected Sprite sprite;
    protected Fade fade;
    protected float fadeAlpha;

    // Flags
    protected boolean remove;
    protected boolean solid; // If it can be collided with
    protected boolean highlight; // If it should get highlighted
    protected boolean transparent; // If it is visible
    protected boolean facingRight;

    //Tile stuff
    protected TileMap tm;
    protected int xMap;
    protected int yMap;

    /**
     * Creates an entity object
     * @param x the x-position
     * @param y the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected Entity(int x, int y, TileMap tm) {
        this.tm = tm;
        this.x = x;
        this.y = y;

        // Dimensions
        width = 0;
        height = 0;

        // Sprite and animation
        sprite = null;
        fade = null;

        // Flags
        remove = false;
        highlight = false;
        transparent = false;
        facingRight = true;
        solid = true;

        // For the fade effect
        fadeAlpha = 1.0f;

        xMap = 0;
        yMap = 0;
    }

    protected void newFade(int ms, float from, float to){
        fade = new Fade(ms, from, to);
    }

    /**
     * Draws the entity to the frame
     *
     * @param g2d the drawing object
     */
    public void draw(Graphics2D g2d) {
        if(fade != null && fade.isRunning()){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade.getAlpha()));
        }
        if (facingRight) {
            g2d.drawImage(sprite.getImage(), x + xMap, y + yMap, width, height, null);
        } else {
            g2d.drawImage(sprite.getImage(), x + xMap + width, y + yMap, -width, height, null);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    /**
     * Returns the collision rectangle of the entity
     *
     * @return a rectangle with the size and position of the entity
     * @see javafx.scene.shape.Rectangle
     */
    public Rectangle getRectangle() {
        return new Rectangle(x + xMap, y + yMap, width, height);
    }

    /**
     * A static object only has to change it's map position when updates
     */
    public void update() {
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
    private void setMapPosition() {
        xMap = tm.getX();
        yMap = tm.getY();
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

    public boolean isSolid() {
        return solid;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public Point getCenter(){
        return new Point(x + width / 2, y + height / 2);
    }

    // Check if another entity in a chosen range
    public boolean inRange(Entity other, int range){
        return Math.hypot(other.getCenter().getX() - this.getCenter().getX(), other.getCenter().getY() - this.getCenter().getY()) <= range;
    }

}


