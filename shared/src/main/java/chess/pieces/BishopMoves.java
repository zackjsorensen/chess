package chess.pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Vector;

public class BishopMoves {
    ChessPiece empty = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.EMPTY);

    BishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public Vector<ChessPosition> FindPositionsUpLeft(){
        Vector<ChessPosition> moves = null;

        TryOneWay(moves);
        return moves;
    }

    private void TryOneWay(Vector<ChessPosition> moves) {
        ChessPosition nextPosition = new ChessPosition(row+1, col+1);
        for(int i = 2; i < 9; i++){
            if (!inBounds(nextPosition)){
                break;
            }
            if( board.getPiece(nextPosition) != empty){
                break;
            } else {
                moves.add(nextPosition);
                nextPosition = new ChessPosition(row+i, col+i);
            }
        }
    }

    private boolean inBounds(ChessPosition positionToCheck) {
        if (positionToCheck.getColumn() < 0 || positionToCheck.getColumn() > 7) {
            return false;
        }
        if (positionToCheck.getRow() < 0 || positionToCheck.getRow() > 7) {
            return false;
        }
        return true;
    }

    private final ChessPosition myPosition;
    private
    ChessGame.TeamColor color;
    ChessBoard board;
    int row;
    int col;
}
