package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class Game {
    private Board board;
    private Player currentPlayer;
    private Player opponent;

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = board.getPlayer1();
        this.opponent = board.getPlayer2();
    }

    public void play(IO io) {
        while (true) {
            board.printBoard(io);
            if (isGameOver(io)) {
                break;
            }

            io.println("Player " + currentPlayer.getName());
            io.println("    (1-6) - house number for move");
            io.println("    N - New game");
            io.println("    S - Save game");
            io.println("    L - Load game");
            io.println("    q - Quit");
            io.print("Choice:");
            String input = io.readFromKeyboard("");
            if (input.equals("q")) {
                io.println("Game over");
                board.printBoard(io);
                break;
            }

            if (!InputValidation.isValidInput(input, board.getHouseCount(), io)) {
                continue;
            }

            int houseNumber = Integer.parseInt(input);

            if (!InputValidation.isValidHouseNumber(houseNumber, board.getHouseCount(), io)) {
                continue;
            }

            List<House> allHouses = currentPlayer.getHouses();
            int indexSelectedHouse = houseNumber - 1;
            int seedAtSelectedHouse = allHouses.get(indexSelectedHouse).countSeed();
            if (seedAtSelectedHouse == 0) {
                io.println("House is empty. Move again.");
                continue;
            }

            takeTurn(houseNumber, io);
        }
    }


    private void takeTurn(int houseNumber, IO io) {
        // Do the sowing
        SeedMove seedMove = new SeedMove(currentPlayer);
        seedMove.sow(currentPlayer, houseNumber, opponent, io);

        // Check different situations after sowing:
        if (seedMove.isLastSeedAtOwnStore()) {
            return;
        }

        if (!seedMove.isLastSeedAtOwnHouse()) {
            switchPlayer();
            return;
        }

        int index = seedMove.getLastLandedHouseIndex();
        House lastLandedHouse = currentPlayer.getHouses().get(index);


        if (lastLandedHouse.countSeed() == 1) {
            seedMove.captureSeeds(currentPlayer.getHouses(), opponent.getHouses(), index);
        }

        switchPlayer();
    }


    private void switchPlayer() {
        currentPlayer = (currentPlayer == board.getPlayer1()) ? board.getPlayer2() : board.getPlayer1();
        opponent = (opponent == board.getPlayer2()) ? board.getPlayer1() : board.getPlayer2();
    }


    private boolean hasMoveLeft() {
        for (House house : currentPlayer.getHouses()) {
            if (house.countSeed() > 0) {
                return true;
            }
        }
        return false;
    }


    private boolean isGameOver(IO io) {
        if (!hasMoveLeft()) {
            io.println("Game over");
            board.printBoard(io);
            displayResult(io);
            return true;
        }
        return false;
    }


    private void displayResult(IO io) {
        SeedMove seedMove = new SeedMove(currentPlayer);
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
