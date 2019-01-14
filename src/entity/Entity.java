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
    protected Sprite sprite = null;
    protected Fade fade = null;

    // Flags
    protected boolean remove;
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
    protected Entity(int x, int y, String spritePath, TileMap tm) {
        this.tm = tm;
        this.x = x;
        this.y = y;

        if (spritePath != null) {
            this.sprite = new Sprite(spritePath);
            // Dimensions
            width = sprite.getWidth();
            height = sprite.getHeight();
        }


        // Flags
        remove = false;
        facingRight = true;


        // For the fade effect
        final float fadeAlpha = 1.0f;

        xMap = 0;
        yMap = 0;
    }

    protected void newFade(int ms, float from, float to){
        fade = new Fade(ms, from, to);
    }

    /**
     * Draws the entity to the screen
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
     * Returns the collision rectangle of the entity relative to the tilemap
     *
     * @return a rectangle with the size and position of the entity
     * @see javafx.scene.shape.Rectangle
     */
    public Rectangle getRectangle() {
        return new Rectangle(x + xMap, y + yMap, width, height);
    }

    /**
     * Update the position etc. of an Entity
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

    /**
     * Tells if the object should be removed from the map.
     *
     * @return true of false based on if it should be removed.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Returns the center point of the entity
     *
     * @return the center point
     * @see Point
     */
    public Point getCenter(){
        return new Point(x + width / 2, y + height / 2);
    }

    /**
     * Check if another entity is inside a chosen range
     * @param other The other entity
     * @param range The chosen range
     * @return Boolean telling if it is inside the range
     */
    public boolean inRange(Entity other, int range){
        return Math.hypot(other.getCenter().getX() - this.getCenter().getX(), other.getCenter().getY() - this.getCenter().getY()) <= range;
    }

    public boolean collisionWith(Entity other) {
        return other.getRectangle().intersects(this.getRectangle());
    }

    /**
     * Check if the entity is inside the screen border
     * @return boolean telling if it is inside
     */
    public boolean isOnScreen() {
        return x + width + xMap >= 0 && y + height + yMap >= 0 && x + xMap <= GameComponent.WIDTH &&
               y + yMap <= GameComponent.HEIGHT;
    }

    /**
     * Get the x-position relative to the tilemap
     *
     * @return the x-position as an integer
     */
    public int getXMap() {
        return x + tm.getX();
    }

    /**
     * Get the y-position relative to the tilemap
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    protected void setWidth(int width){
        this.width = width;
    }
    protected void setHeight(int height){
        this.height = height;
    }
}


