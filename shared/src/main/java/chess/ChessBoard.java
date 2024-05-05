package chess;

import java.util.Vector;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    public ChessBoard() {
        board = new ChessPiece[8][8]; // array of array, initialized with null values
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
        // -1 is needed because internally arrays go from 0 to 7, but position notation is from 1 to 8
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (board[position.getRow()-1][position.getColumn()-1]== null) {
            return new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.EMPTY);
            // if null, return white empty
        }
        return board[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        setUpPlayer(ChessGame.TeamColor.WHITE, board[1], board[0]);
        setUpPlayer(ChessGame.TeamColor.BLACK, board[6], board[7]);
    }

    //ctr alt p -> refactors to be a parameter
    // ctrl alt m -> methodizes the thing

    private void setUpPlayer(ChessGame.TeamColor teamColor, ChessPiece[] pawnRow, ChessPiece[] baseRow) {
        for (int i = 0; i < 8; i++) {
            pawnRow[i] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        }
        baseRow[0]= new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        baseRow[1]= new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        baseRow[2]= new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        baseRow[3]= new ChessPiece(teamColor, teamColor== ChessGame.TeamColor.WHITE ? ChessPiece.PieceType.QUEEN: ChessPiece.PieceType.KING);
        baseRow[4]= new ChessPiece(teamColor, teamColor == ChessGame.TeamColor.WHITE ? ChessPiece.PieceType.KING: ChessPiece.PieceType.QUEEN);
        baseRow[5]= new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        baseRow[6]= new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        baseRow[7]= new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
    }

    private
    ChessPiece[][] board;

    public static class BishopMoves {
        ChessPiece empty = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.EMPTY);

        BishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
            this.board = board;
            this.myPosition = myPosition;
            this.color = color;
            row = myPosition.getRow();
            col = myPosition.getColumn();
        }

        // returns a vector of all valid ChessPositions for next move
        public Vector<ChessPosition> FindPositions(){
            Vector<ChessPosition> moves = null;

            TryOneWay(moves, 1, 1);
            TryOneWay(moves, 1, -1);
            TryOneWay(moves, -1, -1);
            TryOneWay(moves, -1, 1);
            return moves;
        }

        private void TryOneWay(Vector<ChessPosition> moves, int rowSign, int colSign) {  // signs let us choose +/- 1 to control which direction we increment
            ChessPosition nextPosition;
            for(int i = 0; i < 7; i++){
                nextPosition = new ChessPosition(row+i, col+i);
                if (!inBounds(nextPosition)){
                    break;
                }
                if( board.getPiece(nextPosition) != empty){
                    break;
                } else {
                    moves.add(nextPosition);
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
}
