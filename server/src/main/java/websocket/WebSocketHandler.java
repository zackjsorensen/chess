package websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import model.GameData;
import model.exception.ResponseException;
import model.websocket.commands.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import model.websocket.messages.ErrorMessage;
import model.websocket.messages.LoadGameMessage;
import model.websocket.messages.Notification;
import model.websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.*;

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
    public void onMessage(Session session, String message) throws IOException, ResponseException, InvalidMoveException {
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
            case MAKE_MOVE -> makeMove(gson.fromJson(message, MakeMoveCommand.class), session);
            case LEAVE -> {leave(gson.fromJson(message, LeaveCommand.class), session);}
            case RESIGN -> { resign(gson.fromJson(message, ResignCommand.class), session);
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

    private void leave(LeaveCommand command, Session session) throws ResponseException, IOException {
        GameData dbGameData = (GameData) gameDAO.get(command.gameID);
        ChessGame dbGame = dbGameData.game();
        String username = getUsername(command, session);
        String joinedAs = getUserColor(dbGameData, username);
        String color = null;
        if (joinedAs.equalsIgnoreCase("black")){
            color = "BLACK";
        } else if (joinedAs.equalsIgnoreCase("white")){
            color = "WHITE";
        }
        if (color != null){
            gameDAO.updatePlayer(dbGameData.gameID(), color, null);
        }
        broadcast(command.gameID,new Notification( String.format("%s left the game.", username)), command.getAuthString());

        session.close();
    }

    private void resign(ResignCommand command, Session session) throws ResponseException, IOException {
        String username = getUsername(command, session);

        broadcast(command.gameID,new Notification( String.format("%s resigned from the game.", username)), command.getAuthString());
        sendMessage(session, new Notification("You have resigned; the game is over"));
        session.close();
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

    private void makeMove(MakeMoveCommand command, Session session) throws ResponseException, IOException, InvalidMoveException {
        // assume that we get this in Position Notation, not true Notation...
        GameData dbGameData = (GameData) gameDAO.get(command.gameID);
        ChessGame dbGame = dbGameData.game();
        String username = getUsername(command, session);
        if (username == null){
            return;
        }
        if (verifyTurn(session, dbGameData, username, dbGame)) {
//            ChessPosition startPosTrueNotation = command.move.getStartPosition();
//            ChessPosition endPosTrueNotation = command.move.getEndPosition();
            //ChessMove movePosNotation = new ChessMove(trueToPositionNotation(startPosTrueNotation), trueToPositionNotation(endPosTrueNotation), command.move.getPromotionPiece());
            Collection<ChessMove> validMoves = dbGame.validMoves(command.move.getStartPosition());
            // will that work, or is it only by object reference?
            if (validMoves.contains(command.move)) {
                dbGame.makeMove(command.move);

                // is the reference updated? It should be...
                gameDAO.updateGameState(dbGameData.gameID(), dbGameData);
                sendGame(command.gameID, new LoadGameMessage(gson.toJson(dbGame)));
                broadcast(command.gameID, new Notification(String.format("%s made the following move: %s ", username, command.move)), command.getAuthString());
                // need to add in check, checkmate, stalemate
                ChessGame.TeamColor nextPlayer = dbGame.getTeamTurn();
                if (dbGame.isInCheckmate(nextPlayer)){
                    broadcast(command.gameID, new Notification(String.format("%s is in checkmate. Game over", nextPlayer.toString())), null);
                    dbGameData.endGame();
                    // what else???
                } else if (dbGame.isInCheck(nextPlayer)){
                    broadcast(command.gameID, new Notification(String.format("%s is in check", nextPlayer.toString())), null);
                } else if (dbGame.isInStalemate(nextPlayer)){
                    broadcast(command.gameID, new Notification("Stalemate, Game over"), null);
                }
            } else {
                sendMessage(session, new ErrorMessage("Error - invalid move"));
            }
        }

    }


    private boolean verifyTurn(Session session, GameData dbGameData, String username, ChessGame dbGame) throws IOException {
        String joinedAs = getUserColor(dbGameData, username);
        ChessGame.TeamColor teamColor = dbGame.getTeamTurn();
        if (joinedAs.equalsIgnoreCase("Observer")) {
            sendMessage(session, new ErrorMessage("Error: Observers cannot make moves"));
            return false;
        } else if (stringToTeamColor(joinedAs) != teamColor) {
            sendMessage(session, new ErrorMessage("Error: It is not your turn"));
            return false;
        }
        return true;
    }

    private ChessGame.TeamColor stringToTeamColor(String colorStr) {
        if (colorStr.equalsIgnoreCase("BLACK")) {
            return ChessGame.TeamColor.BLACK;
        } else if (colorStr.equalsIgnoreCase("WHITE")) {
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
            if (c.session.isOpen()) {
                if (!c.authToken.equals(authTokenToExclude)) {
                    sendMessage(c.session, notification);
                }
            }

        }
    }

    public void sendGame(int id, LoadGameMessage message) throws IOException {
        Set<Connection> gameConnections = connections.get(id);
        for (Connection c : gameConnections) {
            if (c.session.isOpen()){
                sendMessage(c.session, message);
            }
        }
    }
}
