package websocket;

import com.google.gson.Gson;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.GameData;
import model.exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.ConnectCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.Notification;
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
    SQLAuthDAO authDAO;

    public WebSocketHandler(SQLGameDAO gameDAO, SQLAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
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
        // send notification of join to other players
        String username = authDAO.get(command.getAuthString()).username();
        GameData myGame = (GameData) gameDAO.get(command.gameID);
        String joinedAs;
        if (myGame.blackUsername() != null && myGame.blackUsername().equals(username)){
            joinedAs = "Black";
        } else if (myGame.whiteUsername() != null && myGame.whiteUsername().equals(username)){
            joinedAs = "White";
        } else {
            joinedAs = "Observer";
        }

        Notification notification = new Notification(String.format("%s joined as %s", username, joinedAs));
        broadcast(command.gameID, notification, command.getAuthString());
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

    public void broadcast(int id, Notification notification, String authTokenToExclude) throws IOException {
        Set<Connection> gameConnections = connections.get(id);
        for (Connection c : gameConnections){
            if (!c.authToken.equals(authTokenToExclude)) {
                sendMessage(c.session, notification);
            }
        }
    }




}
