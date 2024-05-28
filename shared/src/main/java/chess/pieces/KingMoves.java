package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class KingMoves {
    private ChessGame.TeamColor color;
    private ChessBoard board;
    private int row;
    private int col;
    private final ChessPosition myPosition;
    private ArrayList<ChessPosition> positions;

    public KingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
        positions = new ArrayList<>();
    }
/** Returns an ArrayList of all Possible Chess Moves for the current piece and board */
    public ArrayList<ChessMove> findPositions() {
        tryOneWay(0, 1);
        tryOneWay(-1, 1);
        tryOneWay(-1, 0);
        tryOneWay(-1, -1);
        tryOneWay(0, -1);
        tryOneWay(1, -1);
        tryOneWay(1, 0);
        tryOneWay(1, 1);

        return makeMovesArray(positions);
    }
/** Takes the ArrayList of available positions and uses it to make an ArrayList of Chess Moves*/
    private ArrayList<ChessMove> makeMovesArray(ArrayList<ChessPosition> positions){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, null));  // figure that out later...
        }
        return moves;
    }
/** Checks if 1 spot away from the king is available, given relative position to King's position*/
    private void tryOneWay(int rowIncrement, int colIncrement){
        ChessPosition nextPosition = new ChessPosition(row + rowIncrement, col + colIncrement);
        if (inBounds(nextPosition)){
            if ( board.getPiece(nextPosition) != null) {
                if (board.getPiece(nextPosition).getTeamColor() != color) { // if it's an enemy piece
                    positions.add(nextPosition);
                }
            } else { // nextPosition is empty
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

}
