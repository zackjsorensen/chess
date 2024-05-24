import Service.UserService;
import chess.*;
import dataaccess.MemoryUserDAO;
import server.Server;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        try {
            var port = 8080;
            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }
            // create a server - something like this
            Server server = new Server();
            server.run(port);
//            var port = server.run(port);
//            System.out.println("Started test HTTP server on " + port);
//            createRoutes();

//            Spark.awaitInitialization();
            System.out.println("Listening on port " + port);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Specify the port number as a command line parameter");
        }
    }
}

//    private static void createRoutes() {
//        Spark.get("/hello", (req, res) -> "Hello Zacharias!");
//    }


//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Server: " + piece);


//
//            var service = new UserService(new MemoryUserDAO());
//            var server = new Server();
//            server.run(port);
//
//            System.out.printf("Server started on port %d%n", port);
//            return;
//        } catch (Throwable ex) {
//            System.out.printf("Unable to start server: %s%n", ex.getMessage());
//
//        }
//    }
//}