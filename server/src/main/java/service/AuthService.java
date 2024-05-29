package service;

import dataaccess.MemoryAuthDAO;
import model.AuthData;

import java.util.UUID;

public class AuthService {
    private final MemoryAuthDAO dataAccess;
    public AuthService(MemoryAuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData createAuth(String username){
        String newAuth = UUID.randomUUID().toString();
        AuthData myAuth = new AuthData(newAuth, username);
        dataAccess.add(myAuth);
        return myAuth;
    }

    public AuthData getAuth(String authToken){
        return dataAccess.get(authToken);
    }

    public void deleteAuth(String authToken){
        dataAccess.delete(authToken);
    }

    public void clear(){
        dataAccess.clear();
    }
}
