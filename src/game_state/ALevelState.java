package game_state;

import entity.AEntity;
import entity.Player;
import entity.tile_types.EchoCircle;
import flashlight.FlashLight;
import main.GameStateManager;
import map.Background;
import map.TileMap;
import menu.Menu;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 */
public abstract class ALevelState implements IGameState {
    protected TileMap tm = null;
    protected Player player = null;
    protected FlashLight fl = null;

    protected Menu menu = null;

    protected ArrayList<AEntity> entities;

    // Path to the map file
    protected String mapPath;

    protected boolean restart;

    protected Background bg = null;

    protected GameStateManager gsm;

    protected ALevelState(String mapPath) {
        this.mapPath = mapPath;

        entities = new ArrayList<>();
    }

    /**
     * @param gsm the game state manager which controls the current state
     */
    public void init(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Loads the level by setting the tile map, object and background
     */
    public void loadLevel() {
        restart = false;

        tm = new TileMap(mapPath);
        tm.load();

        bg = new Background("resources/Backgrounds/background.jpg", 0);

        menu = new Menu(10, 10, gsm);

        player = new Player(100, -500, tm);
        fl = new FlashLight(tm, player);
        fl = new FlashLight(tm, player);
    }

    /**
     * Updates everything on the screen
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos) {
        // The game pauses if the menu is open, aka nothing updates
        if (!menu.isOpen()) {
            player.update();
            fl.update(mousePos);
            tm.update(player);
            bg.update();

            for(int i = 0; i < entities.size(); i++){
                entities.get(i).update();
                if(entities.get(i).shouldRemove()){
                    entities.remove(i);
                    i--;
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

        for(AEntity e : entities){
            e.draw(g2d);
        }
        fl.draw(g2d);
        player.draw(g2d);

        menu.draw(g2d);
        g2d.dispose();
    }


    /**
     * Updates the mouse position
     */
    /*private void updateMouse(Point mousePos) {
	if (mousePos != null) {
	    // Sets the target point for the flashlight
	    fl.setTargetX((int) mousePos.getX());
	    fl.setTargetY((int) mousePos.getY());
	}
    }*/
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
            case KeyEvent.VK_E:
                player.setPosition(100, 100);
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
        fl.createEcho();
    }


    public void mouseMoved(MouseEvent e) {

    }

}
