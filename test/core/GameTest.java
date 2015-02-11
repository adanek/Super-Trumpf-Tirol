package core;

import contracts.game.GameState;
import contracts.game.IGame;
import org.junit.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class GameTest {

    @Test
    public void getGameId_validGame_returnsId() {

        String id = UUID.randomUUID().toString();
        Game sut = createGameWithId(id);

        String expected = id;
        String actual = sut.getGameID();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void getCardId_player1_returnsFirstCard() {

        String p1Id = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1Id);

        int expected = 0;
        int actual = sut.getCardId(p1Id);

        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = UnknownPlayerException.class)
    public void chooseCategory_invalidPlayerId_throwsException() {

        String p1 = UUID.randomUUID().toString();
        String fake = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1);
        int categoryId = 0;
        sut.chooseCategory(fake, categoryId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void chooseCategory_invalidCategory_throwsException() {

        String p1 = UUID.randomUUID().toString();
        Game sut = createGameForPlayer(p1);
        int categoryId = -1;
        sut.chooseCategory(p1, categoryId);
    }

    @Test(expected = IllegalStateException.class)
    public void chooseCategory_passivePlayer_throwsException() {

        Game sut = createGame();
        String passivePlayer = sut.getPassivePlayer();
        int categoryId = 0;

        sut.chooseCategory(passivePlayer, categoryId);
    }

    @Test(expected = IllegalStateException.class)
    public void chooseCategory_activePlayerChooseTwice_throwsException() {

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        int categoryId = 0;
        sut.chooseCategory(activePlayer, categoryId);
        sut.chooseCategory(activePlayer, categoryId);
    }

    @Test
    public void chooseCategory_activePlayerChooseOnce_changesState() {

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        int categoryId = 0;
        sut.chooseCategory(activePlayer, categoryId);

        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(activePlayer).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalStateException.class)
    public void commitCard_activePlayer_throwsException() {

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();

        sut.commitCard(activePlayer);
    }

    @Test
    public void commitCard_passivePlayerCommitsOnce_changesState() {

        Game sut = createGame();
        String passivePlayer = sut.getPassivePlayer();

        sut.commitCard(passivePlayer);

        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(passivePlayer).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalStateException.class)
    public void commitCard_passivePlayerCommitsTwice_throwsException() {

        Game sut = createGame();
        String passivePlayer = sut.getPassivePlayer();

        sut.commitCard(passivePlayer);
        sut.commitCard(passivePlayer);
    }

    @Test
    public void getStatus_ChoiceAndCommitCardReceived_Player1InRightState() {

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        String passivePlayer = sut.getPassivePlayer();

        sut.chooseCategory(activePlayer, 0);
        sut.commitCard(passivePlayer);

        String expected = GameState.WaitForCommitRound.toString();
        String actual = sut.getGameStatus(activePlayer).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatus_ChoiceAndCommitCardReceived_Player2InRightState() {

        Game sut = createGame();
        String activePlayer = sut.getActivePlayer();
        String passivePlayer = sut.getPassivePlayer();

        sut.chooseCategory(activePlayer, 0);
        sut.commitCard(passivePlayer);

        String expected = GameState.WaitForCommitRound.toString();
        String actual = sut.getGameStatus(passivePlayer).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = UnknownPlayerException.class)
    public void commitRound_invaildPlayer_throwsException() {
        Game sut = createGame();
        String fakeId = UUID.randomUUID().toString();

        sut.commitRound(fakeId);
    }

    @Test(expected = IllegalStateException.class)
    public void commitRound_wrongState_throwsException() {
        Game sut = createGame();
        String pid = sut.getActivePlayer();

        sut.commitRound(pid);
    }

    @Test
    public void commitRound_player1_changesState() {
        Game sut = createGame();

        String p1 = sut.getActivePlayer();
        String p2 = sut.getPassivePlayer();

        sut.chooseCategory(p1, 0);
        sut.commitCard(p2);
        sut.commitRound(p1);

        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(p1).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void commitRound_player2_changesState() {
        Game sut = createGame();

        String p1 = sut.getActivePlayer();
        String p2 = sut.getPassivePlayer();

        sut.chooseCategory(p1, 0);
        sut.commitCard(p2);
        sut.commitRound(p2);

        String expected = GameState.WaitForOtherPlayer.toString();
        String actual = sut.getGameStatus(p2).getGameState();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void commitRound_bothPlayer_newRound() {

        IGame sut = createGame();
        String p1 = sut.getActivePlayer();
        String p2 = sut.getPassivePlayer();
        int roundNr = sut.getGameStatus(p1).getRound();
        sut.chooseCategory(p1, 0);
        sut.commitCard(p2);
        sut.setWinner(p1);
        sut.commitRound(p1);
        sut.commitRound(p2);

        int expected = roundNr + 1;
        int actual = sut.getGameStatus(p2).getRound();

        assertThat(actual).isEqualTo(expected);
    }

    // Helpers

    private Game createGameWithId(String id) {

        String p1Id = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();

        return createGame(id, p1Id, p2Id);
    }

    private Game createGameForPlayer(String p1Id) {

        String gid = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();

        return createGame(gid, p1Id, p2Id);
    }

    private Game createGame() {

        String gid = UUID.randomUUID().toString();
        String p1Id = UUID.randomUUID().toString();
        String p2Id = UUID.randomUUID().toString();

        return createGame(gid, p1Id, p2Id);
    }

    private Game createGame(String gid, String p1Id, String p2Id) {

        Queue<Integer> cardsP1 = new LinkedList<>();
        Queue<Integer> cardsP2 = new LinkedList<>();

        for (int i = 0; i < 26; i++) {
            cardsP1.add(i);
        }

        for (int i = 26; i < 52; i++) {
            cardsP2.add(i);
        }

        return new Game(gid, p1Id, p2Id, cardsP1, cardsP2);
    }
}