package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * basic tests for the best first search algorithm.
 * checks name, null handling, edge cases, and solvability of mazes.
 */
class BestFirstSearchTest {

    /**
     * make sure the algorithm returns its correct name
     */
    @Test
    void testAlgorithmName() {
        BestFirstSearch searcher = new BestFirstSearch();
        assertEquals("Best First Search", searcher.getName(), "name should match expected string");
    }

    /**
     * make sure the algorithm handles null input safely
     */
    @Test
    void testNullInputReturnsNull() {
        BestFirstSearch searcher = new BestFirstSearch();
        Solution result = searcher.solve(null);
        assertNull(result, "null input should return null solution");
    }

    /**
     * test that the algorithm finds a valid path in a simple generated maze
     */
    @Test
    void testSolvesValidMaze() {
        IMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(5, 5); // generates a small solvable maze
        ISearchable searchable = new SearchableMaze(maze);
        BestFirstSearch searcher = new BestFirstSearch();

        Solution result = searcher.solve(searchable);
        assertNotNull(result, "should return a solution for a valid maze");

        List<AState> steps = result.getSolutionPath();
        if (steps.isEmpty()) {
            System.out.println("solution path was empty:");
            maze.print();
        }

        assertFalse(steps.isEmpty(), "path should not be empty in solvable maze");
        assertEquals(searchable.getStartState(), steps.get(0), "first step should be the start");
        assertEquals(searchable.getGoalState(), steps.get(steps.size() - 1), "last step should be the goal");
    }

    /**
     * test a maze that is fully blocked except start and goal
     */
    @Test
    void testUnsolvableMazeReturnsEmptyPath() {
        Maze maze = new Maze();
        maze.setDimensions(3, 3);

        // set all cells to walls
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                maze.setCell(new Position(i, j), 1);
            }
        }

        // open start and goal only (no path between them)
        maze.setCell(new Position(0, 0), 0);
        maze.setCell(new Position(2, 2), 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(2, 2));

        ISearchable mazeProblem = new SearchableMaze(maze);
        BestFirstSearch searcher = new BestFirstSearch();
        Solution result = searcher.solve(mazeProblem);

        assertNotNull(result, "should return a solution object even if unsolvable");
        assertTrue(result.getSolutionPath().isEmpty(), "path should be empty when no path exists");
    }

    /**
     * test a minimal maze of size 1x1 (start equals goal)
     */
    @Test
    void testTinyMazeWithOnlyStartAndGoal() {
        Maze maze = new Maze();
        maze.setDimensions(1, 1);
        maze.setCell(new Position(0, 0), 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 0));

        ISearchable searchable = new SearchableMaze(maze);
        BestFirstSearch searcher = new BestFirstSearch();
        Solution solution = searcher.solve(searchable);

        assertNotNull(solution, "solution should not be null for trivial maze");
        assertEquals(1, solution.getSolutionPath().size(), "path should contain exactly one step");
    }
}
