package task;

import android.os.AsyncTask;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.Result;
import model.ModelRoot;
import server.ServerProxy;

/**
 * Created by hao on 10/3/18.
 */

public class GenericTask extends AsyncTask<Object, Void, Result> {

    String action;

    public GenericTask (String action) {
        this.action = action;
    }

    @Override

    public Result doInBackground(Object... params) {
        ServerProxy serverProxy = new ServerProxy();


        switch (action) {
            case "Login":
                return serverProxy.Login((String) params[0], (String) params[1]);
            case "Register":
                return serverProxy.Register((String) params[0], (String) params[1]);
            case "JoinGame":
                return serverProxy.JoinGame((String) params[0], (String) params[1], (String) params[2]);
            case "CreateGame":
                return serverProxy.CreateGame((Game) params[0], (String) params[1]);
            case "StartGame":
                return serverProxy.StartGame((String) params[0], (String) params[1]);
            case "GetGameList":
                return serverProxy.GetGameList((String) params[0]);
            case "GetGame":
                return serverProxy.GetGame((String) params[0], (String) params[1]);
            default:
                return new Result(false,null, "Invalid Request");
        }


    }

    @Override
    public void onPostExecute(Result result) {

        switch (action) {
            case "Login":
                FetchAuthTokenFrom(result);
                break;
            case "Register":
                //need to update the authkey
                FetchAuthTokenFrom(result);
                break;
            case "GetGameList":
                FetchGameListFrom(result);
                break;
            case "GetPlayerList": //TODO: change into getGame
                FetchPlayerListFrom(result);
                break;
            default:
                break;
        }
        PresenterFacade.getInstance().updateFragment(result);

    }

    private void FetchAuthTokenFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setAuthToken((String) result.getData());
        }
    }

    private void FetchGameListFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setGameList((List<Game>) result.getData());
        }
    }

    private void FetchPlayerListFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().getGame().setPlayers((ArrayList<communication.Player>) result.getData());
        }
    }



}
