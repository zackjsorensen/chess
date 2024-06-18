package model.websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    public Integer gameID;
    public ChessMove move;

    public MakeMoveCommand(String authToken, int gameID, ChessMove move) {
        // does it work to construct in pieces like this?
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }
}
