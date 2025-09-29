package kalah;

import com.qualitascorpus.testsupport.IO;

public class GameResult {
    private Board board;
    private TurnManager currentTurn;

    public GameResult(Board board, TurnManager currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public boolean isGameOver(IO io) {
        if (!currentTurn.hasMoveLeft()) {
            io.println("Game over");
            board.printBoard(io);
            displayResult(io);
            return true;
        }
        return false;
    }

    private void displayResult(IO io) {
        SeedMove seedMove = new SeedMove(currentTurn.getCurrentPlayer());
        seedMove.moveSeedsToStore(board.getPlayer1());
        seedMove.moveSeedsToStore(board.getPlayer2());
        int totalSeedsPlayer1 = board.getPlayer1().getStore().countSeed();
        int totalSeedsPlayer2 = board.getPlayer2().getStore().countSeed();
        io.println("\tplayer 1:" + totalSeedsPlayer1);
        io.println("\tplayer 2:" + totalSeedsPlayer2);

        if (totalSeedsPlayer1 > totalSeedsPlayer2) {
            io.println("Player 1 wins!");
        } else if (totalSeedsPlayer1 < totalSeedsPlayer2) {
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }
}
