package entity.usable;

import map.TileMap;

/**
 * An usable which the player can interact with to get some kind of information.
 */
public class InfoSign extends Usable {

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public InfoSign(String text, int x, int y, TileMap tm) {
	super(x, y, "resources/Sprites/Tiles/PlatformerTiles/Tiles/sign.png", tm);

	fallSpeed = 0.5;
	maxFallSpeed = 10;
	gravity = 0.3;
	jumpStart = -5;

	width = sprite.getWidth();
	height = sprite.getHeight();

	final InfoSignMessage message = new InfoSignMessage(text, x, y, tm);
    }

    @Override public void use(){
	//message.toggle();
    }
}
