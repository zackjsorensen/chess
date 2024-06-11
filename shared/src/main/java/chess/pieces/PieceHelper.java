package chess.pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

import java.util.ArrayList;

public class PieceHelper {
    private ChessBoard board;
    private ArrayList<ChessPosition> positions;
    private ChessGame.TeamColor color;
    int row;
    int col;
    private final ChessPosition myPosition;

    public PieceHelper(ChessBoard board, ArrayList<ChessPosition> positions, ChessGame.TeamColor color, ChessPosition myPosition) {
        this.board = board;
        this.positions = positions;
        this.color = color;
        this.myPosition = myPosition;
        row = myPosition.getRow();
        col = myPosition.getColumn();

    }

    public void tryOneWay(int rowIncrement, int colIncrement){
        ChessPosition nextPosition;
        for(int i = 1; i < 7; i++){
            nextPosition= new ChessPosition(row+ rowIncrement*i, col+ colIncrement*i);
            if (!inBounds(nextPosition)){
                break;
            }
            if( board.getPiece(nextPosition) != null){
                if (board.getPiece(nextPosition).getTeamColor() == color){
                    break;
                } else {
                    positions.add(nextPosition);
                    break;
                }
            }
            positions.add(nextPosition);
        }
    }

    public boolean inBounds(ChessPosition positionToCheck) {
        if (positionToCheck.getColumn() < 1 || positionToCheck.getColumn() > 8) {
            return false;
        }
        if (positionToCheck.getRow() < 1 || positionToCheck.getRow() > 8) {
            return false;
        }
        return true;
    }
}
