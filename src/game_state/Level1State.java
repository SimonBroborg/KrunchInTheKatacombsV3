package game_state;


import java.awt.event.MouseEvent;

/**
 *  Loads level 1 of the game
 */
public class Level1State extends ALevelState {
    public Level1State(final String mapPath) {
        super(mapPath);

        this.loadLevel();
    }

}
