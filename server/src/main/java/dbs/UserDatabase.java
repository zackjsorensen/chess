package dbs;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private Map<String, String> myUsers;
    public UserDatabase() {
        myUsers = new HashMap<>();
    }

    public void insert(String username, String password){
        myUsers.put(username, password);
    }

    // see what a model is??

}
