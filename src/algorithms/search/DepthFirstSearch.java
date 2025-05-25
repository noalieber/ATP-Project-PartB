package algorithms.search;

import java.util.*;

/**
 * depth first search implementation using a stack.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    /**
     * returns the name of the algorithm.
     *
     * @return the name string
     */
    @Override
    public String getName() {
        return "Depth First Search";
    }

    /**
     * runs dfs on the given searchable domain.
     *
     * @param domain the searchable problem
     * @return solution from start to goal if found, otherwise empty
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        AState start = domain.getStartState();
        AState goal = domain.getGoalState();

        Stack<AState> stack = new Stack<>();
        Set<AState> visited = new HashSet<>();

        start.setCost(0);
        stack.push(start);

        while (!stack.isEmpty()) {
            AState current = stack.pop();
            visitedNodes++;
            visited.add(current);

            // goal reached
            if (current.equals(goal)) {
                return new Solution(current);
            }

            // explore neighbors
            for (AState neighbor : domain.getAllPossibleStates(current)) {
                if (!visited.contains(neighbor) && !stack.contains(neighbor)) {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + 1);
                    stack.push(neighbor);
                }
            }
        }

        // no path found
        return new Solution(null);
    }
}
