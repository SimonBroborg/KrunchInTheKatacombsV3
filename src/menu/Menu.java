package menu;

import main.GameComponent;
import main.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu which the player can open during the game
 */
public class Menu
{
    private int x;
    private int y;
    private int width;
    private int height;

    private float alpha;
    private float fadeSpeed;

    private boolean open;

    private Sprite menuBackground;

    private List<MenuButton> buttons;


    public Menu(int x, int y) {
	this.x = x;
	this.y = y;
	buttons = null;
	buttons = new ArrayList<>();
	open = false;
	alpha = 0;
	fadeSpeed = 0.1f;

	menuBackground = new Sprite("resources/Sprites/Misc/menuBackground.png");
	this.width = menuBackground.getWidth();
	this.height = menuBackground.getHeight();

	buttons.add(new MenuButton());

    }

    private AlphaComposite makeComposite(float alpha) {
	int type = AlphaComposite.SRC_OVER;
	return (AlphaComposite.getInstance(type, alpha));
    }


    public void draw(Graphics2D g2d) {


	Composite originalComposite = g2d.getComposite();
	g2d.setPaint(Color.blue);

	g2d.setComposite(makeComposite(alpha));


	if (open) {
	    alpha += fadeSpeed;
	    if (alpha >= 1.0f) alpha = 1.0f;

	} else {
	    alpha -= fadeSpeed;
	    if (alpha <= 0) alpha = 0;
	}

	g2d.setColor(new Color(255, 255, 255, 100));
	g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);

	g2d.drawImage(menuBackground.getImage(), x, y, null);
	for (MenuButton mb : buttons) {
	    mb.draw(g2d);
	}

	g2d.setComposite(originalComposite);
    }

    public void update() {
	for (MenuButton mb : buttons) {
	    mb.update();
	}
    }

    public void toggle() {
	open = !open;
    }

    public boolean isOpen() {
	return open;
    }

    public void addButton(MenuButton mb) {
	buttons.add(mb);
    }
}
