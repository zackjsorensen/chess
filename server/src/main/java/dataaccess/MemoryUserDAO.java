package dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements DataAccessInterface {
    private Map<String, UserData> myUsers;

    public MemoryUserDAO() {
        myUsers = new HashMap<>();
    }

    @Override
    public void clear() {
        myUsers = new HashMap<>();
    }

    @Override
    public void add(Record thingToAdd) {
        UserData user = (UserData) thingToAdd;
        myUsers.put(user.username(), user);
    }

    @Override
    public Record get(String identifier) {
        return myUsers.get(identifier);
    }
}
