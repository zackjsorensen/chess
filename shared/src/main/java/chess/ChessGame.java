package chess;

import com.sun.jdi.ThreadGroupReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor currentTeam;
    private ChessBoard board;
    public ChessGame() {
        currentTeam = TeamColor.WHITE; // should that be there?
        board = new ChessBoard();
        board.resetBoard();  // constructor makes a game that is ready to start, but that can be overriden if needed.
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTeam = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece myPiece = new ChessPiece(board.getPiece(startPosition).getTeamColor(), board.getPiece(startPosition).getPieceType());
        Collection<ChessMove> possibleMoves = myPiece.pieceMoves(board, startPosition);
        // from here
        // deepCopy the board and check all possible positions
        // Add those that are valid to the ArrayList to return
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

            // Iterate through enemy pieces and see if they can get to King's Position
            // first - find our king
            ChessPosition kingPos = findKing(teamColor);
            // iterate through enemy's pieces, see if their possibleMoves endanger the King
            // iterate thorugh board to find all enemy pieces
            //      when enemy is found, get possibleMoves
            //      Check if any of those ending Positions == kingPos. If so, return true. Else, keep going
            // Go until end of board or number of pieces checked reaches 16.
        int piecesChecked = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(currentPos);
                if (currentPiece != null && currentPiece.getTeamColor() != teamColor){
                    ArrayList<ChessMove> enemyMoves = (ArrayList<ChessMove>) currentPiece.pieceMoves(this.board, currentPos);
                    for (ChessMove move : enemyMoves){
                        if (move.endPosition == kingPos){
                            return true;
                        }
                        piecesChecked++;
                        if (piecesChecked > 15){
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    private ChessPosition findKing(TeamColor teamColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(currentPos);
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING){
                    return currentPos;
                }
            }
        }
        return null;
    }



    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
