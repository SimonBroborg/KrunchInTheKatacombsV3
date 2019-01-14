package game_state.levels;


import game_state.ALevelState;
import game_state.State;

/**
 *  Loads a level from a map file
 */
public class Level2State extends ALevelState
{

    /**
     * The constructor takes in a string with the path to the map file
     */
    public Level2State() {
        super("resources/Maps/map2.tmx");
    }

    @Override public void playerOnPortal() {
        gsm.setState(State.LEVEL_3);
    }
}
