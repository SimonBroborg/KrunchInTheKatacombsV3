package game_state;

import entity.Entity;
import entity.LightSource;
import entity.Usable.Chest;
import entity.Usable.InfoSign;
import entity.Usable.Usable;
import entity.movables.*;
import entity.tile_types.Torch;
import javafx.scene.effect.Blend;
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
public abstract class ALevelState implements IGameState {

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

    /**
     * The constructor takes in a string with the path to the map file
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
        usables = new ArrayList<>();
        torches = new ArrayList<>();

        menu = new Menu(10, 10, gsm);

        bg = new Background("resources/Backgrounds/background.jpg", 0);


        // Everything depending on the tile map must be created after this
        tm.load();

        usables = tm.getUsables();
        torches = tm.getTorches();
        usables.addAll(torches);

        lightMap = new Area(
                new Rectangle(0, 0, GameComponent.SCALED_WIDTH, GameComponent.SCALED_HEIGHT));

        player = new Player(100, 220, tm);

        hud = new HUD(player);
        info = new InfoDisplay(player);

        usables.add(new Chest(300, -700, tm));
        usables.add(new InfoSign("This is some text",500, -700, tm));

        //enemies.add(new HunterEnemy(300, -500, player,  tm));
        //enemies.add(new BasicEnemy2(500, -500, player,  tm));
    }

    private void reset(){
        enemies.clear();

        player = new Player(100, -200, tm);
        hud = new HUD(player);

        enemies.add(new HunterEnemy(300, -500, player,  tm));
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
            Iterator<Enemy> eIt = enemies.iterator();
            while (eIt.hasNext()) {
                Entity e = eIt.next();
                e.update();
                if (e.shouldRemove()) {
                    eIt.remove();
                }
            }

            // update the enemies
            Iterator<Usable> uIt = usables.iterator();
            while (uIt.hasNext()) {
                Usable u = uIt.next();
                u.update(player);

                if (u.shouldRemove()) {
                    uIt.remove();
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

        for (Entity e : enemies) {
            e.draw(g2d);
        }


        for(Usable u : usables){
            u.draw(g2d);
        }

        player.draw(g2d);

        drawDark(g2d);


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


        lightMap.subtract(player.getFlashLight().getLightBulb());
        player.getFlashLight().draw(g2d);

        for(Torch t : torches){
            if(t.isLit()) {
                LightSource ls = t.getLightSource();
                lightMap.subtract(ls.getLight());
                ls.draw(g2d);
            }
        }


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

}
