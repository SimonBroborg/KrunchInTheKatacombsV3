package dialog;

import entity.AEntity;
import main.Sprite;
import map.TileMap;

import java.awt.*;

/**
 * A dialog box showing dialogs or messages
 */
public class DialogBox extends AEntity
{
    private String text;

     /**
      * Creates an entity object
     *
     * @param x The x-position
     * @param y The y-position
     * @param tm the levels tiles, used to check collisions etc
     */
    public DialogBox(String text, final int x, final int y, final TileMap tm) {
	super(x, y, tm);
	this.text = text;
	sprite = new Sprite("resources/Sprites/misc/dialogueBox.png");

	newFade(5000,1.0f, 0.0f);
	fade.run();
	width = sprite.getWidth();
	height = sprite.getHeight();
    }

    @Override public void update() {
	super.update();
   	fade.update();
    }

    public void draw(Graphics2D g2d){
        super.draw(g2d);
        drawText(g2d);
    }
    private void drawText(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.drawString(text, xMap, yMap);
    }

}
