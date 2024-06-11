package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class BishopMoves {
    private ArrayList<ChessPosition> positions;

    public BishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
        positions = new ArrayList<>();
    }

    public ArrayList<ChessMove> findPositions(){

        tryOneWay( 1,1);
        tryOneWay( -1,1);
        tryOneWay( -1,-1);
        tryOneWay( 1,-1);

        return makeMovesArray(positions);
    }

    private ArrayList<ChessMove> makeMovesArray(ArrayList<ChessPosition> positions){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, null));  // figure that out later...
        }
        return moves;
    }

    private void tryOneWay( int rowIncrement, int colIncrement) {
        PieceHelper helper = new PieceHelper(board, positions, color, myPosition);
        helper.tryOneWay(rowIncrement, colIncrement);
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
