package org.tensin.common.snowflake;

/**
 * The Class SnowflakeID.
 */
public class SnowflakeID {

    /** The Constant NODE_SHIFT. */
    public static final int NODE_SHIFT = 10;

    /** The Constant SEQ_SHIFT. */
    public static final int SEQ_SHIFT = 12;

    /** The Constant MAX_NODE. */
    public static final short MAX_NODE = 1024;

    /** The Constant MAX_SEQUENCE. */
    public static final short MAX_SEQUENCE = 4096;

    /** The sequence. */
    private short sequence;

    /** The reference time. */
    private long referenceTime;

    /** The node. */
    private final int node;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node
     *            This is an id you use to differentiate different nodes.
     */
    protected SnowflakeID(final int node) {
        if ((node < 0) || (node > MAX_NODE)) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s, reveied %s", 0, MAX_NODE, node));
        }
        this.node = node;
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long next() {
        long currentTime = System.currentTimeMillis();
        long counter;

        synchronized (this) {
            if (currentTime < referenceTime) {
                throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
            } else if (currentTime > referenceTime) {
                sequence = 0;
            } else {
                if (sequence < MAX_SEQUENCE) {
                    sequence++;
                } else {
                    throw new RuntimeException("Sequence exhausted at " + sequence);
                }
            }
            counter = sequence;
            referenceTime = currentTime;
        }

        return (currentTime << NODE_SHIFT << SEQ_SHIFT) | (node << SEQ_SHIFT) | counter;
    }

    /**
     * Node.
     *
     * @return the int
     */
    public int node() {
        return node;
    }
}