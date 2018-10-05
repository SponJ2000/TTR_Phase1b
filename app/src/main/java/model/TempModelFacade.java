package model;

import android.os.AsyncTask;
import android.util.Log;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;

import communication.*;

public class TempModelFacade {

    private static final String TAG = "TempModelFacade";

    private static TempModelFacade modelFacade;

    private Game mCurrentGame;
    private Player mUser;

    private TempModelFacade() {
        mUser = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mUser);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );
        mCurrentGame = new Game("new republic (the game id)", mUser.getmUsername(), fakePlayers, 5);
    }

    public static TempModelFacade getInstance() {
        if (modelFacade == null) {
            return new TempModelFacade();
        }
        return modelFacade;
    }

    public Result Login(String id, String password){
        mUser = new Player(id);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result Register(String id, String password) {
        mUser = new Player(id);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result JoinGame(String id, Game game) {
        mCurrentGame = game;
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result CreateGame(Game game) {
        mCurrentGame = game;
        Log.d(TAG, "Current game: " + mCurrentGame.toString());
        PresenterFacade.getInstance().onComplete(null);
        PresenterFacade.getInstance().updateFragment(null);
        return null;
    }

    public Result LeaveGame(Game game) {
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result StartGame(Game game) {
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result GetGameList() {
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Game GetCurrentGame() {
        Log.d(TAG, "Current game: " + mCurrentGame.toString());
        return mCurrentGame;
    }

    public Player GetUser() {
        return mUser;
    }

    private class createGameTask extends AsyncTask<Void, Void, Result> {

        @Override
        protected Result doInBackground(Void... params) {
//          check the server then return the server result
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            return;
        }
    }

}
