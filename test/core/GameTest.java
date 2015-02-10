package core;

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