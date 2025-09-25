package kalah;

import com.qualitascorpus.testsupport.IO;

public class MoveCommand implements Command {
    private Game game;
    private IO io;
    private String inputHouseNumber;

    public MoveCommand(Game game, IO io, String inputHouseNumber) {
        this.game = game;
        this.io = io;
        this.inputHouseNumber = inputHouseNumber;
    }

    @Override
    public void execute() {
        game.handleMoveCommand(inputHouseNumber, io);
    }
}
