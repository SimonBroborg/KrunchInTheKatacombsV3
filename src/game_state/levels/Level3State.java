package game_state.levels;


import game_state.ALevelState;

/**
 *  Loads a level from a map file
 */
public class Level3State extends ALevelState
{

    /**
     * The constructor takes in a string with the path to the map file
     */
    public Level3State() {
        super("resources/Maps/map3.tmx");
    }

    @Override public void playerOnPortal() {
    }
}
