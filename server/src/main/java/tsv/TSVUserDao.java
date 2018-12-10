package tsv;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import communication.Player;
import communication.PlayerUser;
import dao.IUserDao;
import dao.User;
import tsv.TSVReaderWriter;

import static tsv.DATA_TYPES.USER;

public class TSVUserDao implements IUserDao {

    private TSVReaderWriter rw;

    private static int ARRAY_SIZE = 4;

    private static int i_TYPE = 0;
    private static int i_ID = 1;
    private static int i_PASS = 2;
    private static int i_AUTH = 3;

    TSVUserDao() {
        String[] header = new String[ARRAY_SIZE];
        header[i_TYPE] = "type";
        header[i_ID] = "id";
        header[i_PASS] = "password";
        header[i_AUTH] = "authtoken";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addUser(String id, String password, String authtoken) {
        String[] row = new String[ARRAY_SIZE];
        row[i_TYPE] = USER;
        row[i_ID] = id;
        row[i_PASS] = password;
        row[i_AUTH] = authtoken;

        rw.writeLine(row);

        return true;
    }

    @Override
    public boolean removeUser(String id) {

        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row[i_TYPE].equals(USER)) {
                if(row[i_ID].equals(id)) {
                    rows.remove(i);
                    break;
                }
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateAuthToken(String id, String authtoken) {

        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if(row[i_TYPE].equals(USER)) {
                if(row[i_ID].equals(id)) {
                    row[i_AUTH] = authtoken;
                    break;
                }
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
            if (user[i_TYPE].equals(USER)) {
                users.add(new User(user[i_ID], user[i_PASS], user[i_AUTH]));
            }
        }
        return users;
    }

}
