package org.tensin.common.snowflake;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * The Class SnowflakeIDTestCase.
 */
public class NodeIDGeneratorTestCase {

    private static final Collection<String> JVMS = ImmutableList.of("E01", "E02", "E03", "E04", "W01", "C01", "Z01", "S01", "S02", "T01", "R01", "ABCD",
            "ABCDE", "12345", "A5", "B4");

    private static final Collection<String> JVMS2 = ImmutableList.of("LTPE01", "LTPE02", "LTPE03", "LTPE04", "LTPW01", "LTPC01", "LTPZ01", "LTPS01", "LTPS02",
            "LTPT01", "LTPR01");

    private Collection<SnowflakeID> buildGenerators(final Collection<String> noeuds, final Collection<String> jvms) {
        final Collection<SnowflakeID> generators = new ArrayList<SnowflakeID>();
        for (final String noeud : noeuds) {
            for (final String jvm : jvms) {
                generators.add(SnowflakeIDBuilder.build(NodeIDGenerator.build(noeud, jvm)));

            }
        }
        return generators;
    }

    private void test(final Collection<String> nodes, final Collection<String> jvms) {
        final Collection<Long> ids = new TreeSet<Long>();
        final Collection<SnowflakeID> generators = buildGenerators(nodes, jvms);
        for (final SnowflakeID generator : generators) {
            ids.add(Long.valueOf(generator.node()));
            System.out.println(generator.node());
        }
        Assert.assertEquals(generators.size(), ids.size());

    }

    @Test
    public void testAllCombinaisons() {
        final Collection<Collection<String>> allPotentialServersNames = new ArrayList<Collection<String>>();
        allPotentialServersNames.add(ImmutableList.of("yvas0920", "yvas0930", "yvas0bf0"));
        allPotentialServersNames.add(ImmutableList.of("yvasrjs1", "yvasrjs2", "yvasrjs3"));
        allPotentialServersNames.add(ImmutableList.of("yvas0hn0", "yvas0hp0", "yvas0hr0"));
        allPotentialServersNames.add(ImmutableList.of("yvas0eg0", "yvas0eh0"));
        allPotentialServersNames.add(ImmutableList.of("APP-INSTANCE-1", "APP-INSTANCE-2", "APP-INSTANCE-3"));

        final Collection<Collection<String>> allJvmsPotentialNames = new ArrayList<Collection<String>>();
        allJvmsPotentialNames.add(JVMS);
        allJvmsPotentialNames.add(JVMS2);
        allJvmsPotentialNames.add(ImmutableList.of("T01", "S01", "Z01", "S02"));

        for (final Collection<String> serversNames : allPotentialServersNames) {
            for (final Collection<String> jvmsNames : allJvmsPotentialNames) {
                System.out.println("Comparing [" + serversNames + "] with [" + jvmsNames + "]");
                test(serversNames, jvmsNames);
            }
        }
    }

    @Test
    public void testSnowflakeIDGenerators() throws InterruptedException {
        final Collection<SnowflakeID> generators = new ArrayList<SnowflakeID>();
        final Collection<Long> ids = new TreeSet<Long>();
        for (final String jvm : JVMS) {
            generators.add(SnowflakeIDBuilder.build(NodeIDGenerator.build("hostname", jvm)));
        }

        for (final SnowflakeID generator : generators) {
            ids.add(Long.valueOf(generator.node()));
            System.out.println(generator.node());
        }
        Assert.assertEquals(generators.size(), ids.size());
    }
}