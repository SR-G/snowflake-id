package org.tensin.common.snowflake;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * The Class NodeIDGenerator.
 */
public class NodeIDGenerator implements INodeIDGenerator {

    /**
     * The Class HashHelper.
     */
    public static final class HashHelper {

        /**
         * Generate hash from guava.
         *
         * @param values
         *            the values
         * @return the int
         */
        public static int generateHashFromGuava(final String... values) {
            int result = 0;
            final HashFunction hf = Hashing.md5();
            final Hasher hasher = hf.newHasher();
            for (final String value : values) {
                hasher.putString(value, Charsets.UTF_8);
            }
            final HashCode hc = hasher.hash();
            result = hc.asInt();
            if (result < 0) {
                result = result * -1;
            }
            return result;
        }

        /**
         * Generate hash from int.
         * Solution 2 - n'accepte que des A-Z0-9
         *
         * @param value
         *            the value
         * @return the int
         */
        public static int generateHashFromInt(final String value) {
            int result = 0;
            result = Integer.parseInt(value, 36);
            return result;
        }

        /**
         * Generate hash from string hashcode.
         * Solution 3 : la plus simple
         *
         * @param value
         *            the value
         * @return the int
         */
        public static int generateHashFromStringHashcode(final String value) {
            int result = 0;
            result = value.hashCode();
            if (result < 0) {
                result = result * -1;
            }
            return result;
        }

        /**
         * Instantiates a new hash helper.
         */
        private HashHelper() {

        }
    }

    /**
     * Builds the.
     *
     * @param node
     *            the node
     * @param jvm
     *            the jvm
     * @return the i node id generator
     */
    public static INodeIDGenerator build(final String node, final String jvm) {
        return new NodeIDGenerator(node, jvm);
    }

    /** The value. */
    private final String node;

    /** The jvm. */
    private final String jvm;

    /**
     * Instantiates a new bean log snowflake id pyramide node generator.
     *
     * @param node
     *            the node
     * @param jvm
     *            the jvm
     */
    private NodeIDGenerator(final String node, final String jvm) {
        this.node = node;
        this.jvm = jvm;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.inetpsa.elf.beanlogs.INodeIDGenerator#generate()
     */
    @Override
    public int generate() {
        // return HashHelper.generateHashManually(node + jvm);
        // return HashHelper.generateHashFromInt(node + jvm);
        // return HashHelper.generateHashFromGuava(jvm, node);
        return HashHelper.generateHashFromStringHashcode(node + jvm);
    }
}