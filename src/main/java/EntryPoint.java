import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Omar on 20/07/16.
 */
public class EntryPoint {

    public static void main(String[] args) throws Exception {
        int defaultPort = 8080;
        int adminPort = 9999;
        Server server = new Server();
        server.addConnector(serverConnector(defaultPort, server));
        server.addConnector(serverConnector(adminPort, server));
        server.setHandler(new HelloJettyHandler());
        server.start();
        server.join();
    }

    private static ServerConnector serverConnector(int port, Server server) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        return connector;
    }

    private static class HelloJettyHandler extends AbstractHandler {
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
            request.setHandled(true);
            httpServletResponse.getWriter().write("Hello embedded jetty!!!");
        }
    }
}
