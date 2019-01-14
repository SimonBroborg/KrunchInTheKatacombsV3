package map;

import entity.Entity;
import entity.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A chunk is a block of tiles. It helps with rendering less tiles than needed and gives an overall better performance.
 */
public class Chunk extends Entity {
    private List<Tile> tiles;

    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Chunk(int x, int y, int numCols, int numRows, TileMap tm) {
        super(x, y, null, tm);

        tiles = new ArrayList<>();

        setWidth(tm.getTileWidth() * numCols);
        setHeight(tm.getTileHeight() * numRows);
    }

    @Override public void draw(Graphics2D g2d) {
        for(Tile t : tiles){
            t.draw(g2d);
        }
    }

    public void addTile(Tile t){
        tiles.add(t);
    }

    public Iterable<Tile> getTiles() {
        return tiles;
    }

}

