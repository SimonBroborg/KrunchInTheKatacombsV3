package main;

import game_state.Level1State;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 *
 */
public class GameStateManager
{
    private Level1State level1;

    public GameStateManager() {
	level1 = new Level1State("resources/Maps/map1.tmx");
	// Sets the gsm for every state
	level1.init(this);
    }

    public void update(Point mousePos) {
	level1.update(mousePos);
    }

    public void draw(Graphics2D g2d) {
	level1.draw(g2d);
    }

    public void keyPressed(int k) {
	level1.keyPressed(k);
    }

    public void keyReleased(int k) {
	level1.keyReleased(k);
    }

    public void mouseClicked(MouseEvent e) {
	level1.mouseClicked(e);
    }

    public void mouseMoved(MouseEvent e) {
	level1.mouseMoved(e);
    }


}
