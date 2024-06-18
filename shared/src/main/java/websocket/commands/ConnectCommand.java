package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    public Integer gameID;

    public ConnectCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        commandType = CommandType.CONNECT;
    }
}
