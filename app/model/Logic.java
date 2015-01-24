package model;

import java.util.*;

import contracts.game.*;
import contracts.game.GameStatus;
import play.db.ebean.Model;
import scala.Array;

import java.util.Random;

/**
 * Created by Mark on 19.01.2015.
 */
public class Logic implements GameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private data.Card[] cards;

    /**
     * Constructor
     *
     * @param cards:
     * Array containing all cards
     */
    public Logic(data.Card[] cards) {
        this.map = new HashMap<UUID, Game>();
        this.cards = cards;
    }

    /**
     * Creates new game for a given player and returns the gameId as String
     */
    @Override
    public String createNewGame(String playerId) {
        final UUID gameID = UUID.randomUUID();
        final UUID player1Id = UUID.fromString(playerId);
        final UUID player2Id = UUID.randomUUID();

        /**
         * Creates and shuffles Array in order to get random cards
         */
        ArrayList<Integer> shuffleArray = new ArrayList<Integer>();
        for(int i = 0; i < 52; i++){
            shuffleArray.add(i);
        }
        Collections.shuffle(shuffleArray);

        Queue<Integer> player1Cards = new LinkedList<Integer>();
        for(int i = 0; i < 26; i++){
            player1Cards.add(shuffleArray.get(i));
        }

        Queue<Integer> player2Cards = new LinkedList<Integer>();
        for(int i = 26; i < 52; i++){
            player2Cards.add(shuffleArray.get(i));
        }
        Game game = new Game(gameID, player1Id, player2Id, player1Cards, player2Cards);
        map.put(gameID, game);

	return gameID.toString();
    }

    @Override
    public model.GameStatus getGameStatus(String gameId, String playerId) {
	    Game game = map.get(UUID.fromString(gameId));
        model.GameStatus status = game.getStatus();
        if(status.getChoosenCategory() != null){
            if(game.getPlayer1sMove()){
                status.setRoundState(RoundState.WON);
            }
            else {
                status.setRoundState(RoundState.LOST);
            }
        }
        return status;
    }

    @Override
    public data.Card getCard(String gameId, String playerId) {
	Game game = map.get(UUID.fromString(gameId));
        if (UUID.fromString(playerId).equals(game.getPlayer1ID())){
            return cards[game.getPlayer1Card()];
        }else{
            return null;
        }
    }

    @Override
    public data.Card getCardFromCompetitor(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        if (UUID.fromString(playerId).equals(game.getPlayer1ID())){
            return cards[game.getPlayer2Card()];
        }else{
            return null;
        }
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {
        Game game = map.get(UUID.fromString(gameId));
        if(game.getPlayer1sMove() == true){
            compareCategories(game, categoryID);
        }
    }

    @Override
    public void commitRound(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        // If player1 moves next round, he won this round.
        if(game.getPlayer1sMove() == true){
            game.player1win();
        }else{
            game.player2win();
        }
        game.getStatus().updateRound();
    }

    @Override
    public void commitCard(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        Random rand = new Random();
        // Random number from 0 to 4
        int categoryID = rand.nextInt(5);
        compareCategories(game, categoryID);
    }

    public void compareCategories(Game game, int categoryID){
        // Get Ranks of the cards
        int player1Value = cards[game.getPlayer1Card()].getRankingArray()[categoryID];
        int player2Value = cards[game.getPlayer2Card()].getRankingArray()[categoryID];
        // Smaller rank means better position
        if(player1Value > player2Value){
            // If player1 loses, he does not move in the next round.
            game.setPlayer1sMove(false);
        }else if(player1Value < player2Value){
            // If player1 win, he moves next round.
            game.setPlayer1sMove(true);
        }
        game.getStatus().setChoosenCategory(cards[0].getCategories().get(categoryID).getName());
        game.getStatus().updateStatus(GameState.WaitForCommit);
    }
}
