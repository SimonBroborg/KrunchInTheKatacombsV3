package map;

import entity.Tile;
import entity.movables.Enemy;
import entity.movables.Player;
import entity.tile_types.AbyssTile;
import entity.tile_types.BackgroundTile;
import entity.tile_types.LadderTile;
import entity.tile_types.NormalTile;
import entity.tile_types.Torch;
import entity.usable.Usable;
import main.GameComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The map filled with tiles
 */
public class TileMap {
    // Map converting
    private String[][] textMap = null; // Convert the map file to a 2D-array
    private Iterable<Tile> tileMap;  // Convert the text map to a 2D-array of tiles
    private Chunk[][] chunks = null;


    private List<Usable> usables = null;
    private Collection<Torch> torches = null;
    private List<Enemy> enemies = null;
    private Player player = null;

    // Position
    private int x;
    private int y;

    private int tileWidth;
    private int tileHeight;

    private int numCols;
    private int numRows;

    private int chunkNumRows;
    private int chunkNumCols;

    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    private float tween;

    private Map<Integer, ArrayList<String>> spritePaths; // Stores the paths to the different tile sprites
    private String mapPath; // The path to the map text file

    public TileMap(String mapPath) {
        this.mapPath = mapPath;

        spritePaths = new HashMap<>();

        tileMap = new ArrayList<>();
        tween = 0.7f;

        chunkNumCols = 3;
        chunkNumRows = 3;
    }

    /**
     * Sets the text map, the variables, and the tile map
     */
    public void load() {
        final MapParser parser = new MapParser(this);

        parser.loadTMXFile(mapPath);

        textMap = parser.getTextMap();

        player = parser.getPlayer();
        usables = parser.getUsables();
        enemies = parser.getEnemies();
        torches = parser.getTorches();

        numCols = parser.getWidth();
        numRows = parser.getHeight();
        tileWidth = parser.getTileWidth();
        tileHeight = parser.getTileHeight();
        spritePaths = parser.getSpritePaths();

        numRowsToDraw = (int) Math.ceil((float) GameComponent.SCALED_HEIGHT / (chunkNumRows * tileHeight));
        numColsToDraw = (int) Math.ceil((float) GameComponent.SCALED_WIDTH / (chunkNumCols * tileWidth));

        createChunks();

        loadTileMap();
    }

    private void createChunks(){
        int numChunkCols = numCols / chunkNumCols + 1;
        int numChunkRows = numRows / chunkNumRows + 1;

        chunks = new Chunk[numChunkRows][numChunkCols];

        for(int row = 0; row < numChunkRows; row++){
            for(int col = 0; col < numChunkCols; col++){
                chunks[row][col] = new Chunk(col * tileWidth * chunkNumCols, row * tileHeight * chunkNumCols, numCols * tileWidth, numRows * tileHeight, this);
            }
        }
    }

    /**
     * Updates the tile textMap's position
     *
     * @param player the player object which the position of the textMap is based on
     */
    public void update(Player player) {
        setPosition(GameComponent.SCALED_WIDTH / 2 - player.getX() - player.getWidth() / 2,
                    GameComponent.SCALED_HEIGHT / 2 - player.getY() - player.getHeight() / 2);

        for(Chunk[] chunks : chunks) {
            for (Chunk c : chunks){
                c.update();
            }
        }
    }
    /**
     * Loads the map by creating all the tiles
     */
    private void loadTileMap() {
        tileMap = new ArrayList<>();

        // Loops through the text map and adds a tile
        for (int y = 0; y < textMap.length; y++) {
            for (int x = 0; x < textMap[y].length; x++) {

                // If it's not an empty tile
                if (Integer.parseInt(textMap[y][x]) != 0) {
                    Tile tile;
                    switch (spritePaths.get(Integer.valueOf(Integer.parseInt(textMap[y][x]) - 1)).get(0)) {

                        case "background":
                            tile = new BackgroundTile(
                                    spritePaths.get(Integer.valueOf(Integer.parseInt(textMap[y][x]) - 1)).get(1), x * tileWidth,
                                    y * tileHeight, this);
                            break;

                        case "ladder":
                            tile = new LadderTile(spritePaths.get(Integer.valueOf(Integer.parseInt(textMap[y][x]) - 1)).get(1),
                                                  x * tileWidth, y * tileHeight, this);
                            break;

                        case "abyss":
                            tile = new AbyssTile(spritePaths.get(Integer.valueOf(Integer.parseInt(textMap[y][x]) - 1)).get(1),
                                                 x * tileWidth, y * tileHeight, this);
                            break;
                        default:
                            tile = new NormalTile(spritePaths.get(Integer.valueOf(Integer.parseInt(textMap[y][x]) - 1)).get(1),
                                                  x * tileWidth, y * tileHeight, this);
                            break;
                    }
                    int chunkRow;
                    int chunkCol;

                    chunkRow = y / chunkNumRows;
                    chunkCol = x / chunkNumCols;

                    chunks[chunkRow][chunkCol].addTile(tile);
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
        this.x += (int) ((x - this.x) * tween);
        this.y += (int) ((y - this.y) * tween);

        // The tilemap doesnt move outisde the map borders
        if(this.x > 0){
            this.x = 0;
        } else if (this.x + tileWidth * numCols < GameComponent.SCALED_WIDTH) {
            this.x = GameComponent.SCALED_WIDTH - tileWidth * numCols;
        }
        if(this.y > 0) {
            this.y = 0;
        } else if (this.y + tileHeight * numRows < GameComponent.SCALED_HEIGHT) {
            this.y = GameComponent.SCALED_HEIGHT - tileHeight * numRows;
        }

        colOffset = -this.x / (chunkNumCols * tileWidth);
        rowOffset = -this.y / (chunkNumRows * tileHeight);
    }

    /**
     * Draws the chunks to the screen (if they are inside the screen)
     *
     * @param g2d the graphics object
     */
    public void draw(Graphics2D g2d) {

        for(int row = rowOffset; row <= rowOffset + numRowsToDraw; row++){
            if(row >= chunks.length ) break;
            for(int col = colOffset; col <= colOffset + numColsToDraw; col++){
                if(col >= chunks[row].length) break;
                chunks[row][col].draw(g2d);
            }
        }
    }


    public Iterable<Tile> getTileMap() {
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

    public List<Usable> getUsables() {
        return usables;
    }

    public Collection<Torch> getTorches() {
        return torches;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public Player getPlayer() {
        return player;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }
}
