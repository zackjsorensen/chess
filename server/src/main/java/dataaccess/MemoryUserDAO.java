package dataaccess;

import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

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
    public void add(UserData dataObj) {
         myData.put(dataObj.username(), dataObj);
    }

    @Override
    public UserData get(String identifier) {
        return myData.get(identifier);
    }

//    @Override
//    public String getIdentifier(UserData dataObj) {
//        return "";
//    }
}

//    private Map<String, UserData> myUsers;
//
//    public MemoryUserDAO() {
//        myUsers = new HashMap<>();
//    }
//
//    @Override
//    public void clear() {
//        myUsers = new HashMap<>();
//    }
//
//    @Override
//    public void add(UserData thingToAdd) {
//        UserData user = (UserData) thingToAdd;
//        myUsers.put(user.username(), user);
//    }
//
//    @Override
//    public UserData get(String identifier) {
//        return myUsers.get(identifier);
//    }
//}
