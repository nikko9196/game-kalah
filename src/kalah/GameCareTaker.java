package kalah;

public class GameCareTaker {
    private BoardSnapShot lastBoardSnapShot;

    public GameCareTaker() {
    }

    public void saveBoard(BoardSnapShot boardSnapShot) {
        this.lastBoardSnapShot = boardSnapShot;
    }

    public BoardSnapShot loadBoard() {
        return this.lastBoardSnapShot;
    }

    public void clearBoardSnapShot() {
        this.lastBoardSnapShot = null;
    }
}