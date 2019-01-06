package menu;

import game_state.GameState;
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

    private String text;
    private Color textColor;

    private Sprite sprite;

    private boolean hovered;

    private GameState state;

    /**
     * Creates a new button
     */
    public MenuButton(int width, int height, String text, GameState state) {
        sprite = null;

        this.text = text;

        this.width = width;
        this.height = height;

        textColor = Color.WHITE;

        this.state = state;

        hovered = false;
        //this.width = sprite.getNumCols();
        //this.height = sprite.getNumRows();
    }

    /**
     * Draws the button text with a centered position
     *
     * @param g2d The graphics object
     */
    private void drawCenteredString(Graphics2D g2d) {
        g2d.setColor(textColor);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 40));

        FontMetrics fm = g2d.getFontMetrics();
        int x = this.x + (width - fm.stringWidth(text)) / 2;

        int y = this.y + (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);

        g2d.drawString(text, x, y);
    }

    /**
     * Draw the button to the screen
     *
     * @param g2d The grapgics object
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255));
        //g2d.fillRect(x, y, width, height);
        drawCenteredString(g2d);
    }

    /**
     * Updates the button, checking for mouse hover
     *
     * @param mousePos The position of the mouse
     */
    public void update(Point mousePos) {
        if (mousePos != null && checkHover(mousePos)) {
            textColor = new Color(0, 100, 255);
        } else {
            textColor = Color.BLACK;
        }
    }

    /**
     * Check if the mouse is hovering the button
     *
     * @param p The position of the mouse
     */
    private boolean checkHover(Point p) {
        hovered = new Rectangle(x, y, width, height).intersects(p.x, p.y, 1, 1);
        return hovered;
    }

    public boolean isHovered() {
        return hovered;
    }

    public GameState getState() {
        return state;
    }

    // Getters
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

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

