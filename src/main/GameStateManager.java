package main;

import game_state.IGameState;
import game_state.Level1State;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This manages and keeps track of the game states and switching between them
 */
public class GameStateManager
{
    private Level1State level1;
    private IGameState currentState;

    public GameStateManager() {
        currentState = new Level1State("resources/Maps/map1.tmx");
        // Sets the gsm for every state
        currentState.init(this);
    }

    /**
     * Update the current game state
     * @param mousePos The mous position on the game component
     */
    public void update(Point mousePos) {
        currentState.update(mousePos);
    }

    /**
     * Draw the current game state
     * @param g2d The graphics object
     */
    public void draw(Graphics2D g2d) {
        currentState.draw(g2d);
    }


    public void keyPressed(int k) {
        currentState.keyPressed(k);
    }

    public void keyReleased(int k) {
        currentState.keyReleased(k);
    }

    public void mouseClicked(MouseEvent e) {
        currentState.mouseClicked(e);
    }

    public void mouseMoved(MouseEvent e) {
        currentState.mouseMoved(e);
    }


    /**
     * Change the current state to a new one
     * @param state The wanted game state
     */
    public void setState(IGameState state){
        currentState = state;
    }


    /**
     * Get the current game state
     * @return The current game state
     */
    public IGameState getCurrentState() {
        return currentState;
    }
}
