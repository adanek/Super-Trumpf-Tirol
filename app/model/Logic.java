package model;

import java.util.*;

import play.db.ebean.Model;
import scala.Array;
import contracts.game.GameHandler;
import contracts.game.GameStatus;
import contracts.game.ICard;
import contracts.game.GameState;

/**
 * Created by Mark on 19.01.2015.
 */
public class Logic implements GameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private data.Card[] cards;

    // TODO
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
        return game.getStatus();
    }

    @Override
    public data.Card getCard(String gameId, String playerId) {
	Game game = map.get(UUID.fromString(gameId));
        if (UUID.fromString(playerId) == game.getPlayer1ID()){
            return cards[game.getPlayer1Card()];
        }else{
            return null;
        }
    }

    @Override
    public data.Card getCardFromCompetitor(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        if (UUID.fromString(playerId) == game.getPlayer1ID()){
            return cards[game.getPlayer2Card()];
        }else{
            return null;
        }
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {
        Game game = map.get(UUID.fromString(gameId));
        if(game.getPlayer1sMove() == true){
            int player1Value = cards[game.getPlayer1Card()].getRankingArray()[categoryID];
            int player2Value = cards[game.getPlayer2Card()].getRankingArray()[categoryID];
            // Smaller rank means better position
            if(player1Value > player2Value){
                game.setPlayer1sMove(false);
            }else if(player1Value < player2Value){
                game.setPlayer1sMove(true);
            }
            game.getStatus().updateStatus(GameState.WaitForCommit);
        }
    }

    @Override
    public void commitRound(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        if(game.getPlayer1sMove() == true){
            game.player1win();
        }else{
            game.player2win();
        }
    }

    @Override
    public void commitCard(String gameId, String playerId) {

    }
}
