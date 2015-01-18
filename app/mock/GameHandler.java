package mock;

import contracts.game.*;
import contracts.game.Card;
import contracts.game.CardCategory;

import java.util.UUID;

public class GameHandler implements contracts.game.GameHandler {
    @Override
    public UUID createNewGame(UUID playerId) {
        return UUID.randomUUID();
    }

    @Override
    public GameStatus getGameStatus(UUID gameId, UUID playerId) {
        return new GameStatus( GameState.WaitForYourChoice, 12, 40, 22);
    }

    @Override
    public Card getCard(UUID gameId, UUID playerId) {
        return new mock.Card();
    }

    @Override
    public Card getCardFromCompetitor(UUID gameId, UUID playerId) {
        return null;
    }

    @Override
    public void makeMove(UUID gameId, UUID playerId, int categoryId) {

    }

    @Override
    public void commitRound(UUID gameId, UUID playerId) {

    }

    @Override
    public void commitCard(UUID gameId, UUID playerId) {

    }
}