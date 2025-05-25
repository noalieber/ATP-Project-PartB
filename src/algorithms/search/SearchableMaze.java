package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * adapter that allows a maze to be used as a searchable domain.
 * wraps the Maze class and translates its positions into MazeState objects.
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState[][] states;

    /**
     * builds a searchable wrapper over a given maze.
     * initializes MazeState objects only for path cells (value 0).
     * @param maze the maze to wrap
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;

        Position start = maze.getStartPosition();
        Position goal = maze.getGoalPosition();

        int rows = maze.getRows();
        int cols = maze.getColumns();
        states = new MazeState[rows][cols];

        // create a MazeState only for walkable positions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze.getGrid()[i][j] == 0) {
                    states[i][j] = new MazeState(new Position(i, j));
                }
            }
        }
    }

    /**
     * returns the start state of the maze.
     */
    @Override
    public AState getStartState() {
        Position p = maze.getStartPosition();
        return states[p.getRowIndex()][p.getColumnIndex()];
    }

    /**
     * returns the goal state of the maze.
     */
    @Override
    public AState getGoalState() {
        Position p = maze.getGoalPosition();
        return states[p.getRowIndex()][p.getColumnIndex()];
    }

    /**
     * returns all valid neighbors of a given state,
     * including diagonals if both adjacent cells are walkable.
     */
    @Override
    public List<AState> getAllPossibleStates(AState state) {
        MazeState mazeState = (MazeState) state;
        Position pos = mazeState.getPosition();
        int row = pos.getRowIndex();
        int col = pos.getColumnIndex();

        // skip walls
        if (maze.getGrid()[row][col] != 0) {
            return Collections.emptyList();
        }

        List<AState> successors = new ArrayList<>();

        int[][] cardinalDirs = { {-1,0}, {1,0}, {0,-1}, {0,1} };
        int[][] diagonalDirs = { {-1,-1}, {-1,1}, {1,-1}, {1,1} };

        // check up/down/left/right
        for (int[] dir : cardinalDirs) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isWalkable(newRow, newCol)) {
                successors.add(states[newRow][newCol]);
            }
        }

        // check diagonals if adjacent steps are valid
        for (int[] diag : diagonalDirs) {
            int newRow = row + diag[0];
            int newCol = col + diag[1];
            int adjRow = row + diag[0];
            int adjCol = col;
            int sideRow = row;
            int sideCol = col + diag[1];

            if (isWalkable(newRow, newCol)
                    && isWalkable(adjRow, adjCol)
                    && isWalkable(sideRow, sideCol)) {
                successors.add(states[newRow][newCol]);
            }
        }

        return successors;
    }

    /**
     * returns true if a cell is in bounds and not a wall.
     */
    private boolean isWalkable(int row, int col) {
        return isInBounds(row, col) && maze.getGrid()[row][col] == 0 && states[row][col] != null;
    }

    /**
     * checks that a row and col are inside maze bounds.
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getColumns();
    }
}
