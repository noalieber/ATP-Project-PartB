package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.Objects;

/**
 * represents a state in a maze.
 * each MazeState holds a Position in the maze.
 */
public class MazeState extends AState implements Serializable {
    private Position position;

    /**
     * creates a maze state from a given position.
     * @param pos the position in the maze
     */
    public MazeState(Position pos) {
        this.position = pos;
    }

    /**
     * gets the position this state represents.
     * @return the maze position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * checks if two states are equal based on their position.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MazeState)) return false;
        MazeState other = (MazeState) o;
        return Objects.equals(position, other.position);
    }

    /**
     * generates hash code based on the position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    /**
     * returns the position as a string.
     */
    @Override
    public String toString() {
        return position.toString();
    }
}
