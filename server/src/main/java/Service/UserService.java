package Service;

import dataaccess.MemoryUserDAO;
import model.UserData;

public class UserService {
    private final MemoryUserDAO dataAccess;
    // this is the DAO object created by main where data is stored while the service is running

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public UserData getUser(String username){
        return (UserData) dataAccess.get(username);
    }

    public void addUser(UserData user){
        dataAccess.add(user);
    }

    public void clearUsers(){
        dataAccess.clear();
    }

}
