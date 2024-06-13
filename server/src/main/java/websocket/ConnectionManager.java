package websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    Map<String, Connection> connectionsMap;

    public ConnectionManager() {
        connectionsMap = new HashMap<>();
    }

    public void add(String playerName, Session session){
        connectionsMap.put(playerName, new Connection(session, playerName));
    }

    public void remove(String playerName){
        connectionsMap.remove(playerName);
    }



}
