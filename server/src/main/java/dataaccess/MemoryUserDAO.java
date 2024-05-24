package dataaccess;

import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements DataAccessInterface<String, UserData> {
    private Map<String, UserData> myData;

    public MemoryUserDAO() {
        myData = new HashMap<>();
    }

    @Override
    public void clear() {
        myData.clear();
    }

    @Override
    public void add(UserData dataObj) {
        myData.put(dataObj.username(), dataObj);
    }

    @Override
    public UserData get(String identifier) {
        return myData.get(identifier);
    }
}