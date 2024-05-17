package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class QueenMoves {
    private ChessGame.TeamColor color;
    private ChessBoard board;
    private int row;
    private int col;
    private final ChessPosition myPosition;
    private ArrayList<ChessPosition> positions;

    public QueenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        this.board = board;
        this.myPosition = myPosition;
        this.color = color;
        row = myPosition.getRow();
        col = myPosition.getColumn();
        positions = new ArrayList<>();
    }

    public ArrayList<ChessMove> FindPositions(){
        TryOneWay(1, 0);
        TryOneWay(-1, 0);
        TryOneWay(0, 1);
        TryOneWay(0, -1);
        TryOneWay(1, 1);
        TryOneWay(-1, 1);
        TryOneWay(-1, -1);
        TryOneWay(1, -1);
        return MakeMovesArray(positions);
    }

    private void TryOneWay( int rowIncrement, int colIncrement) {
        ChessPosition nextPosition;
        for(int i = 1; i < 7; i++){
            nextPosition= new ChessPosition(row+ rowIncrement*i, col+ colIncrement*i);
            if (!inBounds(nextPosition)){
                break;
            }
            if( board.getPiece(nextPosition) != null){ // if nextPosition is not empty
                if (board.getPiece(nextPosition).getTeamColor() == color){ // if it's our teammate, break, we can't move there
                    break;
                } else { // if it's an enemy piece, we can move there
                    positions.add(nextPosition); // add position of capturable enemy piece
                    break;
                }
            }
            positions.add(nextPosition); // if the spot is empty, add the position
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
