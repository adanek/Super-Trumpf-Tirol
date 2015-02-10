package core;

/**
 * Represents an exception if the given playerId 
 * is not one of the players in the current game
 */
public class UnknownPlayerException extends IllegalArgumentException {

    public UnknownPlayerException() {
        super("There exist no player with the given id in this game.");
    }
}
