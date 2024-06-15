package serveraccess.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import ui.DrawChessBoard;
import websocket.messages.LoadGameMessage;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient extends Endpoint {
    Gson gson = new Gson();
    Session session;
    public DrawChessBoard chessBoard;
    String color;
    ChessGame.TeamColor team;

    public WebSocketClient(String color) throws URISyntaxException, DeploymentException, IOException {
        if (color == null){
            team = ChessGame.TeamColor.WHITE;
        } else if (color.equalsIgnoreCase("black")){
            team = ChessGame.TeamColor.BLACK;
        } else {
            team = ChessGame.TeamColor.WHITE;
        }
        this.color = color;
        // when we make a websocket, we really want to be starting it.
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        // send connect message, I assume ois what this does
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                try {
                    ServerMessage msg = gson.fromJson(s, ServerMessage.class);
                    System.out.println(msg);
                    switch (msg.getServerMessageType()) {
                        case LOAD_GAME -> {loadGame(gson.fromJson(s, LoadGameMessage.class));}
                        case ERROR -> {}
                        case NOTIFICATION -> WebSocketClient.this.notify(gson.fromJson(s, Notification.class));
                    }


                } catch (Exception e){
                    System.out.println("Unreadable data sent from server ws");
                    System.out.println("Caught: " + e.getMessage());
                    e.printStackTrace();
                }


                System.out.println("Client received: " + s);
            }
        });

    }

    // send message to server
    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    private void loadGame(LoadGameMessage loadMsg) throws Exception {
        String gameString = loadMsg.game;
        ChessGame game = gson.fromJson(gameString, ChessGame.class);
        chessBoard = new DrawChessBoard(game, color);
        chessBoard.drawAll();
    }

    private void notify(Notification notification){
        System.out.println(notification.message);
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
