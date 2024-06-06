package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import chess.ChessGame;
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

    public DrawChessBoard(ChessGame game, String color) throws Exception {
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        this.game = game;
        this.color = color;
        if (color.equals("BLACK")) {
            whiteLeadsOnEven = true;
        } else if (color.equals("WHITE")) {
            whiteLeadsOnEven = false;
        } else {
            throw new Exception("Bad color parameter");
        }
    }


    public void drawAll() {
        if (color.equals("BLACK")) {
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
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            drawNumbers(row);
            if (row % 2 == 0) {
                drawWhiteRow();
            } else {
                drawBlackRow();
            }

            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            drawNumbers(row);
            out.println();
        }
        drawLetters();
    }

    private void drawNumbers(int row) {
        if (color.equals("WHITE")){
            out.printf(" %s ", (row + 1));
        } else {
            out.printf(" %s ", (8 - row));
        }
    }

    private void drawWhiteRow() {
        for (int column = 0; column < 8; column = column + 2) {
            drawWhiteSquare();
            drawBlackSquare();
        }
    }

    private void drawBlackRow() {
        for (int column = 0; column < 8; column = column + 2) {
            drawBlackSquare();
            drawWhiteSquare();
        }
    }

    private void drawWhiteSquare() {
        out.print(EscapeSequences.SET_BG_COLOR_WHITE);
        out.print(EMPTY);
    }

    private void drawBlackSquare() {
        out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        out.print(EMPTY);
    }
}


