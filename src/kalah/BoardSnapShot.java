package kalah;

import java.util.ArrayList;
import java.util.List;

public class BoardSnapShot {
    private final PlayerState player1State;
    private final PlayerState player2State;
    private final String currentPlayerName;

    public BoardSnapShot(Player player1, Player player2, Player currentPlayer) {
        this.player1State = new PlayerState(player1);
        this.player2State = new PlayerState(player2);
        this.currentPlayerName = currentPlayer.getName();
    }

    private static class PlayerState {
        private final List<Integer> houses;
        private final int store;

        PlayerState(Player player) {
            this.houses = new ArrayList<>();
            for (House house : player.getHouses()) {
                this.houses.add(house.countSeed());
            }
            this.store = player.getStore().countSeed();
        }

        private List<Integer> getHouses() {
            return houses;
        }

        private int getStore() {
            return store;
        }
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public void restoreBoardState(Board loadedBoard) {
        // Restore P1 houses:
        for (int i = 0; i < loadedBoard.getHouseCount(); i++) {
            House house = loadedBoard.getPlayer1().getHouses().get(i);
            house.addSeed(player1State.getHouses().get(i));
        }

        // Restore P2 houses:
        for (int i = 0; i < loadedBoard.getHouseCount(); i++) {
            House house = loadedBoard.getPlayer2().getHouses().get(i);
            house.addSeed(player2State.getHouses().get(i));
        }

        // Restore P1 and P2 stores:
        loadedBoard.getPlayer1().getStore().addSeed(player1State.getStore());
        loadedBoard.getPlayer2().getStore().addSeed(player2State.getStore());
    }
}