package ca.mcmaster.se2aa4.mazerunner;

public class TurnLeftCommand implements Command {
    private Explorer explorer;

    public TurnLeftCommand(Explorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public void execute() {
        explorer.turnLeft();
    }
}