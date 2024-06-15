package ui;

import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import server.Server;
import serveraccess.ServerFacade;
import websocket.commands.MakeMoveCommand;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.module.ResolutionException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class GamePlayUI {
    PrintStream out;
    private Scanner scanner;
    public DrawChessBoard chessBoard;
    public ServerFacade serverFacade;
    public String authToken;
    int id;
    Gson gson = new Gson();


    public GamePlayUI(String authToken, ServerFacade serverFacade, Scanner scanner, int id) {
        out = new PrintStream(System.out, true,StandardCharsets.UTF_8);
        this.scanner = scanner;
        this.serverFacade = serverFacade;
        this.authToken = authToken;
        this.id = id;
    }

    public void run() throws IOException {
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
                makeMove();
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
        out.println("Move - Enter the move you want to make.");
        out.println("Resign - forfeit a game.");
        out.println("Highlight - Highlights legal moves for a piece at the entered position.");
    }

    private void makeMove() throws IOException {
        out.println("Enter the current position of the piece you want to move: ");
        String startPosString = scanner.nextLine();
        out.println("Enter the position you want the piece to move to:");
        String endPosString = scanner.nextLine();
        ChessPosition startPos = convertToChessPosition(startPosString);
        ChessPosition endPos = convertToChessPosition(endPosString);
        ChessMove move = new ChessMove(startPos, endPos, null);
        MakeMoveCommand command = new MakeMoveCommand(authToken, id, move);
        serverFacade.wsClient.send(gson.toJson(command));
    }

    private ChessPosition convertToChessPosition(String input){
        int column;
        int row;

        if (input.length() != 2) {
            out.println("Invalid: Please enter one letter and one number, like so: a3. Type move to try again.");
            return null;
        }
        column = LetterToInt.convert(input.charAt(0));
        int temp = input.charAt(1);
        if (temp > 8){
            out.println("Invalid: Please enter one letter and one number, like so: a3. Type move to try again.");
            return null;
        }
        row = 8 - temp;
        return new ChessPosition(row, column);
    }






}
