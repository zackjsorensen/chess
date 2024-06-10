import dataaccess.exception.ResponseException;
import model.UserData;

import java.net.MalformedURLException;

public class ServerFacade {
    ClientCommunicator communicator;

    public ServerFacade() {
        communicator = new ClientCommunicator();
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


}
