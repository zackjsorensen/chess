package websocket;

import com.google.gson.Gson;
import dataaccess.sql.SQLGameDAO;
import model.GameData;
import model.exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.ConnectCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketHandler {
    public Map<Integer, Set<Connection>> connections = new HashMap<>();
    Gson gson = new Gson();
    SQLGameDAO gameDAO;

    public WebSocketHandler(SQLGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, ResponseException {
        System.out.println("WS recieved this: " + message);

        UserGameCommand command;
        try {
            command = gson.fromJson(message, UserGameCommand.class);
            System.out.println(command);
        } catch (Exception e) {
            // idk about this....
            throw new ResponseException(401, "Invalid user command");
        }
        // save session to map
        // switch statement to make it the right kind of command
        switch (command.getCommandType()){
            case CONNECT -> {connect(gson.fromJson(message, ConnectCommand.class), session);}
            case MAKE_MOVE -> {}
            case LEAVE -> {}
            case RESIGN -> {}
        }
    }

    private void connect(ConnectCommand command, Session session) throws ResponseException, IOException {
        saveSession(command, session);
        GameData gameData = (GameData) gameDAO.get(command.gameID);
        sendMessage(session, new LoadGameMessage(gson.toJson(gameData.game())));
        // send them the game state
    }

    private void saveSession(ConnectCommand command, Session session) {
        Integer id = command.gameID;
        Connection newConnection = new Connection(session, command.getAuthString());
        if (connections.containsKey(id)){
            Set<Connection> tempSet = connections.get(id);
            tempSet.add(newConnection);
        } else {
            Set<Connection> newSet = new HashSet<Connection>();
            newSet.add(newConnection);
            connections.put(id, newSet);
        }
    }

    public void sendMessage(Session session, ServerMessage msg) throws IOException {
        String json = gson.toJson(msg);
        session.getRemote().sendString(json);
    }




}
