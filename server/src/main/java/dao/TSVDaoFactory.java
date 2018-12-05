package dao;

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
