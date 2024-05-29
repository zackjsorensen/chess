import server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            var port = 8080;
            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }
            Server server = new Server();
            server.run(port);

            System.out.println("Listening on port " + port);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Specify the port number as a command line parameter");
        }
    }
}