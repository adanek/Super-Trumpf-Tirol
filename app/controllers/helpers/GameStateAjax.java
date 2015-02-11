package controllers.helpers;

import contracts.game.GameStatus;
import static contracts.game.GameState.WaitForCommitRound;

/**
 * Created by adanek on 23.01.15.
 * Wrapper for GameState do add additonally properties* 
 */
public class GameStateAjax {
    
    public GameStateAjax(GameStatus state){

        this.State = state.getGameState();
        this.Round= state.getRound();
        this.RoundState=state.getRoundState().toString();
        this.CardCountPlayer=state.getCardCount();
        this.CardCountCompetitor = state.getCardCountCompetitor();
        this.ChoosenCategory=state.getChoosenCategoryId();

        if(state.getGameState().equals(WaitForCommitRound.toString()))
        {
            switch (state.getRoundState()) {
                case WON:
                    this.Message = play.i18n.Messages.get("game-state-commit-won");
                    break;
                case LOST:
                    this.Message = play.i18n.Messages.get("game-state-commit-lost");
                    break;
                case DRAWN:
                    this.Message = play.i18n.Messages.get("game-state-commit-drawn");
                    break;
            }
        }
        else {
            this.Message = play.i18n.Messages.get("game-state-" + state.getGameState());
        }
    }
    
    public String Message;
    public String State;
    public int Round;
    public String RoundState;
    public int CardCountPlayer;
    public int CardCountCompetitor;
    public int ChoosenCategory;
}
