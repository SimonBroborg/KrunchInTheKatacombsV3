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
        super(x, y, tm);

        width = tm.getNumCols() * tm.getTileWidth();
        height = tm.getNumRows() * tm.getTileHeight();
        lightmap = new Area(new Rectangle(x, y, width, height));
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
    }

    public void draw(Graphics2D g2d){
        float darknessAlpha = 0.95f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darknessAlpha));

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);
        /*for(Torch t : torches){
            if(t.isLit()) {
                t.getLightSource().draw(g2d);
            }
        }

        g2d.setColor(Color.BLACK);

        g2d.setClip(lightmap);
        g2d.fillRect(x, y, width, height);

        g2d.setClip(null);
*/
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
