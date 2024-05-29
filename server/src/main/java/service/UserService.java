package service;

import dataaccess.MemoryUserDAO;
import model.UserData;

public class UserService {
    private final MemoryUserDAO dataAccess;
    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void addUser(UserData user) {
        dataAccess.add(user);
    }

    public UserData getUser(String username){
        return (UserData) dataAccess.get(username);
    }

    public void clearUsers(){
        dataAccess.clear();
    }

    public boolean checkRequest(UserData user){
        return user.password() != null && user.username() != null && user.email() != null;
    }
    public boolean verify(UserData user) {
        if (getUser(user.username()) == null){
            return false;
        } else {
            UserData dbUser = getUser(user.username());
            return user.password().equals(dbUser.password());
        }
    }

}
