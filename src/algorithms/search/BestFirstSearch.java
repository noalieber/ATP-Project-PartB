package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.*;

/**
 * best first search algorithm.
 * uses manhattan distance to choose the next state to explore.
 */
public class BestFirstSearch extends ASearchingAlgorithm {

    /**
     * returns the name of the algorithm.
     *
     * @return name as string
     */
    @Override
    public String getName() {
        return "Best First Search";
    }

    /**
     * solves a searchable problem using best first search.
     * the algorithm prefers states that are closer to the goal.
     *
     * @param domain the problem to solve
     * @return a solution object (path from start to goal)
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        AState start = domain.getStartState();
        AState goal = domain.getGoalState();

        // priority queue ordered by estimated distance to goal
        PriorityQueue<AState> open = new PriorityQueue<>(Comparator.comparingDouble(state -> estimate(state, goal)));
        HashSet<AState> openSet = new HashSet<>();
        HashSet<AState> closed = new HashSet<>();

        open.add(start);
        openSet.add(start);
        start.setCameFrom(null);

        while (!open.isEmpty()) {
            AState current = open.poll();
            openSet.remove(current);
            visitedNodes++;
            closed.add(current);

            if (current.equals(goal)) {
                return new Solution(current);
            }

            for (AState neighbor : domain.getAllPossibleStates(current)) {
                if (closed.contains(neighbor)) continue;

                // if we haven't visited this neighbor yet
                if (!openSet.contains(neighbor)) {
                    neighbor.setCameFrom(current);
                    open.add(neighbor);
                    openSet.add(neighbor);
                }
            }
        }

        // no solution found
        return new Solution(null);
    }

    /**
     * estimates how close one state is to another using manhattan distance.
     *
     * @param a current state
     * @param b goal state
     * @return estimated cost (lower is better)
     */
    private double estimate(AState a, AState b) {
        if (a instanceof MazeState && b instanceof MazeState) {
            Position p1 = ((MazeState) a).getPosition();
            Position p2 = ((MazeState) b).getPosition();
            return Math.abs(p1.getRowIndex() - p2.getRowIndex()) +
                   Math.abs(p1.getColumnIndex() - p2.getColumnIndex());
        }
        return Double.POSITIVE_INFINITY;
    }
}
