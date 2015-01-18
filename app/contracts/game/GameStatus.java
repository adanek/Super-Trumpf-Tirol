package contracts.game;

import java.util.UUID;

public interface GameStatus {

    /**
     * The current state of the game
     * *
     * @return a string representring the current state of the game
     */
    String getMessage();

    /**
     * The current round of the game
     *
     * @return the current round of the game
     */
    int getRound();
    
    // number of cards in your stack
    int getCardCount();
    
    // number of cards in your competitors stack
    int getCardCountCompetitor();
}
