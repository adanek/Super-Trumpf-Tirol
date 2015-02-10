package core;

import contracts.game.GameState;
import contracts.game.RoundState;

import java.util.Observable;
import java.util.Queue;

public class Game extends Observable {

    // Fields
    private String gid;
    private String p1Id;
    private String p2Id;
    private Queue<Integer> player1Cards;
    private Queue<Integer> player2Cards;
    private Round round;
   
    // Constructor
    /**
     * Creates an new instance of a game
      * @param gameID the id of the game
     * @param player1ID the id of the first player
     * @param player2ID the id of the second player
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
    }

    // Properties
    /**
     * Returns the id of the current game
     *
     * @return the id of the current game
     */
    public String getGameID() {
        return gid;
    }

    /**
     * Returns the index of the current card form the player
     *
     * @param playerId the id of the player
     * @return the index of the current card from the player
     */
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
    public GameStatus getGameStatus(String playerId) {

        validatePlayerId(playerId);
        GameStatus state = new GameStatus();

        state.setGameState(getGameState(playerId));
        state.setPlayerCardCount(getPlayerCardCount(playerId));
        state.setCompetitorCardCount(getCompetitorCardCount(playerId));
        state.setRound(round.getNumber());
        state.setRoundState(getRoundState(playerId));
        state.setChoosenCategory(round.getChoosenCategory());

        return state;
    }

    /**
     * Returns the current state of the game from the view of the player
     * @param playerId the id of the player
     * @return the current state of the game from the view of the player
     */
    private GameState getGameState(String playerId) {
        
        GameState state = null;
        Boolean isActive = round.getActivePlayer().equals(playerId);
        
        if(isActive){
            if(round.getChoosenCategory()==null){
                state = GameState.WaitForYourChoice;
            }
            else if(!round.hasPassivPlayerCommitedCard()){
                state = GameState.WaitForOtherPlayer;
            } else if(!round.hasActivePlayerHasCommitedRound()){
                state = GameState.WaitForCommitRound;
            } else {
                state = GameState.WaitForOtherPlayer;
            }            
        } else {
            if(!round.hasPassivPlayerCommitedCard()){
                state = GameState.WaitForCommitCard;
            } else if(round.getChoosenCategory() == null){
                state = GameState.WaitForOtherPlayer;
            } else if(!round.hasPassivePlayerHasCommitedRound()){
                state = GameState.WaitForCommitRound;
            } else {
                state = GameState.WaitForOtherPlayer;
            }
        }
        
        return state;
    }

    /**
     * Returns the current state of the round form the view of the player 
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
     * @param playerId the id of the player
     * @return the card count of the competitor
     */
    private int getCompetitorCardCount(String playerId) {

        return getPlayerCardCount(getCompetitorId(playerId));
    }

    /**
     * Returns the card count of the player
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
     * @param playerId the id to check
     */
    private void validatePlayerId(String playerId) {

        if (!playerId.equals(p1Id) && !playerId.equals(p2Id))
            throw new UnknownPlayerException();
    }

    /**
     * Returns the id of on of the players (randomly choosen)
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
}
