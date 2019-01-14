package game_state;

import game_state.levels.*;

/**
 * Contains all the games states
 */
public enum State
{
    /**
     * Level 1 of the game
     */
    LEVEL_1(new Level1State()),
    /**
     * Level 2 of the game
     */
    LEVEL_2(new Level2State()),
    /**
     * Level 2 of the game
     */
    LEVEL_3(new Level3State()),

    /**
     * The games main menu
     */
    MAIN_MENU(new MainMenuState());

    private final GameState state;

    State(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }
}
