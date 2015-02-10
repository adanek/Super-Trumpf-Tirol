package core;
import contracts.game.GameState;

import java.util.Observable;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by Mark on 19.01.2015.
 */
public class Game extends Observable{

    private UUID GameID;
    private UUID Player1ID;
    private UUID Player2ID;
    private Queue<Integer> player1Cards;
    private Queue<Integer> player2Cards;

    private GameStatus status;
    private Boolean player1sMove;

    public Game(UUID gameID, UUID player1ID, UUID player2ID, Queue<Integer> player1Cards, Queue<Integer> player2Cards) {
        GameID = gameID;
        Player1ID = player1ID;
        Player2ID = player2ID;
        this.status = new GameStatus(1, 26, 26, GameState.WaitForYourChoice);
        this.player1Cards = player1Cards;
        this.player2Cards = player2Cards;
        this.player1sMove = true;
    }

    /**
    Moves the first card of each player to player1's queue.
     */
    public void player1win(){
        player1Cards.add(player1Cards.remove());
        player1Cards.add(player2Cards.remove());
        status.updatePlayer1Cards(player1Cards.size());
        status.updatePlayer2Cards(player2Cards.size());
        player1sMove = true;
        if(player1Cards.size() == 52){
            status.updateStatus(GameState.Won);
        }else {
            status.updateStatus(GameState.WaitForYourChoice);
        }
    }

    /**
     Moves the first card of each player to player2's queue.
     */
    public void player2win(){
        player2Cards.add(player2Cards.remove());
        player2Cards.add(player1Cards.remove());
        status.updatePlayer1Cards(player1Cards.size());
        status.updatePlayer2Cards(player2Cards.size());
        player1sMove = false;
        if(player2Cards.size() == 52){
            status.updateStatus(GameState.Lose);
        }else {
            status.updateStatus(GameState.WaitForOtherPlayer);
        }
    }

    public void draw(){
        player1Cards.add(player1Cards.remove());
        player2Cards.add(player2Cards.remove());
        status.updatePlayer1Cards(player1Cards.size());
        status.updatePlayer2Cards(player2Cards.size());
        if(player1Cards.size() == 52){
            status.updateStatus(GameState.Won);
        }else {
            status.updateStatus(GameState.WaitForYourChoice);
        }
    }

    public GameStatus getStatus() {
        return status;
    }

    public Integer getPlayer1Card() {
        return player1Cards.peek();
    }

    public Integer getPlayer2Card() {
        return player2Cards.peek();
    }

    public UUID getPlayer1ID() {
        return Player1ID;
    }

    public UUID getPlayer2ID() {
        return Player2ID;
    }

    public Boolean getPlayer1sMove() {
        return player1sMove;
    }

    public void setPlayer1sMove(Boolean player1sMove) {
        this.player1sMove = player1sMove;
    }

    public UUID getGameID() {
        return GameID;
    }
}
