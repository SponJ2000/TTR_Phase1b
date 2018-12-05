package dao;

import java.sql.Blob;
import java.util.List;

public class TSVGameDao implements IGameDao {

    TSVGameDao() {
    }

    @Override
    public boolean addGame(String gameID, Blob game) {
        return false;
    }

    @Override
    public boolean removeGame(String gameID) {
        return false;
    }

    @Override
    public boolean updateGame(String gameID, Blob game) {
        return false;
    }

    @Override
    public boolean updateCmdList(String gameID, Blob cmdlist) {
        return false;
    }

    @Override
    public List<Blob> getGames() {
        return null;
    }

}
