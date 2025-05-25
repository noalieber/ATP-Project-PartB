package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * represents a maze made of paths (0) and walls (1).
 * includes a start and goal position.
 */
public class Maze implements Serializable {
    private int rows;
    private int columns;
    private int[][] grid;
    private Position startPosition;
    private Position goalPosition;

    /**
     * empty constructor for maze.
     */
    public Maze() {
    }

    /**
     * constructs a Maze instance from a byte array.
     * @param data byte array representing an uncompressed maze
     */
    public Maze(byte[] data) {
        int index = 0;

        this.rows = data[index++] & 0xFF;
        this.columns = data[index++] & 0xFF;

        int startRow = data[index++] & 0xFF;
        int startCol = data[index++] & 0xFF;
        int goalRow = data[index++] & 0xFF;
        int goalCol = data[index++] & 0xFF;

        this.startPosition = new Position(startRow, startCol);
        this.goalPosition = new Position(goalRow, goalCol);

        this.grid = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.grid[i][j] = data[index++] & 0xFF;
            }
        }
    }


    /**
     * sets the dimensions of the maze and initializes the grid.
     * sets default start and goal positions.
     *
     * @param rows number of rows
     * @param columns number of columns
     */
    public void setDimensions(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new int[rows][columns];
        this.startPosition = new Position(0, 0);
        this.goalPosition = new Position(rows - 1, columns - 1);
    }

    /**
     * sets a value (0 or 1) at a specific cell in the grid.
     *
     * @param pos the position to update
     * @param value 0 = path, 1 = wall
     */
    public void setCell(Position pos, int value) {
        grid[pos.getRowIndex()][pos.getColumnIndex()] = value;
    }

    /**
     * gets the value at a specific cell.
     *
     * @param pos the position to check
     * @return 0 or 1
     */
    public int getCell(Position pos) {
        return grid[pos.getRowIndex()][pos.getColumnIndex()];
    }

    /**
     * @return the full grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return start position of the maze
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return goal position of the maze
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * sets the start position.
     *
     * @param startPosition position to start from
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * sets the goal position.
     *
     * @param goalPosition position to reach
     */
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * prints the maze to console. uses 'S' for start, 'E' for goal, '1' for walls and '0' for paths.
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Position current = new Position(i, j);
                if (current.equals(startPosition)) {
                    System.out.print("S");
                } else if (current.equals(goalPosition)) {
                    System.out.print("E");
                } else {
                    System.out.print(grid[i][j] == 1 ? "1" : "0");
                }
            }
            System.out.println();
        }
    }

    /**
     * replaces the grid with a new one and updates dimensions.
     * keeps or sets default start and goal positions if missing.
     *
     * @param newGrid new maze layout
     */
    public void setGrid(int[][] newGrid) {
        if (newGrid == null) return;
        this.rows = newGrid.length;
        this.columns = newGrid[0].length;
        this.grid = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(newGrid[i], 0, this.grid[i], 0, columns);
        }

        if (this.startPosition == null) {
            this.startPosition = new Position(0, 0);
        }
        if (this.goalPosition == null) {
            this.goalPosition = new Position(rows - 1, columns - 1);
        }
    }

    /**
     * converts the maze into an uncompressed byte array representation.
     * @return byte array representing this maze
     */
    public byte[] toByteArray() {
        int size = 6 + rows * columns;
        byte[] data = new byte[size];
        int index = 0;

        data[index++] = (byte) rows;
        data[index++] = (byte) columns;
        data[index++] = (byte) startPosition.getRowIndex();
        data[index++] = (byte) startPosition.getColumnIndex();
        data[index++] = (byte) goalPosition.getRowIndex();
        data[index++] = (byte) goalPosition.getColumnIndex();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[index++] = (byte) grid[i][j];
            }
        }

        return data;
    }
}
