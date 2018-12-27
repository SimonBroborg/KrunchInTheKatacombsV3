package game_state;

import entity.AEntity;
import entity.movables.BasicEnemy;
import entity.movables.BasicEnemy2;
import entity.movables.Enemy;
import entity.movables.Player;
import main.GameStateManager;
import main.HUD;
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

    protected TileMap tm;

    protected Player player = null;

    protected Menu menu = null;

    protected List<Enemy> enemies;

    // Path to the map file
    protected String mapPath;

    protected boolean restart;

    protected Background bg = null;

    protected GameStateManager gsm = null;

    protected HUD hud;

    /**
     * The construcotr takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    protected ALevelState(String mapPath) {
        this.mapPath = mapPath;

        tm = new TileMap(mapPath);
    }

    /**
     * @param gsm the game state manager which controls the current state
     */
    public void init(GameStateManager gsm) {
        this.gsm = gsm;
        enemies = new ArrayList<>();
        menu = new Menu(10, 10, gsm);


        bg = new Background("resources/Backgrounds/background.jpg", 0);

        // Everything depending on the tile map must be created after this
        tm.load();

        player = new Player(100, -200, tm);
        hud = new HUD(player);


        enemies.add(new BasicEnemy(300, -500, player,  tm));
        enemies.add(new BasicEnemy2(500, -500, player,  tm));
    }

    private void reset(){
        enemies.clear();

        player = new Player(100, -200, tm);
        hud = new HUD(player);

        enemies.add(new BasicEnemy(300, -500, player,  tm));
        enemies.add(new BasicEnemy2(500, -500, player,  tm));

    }


    /**
     * Updates everything on the screen
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos) {
        menu.update(mousePos);
        // The game pauses if the menu is open, aka nothing updates
        if (!menu.isOpen()) {

            tm.update(player);
            bg.update();

            // the player
            player.update(mousePos);



            // update the enemies
            Iterator<Enemy> it = enemies.iterator();
            while (it.hasNext()) {
                AEntity e = it.next();
                e.update();
                if (e.shouldRemove()) {
                    it.remove();
                }
            }

            hud.update();

        }

        if(player.isDead()){
            player.respawn(100, -500);
            //reset();
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

        for (AEntity e : enemies) {
            e.draw(g2d);
        }

        player.draw(g2d);

        hud.draw(g2d);

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
                player.toggleFlashlight();
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
        menu.mouseClicked();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // DO nothing
    }

}
