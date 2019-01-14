package main;

import game_state.State;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This manages and keeps track of the game states and switching between them
 */
public class GameStateManager
{
    private boolean paused;
    private State currentState;

    public GameStateManager() {
	currentState = State.MAIN_MENU;
	currentState.getState().init(this);

	paused = false;
    }

    /**
     * Update the current game state
     * @param mousePos The mouse position on the game component
     */
    public void update(Point mousePos) {
        if(paused){
            return;
	}
	currentState.getState().update(mousePos);
    }

    /**
     * Draw the current game state
     * @param g2d The graphics object
     */
    public void draw(Graphics2D g2d) {
        if(paused){
            return;
	}
	currentState.getState().draw(g2d);
    }

    /**
     * Change the current state to a new one
     * @param state The wanted game state
     */
    public void setState(State state) {
	currentState = state;

	// Using pause prevents a NullPointerException when chaning the state
	paused = true;
	currentState.getState().init(this);
    	paused = false;
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

}
