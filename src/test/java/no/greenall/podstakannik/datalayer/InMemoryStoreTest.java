package no.greenall.podstakannik.datalayer;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Responsibility: Test InMemoryStore.class.
 */
public class InMemoryStoreTest {
    @Test
    public void it_exists() {
        assertNotNull(new InMemoryStore("empty"));
    }
}
