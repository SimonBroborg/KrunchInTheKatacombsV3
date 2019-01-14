package game_state;

import entity.Entity;
import entity.Lightmap;
import entity.movables.Enemy;
import entity.movables.Player;
import entity.tile_types.Torch;
import entity.usable.Portal;
import entity.usable.Usable;
import hud.HUD;
import hud.InfoDisplay;
import hud.PopupWindowQueue;
import main.GameComponent;
import main.GameStateManager;
import map.Background;
import map.TileMap;
import sound.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class level state. Sets the default behavior and methods for the levels of the game.
 */
public abstract class ALevelState implements GameState {

    protected TileMap tm;

    protected Player player = null;

    private Portal portal = null;

    private List<Enemy> enemies = null;
    private List<Usable> usables = null;

    private Lightmap lightMap = null;

    private Background bg = null;

    protected GameStateManager gsm = null;

    // hud
    private HUD hud = null;
    private InfoDisplay info = null;
    private PopupWindowQueue popupQ = null;

    private float foregroundAlpha;

    private SoundPlayer backgroundSound = null;

    /**
     * The constructor takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    protected ALevelState(String mapPath) {
        // Path to the map file
        tm = new TileMap(mapPath);
    }

    /**
     * Initialize lists and instances
     * @param gsm the game state manager which controls the current state
     */
    @SuppressWarnings("InstanceofConcreteClass") public void init(GameStateManager gsm) {
        this.gsm = gsm;
        tm.load();

        // Initialize the array lists
        enemies = new ArrayList<>();
        usables = new ArrayList<>();
        Collection<Torch> torches;

        bg = new Background("resources/Backgrounds/background.jpg", 0);
        foregroundAlpha = 1.0f;

        // Set the usables and torches
        usables = tm.getUsables();
        torches = tm.getTorches();
        usables.addAll(torches);

        lightMap = new Lightmap(tm.getX(), tm.getY(), tm);
        lightMap.setTorches(torches);

        portal = tm.getPortal();

        player = tm.getPlayer();

        enemies = tm.getEnemies();

        hud = new HUD(player);
        info = new InfoDisplay(player);
        popupQ = new PopupWindowQueue();

        backgroundSound = new SoundPlayer("resources/Sounds/background/Bog-Creatures-On-the-Move_Looping.wav");
    }

    /**
     * Updates everything on the screen
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos) {
        if(!backgroundSound.playing()){
            //backgroundSound.playOnce();
        }
        if(foregroundAlpha > 0.0f){
            foregroundAlpha -= 0.01;
        }
        popupQ.update();

        // The game pauses if the menu is open, aka nothing updates
        if (!popupQ.isDisplaying()) {

            tm.update(player);
            bg.update();
            lightMap.update();

            // the player
            player.update(mousePos);

            // update the enemies
            Iterator<Enemy> eIt = enemies.iterator();
            while (eIt.hasNext()) {
                Entity e = eIt.next();
                e.update();
                if (e.shouldRemove()) {
                    eIt.remove();
                }
            }

            // Update the usables
            Iterator<Usable> uIt = usables.iterator();
            while (uIt.hasNext()) {
                Usable u = uIt.next();
                u.update(player);

                if (u.shouldRemove()) {
                    uIt.remove();
                }
            }

            portal.update(player);

            // Update the hud
            hud.update();
        }

        // Respawn the player if it dies
        if(player.isDead()){
            player.respawn();
        }

        if(portal.isUsed()){
            playerOnPortal();
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

        portal.draw(g2d);

        for(Usable u : usables){
            u.draw(g2d);
        }

        for (Entity e : enemies) {
            e.draw(g2d);
        }

        player.draw(g2d);

        lightMap.draw(g2d);

        if(foregroundAlpha > 0.0f){
            g2d.setColor(new Color(0.0f, 0.0f, 0.0f, foregroundAlpha));
            g2d.fillRect(0, 0, GameComponent.WIDTH, GameComponent.HEIGHT);
        }

        // Draw hud and menu
        popupQ.draw(g2d);
        hud.draw(g2d);
        info.draw(g2d);

        g2d.dispose();
    }

    /**
     * Checks if a key is pressed and act correspondingly
     *
     * @param k the number of the key pressed
     */
    @Override
    public void keyPressed(final int k) {
        player.keyPressed(k);
        switch (k) {
            case KeyEvent.VK_E:
                // Prioritizes closing popups
                if(popupQ.isDisplaying()){
                    popupQ.nextPopup();
                }else {
                    for (Usable u : usables) {
                        if (u.canUse()) {
                            u.use();
                        }
                    }
                    if(portal.canUse()){
                        portal.use();
                    }
                }
                break;
            case KeyEvent.VK_ESCAPE:
                gsm.setState(State.MAIN_MENU);
                break;
            case KeyEvent.VK_F3:
                info.toggle();
                break;
            case KeyEvent.VK_F1:
                hud.toggle();
                break;
        }
    }

    public abstract void playerOnPortal();


    @Override
    public void keyReleased(final int k) {
        player.keyReleased(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // DO nothing
    }
}
