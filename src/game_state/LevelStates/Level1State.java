package game_state.LevelStates;


import game_state.ALevelState;

/**
 *  Loads level 1 of the game
 */
public class Level1State extends ALevelState {

    /**
     * The constructor takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    public Level1State(String mapPath) {
        super(mapPath);
    }
}
