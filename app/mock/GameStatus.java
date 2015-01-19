package mock;

import contracts.game.GameState;
import play.i18n.Messages;

import java.util.UUID;

public class GameStatus implements contracts.game.GameStatus {
    
    private GameState state;
    private int cardCount;
    private int competitorCardCount;
    private int round;

    /**
     * 
     * @param state
     * @param cardCount
     * @param competitorCardCount
     * @param round
     */
    public GameStatus(GameState state, int cardCount, int competitorCardCount, int round) {
        this.state = state;
        this.cardCount = cardCount;
        this.competitorCardCount = competitorCardCount;
        this.round = round;
    }

    @Override

    public String getMessage() {
        return Messages.get("game-state-" + this.state.toString());
    }

    @Override
    public int getRound() {
        return this.round;
    }

    @Override
    public int getCardCount() {
        return this.cardCount;
    }

    @Override
    public int getCardCountCompetitor() {
        return this.competitorCardCount;
    }
}
