package contracts.game;

public interface GameHandler {

    /** 
     * Creates a new single player game 
     */
    public void createNewGame();

    /**
     * * 
     * @return the current game state
     */
    public GameStatus getGameStatus();

    /**
     * * 
     * @return the current card of the player
     */
    public Card getCard();

    /**
     * * Returns the current card of the competitor to compare it with your own after the choice is made
     * * Only possible between GameState WaitForCommit 
     * @return the current card of the competitor
     */
    public Card getCardFromCompetitor();

    /**
     * Submit the chosen category
     * Only available in state WaitForYourChoice 
     * @param category
     */
    public void makeMove(CardCategory category);

    /**
     * Finishes a round.
     *
     * Both players have to commit each round.
     * This gives them the opportunity to look at theirs competitors card
     * to see why the won or lose 
     * 
     * Only available for the state WaitForCommit
     */
    public void commitRound();
}
