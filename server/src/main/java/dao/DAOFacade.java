package dao;

import java.sql.Blob;
import java.util.List;

import communication.GameServer;
import communication.LobbyGame;

/**
 * Created by jalton on 12/5/18.
 */

public class DAOFacade implements IGameDao, ILobbyDao, IUserDao {

    private IDaoFactory daoFactory = null;
    private static DAOFacade instance = null;

    private DAOFacade() {

    }

    public static DAOFacade getInstance() {
        if (instance == null) {
            instance = new DAOFacade();
        }

        return instance;
    }

    public void setDaoFactory(IDaoFactory factory) {
        daoFactory = factory;
    }

    @Override
    public boolean addGame(String gameID, GameServer game) {
        return false;
    }

    @Override
    public boolean addLobby(String id, LobbyGame lobby) {
        return false;
    }

    @Override
    public boolean addUser(String id, String password, String authtoken) {
        return false;
    }

    @Override
    public boolean removeLobby(String id) {
        return false;
    }

    @Override
    public boolean removeGame(String gameID) {
        return false;
    }

    @Override
    public boolean updateLobby(String id, LobbyGame lobby) {
        return false;
    }

    @Override
    public boolean updateGame(String gameID, GameServer game) {
        return false;
    }

    @Override
    public boolean removeUser(String id) {
        return false;
    }

    @Override
    public List<Blob> getLobbies() {
        return null;
    }

    @Override
    public boolean updateCmdList(String gameID, ArrayList<GenericCommand> cmdlist) {
        return false;
    }

    @Override
    public boolean updateAuthToken(String id, String authtoken) {
        return false;
    }

    @Override
    public List<User> getUsers() {
        return null; }

    @Override
    public List<GameServer> getGames() {
        return null;
    }
}
