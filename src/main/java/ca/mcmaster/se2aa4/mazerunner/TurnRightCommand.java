package ca.mcmaster.se2aa4.mazerunner;

public class TurnRightCommand implements Command {
    private Explorer explorer;

    public TurnRightCommand(Explorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public void execute() {
        explorer.turnRight();
    }
}