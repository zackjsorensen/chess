package chess;

import chess.pieces.BishopMoves;
import chess.pieces.KingMoves;
import chess.pieces.KnightMoves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType pieceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {

        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN,
        EMPTY
    }
    public enum teamColor {
        BLACK,
        WHITE
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        throw new RuntimeException("Not implemented");
        switch (pieceType){
            case BISHOP -> {
                BishopMoves bishopSet = new BishopMoves(board, myPosition, pieceColor);
                return bishopSet.FindPositions();
            }
            case KING -> {
                KingMoves kingSet = new KingMoves(board, myPosition, pieceColor);
                return kingSet.FindPositions();
            }
            case KNIGHT -> {
                KnightMoves knightSet = new KnightMoves(board, myPosition, pieceColor);
                return knightSet.FindPositions();
            }
            default -> {return new ArrayList<>();}

        }

    }


}
