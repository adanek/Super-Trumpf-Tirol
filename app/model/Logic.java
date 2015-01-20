package model;

import java.util.HashMap;
import java.util.UUID;

import play.db.ebean.Model;
import scala.Array;
import contracts.game.GameHandler;
import contracts.game.GameStatus;
import contracts.game.ICard;

/**
 * Created by Mark on 19.01.2015.
 */
public class Logic extends Model implements GameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private Array<ICard> cards;

    @Override
    public String createNewGame(String playerId) {
	return null;
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
