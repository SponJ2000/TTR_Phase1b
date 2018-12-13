package dao.tsv;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import communication.LobbyGame;
import communication.Serializer;
import dao.ILobbyDao;

import static dao.tsv.DATA_TYPES.LOBBY;


public class TSVLobbyDao implements ILobbyDao {
    private TSVReaderWriter rw;

    private static int ARRAY_SIZE = 4;

    private static int i_TYPE = 0;
    private static int i_ID = 1;
    private static int i_LOBBY = 2;
    private static int i_NULL = 3;

    TSVLobbyDao() {
        String[] header = new String[ARRAY_SIZE];
        header[i_TYPE] = LOBBY;
        header[i_ID] = "id";
        header[i_LOBBY] = "lobby";
        header[i_NULL] = "null";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addLobby(String id, LobbyGame lobby) {
        String[] row = new String[ARRAY_SIZE];
        row[i_TYPE] = LOBBY;
        row[i_ID] = id;
        row[i_LOBBY] = new Serializer().serializeGameLobby(lobby);
        row[i_NULL] = "";
        rw.writeLine(row);

        return true;
    }

    @Override
    public boolean removeLobby(String id) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[i_TYPE].equals(LOBBY)) {
                String [] row = rows.get(i);
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
    public boolean updateLobby(String id, LobbyGame lobby) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[i_TYPE].equals(LOBBY)) {
                String [] row = rows.get(i);
                if(row[i_ID].equals(id)) {
                    row[i_LOBBY] = new Serializer().serializeGameLobby(lobby);
                    break;
                }
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public List<LobbyGame> getLobbies() {
        List<LobbyGame> lobbies = new ArrayList<>();
        List<String[]> rows = rw.readAll();

        for(String[] row : rows) {
            if(row[i_TYPE].equals(LOBBY)) {
                try {
                    Serializer serializer = new Serializer();
                    lobbies.add(serializer.deserializeGameLobby(row[i_LOBBY]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return lobbies;
    }

}
