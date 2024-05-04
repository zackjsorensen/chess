package chess.pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

/**
 * Calculates all the positions a pawn can move to
 * Does not take into account moves that are illegal due to leaving the king in
 * danger
 *
 * @return Collection of valid moves for pawn
 */
public class PawnCalc {

PawnCalc(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
    this.board = board;
    this.myPosition = myPosition;
    this.color = color;
    row = myPosition.getRow();
    col = myPosition.getColumn();
}

//public ChessPosition [] TryForward(){
//    if (board[row+1][col] )
//    board.getPiece(new ChessPosition(row+1, col));
//}


    private final ChessPosition myPosition;
    private
    ChessGame.TeamColor color;
    ChessBoard board;
    int row;
    int col;
}

