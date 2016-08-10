package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.HelloWorldHttpServlet;
import servlets.StarWarsCharacterHttpServlet;

public class ExampleApp {

    private Server server;

    public static void main(String[] args) throws Exception {
        new ExampleApp().run();
    }

    public void run() throws Exception {
        int defaultPort = 8080;
        int adminPort = 9999;
        server = new Server();
        server.addConnector(serverConnector(defaultPort, server));
        server.addConnector(serverConnector(adminPort, server));
        server.setHandler(helloServletHandler());
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler helloServletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new HelloWorldHttpServlet()), "/hello");
        servletHandler.addServlet(new ServletHolder(new StarWarsCharacterHttpServlet()), "/starWarsCharacter");
        return servletHandler;
    }

    private ServerConnector serverConnector(int port, Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        return connector;
    }
}
