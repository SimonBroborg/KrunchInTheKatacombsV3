package main;

import entity.movables.Player;

import java.awt.*;

public class HUD {
    private Player player;
    private Sprite sprite;
    public HUD(Player p){
        player = p;

        sprite = new Sprite("resources/Sprites/HUD/hud.png");

    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite.getImage(), 0, 0, null);
    }

    public void update(){


    }
}
