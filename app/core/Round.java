package core;

/**
 * Represents one round in a game 
 */
public class Round {

    // Fields
    private int number;
    private String activePlayer;
    private String choosenCategory;
    private Boolean passivePlayerHasCommitedCard;
    private Boolean activePlayerHasCommitedRound;
    private Boolean passivePlayerHasCommitedRound;
    private String winner;
    
    // Constructors

    /**
     * Create an new instance of a round 
     * @param number the number of the round
     * @param activePlayer the active player in this round
     */
    public Round(int number, String activePlayer) {
        this.number = number;
        this.activePlayer = activePlayer;
        this.passivePlayerHasCommitedCard = false;
        this.activePlayerHasCommitedRound = false;
        this.passivePlayerHasCommitedRound= false;
    }

    // Properties

    /**
     * Returns the number of the round 
     * @return the number of the round
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the id of the active player in this round 
     * @return the id of the active player in this round
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * Returns the choosen category in this round 
     * @return the choosen category in this round
     */
    public String getChoosenCategory() {
        return choosenCategory;
    }

    /**
     * Sets the given category to the choosen one in this round. 
     * @param choosenCategory the choosen category.
     */
    public void setChoosenCategory(String choosenCategory) {
        this.choosenCategory = choosenCategory;
    }

    /**
     * Returns the winner of this round or null if the decision is not made yet
     * @return the winner of this round.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the winner of this round
     * @param winner the winner of this round.
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Boolean hasPassivPlayerCommitedCard() {
        return passivePlayerHasCommitedCard;
    }

    public void setPassivPlayerCommitedCard() {
        this.passivePlayerHasCommitedCard = true;
    }

    public Boolean hasActivePlayerHasCommitedRound() {
        return activePlayerHasCommitedRound;
    }

    public void setActivePlayerHasCommitedRound() {
        this.activePlayerHasCommitedRound = true;
    }
    
    public Boolean hasPassivePlayerHasCommitedRound() {
        return passivePlayerHasCommitedRound;
    }

    public void setPassivePlayerHasCommitedRound() {
        this.passivePlayerHasCommitedRound = true;
    }
}
