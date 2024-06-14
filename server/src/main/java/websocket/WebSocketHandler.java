package websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketHandler {
    public Map<Integer, Set<Session>> connections = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("WS recieved this: " + message);
        session.getRemote().sendString("WS response: " + message);
    }


    // on message code to do a simple echo

}
