package entity.tile_types;

import entity.Animation;
import entity.LightSource;
import entity.Usable.Usable;
import entity.movables.Player;
import main.Sprite;
import map.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Torch extends Usable {
    private boolean lit;
    private Animation animation;
    private LightSource lightSource;

    private static final int RANGE = 100;

    public Torch(boolean isLit, int x, int y, TileMap tm) {
        super(x, y, tm);

        animation = new Animation();

        lit = isLit;

        BufferedImage[] bi = {
                new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/tochLit.png").getImage(),
                new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/tochLit2.png").getImage()
        };

        sprite = new Sprite("resources/Sprites/Tiles/platformerTiles/Tiles/torch.png");

        animation.setFrames(bi);
        animation.setDelay(400);

        width = sprite.getWidth();
        height = sprite.getHeight();

        lightSource = new LightSource(400, (int)getCenter().getX(), (int)getCenter().getY() - height, tm);

    }

    @Override
    public void update(Player p) {
        super.update(p);
        if(lit) {
            animation.update();
            lightSource.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if(lit)
            g2d.drawImage(animation.getImage(), x + xMap, y + yMap, width, height, null);
        else
            g2d.drawImage(sprite.getImage(), x + xMap, y + yMap, width, height, null);

    }

    @Override
    public void use() {
        toggle();
    }

    private void toggle(){
        lit = !lit;
    }

    public boolean isLit() {
        return lit;
    }

    public LightSource getLightSource() {
        return lightSource;
    }
}

