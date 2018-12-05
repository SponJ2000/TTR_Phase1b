package dao;

public interface IDoaFactory {

    IUserDao getUserDao();
    ILobbyDao getLobbyDao();
    IGameDao getGameDao();

}
