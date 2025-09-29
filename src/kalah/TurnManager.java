package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class TurnManager {
    private Player currentPlayer;
    private Player opponent;

    public TurnManager(Player currentPlayer, Player opponent) {
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
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    public boolean hasMoveLeft() {
        for (House house : currentPlayer.getHouses()) {
            if (house.countSeed() > 0) {
                return true;
            }
        }
        return false;
    }

    public void executeMoveInTurn(String input, int houseCount, IO io) {
        if (!InputValidation.isValidInput(input, houseCount, io)) {
            return;
        }

        int houseNumber = Integer.parseInt(input);

        if (!InputValidation.isValidHouseNumber(houseNumber, houseCount, io)) {
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
