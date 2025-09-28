package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class Game {
    private Board board;
    private boolean quitRequested = false;
    private GameCareTaker gameCareTaker = new GameCareTaker();
    private TurnManager currentTurn;

    public Game(Board board) {
        this.board = board;
        this.currentTurn = new TurnManager(board, board.getPlayer1(), board.getPlayer2());
    }

    public void play(IO io) {
        while (true) {
            board.printBoard(io);
            if (isGameOver(io)) {
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


    // Methods used to check if Game is over:
    private boolean isGameOver(IO io) {
        if (!hasMoveLeft()) {
            io.println("Game over");
            board.printBoard(io);
            displayResult(io);
            return true;
        }
        return false;
    }

    private boolean hasMoveLeft() {
        for (House house : currentTurn.getCurrentPlayer().getHouses()) {
            if (house.countSeed() > 0) {
                return true;
            }
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
        if (!InputValidation.isValidInput(input, board.getHouseCount(), io)) {
            return;
        }

        int houseNumber = Integer.parseInt(input);

        if (!InputValidation.isValidHouseNumber(houseNumber, board.getHouseCount(), io)) {
            return;
        }

        List<House> allHouses = currentTurn.getCurrentPlayer().getHouses();
        int indexSelectedHouse = houseNumber - 1;
        int seedAtSelectedHouse = allHouses.get(indexSelectedHouse).countSeed();
        if (seedAtSelectedHouse == 0) {
            io.println("House is empty. Move again.");
            return;
        }

        currentTurn.takeTurn(houseNumber, io);
    }


    // Command Pattern (Memento Pattern is also applied): Method for NewGameCommand class to use:
    public void createNewGame() {
        board.createBoard();
        this.currentTurn = new TurnManager(board, board.getPlayer1(), board.getPlayer2());
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

        // Step 1: Create a board after loading:
        Board loadedBoard = new Board(board.getHouseCount(), 0, false);

        // Step 2A: Restore P1 houses:
        for (int i = 0; i < loadedBoard.getHouseCount(); i++) {
            House house = loadedBoard.getPlayer1().getHouses().get(i);
            house.addSeed(lastSnap.getPlayer1State().getHouses().get(i));
        }

        // Step 2B: Restore P2 houses:
        for (int i = 0; i < loadedBoard.getHouseCount(); i++) {
            House house = loadedBoard.getPlayer2().getHouses().get(i);
            house.addSeed(lastSnap.getPlayer2State().getHouses().get(i));
        }

        // Step 3: Restore P1 and P2 stores:
        loadedBoard.getPlayer1().getStore().addSeed(lastSnap.getPlayer1State().getStore());
        loadedBoard.getPlayer2().getStore().addSeed(lastSnap.getPlayer2State().getStore());

        // Step 4: Swap the loaded board into the game:
        this.board = loadedBoard;

        // Step 5: Restore current player:
        if ("P1".equals(lastSnap.getCurrentPlayerName())) {
            this.currentTurn = new TurnManager(board, board.getPlayer1(), board.getPlayer2());
        } else {
            this.currentTurn = new TurnManager(board, board.getPlayer2(), board.getPlayer1());
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
