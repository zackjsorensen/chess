package service;

import dataaccess.exception.ResponseException;
import dataaccess.sql.SQLUserDAO;
import model.UserData;

public class UserService {
    private final SQLUserDAO dataAccess;
    public UserService(SQLUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void addUser(UserData user) throws ResponseException {
        dataAccess.add(user);
    }

    public UserData getUser(String username) throws ResponseException{
        return (UserData) dataAccess.get(username);
    }

    public void clearUsers() throws ResponseException {
            dataAccess.clear();
        // when should I handle and when should I declare? // Should I always convert to runtime exception?
    }

    public boolean checkRequest(UserData user){
        return user.password() != null && user.username() != null && user.email() != null;
    }
    public boolean verify(UserData user) throws ResponseException {
        if (getUser(user.username()) == null){
            return false;
        } else {
            UserData dbUser = getUser(user.username());
            return user.password().equals(dbUser.password());
        }
    }

}
