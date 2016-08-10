import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExampleApp {

    private Server server;

    public static void main(String[] args) throws Exception {
        new ExampleApp().run();
    }

    void run() throws Exception {
        int defaultPort = 8080;
        int adminPort = 9999;
        server = new Server();
        server.addConnector(serverConnector(defaultPort, server));
        server.addConnector(serverConnector(adminPort, server));
        server.setHandler(helloServletHandler());
        server.start();
    }

    void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler helloServletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new HelloWorldHttpServlet()), "/hello");
        servletHandler.addServlet(new ServletHolder(new StarWarsCharacterHttpServlet()), "/starWarsCharacter");
        return servletHandler;
    }

    private static class HelloWorldHttpServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.getWriter().write("Hello from a servlet!!!!");
        }
    }

    private ServerConnector serverConnector(int port, Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        return connector;
    }
}
