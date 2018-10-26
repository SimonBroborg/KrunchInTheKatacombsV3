package menu;

import game_state.QuitState;
import main.GameComponent;
import main.GameStateManager;
import main.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu which the player can open during the game
 */
public class Menu
{
    private static final float FADE_SPEED = 0.1f;

    private int x;
    private int y;
    private int width;
    private int height;

    private float alpha;
    private boolean open;

    private Sprite menuBackground;
    private Sprite logo;

    private List<MenuButton> buttons;
    private static final int BUTTON_Y_OFFSET = 10;
    private int buttonHeight;

    private GameStateManager gsm;
    public Menu(int x, int y, GameStateManager gsm) {
	this.x = x;
	this.y = y;
	this.gsm = gsm;
	buttons = null;
	buttons = new ArrayList<>();
	open = false;
	alpha = 0;
	buttonHeight = 41;

	menuBackground = new Sprite("resources/Sprites/Misc/menuBackground.png");
	logo = new Sprite("resources/Sprites/Misc/gameLogo.png");

	this.width = menuBackground.getWidth();

	this.x = GameComponent.SCALED_WIDTH / 2 - this.width / 2;


	//addButton(new MenuButton(width, buttonHeight, "Resume Game", gsm.getCurrentState()));
	addButton(new MenuButton(width, buttonHeight, "Exit Game", new QuitState(gsm)));

	height = (buttons.size() * (buttonHeight + BUTTON_Y_OFFSET));
	this.y = GameComponent.SCALED_HEIGHT / 2 - height / 2 - 40;
	for (int i = 0; i < buttons.size(); i++) {

	    buttons.get(i).setY(this.y + i * (buttonHeight + BUTTON_Y_OFFSET));
	    buttons.get(i).setX(this.x);
	}
    }

    public void draw(Graphics2D g2d) {
	Composite originalComposite = g2d.getComposite();
	g2d.setPaint(Color.blue);

	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));


	if (open) {
	    alpha += FADE_SPEED;
	    if (alpha >= 1.0f) alpha = 1.0f;

	} else {
	    alpha -= FADE_SPEED;
	    if (alpha <= 0) alpha = 0;
	}

	g2d.setColor(new Color(255, 255, 255, 100));
	g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);

	g2d.drawImage(logo.getImage(), GameComponent.SCALED_WIDTH / 2 - logo.getWidth() / 2, y - logo.getHeight(), null);
	g2d.drawImage(menuBackground.getImage(), x, y, width, height, null);
	for (MenuButton mb : buttons) {
	    mb.draw(g2d);
	}

	g2d.setComposite(originalComposite);
    }

    public void update(Point mousePos) {
	for (MenuButton mb : buttons) {
	    mb.update(mousePos);
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
