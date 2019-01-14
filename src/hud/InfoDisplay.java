package hud;

import entity.movables.Player;
import main.GameComponent;

import java.awt.*;

/**
 * Display various information about the player etc. Used for debugging purposes.
 */
public class InfoDisplay {
    private int x;
    private int y;

    private Color fontColor;

    private boolean visible;

    private Player player;

    public InfoDisplay(Player player){
	this.player = player;
	final int fontSize = 20;
	fontColor = Color.white;

	visible = false;

	this.x = 200;
	this.y = 100;
    }

    public void draw(Graphics2D g2d){
	if(visible) {
	    g2d.setColor(fontColor);
	    g2d.drawString("FPS: " + GameComponent.ups, x, y);
	    g2d.drawString("On ground: " + player.isOnGround(), x, y + 30);
	    g2d.drawString("Jumping: " + player.isJumping(), x, y + 60);
	}

    }

    public void toggle(){
	visible = !visible;
    }
}
