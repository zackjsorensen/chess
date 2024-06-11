package server.handlers;

import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.*;
import service.*;
import com.google.gson.Gson;
import server.reqresobjects.ErrorResponse;
import model.ListGamesResult;
import spark.Request;
import spark.Response;

import java.util.List;

public class Handlers {
    public UserService userService;
    public AuthService authService;
    public GameService gameService;
    Gson gson;
    UserData empty;

    public Handlers() {
        userService = new UserService(new SQLUserDAO()); // we have our userService that starts with an empty userDAO
        authService = new AuthService(new SQLAuthDAO());
        gameService = new GameService(new SQLGameDAO());
        gson = new Gson();
        empty = (new UserData(null, null, null));
    }

    public String registerUser(Request req, Response res) {
        model.UserData user = gson.fromJson(req.body(), UserData.class);
        try {
            if (!userService.checkRequest(user)) {
                return respondToBadReq(res);
            }
            if (userService.getUser(user.username()) != null) {
                res.status(403);
                return gson.toJson(new LoginResponse(null, null, "Error: already taken"));
                // hopefully that didn't break everything....
            }
            userService.addUser(user);
            res.status(200);
            return gson.toJson(authService.createAuth(user.username()));
        } catch (ResponseException e) {
            res.status(e.statusCode);
            return e.getMessage();
        }
    }

    private String respondToBadReq(Response res) {
        res.status(400);
        return gson.toJson(new ErrorResponse("Error: bad request"));
    }

    public String login(Request req, Response res) throws ResponseException {
        model.UserData user = gson.fromJson(req.body(), UserData.class);
        if (!userService.verify(user)) {
            return respondToUnauthorized(res);
        }
        res.status(200);
        return gson.toJson(authService.createAuth(user.username()));
    }

    private String respondToUnauthorized(Response res) {
        res.status(401);
        return gson.toJson(new ErrorResponse("Error: unauthorized"));
    }

    public String logout(Request req, Response res) {
        String authToken = req.headers("authorization");
        try {
            if (authService.getAuth(authToken) == null) {
                return respondToUnauthorized(res);
            }
            authService.deleteAuth(authToken);
            res.status(200);
            return gson.toJson(empty);
        } catch (ResponseException e) {
            res.status(e.statusCode);
            return e.getMessage();
        }
    }

    public String clearDB(Request req, Response res) {
        try {
            userService.clearUsers();
            authService.clear();
            gameService.clear();
            res.status(200);
            String body = gson.toJson(empty);// I guess this is returning an empty response
            res.body(body);
            return body;
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }

    public String createGame(Request req, Response res) throws ResponseException {
        String gameName = gson.fromJson(req.body(), CreateGameReq.class).gameName();
        if (gameName == null) {
            return respondToBadReq(res);
        }
        if (!checkAuthToken(req)) {
            return respondToUnauthorized(res);
        }
        int gameID = gameService.createGame(gameName);
        res.status(200);
        return gson.toJson(new GameData(gameID, null, null, null, null));
    }

    private boolean checkAuthToken(Request req) throws ResponseException {
        String authToken = req.headers("authorization");
        if (authToken == null) {
            return false;
        }
        return authService.getAuth(authToken) != null;
    }

    public String joinGame(Request req, Response res) throws DataAccessException {
        JoinGameReq joinRequest = gson.fromJson(req.body(), JoinGameReq.class);
        if (!checkAuthToken(req)) {
            return respondToUnauthorized(res);
        }
        if (joinRequest.gameID() == 0 || joinRequest.color() == null || (!joinRequest.color().equalsIgnoreCase("BLACK") && !joinRequest.color().equalsIgnoreCase("WHITE"))) {
            return respondToBadReq(res);
        }
        String username = authService.getAuth(req.headers("authorization")).username();
        if (username == null) {
            return respondToUnauthorized(res);
        }
        if (joinRequest.color() == null){
            respondToBadReq(res);
        }
//            joinRequest = new JoinGameReq(joinRequest.gameID(), "WHITE");
////            if (gameService.isColorTaken(joinRequest.gameID(), joinRequest.color())){
////                joinRequest = new JoinGameReq(joinRequest.gameID(), "BLACK");
////            }
//        }
        if (gameService.isColorTaken(joinRequest.gameID(), joinRequest.color())) {
            throw new ResponseException(403, "Color taken");
        }

        res.status(200);
        gameService.joinGame(joinRequest.gameID(), username, joinRequest.color());
        return gson.toJson(empty);
    }

    public String listGames(Request req, Response res) throws ResponseException {
        if (!checkAuthToken(req)) {
            return respondToUnauthorized(res);
        }
        res.status(200);
        return gson.toJson(new ListGamesResult(List.of(gameService.listGames())));
    }


}
