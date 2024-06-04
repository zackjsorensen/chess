package dataaccess.memorydao;

import dataaccess.DataAccessInterface;
import dataaccess.exception.ResponseException;
import model.AuthData;

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
    public void clear(String databaseName) throws ResponseException {
    }

    @Override
    public int add(AuthData dataObj) {
        myData.put(dataObj.authToken(), dataObj);
        return 0;
    }

    @Override
    public AuthData get(String identifier) {
        return myData.get(identifier);
    }

    public void delete(String authToken){
        myData.remove(authToken);
    }
}
