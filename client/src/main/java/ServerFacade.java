import model.UserData;

import java.net.MalformedURLException;

public class ServerFacade {
    ClientCommunicator communicator;

    public ServerFacade() {
        communicator = new ClientCommunicator();
    }

    public ResponseObj register(UserData user) throws MalformedURLException {
        return communicator.register(user);
    }

    public ResponseObj login(UserData user) throws MalformedURLException {
        return communicator.login(user);
    }


}
