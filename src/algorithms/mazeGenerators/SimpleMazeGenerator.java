package algorithms.mazeGenerators;

import java.util.Random;

/**
 * simple maze generator that fills the grid with random walls and paths.
 * always keeps the start and goal positions open.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * generates a maze with random walls and paths.
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return a new maze instance with start and goal positions
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze();
        maze.setDimensions(rows, columns);

        Random rand = new Random();

        // randomly assign 0 (path) or 1 (wall) to each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.setCell(new Position(i, j), rand.nextDouble() < 0.3 ? 1 : 0);
            }
        }

        // set fixed start and goal positions
        Position start = new Position(0, 0);
        Position goal = new Position(rows - 1, columns - 1);
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        // make sure start and goal are open
        maze.setCell(start, 0);
        maze.setCell(goal, 0);

        return maze;
    }
}
