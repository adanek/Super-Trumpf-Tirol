package contracts.game;

import java.util.UUID;

public interface GameStatus {

    /**
     * The current state of the game
     * *
     * @return a string representing the current state of the game
     */
    String getGameState();

    /**
     * The current round of the game
     *
     * @return the current round of the game
     */
    int getRound();

    /**
     * *
     * @return the state of the current round.
     */
    RoundState getRoundState();
    
    // number of cards in your stack
    int getCardCount();
    
    // number of cards in your competitors stack
    int getCardCountCompetitor();

    /**
     * The choosen category of this round.*
     * @return Null if the category is not yet choosen.
     */
    String getChoosenCategory();
}
