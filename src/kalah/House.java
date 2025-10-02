package kalah;

public class House extends Pit {
    public House(int seeds) {
        super(seeds);
    }

    public int collectSeeds() {
        int collectedSeeds = this.seeds;
        this.seeds = 0;
        return collectedSeeds;
    }
}
