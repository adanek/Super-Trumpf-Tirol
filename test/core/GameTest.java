package core;

import contracts.game.GameState;
import org.junit.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import static org.fest.assertions.Assertions.*;

public class GameTest {
    
    @Test
    public void getGameId_validGame_returnsId(){
        
        String id = UUID.randomUUID().toString();
        Game sut = createGameWithId(id);
        
        String expected = id;
        String actual = sut.getGameID();
        
        assertThat(expected).isEqualTo(actual);
    }
    
    @Test 
    public void getCardId_player1_returnsFirstCard(){
        
        String p1Id = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1Id);
        
        int expected = 0;
        int actual = sut.getCardId(p1Id);
        
        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = UnknownPlayerException.class)
    public void chooseCategory_invalidPlayerId_throwsException(){
        
        String p1 = UUID.randomUUID().toString();
        String fake = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1);
        
        sut.chooseCategory(fake, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void chooseCategory_invalidCategory_throwsException(){

        String p1 = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1);
        String fake = null;
        sut.chooseCategory(p1, fake);
    }

    @Test(expected = IllegalStateException.class)
    public void chooseCategory_passivePlayer_throwsException(){

        Game sut = createGame();
        String passivePlayer = sut.getPassivPlayer();
        
        sut.chooseCategory(passivePlayer, "passivePlayer");
    }

    @Test(expected = IllegalStateException.class)
    public void chooseCategory_activePlayerChooseTwice_throwsException(){

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        
        sut.chooseCategory(activePlayer, "activePlayer");
        sut.chooseCategory(activePlayer, "activePlayer");        
    }    
    
    @Test
    public void chooseCategory_activePlayerChooseOnce_changesState(){

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        
        sut.chooseCategory(activePlayer, "activePlayer");
        
        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(activePlayer).getGameState();
        
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test(expected = IllegalStateException.class)
    public void commitCard_activePlayer_throwsException(){

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        
        sut.commitCard(activePlayer);
    }

    @Test
    public void commitCard_passivePlayerCommitsOnce_changesState(){

        Game sut = createGame();
        String passivePlayer = sut.getPassivPlayer();
        
        sut.commitCard(passivePlayer);
        
        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(passivePlayer).getGameState();
        
        assertThat(actual).isEqualTo(expected);        
    }    
    
    @Test(expected = IllegalStateException.class)
    public void commitCard_passivePlayerCommitsTwice_throwsException(){

        Game sut = createGame();
        String passivePlayer = sut.getPassivPlayer();
        
        sut.commitCard(passivePlayer);
        sut.commitCard(passivePlayer);
    }
    
    // Helpers
    
    private Game createGameWithId(String id){

        String p1Id = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();

        return createGame(id, p1Id, p2Id);
    }
    
    private Game createGameForPlayer(String p1Id) {

        String gid = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();
        
        return createGame(gid, p1Id, p2Id);
    }
    
    private Game createGameForPlayers(String p1Id, String p2Id) {

        String gid = UUID.randomUUID().toString();
         
        return createGame(gid, p1Id, p2Id);
    }

    private Game createGame(){

        String gid = UUID.randomUUID().toString();
        String p1Id = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();

        return createGame(gid, p1Id, p2Id);
    }
    
    private Game createGame(String gid, String p1Id, String p2Id) {        
     
        Queue<Integer> cardsP1 = new LinkedList<>();
        Queue<Integer> cardsP2 = new LinkedList<>();
        
        for(int i = 0; i < 26; i++){
            cardsP1.add(i);
        }
        
        for(int i = 26; i < 52; i++){
            cardsP2.add(i);
        }
        
        return new Game(gid, p1Id, p2Id, cardsP1, cardsP2);
    }
}