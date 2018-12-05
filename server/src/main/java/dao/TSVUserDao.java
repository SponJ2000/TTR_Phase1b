package dao;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import communication.Player;
import communication.PlayerUser;
import tsv.TSVReaderWriter;

public class TSVUserDao implements IUserDao {

    private TSVReaderWriter rw;

    TSVUserDao() {
        String[] header = new String[3];
        header[0] = "id";
        header[1] = "password";
        header[2] = "authtoken";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addUser(String id, String password, String authtoken) {
        String[] row = new String[3];
        row[0] = id;
        row[1] = password;
        row[2] = authtoken;

        rw.writeLine(row);

        return true;
    }

    @Override
    public boolean removeUser(String id) {

        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[0].equals(id)) {
                rows.remove(i);
                break;
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateAuthToken(String id, String authtoken) {

        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[0].equals(id)) {
                rows.get(i)[2] = authtoken;
                break;
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<String[]> rows = rw.readAll();
        for(String[] user : rows) {
            users.add(new User(user[0], user[1], user[2]));
        }
        return users;
    }

}
