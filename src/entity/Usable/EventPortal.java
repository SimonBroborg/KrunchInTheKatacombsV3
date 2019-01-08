package entity.Usable;

import game_state.ALevelState;
import main.Sprite;
import map.TileMap;

public class EventPortal extends Usable {
    private boolean direction;
    private ALevelState als;
    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public EventPortal(boolean direction, int x, int y, TileMap tm) {
        super(x, y, null, tm);

        if(direction){
            sprite = new Sprite("resources/Sprites/Tiles/eventPortalNext.png");
        }else{
            sprite = new Sprite("resources/Sprites/Tiles/eventPortalPrev.png");
        }

        width = sprite.getWidth();
        height = sprite.getHeight();

        // Sets the direction of the portal (next = true, prev = false)
        this.direction = direction;
    }

    @Override
    public void use() {
        if(direction){
            als.setLoadNext(true);
        }else{
            als.setLoadPrev(true);
        }
    }

    public void setAls(ALevelState als) {
        this.als = als;
    }
}
