package ui;

import server.Server;
import serveraccess.ServerFacade;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class GamePlayUI {
    PrintStream out;
    private Scanner scanner;
    public DrawChessBoard chessBoard;
    public ServerFacade serverFacade;
    public String authToken;


    public GamePlayUI(String authToken, ServerFacade serverFacade, Scanner scanner) {
        out = new PrintStream(System.out, true,StandardCharsets.UTF_8);
        this.scanner = scanner;
        this.serverFacade = serverFacade;
        this.authToken = authToken;
    }

    public void run() {
        out.println("Game joined. Type help for commands");
        String line = scanner.nextLine();
        while (true){
            if (line.equalsIgnoreCase("help")){
                help();
            } else if (line.equalsIgnoreCase("Redraw")){
                chessBoard = serverFacade.wsClient.chessBoard;
                chessBoard.drawAll();
            } else if (line.equalsIgnoreCase("Leave")){
                // code to leave
                break;
            } else if (line.equalsIgnoreCase("Move")){

            } else if (line.equalsIgnoreCase("Resign")){
                // code to lose
                break;
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
