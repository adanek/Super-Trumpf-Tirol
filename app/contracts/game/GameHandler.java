package contracts.game;


import java.util.UUID;

public interface GameHandler {

    /**
     * Creates a new single player game
     * @param playerId the ID of the player
     */
    public void createNewGame(UUID playerId);

    /**
     * Returns the current state of the game 
     * 
     * @param gameId the id of the game
     * @param playerId the ID of the player which made the request               
     * 
     * @return the current game state
     */
    public GameStatus getGameStatus(UUID gameId, UUID playerId);

    /**
     * Returns the current card of the player
     * *
     * @param gameId The ID of the current game
     * @param playerId The ID of the passive player* 
     * @return the current card of the player
     */
    public Card getCard(UUID gameId, UUID playerId);

    /**
     * Returns the current card of the competitor to compare it with your own after the choice is made
     * Only possible between GameState WaitForCommit
     * * 
     * @param gameId The ID of the current game
     * @param playerId The ID of the passive player
     * @return the current card of the competitor
     */
    public Card getCardFromCompetitor(UUID gameId, UUID playerId);

    /**
     * Submit the chosen category
     * Only available in state WaitForYourChoice 
     *
     * @param gameId The ID of the current game
     * @param playerId The ID of the passive player
     * @param categoryID The Id of the choosen category
     */
    public void makeMove(UUID gameId, UUID playerId, CardCategory categoryID);

    /**
     * Finishes a round.
     *
     * Both players have to commit each round.
     * This gives them the opportunity to look at theirs competitors card
     * to see why the won or lose 
     * 
     * Only available for the state WaitForCommit
     *  
     * @param gameId The ID of the current game
     * @param playerId The ID of the passive player
     */
    public void commitRound(UUID gameId, UUID playerId);

    /**
     * The passiv player has to commit his card before the evaluation of the round can be done
     *
     * @param gameId The ID of the current game
     * @param playerId The ID of the passive player
     */
    public void commitCard(UUID gameId, UUID playerId);
}
