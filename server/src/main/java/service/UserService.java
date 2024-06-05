package service;

import dataaccess.exception.ResponseException;
import dataaccess.sql.SQLUserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;


public class UserService {
    private final SQLUserDAO dataAccess;
    public UserService(SQLUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void addUser(UserData user) throws ResponseException {
        dataAccess.add(new UserData(user.username(), hashPassword(user.password()), user.email()));
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
            String storedPassword = getUser(user.username()).password();
            return BCrypt.checkpw(user.password(), storedPassword);
        }
    }

}
