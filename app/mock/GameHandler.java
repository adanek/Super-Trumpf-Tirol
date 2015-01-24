package mock;

import java.util.UUID;

import config.ServiceLocator;
import contracts.game.GameState;
import contracts.game.ICard;
import data.Card;

public class GameHandler implements contracts.game.GameHandler {
	@Override
	public String createNewGame(String playerId) {
		return UUID.randomUUID().toString();
	}

	@Override
	public GameStatus getGameStatus(String gameId, String playerId) {
		return new GameStatus(GameState.WaitForYourChoice, 12, 40, 22);
	}

	@Override
	public ICard getCard(String gameId, String playerId) {

		Card card = ServiceLocator.getDataProvider().getAllCards().get(15);
		return  card;
	}

	@Override
	public ICard getCardFromCompetitor(String gameId, String playerId) {
		
		Card card = ServiceLocator.getDataProvider().getAllCards().get(22);
		return  card;
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