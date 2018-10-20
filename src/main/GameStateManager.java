package main;

import game_state.IGameState;
import game_state.Level1State;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 *
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

    public void update(Point mousePos) {
	currentState.update(mousePos);
    }


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
	level1.mouseClicked(e);
    }

    public void mouseMoved(MouseEvent e) {
	currentState.mouseMoved(e);
    }


    public void setState(IGameState state){
        currentState = state;
    }

    public IGameState getCurrentState() {
	return currentState;
    }
}
