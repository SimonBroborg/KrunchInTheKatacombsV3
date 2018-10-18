package menu;

import main.Sprite;

import java.awt.*;

/**
 * A button in the meny which can be clicked by
 */
public class MenuButton
{
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean hovered;

    private Sprite sprite;

    /**
     * Creates a new button
     * @param x x position
     * @param y y position
     * @param spritePath path to the sprite for the button
     */
    public MenuButton(int x, int y, String spritePath){
        sprite = new Sprite(spritePath);
        this.x = x;
        this.y = y;
    	this.width = sprite.getWidth();
    	this.height = sprite.getHeight();

    }

    /**
     * Draw the button to the screen
     * @param g2d The grapgics object
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite.getImage(), x, y, width, height, null);
        g2d.drawImage(sprite.getImage(), x, y, width, height, null);
    }

    public void update(){

    }

    /**
     * Check if the mouse is hovering the button
     * @param p
     */
    public void checkHover(Point p){
        hovered = new Rectangle(x, y, width, height).intersects(p.x, p.y, 1, 1);
    }


    // Getters

    public boolean isHovered() {
	return hovered;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getY() {
	return y;
    }

    public int getX() {
	return x;
    }

}

