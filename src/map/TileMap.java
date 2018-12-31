package map;

import entity.Tile;
import entity.movables.Player;
import entity.tile_types.EmptyTile;
import entity.tile_types.NormalTile;
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

    // Position
    private int x;
    private int y;

    private int tileWidth;
    private int tileHeight;

    private int width;
    private int height;

    private Map<Integer, ArrayList<String>> spritePaths; // Stores the paths to the different tile sprites
    private String mapPath; // The path to the map text file

    public TileMap(String mapPath) {
        this.mapPath = mapPath;
        spritePaths = new HashMap<>();
    }

    /**
     * Sets the text map, the variables, and the tile map
     */
    public void load() {
        final MapParser parser = new MapParser(this);

        parser.loadTMXFile(mapPath);

        textMap = parser.getTextMap();

        width = parser.getWidth();
        height = parser.getHeight();
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
        tileMap = new Tile[height][width];

        // Loops through the text map and adds a tile
        for (int y = 0; y < textMap.length; y++) {
            for (int x = 0; x < textMap[y].length; x++) {

                // If it's not an empty tile
                if (Integer.parseInt(textMap[y][x]) != 0) {
                    switch (spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(0)) {
                        case "normalTile":
                            tileMap[y][x] =
                                    new NormalTile(spritePaths.get(Integer.parseInt(textMap[y][x]) - 1).get(1), x * tileWidth,
                                            y * tileHeight, this);
                            break;

                        // In case an tile isn't correct
                        default:
                            tileMap[y][x] = new EmptyTile("resources/Sprites/tiles/normalTile.png", x * tileWidth, y * tileHeight, this);
                    }
                }
                // create an empty tile if the tile isn't valid
                else {
                    tileMap[y][x] =
                            new EmptyTile("resources/Sprites/tiles/normalTile.png", x * tileWidth, y * tileHeight, this);
                }
            }
        }
    }

    /**
     * Draws all the tiles to the frame
     *
     * @param g2d the graphics object
     */
    public void draw(Graphics2D g2d) {
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (!(tile.isTransparent())) {
                    tile.draw(g2d);
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
        this.x = x;
        this.y = y;
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
