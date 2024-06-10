package client;

import com.google.gson.Gson;
import dataaccess.exception.ResponseException;
import model.AuthData;
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
        var port = server.run(8080); // why 0? No entiendo..
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
        ResponseObj res = facade.login(hoid);
        AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
        ResponseObj res2 = facade.logout(auth.authToken());
        assert(res2.statusCode() == 200);
    }

    @Test
    public void logoutBad() throws MalformedURLException, ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> facade.logout("das;dfk"));
    }

    @Test
    public void createGameGood() throws MalformedURLException, ResponseException {
        ResponseObj res = facade.register(hoid);
        AuthData auth = new Gson().fromJson(res.body(), AuthData.class);
        int id = facade.createGame("Uno", auth.authToken());
        Assertions.assertInstanceOf(Integer.class, id);
    }

    @Test
    public void createGameBad() throws MalformedURLException, ResponseException {
        try {
            facade.createGame("Uno", null);
        } catch (ResponseException e) {
            Assertions.assertEquals("Unauthorized", e.getMessage());
        }
    }

}
