package server;

import com.google.gson.Gson;
import server.reqresobjects.*;
import spark.*;
import server.handlers.Handlers;

public class Server {
    Handlers myhandlers;
    Gson gson;

    public Server() {
       myhandlers = new Handlers();
       gson = new Gson();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

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
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
