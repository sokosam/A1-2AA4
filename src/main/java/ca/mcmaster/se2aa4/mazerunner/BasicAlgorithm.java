package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class BasicAlgorithm implements Explorer {
    private Maze maze;
    private static final int[][] DIRECTIONS = {
            { 0, 1 }, // East
            { 1, 0 }, // North
            { 0, -1 }, // West
            { -1, 0 } // South
    };
    private String path;
    public int currentRow;
    public int currentCol;
    private int directionIndex;
    private int endCol;
    private int endRow;

    public BasicAlgorithm() {
        this.maze = null;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        this.currentCol = 0;
        this.currentRow = maze.getEntranceRow();
        this.endCol = maze.getGrid().get(0).size() - 1;
        this.endRow = maze.getExitRow();
        this.directionIndex = 0; // Start facing East
    }

    public Maze getMaze() {
        return this.maze;
    }

    public void reset() {
        if (this.maze == null) {
            return;
        }
        this.currentCol = 0;
        this.currentRow = maze.getEntranceRow();
        this.directionIndex = 0; // Start facing East
        this.path = "";
    }

    public String explore() {
        StringBuilder path = new StringBuilder();
        ArrayList<ArrayList<Integer>> grid = this.maze.getGrid();

        while (!(this.currentRow == this.endRow && this.currentCol == this.endCol)) {
            // Try turning right first
            turnRight();
            moveForward();
            if (canMove(grid, this.currentRow, this.currentCol)) {
                // If the right is open, turn right and move
                path.append("R").append("F");
            } else {
                // Undo Previous steps
                moveBackward();
                turnLeft();

                moveForward();
                if (canMove(grid, this.currentRow, this.currentCol)) {
                    // Move forward
                    path.append("F");
                } else {
                    // If forward is also blocked, turn left
                    moveBackward();
                    turnLeft();
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

    public boolean hasFailed() {
        if (this.maze == null) {
            return true;
        }
        return !canMove(this.maze.getGrid(), this.currentRow, this.currentCol);
    }

    public void turnLeft() {
        this.directionIndex = (this.directionIndex + 3) % 4;
    }

    public void turnRight() {
        this.directionIndex = (this.directionIndex + 1) % 4;
    }

    public void moveForward() {
        this.currentCol += DIRECTIONS[this.directionIndex][1];
        this.currentRow += DIRECTIONS[this.directionIndex][0];
    }

    public void moveBackward() {
        this.currentCol -= DIRECTIONS[this.directionIndex][1];
        this.currentRow -= DIRECTIONS[this.directionIndex][0];
    }

    public List<Command> parsePathToCommands(String path, Explorer explorer) {
        List<Command> commands = new ArrayList<>();
        for (char c : path.toCharArray()) {
            switch (c) {
                case 'F':
                    commands.add(new ForwardCommand(explorer));
                    break;
                case 'L':
                    commands.add(new TurnLeftCommand(explorer));
                    break;
                case 'R':
                    commands.add(new TurnRightCommand(explorer));
                    break;
                default:
            }
        }
        return commands;
    }

    public boolean checkPath(String path) {
        List<Command> commands = parsePathToCommands(path, this);
        for (Command command : commands) {
            command.execute();
            if (this.hasFailed()) {
                return false;
            }
        }
        return true;

    }

    public int getCurrentRow() {
        return this.currentRow;
    }

    public int getCurrentCol() {
        return this.currentCol;
    }

    public int getDirectionIndex() {
        return this.directionIndex;
    }
}
