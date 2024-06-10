package serveraccess;

import dataaccess.exception.ResponseException;
import model.UserData;
import java.net.MalformedURLException;

public class ServerFacade {
    ClientCommunicator communicator;

    public ServerFacade(int port){
        communicator = new ClientCommunicator(port);
    }

    public ServerFacade() {
        this(8080);
    }

    public ResponseObj register(UserData user) throws MalformedURLException, ResponseException {
        return communicator.register(user);
    }

    public ResponseObj login(UserData user) throws MalformedURLException, ResponseException {
        return communicator.login(user);
    }

    public ResponseObj logout(String authToken) throws MalformedURLException, ResponseException {
        return communicator.logout(authToken);
    }

    public void clear() throws MalformedURLException, ResponseException {
        communicator.clear();
    }


}
