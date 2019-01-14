package game_state;

import main.GameStateManager;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 *
 */
public interface GameState
{
    /**
     * Initialize the state
     *
     * @param gsm the game state manager which controls the current state
     */
    void init(GameStateManager gsm);

    /**
     * Updates everything in the state ( positions etc. )
     *
     * @param mousePos the position of the mouse
     */
    void update(Point mousePos);

    /**
     * Draw's everything to the screen
     *
     * @param g2d the drawing object
     * @see Graphics2D
     */
    void draw(Graphics2D g2d);

    /**
     * Things that happen when the player presses a keyboard button
     *
     * @param k the number of the key pressed
     */
    void keyPressed(int k);

    /**
     * Things that heppen when the player releases a keyboard button
     *
     * @param k the number of the key pressed
     */
    void keyReleased(int k);

    /**
     * Things that happen when the player clicks a mouse button
     *
     * @param e information about the event
     */
    void mouseClicked(MouseEvent e);


    /**
     * Things that happen when the player moves the mouse
     *
     * @param e information about the event
     */
    void mouseMoved(MouseEvent e);


}
