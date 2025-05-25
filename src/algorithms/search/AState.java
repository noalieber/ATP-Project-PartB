package algorithms.search;

/**
 * base class for a state in a search problem.
 * each problem (like maze) should extend this class to define its own state.
 */
public abstract class AState {
    protected double cost; // how much it costs to reach this state
    protected AState cameFrom; // previous state in the path

    /**
     * returns the cost to reach this state.
     *
     * @return cost value
     */
    public double getCost() {
        return cost;
    }

    /**
     * sets the cost to reach this state.
     *
     * @param cost cost value to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * returns the state this one came from.
     *
     * @return previous state
     */
    public AState getCameFrom() {
        return cameFrom;
    }

    /**
     * sets the state this one came from.
     *
     * @param cameFrom the state that led to this one
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    /**
     * checks if this state equals another one.
     *
     * @param obj another object to compare
     * @return true if both represent the same state
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * returns a unique code for this state, used in hash tables.
     *
     * @return hash code
     */
    @Override
    public abstract int hashCode();
}
