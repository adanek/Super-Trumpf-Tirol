package core;

import contracts.game.GameState;
import contracts.game.RoundState;

public class GameStatus implements contracts.game.GameStatus{
    
    // Fields
    private int round;
    private int playerCardCount;
    private int competitorCardCount;
    private GameState gameState;
    private RoundState roundState;
    private int choosenCategory;
    
    // Constructor
    public GameStatus() {
        this.round = 0;
        this.playerCardCount = 0;
        this.competitorCardCount=0;
        this.gameState = GameState.WaitForYourChoice;
        this.roundState = RoundState.OUTSTANDING;
        this.choosenCategory = -1;
    }

    // Properties
    @Override
    public String getGameState() {
       return this.gameState.toString();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public RoundState getRoundState() {
        return this.roundState;
    }
    
    public void setRoundState(RoundState roundState) {
        this.roundState = roundState;
    }
    
    @Override
    public int getCardCount() {
        return this.playerCardCount;
    }

    public void setPlayerCardCount(int playerCardCount) {
        this.playerCardCount = playerCardCount;
    }
    
    @Override
    public int getCardCountCompetitor() {
        return this.competitorCardCount;
    }

    public void setCompetitorCardCount(int competitorCardCount) {
        this.competitorCardCount = competitorCardCount;
    }

    @Override
    public Integer getChoosenCategoryId() {
        return this.choosenCategory;
    }

    public void setChoosenCategoryId(Integer choosenCategory) {
        this.choosenCategory = choosenCategory;
    }
}
