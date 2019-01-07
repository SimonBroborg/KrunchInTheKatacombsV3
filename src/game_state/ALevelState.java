package game_state;

import entity.Lightmap;
import sound.SoundPlayer;
import entity.Entity;
import entity.LightSource;
import entity.Usable.EventPortal;
import entity.Usable.Usable;
import entity.movables.*;
import entity.tile_types.Torch;
import main.GameComponent;
import main.GameStateManager;
import HUD.*;
import map.*;
import menu.Menu;
import java.awt.geom.Area;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class level state. Sets the default behavior and methods for the levels of the game.
 */
public abstract class ALevelState implements GameState {

    private boolean inited;

    private boolean loadNext;
    private boolean loadPrev;

    protected TileMap tm;

    protected Player player = null;

    protected Menu menu = null;

    protected List<Enemy> enemies;
    protected List<Usable> usables;
    protected ArrayList<Torch> torches;

    protected Lightmap lightMap;

    // Path to the map file
    protected String mapPath;

    protected boolean restart;

    protected Background bg = null;

    protected GameStateManager gsm = null;

    // HUD
    protected HUD hud;
    protected InfoDisplay info;
    protected PopupWindowQueue popupQ;

    protected Rectangle foreground;
    protected float foregroundAlpha;

    private SoundPlayer backgroundSound;

    /**
     * The constructor takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    protected ALevelState(String mapPath) {
        this.mapPath = mapPath;
        tm = new TileMap(mapPath);

        tm.load();
        inited = false;
        loadNext = false;
        loadPrev = false;
    }

    /**
     * Initialize lists and instances
     * @param gsm the game state manager which controls the current state
     */
    public void init(GameStateManager gsm) {
        this.gsm = gsm;

        // Initialize the array lists
        enemies = new ArrayList<>();
        usables = new ArrayList<>();
        torches = new ArrayList<>();
        menu = new Menu(10, 10, gsm);

        bg = new Background("resources/Backgrounds/background.jpg", 0);
        foreground = new Rectangle(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);
        foregroundAlpha = 1.0f;

        // Set the usables and torches
        usables = tm.getUsables();
        torches = tm.getTorches();
        usables.addAll(torches);

        lightMap = new Lightmap(tm.getX(), tm.getY(), tm);
        lightMap.setTorches(torches);

        for(Usable u : usables){
            if(u instanceof EventPortal){
                ((EventPortal) u).setAls(this);
            }
        }
        player = tm.getPlayer();
        enemies = tm.getEnemies();



        hud = new HUD(player);
        info = new InfoDisplay(player);
        popupQ = new PopupWindowQueue();

        backgroundSound = new SoundPlayer("resources/Sounds/background/Bog-Creatures-On-the-Move_Looping.wav");
        inited = true;
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
        menu.update(mousePos);
        popupQ.update();

        // The game pauses if the menu is open, aka nothing updates
        if (!menu.isOpen() && !popupQ.isDisplaying()) {

            tm.update(player);
            bg.update();
            //lightMap.update();

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

            // Update the HUD
            hud.update();
        }

        // Respawn the player if it dies
        if(player.isDead()){
            player.respawn();
            //reset();
        }

        // Check if another level should be loaded
        if(loadNext){
            gsm.nextLevel();
            loadNext = false;
        }
        else if(loadPrev){
            gsm.prevLevel();
            loadPrev = false;
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

        for (Entity e : enemies) {
            e.draw(g2d);

        }

        for(Usable u : usables){
            u.draw(g2d);
        }

        //player.getFlashLight().draw(g2d);
        player.draw(g2d);

        //lightMap.draw(g2d);

        if(foregroundAlpha > 0.0f){
            g2d.setColor(new Color(0.0f, 0.0f, 0.0f, foregroundAlpha));
            g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);
        }


        // Draw
        popupQ.draw(g2d);
        hud.draw(g2d);
        menu.draw(g2d);
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
        switch (k) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_W:
                player.setClimbingUp(true);
                break;
            case KeyEvent.VK_S:
                player.setClimbDown(true);
                break;
            case KeyEvent.VK_E:
                if(popupQ.isDisplaying()){
                    popupQ.nextPopup();
                }else {
                    for (Usable u : usables) {
                        if (u.canUse()) {
                            u.use();
                        }
                    }
                }

                break;
            case KeyEvent.VK_O:
                loadPrev = true;
                break;
            case KeyEvent.VK_P:
                loadNext = true;
                break;
            case KeyEvent.VK_SPACE:
                player.setJumping(true);
                break;
            case KeyEvent.VK_R:
                player.respawn();
                break;
            case KeyEvent.VK_T:
                player.toggleFlashlight();
                break;
            case KeyEvent.VK_ESCAPE:
                menu.toggle();
                break;
            case KeyEvent.VK_F3:
                info.toggle();
                break;
        }
    }


    @Override
    public void keyReleased(final int k) {
        if (k == KeyEvent.VK_A) player.setLeft(false);
        if (k == KeyEvent.VK_D) player.setRight(false);
        if (k == KeyEvent.VK_W) player.setClimbingUp(false);
        if (k == KeyEvent.VK_S) player.setClimbDown(false);
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

    public boolean isInited() {
        return inited;
    }

    public void setLoadNext(boolean loadNext) {
        this.loadNext = loadNext;
        backgroundSound.stop();
    }

    public void setLoadPrev(boolean loadPrev) {
        this.loadPrev = loadPrev;
        backgroundSound.stop();
    }
}
