package game_state;

import java.awt.*;
import java.awt.event.MouseEvent;
import main.*;

/**
 *
 */
public interface IGameState

{
    /**
     * Initialize the state
     *
     * @param gsm the game state manager which controls the current state
     */
    public void init(GameStateManager gsm);

    /**
     * Updates everything in the state ( positions etc. )
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos);

    /**
     * Draw's everything to the screen
     *
     * @param g2d the drawing object
     * @see Graphics2D
     */
    public void draw(Graphics2D g2d);

    /**
     * Things that happen when the player presses a keyboard button
     *
     * @param k the number of the key pressed
     */
    public void keyPressed(int k);

    /**
     * Things that heppen when the player releases a keyboard button
     *
     * @param k the number of the key pressed
     */
    public void keyReleased(int k);

    /**
     * Things that happen when the player clicks a mouse button
     *
     * @param e information about the event
     */
    public void mouseClicked(MouseEvent e);


    /**
     * Things that happen when the player moves the mouse
     *
     * @param e information about the event
     */
    public void mouseMoved(MouseEvent e);


}
