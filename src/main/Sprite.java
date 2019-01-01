package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A sprite class which contains a BufferedImage and the width and height of that image.
 */
public class Sprite
{
    private BufferedImage image = null;
    private int width;
    private int height;

    public Sprite(String spritePath){
        // TODO Resource thingy instead of normal try-catch
        try {
            image = ImageIO.read(new File(spritePath));
        } catch(IOException ignored){
            System.out.println("Sprite from path: " + spritePath + ", could not get loaded.");
        }


        // Set the width and height of the sprite. 0 if the image couldn't load
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }
        else{
            width = 0;
            height = 0;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
