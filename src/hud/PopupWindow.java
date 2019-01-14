package hud;

import main.GameComponent;
import main.Sprite;

import java.awt.*;

@SuppressWarnings("MagicNumber")
/**
 *  A window which pops up on top of the screen. Must be closed before any other actions.
 */
public class PopupWindow {

    private int x;
    private int y;

    private Sprite background;

    private float height;
    private int maxHeight;

    private Font font;

    private float growth;

    private String text;
    private String continueText;

    public PopupWindow(String text){
        this.text = text;
        continueText = "Press E to continue...";
        x = GameComponent.SCALED_WIDTH / 2;
        y = GameComponent.SCALED_HEIGHT / 2;
        height = 0;
        //noinspection MagicNumber
        maxHeight = 50;

        background = new Sprite("resources/Sprites/Misc/dialogueBox.png");
        growth = 3.0f;

        font = new Font("fira code", Font.BOLD, 20);
    }

    public void update(){
        // animate the height
        if(height < maxHeight){
            height += growth;
            y -= growth / 2;
        }else{
            height = maxHeight;
        }
    }

    public void draw(Graphics2D g2d){
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();

        final int horizontalOffset = 20;
        final float width = fm.stringWidth(text) + horizontalOffset * 2;
        x = GameComponent.SCALED_WIDTH / 2 - (int) width / 2;

        final int verticalOffset = 40;
        maxHeight = font.getSize() + verticalOffset * 2;

        float fontX = this.x + (width - fm.stringWidth(text)) / 2;
        float fontY = this.y + (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);

        g2d.drawImage(background.getImage(), x, y, (int) width, (int) height, null);
        if(height == maxHeight) {
            g2d.setColor(Color.white);
            g2d.drawString(text, fontX, fontY);

            g2d.setFont(new Font("fira code", Font.PLAIN, 14));
            fm = g2d.getFontMetrics();
            g2d.drawString(continueText, x + width - horizontalOffset - fm.stringWidth(continueText), y + height - 14);
        }
    }
}

