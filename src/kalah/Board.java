package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Player player1;
    private Player player2;
    private int houseCount;
    private int seedsPerHouse;
    private boolean clockwise;

    public Board(int houseCount, int seedsPerHouse, boolean clockwise) {
        this.houseCount = houseCount;
        this.seedsPerHouse = seedsPerHouse;
        this.clockwise = clockwise;
        createBoard();
    }

    public void createBoard() {
        // Initialise houses for each player1 and player2:
        List<House> housesPlayer1 = new ArrayList<>();
        List<House> housesPlayer2 = new ArrayList<>();
        for (int i = 0; i < houseCount; i++) {
            housesPlayer1.add(new House(seedsPerHouse));
            housesPlayer2.add(new House(seedsPerHouse));
        }

        // Initialise a store for each player1 and player2:
        Store storePlayer1 = new Store();
        Store storePlayer2 = new Store();

        // Create player1 and player2:
        this.player1 = new Player("P1", housesPlayer1, storePlayer1);
        this.player2 = new Player("P2", housesPlayer2, storePlayer2);
    }

    public void printBoard(IO io) {
        // TOP LINE:
        printHorizontalBorder(io);

        // TOP ROW:
        if (clockwise) {
            // Print player2's houses (in order):
            printRow(io, String.format("| %2d |", player1.getStore().countSeed()), player2.getHouses(), " P2 |", true);
        } else {
            // Print player2's houses (in reverse order):
            printRow(io, "| P2 |", player2.getHouses(), String.format(" %2d |", player1.getStore().countSeed()), false);
        }

        // MIDDLE LINE:
        printDivider(io);

        // BOTTOM ROW:
        if (clockwise) {
            // Print player1's houses (in reverse order):
            printRow(io, "| P1 |", player1.getHouses(), String.format(" %2d |", player2.getStore().countSeed()), false);
        } else {
            // Print player1's houses (in order):
            printRow(io, String.format("| %2d |", player2.getStore().countSeed()), player1.getHouses(), " P1 |", true);
        }

        // BOTTOM LINE:
        printHorizontalBorder(io);
    }

    private void printHorizontalBorder(IO io) {
        io.print("+----");
        for (int i = 0; i < houseCount; i++) {
            io.print("+-------");
        }
        io.println("+----+");
    }

    private void printDivider(IO io) {
        io.print("|    |");
        for (int i = 0; i < houseCount; i++) {
            if (i < houseCount - 1) {
                io.print("-------+");
            } else {
                io.print("-------");
            }
        }
        io.println("|    |");
    }

    private void printRow(IO io, String leftLabel, List<House> playerHouses, String rightLabel, boolean straightForward) {
        io.print(leftLabel);
        if (straightForward) {
            for (int i = 0; i < playerHouses.size(); i++) {
                io.print(String.format(" %d[%2d] |", i + 1, playerHouses.get(i).countSeed()));
            }
        } else {
            for (int i = playerHouses.size() - 1; i >= 0; i--) {
                io.print(String.format(" %d[%2d] |", i + 1, playerHouses.get(i).countSeed()));
            }
        }
        io.println(rightLabel);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getHouseCount() {
        return houseCount;
    }
}