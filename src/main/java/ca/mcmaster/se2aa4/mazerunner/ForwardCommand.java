package ca.mcmaster.se2aa4.mazerunner;

public class ForwardCommand implements Command {
    private Explorer explorer;

    public ForwardCommand(Explorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public void execute() {
        // Call some method on explorer that moves forward
        explorer.moveForward();
    }
}