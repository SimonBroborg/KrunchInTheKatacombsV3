package game_state;

import main.GameStateManager;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This state exits the game immidietaly
 */
public class QuitState implements GameState
{
    public QuitState(GameStateManager gsm) {

    }

    @Override public void init(final GameStateManager gsm) {

    }

    @Override public void update(final Point mousePos) {
        System.exit(0);
    }

    @Override public void draw(final Graphics2D g2d) {

    }

    @Override public void keyPressed(final int k) {

    }

    @Override public void keyReleased(final int k) {

    }

    @Override public void mouseClicked(final MouseEvent e) {

    }

    @Override public void mouseMoved(final MouseEvent e) {

    }
}
