package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import ca.mcmaster.se2aa4.mazerunner.Maze;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Options option = new Options();
        option.addOption(new Option("i", "input", true, "Input file path"));
        option.addOption(new Option("p", "path", true, "Examine the path"));
        option.addOption(new Option("a", "algorithm", true, "Algorithm to use (bfs or basic)"));

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
            System.out.println(" ");
            System.out.println(maze.toString());

            if (maze.getEntranceRow() == -1) {
                logger.error("No entrance found in the maze");
                throw new Exception("No entrance found in the maze");
            }

            // Decide which factory to use (could be via command line args, config file,
            // etc.)
            ExplorerFactory factory = null;
            if (cmd.hasOption("a")) {
                String algo = cmd.getOptionValue("a");
                if ("bfs".equalsIgnoreCase(algo)) {
                    factory = new BFSExplorerFactory();
                } else {
                    factory = new BasicExplorerFactory();
                }
            } else {
                // default
                factory = new BasicExplorerFactory();
            }

            // Use the factory method
            Explorer explorer = factory.createExplorer();
            explorer.setMaze(maze);

            if (cmd.hasOption("p")) {
                String checkPath = cmd.getOptionValue("p");
                List<Command> commands = new ArrayList<>();
                for (char c : checkPath.toCharArray()) {
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

                boolean success = explorer.checkPath(checkPath);
                if (success) {
                    System.out.println("Input -p path is possible");
                } else {
                    System.out.println("Input -p path is not possible");
                }

            }
            explorer.reset();
            String path = explorer.explore();
            System.out.println("**** Computing path");
            System.out.println("Found path: " + path);

            System.out.println("Canonical Form: " + explorer.getCanonicalPath());
            System.out.println("Factorized Form: " + explorer.getFactorizedPath());

        } catch (org.apache.commons.cli.ParseException pe) {
            logger.error("Failed to parse command line arguments: " + pe.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\" + e.getMessage());
        }

        System.out.println("** End of MazeRunner");
    }
}
