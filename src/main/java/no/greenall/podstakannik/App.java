package no.greenall.podstakannik;

import no.greenall.podstakannik.endpoint.Resource;
import no.greenall.podstakannik.endpoint.SPARQLEndpoint;
import org.apache.jena.system.JenaSystem;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Responsibility: Provide start class for application.
 */
public final class App {

    private static final int APP_PORT = 8005;
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private final int port;
    private Server jettyServer;
    private final String persistenceDirectory;
    private static final String DATA_DIRECTORY = System.getProperty("DATA_DIRECTORY", "/datastore");

    App(int port, String persistenceDirectory) {
        this.port = port;
        this.persistenceDirectory = persistenceDirectory;
    }

    public static void main(String[] args) {
        App app = new App(APP_PORT, DATA_DIRECTORY);
        try {
            JenaSystem.init();
            app.startSync();
        } catch (Exception exception) {
            LOG.error("App initialisation failed:");
            exception.printStackTrace(System.err);
        }
    }

    private void startSync() throws Exception {
        try {
            startAsync();
            join();
        } finally {
            stop();
        }
    }

    void startAsync() throws Exception {
        setUpApp();
    }

    private void setUpApp() throws Exception {
        jettyServer = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(ServerProperties.PROVIDER_CLASSNAMES,
                String.join(",", asList(
                        SPARQLEndpoint.class.getCanonicalName(),
                        Resource.class.getCanonicalName()
                )));

        HandlerCollection collection = new HandlerCollection();
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setExtended(false);
        requestLogHandler.setRequestLog(requestLog);
        collection.setHandlers(new Handler[]{context, requestLogHandler});
        jettyServer.setHandler(collection);
        jettyServer.start();
        LOG.info("App started on port: " + port);
    }

    private void stop() throws Exception {
        LOG.info("Stopping App on port: " + port);
        try {
            jettyServer.stop();
        } finally {
            jettyServer.destroy();
        }
    }
}
