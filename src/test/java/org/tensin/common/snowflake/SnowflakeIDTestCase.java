package org.tensin.common.snowflake;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Assert;
import org.junit.Test;

public class SnowflakeIDTestCase {

    @Test
    public void testThreadedSnowflakeID() throws InterruptedException {
        final Collection<Long> generatedIds = new ConcurrentSkipListSet<>();
        final SnowflakeID generator = SnowflakeIDBuilder.build(1);
        final int nbThreads = 5;
        final int nbIdsPerThread = 5000;
        final Thread[] threads = new Thread[nbThreads];
        for (int t = 0; t < nbThreads; t++) {
            final Thread thread = new Thread() {

                @Override
                public void run() {
                    super.run();
                    for (int i = 0; i < nbIdsPerThread; i++) {
                        final long id = generator.next();
                        generatedIds.add(Long.valueOf(id));
                    }
                }

            };
            threads[t] = thread;
            thread.start();
        }

        for (int t = 0; t < nbThreads; t++) {
            threads[t].join();
        }

        Assert.assertEquals(nbThreads * nbIdsPerThread, generatedIds.size());
    }
}