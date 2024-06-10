import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import dataaccess.exception.ResponseException;
import model.AuthData;
import model.UserData;
import ui.DrawChessBoard;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    private static String authToken;
    static PrintStream out;
    static ServerFacade serverFacade;
    static Scanner scanner;
    enum UserState {
        LOGGED_IN,
        LOGGED_OUT;
    }
    static UserState userState;

    public static void main(String[] args) throws Exception {
        serverFacade = new ServerFacade();

        System.out.println("♕ Welcome to 240 Chess. Type help for list of commands.");
        out = new PrintStream(System.out, true,StandardCharsets.UTF_8);
        scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        userState = UserState.LOGGED_OUT;
        while(!line.equalsIgnoreCase("quit")) {
            if (line.equalsIgnoreCase("help")) {
                help();
            } else if (line.equalsIgnoreCase("register")) {
                register(out, scanner, serverFacade);
            } else if (line.equalsIgnoreCase("login")){
                login(out, scanner);
            }
            line = scanner.nextLine();
        }

    }

    private static void postLoginUI() throws MalformedURLException, ResponseException {
        String line = scanner.nextLine();
        while(!line.equalsIgnoreCase("quit") && userState == UserState.LOGGED_IN){
            if (line.equalsIgnoreCase("help")) {
                helpPostLogin();
            } else if (line.equalsIgnoreCase("logout")){
                logout();
            }
            line = scanner.nextLine();
        }
    }

    private static void help(){
        out.println("Commands:");
        out.println(" - register: create a new user");
        out.println(" - login: kind of self-explanatory");
        out.println(" - quit: also self-explanatory");
        out.println(" - help: see this exact same message, again! You should choose this option...");
    }

    private static void register(PrintStream out, Scanner scanner, ServerFacade serverFacade) throws MalformedURLException, ResponseException {
        out.println("Enter a username");
        String username = scanner.nextLine();
        out.println("Enter a password");
        String password = scanner.nextLine();
        out.println("Enter an email address (optional)");
        String email = scanner.nextLine();
        UserData user = new UserData(username, password, email);
        ResponseObj res = serverFacade.register(user);
        if (res.statusCode() == 200 || res.statusCode() == 201){
            AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
            authToken = auth.authToken();
            out.println(authToken);
            userState = UserState.LOGGED_IN;
            postLoginUI();
        }
    }

    private static void login(PrintStream out, Scanner scanner) throws MalformedURLException, ResponseException {
        out.println("Enter a username");
        String username = scanner.nextLine();
        out.println("Enter a password");
        String password = scanner.nextLine();
        UserData user = new UserData(username, password, null);
        ResponseObj res = serverFacade.login(user);
        out.println();
        if (res.statusCode() == 200 || res.statusCode() == 201){
            AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
            authToken = auth.authToken();
            out.println(authToken);
            userState = UserState.LOGGED_IN;
            postLoginUI();
        }
    }

    private static void helpPostLogin(){
        out.println("Commands:");
        out.println(" - logout: take a wild guess what this does");
        out.println(" - create: create a new game");
        out.println(" - list: see a list of games");
        out.println(" - join: join a game");
        out.println(" - quit: hmmm, I wonder what this command does??");
        out.println(" - help: see this exact same message, again! You should choose this option...");
    }

    private static void logout() throws MalformedURLException, ResponseException {
        ResponseObj res = serverFacade.logout(authToken);
        if (res.statusCode() == 200 || res.statusCode() == 201) {
            userState = UserState.LOGGED_OUT;
            // getting 401....
        }
    }
}

// study classpath, linux shortcut??