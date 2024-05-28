package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import server.ReqResObjects.*;
import spark.*;
import Service.*;
import java.util.List;
import server.Handlers.Handlers;

public class Server {
    int port;
    Handlers myhandlers;
    Gson gson;

    public Server() {
       myhandlers = new Handlers();
       gson = new Gson();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (req, res)-> myhandlers.registerUser(req, res));
        Spark.post("/session", (req, res)-> myhandlers.login(req, res));
        Spark.delete("/session", (req1, res1) -> myhandlers.logout(req1, res1));
        Spark.delete("/db", (req, res)-> myhandlers.clearDB(req, res));
        Spark.post("/game", (req, res)-> myhandlers.createGame(req, res));
        Spark.put("/game", (req, res)-> myhandlers.joinGame(req, res));
        Spark.get("/game", (req, res)-> myhandlers.listGames(req, res));

        Spark.exception(Exception.class, this::errorHandler);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public int port() {
        return Spark.port();
    }

    public Object errorHandler(Exception e, Request req, Response res) {
        var body = gson.toJson(new ErrorResponse(e.getMessage()));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

//    private String registerUser(Request req, Response res) {
//        model.UserData user = gson.fromJson(req.body(), UserData.class);
//        if(!userService.checkRequest(user)){
//            res.status(400);
//            return gson.toJson(new LoginResult(null, "Error: bad request"));
//        }
//        if (userService.getUser(user.username()) != null){
//            res.status(403);
//            return gson.toJson(new LoginResult(null, "Error: already taken"));
//        }
//
//        userService.addUser(user);
//        res.status(200);
//        return gson.toJson(authService.createAuth(user.username()));
//
//    }
//
//    private String login(Request req, Response res) {
//        model.UserData user = gson.fromJson(req.body(), UserData.class); // so then email should just be null, yeah?
//        if (!userService.verify(user)) {
//            res.status(401);
//            return gson.toJson(new LoginResult(null, "Error: unauthorized"));
//            // should I be throwing errors for these??
//        }
//        res.status(200);
//        return gson.toJson(authService.createAuth(user.username()));
//    }
//
//    private String logout(Request req, Response res) {
//        String authToken = req.headers("authorization");
//        if (authService.getAuth(authToken)== null){
//            res.status(401);
//            return gson.toJson( new ErrorResponse("Error: unauthorized"));
//        }
//        authService.deleteAuth(authToken);
//        res.status(200);
//        return gson.toJson(empty);
//    }
//
//    private String clearDB(Request req, Response res) {
//        try {
//            userService.clearUsers();
//            authService.clear();
//            gameService.clear();
//            res.status(200);
//            String body = gson.toJson(empty);// I guess this is returning an empty response
//            res.body(body);
//            return body;
//        } catch (Exception e) {
//            res.status(500);
//            return gson.toJson(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    private String createGame(Request req, Response res){
//        String gameName = gson.fromJson(req.body(), GameData.class).gameName();
//        if (gameName == null){
//            res.status(400);
//            return gson.toJson(new ErrorResponse("Error: bad request"));
//        }
//        if (!checkAuthToken(req)){
//            res.status(401);
//            return gson.toJson(new ErrorResponse("Error: unauthorized"));
//        }
//        int gameID = gameService.createGame(gameName);
//        res.status(200);
//        return gson.toJson(new GameData(gameID, null, null, null, null));
//    }
//
//    private boolean checkAuthToken(Request req){
//        String authToken = req.headers("authorization");
//        if (authToken == null){
//            return false;
//        }
//        return authService.getAuth(authToken) != null;
//    }
//
//    private String joinGame(Request req, Response res) throws DataAccessException {
//        JoinRequest joinRequest = gson.fromJson(req.body(), JoinRequest.class);
//        if (!checkAuthToken(req)){
//            res.status(401);
//            return gson.toJson(new ErrorResponse("Error: unauthorized"));
//        }
//        if (joinRequest.gameID() == 0){
//            res.status(400);
//            return gson.toJson(new LoginResult(null, "Error: bad request"));
//        }
//        String username = authService.getAuth(req.headers("authorization")).username();
//        if (username == null){
//            res.status(401);
//            return gson.toJson(new ErrorResponse("Error: unauthorized"));
//        }
//        if (joinRequest.playerColor()== null || (!joinRequest.playerColor().equals("BLACK") && !joinRequest.playerColor().equals("WHITE"))){
//            res.status(400);
//            return gson.toJson(new LoginResult(null, "Error: bad request"));
//        }
//       try {
//           if (gameService.isColorTaken(joinRequest.gameID(), joinRequest.playerColor())){
//               res.status(403);
//               return gson.toJson(new LoginResult(null, "Error: already taken"));
//           }
//
//       } catch (Exception e){
//           res.status(403);
//           return gson.toJson(new ErrorResponse(e.getMessage()));
//       }
//       res.status(200);
//       gameService.joinGame(joinRequest.gameID(), username, joinRequest.playerColor());
//       return gson.toJson(empty);
//    }
//
//    private String listGames(Request req, Response res){
//        if (!checkAuthToken(req)){
//            res.status(401);
//            return gson.toJson(new ErrorResponse("Error: unauthorized"));
//        }
////        if (gameService.listGames() == null){
////            res.status(200);
////            return gson.toJson(new ListGamesResult("games", new ArrayList<>()));
////        }
//        res.status(200);
//        return gson.toJson(new ListGamesResult(List.of(gameService.listGames())));
//    }
//
//    public Object errorHandler(Exception e, Request req, Response res) {
//        var body = gson.toJson(new ErrorResponse(e.getMessage()));
//        res.type("application/json");
//        res.status(500);
//        res.body(body);
//        return body;
//    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
