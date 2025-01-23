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
