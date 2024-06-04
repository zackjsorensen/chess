package dataaccess.memorydao;

import dataaccess.DataAccessInterface;
import dataaccess.exception.ResponseException;
import model.UserData;

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
    public void clear(String databaseName) throws ResponseException {

    }

    @Override
    public int add(UserData dataObj) {
        myData.put(dataObj.username(), dataObj);
        return 0;
    }

    @Override
    public UserData get(String identifier) {
        return myData.get(identifier);
    }
}