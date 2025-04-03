package ca.mcmaster.se2aa4.mazerunner;

public class BasicExplorerFactory extends ExplorerFactory {
    @Override
    public Explorer createExplorer() {
        return new BasicAlgorithm();
    }
}
