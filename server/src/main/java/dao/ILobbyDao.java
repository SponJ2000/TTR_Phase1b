package dao;

import java.sql.Blob;
import java.util.List;

public interface ILobbyDao {

    boolean addLobby(String id, Blob lobby);
    boolean removeLobby(String id);
    boolean updateLobby(String id, Blob lobby);
    List<Blob> getLobbies();

}
