package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class  Maze {
    private ArrayList<ArrayList<Integer>> grid;
    
    public Maze(String file){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        int maxCol = 0;
        this.grid = new ArrayList<ArrayList<Integer>>();
        try {
            while ((line = reader.readLine()) != null) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                int curr = 0;
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        row.add(1);
                        curr++;
                    } else if (line.charAt(idx) == ' ') {
                        row.add(0);
                        curr++;
                    }
                }
                if (curr > maxCol) {
                    maxCol = curr;
                }
                this.grid.add(row);
            }
            for (ArrayList<Integer> row : this.grid) {
                while (row.size() < maxCol) {
                    row.add(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Integer>> getGrid() {
        return new ArrayList<ArrayList<Integer>>(this.grid);
    }

    // Useful method for getting the entrance row of the maze
    public int getEntranceRow() {
        for (int i = 0; i < this.grid.size(); i++) {
            if (this.grid.get(i).get(0) == 0) {
                return i;
            }
        }
        return -1;
    }

    public int getExitRow(){
        for (int i = 0; i < this.grid.size(); i++) {
            if (this.grid.get(i).get(this.grid.get(i).size() - 1) == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean isPossible(String path){
        int row = getEntranceRow();
        int col = 0;
        int[][] directions = {
            {0, 1},   // East
            {1, 0},   // North
            {0, -1},  // West
            {-1, 0}   // South
        };
        int curr = 0;
        for (int i = 0; i < path.length(); i++){
            char move = path.charAt(i);
            // System.out.println(move);  - Used for debugging
            if (move != 'F' && move != 'L' && move != 'R'  && move != ' '){
                return false;
            }
            
            if (move == 'F'){
                row += directions[curr][0];
                col += directions[curr][1];
                // System.out.println(row +"" + col); - Used for debugging
                // If the row/col is out of the bounds of the grid, or if the cell is blocked, return false
                if (row < 0 || row >= this.grid.size() || col < 0 || col >= this.grid.get(0).size() || this.grid.get(row).get(col) == 1){
                    return false;
                }
            } 
            // Update direction
            else if (move == 'L'){
                curr = (curr + 3) % 4;
            }
            // Update direction
            else if (move == 'R'){
                curr = (curr + 1) % 4;
            }
        }
        return true;
    }

    public String toString() {
        String res = "";
        for (ArrayList<Integer> row : this.grid) {
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
