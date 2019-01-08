package HUD;

import entity.movables.Player;
import main.Sprite;

import java.awt.*;

/**
 * A HUD which displays things on the screen such as the players hp and the flashlights battery
 */
public class HUD {
    private Player player;
    private Sprite sprite;

    public HUD(Player p){
        player = p;
        sprite = new Sprite("resources/Sprites/HUD/hud.png");
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite.getImage(), 0, 0, null);

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.white);


        // Draw the player hp and battery power
        g2d.drawString(player.getHp() + "%", sprite.getWidth() / 2 , 35 + 7);
        g2d.drawString(player.getFlashLight().getBatteryPower() + "%", sprite.getWidth() / 2, 115 + 7);
    }

    public void update(){

    }
}
