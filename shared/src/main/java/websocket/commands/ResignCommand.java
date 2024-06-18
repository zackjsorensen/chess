package websocket.commands;

public class ResignCommand extends UserGameCommand{
    public Integer gameID;
    public boolean resigned;

    public ResignCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
        resigned = false;
    }
}
