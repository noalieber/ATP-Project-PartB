package algorithms.search;

import java.util.List;

/**
 * interface for searchable problems like mazes or graphs.
 * defines how to get the start, goal, and neighbors for a state.
 */
public interface ISearchable {

    /**
     * returns the starting state of the problem.
     *
     * @return the start state
     */
    AState getStartState();

    /**
     * returns the goal state we want to reach.
     *
     * @return the goal state
     */
    AState getGoalState();

    /**
     * returns all valid next steps from a given state.
     *
     * @param state the current state
     * @return list of neighboring states
     */
    List<AState> getAllPossibleStates(AState state);
}
