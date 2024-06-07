import chess.ChessGame;
import chess.ChessPiece;
import ui.DrawChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("♕ Welcome to 240 Chess. Type help for list of commands.");
        PrintStream out = new PrintStream(System.out, true,StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(!line.equalsIgnoreCase("quit")) {
            if (line.equalsIgnoreCase("help")) {
                // call help method, pass out as param
                help(out);
            } else if (line.equalsIgnoreCase("register")) {
                register(out, scanner);
            }
            line = scanner.nextLine();
        }

    }

    private static void help(PrintStream out){
        out.println("Commands:");
        out.println(" - register: create a new user");
        out.println(" - login: kind of self-explanatory");
        out.println(" - quit: also self-explanatory");
        out.println(" - help: see this exact same message, again! You should choose this option...");
    }

    private static void register(PrintStream out, Scanner scanner){
        out.println("Enter a username");
        String username = scanner.nextLine();
        out.println("Enter a password");
        String password = scanner.nextLine();
        out.println("Enter an email address (optional)");
        String email = scanner.nextLine();
    }

    private static void login(PrintStream out, Scanner scanner){
        out.println("Enter a username");
        out.println("Enter a username");
        String username = scanner.nextLine();
        out.println("Enter a password");
        String password = scanner.nextLine();
    }
}

// study classpath, linux shortcut??