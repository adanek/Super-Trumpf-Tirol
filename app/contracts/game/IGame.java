package contracts.game;

public interface IGame {
    String getGameID();

    String getActivePlayer();

    String getPassivePlayer();

    int getCardId(String playerId);

    int getCompetitorCardId(String playerId);

    core.GameStatus getGameStatus(String playerId);

    void chooseCategory(String playerId, Integer category);

    void commitCard(String playerId);
    
    void commitRound(String playerId);

    Boolean canEvaluateRound();

    void setWinner(String winnerId);
    
    void setAborted(String playerId);

    Boolean isFinished();
    
    void notifyObservers();
}
