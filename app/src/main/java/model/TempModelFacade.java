package model;

import android.os.AsyncTask;
import android.util.Log;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import communication.Player;
import communication.Game;

public class TempModelFacade {

    private static final String TAG = "TempModelFacade";

    private static TempModelFacade modelFacade;

    private Game mCurrentGame;
    private Player mUser;
    private List<Game> mGameList;

    private TempModelFacade() {
        mUser = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mUser);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        mCurrentGame = new Game("new republic (the game id)", mUser.getPlayerName(), fakePlayers, 5);
        fakePlayers = new ArrayList<>();
        fakePlayers.add( new Player("other 1") );
        fakePlayers.add( new Player("other 2") );
        fakePlayers.add( new Player("other 3") );
        mGameList = new ArrayList<>();
        mGameList.add(mCurrentGame);
        mGameList.add( new Game("myGame", "bob", fakePlayers, 5) );
        mGameList.add( new Game("the other game", "johnny", fakePlayers, 4) );
        mGameList.add( new Game("ticket to lose", "lil' jimmy", fakePlayers, 5) );
        mGameList.add( new Game("i love pesto", "helo bots!", fakePlayers, 4) );
    }

    public static TempModelFacade getInstance() {
        if (modelFacade == null) {
            modelFacade = new TempModelFacade();
        }
        return modelFacade;
    }

    public void Login(String id, String password){
        mUser = new Player(id);
        Log.d(TAG, "Current user: " + mUser.toString());
        new serverTask().execute();
        Log.d(TAG+"_join", "end");
    }

    public void Register(String id, String password) {
        mUser = new Player(id);
        Log.d(TAG, "Current user: " + mUser.toString());
        new serverTask().execute();
        Log.d(TAG+"_join", "end");
    }

    public void JoinGame(String id, Game game) {
        mCurrentGame = game;
        Log.d(TAG+"_join", "Current game: " + mCurrentGame.toString());
        new serverTask().execute();
        Log.d(TAG+"_join", "end");
    }

    public void CreateGame(Game game) {
        mCurrentGame = game;
        Log.d(TAG+"_create", "Current game: " + mCurrentGame.toString());
        new serverTask().execute();
        Log.d(TAG+"_join", "end");
    }

    public void LeaveGame(Game game) {
        new serverTask().execute();
    }

    public void StartGame(Game game) {
        new serverTask().execute();
    }

    public void UpdateGameList() {
//      go grab the updated GameList from the server
        new serverTask().execute();
    }

    public List<Game> GetGameList() {
        Log.d(TAG+"_get", "getting gamelist: " + mGameList);
        return mGameList;
    }

    public Game GetCurrentGame() {
        Log.d(TAG+"_get", "Current game: " + mCurrentGame.toString());
        return mCurrentGame;
    }

    public Player GetUser() {
        Log.d(TAG+"_get", "Current user: " + mUser.toString());
        return mUser;
    }

    private class serverTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
//          check the server then return the server result
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//                Log.d(TAG, "timer exception");
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG+"_task", "telling presenter to update self");
            PresenterFacade.getInstance().onComplete(result);
            PresenterFacade.getInstance().updateFragment(result);
        }
    }

}
