package sqldao;

import dao.IDaoFactory;
import dao.IGameDao;
import dao.ILobbyDao;
import dao.IUserDao;

public class SQLFactory implements IDaoFactory {

    private SQLUserDAO userDAO = new SQLUserDAO();
    private SQLLobbyDAO lobbyDAO = new SQLLobbyDAO();
    private SQLGameDAO gameDAO = new SQLGameDAO();

    @Override
    public IUserDao getUserDao() {
        return userDAO;
    }

    @Override
    public ILobbyDao getLobbyDao() {
        return lobbyDAO;
    }

    @Override
    public IGameDao getGameDao() {
        return gameDAO;
    }
}
