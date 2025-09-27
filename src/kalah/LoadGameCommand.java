package kalah;

import com.qualitascorpus.testsupport.IO;

public class LoadGameCommand implements Command {
    private Game game;
    private IO io;

    public LoadGameCommand(Game game, IO io) {
        this.game = game;
        this.io = io;
    }

    @Override
    public void execute() {
        game.loadGame(io);
    }
}
