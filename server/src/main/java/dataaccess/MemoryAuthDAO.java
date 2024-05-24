package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements DataAccessInterface<String, AuthData> {
    private Map<String, AuthData> myData;
    public MemoryAuthDAO() {
        myData = new HashMap<>();
    }

    @Override
    public void clear() {
        myData.clear();
    }

    @Override
    public void add(AuthData dataObj) {
        myData.put(dataObj.authToken(), dataObj);
    }

    @Override
    public AuthData get(String identifier) {
        return myData.get(identifier);
    }
}
