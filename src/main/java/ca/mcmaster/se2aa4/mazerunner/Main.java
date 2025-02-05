package ca.mcmaster.se2aa4.mazerunner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import ca.mcmaster.se2aa4.mazerunner.Maze;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Options option = new Options();
        option.addOption(new Option("i", "input", true, "Input file path"));
        option.addOption(new Option("p", "path", true, "Examine the path"));
        

        logger.info("** Starting Maze Runner");
        try {

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(option, args);
            logger.info("**** Reading the maze from file ");
            String inputFile = "";
            if (cmd.hasOption("i")) {
                inputFile = cmd.getOptionValue("i");
                logger.info("Input file: " + inputFile);
            }
             else {
                logger.error("No input file provided");
                System.exit(1);
            }

            Maze maze = new Maze(inputFile);
            System.out.println(" ");
            System.out.println(maze.toString());

            if (maze.getEntranceRow() == -1){
                logger.error("No entrance found in the maze");
                throw new Exception("No entrance found in the maze");
            }
        
            if (cmd.hasOption("p")) {
                String path = cmd.getOptionValue("p");
                // System.out.println("Path: " + path);
                if (maze.isPossible(path)){
                    System.out.println("Input -p path is possible");
                } else {
                    System.out.println("Input -p path is not possible");
                }
            }

            System.out.println("**** Computing path");
            BasicAlgorithm algo = new BasicAlgorithm();
            algo.setMaze(maze);
            algo.explore();
            System.out.println("Canonical Form: " + algo.getCanonicalPath());
            System.out.println("Factorized Form: " + algo.getFactorizedPath());


        } catch(org.apache.commons.cli.ParseException pe) {
            logger.error("Failed to parse command line arguments: " + pe.getMessage());
            System.exit(1);
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\" + e.getMessage());
        }


        System.out.println("** End of MazeRunner");
    }
}
