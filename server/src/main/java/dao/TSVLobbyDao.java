package dao;

import java.sql.Blob;
import java.util.List;

import tsv.TSVReaderWriter;

public class TSVLobbyDao implements ILobbyDao {
    private TSVReaderWriter rw;

    TSVLobbyDao() {
        String[] header = new String[3];
        header[0] = "id";
        header[1] = "password";
        header[2] = "authtoken";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addLobby(String id, Blob lobby) {
        return false;
    }

    @Override
    public boolean removeLobby(String id) {
        return false;
    }

    @Override
    public boolean updateLobby(String id, Blob lobby) {
        return false;
    }

    @Override
    public List<Blob> getLobbies() {
        return null;
    }

}
