package websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

public class Connection {
    public String authToken;
    public Session session;

    public Connection(Session session, String authToken) {
        this.session = session;
        this.authToken = authToken;
    }

    // how we will send a message - hmmmm
    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
