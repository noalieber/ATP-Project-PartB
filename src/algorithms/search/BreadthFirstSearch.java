package algorithms.search;

import java.util.*;

/**
 * breadth first search implementation using a simple queue.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    /**
     * returns the algorithm name.
     *
     * @return name string
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }

    /**
     * solves the given searchable problem using bfs.
     *
     * @param domain the searchable problem
     * @return a solution from start to goal (or empty if no path)
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        AState start = domain.getStartState();
        AState goal = domain.getGoalState();

        Queue<AState> openQueue = new LinkedList<>();
        Set<AState> closedSet = new HashSet<>();

        start.setCost(0);
        openQueue.add(start);

        while (!openQueue.isEmpty()) {
            AState current = openQueue.poll();
            visitedNodes++;
            closedSet.add(current);

            // if we reached the goal
            if (current.equals(goal)) {
                return new Solution(current);
            }

            // check neighbors
            for (AState neighbor : domain.getAllPossibleStates(current)) {
                if (!closedSet.contains(neighbor) && !openQueue.contains(neighbor)) {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + 1); // uniform cost
                    openQueue.add(neighbor);
                }
            }
        }

        // no path found
        return new Solution(null);
    }
}
