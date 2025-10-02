package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure. Replace this comment with a more appropriate
 * one.
 */
public class Kalah {
    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        // Replace what's below with your implementation
        int houseCount = 6;
        int seedsPerHouse = 4;
        boolean clockwise = false;

        Board board = new Board(houseCount, seedsPerHouse, clockwise);
        Game game = new Game(board);
        game.play(io);
    }

    public void play(IO io, boolean p1, boolean p2, boolean p3) {
        // DO NOT CHANGE. Only here for backwards compatibility
        play(io);
    }
}
