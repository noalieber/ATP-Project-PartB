package algorithms.search;

/**
 * interface for search algorithms.
 * defines how the algorithm works and what it reports.
 */
public interface ISearchingAlgorithm {

    /**
     * returns the name of the algorithm.
     *
     * @return name of the algorithm
     */
    String getName();

    /**
     * runs the search on a given searchable domain.
     *
     * @param domain the problem to solve
     * @return a solution from start to goal if one exists
     */
    Solution solve(ISearchable domain);

    /**
     * returns how many states were checked during the search.
     *
     * @return number of evaluated nodes
     */
    int getNumberOfNodesEvaluated();
}
