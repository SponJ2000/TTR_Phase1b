package task;

import android.os.AsyncTask;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;

import communication.LobbyGame;
import communication.Message;
import communication.Result;
import communication.Serializer;
import communication.Ticket;
import model.ModelRoot;
import server.Poller;
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
            case "login":
                return serverProxy.Login((String) params[0], (String) params[1]);
            case "register":
                return serverProxy.Register((String) params[0], (String) params[1]);
            case "joinLobbyGame":
                return serverProxy.JoinLobby((String) params[0], (String) params[1], (String) params[2]);
            case "leaveGame":
                return serverProxy.LeaveGame((String) params[0], (String) params[1], (String) params[2]);
            case "leaveLobbyGame":
                return serverProxy.LeaveLobbyGame((String) params[0], (String) params[1], (String) params[2]);
            case "CreateLobby":
                return serverProxy.CreateLobby((LobbyGame) params[0], (String) params[1]);
            case "startGame":
                return serverProxy.StartGame((String) params[0], (String) params[1]);
            case "GetLobbyList":
                return serverProxy.GetLobbyList((String) params[0]);
            case "GetGame":
                return serverProxy.GetGame((String) params[0], (String) params[1]);
            case "CheckGameList":
                return serverProxy.CheckGameList((String) params[0]);
            case "CheckGame":
                return serverProxy.CheckGame((String) params[0], (String) params[1], (Integer)params[2]);
            case "SendMessage":
                return serverProxy.SendMessage((String) params[0], (String) params[1], (Message) params[2]);
            case "GetTickets":
                return serverProxy.GetTickets((String) params[0], (String) params[1]);
            case "ChooseTicket":
                return serverProxy.ChooseTicket((String) params[0], (String) params[1], (List<Ticket>) params[2]);
            case "ReturnTickets":
                return serverProxy.ReturnTickets((String) params[0], (String) params[1], (List<Ticket>) params[2]);
            case "DrawTrainCard":
                return serverProxy.DrawTrainCard((int) params[0], (String) params[1]);
            default:
                return new Result(false,null, "Invalid Request");
        }


    }

    @Override
    public void onPostExecute(Result result) {

        switch (action) {
            case "login":
                OnSignIn(result);
                break;
            case "register":
                //need to update the authkey
                OnSignIn(result);
                break;
            case "GetLobbyList":
                FetchGameListFrom(result);
                break;
            case "GetGame":
                FetchGameFrom(result);
                break;
            case "GetTickets":
                FetchTicketsOption(result);
                break;
            case "ChooseTicket":
                OnTickectsChoosen(result);
                break;
            case "DrawTrainCard":
                OnTrainCardDrawn(result);
            default:
                break;
        }
        PresenterFacade.getInstance().updatePresenter(result);

    }

    private void OnSignIn(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setAuthToken((String) result.getData());
            Poller.StartPoller();
        }
    }

    private void FetchGameListFrom(Result result) {
        if (result.isSuccess()) {

            ArrayList<Object> temp = (ArrayList<Object>) result.getData();
            ArrayList<LobbyGame> gameLobbies = new ArrayList<>();
            for (int i = 0;i < temp.size(); i++) {
                gameLobbies.add(new Serializer().deserializeGameLobby(temp.get(i).toString()));
            }
            ModelRoot.getInstance().setGameLobbies(gameLobbies);

        }
    }

    private void FetchGameFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setGame((LobbyGame) result.getData());
        }
    }

    private void OnTickectsChoosen(Result result) {
        System.out.println(result.toString());
        if (result.isSuccess()) {
            ModelRoot.getInstance().getGame().getPlayerUser().setTickets(ModelRoot.getInstance().getTicketsWanted());
            ModelRoot.getInstance().setTicketsWanted(new ArrayList<Ticket>());
        }
        else {
            System.out.println("errow masssage is ");
            System.out.println(result.getErrorInfo());
        }
    }

    private void OnTrainCardDrawn(Result result) {
        if (result.isSuccess()) {
            ModelRoot m = ModelRoot.getInstance();
            String userName = m.getUserName();
            if (result.getData() == null) {
                ArrayList<Ticket> ticketsRecieved = (ArrayList<Ticket>)result.getData();
                ModelRoot.getInstance().getGame().getPlayerUser().setTickets(ticketsRecieved);
            }
        }
    }

    private void FetchTicketsOption(Result result) {
        if (result.isSuccess()) {
            Serializer serializer = new Serializer();
            ArrayList<Object> objects = (ArrayList<Object>) result.getData();

            ArrayList<Ticket> ticketsToChoose = new ArrayList<Ticket>();
            for (Object o: objects) {
                ticketsToChoose.add((Ticket) serializer.deserializeTicket(o.toString()));
            }
            ModelRoot.getInstance().getGame().getPlayerUser().setTicketToChoose(ticketsToChoose);
        }
    }
}
