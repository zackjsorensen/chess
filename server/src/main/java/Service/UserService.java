package Service;

import dataaccess.MemoryUserDAO;

public class UserService {
    private MemoryUserDAO dataAccess;
    // this is the DAO object created by main where data is stored while the service is running

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

}
