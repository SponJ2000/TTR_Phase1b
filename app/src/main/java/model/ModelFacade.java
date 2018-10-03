package model;


import server.Result;

/**
 * Created by hao on 10/3/18.
 */

public class ModelFacade {

    private static ModelFacade modelFacade;

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            return new ModelFacade();
        }
        return modelFacade;
    }

    public Result Login(String id, String password){
        return null;
    }
    public Result Register(String id, String password) {
        return null;
    }
    public Result JoinGame(String id, Game game) {return null;}

    public Result CreateGame(Game game) {
        return null;
    }
    public Result StartGame(Game game) {
        return null;
    }

    public Result GetGameList() {
        return null;
    }
}
