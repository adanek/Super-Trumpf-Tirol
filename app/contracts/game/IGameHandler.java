package contracts.game;

/**
 * Main interface for the communication between UI and IGameHandler
 */
public interface IGameHandler {

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
     * Creates a new multiplayer game* 
     * @param player1Id the id of player1
     * @param player2Id the id of player2
     * @return the id of the new game
     */
    public String createNewGame(String player1Id, String player2Id);
    /**
     * Returns the current state of the game from the sight of the player
     * 
     * @param gameId
     *            the id of the game
     * @param playerId
     *            the ID of the player which made the request
     * 
     * @return an object representing the current game state
     */
    public GameStatus getGameStatus(String gameId, String playerId);

    /**
     * Returns the current card of the player 
     * 
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the player
     * @return an object representing the current ard of the player
     */
    public ICard getCard(String gameId, String playerId);

    /**
     * Returns the current card of the competitor to compare it with your own
     * after the choice is made 
     * This is only possible in GameState WaitForCommit
     * 
     * @param gameId
     *            The ID of the current game
     * @param playerId
     *            The ID of the passive player
     * @throws java.lang.IllegalStateException
     *            If state is not WaitForCommit
     * @return the current card of the competitor
     */
    public ICard getCardFromCompetitor(String gameId, String playerId) throws IllegalStateException;

    /**
     * Submit the chosen category 
     * 
     * Only available in state WaitForYourChoice
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
     * to look at theirs competitors card to see why the have won or lost
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
     * round can be done. This gives him the opportunity to take a look at his
     * card. Also this slows down the game speed intentionally.
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
    
    public void registerForMultiPlayerGame(String playerId);
    public String getMultiPlayerGameId(String playerId);
    
}
