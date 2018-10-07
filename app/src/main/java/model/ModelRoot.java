package model;

/**
 * Created by hao on 10/5/18.
 */

public class ModelRoot {

    private static ModelRoot modelRoot;

    public static ModelRoot getInstance(){
        if (modelRoot == null) {
            modelRoot = new ModelRoot();
        }

        return modelRoot;
    }

    private String authToken;

    private GameListModel gameListModel;

    private Game game;

    public ModelRoot() {
        authToken = null;

        gameListModel = new GameListModel();

        game = null;
    }

    public void setAuthToken (String s) {
        authToken = s;
    }

    public String getAuthToken () {
        return authToken;
    }

    public GameListModel getGameListModel() {
        if (gameListModel == null) {
            gameListModel = new GameListModel();
        }
        return gameListModel;
    }

    public Game getGame(){
        if (game == null) {
            game = new Game();
        }
        return game;
    }

}
