package controllers.helpers;

import contracts.game.GameState;
import contracts.game.GameStatus;

/**
 * Created by adanek on 23.01.15.
 * Wrapper for GameState do add additonally properties* 
 */
public class GameStateAjax {
    
    public GameStateAjax(GameStatus state){
     
        this.State = state.getMessage();
        this.Message = play.i18n.Messages.get("game-state-"+ state.getMessage());
        this.Round= state.getRound();
        this.RoundState="won";
        this.CardCountPlayer=state.getCardCount();
        this.CardCountCompetitor = state.getCardCountCompetitor();
    }
    
    public String Message;
    public String State;
    public int Round;
    public String RoundState;
    public int CardCountPlayer;
    public int CardCountCompetitor;
    
    
}
