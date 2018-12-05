package dao;

import java.sql.Blob;
import java.util.List;

public class TSVUserDao implements IUserDao {

    TSVUserDao() {

    }

    @Override
    public boolean addUser(String id, String password, String authtoken) {
        return false;
    }

    @Override
    public boolean removeUser(String id) {
        return false;
    }

    @Override
    public boolean updateAuthToken(String id) {
        return false;
    }

    @Override
    public List<Blob> getUsers() {
        return null;
    }

}
