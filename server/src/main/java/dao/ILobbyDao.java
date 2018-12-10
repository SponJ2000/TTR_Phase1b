package dao;

import java.sql.Blob;
import java.util.List;

import communication.LobbyGame;

public interface ILobbyDao {

    boolean addLobby(String id, LobbyGame lobby);
    boolean removeLobby(String id);
    boolean updateLobby(String id, LobbyGame lobby);
    List<Blob> getLobbies();

}
