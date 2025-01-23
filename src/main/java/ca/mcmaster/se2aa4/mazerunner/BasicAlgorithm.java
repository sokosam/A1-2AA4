package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;

public class BasicAlgorithm implements Explorer {
    private Maze maze;

    public BasicAlgorithm() {
        this.maze = null;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return this.maze;
    }

    public String explore() {
        String res = "";
        for (ArrayList<Integer> row : this.maze.getGrid()) {
            for (int cell : row) {
                if (cell == 1) {
                    res += "1 ";
                } else {
                    res += "0 ";
                }
            }
            res += System.lineSeparator();
        }
        return res;
    }
    
}
