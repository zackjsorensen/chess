package dataaccess;

import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO extends MemoryParentDAO<String, UserData> {

    public MemoryUserDAO() {
        super();
    }

    @Override
    public String getIdentifier(UserData dataObj) {
        return dataObj.username();
    }


    @Override
    public Object get() {
        return null;
    }

    @Override
    public Object getIdentifier(Object dataObj) {
        return null;
    }


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
