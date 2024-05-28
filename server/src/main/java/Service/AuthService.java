package Service;

import dataaccess.MemoryAuthDAO;
import model.AuthData;

import java.util.UUID;

public class AuthService {
    private final MemoryAuthDAO dataAccess;


    public AuthService(MemoryAuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData getAuth(String authToken){
        return dataAccess.get(authToken);
    }

    public void clear(){
        dataAccess.clear();
    }

    public void deleteAuth(String authToken){
        dataAccess.delete(authToken);
    }

    public AuthData createAuth(String username){
        // do I need to check for an existing auth?
        String newAuth = UUID.randomUUID().toString();
        AuthData myAuth = new AuthData(newAuth, username);
        dataAccess.add(myAuth);
        return myAuth;
    }
}
