package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;

public class BasicAlgorithm implements Explorer {
    private Maze maze;
    private static final int[][] DIRECTIONS = {
        {0, 1},   // East
        {1, 0},   // North
        {0, -1},  // West
        {-1, 0}   // South
    };
    private String path;

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
        StringBuilder path = new StringBuilder();
        ArrayList<ArrayList<Integer>> grid = this.maze.getGrid();

        int startRow = this.maze.getEntranceRow();
        int startCol = 0;
        int endRow = this.maze.getExitRow();
        int endCol = grid.get(0).size() - 1;

        int currentRow = startRow;
        int currentCol = startCol;
        int directionIndex = 0;  

        while (!(currentRow == endRow && currentCol == endCol)) {
            // Try turning right first
            int rightDirection = (directionIndex + 1) % 4; // Turning right
            int rightRow = currentRow + DIRECTIONS[rightDirection][0];
            int rightCol = currentCol + DIRECTIONS[rightDirection][1];

            if (canMove(grid, rightRow, rightCol)) {
                // If the right is open, turn right and move
                directionIndex = rightDirection; // Update direction to the right
                currentRow = rightRow;
                currentCol = rightCol;
                path.append("R").append("F");
            } else {
                // If right is blocked, try moving forward
                int forwardRow = currentRow + DIRECTIONS[directionIndex][0];
                int forwardCol = currentCol + DIRECTIONS[directionIndex][1];

                if (canMove(grid, forwardRow, forwardCol)) {
                    // Move forward
                    currentRow = forwardRow;
                    currentCol = forwardCol;
                    path.append("F");
                } else {
                    // If forward is also blocked, turn left
                    directionIndex = (directionIndex + 3) % 4; // Turn left
                    path.append("L");
                }
            }
        }

        this.path = path.toString();    

        return this.path;
    }

    public String getCanonicalPath() {
        StringBuilder canonicalPath = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            canonicalPath.append(path.charAt(i));
            if (i < path.length() - 1 && path.charAt(i) != path.charAt(i + 1)) {
                canonicalPath.append(" ");
            }
        }
        return canonicalPath.toString();
    }

    public String getFactorizedPath() {
        StringBuilder factorizedPath = new StringBuilder();
        int i = 0;
        while (i < path.length()) {
            char currentChar = path.charAt(i);
            int count = 1;
            while (i + 1 < path.length() && path.charAt(i + 1) == currentChar) {
                count++;
                i++;
            }
            if (count > 1) {
                factorizedPath.append(count).append(currentChar);
            } else {
                factorizedPath.append(currentChar);
            }
            if (i < path.length() - 1) {
                factorizedPath.append(" ");
            }
            i++;
        }
        return factorizedPath.toString();
    }

    private static boolean canMove(ArrayList<ArrayList<Integer>> maze, int row, int col) {
        return row >= 0 && row < maze.size() &&
               col >= 0 && col < maze.get(0).size() &&
               maze.get(row).get(col) == 0;
    }
    
}
