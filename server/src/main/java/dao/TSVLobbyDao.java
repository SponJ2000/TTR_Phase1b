package dao;

import java.sql.Blob;
import java.util.List;

public class TSVLobbyDao implements ILobbyDao {

    TSVLobbyDao() {

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
