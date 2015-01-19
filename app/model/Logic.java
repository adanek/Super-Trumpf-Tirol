package model;

import contracts.game.Card;
import contracts.game.GameHandler;
import contracts.game.GameStatus;
import play.db.ebean.Model;
import scala.Array;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mark on 19.01.2015.
 */
public class Logic extends Model implements GameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private Array<Card> cards;


    @Override
    public String createNewGame(String playerId) {
        return null;
    }

    @Override
    public GameStatus getGameStatus(String gameId, String playerId) {
        return null;
    }

    @Override
    public Card getCard(String gameId, String playerId) {
        return null;
    }

    @Override
    public Card getCardFromCompetitor(String gameId, String playerId) {
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
