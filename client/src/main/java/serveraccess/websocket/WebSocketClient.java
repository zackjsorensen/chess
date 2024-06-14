package serveraccess.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient extends Endpoint {

    String message = "Echo??";
    Session session;

    public WebSocketClient() throws URISyntaxException, DeploymentException, IOException {
        // when we make a websocket, we really want to be starting it.
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        // send connect message, I assume ois what this does
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                System.out.println("Client received: " + s);
            }
        });

    }

    // send message to server
    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
