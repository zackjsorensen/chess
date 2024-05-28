package Service;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.UserData;

public class UserService {
    private final MemoryUserDAO dataAccess;
    // this is the DAO object created by the server where data is stored while the service is running - starts out empty

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public UserData getUser(String username){
        return (UserData) dataAccess.get(username);
    }

    public boolean checkRequest(UserData user){
        if (user.password() == null || user.username() == null || user.email() == null){
            return false;
        }
        return true;
    }

    public void addUser(UserData user) {
        dataAccess.add(user);
    }

    public void clearUsers(){
        dataAccess.clear();
    }

    public boolean verify(UserData user) {
        if (getUser(user.username()) == null){
            return false;
        } else {
            UserData dbUser = getUser(user.username());
            // should I throw exceptions here? Honestly I don't think so...
            return user.password().equals(dbUser.password());
        }
    }

}
