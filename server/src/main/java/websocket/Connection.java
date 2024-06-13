package websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

public class Connection {
    public String playerName;
    public Session session;

    public Connection(Session session, String playerName) {
        this.session = session;
        this.playerName = playerName;
    }

    // how we will send a message - hmmmm
    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
