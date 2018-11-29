package map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
 *
 */
public class MapParser {
    // Dimensions
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    private TileMap tm;

    private Map<Integer, String> spritePaths;

    // The TXM-file
    private Document doc = null;

    private String[][] textMap = null;


    public MapParser(TileMap tm) {
        spritePaths = new HashMap<>();
        this.tm = tm;
    }

    public void loadTMXFile(String tmxPath) {
        System.out.println("Loading tmx file...");
        try {
            // sets up so the XML file can be read
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            File xmlFile = new File(tmxPath);
            doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            width = Integer.parseInt(getTagAttribute("map", "width"));
            height = Integer.parseInt(getTagAttribute("map", "height"));
            tileWidth = Integer.parseInt(getTagAttribute("map", "tilewidth"));
            tileHeight = Integer.parseInt(getTagAttribute("map", "tileheight"));

            // Set the size of the map (number of tiles)
            textMap = new String[height][width];

            // Get the numbers representing the tiles
            Node tiles = doc.getElementsByTagName("data").item(0);

            // the tile map as a string
            String tileString = tiles.getTextContent();
            // splits the string into lines
            String[] lines = tileString.split("\n");

            for (int i = 1; i < height + 1; i++) {
                String[] tokens = lines[i].split(",");
                textMap[i - 1] = tokens;
            }

            // "Fetches" the tsx file path from the tmx file and loads the file
            File tileSprites = new File("tileset.tsx");  // (getTagAttribute("tileset", "source"));
            doc = dBuilder.parse("resources/Maps/" + tileSprites);

            doc.getDocumentElement().normalize();

            NodeList tileList = doc.getElementsByTagName("tile");

            // Sets the sprite for each tile type (id)
            for (int i = 0; i < tileList.getLength(); i++) {
                Node nNode = tileList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    String path = xmlFile.getPath() + "/../" +
                            eElement.getElementsByTagName("image").item(0).getAttributes().getNamedItem("source")
                                    .getNodeValue();
                    spritePaths.put(Integer.valueOf(Integer.parseInt(id)), path);
                }
            }

            doc = dBuilder.parse(xmlFile);


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("TMX-file loaded successfully!");
        }
    }

    public Map<Integer, String> getSpritePaths() {
        return spritePaths;
    }

    private String getTagAttribute(String tag, String attribute) {
        String value = "0";

        try {
            value = doc.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
            System.out.println("Couldn't find the attribute \"" + attribute + "\" in the tag \"" + tag + "\" ");
        }
        return value;
    }

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
}
