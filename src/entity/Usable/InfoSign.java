package entity.Usable;

import map.TileMap;

public class InfoSign extends Usable {
    private InfoSignMessage message;

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

        message = new InfoSignMessage(text, x, y, tm);
    }

    @Override
    public void use(){
        //message.toggle();
    }
}
