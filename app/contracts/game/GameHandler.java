package contracts.game;

public interface GameHandler {
    
    public void createNewGame();
    public GameStatus getGameStatus();
    public Card getCard();
    public void makeMove();
    public void commitRound();
}
