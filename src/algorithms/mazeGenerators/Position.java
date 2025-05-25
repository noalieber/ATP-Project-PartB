package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * represents a position in a 2D grid.
 * used for maze cells and coordinates.
 */
public class Position implements Serializable {
    private int row;
    private int column;

    /**
     * creates a position with given row and column.
     *
     * @param row the row index
     * @param column the column index
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * returns the row index of this position.
     *
     * @return row number
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * returns the column index of this position.
     *
     * @return column number
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * returns a string representation like {row,column}.
     */
    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    /**
     * checks if two positions are equal, same row and column.
     *
     * @param obj the object to compare with
     * @return true if same position, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }

    /**
     * generates a hash code for this position.
     */
    @Override
    public int hashCode() {
        return 31 * row + column;
    }
}
