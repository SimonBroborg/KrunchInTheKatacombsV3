package game_state;

import entity.Entity;
import entity.LightSource;
import entity.Usable.Usable;
import entity.movables.*;
import entity.tile_types.Torch;
import main.GameComponent;
import main.GameStateManager;
import main.HUD;
import main.InfoDisplay;
import map.*;
import menu.Menu;
import java.awt.geom.Area;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
    protected List<Torch> torches;

    protected Area lightMap;

    // Path to the map file
    protected String mapPath;

    protected boolean restart;

    protected Background bg = null;

    protected GameStateManager gsm = null;

    protected HUD hud;

    protected InfoDisplay info;

    protected Rectangle foreground;
    protected float foregroundAlpha;

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
        player = tm.getPlayer();
        enemies = tm.getEnemies();

        lightMap = new Area(
                new Rectangle(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT));

        hud = new HUD(player);
        info = new InfoDisplay(player);

        inited = true;
    }

    /**
     * Updates everything on the screen
     *
     * @param mousePos the position of the mouse
     */
    public void update(Point mousePos) {
        if(foregroundAlpha > 0.0f){
            foregroundAlpha -= 0.01;
        }
        menu.update(mousePos);
        // The game pauses if the menu is open, aka nothing updates
        if (!menu.isOpen()) {

            tm.update(player);
            bg.update();

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

        player.getFlashLight().draw(g2d);
        player.draw(g2d);

        drawDark(g2d);


        if(foregroundAlpha > 0.0f){
            g2d.setColor(new Color(0.0f, 0.0f, 0.0f, foregroundAlpha));
            g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);
        }


        // Draw
        hud.draw(g2d);
        menu.draw(g2d);
        info.draw(g2d);

        g2d.dispose();
    }

    private void drawDark(Graphics2D g2d){
        lightMap = new Area(
                new Rectangle(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT));

        float darknessAlpha = 0.95f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darknessAlpha));

        for(Torch t : torches){
            if(t.isLit()) {
                LightSource ls = t.getLightSource();
                lightMap.subtract(ls.getLight());
                ls.draw(g2d);
            }
        }
        player.getFlashLight().setLightMap(lightMap);
        lightMap.subtract(player.getFlashLight().getLightBulb());

        g2d.setColor(Color.BLACK);

        g2d.setClip(lightMap);
        g2d.fillRect(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT);

        g2d.setClip(null);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

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
                for(Usable u : usables){
                    if(u.canUse()){
                        u.use();
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
}
