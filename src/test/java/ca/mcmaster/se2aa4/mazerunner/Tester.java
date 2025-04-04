package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tester {

    private ExplorerFactory explorerFactory;
    private Explorer explorer;
    private Maze maze;

    @BeforeEach
    public void setUp() {
        // Set up the test environment
        explorerFactory = new BasicExplorerFactory();
        explorer = explorerFactory.createExplorer();

        // Initialize the explorer with a maze
        Maze maze = new Maze("./examples/direct.maz.txt");
        explorer.setMaze(maze);
    }

    @Test
    public void testCommands() {

        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.moveForward();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());

        explorer.turnLeft();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 3) % 4, test.getDirectionIndex());

        explorer.moveForward();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow - 1, test.getCurrentRow());
        assertEquals((testDirection + 3) % 4, test.getDirectionIndex());

        explorer.turnRight();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow - 1, test.getCurrentRow());
        // This returns to testDirection as a left turn into right turn will result in
        // change
        assertEquals(testDirection, test.getDirectionIndex());

        explorer.turnRight();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow - 1, test.getCurrentRow());
        assertEquals((testDirection + 1) % 4, test.getDirectionIndex());

        explorer.moveForward();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 1) % 4, test.getDirectionIndex());

    }

    @Test
    public void testForward() {
        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.moveForward();
        assertEquals(testCol + 1, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());

        explorer.moveForward();
        assertEquals(testCol + 2, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());
    }

    @Test
    public void testBackward() {
        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.moveBackward();
        assertEquals(testCol - 1, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());

        explorer.moveBackward();
        assertEquals(testCol - 2, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());
    }

    @Test
    public void testTurnLeft() {
        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.turnLeft();
        assertEquals(testCol, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 3) % 4, test.getDirectionIndex());

        explorer.turnLeft();
        assertEquals(testCol, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 2) % 4, test.getDirectionIndex());
    }

    @Test
    public void testTurnRight() {
        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.turnRight();
        assertEquals(testCol, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 1) % 4, test.getDirectionIndex());

        explorer.turnRight();
        assertEquals(testCol, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals((testDirection + 2) % 4, test.getDirectionIndex());
    }

    @Test
    public void testReset() {
        BasicAlgorithm test = (BasicAlgorithm) explorer;
        int testRow = test.getCurrentRow();
        int testCol = test.getCurrentCol();
        int testDirection = test.getDirectionIndex();

        explorer.moveForward();
        explorer.turnLeft();
        explorer.moveForward();
        explorer.turnRight();
        explorer.moveForward();
        explorer.turnRight();
        explorer.moveForward();

        explorer.reset();
        assertEquals(testCol, test.getCurrentCol());
        assertEquals(testRow, test.getCurrentRow());
        assertEquals(testDirection, test.getDirectionIndex());
    }

    @Test
    public void testExplore() {
        String result = explorer.explore();
        assertEquals("FRFFLFFFRFLFRFLFF", result);
    }

    @Test
    public void testGetCanonicalPath() {
        explorer.explore();
        String result = explorer.getCanonicalPath();
        assertEquals("F R FF L FFF R F L F R F L FF", result);
    }

    @Test
    public void testGetFactorizedPath() {
        explorer.explore();
        String result = explorer.getFactorizedPath();
        assertEquals("F R 2F L 3F R F L F R F L 2F", result);
    }

    @Test
    public void testTryPath() {
        explorer.reset();
        String path = "FRFF";
        boolean result = explorer.checkPath(path);
        assertEquals(true, result);

        explorer.reset();
        path = "FFFFF";
        result = explorer.checkPath(path);
        assertEquals(false, result);

        explorer.reset();
        path = "RRRR";
        result = explorer.checkPath(path);
        assertEquals(true, result);
    }
}
