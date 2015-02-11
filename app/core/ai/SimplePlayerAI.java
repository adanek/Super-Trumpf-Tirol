package core.ai;

import contracts.game.IGame;

import java.util.Random;

/**
 * Represents a simple AI which only chooses a random category 
 */
public class SimplePlayerAI extends PlayerAI {

    /**
     * Creates a new instance of a computer player with a simple AI
     * @param id the id of the new computer player
     */
    public SimplePlayerAI(String id) {
        super(id);
    }

    @Override
    protected void makeChoice(IGame game) {
        Random rnd = new Random();
        int categoryId = rnd.nextInt(5);
        
        gh.makeMove(game.getGameID(), pid, categoryId);
    }
}
