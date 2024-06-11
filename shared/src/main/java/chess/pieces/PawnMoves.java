package chess.pieces;

import chess.*;

import java.util.ArrayList;

/**
 * Calculates all the positions a pawn can move to
 * Does not take into account moves that are illegal due to leaving the king in
 * danger
 *
 * @return Collection of valid moves for pawn
 */
public class PawnMoves {

    public PawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color ) {
        this.myPosition = myPosition;
        this.color = color;
        this.board = board;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.positions = new ArrayList<>();
    }
    
    public ArrayList<ChessMove> findPositions(){
        int i = (color == ChessGame.TeamColor.WHITE) ? 1: -1;
        ChessPosition nextPosition = new ChessPosition(row + i, col);
        goForward(nextPosition, i);
        goDiangol(new ChessPosition(row+i, col-i));
        goDiangol(new ChessPosition(row+i, col+i));
        return positions;
    }

    private void goForward(ChessPosition nextPosition, int i) {
        if (board.getPiece(nextPosition) == null){ // if 1 square forward is empty
            int prePromotionRow = (color == ChessGame.TeamColor.WHITE) ? 7 : 2;
            if (row == prePromotionRow){ // we are about to get promoted
                promoteOptions(nextPosition);
            } else {
                positions.add(new ChessMove(myPosition, nextPosition, null));
                int startingRow = (color == ChessGame.TeamColor.WHITE) ? 2 : 7;
                if (row == startingRow){ // if it's the pawn's first move
                    if (board.getPiece(new ChessPosition(row+2*i, col)) == null){  // if the next sqaure forward is empty
                        positions.add(new ChessMove(myPosition, new ChessPosition(row+2*i, col), null));
                    }
                }
            }
        }
    }

    private void goDiangol(ChessPosition cornerSpace){
        if (inBounds(cornerSpace) && board.getPiece(cornerSpace) != null && board.getPiece(cornerSpace).getTeamColor() != color){
            // aka if there's an enemy piece there to capture
            int prePromotionRow = (color == ChessGame.TeamColor.WHITE) ? 7 : 2;
            if (row == prePromotionRow){ // we are about to get promoted
                promoteOptions(cornerSpace);
            } else {
                positions.add(new ChessMove(myPosition, cornerSpace, null));
            }
        }
    }

    private void promoteOptions(ChessPosition nextPosition) {
        positions.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.QUEEN));
        positions.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.KNIGHT));
        positions.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.ROOK));
        positions.add(new ChessMove(myPosition, nextPosition, ChessPiece.PieceType.BISHOP));
    }

    private boolean inBounds(ChessPosition positionToCheck) {
        PieceHelper pawnHelper = new PieceHelper(board, null, color, myPosition);
        return pawnHelper.inBounds(positionToCheck);
    }

    private final ChessPosition myPosition;
    private ChessGame.TeamColor color;
    private ChessBoard board;
    private int row;
    private int col;
    private ArrayList<ChessMove> positions;

}

