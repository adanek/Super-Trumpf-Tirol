package core;

import contracts.game.IGameHandler;
import contracts.game.GameState;
import contracts.game.RoundState;

import java.util.*;


public class GameHandler implements IGameHandler {

    /** Holds all games */
    private HashMap<UUID, Game> map;
    /** Holds all cards */
    private data.Card[] cards;

    /**
     * Constructor
     *
     * @param cards:
     * Array containing all cards
     */
    public GameHandler(data.Card[] cards) {
        this.map = new HashMap<>();
        this.cards = cards;
    }

    /**
     * Creates new game for a given player and returns the gameId as String
     */
    @Override
    public String createNewGame(String playerId) {
        UUID pid = UUID.fromString(playerId);
        PlayerAI cp = new PlayerAI(UUID.randomUUID());
        Game game = createGame(pid, cp.getId());
        game.addObserver(cp);
        return game.getGameID().toString();
    }

    @Override
    public String createNewGame(String player1Id, String player2Id) {
        UUID p1Id = UUID.fromString(player1Id);
        UUID p2Id = UUID.fromString(player2Id);

        Game game = createGame(p1Id, p2Id);

        return game.getGameID().toString();
    }

    private Game createGame(UUID p1Id, UUID p2Id) {
        
        UUID gid = UUID.randomUUID();
        ArrayList<Integer> shuffleArray = new ArrayList<>();
        for(int i = 0; i < 52; i++){
            shuffleArray.add(i);
        }
        Collections.shuffle(shuffleArray);

        Queue<Integer> player1Cards = new LinkedList<>();
        for(int i = 0; i < 26; i++){
            player1Cards.add(shuffleArray.get(i));
        }

        Queue<Integer> player2Cards = new LinkedList<>();
        for(int i = 26; i < 52; i++){
            player2Cards.add(shuffleArray.get(i));
        }
        Game game = new Game(gid,p1Id,p2Id,player1Cards, player2Cards);
        map.put(gid, game);
    }

    @Override
    public GameStatus getGameStatus(String gameId, String playerId) {
	    Game game = map.get(UUID.fromString(gameId));
        GameStatus status = game.getStatus();
        if(status.getChoosenCategory() != null){
            if(game.getPlayer1sMove()){
                status.setRoundState(RoundState.WON);
            }
            else {
                status.setRoundState(RoundState.LOST);
            }
        }
        return status;
    }

    @Override
    public data.Card getCard(String gameId, String playerId) {
	    Game game = map.get(UUID.fromString(gameId));
        game.getStatus().setRoundState(RoundState.OUTSTANDING);
        if (UUID.fromString(playerId).equals(game.getPlayer1ID())){
            return cards[game.getPlayer1Card()];
        }else{
            return null;
        }
    }

    @Override
    public data.Card getCardFromCompetitor(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        if(!game.getStatus().getGameState().equals(GameState.WaitForCommit.toString())){
            throw new IllegalStateException();
        }
        if (UUID.fromString(playerId).equals(game.getPlayer1ID())){
            return cards[game.getPlayer2Card()];
        }else{
            return null;
        }
    }

    @Override
    public void makeMove(String gameId, String playerId, int categoryID) {
        Game game = map.get(UUID.fromString(gameId));
        if(game.getPlayer1sMove() == true){
            compareCategories(game, categoryID);
        }
    }

    @Override
    public void commitRound(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        // If player1 moves next round, he won this round.
        if(game.getStatus().getRoundState().equals(RoundState.WON)){
            game.player1win();
        }else if(game.getStatus().getRoundState().equals(RoundState.LOST)){
            game.player2win();
        }else if(game.getStatus().getRoundState().equals(RoundState.LOST)){
            game.draw();
        }
        game.getStatus().updateRound();
    }

    @Override
    public void commitCard(String gameId, String playerId) {
        Game game = map.get(UUID.fromString(gameId));
        Random rand = new Random();
        // Random number from 0 to 4
        int categoryID = rand.nextInt(5);
        compareCategories(game, categoryID);
    }

    @Override
    public void abortGame(String gameId, String playerId) {
        //TODO abort Game.
    }

    private void compareCategories(Game game, int categoryID){
        // Get Ranks of the cards
        int player1Value = cards[game.getPlayer1Card()].getRankingArray()[categoryID];
        int player2Value = cards[game.getPlayer2Card()].getRankingArray()[categoryID];
        // Smaller rank means better position
        if(player1Value > player2Value){
            // If player1 loses, he does not move in the next round.
            game.setPlayer1sMove(false);
            game.getStatus().setRoundState(RoundState.LOST);
        }else if(player1Value < player2Value){
            // If player1 win, he moves next round.
            game.setPlayer1sMove(true);
            game.getStatus().setRoundState(RoundState.WON);
        }else if(player1Value == player2Value){
            game.getStatus().setRoundState(RoundState.DRAWN);
        }
        game.getStatus().setChoosenCategory(cards[0].getCategories().get(categoryID).getName());
        game.getStatus().updateStatus(GameState.WaitForCommit);
    }
}
