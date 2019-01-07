package map;

import entity.Entity;
import entity.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Chunk extends Entity {
    private ArrayList<Tile> tiles;


    /**
     * Creates an entity object
     *
     * @param x  the x-position
     * @param y  the y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public Chunk(int x, int y, int numCols, int numRows, TileMap tm) {
        super(x, y, tm);

        tiles = new ArrayList<>();

        width = tm.getTileWidth() * numCols;
        height = tm.getTileHeight() * numRows;
    }

    @Override
    public void draw(Graphics2D g2d) {
        for(Tile t : tiles){
            t.draw(g2d);
        }
    }

    public void addTile(Tile t){
        tiles.add(t);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    @Override
    public Rectangle getRectangle() {
        return super.getRectangle();
    }
}

