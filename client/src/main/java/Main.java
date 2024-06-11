import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.exception.ResponseException;
import model.*;
import serveraccess.ResponseObj;
import serveraccess.ServerFacade;
import ui.DrawChessBoard;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
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
    static Gson gson = new Gson();

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
            } else if (line.equalsIgnoreCase("create")){
                createGame();
            } else if (line.equalsIgnoreCase("join")){
                joinGame();
            } else if (line.equalsIgnoreCase("list")){
                listGames();
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
        ResponseObj res = null;
        try {
            res = serverFacade.login(user);
            if (res.statusCode() == 200 || res.statusCode() == 201){
                AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
                authToken = auth.authToken();
                out.println(authToken);
                userState = UserState.LOGGED_IN;
                postLoginUI();
            }
        } catch (ResponseException e) {
            out.println(e.getMessage());
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

    private static void createGame() throws MalformedURLException {
        out.println("Enter desired game name");
        String gameName = scanner.nextLine();
        try {
            int id = serverFacade.createGame(gameName, authToken);
            out.println("Game ID:" + id);
        } catch (ResponseException e){
            out.println(e.getMessage());
        }
    }

    private static void joinGame() {
        out.println("Enter game ID");
        int id = scanner.nextInt();
        String clear = scanner.nextLine();
        out.println("Enter playerColor to play as");
        String color = scanner.nextLine();
        try {
            serverFacade.joinGame(id, color, authToken);
            DrawChessBoard drawBlack = new DrawChessBoard(new ChessGame(), "BLACK");
            DrawChessBoard drawWhite = new DrawChessBoard(new ChessGame(), "WHITE");
            drawBlack.drawAll();
            drawWhite.drawAll();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    private static void listGames() throws MalformedURLException {
        ListGamesResult games = serverFacade.listGames(authToken);
        out.println(games);
    }
}

// study classpath, linux shortcut??