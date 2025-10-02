package kalah;

import com.qualitascorpus.testsupport.IO;

public class Game {
    private Board board;
    private boolean quitRequested = false;
    private GameCareTaker gameCareTaker = new GameCareTaker();
    private TurnManager currentTurn;

    public Game(Board board) {
        this.board = board;
        this.currentTurn = new TurnManager(board.getPlayer1(), board.getPlayer2());
    }

    public void play(IO io) {
        while (true) {
            board.printBoard(io);
            GameResult gameResult = new GameResult(board, currentTurn);
            if (gameResult.isGameOver(io)) {
                break;
            }

            io.println("Player " + currentTurn.getCurrentPlayer().getName());
            io.println("    (1-6) - house number for move");
            io.println("    N - New game");
            io.println("    S - Save game");
            io.println("    L - Load game");
            io.println("    q - Quit");
            io.print("Choice:");
            String input = io.readFromKeyboard("");

            Command command = createCommand(input, io);
            if (command != null) {
                command.execute();
            }
            if (quitRequested) {
                break;
            }
        }
    }


    // Method to create Command depending on user's input:
    private Command createCommand(String input, IO io) {
        if (input == null) {
            return null;
        }

        if (input.equalsIgnoreCase("q")) {
            return new QuitCommand(this, io);
        } else if (input.equalsIgnoreCase("N")) {
            return new NewGameCommand(this);
        } else if (input.equalsIgnoreCase("S")) {
            return new SaveGameCommand(this);
        } else if (input.equalsIgnoreCase("L")) {
            return new LoadGameCommand(this, io);
        }
        return new MoveCommand(this, io, input);
    }


    // Command Pattern: Method for MoveCommand class to use:
    public void handleMoveCommand(String input, IO io) {
        currentTurn.executeMoveInTurn(input, board.getHouseCount(), io);
    }


    // Command Pattern (Memento Pattern is also applied): Method for NewGameCommand class to use:
    public void createNewGame() {
        board.createBoard();
        this.currentTurn = new TurnManager(board.getPlayer1(), board.getPlayer2());
        gameCareTaker.clearBoardSnapShot();
    }


    // Command Pattern (Memento Pattern is also applied): Method for SaveGameCommand class to use:
    public void saveGame() {
        BoardSnapShot snapShot = new BoardSnapShot(board.getPlayer1(), board.getPlayer2(), currentTurn.getCurrentPlayer());
        gameCareTaker.saveBoard(snapShot);
    }


    // Command Pattern (Memento Pattern is also applied): Method for LoadGameCommand class to use:
    public void loadGame(IO io) {
        BoardSnapShot lastSnap = gameCareTaker.loadBoard();
        if (lastSnap == null) {
            io.println("No saved game");
            return;
        }

        // Create a new board after loading:
        Board loadedBoard = new Board(board.getHouseCount(), 0, false);

        // Restore Players' houses and stores to a new board:
        lastSnap.restoreBoardState(loadedBoard);

        // Swap the loaded board into the game:
        this.board = loadedBoard;

        // Restore current player:
        if ("P1".equals(lastSnap.getCurrentPlayerName())) {
            this.currentTurn = new TurnManager(board.getPlayer1(), board.getPlayer2());
        } else {
            this.currentTurn = new TurnManager(board.getPlayer2(), board.getPlayer1());
        }
    }


    // Command Pattern: Methods for QuitCommand class to use:
    public void requestQuit() {
        this.quitRequested = true;
    }

    public Board getBoard() {
        return board;
    }
}
