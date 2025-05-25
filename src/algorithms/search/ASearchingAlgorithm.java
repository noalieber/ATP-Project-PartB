package algorithms.search;

/**
 * base class for all search algorithms.
 * handles counting of visited nodes during the search.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    // counts how many nodes were evaluated during the search
    protected int visitedNodes = 0;

    /**
     * returns the name of the search algorithm.
     *
     * @return algorithm name
     */
    @Override
    public abstract String getName();

    /**
     * solves a given searchable problem.
     *
     * @param domain the problem to solve
     * @return a solution object containing the path
     */
    @Override
    public abstract Solution solve(ISearchable domain);

    /**
     * returns how many nodes were evaluated by the algorithm.
     *
     * @return number of evaluated nodes
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
}
