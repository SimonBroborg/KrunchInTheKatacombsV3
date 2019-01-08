package entity;

import entity.tile_types.Torch;
import main.GameComponent;
import map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Lightmap extends Entity {
    private ArrayList<Torch> torches;
    private Area lightmap;
    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Lightmap(int x, int y, TileMap tm) {
        super(x, y, null, tm);

        width = tm.getNumCols() * tm.getTileWidth();
        height = tm.getNumRows() * tm.getTileHeight();
        lightmap = new Area(new Rectangle(x, y, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT));
    }

    public void setTorches(ArrayList<Torch> torches) {
        this.torches = torches;

        for(Torch t : torches){
            if(t.isLit()) {
                LightSource ls = t.getLightSource();
                lightmap.subtract(ls.getLight());
            }
        }
    }

    public void update(){
        lightmap = new Area(new Rectangle(x, y, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT));
    }

    public void draw(Graphics2D g2d){
        float darknessAlpha = 0.95f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darknessAlpha));


        for (Torch t : torches) {
            if(t.isLit()) {
                LightSource ls = t.getLightSource();
                ls.draw(g2d);
                lightmap.subtract(ls.getLight());
            }
        }

        g2d.setColor(Color.BLACK);

        g2d.fill(lightmap);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

    }
}
