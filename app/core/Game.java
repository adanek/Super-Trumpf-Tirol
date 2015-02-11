package core;

import contracts.game.GameState;
import contracts.game.ICard;
import contracts.game.RoundState;

import java.util.Observable;
import java.util.Queue;

public class Game extends Observable implements contracts.game.IGame {

    // Fields
    private final String gid;
    private String p1Id;
    private String p2Id;
    private final Queue<Integer> player1Cards;
    private final Queue<Integer> player2Cards;
    private Round round;
    private boolean aborted;
    private String abortedBy;
    private Boolean finished;

    // Constructor

    /**
     * Creates an new instance of a game
     *
     * @param gameID       the id of the game
     * @param player1ID    the id of the first player
     * @param player2ID    the id of the second player
     * @param player1Cards the cards of the first player
     * @param player2Cards the cards of the second player
     */
    public Game(String gameID, String player1ID, String player2ID, Queue<Integer> player1Cards, Queue<Integer> player2Cards) {
        this.gid = gameID;
        this.p1Id = player1ID;
        this.p2Id = player2ID;
        this.player1Cards = player1Cards;
        this.player2Cards = player2Cards;
        this.round = new Round(1, getRandomPlayerId());
        this.aborted = false;
        this.abortedBy = null;
        this.finished = false;
        this.setChanged();
    }

    // Properties

    /**
     * Returns the id of the current game
     *
     * @return the id of the current game
     */
    @Override
    public String getGameID() {
        return gid;
    }

    /**
     * Returns the id of the active player in the current round
     * Needed for testing*
     * * @return the id of the active player in the current round
     */
    @Override
    public String getActivePlayer() {
        return round.getActivePlayer();
    }

    /**
     * Returns the id of the passive player in the current round
     * Needed for testing
     *
     * @return the id of the passive player in the current round
     */
    @Override
    public String getPassivePlayer() {
        return getCompetitorId(getActivePlayer());
    }

    /**
     * Returns the index of the current card form the player
     *
     * @param playerId the id of the player
     * @return the index of the current card from the player
     */
    @Override
    public int getCardId(String playerId) {

        validatePlayerId(playerId);
        Queue<Integer> cards;

        if (playerId.equals(p1Id)) {
            cards = player1Cards;
        } else {
            cards = player2Cards;
        }

        return cards.peek();
    }

    /**
     * Returns the index of the current card from the competitor
     *
     * @param playerId the id of the player
     * @return the index of the current card from the competitor
     */
    @Override
    public int getCompetitorCardId(String playerId) {

        validatePlayerId(playerId);
        int cardId;

        if (playerId.equals(p1Id)) {
            cardId = getCardId(p2Id);
        } else {
            cardId = getCardId(p1Id);
        }

        return cardId;
    }

    /**
     * Returns the current state of the game from the view of the player
     *
     * @param playerId the id of the player
     * @return the current state of the game from the view of the player
     */
    @Override
    public GameStatus getGameStatus(String playerId) {

        validatePlayerId(playerId);
        GameStatus state = new GameStatus();

        state.setGameState(getGameState(playerId));
        state.setPlayerCardCount(getPlayerCardCount(playerId));
        state.setCompetitorCardCount(getCompetitorCardCount(playerId));
        state.setRound(round.getNumber());
        state.setRoundState(getRoundState(playerId));
        state.setChoosenCategoryId(round.getChoosenCategory());

        return state;
    }

