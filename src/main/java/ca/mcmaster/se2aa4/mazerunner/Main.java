package ca.mcmaster.se2aa4.mazerunner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import ca.mcmaster.se2aa4.mazerunner.Maze;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Options option = new Options();
        option.addOption("i", "input", true, "Input file path");
        

        logger.info("** Starting Maze Runner");
        try {

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(option, args);
            logger.info("**** Reading the maze from file ");
            String inputFile = "";
            if (cmd.hasOption("i")) {
                inputFile = cmd.getOptionValue("i");
                logger.info("Input file: " + inputFile);
            } else {
                logger.error("No input file provided");
                System.exit(1);
            }

            Maze maze = new Maze(inputFile);
            System.out.println(maze.toString());

            System.out.println("**** Computing path");
            BasicAlgorithm algo = new BasicAlgorithm();
            algo.setMaze(maze);

            System.out.println(algo.explore());
            System.out.println(algo.getCanonicalPath());
            System.out.println(algo.getFactorizedPath());


        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }


        System.out.println("** End of MazeRunner");
    }
}
