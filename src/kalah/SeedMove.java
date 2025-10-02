package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class SeedMove {
    private Player currentPlayer;
    private boolean lastSeedAtOwnHouse;
    private boolean lastSeedAtOwnStore;
    private int lastLandedHouseIndex;

    public SeedMove(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        lastSeedAtOwnHouse = false;
        lastSeedAtOwnStore = false;
        lastLandedHouseIndex = -1;
    }

    public void sow(Player currentPlayer, int houseNumber, Player opponent) {
        List<House> allHouses = currentPlayer.getHouses();
        int indexSelectedHouse = houseNumber - 1;
        int seedToSow = allHouses.get(indexSelectedHouse).collectSeeds();
        boolean isCurrentPlayerHouse = true;

        while (seedToSow > 0) {
            indexSelectedHouse++;
            // Check if last seed at own house(s) or own store:
            if (isCurrentPlayerHouse) {
                if (indexSelectedHouse < allHouses.size()) {
                    seedToSow--;
                    placeOneSeed(allHouses.get(indexSelectedHouse), true, false, indexSelectedHouse);
                }
                if (indexSelectedHouse == allHouses.size()) {
                    seedToSow--;
                    placeOneSeed(currentPlayer.getStore(), false, true, indexSelectedHouse);
                    indexSelectedHouse = -1;
                    isCurrentPlayerHouse = false;
                }
            }
            // Check if last seed at opponent's house(s) or store:
            else {
                if (indexSelectedHouse < opponent.getHouses().size()) {
                    seedToSow--;
                    placeOneSeed(opponent.getHouses().get(indexSelectedHouse), false, false, indexSelectedHouse);
                }
                if (indexSelectedHouse == opponent.getHouses().size()) {
                    indexSelectedHouse = -1;
                    isCurrentPlayerHouse = true;
                }
            }
        }
    }

    private void placeOneSeed(Pit pit, boolean lastSeedAtOwnHouse, boolean lastSeedAtOwnStore, int lastLandedHouseIndex) {
        pit.addSeed(1);
        this.lastSeedAtOwnHouse = lastSeedAtOwnHouse;
        this.lastSeedAtOwnStore = lastSeedAtOwnStore;
        this.lastLandedHouseIndex = lastLandedHouseIndex;
    }

    public void captureSeeds(List<House> currentPlayerHouses, List<House> opponentHouses, int currentHouseIndex) {
        House currentHouse = currentPlayerHouses.get(currentHouseIndex);

        // Sum of indices between currentHouse and opposite componentHouse is always equal to houseCount (either 6 or 7) minus 1:
        int oppositeHouseIndex = (currentPlayerHouses.size() - 1) - currentHouseIndex;
        House oppositeHouse = opponentHouses.get(oppositeHouseIndex);

        // Check if the condition is met:
        if (currentHouse.countSeed() == 1 && oppositeHouse.countSeed() > 0) {
            int capturedSeeds = currentHouse.collectSeeds() + oppositeHouse.collectSeeds();
            currentPlayer.getStore().addSeed(capturedSeeds);
        }
    }

    public void moveSeedsToStore(Player player) {
        for (House house : player.getHouses()) {
            player.getStore().addSeed(house.collectSeeds());
        }
    }

    public boolean isLastSeedAtOwnHouse() {
        return lastSeedAtOwnHouse;
    }

    public boolean isLastSeedAtOwnStore() {
        return lastSeedAtOwnStore;
    }

    public int getLastLandedHouseIndex() {
        return lastLandedHouseIndex;
    }
}