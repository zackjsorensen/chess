package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

public class DrawChessBoard {
    static PrintStream out;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    private static final String EMPTY = "   ";
    String letters;
    ChessGame game;
    String color;
    boolean whiteLeadsOnEven;
    ChessBoard board;

    public DrawChessBoard(ChessGame game, String color) throws Exception {
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        this.game = game;
        this.color = color;
        board = game.getBoard();
        if (color.equalsIgnoreCase("BLACK")) {
            whiteLeadsOnEven = true;
        } else if (color.equalsIgnoreCase("WHITE")) {
            whiteLeadsOnEven = false;
        } else {
            throw new Exception("Bad playerColor parameter");
        }
    }


    public void drawAll() {
        if (color.equalsIgnoreCase("BLACK")) {
            drawBlackTurn();
        } else {
            drawWhiteTurn();
        }
    }

    private void drawBlackTurn() {
        letters = "    a  b  c  d  e  f  g  h";
        drawRows();
    }

    private void drawWhiteTurn() {
        letters = "    h  g  f  e  d  c  b  a";
        drawRows();
    }

    private void drawLetters() {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        out.println(letters);
    }

    private void drawRows() {
        drawLetters();
        for (int row = 0; row < 8; row++) {
            int rowNum = getRowNum(row);
            int displayRowNum = getRowDisplayNum(row);
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            out.printf(" %s ", (displayRowNum));
            if (row % 2 == 0) {
                drawWhiteRow(rowNum);
            } else {
                drawBlackRow(rowNum);
            }

            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            out.printf(" %s ", (displayRowNum));
            out.println();
        }

        drawLetters();
    }


    private int getRowNum(int row) {
        if (color.equalsIgnoreCase("WHITE")){
            return 8 - row;
        } else {
            return row + 1;
        }
    }

    private int getRowDisplayNum(int row){
        if (color.equalsIgnoreCase("WHITE")){
            return row + 1;
        } else {
            return 8-row;
        }
    }

    private int getColNum(int column) {
        if (color.equalsIgnoreCase("WHITE")){
            return 7 - column;
        } else {
            return column;
        }
    }

    private void drawWhiteRow(int rowNum) {
        for (int column = 0; column < 8; column = column + 2) {
            int whiteColNum = getColNum(column);
            int blackColNum = getColNum(column +1);
            drawWhiteSquare(rowNum, whiteColNum);
            drawBlackSquare(rowNum, blackColNum);
        }
    }

    private void drawBlackRow(int rowNum) {
        for (int column = 0; column < 8; column = column + 2) {
            int blackColNum = getColNum(column);
            int whiteColNum = getColNum(column + 1);
            drawBlackSquare(rowNum, blackColNum);
            drawWhiteSquare(rowNum, whiteColNum);
        }
    }

    private void drawWhiteSquare(int rowNum, int colNum) {
        out.print(EscapeSequences.SET_BG_COLOR_WHITE);
        ChessPiece piece = board.getPiece(new ChessPosition(rowNum, colNum + 1));
        if (piece == null){
            out.print(EMPTY);
        } else {
            out.print(getPieceTextColor(piece.getTeamColor()));
            out.print(getPieceSymbol(piece.getPieceType()));
        }
    }

    private void drawBlackSquare(int rowNum, int colNum) {
        out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        ChessPiece piece = board.getPiece(new ChessPosition(rowNum, colNum + 1));
        if (piece == null){
            out.print(EMPTY);
        } else {
            out.print(getPieceTextColor(piece.getTeamColor()));
            out.print(getPieceSymbol(piece.getPieceType()));
        }
    }

    private String getPieceSymbol(ChessPiece.PieceType pieceType){
        switch(pieceType){
            case PAWN -> {return " P ";}
            case ROOK -> {return " R ";}
            case KNIGHT -> {return " N ";}
            case BISHOP -> {return " B ";}
            case QUEEN -> {return " Q ";}
            case KING -> {return " K ";}
            default -> {return EMPTY;}
        }
    }

    private String getPieceTextColor(ChessGame.TeamColor teamColor) {
        if (teamColor.equals(ChessGame.TeamColor.BLACK)){
            return EscapeSequences.SET_TEXT_COLOR_RED;
        } else {
            return EscapeSequences.SET_TEXT_COLOR_BLUE;
        }
    }


}


