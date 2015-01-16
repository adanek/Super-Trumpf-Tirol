package contracts.game;

public interface GameStatus {
    
    // unique id of the game
    UUID getGameID();
    
    // unique id of the player
    UUID getPlayer();
    
    // current state of the game
    GameState getCurrentState();
    
    // number of cards in your stack
    int getCardCount();
    
    // number of cards in your competitors stack
    int getCardCountCompetitor();
}
