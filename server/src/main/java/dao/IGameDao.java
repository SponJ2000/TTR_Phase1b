package dao;

import java.sql.Blob;
import java.util.List;

public interface IGameDao {

    boolean addGame(String gameID, Blob game);
    boolean removeGame(String gameID);
    boolean updateGame(String gameID, Blob game);
    boolean updateCmdList(String gameID, Blob cmdlist);
    List<Blob> getGames();

}
