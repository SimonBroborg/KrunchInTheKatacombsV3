package main;

import entity.movables.Player;

import java.awt.*;

public class InfoDisplay {
    private int x;
    private int y;

    private int fontSize;
    private Color fontColor;

    private boolean visible;

    private Player player;

    public InfoDisplay(Player player){
        this.player = player;
        fontSize = 20;
        fontColor = Color.white;

        visible = false;

        this.x = 200;
        this.y = 100;

    }

    public void draw(Graphics2D g2d){
        if(visible) {
            g2d.setColor(fontColor);
            g2d.drawString("FPS: " + String.valueOf(GameComponent.ups), x, y);
            g2d.drawString("On ground: " + String.valueOf(player.isOnGround()), x, y + 30);
            g2d.drawString("Jumping: " + String.valueOf(player.isJumping()), x, y + 60);
        }

    }

    public void toggle(){
        visible = !visible;
    }
}
