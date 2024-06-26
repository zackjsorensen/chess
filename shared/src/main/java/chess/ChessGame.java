package chess;

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
        currentTeam = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
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
    public Collection<ChessMove> validMoves(ChessPosition startPosition)  {
        TeamColor myColor = board.getPiece(startPosition).getTeamColor();
        ChessPiece myPiece = new ChessPiece(myColor, board.getPiece(startPosition).getPieceType());
        Collection<ChessMove> possibleMoves = myPiece.pieceMoves(board, startPosition);
        final ChessBoard oldestBoard = getBoard();
        ArrayList<ChessMove> validMovesCollection = new ArrayList<>();   // why collection? Why not just ArrayList?
        for (ChessMove onePossibleMove: possibleMoves){
            ChessBoard oldBoard = getBoard();
            try {
                setBoard((ChessBoard) board.clone());
            } catch (Exception e){
                throw new RuntimeException("Cloneable killed me");
            }

            tryOneMove(onePossibleMove);
            if (!isInCheck(myColor)){
                validMovesCollection.add(onePossibleMove);
            }
            setBoard(oldBoard);
        }
        return validMovesCollection;
    }

    private void tryOneMove(ChessMove onePossibleMove) {
        ChessPiece pieceToMove = board.getPiece(onePossibleMove.getStartPosition());
        board.addPiece(onePossibleMove.getStartPosition(), null);
        board.addPiece(onePossibleMove.getEndPosition(), pieceToMove);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // get piece at start, empty the start position, update the end position, update whose turn it is
        ChessPiece pieceToMove = board.getPiece(move.getStartPosition());
        if (pieceToMove == null){
            throw new InvalidMoveException("No piece to move");
        }

        ArrayList<ChessMove> validMovesArray = (ArrayList<ChessMove>) validMoves(move.getStartPosition());
        if (!validMovesArray.contains(move)){
            throw new InvalidMoveException("This move is not permitted");
        } else if (pieceToMove.getTeamColor() != currentTeam){
            throw new InvalidMoveException("It is not your turn");
        }

        board.addPiece(move.getStartPosition(), null);
        if (move.promotionPiece != null){
            board.addPiece(move.getEndPosition(), new ChessPiece(currentTeam, move.promotionPiece));
        } else {
            board.addPiece(move.getEndPosition(), pieceToMove);
        }
        setTeamTurn((currentTeam == TeamColor.BLACK) ? TeamColor.WHITE : TeamColor.BLACK);

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = findKing(teamColor);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(currentPos);
                if (currentPiece != null && currentPiece.getTeamColor() != teamColor) {
                    ArrayList<ChessMove> enemyMoves = (ArrayList<ChessMove>) currentPiece.pieceMoves(this.board, currentPos);
                    for (ChessMove move : enemyMoves) {
                        if (move.endPosition.equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private ChessPosition findKing(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(currentPos); // get piece is 1 to 8 based
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
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
        // if in check, iterate through all of your own pieces, see if there are any valid moves
        if (!isInCheck(teamColor)){
            return false;
        }
        return iterateThroughBoard(teamColor);
    }

    private boolean iterateThroughBoard(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(currentPos);
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    var myMoves = validMoves(currentPos);
                    if (!myMoves.isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)){
            return false;
        }
        return iterateThroughBoard(teamColor);
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
