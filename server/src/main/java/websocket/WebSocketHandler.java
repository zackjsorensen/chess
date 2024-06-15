package websocket;

import chess.ChessGame;
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
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
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
        switch (command.getCommandType()) {
            case CONNECT -> {
                connect(gson.fromJson(message, ConnectCommand.class), session);
            }
            case MAKE_MOVE -> {
            }
            case LEAVE -> {
            }
            case RESIGN -> {
            }
        }
    }

    private int connect(ConnectCommand command, Session session) throws ResponseException, IOException {
        saveSession(command, session);
        GameData gameData = (GameData) gameDAO.get(command.gameID);
        if (gameData == null) {
            sendMessage(session, new ErrorMessage("Invalid game ID"));
            return 1;
        }
        String username = getUsername(command, session);
        if (username == null) return 1;

        sendMessage(session, new LoadGameMessage(gson.toJson(gameData.game())));
        GameData dbGame = (GameData) gameDAO.get(command.gameID);

        String joinedAs;
        joinedAs = getUserColor(dbGame, username);

        Notification notification = new Notification(String.format("%s joined as %s", username, joinedAs));
        broadcast(command.gameID, notification, command.getAuthString());
        return 0;
    }

    private String getUsername(UserGameCommand command, Session session) throws ResponseException, IOException {
        String username = (authDAO.get(command.getAuthString()) == null) ? (null) : (authDAO.get(command.getAuthString()).username());
        if (username == null) {
            sendMessage(session, new ErrorMessage("Invalid credentials"));
            return null;
        }
        return username;
    }

    private String getUserColor(GameData dbGame, String username) {
        String joinedAs;
        if (dbGame.blackUsername() != null && dbGame.blackUsername().equals(username)) {
            joinedAs = "Black";
        } else if (dbGame.whiteUsername() != null && dbGame.whiteUsername().equals(username)) {
            joinedAs = "White";
        } else {
            joinedAs = "Observer";
        }
        return joinedAs;
    }

    private void makeMove(MakeMoveCommand command, Session session) throws ResponseException, IOException {
        // get the chess move, verify the validity
        // is it their turn? Maybe keep track of that locally? IDK
        GameData dbGameData = (GameData) gameDAO.get(command.gameID);
        ChessGame dbGame = dbGameData.game();
        String username = getUsername(command, session);
        String joinedAs = getUserColor(dbGameData, username);
        ChessGame.TeamColor teamColor = dbGame.getTeamTurn();
        if (joinedAs.equalsIgnoreCase("Observer")){
            sendMessage(session, new ErrorMessage("Error: Observers cannot make moves"));
            return;
        } else if (stringToTeamColor(joinedAs) != teamColor){
            sendMessage(session, new ErrorMessage("Error: It is not your turn"));
        }

    }

    private ChessGame.TeamColor stringToTeamColor(String colorStr){
        if (colorStr.equalsIgnoreCase("BLACK")){
            return ChessGame.TeamColor.BLACK;
        } else if (colorStr.equalsIgnoreCase("WHITE")){
            return ChessGame.TeamColor.WHITE;
        } else {
            return null;
        }
    }
    
    private void saveSession(ConnectCommand command, Session session) {
        Integer id = command.gameID;
        Connection newConnection = new Connection(session, command.getAuthString());
        if (connections.containsKey(id)) {
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
        for (Connection c : gameConnections) {
            if (!c.authToken.equals(authTokenToExclude)) {
                sendMessage(c.session, notification);
            }
        }
    }


}
