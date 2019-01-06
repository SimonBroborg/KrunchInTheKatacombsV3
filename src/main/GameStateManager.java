package main;

import game_state.ALevelState;
import game_state.GameState;
import game_state.LevelStates.Level1State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This manages and keeps track of the game states and switching between them
 */
public class GameStateManager
{
    private GameState currentState;
    private int currentLevel;

    private ArrayList<ALevelState> levels;

    private static int LEVEL1 = 0;
    private static int LEVEL2 = 1;

    public GameStateManager() {
        currentLevel = LEVEL1;
        levels = new ArrayList<>();
        levels.add(new Level1State("resources/Maps/map1.tmx"));
        levels.add(new Level1State("resources/Maps/map2.tmx"));
        levels.add(new Level1State("resources/Maps/map3.tmx"));

        currentState = levels.get(currentLevel);

        // Sets the gsm for every state
        currentState.init(this);
    }

    /**
     * Load the previous level if possible
     */
    public void prevLevel(){
        if(currentLevel > LEVEL1) {
            currentLevel--;
            currentState = levels.get(currentLevel);
            if (!((ALevelState) currentState).isInited()) {
                currentState.init(this);
            }
        }
    }

    /**
     * Load the next level if possible
     */
    public void nextLevel(){
        if(currentLevel < levels.size() - 1) {
            currentLevel++;
            currentState = levels.get(currentLevel);
            if (!((ALevelState) currentState).isInited()) {
                currentState.init(this);
            }
        }
    }

    /**
     * Update the current game state
     * @param mousePos The mouse position on the game component
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
    public void setState(GameState state){
        currentState = state;
    }


    /**
     * Get the current game state
     * @return The current game state
     */
    public GameState getCurrentState() {
        return currentState;
    }
}
