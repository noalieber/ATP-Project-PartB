package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * represents a path from start to goal, built by tracing back from the goal state.
 */
public class Solution implements Serializable {
    private ArrayList<AState> path;

    /**
     * builds a solution by walking backwards from the goal state to the start.
     * @param goalState the final state in the solution path
     */
    public Solution(AState goalState) {
        path = new ArrayList<>();
        if (goalState == null) return; // no path found

        AState current = goalState;
        while (current != null) {
            path.add(current);
            current = current.getCameFrom(); // trace back to the start
        }

        Collections.reverse(path); // reverse to get start → goal order
    }

    /**
     * returns the list of states that form the solution path.
     * @return a list from start to goal
     */
    public ArrayList<AState> getSolutionPath() {
        return path;
    }
}
