package contracts.game;

public enum GameState {

    WaitForYourChoice,
    WaitForOtherPlayer,
    WaitForCommitCard,
    WaitForCommitRound,
    Aborted,
    Won,
    Lost
}
