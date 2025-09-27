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

    public static class PlayerState {
        private final String name;
        private final List<Integer> houses;
        private final int store;

        PlayerState(Player player) {
            this.name = player.getName();
            this.houses = new ArrayList<>();
            for (House house : player.getHouses()) {
                this.houses.add(house.countSeed());
            }
            this.store = player.getStore().countSeed();
        }

        public String getName() {
            return name;
        }

        public List<Integer> getHouses() {
            return houses;
        }

        public int getStore() {
            return store;
        }
    }

    public PlayerState getPlayer1State() {
        return player1State;
    }

    public PlayerState getPlayer2State() {
        return player2State;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}