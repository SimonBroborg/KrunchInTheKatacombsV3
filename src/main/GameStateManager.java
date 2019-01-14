package main;

import game_state.GameState;
import game_state.MainMenuState;
import game_state.level_states.LevelState;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This manages and keeps track of the game states and switching between them
 */
public class GameStateManager
{
    private State currentState;
    private State previousState = null;

    public GameStateManager() {
	currentState = State.LEVEL_1;

        // Sets the gsm for every state
	currentState.getState().init(this);
    }

    /**
     * Load the previous level if possible
     */
    public void prevLevel(){
        /*if(currentLevel > LEVEL1) {
            currentLevel--;
            currentState = levels.get(currentLevel);
            if (!((ALevelState) currentState).isInited()) {
                currentState.init(this);
            }
        }*/
    }

    /**
     * Load the next level if possible
     */
    public void nextLevel(){
        /*if(currentLevel < levels.size() - 1) {
            currentLevel++;
            currentState = levels.get(currentLevel);
            if (!((ALevelState) currentState).isInited()) {
                currentState.init(this);
            }
        }*/
    }

    /**
     * Update the current game state
     * @param mousePos The mouse position on the game component
     */
    public void update(Point mousePos) {
	currentState.getState().update(mousePos);
    }

    /**
     * Draw the current game state
     * @param g2d The graphics object
     */
    public void draw(Graphics2D g2d) {
	currentState.getState().draw(g2d);
    }

    public void keyPressed(int k) {
	currentState.getState().keyPressed(k);
    }

    public void keyReleased(int k) {
	currentState.getState().keyReleased(k);
    }

    public void mouseClicked(MouseEvent e) {
	currentState.getState().mouseClicked(e);
    }

    public void mouseMoved(MouseEvent e) {
	currentState.getState().mouseMoved(e);
    }

    /**
     * Change the current state to a new one
     * @param state The wanted game state
     */
    public void setState(State state) {
	previousState = currentState;
        currentState = state;
	currentState.getState().init(this);
    }

    public State getPreviousState() {
	return previousState;
    }

    public enum State
    {
	/**
	 * Level 1 of the game
	 */
	LEVEL_1(new LevelState("resources/Maps/map1.tmx")),
	/**
	 * The games main menu
	 */
	MAIN_MENU(new MainMenuState());

	private final GameState state;

	State(GameState state) {
	    this.state = state;
	}

	public GameState getState() {
	    return state;
	}
    }
}
