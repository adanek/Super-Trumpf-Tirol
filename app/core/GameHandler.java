package core;

import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.game.GameState;
import contracts.game.ICard;
import contracts.game.IGame;
import contracts.game.IGameHandler;
import core.ai.PlayerAI;
import core.ai.SimplePlayerAI;
import data.Card;
import play.Logger;

import java.util.*;


public class GameHandler implements IGameHandler {

    /**
     * Holds all games
     */
    private final HashMap<String, IGame> games;
    /**
     * Holds all cards
     */
    private final data.Card[] cards;

    /**
     * Constructor
     */
    public GameHandler() {
        DataProvider dataProvider = ServiceLocator.getDataProvider();
        List<Card> allCards = dataProvider.getAllCards();
        this.games = new HashMap<>();
        this.cards = allCards.toArray(new Card[allCards.size()]);
        this.mpGames = new HashMap<>();
        this.waitingPlayers = new LinkedList<>();
    }

    /**
     * Creates new game for a given player and returns the gameId as String
     */
    @Override
    public String createNewGame(String playerId) {

        PlayerAI cp = new SimplePlayerAI(UUID.randomUUID().toString());
        Logger.info(String.format("Player %s has started a new single player game.", playerId));
        IGame game = createGame(playerId, cp.getId());
        game.addObserver(cp);
        game.notifyObservers();

        return game.getGameID();
    }

    @Override
    public String createNewGame(String player1Id, String player2Id) {

        IGame game = createGame(player1Id, player2Id);
        Logger.info(String.format("Player %s and %s has started a new multi player game.", player1Id, player2Id));

        return game.getGameID();
    }

    @Override
    public GameStatus getGameStatus(String gameId, String playerId) {

        IGame game = getGame(gameId);
        return game.getGameStatus(playerId);
    }

    @Override
    public ICard getCard(String gameId, String playerId) {

        IGame game = getGame(gameId);
        int cardId = game.getCardId(playerId);

        return cards[cardId];
    }

    @Override
    public ICard getCardFromCompetitor(String gameId, String playerId) throws IllegalStateException {

        IGame game = getGame(gameId);

        String currentState = game.getGameStatus(playerId).getGameState();
        String expectedState = GameState.WaitForCommitRound.toString();
        if (!currentState.equals(expectedState))
            throw new IllegalStateException("The competitors card is only accessible in state WaitForCommit.");

        int cardId = game.getCompetitorCardId(playerId);
        return cards[cardId];
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {

        try {
            IGame game = getGame(gameId);
            Logger.info(String.format("Player %s has choosen category %d.", playerId, categoryID));
            game.chooseCategory(playerId, categoryID);
            tryEvaluateRound(game);

        } catch (IllegalStateException ex) {
            Logger.error(ex.getMessage());
        }
    }

    @Override
    public void commitCard(String gameId, String playerId) {

        try {
            IGame game = getGame(gameId);
            Logger.info(String.format("Player %s has committed his card.", playerId));
            game.commitCard(playerId);
            tryEvaluateRound(game);

        } catch (IllegalStateException ex) {
            Logger.error(ex.getMessage());
        }
    }

    @Override
    public void commitRound(String gameId, String playerId) {
        try {
            IGame game = getGame(gameId);
            Logger.info(String.format("Player %s has committed the round.", playerId));
            game.commitRound(playerId);

        } catch (IllegalStateException ex) {
            Logger.error(ex.toString());
        }
    }

    @Override
    public void abortGame(String gameId, String playerId) {

        try {
            IGame game = getGame(gameId);

            Logger.info(String.format("Player %s has aborted game %s.", playerId, gameId));
            game.setAborted(playerId);

            // Delete the game if both players have aborted.
            if (game.isFinished()) {
                games.remove(game.getGameID());
                Logger.info(String.format("Game %s has been removed.", gameId));
            } else {
                game.notifyObservers();
            }

        } catch (UnknownPlayerException ex) {
            Logger.error(ex.toString());
        }

    }

    private HashMap<String, String> mpGames;
    private Queue<String> waitingPlayers;

    @Override
    public void registerForMultiPlayerGame(String playerId) {
        
        // A player can only exists once in the queue
        if(waitingPlayers.contains(playerId)){
            Logger.info(String.format("Player %s has registered for multi player game.", playerId));
            waitingPlayers.add(playerId);
            tryCreateMultiPlayerGame();
        }
        else {
            Logger.info(String.format("Player %s wants to register for multi player game but is already in queue.", playerId));;
        }
    }

    @Override
    public void unregisterForMultiPlayerGame(String playerId) {
        Logger.info(String.format("Player %s has unregistered for multi player game.", playerId));
        waitingPlayers.remove(playerId);
    }

    @Override
    public String getMultiPlayerGameId(String playerId) {
        String gameId = mpGames.get(playerId);

        if (gameId != null)
            Logger.info(String.format("Player %s has joined game %s.", playerId, gameId));
            mpGames.remove(playerId);

        return gameId;
    }

    /**
     * Creates a new Game instance*
     *
     * @param p1Id the id of the first player
     * @param p2Id the id of the second player
     * @return a new game instance
     */
    private IGame createGame(String p1Id, String p2Id) {

        String gid = UUID.randomUUID().toString();

        ArrayList<Integer> shuffleArray = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            shuffleArray.add(i);
        }
        Collections.shuffle(shuffleArray);

        int split = shuffleArray.size() / 2;
        Queue<Integer> player1Cards = new LinkedList<>();
        for (int i = 0; i < split; i++) {
            player1Cards.add(shuffleArray.get(i));
        }

        Queue<Integer> player2Cards = new LinkedList<>();
        for (int i = split; i < shuffleArray.size(); i++) {
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
     * @return the game with the given id
     * @throws java.lang.IllegalArgumentException if the game does not exist
     */
    private IGame getGame(String gameId) {
        IGame game = games.get(gameId);

        if (game == null)
            throw new IllegalArgumentException("There exists no game with the given identifier.");

        return game;
    }

    /**
     * Tries to evaluate the current round in the game
     *
     * @param game the game which should be evaluated
     */
    private void tryEvaluateRound(IGame game) {
        if (game.canEvaluateRound()) {

            String pid = game.getActivePlayer();
            int category = game.getGameStatus(pid).getChoosenCategoryId();
            ICard c1 = cards[game.getCardId(pid)];
            ICard c2 = cards[game.getCompetitorCardId(pid)];
            Integer rank1 = c1.getRankingArray()[category];
            Integer rank2 = c2.getRankingArray()[category];

            if (rank1 < rank2) {
                game.setWinner(pid);
            } else {
                game.setWinner(game.getPassivePlayer());
            }
        }
    }

    /**
     * Tries to create a multi player game if at least two players are waiting
     */
    private void tryCreateMultiPlayerGame() {
        
        Logger.info(String.format("Try to create a new multi player game. Queue length: %d", waitingPlayers.size()));
        if (waitingPlayers.size() >= 2) {
            String p1 = waitingPlayers.poll();
            String p2 = waitingPlayers.poll();

            IGame game = createGame(p1, p2);
            String gid = game.getGameID();

            games.put(gid, game);
            mpGames.put(p1, gid);
            mpGames.put(p2, gid);

            Logger.info(String.format("Multi player game %s created.", gid));
        }
    }
}
