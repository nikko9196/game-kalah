package kalah;

public abstract class Pit {
    protected int seeds;

    public Pit(int seeds) {
        this.seeds = seeds;
    }

    public int countSeed() {
        return seeds;
    }

    public void addSeed(int seeds) {
        this.seeds = this.seeds + seeds;
    }
}
