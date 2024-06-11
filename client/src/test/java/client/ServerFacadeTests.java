package client;

import com.google.gson.Gson;
import model.exception.DataAccessException;
import model.exception.ResponseException;
import model.AuthData;
import model.ListGamesResult;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import serveraccess.*;

import java.net.MalformedURLException;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    UserData hoid = new UserData("Hoid", "Cephandrius", "");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0); // why 0? No entiendo..
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @BeforeEach
    public void setup() throws MalformedURLException, ResponseException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerGood() throws MalformedURLException, ResponseException {
        ResponseObj res = facade.register(new UserData("Vlad", "Drac", ""));
        assert(res.statusCode() == 200);
    }

    @Test
    public void registerBad() throws MalformedURLException, ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> facade.register(new UserData("Grr", null, null)));
    }

    @Test
    public void loginGood () throws MalformedURLException, ResponseException {
        facade.register(hoid);
        ResponseObj res = facade.login(hoid);
        assert(res.statusCode() == 200);
    }

    @Test
    public void loginBad() throws MalformedURLException, ResponseException {
        facade.register(hoid);
        Assertions.assertThrows(ResponseException.class, () -> facade.login(new UserData("Hoid", "Na", null)));
    }

    @Test
    public void logoutGood() throws MalformedURLException, ResponseException {
        facade.register(hoid);
        AuthData auth = getAuthData(facade.login(hoid));
        ResponseObj res2 = facade.logout(auth.authToken());
        assert(res2.statusCode() == 200);
    }

    @Test
    public void logoutBad() throws MalformedURLException, ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> facade.logout("das;dfk"));
    }

    @Test
    public void createGameGood() throws MalformedURLException, ResponseException {
        int id = getId();
        Assertions.assertInstanceOf(Integer.class, id);
    }

    private int getId() throws MalformedURLException, ResponseException {
        AuthData auth = getAuthData(facade.register(hoid));
        int id = facade.createGame("Uno", auth.authToken());
        return id;
    }

    @Test
    public void createGameBad() throws MalformedURLException, ResponseException {
        try {
            facade.createGame("Uno", null);
        } catch (ResponseException e) {
            Assertions.assertEquals("Unauthorized", e.getMessage());
        }
    }

    @Test
    public void clearTest() throws MalformedURLException, ResponseException {
        facade.register(hoid);
        facade.clear();
        try {
            facade.login(hoid);
        } catch (ResponseException e){
            Assertions.assertEquals("Unauthorized", e.getMessage());
        }
    }

    @Test
    public void joinGood() throws MalformedURLException, ResponseException {
        AuthData auth = getAuthData(facade.register(hoid));
        int id = facade.createGame("Uno", auth.authToken());
        facade.joinGame(id, "BLACK", auth.authToken());
        ListGamesResult result = facade.listGames(auth.authToken());
        assert(result.games().getFirst().blackUsername().equals("Hoid"));
    }

    private AuthData getAuthData(ResponseObj facade) throws MalformedURLException, ResponseException {
        ResponseObj res = facade;
        AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
        return auth;
    }

    @Test
    public void joinBad() throws MalformedURLException, ResponseException {
        AuthData auth = getAuthData(facade.register(hoid));
        int id = facade.createGame("Uno", auth.authToken());
        facade.joinGame(id, "BLACK", auth.authToken());
        var body = facade.register(new UserData("a", "b", "c")).body();
        AuthData newAuth = new Gson().fromJson(body, AuthData.class);
        try {
            facade.joinGame(id, "BLACK", newAuth.authToken());
        } catch (ResponseException e){
            Assertions.assertEquals("Forbidden", e.getMessage());
        }
    }

    @Test
    public void listGood() throws MalformedURLException, ResponseException {
        AuthData auth = getAuthData(facade.register(hoid));
        facade.createGame("First", auth.authToken());
        ListGamesResult result = facade.listGames(auth.authToken());
        Assertions.assertEquals("First", result.games().getFirst().gameName());
    }

    @Test
    public void listEmpty() throws MalformedURLException, ResponseException {
        AuthData auth = getAuthData(facade.register(hoid));
        ListGamesResult result = facade.listGames(auth.authToken());
        assert(result.games().isEmpty());
    }

    @Test
    public void listBad() throws MalformedURLException, ResponseException, DataAccessException {
        Assertions.assertThrows(DataAccessException.class, ()-> facade.listGames(null));
    }

}
