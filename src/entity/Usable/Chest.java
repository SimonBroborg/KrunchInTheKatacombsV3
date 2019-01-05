package entity.Usable;

import entity.movables.Player;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * A chest which can contain things and be opened by the player
 */
public class Chest extends Usable {
    private boolean opened;
    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Chest(int x, int y, TileMap tm) {
        super(x, y, tm);
        sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/boxAlt.png");

        fallSpeed = 0.5;
        maxFallSpeed = 10;
        gravity = 0.3;
        jumpStart = -5;

        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    @Override
    public void update(Player p) {
        super.update(p);
        if(opened) canUse = false;
    }

    @Override
    public void use() {
        if(!opened) {
            sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/boxEmpty.png");
            width = sprite.getWidth();
            height = sprite.getHeight();
            setJumping(true);
        }
        opened = true;
    }
}
