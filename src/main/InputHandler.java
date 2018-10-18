package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles all the playesr keyboard inputs (the mouse is handled by the mouse handler)
 */
public class InputHandler extends KeyAdapter
{
    private GameStateManager gsm;

    public InputHandler(GameStateManager gsm){
        this.gsm = gsm;
    }

    public void keyPressed(final KeyEvent e){
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(final KeyEvent e){
        gsm.keyReleased(e.getKeyCode());
    }
}
