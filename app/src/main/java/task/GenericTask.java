package task;

import android.os.AsyncTask;

import com.obfuscation.ttr_phase1b.activity.GUIFacade;

import java.util.ArrayList;

import communication.Game;
import communication.Result;
import model.ModelFacade;
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
            case "GetPlayerList":
                return serverProxy.GetPlayerList((String) params[0], (String) params[1]);
            default:
                return new Result(false,null, "Invalid Request");
        }


    }

    @Override
    public void onPostExecute(Result result) {

        // TODO: call onLogin() through layerFacade

        GUIFacade guiFacade = GUIFacade.getInstance();
        switch (action) {
            case "Login":
                FetchAuthTokenFrom(result);
                guiFacade.onLogin(result);
                break;
            case "Register":
                //need to update the authkey
                FetchAuthTokenFrom(result);
                guiFacade.onRegister(result);
            case "JoinGame":
                //no need to update the model
                guiFacade.onJoinGame(result);
            case "CreateGame":
                //no need to update the model
                guiFacade.onCreateGame(result);
                break;
            case "StartGame":
                guiFacade.onStartGame(result);
                break;
            case "GetGameList":
                FetchGameListFrom(result);
                guiFacade.onGetGameList(result);
                break;
            case "GetPlayerList":
                // it might just update things in model
                guiFacade.onGetPlayerList(result);
            default:
                break;
        }


    }

    private void FetchAuthTokenFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setAuthToken((String) result.getData());
        }
    }

    private void FetchGameListFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().getGameListModel().setGames((ArrayList<model.Game>) result.getData());
        }
    }



}
