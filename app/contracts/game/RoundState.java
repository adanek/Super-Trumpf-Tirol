package contracts.game;

/**
 * Valid states of a round
 */
public enum RoundState {

    /**
     * Not yet decided* 
     */
    OUTSTANDING,

    /**
     * The player has won the round
     */
    WON,

    /**
     * The player has lost the round*
     */
    LOST, 
}
