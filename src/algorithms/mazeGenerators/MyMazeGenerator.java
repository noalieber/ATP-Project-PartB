package algorithms.mazeGenerators;

import java.util.*;

/**
 * generates a maze using iterative backtracking with a stack.
 * always places the entry at the top-left (0,0) and the exit at the bottom-right (rows-1, cols-1).
 */
public class MyMazeGenerator extends AMazeGenerator {

    /**
     * generates a maze with the given dimensions.
     * ensures entry at (0,0) and exit at (rows-1, cols-1).
     *
     * @param rows number of rows in the maze
     * @param cols number of columns in the maze
     * @return a maze object with walls and paths
     */
    @Override
    public Maze generate(int rows, int cols) {
        // use odd dimensions internally to allow walls between paths
        int genRows = (rows % 2 == 0) ? rows + 1 : rows;
        int genCols = (cols % 2 == 0) ? cols + 1 : cols;

        Maze maze = new Maze();
        maze.setDimensions(genRows, genCols);
        int[][] grid = maze.getGrid();
        boolean[][] visited = new boolean[genRows][genCols];

        // fill the entire grid with walls
        for (int i = 0; i < genRows; i++) {
            Arrays.fill(grid[i], 1);
        }

        Stack<Position> stack = new Stack<>();
        Random rand = new Random();

        // start maze generation from cell (1,1)
        Position start = new Position(1, 1);
        grid[1][1] = 0;
        visited[1][1] = true;
        stack.push(start);

        // possible directions, 2 step jumps
        int[][] directions = { {0, 2}, {2, 0}, {0, -2}, {-2, 0} };

        while (!stack.isEmpty()) {
            Position current = stack.pop();
            List<Position> neighbors = new ArrayList<>();

            // find all unvisited neighbors
            for (int[] dir : directions) {
                int newRow = current.getRowIndex() + dir[0];
                int newCol = current.getColumnIndex() + dir[1];
                if (isInBounds(newRow, newCol, genRows, genCols) && !visited[newRow][newCol]) {
                    neighbors.add(new Position(newRow, newCol));
                }
            }

            if (!neighbors.isEmpty()) {
                stack.push(current);
                Position next = neighbors.get(rand.nextInt(neighbors.size()));

                int betweenRow = (current.getRowIndex() + next.getRowIndex()) / 2;
                int betweenCol = (current.getColumnIndex() + next.getColumnIndex()) / 2;

                // remove the wall between current and next
                grid[betweenRow][betweenCol] = 0;
                grid[next.getRowIndex()][next.getColumnIndex()] = 0;
                visited[next.getRowIndex()][next.getColumnIndex()] = true;
                stack.push(next);
            }
        }

        // set entry and exit and make sure they are open and reachable
        Position entry = new Position(0, 0);
        Position exit = new Position(rows - 1, cols - 1);

        grid[1][0] = 0; // connect (1,0) to the generated maze
        grid[0][0] = 0; // actual entry

        grid[genRows - 2][genCols - 1] = 0; // connect (genRows-2, genCols-1) to maze
        grid[genRows - 1][genCols - 1] = 0; // actual exit

        // cut the grid back to the requested dimensions
        maze.setGrid(trimGrid(grid, rows, cols));
        maze.setStartPosition(entry);
        maze.setGoalPosition(exit);

        return maze;
    }

    /**
     * checks if a cell is valid for maze generation.
     *
     * @param row current row
     * @param col current column
     * @param maxRows total number of rows
     * @param maxCols total number of columns
     * @return true if the cell is inside the maze boundaries
     */
    private boolean isInBounds(int row, int col, int maxRows, int maxCols) {
        return row > 0 && row < maxRows - 1 && col > 0 && col < maxCols - 1;
    }

    /**
     * trims the generated grid to the requested final dimensions.
     *
     * @param fullGrid the internally generated maze grid
     * @param targetRows final number of rows
     * @param targetCols final number of columns
     * @return a trimmed version of the maze
     */
    private int[][] trimGrid(int[][] fullGrid, int targetRows, int targetCols) {
        int[][] trimmed = new int[targetRows][targetCols];
        for (int i = 0; i < targetRows; i++) {
            for (int j = 0; j < targetCols; j++) {
                trimmed[i][j] = fullGrid[i][j];
            }
        }
        return trimmed;
    }
}
