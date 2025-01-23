package ca.mcmaster.se2aa4.mazerunner;
public interface Explorer {

    public String explore();

    public void setMaze(Maze maze);
    
    public Maze getMaze();
}