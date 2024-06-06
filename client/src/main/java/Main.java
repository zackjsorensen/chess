import chess.ChessGame;
import chess.ChessPiece;
import ui.DrawChessBoard;

public class Main {
    public static void main(String[] args) throws Exception {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        DrawChessBoard draw = new DrawChessBoard(new ChessGame(), "WHITE");
        draw.drawAll();
    }
}

// study classpath, linux shortcut??