package ca.mcmaster.se2aa4.mazerunner;

// Basic Interface for any algorithm that can explore a maze
public interface Explorer {

    public String explore();

    public void setMaze(Maze maze);

    public Maze getMaze();

    public String getCanonicalPath();

    public String getFactorizedPath();

    public void turnLeft();

    public void turnRight();

    public void moveForward();

    public void moveBackward();

    public void reset();

    public boolean hasFailed();

    public boolean checkPath(String path);
}
