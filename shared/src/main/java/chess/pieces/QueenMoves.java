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

    public ArrayList<ChessMove> findPositions(){
        tryOneWay(1, 0);
        tryOneWay(-1, 0);
        tryOneWay(0, 1);
        tryOneWay(0, -1);
        tryOneWay(1, 1);
        tryOneWay(-1, 1);
        tryOneWay(-1, -1);
        tryOneWay(1, -1);
        return makeMovesArray(positions);
    }

    private void tryOneWay(int rowIncrement, int colIncrement) {
        PieceHelper queenHelper = new PieceHelper(board, positions, color, myPosition);
        queenHelper.tryOneWay(rowIncrement, colIncrement);
    }

    /** Takes the ArrayList of available positions and uses it to make an ArrayList of Chess Moves*/
    private ArrayList<ChessMove> makeMovesArray(ArrayList<ChessPosition> positions){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (ChessPosition item : positions){
            moves.add(new ChessMove(myPosition, item, null));  // figure that out later...
        }
        return moves;
    }
}
