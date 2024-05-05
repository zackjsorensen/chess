package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class BishopMoves {
    ChessPiece empty = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.EMPTY);

    public BishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public ArrayList<ChessMove> FindPositions(){
        ArrayList<ChessPosition> positions = null;

        TryOneWay(positions, 1,1);
        TryOneWay(positions, -1,1);
        TryOneWay(positions, -1,-1);
        TryOneWay(positions, 1,-1);

        return MakeMovesArray(positions);
    }

    private ArrayList<ChessMove> MakeMovesArray(ArrayList<ChessPosition> positions){
        ArrayList<ChessMove> moves = null;
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, ChessPiece.PieceType.QUEEN));  // figure that out later...
        }
        return moves;
    }

    private void TryOneWay(ArrayList<ChessPosition> moves, int rowIncrement, int colIncrement) {
        ChessPosition nextPosition;
        for(int i = 0; i < 7; i++){
             nextPosition= new ChessPosition(row+ rowIncrement, col+ colIncrement);
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