    /**
     * Chooses the given category for the current round*
     *
     * @param playerId the id of the player which wants to make the choice
     * @param category the name of the choosen category
     * @throws core.UnknownPlayerException        if the player is neither player1 or player2
     * @throws java.lang.IllegalArgumentException if the category is not valid
     * @throws java.lang.IllegalStateException    if the player is not active in this round
     * @throws java.lang.IllegalStateException    if the current state is not WaitForYourChoice
     */
    @Override
    public void chooseCategory(String playerId, Integer category) {

        validatePlayerId(playerId);
        validateCategory(category);

        if (!round.getActivePlayer().equals(playerId)) {
            throw new IllegalStateException("Only the active player can choose a category");
        }

        if (!getGameState(playerId).equals(GameState.WaitForYourChoice)) {
            throw new IllegalStateException("The category can only be choosen in state WaitForYourChoice");
        }

        this.round.setChoosenCategory(category);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Commits the card of the passive player in the current round
     *
     * @param playerId the id of the player which wants to commit his card
     * @throws core.UnknownPlayerException     if the player is neither player1 or player2
     * @throws java.lang.IllegalStateException if the player is not passive in this round
     *                                         *
     */
    @Override
    public void commitCard(String playerId) {

        validatePlayerId(playerId);

        if (round.getActivePlayer().equals(playerId)) {
            throw new IllegalStateException("Only the passive player can commit his card.");
        }

        if (!getGameState(playerId).equals(GameState.WaitForCommitCard)) {
            throw new IllegalStateException("The card of the passive player can only be committed in state WaitForCommitCard");
        }

        round.setPassivPlayerCommitedCard();

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the winner of the current round
     *
     * @param winnerId the id of the player which has won this round
     */
    @Override
    public void setWinner(String winnerId) {

        validatePlayerId(winnerId);
        this.round.setWinner(winnerId);
    }

    /**
     * Sets the commitRoundFlag for the given playerId
     *
     * @param playerId the id of the player who wants to commit the round
     */
    @Override
    public void commitRound(String playerId) {

        validatePlayerId(playerId);

        if (getGameState(playerId) != GameState.WaitForCommitRound) {
            throw new IllegalStateException("Committing a round is only possible in state WaitForCommitRound");
        }

        if (playerId.equals(getActivePlayer())) {
            round.setActivePlayerHasCommitedRound();
        } else {
            round.setPassivePlayerHasCommitedRound();
        }

        tryFinishRound();
    }

    /**
     * Returns true if all necessary information is collected for the evaluation, otherwise false
     *
     * @return true if all necessary information is collected for the evaluation, otherwise false
     */
    @Override
    public Boolean canEvaluateRound() {
        return (round.getChoosenCategory() >= 0) && round.hasPassivPlayerCommitedCard();
    }

    /**
     * Sets the given player active in the current round
     * Just for testing
     *
     * @param playerId the player which should be active
     */
    public void setActivePlayer(String playerId) {
        if (round.getActivePlayer().equals(playerId))
            return;

        this.round = new Round(round.getNumber(), playerId);

    }

    /**
     * Aborts the game.
     *
     * @param playerId the id of the player who wants to abort
     */
    public void setAborted(String playerId) {

        validatePlayerId(playerId);

        if (!aborted) {
            this.abortedBy = playerId;
            this.aborted = true;

            this.setChanged();
            this.notifyObservers();
        } else {
            this.finished = true;
        }
    }

    public Boolean isFinished() {
        return this.finished;

    }

    // Helpers

    /**
     * Validates that the give category is valid *
     *
     * @param category the category to check
     */
    private void validateCategory(int category) {
        if (category < 0)
            throw new IllegalArgumentException("Choosen category can not be less than zero.");
    }

    /**
     * Returns the current state of the game from the view of the player
     *
     * @param playerId the id of the player
     * @return the current state of the game from the view of the player
     */
    private GameState getGameState(String playerId) {

        GameState state;

        if (this.aborted) {
            state = GameState.Aborted;
        } else {
            Boolean isActive = round.getActivePlayer().equals(playerId);

            if (isActive) {
                if (getCompetitorCardCount(playerId) == 0) {
                    state = GameState.Won;
                } else if (round.getChoosenCategory() == -1) {
                    state = GameState.WaitForYourChoice;
                } else if (!round.hasPassivPlayerCommitedCard()) {
                    state = GameState.WaitForOtherPlayer;
                } else if (!round.hasActivePlayerHasCommitedRound()) {
                    state = GameState.WaitForCommitRound;
                } else {
                    state = GameState.WaitForOtherPlayer;
                }
            } else {
                if (getPlayerCardCount(playerId) == 0) {
                    state = GameState.Lost;
                } else if (!round.hasPassivPlayerCommitedCard()) {
                    state = GameState.WaitForCommitCard;
                } else if (round.getChoosenCategory() == -1) {
                    state = GameState.WaitForOtherPlayer;
                } else if (!round.hasPassivePlayerHasCommitedRound()) {
                    state = GameState.WaitForCommitRound;
                } else {
                    state = GameState.WaitForOtherPlayer;
                }
            }
        }

        return state;
    }

    /**
     * Returns the current state of the round form the view of the player
     *
     * @param playerId the id of the player
     * @return the current state of the round form the view of the player
     */
    private RoundState getRoundState(String playerId) {

        RoundState state;
        String winner = round.getWinner();

        if (winner == null) {
            state = RoundState.OUTSTANDING;
        } else if (winner.equals(playerId)) {
            state = RoundState.WON;
        } else if (winner.equals(getCompetitorId(playerId))) {
            state = RoundState.LOST;
        } else {
            state = RoundState.DRAWN;
        }

        return state;
    }

    /**
     * Returns the card count of the competitor
     *
     * @param playerId the id of the player
     * @return the card count of the competitor
     */
    private int getCompetitorCardCount(String playerId) {

        return getPlayerCardCount(getCompetitorId(playerId));
    }

    /**
     * Returns the card count of the player
     *
     * @param playerId the id of the player
     * @return the card count of the player
     */
    private int getPlayerCardCount(String playerId) {

        int count;

        if (playerId.equals(p1Id)) {
            count = player1Cards.size();
        } else if (playerId.equals(p2Id)) {
            count = player2Cards.size();
        } else
            throw new UnknownPlayerException();

        return count;
    }

    /**
     * Returns the id of the players competitor
     *
     * @param playerId the id of the player
     * @return the id of the players competitor
     */
    private String getCompetitorId(String playerId) {

        String id;

        if (playerId.equals(p1Id)) {
            id = p2Id;
        } else if (playerId.equals(p2Id)) {
            id = p1Id;
        } else {
            throw new UnknownPlayerException();
        }

        return id;
    }

    /**
     * Validates the the given id matches either player1 or player2
     *
     * @param playerId the id to check
     */
    private void validatePlayerId(String playerId) {

        if (playerId == null) {
            throw new NullPointerException("PlayerId must not be null");
        }

        if (!playerId.equals(p1Id) && !playerId.equals(p2Id))
            throw new UnknownPlayerException();
    }

    /**
     * Returns the id of on of the players (randomly choosen)
     *
     * @return an id of one of the players
     */
    private String getRandomPlayerId() {

        String id;
        if (Math.random() >= 0.5) {
            id = p2Id;
        } else {
            id = p1Id;
        }
        return id;
    }

    /**
     * Finishes the current round if possible and starts the next one
     */
    private void tryFinishRound() {

        if (round.hasActivePlayerHasCommitedRound() && round.hasPassivePlayerHasCommitedRound()) {

            Queue<Integer> cardsWinner;
            Queue<Integer> cardsLoser;

            if (round.getWinner().equals(p1Id)) {
                cardsWinner = player1Cards;
                cardsLoser = player2Cards;
            } else {
                cardsWinner = player2Cards;
                cardsLoser = player1Cards;
            }

            cardsWinner.add(cardsWinner.remove());
            cardsWinner.add(cardsLoser.remove());

            this.round = new Round(round.getNumber() + 1, round.getWinner());
        }
    }
}
