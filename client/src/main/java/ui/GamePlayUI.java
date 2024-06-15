package ui;

import serveraccess.ServerFacade;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class GamePlayUI {
    PrintStream out;
    private Scanner scanner;
    public DrawChessBoard chessBoard;


    public GamePlayUI(String authToken, ServerFacade serverFacade, Scanner scanner) {
        out = new PrintStream(System.out, true,StandardCharsets.UTF_8);
        this.scanner = scanner;
    }

    public void run() {
        out.println("Game joined. Type help for commands");
        String line = scanner.nextLine();
        while (true){
            if (line.equalsIgnoreCase("help")){
                help();
            } else if (line.equalsIgnoreCase("Redraw")){

            } else if (line.equalsIgnoreCase("Leave")){

            } else if (line.equalsIgnoreCase("Move")){

            } else if (line.equalsIgnoreCase("Resign")){

            } else if (line.equalsIgnoreCase("Highlight")){

            } else {
                out.println("Invalid command. Type help to see valid commands. ");
            }
            line = scanner.nextLine();
        }
    }

    private void help(){
        out.println("Help - see available commands");
        out.println("Redraw - redraws the board");
        out.println("Leave - leave the game");
        out.println("Move - Enter the move you want to make in this format: Start position, end position. For example: E4, E2.");
        out.println("Resign - forfeit a game.");
        out.println("Highlight - Highlights legal moves for a piece at the entered position.");
    }





}
