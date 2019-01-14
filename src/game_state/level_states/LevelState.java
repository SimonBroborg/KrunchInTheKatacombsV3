package game_state.level_states;


import game_state.ALevelState;

/**
 *  Loads a level from a map file
 */
public class LevelState extends ALevelState
{

    /**
     * The constructor takes in a string with the path to the map file
     *
     * @param mapPath Path to the levels map file
     */
    public LevelState(String mapPath) {
        super(mapPath);
    }
}
