package algorithms.mazeGenerators;

/**
 * base class for maze generators.
 * provides a default implementation for measuring generation time.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * generates a maze with the given number of rows and columns.
     * subclasses must implement this method.
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return the generated maze
     */
    @Override
    public abstract Maze generate(int rows, int columns);

    /**
     * measures how long it takes to generate a maze (in milliseconds).
     *
     * @param rows number of rows to generate
     * @param columns number of columns to generate
     * @return time taken in milliseconds
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long start = System.currentTimeMillis();
        generate(rows, columns);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
