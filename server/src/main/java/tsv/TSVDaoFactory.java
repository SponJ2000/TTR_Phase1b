package tsv;

import dao.IDaoFactory;
import dao.IGameDao;
import dao.ILobbyDao;
import dao.IUserDao;

public class TSVDaoFactory implements IDaoFactory {

    @Override
    public IUserDao getUserDao() {
        return new TSVUserDao();
    }

    @Override
    public ILobbyDao getLobbyDao() {
        return new TSVLobbyDao();
    }

    @Override
    public IGameDao getGameDao() {
        return new TSVGameDao();
    }

}
