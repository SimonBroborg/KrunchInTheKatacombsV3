package map;

import entity.Usable.Chest;
import entity.Usable.EventPortal;
import entity.Usable.Usable;
import entity.movables.Enemies.ShadowEnemy;
import entity.movables.Enemies.ZombEnemy;
import entity.movables.Enemy;
import entity.movables.Player;
import entity.tile_types.Torch;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  This class is used to load map files that can later be used by the tilemap
 *  to create a working game level
 */
public class MapParser {
    // Dimensions
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;


    // contains all the files to the
    private Map<Integer, ArrayList<String>> spritePaths;
    private ArrayList<Usable> usables;
    private ArrayList<Torch> torches;
    private Player player;
    private ArrayList<Enemy> enemies;

    // A document file (for example the map file)
    private Document doc = null;

    private String[][] textMap = null;

    private TileMap tm;

    public MapParser(TileMap tm ) {
        spritePaths = new HashMap<>();
        this.tm = tm;


        usables = new ArrayList<>();
        enemies = new ArrayList<>();
        torches = new ArrayList<>();
    }

    public void loadTMXFile(String tmxPath) {
        System.out.println("Loading tmx file...");
        try {
            // sets up so the XML file can be read
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Load the map file and parse it
            File mapFile = new File(tmxPath);
            doc = dBuilder.parse(mapFile);

            doc.getDocumentElement().normalize();

            // Get the dimensions of the map
            width = Integer.parseInt(getTagAttribute("map", "width"));
            height = Integer.parseInt(getTagAttribute("map", "height"));
            tileWidth = Integer.parseInt(getTagAttribute("map", "tilewidth"));
            tileHeight = Integer.parseInt(getTagAttribute("map", "tileheight"));

            // Set the size of the map (number of tiles)
            textMap = new String[height][width];

            // Get the numbers representing the tiles (the entire map)
            Node tileNumbers = doc.getElementsByTagName("data").item(0);

            // convert the tile numbers to a string
            String tileString = tileNumbers.getTextContent();

            // Split the string into rows of strings
            String[] stringRows = tileString.split("\n");

            // Add the rows to the text map but remove the commas
            for (int i = 1; i < height + 1; i++) {
                String[] tokens = stringRows[i].split(",");
                textMap[i - 1] = tokens;
            }

            // "Fetches" the tsx file path (path to the tileset file) from the tmx file and loads the file
            File tileSprites = new File("tileset.tsx");  // (getTagAttribute("tileset", "source"));

            // Load the tile set file
            doc = dBuilder.parse("resources/Maps/" + tileSprites);

            doc.getDocumentElement().normalize();

            // Set each tile tag as a node in a node list
            NodeList tileList = doc.getElementsByTagName("tile");

            // Sets the sprite for each tile type (id)
            for (int i = 0; i < tileList.getLength(); i++) {
                Node nNode = tileList.item(i);

                // Make sure the node is valid
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Get the tile id
                    String id = eElement.getAttribute("id");

                    // Get the tile type
                    String tileType = eElement.getAttribute("type");

                    // Get the path to each tile image
                    String spritePath = mapFile.getPath() + "/../" +
                            eElement.getElementsByTagName("image").item(0).getAttributes().getNamedItem("source")
                                    .getNodeValue();

                    ArrayList<String> tileInfo = new ArrayList<>();
                    tileInfo.add(tileType);
                    tileInfo.add(spritePath);
                    // Set the sprite to each different tile types
                    spritePaths.put(Integer.valueOf(id), tileInfo);
                }
            }

            // Load the map file again
            doc = dBuilder.parse(mapFile);

            NodeList objectList = doc.getElementsByTagName("object");

            for (int i = 0; i < objectList.getLength(); i++) {
                Node nNode = objectList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Object type \"" + eElement.getAttribute("name") + "\" added! ");

                    int x = (int) Float.parseFloat(eElement.getAttribute("x")) + tm.getX();
                    int y = (int) Float.parseFloat(eElement.getAttribute("y")) + tm.getY();

                    switch (eElement.getAttribute("name")) {
                        case "player":
                            player = new Player(x, y, tm);
                            player.setPosition(x, player.getY() - player.getHeight());
                            break;

                        case "eventPortalNext":
                            EventPortal epn = new EventPortal(true, x, y, tm);
                            epn.setPosition(x, epn.getY() - epn.getHeight());
                            usables.add(epn);
                            break;

                        case "eventPortalPrev":
                            EventPortal epp = new EventPortal(false, x, y, tm);
                            epp.setPosition(x, epp.getY() - epp.getHeight());
                            usables.add(epp);
                            break;

                        case "chest":
                            Chest c = new Chest(x, y, tm);
                            c.setPosition(x, c.getY() - c.getHeight());
                            usables.add(c);
                            break;

                        case "torch":
                            Torch t = new Torch(false, x, y, tm);
                            t.setPosition(x, t.getY() - t.getHeight());
                            torches.add(t);
                            break;

                        case "torchLit":
                            Torch tl = new Torch(true, x, y, tm);
                            tl.setPosition(x, tl.getY() - tl.getHeight());
                            torches.add(tl);
                            break;

                        case "zombEnemy":
                            Enemy ze = new ZombEnemy(x, y, player, tm);
                            ze.setPosition(x, ze.getY() - ze.getHeight());
                            enemies.add(ze);
                            break;

                        case "shadowEnemy":
                            Enemy se = new ShadowEnemy(x, y, player, tm);
                            se.setPosition(x, se.getY() - se.getHeight());
                            enemies.add(se);
                            break;

                        default:
                            System.out.println("The object type:  " + eElement.getAttribute("name") + " does not exist.");
                            break;
                    }
                }
            }


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("TMX-file loaded successfully!");
        }
    }

    /**
     * Get the hash map containing the sprite path for each tile type
     * @return A hash map containing the sprite paths
     */
    public Map<Integer, ArrayList<String>> getSpritePaths() {
        return spritePaths;
    }


    /**
     * Takes in a tag and the attribute which the user is looking for in that tag
     * @param tag The tag name
     * @param attribute The attribute looked for
     * @return The value of the attribute (if found), returns as a string
     */
    private String getTagAttribute(String tag, String attribute) {
        String value = "0";

        // Try-catch to make sure that it doesn't crash the program if the attribute isn't found
        try {
            value = doc.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
            System.out.println("Couldn't find the attribute \"" + attribute + "\" in the tag \"" + tag + "\" ");
        }
        return value;
    }

    // Setters and getters

    public String[][] getTextMap() {
        return textMap;
    }

    public int getWidth() {
        return width;

    }

    public int getHeight() {
        return height;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Torch> getTorches() {
        return torches;
    }

    public ArrayList<Usable> getUsables() {
        return usables;
    }
}
