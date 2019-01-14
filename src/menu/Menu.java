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
    private static final int BUTTON_Y_OFFSET = 10;
    private static final int BUTTON_HEIGHT = 41;
    private int y;
    private float alpha;
    private boolean open;
    private Sprite menuBackground;
    private Sprite logo;
    private List<MenuButton> buttons;
    private GameStateManager gsm;

    public Menu(int x, int y) {
	this.y = y;
	buttons = null;
	buttons = new ArrayList<>();
	open = false;
	alpha = 0;

	menuBackground = new Sprite("resources/Backgrounds/paper.jpg");
	logo = new Sprite("resources/Sprites/Misc/gameLogo.png");

	final int width = menuBackground.getWidth();

	int x1 = GameComponent.SCALED_WIDTH / 2 - width / 2;

	//addButton(new MenuButton(width, BUTTON_HEIGHT, "Resume Game", gsm.getCurrentState()));
	addButton(new MenuButton(width, BUTTON_HEIGHT, "Exit Game", new QuitState(gsm)));

	// Make sure that the menu and buttons are centered on the screen
	final int height = (buttons.size() * (BUTTON_HEIGHT + BUTTON_Y_OFFSET));
	this.y = GameComponent.SCALED_HEIGHT / 2 - height / 2 - BUTTON_HEIGHT - 50;

	for (int i = 0; i < buttons.size(); i++) {
	    buttons.get(i).setY(this.y + i * (BUTTON_HEIGHT + BUTTON_Y_OFFSET));
	    buttons.get(i).setX(x1);
	}
    }

    public void draw(Graphics2D g2d) {
	Composite originalComposite = g2d.getComposite();

	g2d.setPaint(Color.blue);
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));


	// Fade in or out
	if (open) {
	    alpha += FADE_SPEED;
	    if (alpha >= 1.0f) alpha = 1.0f;

	} else {
	    alpha -= FADE_SPEED;
	    if (alpha <= 0) alpha = 0;
	}


	g2d.setColor(Color.black);
	g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);

	g2d.drawImage(menuBackground.getImage(), 0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT, null);
	g2d.drawImage(logo.getImage(), GameComponent.SCALED_WIDTH / 2 - logo.getWidth() / 2, y - logo.getHeight(), null);

	for (MenuButton mb : buttons) {
	    mb.draw(g2d);
	}

	g2d.setComposite(originalComposite);
    }


    // Update the menu ( checks if the buttons are hovered )
    public void update(Point mousePos) {
	for (MenuButton mb : buttons) {
	    mb.update(mousePos);
	}
    }

    /**
     * Toggle if the menu should be opened or closed
     */
    public void toggle() {
	open = !open;
    }

    /**
     * Check if the menu is open
     *
     * @return Boolean which tells if the menu is opened
     */
    public boolean isOpen() {
	return open;
    }

    public void addButton(MenuButton mb) {
	buttons.add(mb);
    }

    /**
     * Check if any button is clicked
     */
    public void mouseClicked() {
	if (open) {
	    for (MenuButton b : buttons) {
		if (b.isHovered()) {
		    //gsm.setState(b.getState());
		}
	    }
	}
    }
}
