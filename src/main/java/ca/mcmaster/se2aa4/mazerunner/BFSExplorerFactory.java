package ca.mcmaster.se2aa4.mazerunner;

public class BFSExplorerFactory extends ExplorerFactory {
    @Override
    public Explorer createExplorer() {
        return new BFSExplorer();
    }
}