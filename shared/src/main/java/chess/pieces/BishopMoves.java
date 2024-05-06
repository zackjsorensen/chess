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
        Vector<ChessPosition> positions = new Vector<ChessPosition>();

        TryOneWay(positions, 1,1);
        TryOneWay(positions, -1,1);
        TryOneWay(positions, -1,-1);
        TryOneWay(positions, 1,-1);

        return MakeMovesArray(positions);
    }

    private ArrayList<ChessMove> MakeMovesArray(Vector<ChessPosition> positions){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, ChessPiece.PieceType.QUEEN));  // figure that out later...
        }
        return moves;
    }

    private void TryOneWay(Vector<ChessPosition> positions, int rowIncrement, int colIncrement) {
        ChessPosition nextPosition;
        for(int i = 1; i < 7; i++){
             nextPosition= new ChessPosition(row+ rowIncrement*i, col+ colIncrement*i);
            if (!inBounds(nextPosition)){
                break;
            }
            if( board.getPiece(nextPosition).getPieceType() != ChessPiece.PieceType.EMPTY){
                break;
            } else {
                positions.add(nextPosition);
            }
        }
    }

    private boolean inBounds(ChessPosition positionToCheck) {
        if (positionToCheck.getColumn() < 1 || positionToCheck.getColumn() > 8) {
            return false;
        }
        if (positionToCheck.getRow() < 1 || positionToCheck.getRow() > 8) {
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
