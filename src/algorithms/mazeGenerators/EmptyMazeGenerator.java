package algorithms.mazeGenerators;

/**
 * simple maze generator that creates an empty maze.
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * creates an empty maze with all cells as free space.
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return the generated empty maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze();
        maze.setDimensions(rows, columns); // creates a maze with all cells = 0
        return maze;
    }
}
