import org.eclipse.jetty.server.Server;

/**
 * Created by Omar on 20/07/16.
 */
public class EntryPoint {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        Server server = new Server(port);
        server.start();
        server.join();
    }
}
