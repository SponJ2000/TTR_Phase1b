//package task;
//
//import android.os.AsyncTask;
//
//import com.obfuscation.ttr_phase1b.activity.GUIFacade;
//
//import communication.Game;
//import communication.Result;
//import server.ServerProxy;
//
///**
// * Created by hao on 10/3/18.
// */
//
//public class LoginTask extends AsyncTask<Object, Void, Result> {
//
//    String action;
//
//    public LoginTask (String action) {
//        this.action = action;
//    }
//
//    @Override
//
//    public Result doInBackground(Object... params) {
//        ServerProxy serverProxy = new ServerProxy();
//
//        try {
//            switch (action) {
//                case "Login":
//                    return serverProxy.Login((String) params[0], (String) params[1]);
//                case "Register":
//                    return serverProxy.Register((String) params[0], (String) params[1]);
//                case "JoinGame":
//                    return serverProxy.JoinGame((String) params[0], (String) params[1], (String) params[2]);
//                case "CreateGame":
//                    return serverProxy.CreateGame((Game) params[0], (String) params[1]);
//                case "StartGame":
//                    return serverProxy.StartGame((String) params[0], (String) params[1]);
//                case "GetGameList":
//                    return serverProxy.GetGameList((String) params[0]);
//                case "GetPlayerList":
//                    return serverProxy.GetPlayerList((String) params[0], (String) params[1]);
//                default:
//                    throw new Exception("Invalid calls");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return new Result(false,e.getMessage(),null);
//        }
//
//    }
//
//    @Override
//    public void onPostExecute(Result result) {
//
//        // TODO: call onLogin() through layerFacade
//
//        GUIFacade guiFacade = GUIFacade.getInstance();
//        switch (action) {
//            case "Login":
//                guiFacade.onLogin(result);
//                break;
//            case "Register":
//                guiFacade.onRegister(result);
//            case "JoinGame":
//                guiFacade.onJoinGame(result);
//            case "CreateGame":
//                guiFacade.onCreateGame(result);
//                break;
//            case "StartGame":
//                guiFacade.onStartGame(result);
//                break;
//            case "GetGameList":
//                guiFacade.onGetGameList(result);
//                // it might just update things in model
//                break;
//            case "GetPlayerList":
//                // it might just update things in model
//                guiFacade.onGetPlayerList(result);
//            default:
//                break;
//        }
//
//
//    }
//
//
//
//}
