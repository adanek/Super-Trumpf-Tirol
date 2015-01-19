package mock;

import contracts.game.*;
import contracts.game.Card;
import contracts.game.CardCategory;

import java.util.UUID;

public class GameHandler implements contracts.game.GameHandler {
    @Override
    public String createNewGame(String playerId) {
        return UUID.randomUUID().toString();
    }

    @Override
    public GameStatus getGameStatus(String gameId, String playerId) {
        return new GameStatus( GameState.WaitForYourChoice, 12, 40, 22);
    }

    @Override
    public Card getCard(String gameId, String playerId) {
        return new mock.Card();
    }

    @Override
    public Card getCardFromCompetitor(String gameId, String playerId) {
        return null;
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryId) {

    }

    @Override
    public void commitRound(String gameId, String playerId) {

    }

    @Override
    public void commitCard(String gameId, String playerId) {

    }
}