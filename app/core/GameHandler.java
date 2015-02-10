package core;

import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.game.GameState;
import contracts.game.ICard;
import contracts.game.IGameHandler;
import data.Card;

import java.util.*;


public class GameHandler implements IGameHandler {

    /**
     * Holds all games
     */
    private HashMap<String, Game> games;
    /**
     * Holds all cards
     */
    private data.Card[] cards;

    /**
     * Constructor
     */
    public GameHandler() {
        DataProvider dataProvider = ServiceLocator.getDataProvider();
        List<Card> allCards = dataProvider.getAllCards();
        this.games = new HashMap<>();
        this.cards = allCards.toArray(new Card[allCards.size()]);
    }

    /**
     * Creates new game for a given player and returns the gameId as String
     */
    @Override
    public String createNewGame(String playerId) {

        PlayerAI cp = new PlayerAI(UUID.randomUUID());
        Game game = createGame(playerId, cp.getId());
        game.addObserver(cp);
        return game.getGameID();
    }

    @Override
    public String createNewGame(String player1Id, String player2Id) {

        Game game = createGame(player1Id, player2Id);

        return game.getGameID();
    }

    @Override
    public GameStatus getGameStatus(String gameId, String playerId) {

        Game game = getGame(gameId);
        return game.getGameStatus(playerId);
    }

    @Override
    public ICard getCard(String gameId, String playerId) {

        Game game = getGame(gameId);
        int cardId = game.getCardId(playerId);

        return cards[cardId];
    }

    @Override
    public ICard getCardFromCompetitor(String gameId, String playerId) throws IllegalStateException {
        
        Game game = getGame(gameId);
        
        String currentState = game.getGameStatus(playerId).getGameState();
        String expectedState = GameState.WaitForCommitRound.toString();
        if(!currentState.equals(expectedState))
            throw new IllegalStateException("The competitors card is only accessible in state WaitForCommit.");

        int cardId = game.getCompetitorCardId(playerId);
        return cards[cardId];
    }

    
    
    
    
    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {

    }

    @Override
    public void commitRound(String gameId, String playerId) {

    }

    @Override
    public void commitCard(String gameId, String playerId) {

    }

    @Override
    public void abortGame(String gameId, String playerId) {

    }

    /**
     * Creates a new Game instance*
     *
     * @param p1Id the id of the first player
     * @param p2Id the id of the second player
     * @return a new game instance
     */
    private Game createGame(String p1Id, String p2Id) {

        String gid = UUID.randomUUID().toString();

        ArrayList<Integer> shuffleArray = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            shuffleArray.add(i);
        }
        Collections.shuffle(shuffleArray);

        Queue<Integer> player1Cards = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            player1Cards.add(shuffleArray.get(i));
        }

        Queue<Integer> player2Cards = new LinkedList<>();
        for (int i = 26; i < 52; i++) {
            player2Cards.add(shuffleArray.get(i));
        }
        Game game = new Game(gid, p1Id, p2Id, player1Cards, player2Cards);
        games.put(gid, game);

        return game;
    }

    /**
     * Returns the game with the given id if it exists, otherwise it throws an exception
     *
     * @param gameId the id of the game
     * @throws java.lang.IllegalArgumentException if the game does not exist
     * @return the game with the given id
     */
    private Game getGame(String gameId) {
        Game game = games.get(gameId);

        if (game == null)
            throw new IllegalArgumentException("There exists no game with the given identifier.");

        return game;
    }
}
