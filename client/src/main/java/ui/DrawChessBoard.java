package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import ui.EscapeSequences;

public class DrawChessBoard {
    static PrintStream out;
    EscapeSequences es;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
    private static final String EMPTY = "   ";

    public DrawChessBoard() {
        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        es = new EscapeSequences();
    }

    public void drawAll(){
        drawRows();
    }

    private void drawLetters(){
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        out.println("    a  b  c  d  e  f  g  h");
    }

    private void drawRows(){
        drawLetters();
        for (int row = 0; row < 8; row++){
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.printf(" %s ", (8-row));
            if (row % 2 == 0){
                drawWhiteRow();
            } else {
                drawBlackRow();
            }
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.printf(" %s ", (8-row));
            out.println();
        }
        drawLetters();
    }

    private void drawWhiteRow() {
       for (int column = 0; column < 8; column = column + 2){
            drawWhiteSquare();
            drawBlackSquare();
       }
    }

    private void drawBlackRow() {
        for (int column = 0; column < 8; column = column + 2){
            drawBlackSquare();
            drawWhiteSquare();
        }
    }

    private void drawWhiteSquare(){
        out.print(EscapeSequences.SET_BG_COLOR_WHITE);
        out.print(EMPTY);
    }

    private void drawBlackSquare(){
        out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        out.print(EMPTY);
    }
}


