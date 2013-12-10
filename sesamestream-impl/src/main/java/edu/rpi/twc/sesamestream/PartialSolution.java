package edu.rpi.twc.sesamestream;

/**
 * An intermediate result in the answering of a continuous query.
 * It contains a {@link SubscriptionImpl}, zero or more already-completed bindings of variables to values,
 * and a basic graph pattern (BGP) of one or more still-to-be-matched triple patterns.
 * Logically, a SesameStream query engine's index is a set of <code>PartialSolution</code>s.
 *
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class PartialSolution {

    private final SubscriptionImpl subscription;

    private final LList<TriplePattern> graphPattern;

    private final VarList bindings;

    /**
     * Constructs an initial partial solution with no bound variables.
     * This is the start state of a query, before any statements have been received.
     *
     * @param subscription an object containing the query and the handler for query results
     */
    public PartialSolution(final SubscriptionImpl subscription) {
        this.subscription = subscription;

        bindings = null;

        // TODO: make this immutable, for performance sake
        graphPattern = subscription.getQuery().getGraphPattern();
    }

    /**
     * Constructs an partial solution with the specified bindings and triple patterns.
     *
     * @param subscription an object containing the query and the handler for query results
     * @param graphPattern the still-to-be-matched RDF triple patterns
     * @param bindings the already-completed bindings of variables to values
     */
    public PartialSolution(final SubscriptionImpl subscription,
                           final LList<TriplePattern> graphPattern,
                           final VarList bindings) {
        this.subscription = subscription;
        this.graphPattern = graphPattern;
        this.bindings = bindings;
    }

    /**
     * @return the already-completed bindings of variables to values of this partial solution
     */
    public VarList getBindings() {
        return bindings;
    }

    /**
     * @return the still-to-be-matched RDF triple patterns of this partial solution
     */
    public LList<TriplePattern> getGraphPattern() {
        return graphPattern;
    }

    /**
     * @return the subscription to which any complete solutions to this <code>PartialSolution</code> will be delivered
     */
    public SubscriptionImpl getSubscription() {
        return subscription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PartialSolution(").append(bindings).append(", {");

        boolean first = true;
        LList<TriplePattern> cur = graphPattern;
        while (!cur.isNil()) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }

            sb.append(cur.getValue());
            cur = cur.getRest();
        }

        sb.append("})");
        return sb.toString();
    }
}