package mock;

import contracts.game.*;
import contracts.game.Card;
import contracts.game.CardCategory;

public class GameHandler implements contracts.game.GameHandler{
    @Override
    public void createNewGame() {

    }

    @Override
    public GameStatus getGameStatus() {
        return null;
    }

    @Override
    public Card getCard() {
        return new mock.Card();
    }

    @Override
    public Card getCardFromCompetitor() {
        return null;
    }

    @Override
    public void makeMove(CardCategory category) {

    }

    @Override
    public void commitRound() {

    }
}
