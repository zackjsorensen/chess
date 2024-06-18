package model.websocket.messages;

public class Notification extends ServerMessage{
    public String message;

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
}
