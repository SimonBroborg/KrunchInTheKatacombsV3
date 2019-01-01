package map;

import entity.Tile;
import entity.Usable.Usable;
import entity.movables.Player;
import entity.tile_types.LadderTile;
import entity.tile_types.NormalTile;
import entity.tile_types.Torch;
import flashlight.BackgroundTile;
import main.GameComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The map filled with tiles
 */
public class TileMap {
    // Map converting
    private String[][] textMap = null; // Convert the map file to a 2D-array
    private Tile[][] tileMap = null;  // Convert the text map to a 2D-array of tiles

    private ArrayList<Usable> usables;

    // Position
    private int x;
    private int y;

    private int tileWidth;
    private int tileHeight;

    private int numCols;
    private int numRows;

    private float tween;

    private Map<Integer, ArrayList<String>> spritePaths; // Stores the paths to the different tile sprites
    private String mapPath; // The path to the map text file

    public TileMap(String mapPath) {
        this.mapPath = mapPath;
        spritePaths = new HashMap<>();

        tween = 0.07f;

    }

    /**
     * Sets the text map, the variables, and the tile map
     */
    public void load() {
        final MapParser parser = new MapParser();

        parser.loadTMXFile(mapPath);

        textMap = parser.getTextMap();

        usables = new ArrayList<>();

        numCols = parser.getWidth();
        numRows = parser.getHeight();
        tileWidth = parser.getTileWidth();
        tileHeight = parser.getTileHeight();
        spritePaths = parser.getSpritePaths();

        loadTileMap();
    }

    /**
     * Updates the tile textMap's position
     *
     * @param player the player object which the position of the textMap is based on
     */
    public void update(Player player) {
        setPosition(GameComponent.WIDTH / 2 * GameComponent.SCALE - player.getX(),
                GameComponent.HEIGHT / 2 * GameComponent.SCALE - player.getY());

    }

    /**
     * Set the text map for the tile map
     * @param textMap A 2D-array containing the map
     */
    public void setTextmap(final String[][] textMap) {
        this.textMap = textMap;
    }

    /**
     * Loads the map by creating all the tiles
     */
    private void loadTileMap() {
        tileMap = new Tile[numRows][numCols];

        // Loops through the text map and adds a tile
        for (int y = 0; y < textMap.length; y++) {
            for (int x = 0; x < textMap[y].length; x++) {

                // If it's not an empty tile
                if (Integer.parseInt(textMap[y][x]) != 0) {
                    switch (spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(0)) {
                        case "background":
                            tileMap[y][x] =
                                    new BackgroundTile(spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(1), x * tileWidth,
                                            y * tileHeight, this);
                            break;

                        case "ladder":
                            tileMap[y][x] = new LadderTile(spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(1), x * tileWidth,
                                    y * tileHeight, this);
                            break;
                        case "torch":
                            usables.add(new Torch(true,x * tileWidth, y * tileHeight, this));
                            break;
                        default:
                            tileMap[y][x] =
                                    new NormalTile(spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(1), x * tileWidth,
                                            y * tileHeight, this);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Set the position of the tile map
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    private void setPosition(int x, int y) {
        // Make the map movement smoother
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        if(this.x > 0){
            this.x = 0;
        }
        if(this.y > 0) {
            this.y = 0;
        }
    }

    /**
     * Draws all the tiles to the frame
     *
     * @param g2d the graphics object
     */
    public void draw(Graphics2D g2d) {
        for(Tile tiles[] : tileMap){
            for(Tile tile : tiles){
                if( tile != null && !tile.isTransparent()){
                    tile.draw(g2d);
                }
            }
        }
    }


    public Tile[][] getTiles() {
        return tileMap;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
