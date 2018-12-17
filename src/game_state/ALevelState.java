package game_state;

import dialog.DialogBox;
import entity.AEntity;
import entity.Player;
import flashlight.FlashLight;
import main.GameStateManager;
import map.*;
import menu.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class level state. Sets the default behavior and methods for the levels of the game.
 */
public abstract class ALevelState implements IGameState {

    protected TileMap tm = null;

    protected Player player = null;

    protected FlashLight fl = null;

    protected Menu menu = null;

    protected DialogBox db = null;

    protected List<AEntity> entities;

    // Path to the map file
    protected String mapPath;

    protected boolean restart;

    protected Background bg = null;

    protected GameStateManager gsm = null;

    /**
     * The construcotr takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    protected ALevelState(String mapPath) {
        this.mapPath = mapPath;
        entities = new ArrayList<>();
        tm = new TileMap(mapPath);

    }

    /**
     * @param gsm the game state manager which controls the current state
     */
    public void init(GameStateManager gsm) {
        this.gsm = gsm;
        menu = new Menu(10, 10, gsm);

        bg = new Background("resources/Backgrounds/background.jpg", 0);
    }

    /**
     * Loads the level by setting the tile map, object and background
     */
    public void loadLevel() {
        restart = false;

        // Everything depending on the tile map must be created after this
        tm.load();

        db = new DialogBox("this is text", 0, 0, tm);


        player = new Player(100, -500, tm);
        fl = new FlashLight(tm, player);
    }

    /**
     * Updates everything on the screen
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos) {
        db.update();
        // The game pauses if the menu is open, aka nothing updates
        if (!menu.isOpen()) {
            player.update();
            fl.update(mousePos);
            tm.update(player);
            bg.update();


            // Loop through the entitys and check if anyone should be removed
            Iterator<AEntity> it = entities.iterator();
            while (it.hasNext()) {
                AEntity e = it.next();
                e.update();
                if (e.shouldRemove()) {
                    it.remove();
                }
            }
        }

        menu.update(mousePos);

        if (restart) {
            loadLevel();
        }
    }

    /**
     * Draws everything to the screen
     *
     * @param g2d the drawing object
     */
    public void draw(final Graphics2D g2d) {
        bg.draw(g2d);
        tm.draw(g2d);

        for (AEntity e : entities) {
            e.draw(g2d);
        }

        fl.draw(g2d);
        player.draw(g2d);


        db.draw(g2d);

        menu.draw(g2d);
        g2d.dispose();
    }


    /**
     * Checks if a key is pressed and act correspondingly
     *
     * @param k the number of the key pressed
     */
    @Override
    public void keyPressed(final int k) {
        switch (k) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJumping(true);
                break;
            case KeyEvent.VK_R:
                player.setPosition(100, 100);
                break;
            case KeyEvent.VK_T:
                fl.toggle();
                break;
            case KeyEvent.VK_ESCAPE:
                menu.toggle();
        }
    }


    @Override
    public void keyReleased(final int k) {
        if (k == KeyEvent.VK_A) player.setLeft(false);
        if (k == KeyEvent.VK_D) player.setRight(false);
        if (k == KeyEvent.VK_SPACE) player.setJumping(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (menu.isOpen()) {
            menu.mouseClicked();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // DO nothing
    }

}
