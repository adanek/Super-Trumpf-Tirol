package model;
import data.Card;
import play.db.ebean.Model;
import contracts.game.GameState;

import contracts.game.GameStatus;

import java.util.Queue;
import java.util.UUID;

/**
 * Created by Mark on 19.01.2015.
 */
public class Game extends Model implements GameStatus{

    private UUID GameID;
    private UUID Player1ID;
    private UUID Player2ID;

    private GameState status;
    private Queue<Card> player1Cards;
    private Queue<Card> player2Cards;
    private int round;
    private Boolean player1sMove;

    public Game(UUID gameID, UUID player1ID, UUID player2ID, Queue<Card> player1Cards, Queue<Card> player2Cards) {
        GameID = gameID;
        Player1ID = player1ID;
        Player2ID = player2ID;
        // this.status = status; TODO
        this.player1Cards = player1Cards;
        this.player2Cards = player2Cards;
        this.round = 1;
        this.player1sMove = true;
    }

    @Override
    public String getMessage() {
        return status.toString();
    }

    @Override
    public int getRound() {
        return round;
    }
    /** Seen from player1`s perspective. */
    @Override
    public int getCardCount() {
        return player1Cards.size();
    }

    /** Seen from player2`s perspective. */
    @Override
    public int getCardCountCompetitor() {
        return player2Cards.size();
    }

    public UUID getPlayer1ID() {
        return Player1ID;
    }

    public UUID getPlayer2ID() {
        return Player2ID;
    }
}
