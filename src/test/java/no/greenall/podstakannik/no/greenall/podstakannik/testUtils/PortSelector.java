package no.greenall.podstakannik.no.greenall.podstakannik.testUtils;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Responsibility: Find a free port to assign to App for testing.
 */
public final class PortSelector {

    private PortSelector() { }

    public static int randomFree() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to pick a random, free, local port.", e);
        }
    }
}
