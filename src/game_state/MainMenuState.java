package game_state;

import main.GameStateManager;
import menu.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The main menu of the game which is seen when the game first starts.
 */
public class MainMenuState implements GameState
{

    private Menu menu;
    private GameStateManager gsm = null;

    public MainMenuState() {
	menu = new Menu(10, 10);
	menu.toggle();
    }

    @Override public void init(GameStateManager gsm) {
	this.gsm = gsm;
    }

    @Override public void update(Point mousePos) {
	menu.update(mousePos);
    }

    @Override public void draw(Graphics2D g2d) {
	menu.draw(g2d);
    }

    @Override public void keyPressed(int k) {
	if (k == KeyEvent.VK_ESCAPE) {
	    gsm.setState(gsm.getPreviousState());
	}
    }

    @Override public void keyReleased(int k) {

    }

    @Override public void mouseClicked(MouseEvent e) {
	menu.mouseClicked();
    }

    @Override public void mouseMoved(MouseEvent e) {

    }
}
