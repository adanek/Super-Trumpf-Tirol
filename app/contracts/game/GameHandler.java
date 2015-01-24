package contracts.game;

public interface GameHandler {

    /**
     * Creates a new single player game
     * 
     * @param playerId
     *            the ID of the player
     * 
     * @return the id of the new game
     */
    public String createNewGame(String playerId);

    /**
     * Returns the current state of the game
     * 
     * @param gameId
     *            the id of the game
     * @param playerId
     *            the ID of the player which made the request
     * 
     * @return the current game state
     */
    public GameStatus getGameStatus(String gameId, String playerId);

    /**
     * Returns the current card of the player *
     * 
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player*
     * @return the current card of the player
     */
    public ICard getCard(String gameId, String playerId);

    /**
     * Returns the current card of the competitor to compare it with your own
     * after the choice is made Only possible between GameState WaitForCommit *
     * 
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player
     * @return the current card of the competitor
     */
    public ICard getCardFromCompetitor(String gameId, String playerId);

    /**
     * Submit the chosen category Only available in state WaitForYourChoice
     *
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player
     * @param categoryID
     *            The Id of the choosen category
     */
    public void makeMove(String gameId, String playerId, int categoryID);

    /**
     * Finishes a round.
     *
     * Both players have to commit each round. This gives them the opportunity
     * to look at theirs competitors card to see why the won or lose
     * 
     * Only available for the state WaitForCommit
     * 
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player
     */
    public void commitRound(String gameId, String playerId);

    /**
     * The passiv player has to commit his card before the evaluation of the
     * round can be done
     *
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player
     */
    public void commitCard(String gameId, String playerId);


    /**
     * Aborts the game.
     * @param gameId
     * @param playerId
     */
    public void abortGame(String gameId, String playerId);
}
