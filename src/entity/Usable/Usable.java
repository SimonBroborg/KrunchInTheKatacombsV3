package entity.Usable;

import entity.movables.Movable;
import entity.movables.Player;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * Entities which can be used/Activated by the player
 */
public abstract class Usable extends Movable {
    protected boolean canUse;
    private Sprite useSprite;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    protected Usable(int x, int y, String spritePath, TileMap tm) {
        super(x, y, spritePath, tm);
        useSprite = new Sprite("resources/Sprites/Misc/useSprite.png");
    }

    public void update(Player p) {
        super.update();
        canUse = inRange(p, 70);
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        if(canUse){
            g2d.drawImage(useSprite.getImage(), x+ xMap + width, y + yMap - useSprite.getHeight() - 5, null);
        }
    }

    public abstract void use();

    public boolean canUse() {
        return canUse;
    }
}
