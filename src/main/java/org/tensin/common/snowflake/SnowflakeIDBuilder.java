package org.tensin.common.snowflake;

/**
 * The Class SnowflakeIDBuilder.
 */
public final class SnowflakeIDBuilder {

    /**
     * Builds the snowlake ID.
     * Beware, there may be some collision with nodes ID (many input strings modulo MAX_NODE = any input string will be translated to a value between 1 and 1024
     *
     * @param s
     *            the s
     * @return the bean log snowflake id
     */
    public static SnowflakeID build(final INodeIDGenerator generator) {
        return new SnowflakeID(generator.generate() % SnowflakeID.MAX_NODE);
    }

    /**
     * Builds the snowflake ID.
     *
     * @param node
     *            the node
     * @return the bean log snowflake id
     */
    public static SnowflakeID build(final int node) {
        return new SnowflakeID(node);
    }
}