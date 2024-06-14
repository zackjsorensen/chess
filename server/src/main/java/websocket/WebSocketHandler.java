package websocket;

import com.google.gson.Gson;
import model.exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketHandler {
    public Map<Integer, Set<Connection>> connections = new HashMap<>();
    Gson gson = new Gson();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, ResponseException {
        System.out.println("WS recieved this: " + message);
        session.getRemote().sendString("WS response: " + message);

        UserGameCommand command;
        try {
            command = gson.fromJson(message, UserGameCommand.class);
        } catch (Exception e) {
            // idk about this....
            throw new ResponseException(401, "Invalid user command");
        }
        // save session to map
        connections.put()
        // switch statement to make it the right kind of command
        switch (command.getCommandType()){
            case CONNECT -> {}
            case MAKE_MOVE -> {}
            case LEAVE -> {}
            case RESIGN -> {}
        }
    }


    // on message code to do a simple echo

}
