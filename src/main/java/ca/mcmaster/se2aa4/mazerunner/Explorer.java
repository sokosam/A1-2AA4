package ca.mcmaster.se2aa4.mazerunner;

// Basic Interface for any algorithm that can explore a maze
public interface Explorer {

    public String explore();

    public void setMaze(Maze maze);

    public Maze getMaze();

    public String getCanonicalPath();

    public String getFactorizedPath();

}