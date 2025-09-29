package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class TurnManager {
    private Board board;
    private Player currentPlayer;
    private Player opponent;

    public TurnManager(Board board, Player currentPlayer, Player opponent) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.opponent = opponent;
    }

    public void takeTurn(int houseNumber, IO io) {
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

    public boolean hasMoveLeft() {
        for (House house : currentPlayer.getHouses()) {
            if (house.countSeed() > 0) {
                return true;
            }
        }
        return false;
    }

    public void executeMoveInTurn(String input, IO io) {
        if (!InputValidation.isValidInput(input, board.getHouseCount(), io)) {
            return;
        }

        int houseNumber = Integer.parseInt(input);

        if (!InputValidation.isValidHouseNumber(houseNumber, board.getHouseCount(), io)) {
            return;
        }

        List<House> allHouses = currentPlayer.getHouses();
        int indexSelectedHouse = houseNumber - 1;
        int seedAtSelectedHouse = allHouses.get(indexSelectedHouse).countSeed();
        if (seedAtSelectedHouse == 0) {
            io.println("House is empty. Move again.");
            return;
        }

        takeTurn(houseNumber, io);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
