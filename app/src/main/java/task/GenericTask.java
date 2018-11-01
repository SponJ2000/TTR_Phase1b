package task;

import android.os.AsyncTask;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.Message;
import communication.Result;
import communication.Serializer;
import communication.Ticket;
import model.ModelFacade;
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
            case "Login":
                return serverProxy.Login((String) params[0], (String) params[1]);
            case "Register":
                return serverProxy.Register((String) params[0], (String) params[1]);
            case "JoinGame":
                return serverProxy.JoinGame((String) params[0], (String) params[1], (String) params[2]);
            case "LeaveGame":
                return serverProxy.LeaveGame((String) params[0], (String) params[1], (String) params[2]);
            case "CreateGame":
                return serverProxy.CreateGame((Game) params[0], (String) params[1]);
            case "StartGame":
                return serverProxy.StartGame((String) params[0], (String) params[1]);
            case "GetGameList":
                return serverProxy.GetGameList((String) params[0]);
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
            case "DrawTrainCard":
                return serverProxy.DrawTrainCard((int) params[0], (String) params[1]);
            default:
                return new Result(false,null, "Invalid Request");
        }


    }

    @Override
    public void onPostExecute(Result result) {

        switch (action) {
            case "Login":
                OnSignIn(result);
                break;
            case "Register":
                //need to update the authkey
                OnSignIn(result);
                break;
            case "GetGameList":
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
            ArrayList<Game> games = new ArrayList<>();
            for (int i = 0;i < temp.size(); i++) {
                games.add(new Serializer().deserializeGame(temp.get(i).toString()));
            }
            ModelRoot.getInstance().setGameList(games);

        }
    }

    private void FetchGameFrom(Result result) {
        if (result.isSuccess()) {
            ModelRoot.getInstance().setGame((Game) result.getData());
        }
    }

    private void OnTickectsChoosen(Result result) {
//        if (ModelFacade.getInstance().getTickets()!= null)
//        System.out.println("ticket size is: " + ModelFacade.getInstance().getTickets().size());
        System.out.println(result.toString());
        if (result.isSuccess()) {
//            System.out.println("make ticket added to the player");
//            System.out.println("ticket size is: " + ModelFacade.getInstance().getTickets().size());
//            System.out.println("number of tickets owned: " + ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).getTickets());
            ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).setTickets(ModelRoot.getInstance().getTicketsWanted());
            ModelRoot.getInstance().setTicketsWanted(new ArrayList<Ticket>());
//            System.out.println("number of tickets owned: " + ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).getTickets());
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
                ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).setTickets(ticketsRecieved);
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
            ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).setTicketToChoose(ticketsToChoose);
        }
    }
}
