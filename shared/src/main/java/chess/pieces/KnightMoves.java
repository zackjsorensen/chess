package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class KnightMoves {
    private ChessGame.TeamColor color;
    private ChessBoard board;
    private int row;
    private int col;
    private final ChessPosition myPosition;
    private ArrayList<ChessPosition> positions;

    public KnightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
        positions = new ArrayList<>();
    }
    /** Returns an ArrayList of all Possible Chess Moves for the current piece and board */
    public ArrayList<ChessMove> FindPositions() {
        TryOneWay(2, 1);
        TryOneWay(1, 2);
        TryOneWay(-1, 2);
        TryOneWay(-2, 1);
        TryOneWay(-2, -1);
        TryOneWay(-1, -2);
        TryOneWay(1, -2);
        TryOneWay(2, -1);

        return MakeMovesArray(positions);
    }

    /** Checks a given relative position to see if it's empty or can be captured*/
    private void TryOneWay(int rowIncrement, int colIncrement){
        ChessPosition nextPosition = new ChessPosition(row + rowIncrement, col + colIncrement);
        if (inBounds(nextPosition)){
            if ( board.getPiece(nextPosition).getPieceType() != ChessPiece.PieceType.EMPTY) {
                if (board.getPiece(nextPosition).getTeamColor() != color) { // if it's an enemy piece
                    positions.add(nextPosition);
                }
            } else { // nextPosition is empty
                positions.add(nextPosition);
            }
        }
    }

    /** Takes the ArrayList of available positions and uses it to make an ArrayList of Chess Moves*/
    private ArrayList<ChessMove> MakeMovesArray(ArrayList<ChessPosition> positions){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, null));  // figure that out later...
        }
        return moves;
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

}
