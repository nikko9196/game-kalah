package kalah;

import java.util.List;

public class Player {
    private String name;
    private List<House> houses;
    private Store store;

    public Player(String name, List<House> houses, Store store) {
        this.name = name;
        this.houses = houses;
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public List<House> getHouses() {
        return houses;
    }

    public Store getStore() {
        return store;
    }
}
