package serveraccess.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import ui.DrawChessBoard;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.lang.module.ResolutionException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient extends Endpoint {
    Gson gson = new Gson();
    Session session;
    public DrawChessBoard chessBoard;
    String color;
    ChessGame.TeamColor team;
    public ChessGame game;
    public boolean gameOver;

    public WebSocketClient(String color, int port) throws URISyntaxException, DeploymentException, IOException {
        if (color == null) {
            team = ChessGame.TeamColor.WHITE;
        } else if (color.equalsIgnoreCase("black")) {
            team = ChessGame.TeamColor.BLACK;
        } else {
            team = ChessGame.TeamColor.WHITE;
        }
        this.color = color;
        game = null;
        gameOver = false;
        // URL HERE -- shouldn't be hard coded....
        String urlString = "ws://localhost:" + port + "/ws";
        URI uri = new URI(urlString);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                try {
                    ServerMessage msg = gson.fromJson(s, ServerMessage.class);
                    System.out.println(msg);
                    switch (msg.getServerMessageType()) {
                        case LOAD_GAME -> {
                            loadGame(gson.fromJson(s, LoadGameMessage.class));
                        }
                        case ERROR -> error(gson.fromJson(s, ErrorMessage.class));
                        case NOTIFICATION -> WebSocketClient.this.notify(gson.fromJson(s, Notification.class));
                    }
                } catch (Exception e) {
                    System.out.println("Unreadable data sent from server ws");
                    System.out.println("Caught: " + e.getMessage());
                    e.printStackTrace();
                }
//                System.out.println("Client received: " + s);
            }
        });
    }

    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    private void loadGame(LoadGameMessage loadMsg) throws GameOverException {
        String gameString = loadMsg.game;
        game = gson.fromJson(gameString, ChessGame.class);
        if (color.equalsIgnoreCase("Observer")) {
            try {
                chessBoard = new DrawChessBoard(game, "WHITE");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                chessBoard = new DrawChessBoard(game, color);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        chessBoard.drawAll();
        if (game.isInStalemate(ChessGame.TeamColor.BLACK) || game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK)){
            gameOver = true;
            // throw new GameOverException("Game is over");
        }
    }

    private void notify(Notification notification) {
        System.out.println(notification.message);
    }

    private void error(ErrorMessage error){
        System.out.println(error.errorMessage);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
