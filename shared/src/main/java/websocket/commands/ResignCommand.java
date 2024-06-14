package websocket.commands;

public class ResignCommand extends UserGameCommand{
    Integer gameID;

    public ResignCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }
}
