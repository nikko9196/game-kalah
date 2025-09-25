package kalah;

import com.qualitascorpus.testsupport.IO;

public class QuitCommand implements Command {
    private Game game;
    private IO io;

    public QuitCommand(Game game, IO io) {
        this.game = game;
        this.io = io;
    }

    @Override
    public void execute() {
        io.println("Game over");
        game.getBoard().printBoard(io);
        game.requestQuit();
    }
}
