package core.ai;

import config.ServiceLocator;
import contracts.game.GameState;
import contracts.game.IGame;
import contracts.game.IGameHandler;
import core.Game;
import core.GameStatus;

import java.util.Observable;
import java.util.Observer;

public abstract class PlayerAI implements Observer {

    protected String pid;
    protected IGameHandler gh;

    public PlayerAI(String id) {
        this.pid = id;
        this.gh = ServiceLocator.getGameHandler();
    }

    public String getId() {
        return this.pid;
    }

    @Override
    public void update(Observable o, Object arg) {
        Game game = (Game) o;
        GameStatus status = game.getGameStatus(pid);
        GameState state = GameState.valueOf(status.getGameState());
        
        switch (state) {
            case WaitForYourChoice:
                makeChoice(game);
                break;
            case WaitForCommitCard:
                commitCard(game);
                break;
            case WaitForCommitRound:
                commitRound(game);
                break;
            case Aborted:
            case Won:
            case Lost:
                unregister(game);
            default:
                break;
        }
    }

    private void unregister(Game game) {
        game.deleteObserver(this);
        gh.abortGame(game.getGameID(), pid);
    }

    private void commitRound(IGame game) {
        gh.commitRound(game.getGameID(), pid);
    }

    private void commitCard(IGame game) {
      gh.commitCard(game.getGameID(), pid);
    }

    protected abstract void makeChoice(IGame game);
}
