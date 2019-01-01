package entity.tile_types;

import entity.Usable.Usable;
import entity.movables.Player;
import main.Sprite;
import map.TileMap;

public class Torch extends Usable {
    private boolean lit;

    private static final int RANGE = 100;

    public Torch(boolean isLit, int x, int y, TileMap tm) {
        super(x, y, tm);

        lit = isLit;

        sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/tochLit.png");

        width = sprite.getWidth();
        height = sprite.getHeight();

    }

    @Override
    public void update(Player p) {
        super.update(p);

        if (lit) {
            sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/tochLit.png");

        } else {
            sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/torch.png");
        }
    }

    @Override
    public void use() {
        toggle();
    }

    private void toggle(){
        lit = !lit;
    }
}
