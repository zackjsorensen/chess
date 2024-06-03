package service;

import dataaccess.exception.ResponseException;
import dataaccess.memorydao.MemoryAuthDAO;
import dataaccess.sql.SQLAuthDAO;
import model.AuthData;

import java.util.UUID;

public class AuthService {
    private final SQLAuthDAO dataAccess;
    public AuthService(SQLAuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData createAuth(String username) throws ResponseException {
        String newAuth = UUID.randomUUID().toString();
        AuthData myAuth = new AuthData(newAuth, username);
        dataAccess.add(myAuth);
        return myAuth;
    }

    public AuthData getAuth(String authToken) throws ResponseException {
        return dataAccess.get(authToken);
    }

    public void deleteAuth(String authToken) throws ResponseException {
        dataAccess.delete(authToken);
    }

    public void clear() throws ResponseException {
        dataAccess.clear();
    }
}
