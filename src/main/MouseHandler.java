package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 */
public class MouseHandler extends MouseAdapter
{
    private GameStateManager gsm;

    public MouseHandler(GameStateManager gsm) {
	this.gsm = gsm;
    }

    @Override public void mouseClicked(final MouseEvent e) {
	gsm.mouseClicked(e);
    }

    @Override public void mouseMoved(final MouseEvent e) {
	gsm.mouseMoved(e);
    }
}
