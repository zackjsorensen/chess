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

//    public void broadcast(String excludeVisitorName, Notification notification) throws IOException {
//        var removeList = new ArrayList<Connection>();
//        for (var c : connections.values()) {
//            if (c.session.isOpen()) {
//                if (!c.visitorName.equals(excludeVisitorName)) {
//                    c.send(notification.toString());
//                }
//            } else {
//                removeList.add(c);
//            }
//        }
//
//        // Clean up any connections that were left open.
//        for (var c : removeList) {
//            connections.remove(c.visitorName);
//        }
//    }



}
