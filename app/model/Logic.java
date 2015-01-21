package model;

import java.util.*;

import play.db.ebean.Model;
import scala.Array;
import contracts.game.GameHandler;
import contracts.game.GameStatus;
import contracts.game.ICard;

/**
 * Created by Mark on 19.01.2015.
 */
public class Logic implements GameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private Array<ICard> cards;

    // TODO
    public Logic(Array<ICard> cards) {
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
    public GameStatus getGameStatus(String gameId, String playerId) {
	return null;
    }

    @Override
    public ICard getCard(String gameId, String playerId) {
	return null;
    }

    @Override
    public ICard getCardFromCompetitor(String gameId, String playerId) {
	return null;
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {

    }

    @Override
    public void commitRound(String gameId, String playerId) {

    }

    @Override
    public void commitCard(String gameId, String playerId) {

    }
}
