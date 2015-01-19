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


    //TODO
    @Override
    public UUID createNewGame(UUID playerId) {
        return null;
    }

    @Override
    public GameStatus getGameStatus(UUID gameId, UUID playerId) {
        return null;
    }

    @Override
    public Card getCard(UUID gameId, UUID playerId) {
        return null;
    }

    @Override
    public Card getCardFromCompetitor(UUID gameId, UUID playerId) {
        return null;
    }

    @Override
    public void makeMove(UUID gameId, UUID playerId, int categoryID) {

    }

    @Override
    public void commitRound(UUID gameId, UUID playerId) {

    }

    @Override
    public void commitCard(UUID gameId, UUID playerId) {

    }
}
