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

    public static void main(String[] args) throws Exception {
        new ExampleApp().run();
    }

    private void run() throws Exception {
        int defaultPort = 8080;
        int adminPort = 9999;
        Server server = new Server();
        server.addConnector(serverConnector(defaultPort, server));
        server.addConnector(serverConnector(adminPort, server));
        server.setHandler(helloServletHandler());
        server.start();
        server.join();
    }

    private ServletContextHandler helloServletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new HelloWorldHttpServlet()), "/hello");
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
