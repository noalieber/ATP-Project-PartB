package algorithms.mazeGenerators;

/**
 * interface for classes that generate mazes.
 */
public interface IMazeGenerator {

    /**
     * creates a maze with the given number of rows and columns.
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return a new maze object
     */
    Maze generate(int rows, int columns);

    /**
     * measures how long it takes to generate a maze.
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return time in ms.
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
